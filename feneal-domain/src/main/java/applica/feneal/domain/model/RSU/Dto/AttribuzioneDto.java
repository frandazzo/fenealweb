package applica.feneal.domain.model.RSU.Dto;

import applica.feneal.domain.model.RSU.Election.Math.DivisioneDecimaleConQuozienteInteroEResto;

public class AttribuzioneDto {
    private String note;
    private int attribuzioneDiretta;
    private int attribuzioneMaggiorResto;
    private int attribuzioneSolidarieta;
    private DivisioneDecimaleConQuozienteInteroEResto quorum;
    private VotazioneDto votazione;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAttribuzioneDiretta() {
        return attribuzioneDiretta;
    }

    public void setAttribuzioneDiretta(int attribuzioneDiretta) {
        this.attribuzioneDiretta = attribuzioneDiretta;
    }

    public int getAttribuzioneMaggiorResto() {
        return attribuzioneMaggiorResto;
    }

    public void setAttribuzioneMaggiorResto(int attribuzioneMaggiorResto) {
        this.attribuzioneMaggiorResto = attribuzioneMaggiorResto;
    }

    public int getAttribuzioneSolidarieta() {
        return attribuzioneSolidarieta;
    }

    public void setAttribuzioneSolidarieta(int attribuzioneSolidarieta) {
        this.attribuzioneSolidarieta = attribuzioneSolidarieta;
    }

    public DivisioneDecimaleConQuozienteInteroEResto getQuorum() {
        return quorum;
    }

    public void setQuorum(DivisioneDecimaleConQuozienteInteroEResto quorum) {
        this.quorum = quorum;
    }

    public VotazioneDto getVotazione() {
        return votazione;
    }

    public void setVotazione(VotazioneDto votazione) {
        this.votazione = votazione;
    }
}
