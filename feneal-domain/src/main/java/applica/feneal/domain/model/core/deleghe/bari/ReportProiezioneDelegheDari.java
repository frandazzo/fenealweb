package applica.feneal.domain.model.core.deleghe.bari;

import java.util.List;

public class ReportProiezioneDelegheDari {
    public ContatoreStatoDelegheBari getContatoreStato() {
        return contatoreStato;
    }
    public List<ProiezioneDelegheBari> getDeleghe() {
        return deleghe;
    }

    private ContatoreStatoDelegheBari contatoreStato;
    private List<ProiezioneDelegheBari> deleghe;


    public ReportProiezioneDelegheDari(List<ProiezioneDelegheBari> deleghe){
        this.deleghe = deleghe;
        this.contatoreStato = new ContatoreStatoDelegheBari(deleghe, false);
    }


}
