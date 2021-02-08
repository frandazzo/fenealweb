package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.ListaElettorale;

public class HelperAttribuzioneIndirettaItem implements Comparable {

    private ListaElettorale lista;
    private double numeroChiave;
    private  int occorrenze;

    public HelperAttribuzioneIndirettaItem(ListaElettorale listaElettorale, double numeroChiave){
        this.lista = listaElettorale;
        this.numeroChiave = numeroChiave;
    }

    @Override
    public int compareTo(Object o) {
        HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem = (HelperAttribuzioneIndirettaItem)o;
        if (this.numeroChiave > attribuzioneIndirettaItem.getNumeroChiave())
            return -1;
        if (this.numeroChiave < attribuzioneIndirettaItem.getNumeroChiave())
            return 1;
        return 0;
    }

    public double getNumeroChiave() {
        return numeroChiave;
    }

    public ListaElettorale getLista() {
        return lista;
    }

    public int getOccorrenze() {
        return occorrenze;
    }

    public void setOccorrenze(int occorrenze) {
        this.occorrenze = occorrenze;
    }
}
