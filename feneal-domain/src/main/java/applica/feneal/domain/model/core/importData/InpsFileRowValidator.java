package applica.feneal.domain.model.core.importData;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

public class InpsFileRowValidator  extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {

        valid = true;
        error = "";


        if (row.get("Codice Fiscale") == null) {
            this.error = "Manca il campo cod. fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("Codice Fiscale").toString().trim())) {
                this.error = "Manca il campo cod. fiscale";
                this.valid = false;
            }
        }




    }

}