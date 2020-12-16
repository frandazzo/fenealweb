package applica.feneal.services;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.lavoratori.ListeLavoroComparison;
import applica.feneal.domain.model.core.lavoratori.search.ListaLavoroSearchParams;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;

import java.util.List;

/**
 * Created by fgran on 07/06/2016.
 */
public interface ListaLavoroService {

    ListaLavoro getListaById(long listaId);
    void deleteLista(long listaId);

    ListaLavoro createListaFromQuote(List<DettaglioQuotaAssociativa> quote, String description) throws Exception;
    ListaLavoro createListaFromDeleghe(List<Delega> deleghe, String description) throws Exception;
    ListaLavoro createListaFromMagazzinoDeleghe(List<MagazzinoDelega> magazzino, String description) throws Exception;
    ListaLavoro createListaFromLiberi(List<LiberoDbNazionale> liberi, String description) throws Exception;
    ListaLavoro createListaFromArchivioDocumentale(List<Documento> documenti, String description) throws Exception;
    ListaLavoro createListaFromRichieste(List<RichiestaInfo> richiestaInfo, String description) throws Exception;
    ListaLavoro createListaFromComunicazioni(List<Comunicazione> comunicazioni, String description) throws Exception;


    ListaLavoro mergeWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception;
    ListaLavoro excludeWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception;
    ListaLavoro intersectWithOtherList(ListaLavoro a, ListaLavoro b, String description) throws Exception;

    ListeLavoroComparison compareLists (ListaLavoro a, ListaLavoro b, String description) throws Exception;

    ListaLavoro createListaLavoroFromNotification(long notificationId, String type) throws Exception;

    void saveListaLavoro(ListaLavoro lista);

    void deleteWorkerFromLista(long listaId, long lavId);

    void addWorker(long listaId, long workerId);

    ListaLavoro createListaFromIncrocio(List<LavoratoreIncrocio> incrocio, String description) throws Exception;
}
