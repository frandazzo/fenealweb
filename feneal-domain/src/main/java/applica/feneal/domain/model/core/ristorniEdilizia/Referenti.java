package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.AEntity;

public class Referenti extends AEntity {

    private String completeName;
    private String city;
    private int proRataShare;

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getProRataShare() {
        return proRataShare;
    }

    public void setProRataShare(int proRataShare) {
        this.proRataShare = proRataShare;
    }
}
