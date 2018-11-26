package applica.feneal.services.impl;

import applica.feneal.services.utils.CustomTemplatedMail;
import applica.framework.library.mail.MailException;
import applica.framework.library.mail.TemplatedMail;
import applica.framework.library.options.OptionsManager;

import javax.mail.MessagingException;

/**
 * Created by fgran on 03/06/2016.
 */
public class MailSimple implements Runnable{
    private String content;
    private String subject;
    private OptionsManager options;
    private String recipient;
    private String mailFrom;


    public MailSimple(String content, String mailFrom, String recipient,  OptionsManager options, String subject) {
        this.content = content;
        this.recipient = recipient;
        this.subject = subject;
        this.options = options;
        this.mailFrom = mailFrom;
    }

    @Override
    public void run() {

        try {
            CustomTemplatedMail tmail = new CustomTemplatedMail();

            tmail.setOptions(options);
            tmail.setMailFormat(TemplatedMail.HTML);
            tmail.setTemplatePath("templates/mail/simpleMail.vm");
            tmail.setFrom(mailFrom);
            tmail.setSubject(subject);
            tmail.setTo(recipient);
            tmail.put("content", content);

            tmail.send();
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


}

