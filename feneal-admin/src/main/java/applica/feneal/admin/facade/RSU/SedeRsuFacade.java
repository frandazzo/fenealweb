package applica.feneal.admin.facade.RSU;

import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaSedeRsu;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;

import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.validation.RSU.SedeRsuValidator;
import applica.feneal.services.GeoService;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.feneal.services.RSU.SedeRsuService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by felicegramegna on 23/02/2021.
 */

@Component
public class SedeRsuFacade {

    @Autowired
    private Security sec;

    @Autowired
    private SedeRsuService svc;
    @Autowired
    private AziendaRsuService azRsuSrvc;
    @Autowired
    private GeoService geosvc;


    public List<UiAnagraficaSedeRsu> getAllSediAziendaRsu(long firmId) {
        List<SedeRSU> list = svc.getAllSediAziendaRsu(firmId);
        List<UiAnagraficaSedeRsu> result = new ArrayList<>();
        for(SedeRSU s : list){
            result.add(getUiAnagraficaSedeRsuFormSedeRsuEntity(s));
        }
        return result;
    }

    private UiAnagraficaSedeRsu getUiAnagraficaSedeRsuFormSedeRsuEntity(SedeRSU s) {
        UiAnagraficaSedeRsu r = new UiAnagraficaSedeRsu();
        r.setAddress(s.getAddress());
        r.setFirmId(s.getAziendaRSU().getLid());
        r.setId(s.getLid());
        r.setCity(s.getCity() != null ? s.getCity() : "");
        r.setProvince(s.getProvince()  != null ? s.getProvince() : "");
        r.setDescription(s.getDescription());

        return r;
    }

    public void deleteAzienda(long id) {
        svc.delete(((User) sec.getLoggedUser()).getLid(), id);
    }

    public long saveAnagrafica(UiAnagraficaSedeRsu anag) throws Exception {
        SedeRSU s = convertUiAnagraficaToSedeRsu(anag);

        svc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),s);

        return s.getLid();
    }

    private SedeRSU convertUiAnagraficaToSedeRsu(UiAnagraficaSedeRsu anag) throws ParseException {
        SedeRSU az = new SedeRSU();

        az.setId(anag.getId());
        az.setDescription(anag.getDescription());
        az.setAddress(anag.getAddress());
        az.setAziendaRSU(azRsuSrvc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),anag.getFirmId()));




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



        return az;
    }
}
