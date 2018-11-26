package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiLavoratori;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.lavoratori.ListeLavoroComparison;
import applica.feneal.services.ListaLavoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by angelo on 10/06/2016.
 */
@Component
public class ListeLavoroFacade {

    @Autowired
    private ListaLavoroService svc;


    public ListaLavoro getListaLavoroById(long listaId) {

        ListaLavoro l =  svc.getListaById(listaId);

        return l;
    }


    public void deleteLista(long id) {
        svc.deleteLista(id);
    }

    public ListaLavoro mergeLists(long listaId, long otherListaId, String description) throws Exception {

        ListaLavoro l1 = svc.getListaById(listaId);
        ListaLavoro l2 = svc.getListaById(otherListaId);

        return svc.mergeWithOtherList(l1, l2, description);
    }

    public ListaLavoro excludeLists(long listaId, long otherListaId, String description) throws Exception {

        ListaLavoro l1 = svc.getListaById(listaId);
        ListaLavoro l2 = svc.getListaById(otherListaId);

        return svc.excludeWithOtherList(l1, l2, description);
    }

    public ListaLavoro intersectLists(long listaId, long otherListaId, String description) throws Exception {

        ListaLavoro l1 = svc.getListaById(listaId);
        ListaLavoro l2 = svc.getListaById(otherListaId);

        return svc.intersectWithOtherList(l1, l2, description);
    }

    public ListeLavoroComparison compareLists(long listaId, long otherListaId, String description) throws Exception {

        ListaLavoro l1 = svc.getListaById(listaId);
        ListaLavoro l2 = svc.getListaById(otherListaId);

        ListeLavoroComparison lista = svc.compareLists(l1, l2, description);

        return lista;
    }

    public void deleteWorkers(long listaId, UiLavoratori lavs) {

        for (Lavoratore lav : lavs.getLavoratori()) {
            svc.deleteWorkerFromLista(listaId, lav.getLid());

        }
    }

    public void addWorker(long listaId, long workerId) {
        svc.addWorker(listaId, workerId);
    }
}
