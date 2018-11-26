package applica.feneal.services;

import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.UpdatableDettaglioQuota;
import applica.feneal.domain.model.core.quote.fenealgestImport.DettaglioQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.core.servizi.search.UiIqaReportSearchParams;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.framework.security.AuthenticationException;

import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 20/05/2016.
 */
public interface QuoteAssociativeService {

    String importQuote(Importazione impotazioneDbNazionale) throws Exception;


    String importQuoteAssociativeFromDatiTerritori(int companyId, RiepilogoQuotaDTO dto) throws AuthenticationException;

    RiepilogoQuoteAssociative creaQuoteManuali(RiepilogoQuotaDTO dto) throws Exception;


    void deleteQuota(long idRiepilogoQuota);


    Importazione getImportazioneById(int id);


    List<DettaglioQuotaAssociativa> retrieveQuote(UiIqaReportSearchParams params);


    List<DettaglioQuotaAssociativa> getDettagliQuota(long idRiepilogoQuota, Long idWorker);


    List<DettaglioQuotaAssociativa> getStoricoVersamenti(long workerId);

    RiepilogoQuoteAssociative getRiepilogoQuotaById(long id);

    RiepilogoQuoteAssociative getRiepilogoQuotaByGuid(String id);

    String importLiberiFromDatiTerritori(int companyId, RiepilogoQuotaDTO dto) throws AuthenticationException;

    DettaglioQuotaAssociativa addItem(RiepilogoQuoteAssociative riepilogoQuoteAssociative, DettaglioQuotaDTO dettaglioQuotaDTO) throws Exception;

    void duplicaQuota(long quotaId, Date inizio, Date fine);

    void deleteItem(long quotaId, long itemId);

    void updateItem(long quotaId, long itemId, UpdatableDettaglioQuota updatedData);

    void modifyCompetenceQuotaItems(long quotaId, Date inizio, Date fine);
}
