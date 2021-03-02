package applica.feneal.domain.model.RSU.Dto;

import applica.feneal.domain.model.RSU.Election.PattoSolidarieta;

import java.util.List;

public class ElezioneDto {
    private int anno;
    private String divisione;
    private String azienda;
    private String validationError = "";
    private List<ListaElettoraleDto> liste;
    private PattoSolidarieta solidarieta;
    private EsitiDto esiti;

    public PattoSolidarieta getSolidarieta() {
        return solidarieta;
    }

    public void setSolidarieta(PattoSolidarieta solidarieta) {
        this.solidarieta = solidarieta;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getDivisione() {
        return divisione;
    }

    public void setDivisione(String divisione) {
        this.divisione = divisione;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public List<ListaElettoraleDto> getListe() {
        return liste;
    }

    public void setListe(List<ListaElettoraleDto> liste) {
        this.liste = liste;
    }

    public EsitiDto getEsiti() {
        return esiti;
    }

    public void setEsiti(EsitiDto esiti) {
        this.esiti = esiti;
    }
}
