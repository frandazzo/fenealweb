package applica.feneal.domain.model.RSU.Election;

import org.apache.commons.lang.StringUtils;

public class ListaElettorale {


    private String nome;
    private boolean firmatariaCCNL;



    public boolean checkLista()
    {
        this.validationError = "";
        if (StringUtils.isEmpty(this.nome))
            this.validationError = "Il nome della lista non può essere nullo";
        if (StringUtils.isEmpty(this.validationError))
            return true;

        return false;
    }


    public ListaElettorale()
    {
    }


    public ListaElettorale(String nome, boolean firmatariaCCNL)
    {
        this.nome = nome;
        this.firmatariaCCNL = firmatariaCCNL;
    }



    public  String toString(){
        return String.format("Lista: %s; Firmataria CCNL %s", this.nome, this.firmatariaCCNL ?  "Sì" :  "No");
    }





    private String validationError;

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getFirmatariaCCNL(){
        return firmatariaCCNL;
    }

    public void setFirmatariaCCNL(boolean firmatariaCCNL) {
        this.firmatariaCCNL = firmatariaCCNL;
    }
}
