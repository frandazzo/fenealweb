package applica.feneal.domain.model.core.deleghe;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

/**
 * Created by fgran on 02/02/2017.
 */
public class ImportDelegheValidatorForBari extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
        if (row.get("COD. FISCALE") == null) {
            this.error = "Manca il campo cod. fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("COD. FISCALE").toString().trim())) {
                this.error = "Manca il campo cod. fiscale";
                this.valid = false;
            }
        }
    }

}