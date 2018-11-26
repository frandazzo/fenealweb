package applica.feneal.admin.facade;

import applica.feneal.admin.utils.LiberiExcelExporter;
import applica.feneal.admin.viewmodel.app.dashboard.report.AppReportIscritti;
import applica.feneal.admin.viewmodel.app.dashboard.report.AppReportNonIscritti;
import applica.feneal.admin.viewmodel.app.dashboard.report.UiAppReportNonIscrittiInput;
import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.*;
import applica.feneal.services.messages.MessageInput;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 15/04/2016.
 */
@Component
public class LiberiFacade {

    @Autowired
    private ReportNonIscrittiService libService;

    @Autowired
    private RichieseInfoAiTerritoriService richSvc;

    @Autowired
    private LavoratoreService lavServ;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private Security security;

    @Autowired
    private GeoService geoSvc;



    @Autowired
    private ParitheticRepository enteRep;


    @Autowired
    private LiberiExcelExporter exporter;

    public String printComplete(List<UiLibero> liberi) throws IOException {

        //qui devo scrivere tutto il codice per trasformare l'ui inn un oggettto document excel
        //da inviare ocn web service. non eseguo questa operazione nei servizi al causa del fatto che l'uilibero
        //è definito nell'admin e non ho voglia di trasformarlo....
        return exporter.createExcelFile(liberi);
    }




    public List<UiLibero> reportNonIscritti(LiberoReportSearchParams params) throws ParseException {

        List<LiberoDbNazionale> lib = libService.retrieveLiberi(params);

        return convertLiberiToUiLiberi(lib);




    }

    public List<UiLibero> convertLiberiToUiLiberi(List<LiberoDbNazionale> lib) throws ParseException {
        List<UiLibero> result = new ArrayList<>();

        for (LiberoDbNazionale liberoDbNazionale : lib) {
            UiLibero l = new UiLibero();
            l.setLavoratoreDelegheOwner(liberoDbNazionale.isDelegheOwner());
            l.setAziendaRagioneSociale(liberoDbNazionale.getCurrentAzienda());
            l.setLavoratoreCap(liberoDbNazionale.getCap());
            l.setLavoratoreCellulare(liberoDbNazionale.getTelefono());
            l.setLavoratoreCittaResidenza(liberoDbNazionale.getNomeComuneResidenza());
            l.setLavoratoreCodiceFiscale(liberoDbNazionale.getCodiceFiscale());
            l.setLavoratoreCognome(liberoDbNazionale.getCognome());

            l.setLavoratoreDataNascita(liberoDbNazionale.getDataNascita());
            l.setLavoratoreIndirizzo(liberoDbNazionale.getIndirizzo());
            l.setLavoratoreLuogoNascita(liberoDbNazionale.getNomeComune());
            l.setLavoratoreNazionalita(liberoDbNazionale.getNomeNazione());
            l.setLavoratoreNome(liberoDbNazionale.getNome());
            l.setLavoratoreCittaResidenza(liberoDbNazionale.getNomeComuneResidenza());
            l.setLavoratoreProvinciaNascita(liberoDbNazionale.getNomeProvincia());
            l.setLavoratoreProvinciaResidenza(liberoDbNazionale.getNomeProvinciaResidenza());
            if (liberoDbNazionale.getSesso().equals("MASCHIO"))
                l.setLavoratoreSesso(Lavoratore.MALE);
            else
            l.setLavoratoreSesso(Lavoratore.FEMALE);
            l.setLiberoEnteBilaterale(liberoDbNazionale.getEnte());


            l.setLiberoData(liberoDbNazionale.getLiberoAl());
            l.setLiberoProvincia(liberoDbNazionale.getNomeProvinciaFeneal());
            l.setLiberoIscrittoA(liberoDbNazionale.getIscrittoA());


            l.setIscrizioni(convertIscrizioniToUiiscrizioni(liberoDbNazionale.getIscrizioni()));

            l.setNumIscrizioni(l.getIscrizioni().size());

            result.add(l);
        }


        return result;
    }

    public List<UiIscrizione> convertIscrizioniToUiiscrizioni(List<Iscrizione> iscrizioni) {
        List<UiIscrizione> result = new ArrayList<>();

//        UiIscrizione ee = new UiIscrizione();
//        ee.setSettore("Edile");
//        ee.setNomeProvincia("Potenza");
//        ee.setAnno(2015);
//        ee.setNomeRegione("Basilicata");

        for (Iscrizione iscrizione : iscrizioni) {
            result.add(convertIscrizioneToUiIscrizione(iscrizione));
        }


        return result;
    }

