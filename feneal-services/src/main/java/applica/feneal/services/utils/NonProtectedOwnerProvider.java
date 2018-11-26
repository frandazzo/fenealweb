package applica.feneal.services.utils;

import applica.framework.data.security.OwnerProvider;

/**
 * Created by fgran on 14/11/2016.
 */
public class NonProtectedOwnerProvider implements OwnerProvider {


    private long companyId;

    public NonProtectedOwnerProvider(long companyId) {

        this.companyId = companyId;
    }

    public Object provide() {


        return companyId;

    }


}

