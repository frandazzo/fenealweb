package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 05/04/2016.
 */
public class RowBarGraph {

    //quando va valorizzato l'id bisogna cominciare con 0
    private Integer id;
    private String name;
    private List<Double> data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }
}
