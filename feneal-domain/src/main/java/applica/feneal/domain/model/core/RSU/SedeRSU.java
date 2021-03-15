package applica.feneal.domain.model.core.RSU;

import applica.framework.AEntity;

/**
 * Created by felicegramegna on 24/02/2021.
 */

public class SedeRSU extends AEntity {
    private String description;
    private String city;
    private String province;
    private AziendaRSU aziendaRSU;
    private String address;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public AziendaRSU getAziendaRSU() {
        return aziendaRSU;
    }

    public void setAziendaRSU(AziendaRSU aziendaRSU) {
        this.aziendaRSU = aziendaRSU;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
