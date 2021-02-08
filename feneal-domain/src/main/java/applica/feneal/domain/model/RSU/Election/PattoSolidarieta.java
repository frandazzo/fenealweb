package applica.feneal.domain.model.RSU.Election;

public class PattoSolidarieta {

    private boolean criterioSolidarietaApplicabile;
    private int percentualeSogliaInterconfederale;
    private int percentualeSogliaCategoria;
    private int percentualeSogliaInCasoListaDominante;
    private TipoAccordoSolidarieta accordoSolidarieta;

    public TipoAccordoSolidarieta getAccordoSolidarieta() {
        return accordoSolidarieta;
    }

    public void setAccordoSolidarieta(TipoAccordoSolidarieta accordoSolidarieta) {
        this.accordoSolidarieta = accordoSolidarieta;
    }

    public int getPercentualeSogliaInCasoListaDominante() {
        return percentualeSogliaInCasoListaDominante;
    }

    public void setPercentualeSogliaInCasoListaDominante(int percentualeSogliaInCasoListaDominante) {
        this.percentualeSogliaInCasoListaDominante = percentualeSogliaInCasoListaDominante;
    }

    public int getPercentualeSogliaCategoria() {
        return percentualeSogliaCategoria;
    }

    public void setPercentualeSogliaCategoria(int percentualeSogliaCategoria) {
        this.percentualeSogliaCategoria = percentualeSogliaCategoria;
    }

    public int getPercentualeSogliaInterconfederale() {
        return percentualeSogliaInterconfederale;
    }

    public void setPercentualeSogliaInterconfederale(int percentualeSogliaInterconfederale) {
        this.percentualeSogliaInterconfederale = percentualeSogliaInterconfederale;
    }

    public boolean isCriterioSolidarietaApplicabile() {
        return criterioSolidarietaApplicabile;
    }

    public void setCriterioSolidarietaApplicabile(boolean criterioSolidarietaApplicabile) {
        this.criterioSolidarietaApplicabile = criterioSolidarietaApplicabile;
    }


    public enum TipoAccordoSolidarieta
    {
        Categoria,
        Interconfederale,
    }
}
