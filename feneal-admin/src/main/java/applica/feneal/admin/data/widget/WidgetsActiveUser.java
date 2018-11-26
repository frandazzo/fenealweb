package applica.feneal.admin.data.widget;

/**
 * Created by david on 02/04/2016.
 */

/* Lo scopo di questo oggetto e' di elencare tutti i widget presenti nel tema che l'utente vorrebbe visualizzare nella dashboard */
public class WidgetsActiveUser {

    private Boolean header;
    private Boolean dataPanel;
    private Boolean columnGraph;
    private Boolean areaGraph;
    private Boolean pieCharts;
    private Boolean responseTime;
    private Boolean barGraph;
    private Boolean sparkLines;
    private Boolean areaGraphOnlyCurve;
    private Boolean dotList;
    private Boolean textList;
    private Boolean circulars;


    public Boolean getCirculars() {
        return circulars;
    }

    public void setCirculars(Boolean circulars) {
        this.circulars = circulars;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Boolean getDataPanel() {
        return dataPanel;
    }

    public void setDataPanel(Boolean dataPanel) {
        this.dataPanel = dataPanel;
    }

    public Boolean getColumnGraph() {
        return columnGraph;
    }

    public void setColumnGraph(Boolean columnGraph) {
        this.columnGraph = columnGraph;
    }

    public Boolean getAreaGraph() {
        return areaGraph;
    }

    public void setAreaGraph(Boolean areaGraph) {
        this.areaGraph = areaGraph;
    }

    public Boolean getPieCharts() {
        return pieCharts;
    }

    public void setPieCharts(Boolean pieCharts) {
        this.pieCharts = pieCharts;
    }

    public Boolean getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Boolean responseTime) {
        this.responseTime = responseTime;
    }

    public Boolean getBarGraph() {
        return barGraph;
    }

    public void setBarGraph(Boolean barGraph) {
        this.barGraph = barGraph;
    }

    public Boolean getSparkLines() {
        return sparkLines;
    }

    public void setSparkLines(Boolean sparkLines) {
        this.sparkLines = sparkLines;
    }

    public Boolean getAreaGraphOnlyCurve() {
        return areaGraphOnlyCurve;
    }

    public void setAreaGraphOnlyCurve(Boolean areaGraphOnlyCurve) {
        this.areaGraphOnlyCurve = areaGraphOnlyCurve;
    }

    public Boolean getDotList() {
        return dotList;
    }

    public void setDotList(Boolean dotList) {
        this.dotList = dotList;
    }

    public Boolean getTextList() {
        return textList;
    }

    public void setTextList(Boolean textList) {
        this.textList = textList;
    }
}
