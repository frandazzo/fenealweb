package applica.feneal.admin.viewmodel.lavoratori;

import java.util.List;

/**
 * Created by fgran on 19/05/2016.
 */
public class UiWorkerIscrizioniChart {

    private Integer[] anni = new Integer[]{};
    private Integer[] provincesIds = new Integer[]{};
    private String[] provinces = new String[]{};
    private String workerName;
    private Integer[] loggedUserProvinceIds = new Integer[]{};
    private List<UiWorkerChartElement> chartElements;


    public Integer[] getAnni() {
        return anni;
    }

    public void setAnni(Integer[] anni) {
        this.anni = anni;
    }

    public Integer[] getProvincesIds() {
        return provincesIds;
    }

    public void setProvincesIds(Integer[] provincesIds) {
        this.provincesIds = provincesIds;
    }

    public String[] getProvinces() {
        return provinces;
    }

    public void setProvinces(String[] provinces) {
        this.provinces = provinces;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Integer[] getLoggedUserProvinceIds() {
        return loggedUserProvinceIds;
    }

    public void setLoggedUserProvinceIds(Integer[] loggedUserProvinceIds) {
        this.loggedUserProvinceIds = loggedUserProvinceIds;
    }

    public void setChartElements(List<UiWorkerChartElement> chartElements) {
        this.chartElements = chartElements;
    }

    public List<UiWorkerChartElement> getChartElements() {
        return chartElements;
    }
}
