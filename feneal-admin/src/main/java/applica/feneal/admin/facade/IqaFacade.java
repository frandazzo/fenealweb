package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.search.UiIqaReportSearchParams;
import applica.feneal.services.AziendaService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.QuoteAssociativeService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by fgran on 15/04/2016.
 */
@Component
public class IqaFacade {

    @Autowired
    private QuoteAssociativeService quoteService;


    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private Security security;


    public List<UiDettaglioQuota> reportQuote(UiIqaReportSearchParams params){
        List<DettaglioQuotaAssociativa> quote = quoteService.retrieveQuote(params);

        return convertQuoteToUiQuote(quote);
    }


    private void addToHashmap(Hashtable<String, Double> map, String fiscalCode, Double amount){
        if (map.containsKey(fiscalCode)){
            Double val = map.get(fiscalCode);
            val = val + amount;
            map.replace(fiscalCode,val);
            return;
        }

        map.put(fiscalCode, amount);
    }

    private List<UiDettaglioQuota> convertQuoteToUiQuote(List<DettaglioQuotaAssociativa> quote) {
        List<UiDettaglioQuota> result = new ArrayList<>();
        Hashtable<String, Double> totQuote = new Hashtable<>();
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
            addToHashmap(totQuote, q.getLavoratoreCodiceFiscale(), q.getQuota());
        }


        //prinma di restituire il risultato
        //per ogni quota associo il valro totale delle quote
        for (UiDettaglioQuota uiDettaglioQuota : result) {
            uiDettaglioQuota.setLavoratoreTotaleQuote(totQuote.get(uiDettaglioQuota.getLavoratoreCodiceFiscale()));
        }
        return result;
    }


    public ListaLavoro createListalavoro(List<UiDettaglioQuota> iqa, String description) throws Exception {
        List<DettaglioQuotaAssociativa> com = convertUiToDettaglioQuote(iqa);
        return lSrv.createListaFromQuote(com,description);
    }

    private List<DettaglioQuotaAssociativa> convertUiToDettaglioQuote(List<UiDettaglioQuota> iqa) {
        List<DettaglioQuotaAssociativa> a = new ArrayList<>();

        for (UiDettaglioQuota uiDettaglio : iqa) {
            DettaglioQuotaAssociativa s = new DettaglioQuotaAssociativa();
            s.setIdLavoratore(uiDettaglio.getLavoratoreId());

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }
    
}
