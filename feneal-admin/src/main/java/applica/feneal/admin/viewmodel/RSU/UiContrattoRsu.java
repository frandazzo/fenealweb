package applica.feneal.admin.viewmodel.RSU;

/**
 * Created by felicegramegna on 09/03/2021.
 */
public class UiContrattoRsu {
    private String description;
    private long id;
    private int rsuMax;
    private int rsuMin;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRsuMax() {
        return rsuMax;
    }

    public void setRsuMax(int rsuMax) {
        this.rsuMax = rsuMax;
    }

    public int getRsuMin() {
        return rsuMin;
    }

    public void setRsuMin(int rsuMin) {
        this.rsuMin = rsuMin;
    }
}
