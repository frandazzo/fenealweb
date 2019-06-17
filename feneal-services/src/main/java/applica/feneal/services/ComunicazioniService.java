package applica.feneal.services;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.varese.UiDettaglioQuotaVarese;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.framework.security.AuthenticationException;

import java.io.IOException;
import java.util.List;

/**
 * Created by fgran on 02/06/2016.
 */
public interface ComunicazioniService {
    void sendSms(String telNumber, long workerId, String causaleId, String text, String province) throws Exception;

    String getResidualCredit() throws IOException, AuthenticationException;

    boolean existSmsCredentials();

    void sendSmsViaSkebby(String telNumber, String text) throws Exception;
    void sendSmsToMultipleWorkers(List<Lavoratore> lavoratori, String text, String province, String descrizioneCampagna) throws Exception;

    void sendParametricSms(List<UiDettaglioQuotaVarese> quote) throws Exception;

    List<Comunicazione> getAllWorkerComunicazioni(long workerId);

}
