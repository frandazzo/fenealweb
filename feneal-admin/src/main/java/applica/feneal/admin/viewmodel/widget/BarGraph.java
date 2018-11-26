package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 05/04/2016.
 */
public class BarGraph {

    private List<String> categories;
    private String suffix;
    private List<RowBarGraph> rows;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<RowBarGraph> getRows() {
        return rows;
    }

    public void setRows(List<RowBarGraph> rows) {
        this.rows = rows;
    }
}
