package applica.feneal.domain.model.core;

import applica.framework.AEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class VisibleFunction extends AEntity {

    public static final String sms = "SMS";
    public static final String prtiche = "PRATICHE";
    public static final String comunicazioni = "COMUNICAZIONI";

    private String description;
    private String type;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
