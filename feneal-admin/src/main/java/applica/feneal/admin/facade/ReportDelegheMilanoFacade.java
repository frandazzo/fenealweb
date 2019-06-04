package applica.feneal.admin.facade;

import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.feneal.domain.model.geo.Province;
import applica.framework.Disjunction;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportDelegheMilanoFacade {

//    @Autowired
//    private ReportDelegheMilanoFacade impRep;

    @Autowired
    private Security sec;

    @Autowired
    private ProvinceRepository provinceRepository;

    public List<DelegaMilano> getImportazioniPerTerritorio(){



        User u = ((User) sec.getLoggedUser());
        Company c = u.getCompany();

        List<Province> cc = c.getProvinces();

        LoadRequest req = LoadRequest.build();

        Disjunction d = new Disjunction();
        List<Filter> list = new ArrayList<>();
        for (Province province : cc) {
            Filter f = new Filter("idProvincia", province.getIid());
            list.add(f);
        }
        d.setChildren(list);
        req.getFilters().add(d);

//        return impRep.find(req).getRows();

        return null;

    }

}
