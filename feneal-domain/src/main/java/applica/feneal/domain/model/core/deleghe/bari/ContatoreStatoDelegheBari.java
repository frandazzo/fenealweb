package applica.feneal.domain.model.core.deleghe.bari;

import java.util.List;

public class ContatoreStatoDelegheBari {

    private int iscritti;
    private int iscrittiInEntrata;
    private int iscrittInUscita;
    private int nonIscritti;
    private  int indeterminati;

    public int getIscritti() {
        return iscritti;
    }

    public int getIscrittiInEntrata() {
        return iscrittiInEntrata;
    }

    public int getIscrittInUscita() {
        return iscrittInUscita;
    }

    public int getNonIscritti() {
        return nonIscritti;
    }

    public int getIndeterminati() {
        return indeterminati;
    }

    public ContatoreStatoDelegheBari(List<StatoDelega> deleghe){
        iscritti = findNumberForState(DelegaBari.StatoIscrizione, deleghe);
        iscrittiInEntrata = findNumberForState(DelegaBari.StatoIscrizioneInEntrata, deleghe);
        iscrittInUscita = findNumberForState(DelegaBari.StatoIscrizioneInUscita, deleghe);
        nonIscritti = findNumberForState(DelegaBari.StatoNonIscrizione, deleghe);


        int total = iscritti + iscrittiInEntrata + iscrittInUscita + nonIscritti;

        indeterminati = deleghe.size() - total;

    }


    public ContatoreStatoDelegheBari(List<ProiezioneDelegheBari> projections, boolean fakeParams){
        iscritti = findNumberForStateInProjection(DelegaBari.StatoIscrizione, projections);
        iscrittiInEntrata = findNumberForStateInProjection(DelegaBari.StatoIscrizioneInEntrata, projections);
        iscrittInUscita = findNumberForStateInProjection(DelegaBari.StatoIscrizioneInUscita, projections);
        nonIscritti = findNumberForStateInProjection(DelegaBari.StatoNonIscrizione, projections);


        int total = iscritti + iscrittiInEntrata + iscrittInUscita + nonIscritti;

        indeterminati = projections.size() - total;

    }

    private int findNumberForStateInProjection(String stato, List<ProiezioneDelegheBari> deleghe) {
        int result = 0;
        for (ProiezioneDelegheBari statoDelega : deleghe) {
            if (stato.equals(statoDelega.getLastState()))
                result++;
        }

        return result;
    }

    private int findNumberForState(String statoIscrizione, List<StatoDelega> deleghe) {
        int result = 0;
        for (StatoDelega statoDelega : deleghe) {
            if (statoIscrizione.equals(statoDelega.getState()))
                result++;
        }

        return result;
    }

}
