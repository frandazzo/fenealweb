package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.List;

public class UiDettaglioListaLavoroView {

    private String content;
    private List<Lavoratore> lavoratoriLista;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Lavoratore> getLavoratoriLista() {
        return lavoratoriLista;
    }

    public void setLavoratoriLista(List<Lavoratore> lavoratoriLista) {
        this.lavoratoriLista = lavoratoriLista;
    }
}
