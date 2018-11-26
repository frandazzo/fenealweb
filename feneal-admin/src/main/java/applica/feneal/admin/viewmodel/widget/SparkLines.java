package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 05/04/2016.
 */
public class SparkLines {

    private String name;
    //i colori disponibili in questo widget sono warning,info,primary,alert
    private String color;
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
