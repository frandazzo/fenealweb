package applica.feneal.services.impl;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.NotificationRepository;
import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.UtenteDBNazioneRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.MessageService;
import applica.feneal.services.NotificationService;
import applica.feneal.services.messages.MessageInput;
import applica.feneal.services.utils.CustomTemplatedMail;
import applica.framework.LoadRequest;
import applica.framework.library.mail.MailException;
import applica.framework.library.mail.TemplatedMail;
import applica.framework.library.options.OptionsManager;
import applica.framework.library.utils.ProgramException;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fgran on 08/04/2016.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UtenteDBNazioneRepository utDBRep;

    @Autowired
    private NotificationRepository notRep;

    @Autowired
    private ImportazioniRepository impRep;

    @Autowired
    private Security sec;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private CompanyRepository compRep;

    @Autowired
    private MessageService msgServ;

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private IscrizioniRepository iscRep;

    @Override
    public void setNotificationRead(long notificationId) {
        Notification n = notRep.get(notificationId).orElse(null);
        if (n == null)
            return;
        n.setRead(true);
        notRep.save(n);
    }

    @Override
    public List<Notification> getLastNotifications() {
        LoadRequest req = LoadRequest.build().filter("recipientCompanyId", ((User) sec.getLoggedUser()).getCompany().getLid()).sort("date", true);
        req.setPage(1);
        req.setRowsPerPage(100);
        return notRep.find(req).getRows();
    }

    @Override
    public Notification getNotificationById(long notificationId) {
        Notification n = notRep.get(notificationId).orElse(null);
        if (n == null)
            return null;

        setNotificationRead(n);

        return n;
    }

    @Override
    public void createSignalWorkerNotification(Long workerId, String province, String text) throws Exception {
        User u = ((User) sec.getLoggedUser());

        //recupero il lavoratore
        Lavoratore l = lavSvc.getLavoratoreById(u.getLid(),workerId);
        if (l == null)
            throw new Exception("Lavoratore nullo");

        //recupero la provincia selezionata
        Province p = geoSvc.getProvinceByName(province);
        if (p == null)
            throw new Exception("Provincia nulla per la creazione di una notifica");
        //devo inoltre verificare che la provincia faccia parte di un territorio anagrafato in fenealgest
        Company c = compRep.findCompanyByProvinceName(province);
        if (c == null)
            throw new Exception("Nessun territorio designato per la provinc ia selezionata");


        Notification n = new Notification();
        n.setType(Notification.notification_sendworkerinfo);
        n.setRead(false);
        n.setDescription(text);
        n.setBriefDescription(l.getNamesurname());
        n.setDate(new Date());
        n.setBody(String.valueOf(l.getLid()));
        n.setRecipientCompanyId(c.getLid());
        n.setSender(u);

        notRep.save(n);

    }

    @Override
    public void sendNotificationMail(Long workerId, String mail, String text) throws Exception {
        User u = ((User) sec.getLoggedUser());
        Lavoratore l = lavSvc.getLavoratoreById(u.getLid(),workerId);
        if (l == null)
            throw new Exception("Lavoratore nullo");



        //posso adesso inviare la mail con i dati dela lavoaratore
        String workerData = String.format("Segnalazione lavoratore da %s: %s %s, con codice fiscale %s", u.getCompany().getDescription(), l.getName(), l.getSurname(), l.getFiscalcode());

        MessageInput i = new MessageInput();
        //inserisco un pre contenuto di presentazione
        String preContent = String.format("%s %s della %s ti ha segnalato il lavoratore in oggetto. <br/><br/> ", u.getName(), u.getSurname(), u.getCompany().getDescription());
        i.setContent(preContent + text);
        i.setMailFrom(u.getMail());
        String[] recs = mail.split(";");
        i.setRecipients(new ArrayList<String>(Arrays.asList(recs)));
        i.setSubject(workerData);
        msgServ.sendSimpleMail(i);

    }

    @Override
    public void sendThanksReportingMail(long notificationId) throws Exception {

        Notification n = getNotificationById(notificationId);

        if (n != null) {
            MessageInput i = new MessageInput();

            //recupero il lavoratore
            Lavoratore l = lavSvc.getLavoratoreById(((User) sec.getLoggedUser()).getLid(), Long.parseLong(n.getBody()));
            if (l == null)
                throw new Exception("Lavoratore nullo");

            String text = "Salve,<br>l'utente " + ((User) sec.getLoggedUser()).getCompleteName() + " ringrazia per la segnalazione del lavoratore " + l.getSurname() + " " + l.getName() + ".";
            text += "<br><br>Saluti,<br>" + ((User) sec.getLoggedUser()).getCompleteName();

            i.setContent(text);
            i.setMailFrom(((User) sec.getLoggedUser()).getMail());
            i.setRecipients(Arrays.asList(n.getSender().getMail()));
            i.setSubject("Ringraziamento per segnalazione lavoratore");
            msgServ.sendSimpleMail(i);
        }
    }

    @Override
    public void sendNotificationMailToCompanies(List<UtenteDbNazionale> lavs) {

        MessageInput i = new MessageInput();

        // Recupero tutte le company a cui inviare le notificazioni (in base alle iscrizioni degli utenti DB nazionale)
        Map<Company, List<UtenteDbNazionale>> mapCompaniesIscrizioniLavoratori = retrieveCompaniesByIscrizioniUtenti(lavs);

        String initialMailText = String.format("Salve,<br>l'utente %s di %s ha eseguito una ricerca e ha trovato i seguenti lavoratori:<br><br>", ((User) sec.getLoggedUser()).getCompleteName(), ((User) sec.getLoggedUser()).getCompany().toString());

        for (Company company : mapCompaniesIscrizioniLavoratori.keySet()) {
            // Se la mail di notifica non è stata settata oppure se la company corrente del ciclo è quella dell'utente loggato
            // allora non procedo nell'invio email
            if (company.getNotificationMail() == null || company.getId().equals(((User) sec.getLoggedUser()).getCompany().getId()))
                continue;

            String finalMailText = initialMailText;
            for (UtenteDbNazionale lav : mapCompaniesIscrizioniLavoratori.get(company)) {
                finalMailText += " - " + lav.getNomeCompleto() + " (" + lav.getCodiceFiscale() + ")<br>";
            }

            CustomTemplatedMail mail = new CustomTemplatedMail();
            mail.setOptions(optMan);
            mail.setMailFormat(TemplatedMail.HTML);
            mail.setTemplatePath("templates/mail/simpleMail.vm");
            mail.setFrom(optMan.get("registration.mail.from"));
            mail.setSubject("Notifica consultazione DB nazionale");
            mail.setTo(company.getNotificationMail());
            mail.put("content", finalMailText);

            // Invio email ad ogni company
            new Thread(() -> {
                try {
                    mail.send();
                } catch (MailException e) {
                    e.printStackTrace();
                    throw new ProgramException("Error sending registration mail");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    throw new ProgramException("Error sending registration mail");
                }
            }).start();
        }
    }

    @Override
    public void notifyImportDbNazionale(Integer id) {

        //devo ricercare l'importazione con lìid indicato per estrapolarne i lavoratori
        Importazione impp = impRep.get(id).orElse(null);
        if (impp == null)
            return;

        //recupero la company che ha fatto l'import se esiste
        Company c = compRep.findCompanyByProvinceName(impp.getNomeProvincia());

        //recuoero tutte le iscrizioni per l'importazione selezionata
        List<Iscrizione> iscrizioni = retrieveIscrizioni(id);
        //da ogni iscirzione recupero la lista dei lavoartori
        //suddivisa per province di residenza qualora esista..
        HashMap<String, List<UtenteDbNazionale>> list = new HashMap<>();
        for (Iscrizione iscrizione : iscrizioni) {
            int idLav = iscrizione.getId_Lavoratore();
            UtenteDbNazionale ut = utDBRep.get(idLav).orElse(null);

            if (ut != null)
                //se ovviamente hanno una provincia di residenza e non risiedono nella provincia afferente al territorio
                //che ha fatto l'importaizone....
                if (!StringUtils.isEmpty(ut.getNomeProvinciaResidenza())){

                    //verifico che l'utente non apppartenga al territorio che ha fatto l'importazione
                    //se tale territorio esiste oppure se non esiste inserisco tutto
                   // if (c == null || !c.containProvince(ut.getNomeProvinciaResidenza()))
                    if (!impp.getNomeProvincia().toLowerCase().equals(ut.getNomeProvinciaResidenza().toLowerCase()))
                    {
                        if (!list.containsKey(ut.getNomeProvinciaResidenza()))
                            list.put(ut.getNomeProvinciaResidenza(), new ArrayList<UtenteDbNazionale>());

                        List<UtenteDbNazionale> d = list.get(ut.getNomeProvinciaResidenza());
                        d.add(ut);
                    }
                }

        }

        //adesso che ho recuperato la lista degli utenti raggruppati per
        //provincia di residenza posso inviare le notifiche a tutti i territori esistenti
        //per farlo ovviamente devo inviarle solo ai territori che sono registrati nel sistema

        List<Company> cc = compRep.find(null).getRows();
        for (Company company : cc) {

            List<String> utenti = retrieveUtentiImportantForCompany(company, list);
            if (utenti.size() > 0){
                //posso adesso creare una notifica per la company
                Notification n = new Notification();
                n.setDate(new Date());
                n.setRecipientCompanyId(company.getLid());
                n.setRead(false);
                n.setDescription(String.format("%s ha inviato i dati al database nazionale per il settore %s anno %s. " +
                        "Controlla gli utenti che risiedono nel tuo territorio", impp.getNomeProvincia(), impp.getSettore() , String.valueOf(impp.getAnno())));
                n.setBody(String.join(";", utenti));
                n.setType(Notification.notification_newexport);

                notRep.save(n);
            }


        }

        //pertanto li recupero e per ogni territorio becco tutte le provincie che ne fanno parte
        //ed inserisco i codici fiscali dei lavoratori che risiedono in quella provincia in modo tale
        //che non appena richiedo l'apertura della notifica posso visualizzare una maschera di questo tipo:


        //***************************************************
        //La provincia di Trento ha inviato i dati al db nazionale:
        //ci sono 150 lavoratori che risiedono nella tua provincia
        //di cui 50 non sono anagrafati <a href="javascript:createListaLavoro()">Crea lista lavoro</a>
        //di cui 85 non hanno mai avuto una delega con te <a href="javascript:createListaLavoro()">Crea lista lavoro</a>
        //i restanti sono conosciuti in qualche modo (crea lista lavoro)



    }

    private List<String> retrieveUtentiImportantForCompany(Company company, HashMap<String, List<UtenteDbNazionale>> list) {

       List<Province> pr = company.getProvinces();

        List<String> result = new ArrayList<>();

        for (Province province : pr) {
            if (list.containsKey(province.getDescription().toUpperCase())){
                List<UtenteDbNazionale> l = list.get(province.getDescription().toUpperCase());
                List<String> r = l.stream().map(utenteDbNazionale -> utenteDbNazionale.getCodiceFiscale()).collect(Collectors.toList());
                result.addAll(r);
            }

        }

        //rimuovo eventuali codici fiscali duplicati
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);

        return result;

    }

    private Map<Company, List<UtenteDbNazionale>> retrieveCompaniesByIscrizioniUtenti(List<UtenteDbNazionale> lavs) {
        Map<Company, List<UtenteDbNazionale>> mapCompaniesIscrizioniLavoratori = new HashMap<>();
        List<Iscrizione> iscrizioni = lavs.stream().map(l -> l.getIscrizioni()).collect(Collectors.toList())
                .stream().flatMap(s -> s.stream()).collect(Collectors.toList());

        List<String> iscrizioniProvinces = iscrizioni.stream().map(l -> l.getNomeProvincia()).distinct().collect(Collectors.toList());

        iscrizioniProvinces.stream().forEach(prov -> {
            Company company = compRep.findCompanyByProvinceName(prov);
            if (company != null) {
                List<UtenteDbNazionale> lavsForProvince = lavs.stream().filter(l -> l.getIscrizioni().stream().anyMatch(i -> prov.equals(i.getNomeProvincia()))).collect(Collectors.toList());
                Company c = checkIfAlreadyExistsCompanyInIscrizioniMap(company, mapCompaniesIscrizioniLavoratori);
                if (c == null)
                    mapCompaniesIscrizioniLavoratori.put(company, lavsForProvince);
                else
                    // Accorpo i lavoratori nella stessa company nel caso si trovino su province diverse della medesima company
                    mapCompaniesIscrizioniLavoratori.get(c).addAll(lavsForProvince);
            }
        });

        return mapCompaniesIscrizioniLavoratori;
    }

    private List<Iscrizione> retrieveIscrizioni(int idImportazione) {

        LoadRequest req = LoadRequest.build().filter("id_Importazione", idImportazione);
        return iscRep.find(req).getRows();

    }

    private void setNotificationRead(Notification not){
        not.setRead(true);
        notRep.save(not);
    }

    private Company checkIfAlreadyExistsCompanyInIscrizioniMap(Company company, Map<Company, List<UtenteDbNazionale>> mapCompaniesIscrizioniLavoratori) {

        for (Company c : mapCompaniesIscrizioniLavoratori.keySet())
            if (c.getId().equals(company.getId()))
                return c;

        return null;
    }
}
