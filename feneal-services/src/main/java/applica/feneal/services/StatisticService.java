package applica.feneal.services;

import applica.feneal.domain.model.core.aziende.NonIscrittiAzienda;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerEnte;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerSettore;
import applica.feneal.domain.model.core.report.RiepilogoIscrizione;
import applica.feneal.domain.model.core.report.Sindacalizzaizone;
import it.fenealgestweb.www.FenealgestStatsStub;

import java.io.IOException;
import java.util.List;

/**
 * Created by fgran on 01/05/2016.
 */
public interface StatisticService {

    Sindacalizzaizone getSindacalizzazione(Long loggedUserId, String territorio, String ente);
    List<RiepilogoIscrizione> getAndamentoIscrittiPerTerritorioAccorpato(Long loggedUserId);
    List<RiepilogoIscrizione> getContatoreIscrittiPerTerritorioAccorpato(Long loggedUserId, Integer anno);


    List<RiepilogoIscrizione> getAndamentoIscrittiPerSettoreEdile(Long loggedUserId, String territorio);
    List<RiepilogoIscrittiPerEnte> getContatoreIscrittiPerSettoreEdile(Long loggedUserId, Integer anno, String territorio);

    List<RiepilogoIscrizione> getAndamentoIscrittiPerProvincia(Long loggedUserId, String territorio);
    RiepilogoIscrittiPerSettore getContatoreIscritti(Long loggedUserId, Integer anno, String territorio);

    List<String> getListaTerritori(Long loggedUserId);

    List<String> getListaEnti();

    List<NonIscrittiAzienda> getClassificaNonIscritti(String province, String ente);

    List<String> statsGetDataExportIscritti(String provinceName) throws IOException;

    FenealgestStatsStub.DataExportResult statsGetStatisticsResult(String province, String filenames) throws IOException;
}
