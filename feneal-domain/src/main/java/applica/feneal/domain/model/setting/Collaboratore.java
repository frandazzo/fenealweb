package applica.feneal.domain.model.setting;

import applica.feneal.domain.model.utils.SecuredDomainEntity;
import org.springframework.util.StringUtils;

/**
 * Created by fgran on 05/04/2016.
 */
public class Collaboratore  extends SecuredDomainEntity {

    private String description;

    @Override
    public String toString() {
        if (StringUtils.hasLength(description))
            return description;
        return String.valueOf(getId());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
