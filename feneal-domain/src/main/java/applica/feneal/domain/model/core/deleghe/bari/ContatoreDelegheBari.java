package applica.feneal.domain.model.core.deleghe.bari;

import java.util.List;

public class ContatoreDelegheBari {

    private int iscrizioni;
    private int iscrizioniInefficaci;
    private int iscrizioniCongelate;
    private int iscrizioniIRiconferma;
    private int revocheDeleghe;
    private int revochebianche;
    private int revocheInefficaci;
    private int anomalie;
    private int doppioni;
    private int indeterminate;

    public ContatoreDelegheBari(List<StatoDelega> deleghe){
        iscrizioni = findNumberForAction(DelegaBari.Iscrizione, deleghe);
        iscrizioniInefficaci = findNumberForAction(DelegaBari.IscrizioneInefficace, deleghe);
        iscrizioniIRiconferma = findNumberForAction(DelegaBari.IscrizioneIRiconferma, deleghe);
        iscrizioniCongelate = findNumberForAction(DelegaBari.IscrizioneCongelata, deleghe);

        revocheDeleghe = findNumberForAction(DelegaBari.RevocaDelega, deleghe);
        revochebianche = findNumberForAction(DelegaBari.RevocaBianca, deleghe);
        revocheInefficaci = findNumberForAction(DelegaBari.RevocaInefficace, deleghe);

        doppioni = findNumberForAction(DelegaBari.Doppione, deleghe);
        anomalie = findNumberForAction(DelegaBari.Anomalia, deleghe);



        int total = iscrizioni + iscrizioniCongelate + iscrizioniIRiconferma + iscrizioniInefficaci
                + revochebianche + revocheDeleghe + revocheInefficaci + doppioni + anomalie;

        indeterminate = deleghe.size() - total;

    }

    private int findNumberForAction(String iscrizione, List<StatoDelega> deleghe) {
        int result = 0;
        for (StatoDelega statoDelega : deleghe) {
            if (iscrizione.equals(statoDelega.getLastAction()))
                result++;
        }

        return result;
    }


    public int getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(int iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public int getIscrizioniInefficaci() {
        return iscrizioniInefficaci;
    }

    public void setIscrizioniInefficaci(int iscrizioniInefficaci) {
        this.iscrizioniInefficaci = iscrizioniInefficaci;
    }

    public int getIscrizioniCongelate() {
        return iscrizioniCongelate;
    }

    public void setIscrizioniCongelate(int iscrizioniCongelate) {
        this.iscrizioniCongelate = iscrizioniCongelate;
    }

    public int getIscrizioniIRiconferma() {
        return iscrizioniIRiconferma;
    }

    public void setIscrizioniIRiconferma(int iscrizioniIRiconferma) {
        this.iscrizioniIRiconferma = iscrizioniIRiconferma;
    }

    public int getRevocheDeleghe() {
        return revocheDeleghe;
    }

    public void setRevocheDeleghe(int revocheDeleghe) {
        this.revocheDeleghe = revocheDeleghe;
    }

    public int getRevochebianche() {
        return revochebianche;
    }

    public void setRevochebianche(int revochebianche) {
        this.revochebianche = revochebianche;
    }

    public int getRevocheInefficaci() {
        return revocheInefficaci;
    }

    public void setRevocheInefficaci(int revocheInefficaci) {
        this.revocheInefficaci = revocheInefficaci;
    }

    public int getAnomalie() {
        return anomalie;
    }

    public void setAnomalie(int anomalie) {
        this.anomalie = anomalie;
    }

    public int getDoppioni() {
        return doppioni;
    }

    public void setDoppioni(int doppioni) {
        this.doppioni = doppioni;
    }

    public int getIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(int indeterminate) {
        this.indeterminate = indeterminate;
    }
}
