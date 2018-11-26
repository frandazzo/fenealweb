package applica.feneal.domain.model.core.aziende;

/**
 * Created by fgran on 17/06/2016.
 */
public class NonIscrittiAzienda {

    private String azienda;
    private int liberi;
    private int filca;
    private int fillea;


    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public int getLiberi() {
        return liberi;
    }

    public void setLiberi(int liberi) {
        this.liberi = liberi;
    }

    public int getFilca() {
        return filca;
    }

    public void setFilca(int filca) {
        this.filca = filca;
    }

    public int getFillea() {
        return fillea;
    }

    public void setFillea(int fillea) {
        this.fillea = fillea;
    }
}
