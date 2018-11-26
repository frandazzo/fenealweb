package applica.feneal.services.impl.setup;

import applica.framework.library.utils.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionMap {

    public static final List<String> staticPermissions(String crudEntity){


        String plural = Strings.pluralize(crudEntity);


        return new ArrayList<String>(Arrays.asList( plural + ":new", plural + ":list", plural + ":save", plural + ":edit", plural + ":delete"));


    }



}
