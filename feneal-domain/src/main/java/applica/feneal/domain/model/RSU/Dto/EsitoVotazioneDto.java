package applica.feneal.domain.model.RSU.Dto;

import java.util.List;

public class EsitoVotazioneDto {
    private String validationError;
    private int aventiDiritto;
    private int rSUElegibili;
    private boolean calcoloQuozienteElettoraleConSchedeNulle;
    private List<VotazioneDto> votazioni;
    private int schedeBianche;
    private int schedeNulle;

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public int getAventiDiritto() {
        return aventiDiritto;
    }

    public void setAventiDiritto(int aventiDiritto) {
        this.aventiDiritto = aventiDiritto;
    }

    public int getrSUElegibili() {
        return rSUElegibili;
    }

    public void setrSUElegibili(int rSUElegibili) {
        this.rSUElegibili = rSUElegibili;
    }

    public boolean isCalcoloQuozienteElettoraleConSchedeNulle() {
        return calcoloQuozienteElettoraleConSchedeNulle;
    }

    public void setCalcoloQuozienteElettoraleConSchedeNulle(boolean calcoloQuozienteElettoraleConSchedeNulle) {
        this.calcoloQuozienteElettoraleConSchedeNulle = calcoloQuozienteElettoraleConSchedeNulle;
    }

    public List<VotazioneDto> getVotazioni() {
        return votazioni;
    }

    public void setVotazioni(List<VotazioneDto> votazioni) {
        this.votazioni = votazioni;
    }

    public int getSchedeBianche() {
        return schedeBianche;
    }

    public void setSchedeBianche(int schedeBianche) {
        this.schedeBianche = schedeBianche;
    }

    public int getSchedeNulle() {
        return schedeNulle;
    }

    public void setSchedeNulle(int schedeNulle) {
        this.schedeNulle = schedeNulle;
    }
}