    private UiIscrizione convertIscrizioneToUiIscrizione(Iscrizione iscrizione) {
        UiIscrizione i = new UiIscrizione();

        i.setAnno(iscrizione.getAnno());
        i.setAzienda(iscrizione.getAzienda());
        i.setContratto(iscrizione.getContratto());
        i.setEnte(iscrizione.getEnte());
        i.setLivello(iscrizione.getLivello());
        i.setNomeProvincia(iscrizione.getNomeProvincia());
        i.setPeriodo(iscrizione.getPeriodo());
        i.setNomeRegione(iscrizione.getNomeRegione());
        i.setPiva(iscrizione.getPiva());
        i.setQuota(iscrizione.getQuota());
        i.setSettore(iscrizione.getSettore());


        return i;
    }

    /* Chiama il servizio per inviare la mail di richiesta info ai destinatari indicati nel form
        e all'utente loggato
     */
    public void sendRequestInfoLiberiMail(String destinatario, String htmlLiberi, String provinceInfo, List<UiLibero> selectedUsers) throws Exception {

        if (selectedUsers.size() == 0)
            return;
        // N.B.: 'destinatario' può anche essere una lista di destinatari divisi da ';'
        List<String> recipients = new ArrayList<>();

        String loggedUserMail = ((User) security.getLoggedUser()).getMail();
        recipients.add(loggedUserMail);

        recipients.addAll(Arrays.asList(destinatario.split(";")));

        //recupero la lista dei codici f
        // iscali dai liberi
        List<String> fiscalCodes = new ArrayList<>();
        for (UiLibero selectedUser : selectedUsers) {
            fiscalCodes.add(selectedUser.getLavoratoreCodiceFiscale());
        }

       richSvc.requireInfo(new MessageInput(recipients, loggedUserMail ,htmlLiberi,provinceInfo),fiscalCodes,selectedUsers.get(0).getLiberoProvincia());


    }

    public List<UiLibero>  completeWithInfoOnRichiesteAiTerritori(List<UiLibero> list, int withOrWithoutInfo, String provinceName) {
        List<UiLibero> result = new ArrayList<>();
        for (UiLibero uiLibero : list) {
            Lavoratore lav = lavServ.findLavoratoreByFiscalCode(uiLibero.getLavoratoreCodiceFiscale());
            if (lav != null){
                boolean found = false;
                for (UiIscrizione uiIscrizione : uiLibero.getIscrizioni()) {
                    String province = uiIscrizione.getNomeProvincia();
                   // Province p = geoSvc.getProvinceByName(provinceName);

                    //devo verificare se è stata fatta una richiesta di info alla provincia
                    //di scrizione


                        List<RichiestaInfo> r = richSvc.findListForLavoratoreIdAndProvinceName(lav.getLid(), province);
                        if (r.size() == 0){
                            if (withOrWithoutInfo == 1){
                                //includo
                                if (provinceName.equals(province)) {
                                    found = true;
                                }
                            }else{
                                //escludo

                            }
                        }else{
                            if (withOrWithoutInfo == 1){
                                //escludo

                            }else{
                                //includo
                                found = true;
                            }
                        }
                        uiIscrizione.setNumComunicazioni(r.size());

                }
                if (found)
                    result.add(uiLibero);
            }else {
                //se il lavoratore è nullo e richiedo quelli senza richiesta lo aggiungo alla lista
                if (withOrWithoutInfo == 1)
                    result.add(uiLibero);
            }
        }
        return result;
    }


    public void downloadFileLiberi(String pathFile, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(pathFile);

        response.setHeader("Content-Disposition", "attachment;filename=stampaLiberi.xlsx");
        //response.setContentType("application/zip");
        response.setStatus(200);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.close();
    }

    public ListaLavoro createListalavoro(List<UiLibero> liberi, String description) throws Exception {
        List<LiberoDbNazionale> com = convertUiToLiberi(liberi);
        return lSrv.createListaFromLiberi(com, description);
    }

