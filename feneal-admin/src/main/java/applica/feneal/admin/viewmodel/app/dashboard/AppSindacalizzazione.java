package applica.feneal.admin.viewmodel.app.dashboard;

import java.util.List;

/**
 * Created by fgran on 05/09/2016.
 */
public class AppSindacalizzazione {

    private List<IscrittiSindacato> iscritti;
    private int liberi;
    private double tassoSindacalizzazione;
    private String provincia;
    private String ente;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public double getTassoSindacalizzazione() {
        return tassoSindacalizzazione;
    }

    public void setTassoSindacalizzazione(double tassoSindacalizzazione) {
        this.tassoSindacalizzazione = tassoSindacalizzazione;
    }

    public int getLiberi() {
        return liberi;
    }

    public void setLiberi(int liberi) {
        this.liberi = liberi;
    }

    public List<IscrittiSindacato> getIscritti() {
        return iscritti;
    }

    public void setIscritti(List<IscrittiSindacato> iscritti) {
        this.iscritti = iscritti;
    }




}
