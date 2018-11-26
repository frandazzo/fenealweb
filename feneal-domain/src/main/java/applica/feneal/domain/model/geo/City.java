package applica.feneal.domain.model.geo;

import applica.framework.AEntity;
import applica.framework.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class City extends IEntity {


    private String description;
    private int idProvince;
    private int idRegion;
    private String cap;
    private String  fiscalcode;
    private String istatcode;


    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getIstatcode() {
        return istatcode;
    }

    public void setIstatcode(String istatcode) {
        this.istatcode = istatcode;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }


}
