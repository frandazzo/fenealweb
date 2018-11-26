package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 30/05/2016.
 */
public class RowListInteger{

    private String name;
    private List<Integer> data;


    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
