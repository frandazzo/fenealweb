package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 04/04/2016.
 */
public class RowListDouble {

    private String name;
    private List<Double> data;

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
