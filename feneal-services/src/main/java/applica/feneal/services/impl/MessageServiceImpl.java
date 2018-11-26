package applica.feneal.services.impl;

import applica.feneal.services.MessageService;
import applica.feneal.services.messages.MessageInput;
import applica.feneal.services.messages.MessageResult;
import applica.framework.library.options.OptionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by applica on 10/28/15.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private OptionsManager options;

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

}
