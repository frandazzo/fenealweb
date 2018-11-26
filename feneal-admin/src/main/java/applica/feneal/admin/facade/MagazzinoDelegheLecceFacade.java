package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.deleghe.UiMagazzinoLecce;
import applica.feneal.admin.viewmodel.reports.UiMagazzinoDelegheLecce;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheLecceRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheLecceSearchParams;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.domain.model.core.servizi.MagazzinoDelegaLecce;
import applica.feneal.services.GeoService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportMagazzinoDelegheLecceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MagazzinoDelegheLecceFacade {

    @Autowired
    private ReportMagazzinoDelegheLecceService magazDelService;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private MagazzinoDelegheLecceRepository magazzDelRep;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private ParitheticRepository parRep;

    @Autowired
    private CollaboratorRepository collabRep;


    public List<UiMagazzinoDelegheLecce> magazzinoDeleghe(UiMagazzinoDelegheLecceSearchParams params) {
        List<MagazzinoDelegaLecce> del = magazDelService.retrieveMagazzinoDeleghe(params);

        return convertDelegheToUiDeleghe(del);
    }

    private List<UiMagazzinoDelegheLecce> convertDelegheToUiDeleghe(List<MagazzinoDelegaLecce> del) {

        List<UiMagazzinoDelegheLecce> result = new ArrayList<>();

        for (MagazzinoDelegaLecce delega : del) {
            UiMagazzinoDelegheLecce d = new UiMagazzinoDelegheLecce();
            d.setDelProvincia(delega.getProvince().getDescription());
            d.setDelId(delega.getLid());
            d.setDelData(delega.getData());
            if (delega.getParitethic() != null)
                d.setDelEnte(delega.getParitethic().toString());
            if (delega.getCollaboratore() != null)
                d.setDelCollaboratore(delega.getCollaboratore().toString());
            d.setGiorni(delega.getNumGiorniDaSottoscrizione());
            d.setOtherparithetics(delega.getOtherparithetics());
            d.setLavoratoreId(delega.getLavoratore().getLid());
            d.setLavoratoreCap(delega.getLavoratore().getCap());
            d.setLavoratoreCellulare(delega.getLavoratore().getCellphone());
            d.setLavoratorMail(delega.getLavoratore().getMail());
            d.setLavoratoreTelefono(delega.getLavoratore().getPhone());
            d.setLavoratoreCittaResidenza(delega.getLavoratore().getLivingCity());
            d.setLavoratoreCodiceFiscale(delega.getLavoratore().getFiscalcode());
            d.setLavoratoreCognome(delega.getLavoratore().getSurname());
            d.setCausale(delega.getSubscribeReason() != null? delega.getSubscribeReason().getDescription(): "");
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




    public String generateDeleghe(UiMagazzinoDelegheLecceSearchParams params) throws Exception {
        List<MagazzinoDelegaLecce> del = magazDelService.retrieveDistinctMagazzinoDeleghe(params);

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

}
