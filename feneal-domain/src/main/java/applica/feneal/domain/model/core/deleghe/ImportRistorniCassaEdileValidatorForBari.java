package applica.feneal.domain.model.core.deleghe;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;


/**
 * Created by fgramegna on 24/01/2020.
 */
public class ImportRistorniCassaEdileValidatorForBari extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
        if (row.get("FISCALE") == null) {
            this.error = "Manca il campo fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("FISCALE").toString().trim())) {
                this.error = "Manca il campo fiscale";
                this.valid = false;
            }
        }
    }
}
