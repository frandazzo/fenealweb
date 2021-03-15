package applica.feneal.domain.model.core.RSU;

import applica.framework.AEntity;
/**
 * Created by felicegramegna on 08/03/2021.
 */
public class ContrattoRSU extends AEntity {
    private String description;
    private int rsuMax;
    private int rsuMin;

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRsuMax() {
        return rsuMax;
    }

    public void setRsuMax(int rsuMax) {
        this.rsuMax = rsuMax;
    }

    public int getRsuMin() {
        return rsuMin;
    }

    public void setRsuMin(int rsuMin) {
        this.rsuMin = rsuMin;
    }
}
