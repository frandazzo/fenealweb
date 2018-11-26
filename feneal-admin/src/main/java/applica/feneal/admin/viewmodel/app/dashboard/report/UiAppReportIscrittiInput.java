package applica.feneal.admin.viewmodel.app.dashboard.report;

/**
 * Created by fgran on 15/09/2016.
 */
public class UiAppReportIscrittiInput {
//    selectedProvincia: selectedProvincia,
//    tipoQuota: tipoQuota,
//    geoNazioneSelected : selectedNazione(),
//    geoProvinceSelected : geoProvinceSelected(),
//    geoComuneSelected: comuneValue(),
//    selectedSettore : selectedSettore(),
//    selectedEnte : selectedEnte(),
//    selectedAzienda : selectedAzienda(),
//    anniDa : anniDa(),
//    anniA : anniA(),
//    mesiDa : mesiDa(),
//    mesiA : mesiA(),
    private String selectedProvincia;
    private String mesiDa;
    private String anniDa;
    private String mesiA;
    private String anniA;
    private String tipoQuota;
    private String selectedSettore;
    private boolean delegationActiveExist;
    private String selectedEnte;
    private String selectedAzienda;
    private String geoNazioneSelected;
    private String geoProvinceSelected;
    private String geoComuneSelected;
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

    public String getMesiDa() {
        return mesiDa;
    }

    public void setMesiDa(String mesiDa) {
        this.mesiDa = mesiDa;
    }

    public String getAnniDa() {
        return anniDa;
    }

    public void setAnniDa(String anniDa) {
        this.anniDa = anniDa;
    }

    public String getMesiA() {
        return mesiA;
    }

    public void setMesiA(String mesiA) {
        this.mesiA = mesiA;
    }

    public String getAnniA() {
        return anniA;
    }

    public void setAnniA(String anniA) {
        this.anniA = anniA;
    }

    public String getTipoQuota() {
        return tipoQuota;
    }

    public void setTipoQuota(String tipoQuota) {
        this.tipoQuota = tipoQuota;
    }

    public String getSelectedSettore() {
        return selectedSettore;
    }

    public void setSelectedSettore(String selectedSettore) {
        this.selectedSettore = selectedSettore;
    }

    public boolean isDelegationActiveExist() {
        return delegationActiveExist;
    }

    public void setDelegationActiveExist(boolean delegationActiveExist) {
        this.delegationActiveExist = delegationActiveExist;
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
}
