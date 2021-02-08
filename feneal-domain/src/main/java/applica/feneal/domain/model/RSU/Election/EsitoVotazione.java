package applica.feneal.domain.model.RSU.Election;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EsitoVotazione {

    private String validationError;
    private int aventiDiritto;
    private int rSUElegibili;
    private boolean calcoloQuozienteElettoraleConSchedeNulle;
    private List<Votazione> votazioni = new ArrayList();
    private int schedeBianche;
    private int schedeNulle;



    public int getAventiDiritto(){
        return this.aventiDiritto;
    }
    public void setAventiDiritto(int aventiDiritto){
        this.aventiDiritto = aventiDiritto;
    }

    public int getRSUElegibili(){
        return this.rSUElegibili;
    }
    public void getRSUElegibili(int rsuEleggibili){
            this.rSUElegibili = rsuEleggibili;
    }

    public boolean getCalcoloQuozienteElettoraleConSchedeNulle(){
        return this.calcoloQuozienteElettoraleConSchedeNulle;
    }
    public void setCalcoloQuozienteElettoraleConSchedeNulle(boolean calcoloQuozienteElettoraleConSchedeNulle){
            this.calcoloQuozienteElettoraleConSchedeNulle = calcoloQuozienteElettoraleConSchedeNulle;
    }

    public String getValidationError(){
        return this.validationError;
    }

    public void CreateListaVotazioni(List<ListaElettorale> partecipanti)
    {
        this.votazioni =  new ArrayList<Votazione>();
        for (ListaElettorale lista : partecipanti)
        {
            Votazione votazione = new Votazione();
            votazione.setLista(lista);

            this.votazioni.add(votazione);
        }

    }



    public int getSchedeBianche(){
        return this.schedeBianche;
    }
    public void setSchedeBianche(int schedeBianche) {
        this.schedeBianche = schedeBianche;
    }

    public int getSchedeNulle(){
        return this.schedeNulle;
    }
    public void setSchedeNulle(int schedeNulle) {
        this.schedeNulle = schedeNulle;
    }


    public int getVotanti(){
       return this.getVotiLista() + this.getSchedeNulle() + this.getSchedeBianche();
    }

    public boolean checkValidation()
    {
        this.validationError = "";
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getAventiDiritto() <= 0)
            stringBuilder.append("Il numero di aventi diritto non può essere inferiore o uguale a zero");
        if (this.getVotanti() > this.getAventiDiritto())
            stringBuilder.append("Il numero dei votanti non può essere superiore al numero degli aventi diritto");
        if (this.getVotanti() < this.getAventiDiritto() / 2 + 1)
            stringBuilder.append("Il numero dei votanti deve essere superiore alla metà più uno degli aventi diritto di voto");
        if (this.getRSUElegibili() <= 0)
            stringBuilder.append("Il numero delle RSU da eleggere non può essere inferiore o uguale a zero");
        this.validationError = stringBuilder.toString();
        if (StringUtils.isEmpty(this.validationError))
            return true;
        return false;
    }

    private int getVotiLista()
    {
        int num = 0;
        for (Votazione votazione : this.votazioni) {
            num += votazione.getVoti();
        }
        return num;
    }

    public int getSchedeValide(){
        return this.getCalcoloQuozienteElettoraleConSchedeNulle() ?
                this.getSchedeNulle() + this.getVotiLista() : this.getVotiLista();
    }

    public int getSchedeListe(){
        return this.getVotiLista();
    }

    public List<Votazione> getVotazioni() {
        return this.votazioni;
    }
    public void setVotazioni(List<Votazione> votazioni){
        this.votazioni = votazioni;
    }





}
