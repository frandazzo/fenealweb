package applica.feneal.services.utils;

import applica.framework.library.mail.MailException;
import applica.framework.library.mail.MailUtils;
import applica.framework.library.mail.Recipient;
import applica.framework.library.options.OptionsManager;
import applica.framework.library.velocity.VelocityBuilderProvider;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
public class CustomTemplatedMail {
    private Log logger = LogFactory.getLog(this.getClass());
    public static final int HTML = 1;
    public static final int TEXT = 2;
    private VelocityContext context = new VelocityContext();
    private String templatePath;
    private String from;
    private String to;
    private String returnReceipt;
    private List<Recipient> recipients;
    private String subject;
    private List<String> attachments;
    private OptionsManager options;
    private int mailFormat;

    public CustomTemplatedMail() {
    }

    public String getReturnReceipt() {
        return this.returnReceipt;
    }

    public void setReturnReceipt(String returnReceipt) {
        this.returnReceipt = returnReceipt;
    }

    public String getTemplatePath() {
        return this.templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public OptionsManager getOptions() {
        return this.options;
    }

    public void setOptions(OptionsManager options) {
        this.options = options;
    }

    public void put(String key, Object value) {
        this.context.put(key, value);
    }

    public List<Recipient> getRecipients() {
        return this.recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public int getMailFormat() {
        return this.mailFormat;
    }

    public void setMailFormat(int mailFormat) {
        this.mailFormat = mailFormat;
    }

    public List<String> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public void send() throws MailException, AddressException, MessagingException {
        if(this.options == null) {
            throw new MailException("options not setted");
        } else if(!StringUtils.hasLength(this.from)) {
            throw new MailException("from not setted");
        } else if(!StringUtils.hasLength(this.to) && this.recipients.isEmpty()) {
            throw new MailException("to or recipients not setted");
        } else {
            if(this.mailFormat == 0) {
                this.mailFormat = 2;
            }

            Session session = CustomMailUtils.getMailSession(this.options);
            MimeMessage message = new MimeMessage(session);
            message.addFrom(new InternetAddress[]{new InternetAddress(this.from)});
            if(StringUtils.hasLength(this.to)) {
                message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(this.to));
            } else if(!this.recipients.isEmpty()) {
                Iterator template = this.recipients.iterator();

                while(template.hasNext()) {
                    Recipient bodyWriter = (Recipient)template.next();
                    switch(bodyWriter.getRecipientType()) {
                        case 0:
                            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(bodyWriter.getRecipient()));
                            break;
                        case 1:
                            message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(bodyWriter.getRecipient()));
                            break;
                        case 2:
                            message.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bodyWriter.getRecipient()));
                    }
                }
            }

            message.setSubject(this.subject);
            this.logger.info(String.format("Sending email \'%s\' with template \'%s\' to \'%s\'", new Object[]{this.subject, this.templatePath, this.to}));
            Template template1 = VelocityBuilderProvider.provide().engine().getTemplate(this.templatePath, "UTF-8");
            StringWriter bodyWriter1 = new StringWriter();
            template1.merge(this.context, bodyWriter1);
            if(this.attachments != null && !this.attachments.isEmpty()) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                if(this.mailFormat == 2) {
                    messageBodyPart.setText(bodyWriter1.toString());
                } else if(this.mailFormat == 1) {
                    messageBodyPart.setContent(bodyWriter1.toString(), "text/html");
                }

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                Iterator var7 = this.attachments.iterator();

                while(var7.hasNext()) {
                    String attachment = (String)var7.next();
                    addAttachment(multipart, attachment);
                }

                message.setContent(multipart);
            } else if(this.mailFormat == 2) {
                message.setContent(bodyWriter1.toString(), "text/plain");
                message.setText(bodyWriter1.toString(), "UTF-8");
            } else if(this.mailFormat == 1) {
                message.setContent(bodyWriter1.toString(), "text/html");
            }

            if(StringUtils.hasLength(this.returnReceipt)) {
                message.setHeader("Return-Receipt-To:", String.format("<%s>", new Object[]{this.returnReceipt}));
            }


            if (StringUtils.hasLength(options.get("smtp.username")))
            {
                Transport t = session.getTransport("smtp");
                try{
                    //se ci sono le credenziali opero in questa maniera...
                    t.connect(options.get("smtp.username"), options.get("smtp.password"));
                    t.sendMessage(message,message.getAllRecipients() );
                }
                catch(Exception ex){
                    try {
                        throw  new Exception(ex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }finally{
                    t.close();
                }


            }else{
                Transport.send(message);
            }

            System.out.println("Sent message successfully....");
        }
    }

    private static void addAttachment(Multipart multipart, String attachment) throws MessagingException {
        FileDataSource source = new FileDataSource(attachment);
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(FilenameUtils.getName(attachment));
        multipart.addBodyPart(messageBodyPart);
    }
}
