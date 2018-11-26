package applica.feneal.admin.viewmodel.widget;

/**
 * Created by david on 04/04/2016.
 */
public class AreaGraph {

    //male,female
    private String key;
    //yahoo, [12,15,21]
    private RowListDouble row;
    //percentuale a destra
    private Double percentage;


    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RowListDouble getRow() {
        return row;
    }

    public void setRow(RowListDouble row) {
        this.row = row;
    }
}
