package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.dbnazionale.UiInvioDbNazionale;
import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.framework.Disjunction;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class ImportazioneFacade {

    @Autowired
    private ImportazioniRepository impRep;

    @Autowired
    private Security sec;


    @Autowired
    private GeoService geo;

    @Autowired
    private ProvinceRepository provinceRepository;



    public List<Importazione> getImportazioniPerTerritorio(){



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

        return impRep.find(req).getRows();


    }



    public List<UiInvioDbNazionale> getInviiDbNazionale(int anno){
        List<Province> cc = provinceRepository.find(LoadRequest.build()).getRows();

        LoadRequest req = LoadRequest.build().filter("anno", anno);
        List<Importazione> list = impRep.find(req).getRows();


        return convertToUiInviiDbNazionale(anno, cc,list);
    }

    private List<UiInvioDbNazionale> convertToUiInviiDbNazionale(int anno, List<Province> province, List<Importazione> list) {
        List<UiInvioDbNazionale> result = new ArrayList<>();

        for (Province p : province) {
            result.add(createInvioDbNazionale(anno, p, list));
        }

        return result;
    }

    private UiInvioDbNazionale createInvioDbNazionale(int anno ,Province p, List<Importazione> list) {

        //creo un invio e verifico che nella lista ci siano importazioni per l'anno
        //consideratort
        UiInvioDbNazionale f = new UiInvioDbNazionale();
        f.setAnno(String.valueOf(anno));
        f.setProvincia(p.getDescription());
        f.setRegione(geo.getREgionById(p.getIdRegion()).getDescription());


        List<Importazione> importazioniPerProvincia = list.stream()
                .filter( a -> a.getIdProvincia() == p.getIid()).collect(Collectors.toList());


        for (Importazione importazione : importazioniPerProvincia) {
            if (importazione.getSettore().equals(Sector.sector_edile)){
                f.setEdile("OK");
                if (importazione.getPeriodo() == 1)
                    f.setPrimoSemestre("OK");
                else
                    f.setSecondoSemestre("OK");
            }else if (importazione.getSettore().equals(Sector.sector_IMPIANTIFISSI) ){
                f.setImpiantiFissi("OK");
            }else{
                f.setInps("OK");
            }
        }



        return f;
    }


}
