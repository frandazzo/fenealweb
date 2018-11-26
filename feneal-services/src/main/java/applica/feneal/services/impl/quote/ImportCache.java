package applica.feneal.services.impl.quote;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.HashMap;

/**
 * Created by fgran on 20/05/2016.
 */
public class ImportCache {

    private HashMap<Integer, Lavoratore>  lavoratori =new HashMap<Integer, Lavoratore>();
    private HashMap<String, Lavoratore>  lavoratoriFiscalCode =new HashMap<String, Lavoratore>();
    private HashMap<String, Azienda>  aziende=new HashMap<String, Azienda>();

    public HashMap<String, Lavoratore> getLavoratoriFiscalCode() {
        return lavoratoriFiscalCode;
    }

    public void setLavoratoriFiscalCode(HashMap<String, Lavoratore> lavoratoriFiscalCode) {
        this.lavoratoriFiscalCode = lavoratoriFiscalCode;
    }

    public HashMap<String, Azienda> getAziende() {
        return aziende;
    }

    public void setAziende(HashMap<String, Azienda> aziende) {
        this.aziende = aziende;
    }

    public HashMap<Integer, Lavoratore> getLavoratori() {
        return lavoratori;
    }

    public void setLavoratori(HashMap<Integer, Lavoratore> lavoratori) {
        this.lavoratori = lavoratori;
    }
}
