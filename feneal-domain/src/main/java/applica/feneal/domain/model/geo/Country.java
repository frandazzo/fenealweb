package applica.feneal.domain.model.geo;

import applica.framework.AEntity;
import applica.framework.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class Country extends IEntity {
    private String description;
    private Integer idRace;
    private String fiscalCode;
    private String issCode;

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    private String continent;

    public String getIssCode() {
        return issCode;
    }

    public void setIssCode(String issCode) {
        this.issCode = issCode;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public Integer getIdRace() {
        return idRace;
    }

    public void setIdRace(Integer idRace) {
        this.idRace = idRace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return description;
    }
}
