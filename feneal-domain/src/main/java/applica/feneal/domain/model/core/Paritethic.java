package applica.feneal.domain.model.core;

import applica.framework.AEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class Paritethic extends AEntity {



    public static final String ente_cassaedile = "CASSA EDILE";
    public static final String ente_edilcassa = "EDILCASSA";

    public static final String ente_calec = "CALEC";
    public static final String ente_cea = "CEA";
    public static final String ente_cec = "CEC";
    public static final String ente_ceda = "CEDA";

    public static final String ente_cedaf = "CEDAF";
    public static final String ente_cedam = "CEDAM";

    public static final String ente_celcof = "CELCOF";
    public static final String ente_cema = "CEMA";

    public static final String ente_cert = "CERT";
    public static final String ente_ceva = "CEVA";

    public static final String ente_cedaiier = "CEDAIIER";
    public static final String ente_falea = "FALEA";

    @Override
    public String toString() {
        return type;
    }

    public Paritethic(){}

    public Paritethic(String description, String type){
        this.type = type;
        this.description = description;
    }

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
