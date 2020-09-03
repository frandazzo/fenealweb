package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiAppDelega;
import applica.feneal.admin.viewmodel.deleghe.UiMagazzino;
import applica.feneal.admin.viewmodel.reports.UiMagazzinoDeleghe;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheSearchParams;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportMagazzinoDelegheService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by angelo on 05/05/16.
 */
@Component
public class MagazzinoDelegheFacade {

    @Autowired
    private ReportMagazzinoDelegheService magazDelService;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private MagazzinoDelegheRepository magazzDelRep;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private ParitheticRepository parRep;

    @Autowired
    private CollaboratorRepository collabRep;


    public List<UiMagazzinoDeleghe> magazzinoDeleghe(UiMagazzinoDelegheSearchParams params){
        List<MagazzinoDelega> del = magazDelService.retrieveMagazzinoDeleghe(params);

        return convertDelegheToUiDeleghe(del);
    }


    private List<UiMagazzinoDeleghe> convertDelegheToUiDeleghe(List<MagazzinoDelega> del) {

        List<UiMagazzinoDeleghe> result = new ArrayList<>();

        for (MagazzinoDelega delega : del) {
            UiMagazzinoDeleghe d = new UiMagazzinoDeleghe();
            d.setDelProvincia(delega.getProvince().getDescription());
            d.setDelId(delega.getLid());
            d.setDelData(delega.getData());
            if (delega.getParitethic() != null)
                d.setDelEnte(delega.getParitethic().toString());
            if (delega.getCollaboratore() != null)
                d.setDelCollaboratore(delega.getCollaboratore().toString());

            d.setOtherparithetics(delega.getOtherparithetics());
            d.setLavoratoreId(delega.getLavoratore().getLid());
            d.setLavoratoreCap(delega.getLavoratore().getCap());
            d.setLavoratoreCellulare(delega.getLavoratore().getCellphone());
            d.setLavoratorMail(delega.getLavoratore().getMail());
            d.setLavoratoreTelefono(delega.getLavoratore().getPhone());
            d.setLavoratoreCittaResidenza(delega.getLavoratore().getLivingCity());
            d.setLavoratoreCodiceFiscale(delega.getLavoratore().getFiscalcode());
            d.setLavoratoreCognome(delega.getLavoratore().getSurname());

            d.setLavoratoreDataNascita(delega.getLavoratore().getBirthDate());
            d.setLavoratoreIndirizzo(delega.getLavoratore().getAddress());
            d.setLavoratoreLuogoNascita(delega.getLavoratore().getBirthPlace());
            d.setLavoratoreNazionalita(delega.getLavoratore().getNationality());
            d.setLavoratoreNome(delega.getLavoratore().getName());
            d.setLavoratoreCittaResidenza(delega.getLavoratore().getLivingCity());
            d.setLavoratoreProvinciaNascita(delega.getLavoratore().getBirthProvince());
            d.setLavoratoreProvinciaResidenza(delega.getLavoratore().getLivingProvince());
            if (delega.getLavoratore().getSex() == null){
                d.setLavoratoreSesso(Lavoratore.MALE);
            }else{
                if (delega.getLavoratore().getSex().equals("MASCHIO"))
                    d.setLavoratoreSesso(Lavoratore.MALE);
                else
                    d.setLavoratoreSesso(Lavoratore.FEMALE);
            }


            d.setLavoratoreCodiceCassaEdile(delega.getLavoratore().getCe());
            d.setLavoratoreCodiceEdilcassa(delega.getLavoratore().getEc());
            if (delega.getLavoratore().getFund() != null)
                d.setLavoratoreFondo(delega.getLavoratore().getFund().getDescription());
            d.setLavoratoreNote(delega.getLavoratore().getNotes());

            result.add(d);
        }

        return result;
    }



    public String generateDeleghe(UiMagazzinoDelegheSearchParams params) throws Exception {
        List<MagazzinoDelega> del = magazDelService.retrieveDistinctMagazzinoDeleghe(params);

        return magazDelService.generateDeleghe(del);
    }

