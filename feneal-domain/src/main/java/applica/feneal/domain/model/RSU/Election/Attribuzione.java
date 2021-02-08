package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Election.Math.DivisioneDecimaleConQuozienteInteroEResto;

public class Attribuzione {


    private String note;
    private int attribuzioneDiretta;
    private int attribuzioneMaggiorResto;
    private int attribuzioneSolidarieta;
    private DivisioneDecimaleConQuozienteInteroEResto quorum;
    private Votazione votazione;


    public DivisioneDecimaleConQuozienteInteroEResto getQuorum(){
        return this.quorum;
    }

    public Attribuzione(Votazione votazione, DivisioneDecimaleConQuozienteInteroEResto quorum)
    {
        this.votazione = votazione;
        this.quorum = quorum;
        if (quorum == null)
            return;
        this.quorum.Dividi();
    }



    public void CalcolaAttribuzioneDiretta() throws Exception {
        if (this.quorum == null)
            return;
        this.attribuzioneDiretta = this.quorum.getQuoziente();
    }

    public int getTotaleSeggi() {
        return this.attribuzioneMaggiorResto + this.attribuzioneDiretta + this.attribuzioneSolidarieta;
    }

    public boolean SuperaSogliaSbarramento(double soglia)
    {
        if ((double) this.votazione.getVoti() >= soglia)
            return true;
        this.note = "Soglia di sbarramento non superata";
        return false;
    }

    public Votazione getVotazione() {
        return votazione;
    }

    public void setVotazione(Votazione votazione) {
        this.votazione = votazione;
    }

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
}



