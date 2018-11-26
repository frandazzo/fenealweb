package applica.feneal.domain.model.core;

import applica.framework.AEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class Sector extends AEntity {

    public static final String sector_edile = "EDILE";
    public static final String sector_IMPIANTIFISSI = "IMPIANTI FISSI";
    public static final String sector_inps = "INPS";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    private String description;
    //assume il valore della costante sopra
    private String type;

    @Override
    public String toString() {
        try {
            return String.format("%s", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