    private List<LiberoDbNazionale> convertUiToLiberi(List<UiLibero> liberi) {
        List<LiberoDbNazionale> a = new ArrayList<>();

        for (UiLibero uiLibero : liberi) {
            LiberoDbNazionale s = new LiberoDbNazionale();
            s.setCodiceFiscale(uiLibero.getLavoratoreCodiceFiscale());
            s.setNomeProvinciaFeneal(uiLibero.getLiberoProvincia());

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

    public List<UiLibero> retrieveNonIscrizioniForWorker(long id) throws ParseException {
        List<LiberoDbNazionale> lib = libService.retrieveLiberiForWorker(id);

        return convertLiberiToUiLiberi(lib);
    }

    public List<AppReportNonIscritti> reportNonIscrittiForApp(UiAppReportNonIscrittiInput params) throws ParseException {

        //converto ui input nell'input tipico del report dei non iscritti
        LiberoReportSearchParams newParams = convertParams(params);


        List<LiberoDbNazionale> lib = libService.retrieveLiberi(newParams);


        List<AppReportNonIscritti> rr = convertLiberiToAppNonIscritti(lib);
        if (rr.size() > 0){
            Collections.sort(rr, new Comparator<AppReportNonIscritti>() {
                @Override
                public int compare(final AppReportNonIscritti object1, final AppReportNonIscritti object2) {
                    return object1.getNome().compareTo(object2.getNome());
                }
            } );
        }

        if (rr.size() == 0){
            return rr;

        }


        return paginate(rr,params.getSkip(),params.getTake());



    }

    private List<AppReportNonIscritti> paginate(List<AppReportNonIscritti> list, int skip, int take){


        //calcolo il numero dell'indice del primo elemento che
        //è uguale a skip
        //calcolo l'ultimo elemento dell'array che è uguale a
        //skip + take

        //ma attenzione che se skip + take è maggiore della dimensione
        //della lista di origine allora devo impostare l'ultino elemento al valore della dimensione
        //della lista

        //ma se skip > list.size ritorno unal ista vuota

        if (skip > list.size())
            return new ArrayList<>();

        int max = skip + take;
        if (max > list.size())
            max = list.size();

        return list.subList(skip, max);

    }

    private List<AppReportNonIscritti> convertLiberiToAppNonIscritti(List<LiberoDbNazionale> lib) {



        List<AppReportNonIscritti> result = new ArrayList<>();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        for (LiberoDbNazionale uiIscritto : lib) {
            AppReportNonIscritti a = new AppReportNonIscritti();
            a.setLiberoAl(f.format(uiIscritto.getLiberoAl()));
            a.setAzienda(uiIscritto.getCurrentAzienda());
            a.setProvincia(uiIscritto.getNomeProvinciaFeneal());
            a.setNome(String.format("%s %s", uiIscritto.getCognome(), uiIscritto.getNome()));
            a.setEnte(uiIscritto.getEnte());
            a.setFiscale(uiIscritto.getCodiceFiscale());
            a.setResults(lib.size());
            String badge = "";
            if (uiIscritto.getIscrizioni().size() > 0)
                badge = String.valueOf(uiIscritto.getIscrizioni().size());

            if (uiIscritto.isDelegheOwner())
                badge = badge + "!";
            a.setBadge(badge);

            result.add(a);
        }

        return result;


    }

    private LiberoReportSearchParams convertParams(UiAppReportNonIscrittiInput params) {
        LiberoReportSearchParams p = new LiberoReportSearchParams();

        //è necessario avere ente e provincia con i relativi id



        p.setProvince(String.valueOf(geoSvc.getProvinceByName(params.getSelectedProvincia()).getIid()));
        p.setFirm(params.getSelectedAzienda());
        p.setParithetic(String.valueOf(enteRep.find(LoadRequest.build().filter("type", params.getSelectedEnte() , Filter.EQ)).findFirst().get().getLid()));
        p.setSignedTo(params.getIscrittoA());
        Country c = geoSvc.getCountryByName(params.getGeoNazioneSelected());
        if (c!= null)
            p.setNationality(String.valueOf(c.getIid()));
        else
            p.setNationality("");

        Province c1 = geoSvc.getProvinceByName(params.getGeoProvinceSelected());
        if (c1!= null)
            p.setLivingProvince(String.valueOf(c1.getIid()));
        else
            p.setLivingProvince("");

        City c2 = geoSvc.getCityByName(params.getGeoComuneSelected());
        if (c2!= null)
            p.setLivingCity(String.valueOf(c2.getIid()));
        else
            p.setLivingCity("");



        return p;
    }
}
