package applica.feneal.services;

import applica.feneal.services.messages.MessageInput;
import applica.feneal.services.messages.MessageResult;
import applica.framework.security.AuthenticationException;

import java.io.IOException;

/**
 * Created by applica on 10/28/15.
 */
public interface MessageService {


    MessageResult sendMessages(MessageInput input);

    MessageResult sendSimpleMail(MessageInput input);

    void sendSms(String cell,  String text) throws Exception;
    String getResidualCredit() throws IOException, AuthenticationException;
    boolean existSmsCredentials();

}
