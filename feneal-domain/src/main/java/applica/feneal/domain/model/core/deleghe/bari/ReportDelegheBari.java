package applica.feneal.domain.model.core.deleghe.bari;

import java.util.List;

public class ReportDelegheBari {


    private List<StatoDelega> deleghe;
    private ContatoreDelegheBari contatore;
    private ContatoreStatoDelegheBari contatoreStato;
    private int bilancioEntrate;



    public ContatoreStatoDelegheBari getContatoreStato() {
        return contatoreStato;
    }
    public ContatoreDelegheBari getContatore() {
        return contatore;
    }

    public int getBilancioEntrate() {
        return bilancioEntrate;
    }

    public List<StatoDelega> getDeleghe() {
        return deleghe;
    }



    public ReportDelegheBari(List<StatoDelega> deleghe){

        this.deleghe = deleghe;
        //adesso calcolo il bilancio
        calculateBilancio();
        //ora calcolo tutti i contatori
        calculateStatistics();
        //clacolo lo stato finale
        calculatefinalStatus();

    }

    private void calculatefinalStatus() {
        contatoreStato = new ContatoreStatoDelegheBari(deleghe);
    }

    private void calculateStatistics() {
        contatore = new ContatoreDelegheBari(deleghe);

    }

    private void calculateBilancio() {
        for (StatoDelega statoDelega : deleghe) {
            bilancioEntrate = bilancioEntrate + statoDelega.getContributeToBalace();
        }
    }


}
