package applica.feneal.admin.facade.RSU;

import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.aziende.UiAziendaAnagraficaSummary;
import applica.feneal.admin.viewmodel.aziende.UiCompleteAziendaSummary;
import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;



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

        if(anag.getId() != 0){
            az.setCompanyCreator(anag.getCompanyCreator());
            az.setCreateDate(new SimpleDateFormat("dd/MM/yyyy").parse(anag.getCreateDate()));
            az.setUsernameCreator(anag.getUsernameCreator());
        }

        return az;
    }

    public UiCompleteAziendaSummary getFirmById(long id) {
        AziendaRSU az = svc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),id);

        UiCompleteAziendaSummary s = new UiCompleteAziendaSummary();

        UiAziendaAnagraficaSummary anag = convertAziendaRsuToUiAzienda(az);

        s.setData(anag);

        return s;
    }

    private UiAziendaAnagraficaSummary convertAziendaRsuToUiAzienda(AziendaRSU az) {
        UiAziendaAnagraficaSummary s = new UiAziendaAnagraficaSummary();

        s.setId(az.getLid());
        s.setDescription(az.getDescription());
        s.setCity(az.getCity());
        s.setProvince(az.getProvince());
        s.setCap(az.getCap());
        s.setAddress(az.getAddress());
        s.setNotes(az.getNotes());
        s.setPhone(az.getPhone());
        s.setPiva(az.getPiva());

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


}
