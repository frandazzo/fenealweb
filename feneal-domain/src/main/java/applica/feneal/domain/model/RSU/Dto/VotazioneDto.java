package applica.feneal.domain.model.RSU.Dto;

public class VotazioneDto {
    private ListaElettoraleDto lista;
    private int voti;

    public ListaElettoraleDto getLista() {
        return lista;
    }

    public void setLista(ListaElettoraleDto lista) {
        this.lista = lista;
    }

    public int getVoti() {
        return voti;
    }

    public void setVoti(int voti) {
        this.voti = voti;
    }
}
