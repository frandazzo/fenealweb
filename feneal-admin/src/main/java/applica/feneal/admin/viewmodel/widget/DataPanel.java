package applica.feneal.admin.viewmodel.widget;

import java.util.List;

/**
 * Created by david on 04/04/2016.
 */
public class DataPanel {

    private Integer firstNumberHeader;
    private Integer secondNumberHeader;
    private List<Integer> values;
    private List<RowInteger> rowDatas;


    public Integer getFirstNumberHeader() {
        return firstNumberHeader;
    }

    public void setFirstNumberHeader(Integer firstNumberHeader) {
        this.firstNumberHeader = firstNumberHeader;
    }

    public Integer getSecondNumberHeader() {
        return secondNumberHeader;
    }

    public void setSecondNumberHeader(Integer secondNumberHeader) {
        this.secondNumberHeader = secondNumberHeader;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public List<RowInteger> getRowDatas() {
        return rowDatas;
    }

    public void setRowDatas(List<RowInteger> rowDatas) {
        this.rowDatas = rowDatas;
    }

}
