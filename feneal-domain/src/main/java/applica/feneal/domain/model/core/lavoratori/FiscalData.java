package applica.feneal.domain.model.core.lavoratori;

import java.util.Date;

/**
 * Created by fgran on 24/05/2016.
 */
public class FiscalData {

    private String nazione;
    private String provincia;
    private String comune;
    private String datanascita;
    private String sex;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(String datanascita) {
        this.datanascita = datanascita;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
}
