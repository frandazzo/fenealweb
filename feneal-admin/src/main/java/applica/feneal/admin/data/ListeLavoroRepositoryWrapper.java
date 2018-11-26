package applica.feneal.admin.data;

import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.domain.data.core.lavoratori.ListaLavoroRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Created by applica on 17/03/15.
 */
@org.springframework.stereotype.Repository
public class ListeLavoroRepositoryWrapper implements Repository<ListaLavoro> {

    @Autowired
    private ListaLavoroRepository listaLavoroRepository;

    @Autowired
    private TraceFacade traceFacade;


    @Override
    public Optional<ListaLavoro> get(Object id) {
        return listaLavoroRepository.get(id);
    }

    @Override
    public LoadResponse<ListaLavoro> find(LoadRequest request) {

        LoadResponse<ListaLavoro> res = listaLavoroRepository.find(request);

        // Se ho ricercato un'azienda tramite filtri di ricerca,
        // e quindi esiste più di un filtro di ricerca impostato (oltre quello del companyId),
        // provvedo a tracciare l'attività
        if (request.getFilters().size() > 1) {
            String descr = (String) request.getFilters().stream().filter(p -> "description".equals(p.getProperty())).findFirst().get().getValue();

            manageActivityListaLavoro(descr, res.getRows());
        }

        return res;
    }

    @Override
    public void save(ListaLavoro entity) {
        listaLavoroRepository.save(entity);
    }

    @Override
    public void delete(Object id) {
        listaLavoroRepository.delete(id);
    }

    @Override
    public Class<ListaLavoro> getEntityType() {
        return listaLavoroRepository.getEntityType();
    }


    private void manageActivityListaLavoro(String descr, List<ListaLavoro> listeLavoro) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            traceFacade.traceActivity(user, "Ricerca liste di lavoro", createDetailForListeLavoro(listeLavoro),
                    String.format("Parametri di ricerca: Descrizione: %s", descr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    private String createDetailForListeLavoro(List<ListaLavoro> listeLavoro) {

        StringBuilder b = new StringBuilder();
        b.append("Nella ricerca sono comparse le seguenti liste di lavoro: <br><br>");

        for (ListaLavoro l : listeLavoro) {
            b.append(String.format("%s <br>", l.getDescription()));
        }

        return b.toString();
    }
}
