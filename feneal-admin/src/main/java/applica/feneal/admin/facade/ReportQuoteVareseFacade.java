package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.services.AziendaService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.ReportQuoteVareseService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportQuoteVareseFacade {

    @Autowired
    private ReportQuoteVareseService rptQuoteserv;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private Security security;

    private List<UiDettaglioQuota> convertToUiDettaglioQuota(List<DettaglioQuotaAssociativa> quoteDetails) {

        List<UiDettaglioQuota> result = new ArrayList<>();

        for (DettaglioQuotaAssociativa dettaglio : quoteDetails) {
            UiDettaglioQuota q = new UiDettaglioQuota();

            q.setId(dettaglio.getLid());
            q.setIdQuota(dettaglio.getIdRiepilogoQuotaAssociativa());
            q.setEnte(dettaglio.getEnte());
            q.setQuota(dettaglio.getQuota());
            q.setLivello(dettaglio.getLivello());
            q.setContratto(dettaglio.getContratto());
            q.setProvincia(dettaglio.getProvincia());
            q.setDataRegistrazione(dettaglio.getDataRegistrazione());
            q.setDataInizio(dettaglio.getDataInizio());
            q.setDataFine(dettaglio.getDataFine());
            q.setDataDocumento(dettaglio.getDataDocumento());
            q.setTipoDocumento(dettaglio.getTipoDocumento());
            q.setSettore(dettaglio.getSettore());
            Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());
            if (lav != null) {
                q.setLavoratoreNomeCompleto(String.format("%s %s", lav.getSurname(), lav.getName()));
                q.setLavoratoreCodiceFiscale(lav.getFiscalcode());
                q.setLavoratoreId(lav.getLid());
            }

            Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdAzienda());
            if (az != null) {
                q.setAziendaRagioneSociale(az.getDescription());
                q.setAziendaId(az.getLid());
            }

            result.add(q);
        }

        return result;
    }

    public List<UiDettaglioQuota> reportQuote(UiQuoteVareseReportSearchParams params) {
        List<DettaglioQuotaAssociativa> rpt = rptQuoteserv.retrieveQuoteVarese(params);

        return convertToUiDettaglioQuota(rpt);
    }
}
