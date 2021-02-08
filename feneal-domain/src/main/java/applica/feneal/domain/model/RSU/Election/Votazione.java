package applica.feneal.domain.model.RSU.Election;

public class Votazione {

    private ListaElettorale lista;
    private int voti;


    public ListaElettorale getLista() {
        return lista;
    }

    public void setLista(ListaElettorale lista) {
        this.lista = lista;
    }

    public int getVoti() {
        return voti;
    }

    public void setVoti(int voti) {
        this.voti = voti;
    }
}
