package applica.feneal.services;

import applica.feneal.domain.model.core.lavoratori.IscrittoAnnoInCorso;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.search.LavoratoreSearchParams;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.TelefonoCodiceFiscaleDto;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;

import java.text.ParseException;
import java.util.List;

/**
 * Created by fgran on 06/04/2016.
 */
public interface LavoratoreService {

    Lavoratore getLavoratoreById(long loggedUserId, long lavId);

    void saveOrUpdate(long loggedUserId, Lavoratore lav) throws Exception;

    void delete(long loggedUserId, long idLav) throws Exception;

    List<Lavoratore> findLocalLavoratori(long loggedUserId, LavoratoreSearchParams params);
    List<Lavoratore> findLavoratoriMultiterritorio(long loggedUserId, LavoratoreSearchParams params);
    List<UtenteDbNazionale> findRemoteLavoratori(long loggedUserId, LavoratoreSearchParams params) throws ParseException;


    List<Lavoratore> findCurrentIscrizioniForAzienda(long firmId);
    List<LiberoDbNazionale> findNonIscrizioniForAzienda(long firmId);

    List<String> getNumeriTelefono(String fiscalCode);



    Lavoratore findLavoratoreForProvince(String provinceName, String fiscalcode);
    Lavoratore findLavoratoreByFiscalCode(String fiscalcode);

    UtenteDbNazionale findRemoteLavoratoreByFiscalCode(String fiscalCode);
    LiberoDbNazionale findRemoteLavoratoreLiberoByFiscalCodeAndProvince(String fiscalCode, String province);

    Lavoratore getLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode) throws Exception;
    Lavoratore getLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode, String province) throws Exception;
    Lavoratore getLavoratoreLiberoByFiscalCodeOrCreateItIfNotexist(String fiscalCode, String province) throws Exception;
    Lavoratore getLavoratoreByRemoteIdOrCreateItIfNotexist(int workerId) throws Exception;

    Lavoratore getLavoratoreOfOtherCompanyAndCreateItIfNotExistForCurrentCompany(String id) throws Exception;
    IscrittoAnnoInCorso checkIfIscrittoAnnoInCorso(long workerId) throws Exception;

    Lavoratore getLavoratoreMultiterritorioById(long loggedUserId, long id);

    void updateCellsForAll();
}
