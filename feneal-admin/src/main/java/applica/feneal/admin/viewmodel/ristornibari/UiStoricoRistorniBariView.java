package applica.feneal.admin.viewmodel.ristornibari;

import applica.feneal.domain.model.core.ristorniEdilizia.Ristorno;

import java.util.List;

public class UiStoricoRistorniBariView {
    private String content;
    private List<Ristorno> listaRistorni;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Ristorno> getListaRistorni() {
        return listaRistorni;
    }

    public void setListaRistorni(List<Ristorno> listaRistorni) {
        this.listaRistorni = listaRistorni;
    }
}
