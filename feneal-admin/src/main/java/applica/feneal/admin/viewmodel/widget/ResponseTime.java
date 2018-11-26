package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 05/04/2016.
 */
public class ResponseTime {

    private List<RowListDouble> rows;
    private Double averageValue;
    private String description;


    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RowListDouble> getRows() {
        return rows;
    }

    public void setRows(List<RowListDouble> rows) {
        this.rows = rows;
    }
}
