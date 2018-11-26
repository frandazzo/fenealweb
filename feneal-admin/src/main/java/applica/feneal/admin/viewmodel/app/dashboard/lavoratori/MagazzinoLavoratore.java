package applica.feneal.admin.viewmodel.app.dashboard.lavoratori;

/**
 * Created by fgran on 06/09/2016.
 */
public class MagazzinoLavoratore {

    private String provincia;
    private String ente;
    private String data;
    private String collaboratore;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(String collaboratore) {
        this.collaboratore = collaboratore;
    }


}
