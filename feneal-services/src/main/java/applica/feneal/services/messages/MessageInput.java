package applica.feneal.services.messages;


import java.util.List;

/**
 * Created by applica on 10/28/15.
 */
public class MessageInput {

    private List<String> recipients;
    private String mailFrom;
    private String content;
    private String provinceInfo;
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MessageInput(List<String> recipients, String mailFrom, String content, String provinceInfo) {
        this.recipients = recipients;
        this.mailFrom = mailFrom;
        this.content = content;
        this.provinceInfo = provinceInfo;
    }

    public MessageInput(){

    }


    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvinceInfo() {
        return provinceInfo;
    }

    public void setProvinceInfo(String provinceInfo) {
        this.provinceInfo = provinceInfo;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
}
