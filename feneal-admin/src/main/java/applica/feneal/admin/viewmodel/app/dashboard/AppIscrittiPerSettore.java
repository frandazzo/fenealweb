package applica.feneal.admin.viewmodel.app.dashboard;

import java.util.List;

/**
 * Created by fgran on 06/09/2016.
 */
public class AppIscrittiPerSettore {
    private String provincia;
    private List<IscrittiSettore> data;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public List<IscrittiSettore> getData() {
        return data;
    }

    public void setData(List<IscrittiSettore> data) {
        this.data = data;
    }
}