    public void downloadMagazzinoDelegheFile(String pathFile, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(pathFile);

        response.setHeader("Content-Disposition", "attachment;filename=Magazzino_deleghe.xlsx");
        //response.setContentType("application/zip");
        response.setStatus(200);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.close();
    }


    public ListaLavoro createListalavoro(List<UiMagazzinoDeleghe> deleghe, String description) throws Exception {
        List<MagazzinoDelega> com = convertUiToDeleghe(deleghe);
        return lSrv.createListaFromMagazzinoDeleghe(com, description);
    }

    private List<MagazzinoDelega> convertUiToDeleghe(List<UiMagazzinoDeleghe> deleghe) {
        List<MagazzinoDelega> a = new ArrayList<>();

        for (UiMagazzinoDeleghe uiMagazzinoDeleghe : deleghe) {
            MagazzinoDelega s = new MagazzinoDelega();
            s.setLavoratore(lavRep.get(uiMagazzinoDeleghe.getLavoratoreId()).orElse(null));

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

    public String saveDelegaForApp(UiAppDelega delega) throws Exception {

        MagazzinoDelega d = new MagazzinoDelega();
        d.setId(delega.getId());
        d.setData(new Date());
        d.setLavoratore(lavRep.get(delega.getIdLavoratore()).orElse(null));
        d.setProvince(geoSvc.getProvinceByName(delega.getProvincia()));
        d.setParitethic(parRep.find(LoadRequest.build().filter("type", delega.getEnte(), Filter.EQ)).findFirst().orElse(null));
        if(delega.getCollaboratore().isEmpty() || delega.getCollaboratore() == null){
            d.setCollaboratore(null);
        }else {
            d.setCollaboratore(collabRep.get(delega.getCollaboratore()).orElse(null));
        }


        if (d.getLavoratore() == null)
            throw new Exception("Lavoratore nullo");

        if (d.getLavoratore() != null)
            if (d.getLavoratore().getCompanyId() != ((User) Security.withMe().getLoggedUser()).getCompany().getLid())
            throw new Exception("Lavoratore non compatibile con l'utente loggato");


        if (d.getProvince() == null)
            throw new Exception("Provincia nulla");



        if (d.getParitethic() == null)
            throw new Exception("Ente nullo");


        magazzDelRep.save(d);

        return "OK";
    }

    public String deleteDelega(String id) {

        magazzDelRep.delete(id);

        return "OK";
    }

    public void createDeleghe(UiMagazzino magazzino) {

        List<MagazzinoDelega> list = new ArrayList<>();
        Lavoratore lav = lavRep.get(magazzino.getWorkerId()).get();
        Province prov  = geoSvc.getProvinceById(Integer.parseInt(String.valueOf(magazzino.getProvinceId())));

        Paritethic cassaedile = parRep.find(LoadRequest.build().filter("description", "CASSA EDILE")).findFirst().get();
        Paritethic edilcassa = parRep.find(LoadRequest.build().filter("description", "EDILCASSA")).findFirst().get();




        if (magazzino.getTipoEnte().equals("ENTRAMBI")){


            MagazzinoDelega del = new MagazzinoDelega();
            del.setData(new Date());
            del.setProvince(prov);
            del.setLavoratore(lav);
            del.setParitethic(cassaedile);

            MagazzinoDelega del1 = new MagazzinoDelega();
            del1.setData(new Date());
            del1.setProvince(prov);
            del1.setLavoratore(lav);
            del1.setParitethic(edilcassa);

            list.add(del);
            list.add(del1);

        }else if (magazzino.getTipoEnte().equals("CASSA EDILE")){
            MagazzinoDelega del = new MagazzinoDelega();
            del.setData(new Date());
            del.setProvince(prov);
            del.setLavoratore(lav);
            del.setParitethic(cassaedile);
            list.add(del);
        }else{
            MagazzinoDelega del1 = new MagazzinoDelega();
            del1.setData(new Date());
            del1.setProvince(prov);
            del1.setLavoratore(lav);
            del1.setParitethic(edilcassa);
            list.add(del1);
        }

        for (MagazzinoDelega magazzinoDelega : list) {
            magazzDelRep.save(magazzinoDelega);
        }


    }
}
