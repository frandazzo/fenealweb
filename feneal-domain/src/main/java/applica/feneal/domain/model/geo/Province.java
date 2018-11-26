package applica.feneal.domain.model.geo;

import applica.framework.AEntity;
import applica.framework.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class Province extends IEntity {


    private String description;
    private String sigla;
    private int idRegion;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
