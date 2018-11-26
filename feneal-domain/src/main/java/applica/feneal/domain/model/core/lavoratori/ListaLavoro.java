package applica.feneal.domain.model.core.lavoratori;

import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.annotations.ManyToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 04/06/2016.
 */
public class ListaLavoro extends SecuredDomainEntity {
    private String description;
    @ManyToMany
    private List<Lavoratore> lavoratori;

    public ListaLavoro clone(){

        ListaLavoro l = new ListaLavoro();
        l.setDescription(description);


        List<Lavoratore> l1 = new ArrayList<>();
        for (Lavoratore lavoratore : lavoratori) {
            l1.add(lavoratore);
        }
        l.setLavoratori(l1);

        return l;

    }

    public ListaLavoro intersects(ListaLavoro l , String description){

        List<Lavoratore> reusult  = new ArrayList<>();

        for (Lavoratore lavoratore : lavoratori) {
            if (l.containsWorker(lavoratore.getLid()))
                reusult.add(lavoratore);
        }

        ListaLavoro ll = new ListaLavoro();
        ll.setLavoratori(reusult);
        ll.setDescription(description);

        return ll;
    }




    public boolean containsWorker(long workerId){
        for (Lavoratore lavoratore : lavoratori) {
            if (lavoratore.getLid() == workerId)
                return true;
        }
        return false;
    }

    private void removeWorker(Lavoratore lav){

        if (!containsWorker(lav.getLid()))
            return;

        int indexToRemove = -1;
        for (int i = 0; i < lavoratori.size(); i++) {
           Lavoratore l  = lavoratori.get(i);
            if (l.getLid() == lav.getLid()){
                indexToRemove = i;
                break;
            }

        }
        if (indexToRemove > -1 )
            lavoratori.remove(indexToRemove);

    }

    public ListaLavoro  excludeLavoratoriOfOtherList(ListaLavoro b, String description ){
        for (Lavoratore lavoratore : b.getLavoratori()) {
            removeWorker(lavoratore);
        }


        ListaLavoro ll = new ListaLavoro();
        ll.setDescription(description);
        ll.setLavoratori(lavoratori);

        return ll;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lavoratore> getLavoratori() {
        return lavoratori;
    }

    public void setLavoratori(List<Lavoratore> lavoratori) {
        this.lavoratori = lavoratori;
    }
}
