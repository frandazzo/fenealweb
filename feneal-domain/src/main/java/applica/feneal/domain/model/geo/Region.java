package applica.feneal.domain.model.geo;

import applica.framework.AEntity;
import applica.framework.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class Region  extends IEntity {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @Override
    public String toString() {
        return description;
    }
}
