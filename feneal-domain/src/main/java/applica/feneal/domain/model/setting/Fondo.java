package applica.feneal.domain.model.setting;

import applica.feneal.domain.model.utils.SecuredDomainEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class Fondo extends SecuredDomainEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

