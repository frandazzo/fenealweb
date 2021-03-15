package applica.feneal.admin.facade.RSU;

import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiContrattoRsu;
import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.services.RSU.ContrattoRsuService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by felicegramegna on 09/03/2021.
 */

@Component
public class ContrattoRsuFacade {

    @Autowired
    private Security sec;

    @Autowired
    private ContrattoRSURepository contrattoRSURepository;

    @Autowired
    private ContrattoRsuService svc;


    public void deleteContratto(long id) {
        svc.delete(((User) sec.getLoggedUser()).getLid(), id);
    }

    public long saveContratto(UiContrattoRsu anag) throws Exception {
        ContrattoRSU a = convertUiContrattoRsuToContrattoRsu(anag);

        svc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),a);

        return a.getLid();
    }

    private ContrattoRSU convertUiContrattoRsuToContrattoRsu(UiContrattoRsu anag) {
        ContrattoRSU c = new ContrattoRSU();
        c.setDescription(anag.getDescription());
        c.setId(anag.getId());
        c.setRsuMax(anag.getRsuMax());
        c.setRsuMin(anag.getRsuMin());

        return c;
    }

    public UiContrattoRsu getContrattoDetail(long id) {
        UiContrattoRsu ui = new UiContrattoRsu();
        ContrattoRSU contrattoRSU = svc.getContrattoRsuById(((User) sec.getLoggedUser()).getLid(),id);

        ui.setDescription(contrattoRSU.getDescription());
        ui.setRsuMax(contrattoRSU.getRsuMax());
        ui.setRsuMin(contrattoRSU.getRsuMin());
        ui.setId(contrattoRSU.getIid());

        return ui;
    }
}
