package applica.feneal.services.impl.lavoratori;

import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.lavoratori.ListaLavoroRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.lavoratori.ListeLavoroComparison;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.NotificationService;
import applica.feneal.services.utils.ListaLavoroFromNotifications;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 07/06/2016.
 */
@Service
public class ListaLavoroServiceImpl implements ListaLavoroService {

    @Autowired
    private Security sec;

    @Autowired
    private ListaLavoroRepository liRep;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private NotificationService notSrv;

    @Autowired
    private DelegheRepository delRep;


    @Override
    public ListaLavoro getListaById(long listaId) {
        return liRep.get(listaId).orElse(null);
    }

    @Override
    public ListaLavoro createListaFromQuote(List<DettaglioQuotaAssociativa> quote, String description) throws Exception {


        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quote) {
            if (!workers.containsKey(dettaglioQuotaAssociativa.getIdLavoratore())){
                Lavoratore l = lavRep.get(dettaglioQuotaAssociativa.getIdLavoratore()).orElse(null);
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }


        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public void deleteLista(long listaId) {

        liRep.delete(listaId);
    }

    @Override
    public ListaLavoro createListaFromDeleghe(List<Delega> deleghe, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (Delega delega : deleghe) {
            if (!workers.containsKey(delega.getWorker().getLid())){
                Lavoratore l = delega.getWorker();
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }


        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public ListaLavoro createListaFromMagazzinoDeleghe(List<MagazzinoDelega> magazzino, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (MagazzinoDelega delega : magazzino) {
            if (!workers.containsKey(delega.getLavoratore().getLid())){
                Lavoratore l = delega.getLavoratore();
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }


        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public ListaLavoro createListaFromLiberi(List<LiberoDbNazionale> liberi, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (LiberoDbNazionale lib : liberi) {


            String liberoCF = lib.getCodiceFiscale();
            //ottengo il lavoratore associato
            Lavoratore l = lavSvc.getLavoratoreLiberoByFiscalCodeOrCreateItIfNotexist(liberoCF, lib.getNomeProvinciaFeneal());


            if (!workers.containsKey(l.getLid())){
                    workers.put(l.getLid(), l);
            }
        }


        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public ListaLavoro createListaFromArchivioDocumentale(List<Documento> documenti, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (Documento doc : documenti) {
            if (!workers.containsKey(doc.getLavoratore().getLid())){
                Lavoratore l = doc.getLavoratore();
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }


        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public ListaLavoro createListaFromRichieste(List<RichiestaInfo> richiestaInfo, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (RichiestaInfo doc : richiestaInfo) {
            if (!workers.containsKey(doc.getLavoratore().getLid())){
                Lavoratore l = doc.getLavoratore();
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }

        return createListFromWorkersHashMap(workers, description);
    }

    @Override
    public ListaLavoro createListaFromComunicazioni(List<Comunicazione> comunicazioni, String description) throws Exception {
        HashMap<Long, Lavoratore> workers = new HashMap<>();
        for (Comunicazione doc : comunicazioni) {
            if (!workers.containsKey(doc.getLavoratore().getLid())){
                Lavoratore l = doc.getLavoratore();
                if (l != null)
                    workers.put(l.getLid(), l);
            }
        }

        return createListFromWorkersHashMap(workers, description);
    }


    private ListaLavoro createListFromWorkersHashMap(HashMap<Long, Lavoratore> workers, String description) throws Exception {
        List<Lavoratore> lista = new ArrayList<>(workers.values());

        validateLista(lista, description);


        ListaLavoro l = new ListaLavoro();
        l.setDescription(description);
        l.setLavoratori(lista);

        liRep.save(l);

        return l;
    }

    private void validateLista(List<Lavoratore> workers, String description) throws Exception {
        if (StringUtils.isEmpty(description))
            throw new Exception("Nome lista non specificato");


        if (workers.size() == 0)
            throw new Exception("Nessuna lavoratore nella lista");

        //tento di recuperare una lista con lo stesso nome
        LoadRequest req = LoadRequest.build().filter("description", description);
        ListaLavoro lll = liRep.find(req).findFirst().orElse(null);
        if (lll != null)
            throw new Exception("Nome della lista gia esistente");

    }


    @Override
    public ListaLavoro mergeWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception {

        if (a.getLid() == b.getLid())
            throw new Exception("Le liste devono essere diverse");

        //devo creare una nuova lista di lavoro con l'unione di tutti i lavoratori di entrambe le liste
        HashMap<Long, Lavoratore> h1 = new HashMap<>();
        for (Lavoratore lavoratore : a.getLavoratori()) {
            h1.put(lavoratore.getLid(), lavoratore);
        }

        for (Lavoratore lavoratore : b.getLavoratori()) {
            if (!h1.containsKey(lavoratore.getLid()))
                h1.put(lavoratore.getLid(), lavoratore);
        }

        return createListFromWorkersHashMap(h1,description);

    }

    @Override
    public ListaLavoro excludeWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception {
        //devo escludere i lavoratori della lista b dalla lista a

        if (a.getLid() == b.getLid())
            throw new Exception("Le liste devono essere diverse");

        ListaLavoro l = a.excludeLavoratoriOfOtherList(b, description);
        validateLista(l.getLavoratori(), description);

        liRep.save(l);
        return l;
    }

    @Override
    public ListaLavoro intersectWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception {

        if (a.getLid() == b.getLid())
            throw new Exception("Le liste devono essere diverse");

        ListaLavoro l = a.intersects(b, description);
        validateLista(l.getLavoratori(), description);

        liRep.save(l);
        return l;
    }

    @Override
    public ListeLavoroComparison compareLists(ListaLavoro a, ListaLavoro b, String description) throws Exception {

        if (a.getLid() == b.getLid())
            throw new Exception("Le liste devono essere diverse");


        ListaLavoro aa = a.clone();
        ListaLavoro bb = b.clone();

        ListaLavoro aaa = a.clone();
        ListaLavoro bbb = b.clone();

        ListeLavoroComparison c = new ListeLavoroComparison();
        ListaLavoro aMinusB = aa.excludeLavoratoriOfOtherList(bb, description + "_(presenti_solo_in_" + aa.getDescription() + " _lista)");
        ListaLavoro bMinusA = b.excludeLavoratoriOfOtherList(a, description + "_(presenti_solo_in_" + bb.getDescription() + " _lista)");
        ListaLavoro intersection = aaa.intersects(bbb, description + "_presenti_in_entrambe");

     //   validateLista(aMinusB.getLavoratori(), description);
     //   validateLista(bMinusA.getLavoratori(), description);
     //   validateLista(intersection.getLavoratori(), description);

        c.setaMinusBList(aMinusB);
        c.setbMinusAListr(bMinusA);
        c.setIntersectionList(intersection);

        return c;



    }

    @Override
    public ListaLavoro createListaLavoroFromNotification(long notificationId, String type) throws Exception {


        Notification not =notSrv.getNotificationById(notificationId);
        if (not == null)
            return null;

        if (!not.getType().equals(Notification.notification_newexport))
            return null;


        String[] fiscalCodes = not.getBody().split(";");
        ListaLavoroFromNotifications lavoratori= retrieveWorkers(fiscalCodes, type);

        ListaLavoro l = new ListaLavoro();
        String tipo = "lavoratori_sconosciuti";
        if (type.equals("unknown")){
             l.setLavoratori(lavoratori.getUnknown());
        }else if (type.equals("known")){
            tipo="lavoratori_con_delega";
            l.setLavoratori(lavoratori.getKnown());
        }else if (type.equals("withoutdelega")){
            tipo = "lavoratori_senza_delega";
            l.setLavoratori(lavoratori.getWithoutdelega());
        }else
            return null;

        if (l.getLavoratori().size() == 0)
            return null;

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");

        //calcolo adesso una possibile descrizione
        String description = String.format("Lista_%s_%s", tipo, f.format(new Date()) );

        l.setDescription(description);

        liRep.save(l);

        return l;

    }

    @Override
    public void saveListaLavoro(ListaLavoro lista) {
        //devo validate la lista


        List<Lavoratore> lavs = lista.getLavoratori();

        Hashtable<Long, Lavoratore> h = new Hashtable<>();

        for (Lavoratore lav : lavs) {
            if (!h.containsKey(lav.getLid()))
                h.put(lav.getLid(), lav);
        }

        List<Lavoratore> oneTimeLavoratore = new ArrayList<>(h.values());
        //adesso ho la lista univoca
        lista.setLavoratori(oneTimeLavoratore);

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");

        //calcolo adesso una possibile descrizione
        String description = String.format("Lista_%s_%s", "importAnagrafiche", f.format(new Date()) );

        lista.setDescription(description);

        liRep.save(lista);




    }

    @Override
    public void deleteWorkerFromLista(long listaId, long lavId) {
        ListaLavoro listaLavoro  = getListaById(listaId);

        if (listaLavoro != null) {
            listaLavoro.getLavoratori().removeIf(l -> l.getLid() == lavId);
        }

        liRep.save(listaLavoro);
    }

    @Override
    public void addWorker(long listaId, long workerId) {
        ListaLavoro listaLavoro  = getListaById(listaId);

        if (listaLavoro != null) {
            Lavoratore lav = lavSvc.getLavoratoreById(((User) sec.getLoggedUser()).getLid(), workerId);
            if (lav != null) {
                listaLavoro.getLavoratori().add(lav);
                liRep.save(listaLavoro);
            }
        }
    }

    private ListaLavoroFromNotifications retrieveWorkers(String[] fiscalCodes, String type) throws Exception {
        ListaLavoroFromNotifications c = new ListaLavoroFromNotifications();


        for (String fiscalCode : fiscalCodes) {

            Lavoratore l = lavSvc.findLavoratoreByFiscalCode(fiscalCode);

            //se sto cercando di creare la lista per gli sconosciuti
            if (type.equals("unknown")){
                if (l == null){
                    //aggiingo alla lista solo se il lavoratore non esiste
                    l = lavSvc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode);
                    c.getUnknown().add(l);
                }
                //se esiste non lo contemplo



            }else if (type.equals("withoutdelega")){

                if (l!= null){
                    //conto le deleghe e se non ne ha lo aggiungo
                    List<Delega> deleghe = delRep.getDelegheByLavoratore(l.getLid());
                    if (deleghe.size() == 0)
                        c.getWithoutdelega().add(l);
                }

            }else{
                //cerco i conosciuti
                if (l!= null){
                    //conto le deleghe e se non ne ha lo aggiungo
                    List<Delega> deleghe = delRep.getDelegheByLavoratore(l.getLid());
                    if (deleghe.size() > 0)
                        c.getKnown().add(l);
                }
            }


        }
        return c ;

    }
}
