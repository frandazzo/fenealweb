package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.services.AziendaService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.QuoteAssociativeService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class VersamentiFacade {

    @Autowired
    private QuoteAssociativeService quoteService;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private Security security;


    public List<UiDettaglioQuota> getStoricoVersamenti(Long workerId){
        List<DettaglioQuotaAssociativa> versamenti = quoteService.getStoricoVersamenti(workerId);

        return convertVersamentiToUiVersamenti(versamenti);
    }


    private List<UiDettaglioQuota> convertVersamentiToUiVersamenti(List<DettaglioQuotaAssociativa> quote) {
        List<UiDettaglioQuota> result = new ArrayList<>();

        for (DettaglioQuotaAssociativa dettaglio : quote) {
            UiDettaglioQuota q = new UiDettaglioQuota();

            q.setIdQuota(dettaglio.getIdRiepilogoQuotaAssociativa());
            q.setEnte(dettaglio.getEnte());
            q.setQuota(dettaglio.getQuota());
            q.setLivello(dettaglio.getLivello());
            q.setContratto(dettaglio.getContratto());
            q.setProvincia(dettaglio.getProvincia());
            q.setDataRegistrazione(dettaglio.getDataRegistrazione());
            q.setDataInizio(dettaglio.getDataInizio());
            q.setDataFine(dettaglio.getDataFine());
            q.setTipoDocumento(dettaglio.getTipoDocumento());
            q.setDataDocumento(dettaglio.getDataDocumento());
            q.setSettore(dettaglio.getSettore());
            Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());
            if (lav != null) {
                q.setLavoratoreNomeCompleto(String.format("%s %s", lav.getSurname(), lav.getName()));
                q.setLavoratoreCodiceFiscale(lav.getFiscalcode());
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
    
}
