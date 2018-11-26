package applica.feneal.services.impl.quote;

/**
 * Created by fgran on 22/05/2016.
 */
public class ImportReport {
    private boolean terminated;
    private String startAnagraficheLav;
    private String anagraficheLav;
    private String endAnagraficheLav;


    private String startAnagraficheAz;
    private String anagraficheAz;
    private String endAnagraficheAz;


    private String startImport;
    private String endImport;
    private String importData;

    private String endAllImport;

    public String getEndAllImport() {
        return endAllImport;
    }

    public void setEndAllImport(String endAllImport) {
        this.endAllImport = endAllImport;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public String getStartAnagraficheLav() {
        return startAnagraficheLav;
    }

    public void setStartAnagraficheLav(String startAnagraficheLav) {
        this.startAnagraficheLav = startAnagraficheLav;
    }

    public String getAnagraficheLav() {
        return anagraficheLav;
    }

    public void setAnagraficheLav(String anagraficheLav) {
        this.anagraficheLav = anagraficheLav;
    }

    public String getEndAnagraficheLav() {
        return endAnagraficheLav;
    }

    public void setEndAnagraficheLav(String endAnagraficheLav) {
        this.endAnagraficheLav = endAnagraficheLav;
    }

    public String getStartAnagraficheAz() {
        return startAnagraficheAz;
    }

    public void setStartAnagraficheAz(String startAnagraficheAz) {
        this.startAnagraficheAz = startAnagraficheAz;
    }

    public String getAnagraficheAz() {
        return anagraficheAz;
    }

    public void setAnagraficheAz(String anagraficheAz) {
        this.anagraficheAz = anagraficheAz;
    }

    public String getEndAnagraficheAz() {
        return endAnagraficheAz;
    }

    public void setEndAnagraficheAz(String endAnagraficheAz) {
        this.endAnagraficheAz = endAnagraficheAz;
    }

    public String getStartImport() {
        return startImport;
    }

    public void setStartImport(String startImport) {
        this.startImport = startImport;
    }

    public String getEndImport() {
        return endImport;
    }

    public void setEndImport(String endImport) {
        this.endImport = endImport;
    }

    public String getImportData() {
        return importData;
    }

    public void setImportData(String importData) {
        this.importData = importData;
    }
}
