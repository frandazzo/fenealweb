package applica.feneal.services;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDeleghe;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.servizi.search.UiQuoteImpiantiFissiSearchParams;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.option.ApplicationOptions;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 05/04/2016.
 */
public interface DelegheService {

    void writeToFile(File myFile, String content);

    String importaDelegheBariCassaEdile(ImportDeleghe importData) throws Exception;

    void accettaDeleghe(List<Delega> deleghe, Date date);
    void inoltraDeleghe(List<Delega> deleghe, Date date);

    void subscribeDelega(Delega del);

    void sendDelega(Date date, Delega del);

    void acceptDelega(Date date, Delega del);

    void cancelDelega(Date date, Delega del, CausaleRevoca reason);

    void revokeDelega(Date date, Delega del, CausaleRevoca reason);

    void activateDelega(Date date, Delega del);

    void goBack(Delega del);

    List<Delega> getAllWorkerDeleghe(long workerId);

    List<Delega> getWorkerDelegheEdiliByDataAndEnte(long workerId, Date date, String ente);

    boolean hasDelegaEdile(long workerId);

    void deleteDelega(long userId, long delegaId);

    void saveOrUpdate(long userId, Delega l) throws Exception;

    Delega getDelegaById(Long id);

    List<Integer> getDelegaPermittedNextStates(Delega delega, List<ApplicationOptions> opt);

    List<Delega> getDelegheImpiantiFissi(UiQuoteImpiantiFissiSearchParams params);

    boolean hasWorkerDelegaAttivaOAccettata(long workerId, String sector, String ente, String azienda, String provincia);

    Delega retrieveActivableWorkerDelega(long idLavoratore, int provinceId, String settore, String ente, long idAzienda);

    List<Lavoratore> findLavoratoriConDelegaEdilePerAzienda(long id);




}
