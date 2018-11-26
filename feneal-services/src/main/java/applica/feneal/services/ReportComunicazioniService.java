package applica.feneal.services;

import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.search.UiComunicazioneReportSearchParams;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface ReportComunicazioniService {

    List<Comunicazione> retrieveComunicazioni(UiComunicazioneReportSearchParams params);

}
