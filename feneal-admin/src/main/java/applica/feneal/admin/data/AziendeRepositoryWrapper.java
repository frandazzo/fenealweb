package applica.feneal.admin.data;

import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by applica on 17/03/15.
 */
@org.springframework.stereotype.Repository
public class AziendeRepositoryWrapper implements Repository<Azienda> {

    @Autowired
    private AziendeRepository aziendeRepository;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private TraceFacade traceFacade;


    @Override
    public Optional<Azienda> get(Object id) {
        return aziendeRepository.get(id);
    }

    @Override
    public LoadResponse<Azienda> find(LoadRequest request) {


        final LoadRequest finalRequest = request;

        request.popFilter("province").ifPresent(filter -> {
            if (StringUtils.hasLength(String.valueOf(filter.getValue())))
                finalRequest.getFilters().add(new Filter("province", geoSvc.getProvinceById(Integer.valueOf((String)filter.getValue())),Filter.EQ));
        });

        request.popFilter("city").ifPresent(filter -> {
            if (StringUtils.hasLength(String.valueOf(filter.getValue())))
                finalRequest.getFilters().add(new Filter("city", geoSvc.getCityById(Integer.valueOf((String)filter.getValue())),Filter.EQ));
        });


        LoadResponse<Azienda> res = aziendeRepository.find(request);

        // Se ho ricercato un'azienda tramite filtri di ricerca,
        // e quindi esiste più di un filtro di ricerca impostato (oltre quello del companyId),
        // provvedo a tracciare l'attività
        if (finalRequest.getFilters().size() > 1) {
            String descr = (String) finalRequest.getFilters().stream().filter(p -> "description".equals(p.getProperty())).findFirst().get().getValue();

            Filter province = finalRequest.getFilters().stream().filter(p -> "province".equals(p.getProperty())).findFirst().orElse(null);
            String provinceDescr = (province != null) ? ((Province)province.getValue()).getDescription() : "";

            Filter city = finalRequest.getFilters().stream().filter(p -> "city".equals(p.getProperty())).findFirst().orElse(null);
            String cityDescr = (city != null) ? ((City)city.getValue()).getDescription() : "";

            manageActivityAziende(descr, provinceDescr, cityDescr, res.getRows());
        }

        return res;
    }

    @Override
    public void save(Azienda entity) {
        aziendeRepository.save(entity);
    }

    @Override
    public void delete(Object id) {
        aziendeRepository.delete(id);
    }

    @Override
    public Class<Azienda> getEntityType() {
        return aziendeRepository.getEntityType();
    }


    private void manageActivityAziende(String descr, String province, String city, List<Azienda> aziende) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            traceFacade.traceActivity(user, "Ricerca aziende", createDetailForAziende(aziende),
                    String.format("Parametri di ricerca: Descrizione: %s; Provincia: %s; Città: %s", descr, province, city));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    private String createDetailForAziende(List<Azienda> aziende) {

        StringBuilder b = new StringBuilder();
        b.append("Nella ricerca sono comparse le seguenti aziende: <br><br>");

        for (Azienda az : aziende) {
            b.append(String.format("%s <br>", az.getDescription()));
        }

        return b.toString();
    }
}
