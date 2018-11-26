package applica.feneal.domain.model.utils;

import applica.framework.AEntity;
import applica.framework.annotations.ManyToOne;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.data.security.SecureEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
@IgnoreMapping
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class SecuredDomainEntity extends AEntity implements SecureEntity {


    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    @Override
    public String getSid() {
        return super.getSid();
    }

    @Override
    public long getLid() {
        return super.getLid();
    }

    @Override
    public int getIid() {
        return super.getIid();
    }

    @ManyToOne
    protected long companyId;







    @Override
    @JsonDeserialize(as=Long.class)
    public Object getOwnerId() {

        if (companyId == 0)
            return null;
        return companyId;
    }

    @Override
    public void setOwnerId(Object o) {
        companyId = (long)o;
    }
}
