package applica.feneal.domain.model.core.importData;

import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

public class CscRowValidator extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {

        valid = true;
        error = "";


        if (row.get("FISCALE") == null) {
            this.error = "Manca il campo cod. fiscale";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("FISCALE").toString().trim())) {
                this.error = "Manca il campo cod. fiscale";
                this.valid = false;
            }
        }

        if (row.get("COGNOME_UTENTE") == null) {
            this.error = this.error +  "\n Manca il campo cognome utente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("COGNOME_UTENTE").toString().trim())) {
                this.error = this.error + "\n Manca il campo cognome utente";
                this.valid = false;
            }
        }


        if (row.get("NOME_UTENTE") == null) {
            this.error = this.error +  "\n Manca il campo nome utente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("NOME_UTENTE").toString().trim())) {
                this.error = this.error + "\n Manca il campo nome utente";
                this.valid = false;
            }
        }


        if (row.get("COGNOME_REFERENTE") == null) {
            this.error = this.error +  "\n Manca il campo cognome referente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("COGNOME_REFERENTE").toString().trim())) {
                this.error = this.error + "\n Manca il campo cognome referente";
                this.valid = false;
            }
        }


        if (row.get("NOME_REFERENTE") == null) {
            this.error = this.error +  "\n Manca il campo nome referente";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("NOME_REFERENTE").toString().trim())) {
                this.error = this.error + "\n Manca il campo nome referente";
                this.valid = false;
            }
        }


        if (row.get("DATA DOMANDA NASPI") == null) {
            this.error = this.error +  "\n Manca il campo data domanda";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("DATA DOMANDA NASPI").toString().trim())) {
                this.error = this.error + "\n Manca il campo data domanda";
                this.valid = false;
            }
        }


    }

}