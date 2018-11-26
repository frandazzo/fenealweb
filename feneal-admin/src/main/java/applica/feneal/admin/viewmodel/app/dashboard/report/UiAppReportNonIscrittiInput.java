package applica.feneal.admin.viewmodel.app.dashboard.report;

/**
 * Created by fgran on 15/09/2016.
 */
public class UiAppReportNonIscrittiInput {

//    selectedProvincia: selectedProvincia,
//    geoNazioneSelected: selectedNazione(),
//    geoProvinceSelected: geoProvinceSelected(),
//    geoComuneSelected: comuneValue(),
//    iscrittiA: selectedIscrittoA,
//    selectedEnte: selectedEnte(),
//    selectedAzienda: selectedAzienda(),
    private String selectedProvincia;
    private String selectedEnte;
    private String selectedAzienda;
    private String geoNazioneSelected;
    private String geoProvinceSelected;
    private String geoComuneSelected;
    private String iscrittoA;

    //dati per la paginaione
    private int take;
    private int skip;


    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public String getSelectedProvincia() {
        return selectedProvincia;
    }

    public void setSelectedProvincia(String selectedProvincia) {
        this.selectedProvincia = selectedProvincia;
    }

    public String getSelectedEnte() {
        return selectedEnte;
    }

    public void setSelectedEnte(String selectedEnte) {
        this.selectedEnte = selectedEnte;
    }

    public String getSelectedAzienda() {
        return selectedAzienda;
    }

    public void setSelectedAzienda(String selectedAzienda) {
        this.selectedAzienda = selectedAzienda;
    }

    public String getGeoNazioneSelected() {
        return geoNazioneSelected;
    }

    public void setGeoNazioneSelected(String geoNazioneSelected) {
        this.geoNazioneSelected = geoNazioneSelected;
    }

    public String getGeoProvinceSelected() {
        return geoProvinceSelected;
    }

    public void setGeoProvinceSelected(String geoProvinceSelected) {
        this.geoProvinceSelected = geoProvinceSelected;
    }

    public String getGeoComuneSelected() {
        return geoComuneSelected;
    }

    public void setGeoComuneSelected(String geoComuneSelected) {
        this.geoComuneSelected = geoComuneSelected;
    }

    public String getIscrittoA() {
        return iscrittoA;
    }

    public void setIscrittoA(String iscrittoA) {
        this.iscrittoA = iscrittoA;
    }
}
