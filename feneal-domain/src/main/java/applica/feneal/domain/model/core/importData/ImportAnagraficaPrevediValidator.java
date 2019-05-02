package applica.feneal.domain.model.core.importData;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

public class ImportAnagraficaPrevediValidator extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
        if (row.get("cod_fiscale") == null) {
            this.error = "Manca il campo cod. fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("cod_fiscale").toString().trim())) {
                this.error = "Manca il campo cod. fiscale";
                this.valid = false;
            }
        }

        if (row.get("den_cognome") == null) {
            this.error = this.error +  "\n Manca il campo cognome utente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("den_cognome").toString().trim())) {
                this.error = this.error + "\n Manca il campo cognome utente";
                this.valid = false;
            }
        }


        if (row.get("den_nome") == null) {
            this.error = this.error +  "\n Manca il campo nome utente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("den_nome").toString().trim())) {
                this.error = this.error + "\n Manca il campo nome utente";
                this.valid = false;
            }
        }




        if (row.get("cassa_edile") == null) {
            this.error = "Manca il campo cassa_edile";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("cassa_edile").toString().trim())) {
                this.error = "Manca il campo cassa_edile";
                this.valid = false;
            }
        }

        if (row.get("regione_cassa_edile") == null) {
            this.error = "Manca il campo regione_cassa_edile";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("regione_cassa_edile").toString().trim())) {
                this.error = "Manca il campo regione_cassa_edile";
                this.valid = false;
            }
        }

    }

}