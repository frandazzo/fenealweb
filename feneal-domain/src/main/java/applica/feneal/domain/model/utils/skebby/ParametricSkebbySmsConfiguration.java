package applica.feneal.domain.model.utils.skebby;

import java.util.HashMap;

public class ParametricSkebbySmsConfiguration {

    private String message_type;
    private String  message;
    private String  sender;
    private HashMap<String, SkebbyParamtericRecipient> recipients =new HashMap<String, SkebbyParamtericRecipient>();

    private String order_id;
    private boolean returnCredits;
    private boolean allowInvalidRecipients;
    private boolean returnRemaining;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public boolean isReturnCredits() {
        return returnCredits;
    }

    public void setReturnCredits(boolean returnCredits) {
        this.returnCredits = returnCredits;
    }

    public boolean isAllowInvalidRecipients() {
        return allowInvalidRecipients;
    }

    public void setAllowInvalidRecipients(boolean allowInvalidRecipients) {
        this.allowInvalidRecipients = allowInvalidRecipients;
    }

    public boolean isReturnRemaining() {
        return returnRemaining;
    }

    public void setReturnRemaining(boolean returnRemaining) {
        this.returnRemaining = returnRemaining;
    }

    public HashMap<String, SkebbyParamtericRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(HashMap<String, SkebbyParamtericRecipient> recipients) {
        this.recipients = recipients;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
