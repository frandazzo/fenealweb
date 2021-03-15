package applica.feneal.admin.facade.RSU;

import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiAziendaRsuAnagraficaSummary;

import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;

import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by felicegramegna on 23/02/2021.
 */

@Component
public class AziendeRsuFacade {

    @Autowired
    private Security sec;

    @Autowired
    private AziendeRSURepository azRep;

    @Autowired
    private AziendaRsuService svc;

    @Autowired
    private GeoService geosvc;

    public long saveAnagrafica(UiAnagraficaAziendaRsu anag) throws Exception {
        AziendaRSU a = convertUiAnagraficaToAziendaRsu(anag);
        svc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),a);

        return a.getLid();
    }

    private AziendaRSU convertUiAnagraficaToAziendaRsu(UiAnagraficaAziendaRsu anag) throws ParseException {
        AziendaRSU az = new AziendaRSU();

        az.setId(anag.getId());
        az.setDescription(anag.getDescription());
        az.setCap(anag.getCap());
        az.setAddress(anag.getAddress());
        az.setNotes(anag.getNotes());
        az.setPhone(anag.getPhone());
        az.setPiva(anag.getPiva());
        az.setCf(anag.getCf());

        //per la provincia se il valore inviato dal client è un numero >0 allora rappresenta l'id della provincia
        try {
            Integer idProvince = Integer.parseInt(anag.getProvince());
            if (idProvince != null)
                if (idProvince > 0){
                    Province p = geosvc.getProvinceById(idProvince);
                    if (p!= null)
                        az.setProvince(p.getDescription());
                }

        } catch(NumberFormatException e){

            //se arrivo qui puo' essere che sto inviando la descrixione
            try{
                Province p = geosvc.getProvinceByName(anag.getProvince());
                if (p!= null)
                    az.setProvince(p.getDescription());

            }catch(Exception e1){
                az.setProvince(null);
            }
        }

        //per la città se il valore inviato dal client è un numero >0 allora rappresenta l'id della città
        try {
            Integer idCity = Integer.parseInt(anag.getCity());
            if (idCity != null)
                if (idCity > 0){
                    City c = geosvc.getCityById(idCity);
                    if (c!= null)
                        az.setCity(c.getDescription());
                }
        } catch(NumberFormatException e){


            //se arrivo qui puo' essere che sto inviando la descrixione
            try{
                City c = geosvc.getCityByName(anag.getCity());
                if (c!= null)
                    az.setCity(c.getDescription());

            }catch(Exception e1){
                az.setCity(null);
            }

        }

        AziendaRSU alreadyExist = svc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),az.getLid());

        if(alreadyExist != null){
            az.setUsernameCreator(alreadyExist.getUsernameCreator());
            az.setCompanyCreator(alreadyExist.getCompanyCreator());
            az.setCreateDate(alreadyExist.getCreateDate());
        }

        return az;
    }

    public UiAziendaRsuAnagraficaSummary getFirmById(long id) {
        AziendaRSU az = svc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),id);

        UiAziendaRsuAnagraficaSummary anag = convertAziendaRsuToUiAzienda(az);

        return anag;
    }

    private UiAziendaRsuAnagraficaSummary convertAziendaRsuToUiAzienda(AziendaRSU az) {
        UiAziendaRsuAnagraficaSummary s = new UiAziendaRsuAnagraficaSummary();

        s.setId(az.getLid());
        s.setDescription(az.getDescription());
        s.setAddress(az.getAddress());
        s.setNotes(az.getNotes());
        s.setPhone(az.getPhone());
        s.setPiva(az.getPiva());
        s.setCity(az.getCity());
        s.setCf(az.getCf());
        s.setProvince(az.getProvince());
        s.setCap(az.getCap());

        return s;
    }

    public long getIdAziendaRsuByDescriptionorCreateIfNotExist(String description) throws Exception {
        if (StringUtils.isEmpty(description))
            throw new Exception("descrizione azienda nulla");
//        Azienda az = azRep.find(LoadRequest.build().filter("description", description)).findFirst().orElse(null);
//        if (az != null)
//            return az.getLid();
//
//
//
//        //creo l'azienda
//        Azienda a = new Azienda();
//        a.setDescription(description);
//        svc.saveOrUpdate(((User) security.getLoggedUser()).getLid(), a);
//
//        return a.getLid();

        return svc.getAziendaRsuByDescriptionorCreateIfNotExist(description).getLid();
    }

    public void deleteAzienda(long id) {
        svc.delete(((User) sec.getLoggedUser()).getLid(), id);
    }


    public UiAziendaRsuAnagraficaSummary getRemoteAziendaRsu(long firmId) {
        UiAziendaRsuAnagraficaSummary ui = getFirmById(firmId);
        return ui;
    }

    public List<UiAziendaRsuAnagraficaSummary> findAziendeRsu(String description) {
        LoadRequest req = LoadRequest.build().disableOwnershipQuery();

        if (!org.springframework.util.StringUtils.isEmpty(description.trim())){
            Filter f1 = new Filter();
            f1.setProperty("description");
            f1.setType(Filter.LIKE);
            f1.setValue(description.trim());
            req.getFilters().add(f1);
        }
        req.setPage(1);
        req.setRowsPerPage(200);

        List<AziendaRSU> l = azRep.find(req).getRows();

        List<UiAziendaRsuAnagraficaSummary> result = new ArrayList<>();
        for (AziendaRSU a: l) {
            UiAziendaRsuAnagraficaSummary ui = convertAziendaRsuToUiAzienda(a);
            result.add(ui);
        }

        if (result.size() > 0){
            Collections.sort(result, new Comparator<UiAziendaRsuAnagraficaSummary>() {
                @Override
                public int compare(final UiAziendaRsuAnagraficaSummary object1, final UiAziendaRsuAnagraficaSummary object2) {
                    return object1.getDescription().compareTo(object2.getDescription());
                }
            } );
        }

        return result;
    }
}
