package applica.feneal.services.impl;

import applica.feneal.services.ComunicazioniService;
import applica.feneal.services.MessageService;
import applica.feneal.services.messages.MessageInput;
import applica.feneal.services.messages.MessageResult;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.AuthenticationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by applica on 10/28/15.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private OptionsManager options;

    @Autowired
    private ComunicazioniService comServ;

    @Override
    public MessageResult sendMessages(MessageInput input) {


        /* EMAIL */


        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (String recipient : input.getRecipients()) {

            Runnable task = new MailRichiestaInfoTerritoriTask(input.getContent(), input.getMailFrom(), recipient, input.getProvinceInfo(), options);
            executor.execute(task);

        }

        executor.shutdown();

        return null;

    }



    @Override
    public MessageResult sendSimpleMail(MessageInput input) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (String recipient : input.getRecipients()) {

            Runnable task = new MailSimple(input.getContent(), input.getMailFrom(), recipient, options, input.getSubject());
            executor.execute(task);

        }

        executor.shutdown();

        return null;
    }

    @Override
    public void sendSms(String cell, String text) throws Exception {
        if (!existSmsCredentials())
            throw new Exception("Funzionalità disabilitata: registrazione non effettuata!");


        if (!isValid(cell))
            throw new Exception("Numero di telefono mancante");

        comServ.sendSmsViaSkebby(cell, text);

    }

    private boolean isValid(String cell){
        if (StringUtils.isEmpty(cell))
            return false;

        return true;
    }

    @Override
    public boolean existSmsCredentials(){
        return comServ.existSmsCredentials();

    }

    @Override
    public String getResidualCredit() throws IOException, AuthenticationException {
       return comServ.getResidualCredit();
    }


}
