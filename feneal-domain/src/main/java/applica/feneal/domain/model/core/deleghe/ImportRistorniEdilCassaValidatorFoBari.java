package applica.feneal.domain.model.core.deleghe;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

public class ImportRistorniEdilCassaValidatorFoBari extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
        if (row.get("Cod.Fisc.") == null) {
            this.error = "Manca il campo fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("Cod.Fisc.").toString().trim())) {
                this.error = "Manca il campo fiscale";
                this.valid = false;
            }
        }
    }
}
