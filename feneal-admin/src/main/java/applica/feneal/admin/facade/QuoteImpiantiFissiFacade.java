package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.quote.QuoteDbNazionaleExporter;
import applica.feneal.admin.viewmodel.quote.UiCreaQuoteImpiantiFissi;
import applica.feneal.admin.viewmodel.quote.UiLavoratoriQuoteImpiantiFissi;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.servizi.search.UiQuoteImpiantiFissiSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import applica.feneal.services.QuoteAssociativeService;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 26/05/2016.
 */
@Component
public class QuoteImpiantiFissiFacade {

    @Autowired
    private DelegheService delService;

    @Autowired
    private Security sec;

    @Autowired
    private GeoService geo;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private QuoteDbNazionaleExporter exporter;

    @Autowired
    private QuoteAssociativeService quoSvc;

    @Autowired
    private OptionsManager optMan;


    public List<UiLavoratoriQuoteImpiantiFissi> retrieveLavoratoriImpiantiFissi(UiQuoteImpiantiFissiSearchParams params){

        List<Delega> deleghe = delService.getDelegheImpiantiFissi(params);

        return convertDelegheToUiLavoratoriQuote(params, deleghe);
    }

    private List<UiLavoratoriQuoteImpiantiFissi> convertDelegheToUiLavoratoriQuote(UiQuoteImpiantiFissiSearchParams params, List<Delega> delegheImpiantiFissi) {

        int numResults = delegheImpiantiFissi.size();
        List<UiLavoratoriQuoteImpiantiFissi> result = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);

        for (Delega del : delegheImpiantiFissi) {
            UiLavoratoriQuoteImpiantiFissi q = new UiLavoratoriQuoteImpiantiFissi();

            q.setLavoratoreId(del.getWorker().getLid());
            q.setLavoratoreNomeCompleto(String.format("%s %s", del.getWorker().getName(), del.getWorker().getSurname()));



            if (del.getWorkerCompany()!= null)
                q.setAzienda(del.getWorkerCompany().getDescription());

            q.setContratto(params.getContract());

            try {
                String importoStr = df.format(params.getAmount() / numResults).replace(",", ".");
                q.setImporto(Double.parseDouble(importoStr));
            } catch (Exception e) {
                q.setImporto(0);
            }

            result.add(q);
        }

        return result;
    }



    public String exportToDBNazionale(UiCreaQuoteImpiantiFissi data) throws Exception {

        StringBuilder currentUsername = new StringBuilder();
        StringBuilder currentPassword = new StringBuilder();
        Province province = geo.getProvinceById(Integer.parseInt(data.getParams().getProvince()));
        exporter.calculateCredentials(currentUsername,currentPassword, province);



        data.getParams().setCompleteProvince(province);
        data.getParams().setCompleteFirm(azRep.get(data.getParams().getFirm()).orElse(null));
        for (UiLavoratoriQuoteImpiantiFissi uiLavoratoriQuoteImpiantiFissi : data.getQuoteRows()) {
            uiLavoratoriQuoteImpiantiFissi.setAzienda(data.getParams().getCompleteFirm().getDescription());
        }
        //qui eseguo la chiamata ai servizi web per evitare la riconversione in ogetti di dominio
        return exporter.exportImpiantiFissi(data, currentUsername.toString(), currentPassword.toString(), data.getParams().getProvince());

    }


    public String exportToDBNazionaleForAll(UiQuoteImpiantiFissiSearchParams params) throws Exception {

        UiCreaQuoteImpiantiFissi data = retireveDataForAllImpiantiFissi(params);



        StringBuilder currentUsername = new StringBuilder();
        StringBuilder currentPassword = new StringBuilder();
        Province province = geo.getProvinceById(Integer.parseInt(data.getParams().getProvince()));
        exporter.calculateCredentials(currentUsername, currentPassword, province);



        data.getParams().setCompleteProvince(province);

        //qui eseguo la chiamata ai servizi web per evitare la riconversione in ogetti di dominio
        return exporter.exportImpiantiFissi(data, currentUsername.toString(), currentPassword.toString(), data.getParams().getProvince());

    }

    private UiCreaQuoteImpiantiFissi retireveDataForAllImpiantiFissi(UiQuoteImpiantiFissiSearchParams data) {

        List<UiLavoratoriQuoteImpiantiFissi> lavoratori=  retrieveLavoratoriImpiantiFissi(data);
        UiCreaQuoteImpiantiFissi result = new UiCreaQuoteImpiantiFissi();
        result.setParams(data);
        result.setQuoteRows(lavoratori);

        return result;
    }



}
