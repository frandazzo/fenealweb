package applica.feneal.domain.model.core.importData;

import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Hashtable;

/**
 * Created by fgran on 19/04/2017.
 */
public class ImportDelegheValidator extends applica.framework.management.csv.RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {
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

        if (row.get("SETTORE") == null) {
            this.error = this.error +  "\n Manca il campo settore";
            this.valid = false;
            return;
        } else {
            if (StringUtils.isEmpty(row.get("SETTORE").toString().trim())) {
                this.error = this.error + "\n Manca il campo SETTORE";
                this.valid = false;
                return;
            }else{
                if (!row.get("SETTORE").toString().trim().equals(Sector.sector_edile) &&
                        !row.get("SETTORE").toString().trim().equals(Sector.sector_IMPIANTIFISSI)){
                    this.error = this.error + "\n Il settore per le deleghe può essere EDILE o IMPIANTI FISSI";
                    this.valid = false;
                    return;
                }
            }
        }


        if (row.get("AZIENDA") == null) {
            this.error = this.error +  "\n Manca il campo AZIENDA";
            this.valid = false;
        } else {
            if (StringUtils.isEmpty(row.get("AZIENDA").toString().trim())) {
                this.error = this.error + "\n Manca il campo AZIENDA";
                this.valid = false;
            }else{

                //se cè l'azienda deve essere un ente se delega edile altrimenti deve essere una
                //normale ragione sociale
                //se è un ente deve essere uo degli enti bilaterali
                String paritetic = row.get("AZIENDA").toString().trim();
                if (row.get("SETTORE").toString().trim().equals(Sector.sector_edile) ){

                    if (!paritetic.equals(Paritethic.ente_cassaedile) &&
                            !paritetic.equals(Paritethic.ente_cea) &&
                            !paritetic.equals(Paritethic.ente_ceva) &&
                            !paritetic.equals(Paritethic.ente_cert) &&
                            !paritetic.equals(Paritethic.ente_falea) &&
                            !paritetic.equals(Paritethic.ente_calec) &&
                            !paritetic.equals(Paritethic.ente_edilcassa) &&
                            !paritetic.equals(Paritethic.ente_cec) &&
                            !paritetic.equals(Paritethic.ente_ceda) &&
                            !paritetic.equals(Paritethic.ente_cedaf) &&
                            !paritetic.equals(Paritethic.ente_cedaiier) &&
                            !paritetic.equals(Paritethic.ente_cedam) &&
                            !paritetic.equals(Paritethic.ente_celcof) &&
                            !paritetic.equals(Paritethic.ente_cema) ){

                        this.error = this.error + "\n Ente bilaterale non supportato";
                        this.valid = false;

                    }


                }



            }
        }



    }

}