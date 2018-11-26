package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.app.dashboard.aziende.AppAzienda;
import applica.feneal.admin.viewmodel.app.dashboard.aziende.NonIscrittoAzienda;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.AppLavoratore;
import applica.feneal.admin.viewmodel.aziende.UiAnagraficaAzienda;
import applica.feneal.admin.viewmodel.aziende.UiAziendaAnagraficaSummary;
import applica.feneal.admin.viewmodel.aziende.UiCompleteAziendaSummary;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.AziendaService;
import applica.feneal.services.GeoService;
import applica.feneal.services.LavoratoreService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
@Component
public class AziendeFacade {

    @Autowired
    private Security security;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private AziendaService svc;

    @Autowired
    private GeoService geosvc;

    @Autowired
    private LavoratoreService lavsvc;

    @Autowired
    private LavoratoriFacade lavFac;

    @Autowired
    private LiberiFacade libFac;


    public List<AppAzienda> findAziende(String description){



        LoadRequest req = LoadRequest.build().filter("description", description, Filter.LIKE);
        req.setPage(1);
        req.setRowsPerPage(200);

        List<Azienda> l = azRep.find(req).getRows();

        List<AppAzienda> result = new ArrayList<>();

        for (Azienda azienda : l) {
            AppAzienda r = convertAziendaToAppAzienda(azienda);
            result.add(r);

        }

        if (result.size() > 0){
            Collections.sort(result, new Comparator<AppAzienda>() {
                @Override
                public int compare(final AppAzienda object1, final AppAzienda object2) {
                    return object1.getDescrizione().compareTo(object2.getDescrizione());
                }
            } );
        }

        return result;
    }

    private AppAzienda convertAziendaToAppAzienda(Azienda a) {
        AppAzienda s = new AppAzienda();

        s.setId(String.valueOf(a.getLid()));
        s.setDescrizione(a.getDescription());
        s.setComune(a.getCity());
        s.setProvincia(a.getProvince());
        s.setCap(a.getCap());
        s.setIndirizzo(a.getAddress());
        s.setShowChevron(true);
        s.setVisible(true);
        return s;
    }


    public UiCompleteAziendaSummary getFirmById(long aziendaId) {

        Azienda a =  azRep.get(aziendaId).orElse(null);

        UiCompleteAziendaSummary s = new UiCompleteAziendaSummary();

        UiAziendaAnagraficaSummary anag = convertAziendaToUiAzienda(a);

        s.setData(anag);

        return s;

    }

    private UiAziendaAnagraficaSummary convertAziendaToUiAzienda(Azienda a) {
        UiAziendaAnagraficaSummary s = new UiAziendaAnagraficaSummary();

        s.setId(a.getLid());
        s.setDescription(a.getDescription());
        s.setCity(a.getCity());
        s.setProvince(a.getProvince());
        s.setCap(a.getCap());
        s.setAddress(a.getAddress());
        s.setNotes(a.getNotes());
        s.setPhone(a.getPhone());
        s.setPiva(a.getPiva());

        return s;
    }

    public long saveAnagrafica(UiAnagraficaAzienda anag) throws Exception {
        Azienda l = convertUiAnagraficaToAzienda(anag);

        svc.saveOrUpdate(((User) security.getLoggedUser()).getLid(),l);

        return l.getLid();
    }

    private Azienda convertUiAnagraficaToAzienda(UiAnagraficaAzienda anag) {
        Azienda az = new Azienda();

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

        return az;
    }

    public void deleteAzienda(long id) {
        svc.delete(((User) security.getLoggedUser()).getLid(), id);
    }

    public long getIdAziendaByDescriptionorCreateIfNotExist(String description) throws Exception {
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

        return svc.getAziendaByDescriptionorCreateIfNotExist(description).getLid();
    }

    public AppAzienda getAppAziendaByDescriptionorCreateIfNotExist(String description) throws Exception {
        Azienda a = svc.getAziendaByDescriptionorCreateIfNotExist(description);

        AppAzienda result = convertAziendaToAppAzienda(a);
        //devo adesso calcolare la lista dei non iscritti e degli iscritti

        List<UiLibero> l1 = libFac.convertLiberiToUiLiberi(lavsvc.findNonIscrizioniForAzienda(a.getLid()));
        List<NonIscrittoAzienda> noniscritti = convertToNonIscrittiAzienda(l1);
        result.setNonIscritti(noniscritti);


        List<Lavoratore> l = lavsvc.findCurrentIscrizioniForAzienda(a.getLid());
        List<AppLavoratore> iscritti = convertToAppLavoratori(l);
        result.setIscritti(iscritti);

        return result;
    }

    private List<NonIscrittoAzienda> convertToNonIscrittiAzienda(List<UiLibero> l1) {
        List<NonIscrittoAzienda> result = new ArrayList<>();

        for (UiLibero uiLibero : l1) {
            NonIscrittoAzienda az = convertUiLiberoToNonIscrittoAzienda(uiLibero);
            result.add(az);
        }

        return result;
    }

    private NonIscrittoAzienda convertUiLiberoToNonIscrittoAzienda(UiLibero uiLibero) {
        NonIscrittoAzienda a = new NonIscrittoAzienda();

        a.setAzienda(uiLibero.getAziendaRagioneSociale());
        a.setEnte(uiLibero.getLiberoEnteBilaterale());
        a.setFiscale(uiLibero.getLavoratoreCodiceFiscale());
        a.setIscrittoA(uiLibero.getLiberoIscrittoA());
        a.setLiberoAl(new SimpleDateFormat("dd/MM/yyyy").format(uiLibero.getLiberoData()));
        a.setProvincia(uiLibero.getLiberoProvincia());
        a.setNome(String.format("%s %s", uiLibero.getLavoratoreCognome(), uiLibero.getLavoratoreNome()));
        a.setDelegheOwner(uiLibero.isLavoratoreDelegheOwner());
        if (uiLibero.getNumIscrizioni() == 0)
            a.setBadge("");
        else
            a.setBadge(String.valueOf(uiLibero.getNumIscrizioni()));
        a.setNumIscrizioni(uiLibero.getNumIscrizioni());

        return a;
    }

    private List<AppLavoratore> convertToAppLavoratori(List<Lavoratore> l) {
        List<AppLavoratore> result = new ArrayList<>();
        for (Lavoratore lavoratore : l) {
            AppLavoratore f = lavFac.convertLavoratoreToAppLavoratore(lavoratore);
            result.add(f);
        }
        return result;
    }

    public AppAzienda getAppAziendaById(String id) throws ParseException {
        Azienda a = azRep.get(id).orElse(null);

        AppAzienda result = convertAziendaToAppAzienda(a);
        //devo adesso calcolare la lista dei non iscritti e degli iscritti

        List<UiLibero> l1 = libFac.convertLiberiToUiLiberi(lavsvc.findNonIscrizioniForAzienda(a.getLid()));
        List<NonIscrittoAzienda> noniscritti = convertToNonIscrittiAzienda(l1);
        result.setNonIscritti(noniscritti);


        List<Lavoratore> l = lavsvc.findCurrentIscrizioniForAzienda(a.getLid());
        List<AppLavoratore> iscritti = convertToAppLavoratori(l);
        result.setIscritti(iscritti);

        return result;
    }
}
