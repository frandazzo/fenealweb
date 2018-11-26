package applica.feneal.services;

import applica.feneal.services.messages.MessageInput;
import applica.feneal.services.messages.MessageResult;

/**
 * Created by applica on 10/28/15.
 */
public interface MessageService {


    MessageResult sendMessages(MessageInput input);

    MessageResult sendSimpleMail(MessageInput input);
}
