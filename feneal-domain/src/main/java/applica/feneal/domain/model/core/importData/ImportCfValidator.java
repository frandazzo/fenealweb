package applica.feneal.domain.model.core.importData;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

public class ImportCfValidator extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
        if (row.get("FISCALE") == null) {
            this.error = "Manca il campo FISCALE";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("FISCALE").toString().trim())) {
                this.error = "Manca il campo FISCALE";
                this.valid = false;
            }
        }



    }


}
