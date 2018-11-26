package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.reports.UiRichiesta;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.domain.model.core.servizi.search.UiRichiestaReportSearchParams;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportRichiesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class RichiesteFacade {

    @Autowired
    private ReportRichiesteService richService;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private LavoratoriRepository lavRep;


    public List<UiRichiesta> reportRichieste(UiRichiestaReportSearchParams params){
        List<RichiestaInfo> rich = richService.retrieveRichieste(params);

        return convertRichiesteToUiRichieste(rich);

    }

    private List<UiRichiesta> convertRichiesteToUiRichieste(List<RichiestaInfo> rich) {
        List<UiRichiesta> result = new ArrayList<>();

        for (RichiestaInfo richiesta : rich) {
            UiRichiesta r = new UiRichiesta();

            r.setRichData(richiesta.getData());
            r.setRichRecipient(richiesta.getDestinatario());
            r.setRichProvince(richiesta.getRequestToProvince());

            r.setLavoratoreId(richiesta.getLavoratore().getLid());
            r.setLavoratoreCap(richiesta.getLavoratore().getCap());
            r.setLavoratoreCellulare(richiesta.getLavoratore().getCellphone());
            r.setLavoratorMail(richiesta.getLavoratore().getMail());
            r.setLavoratoreTelefono(richiesta.getLavoratore().getPhone());
            r.setLavoratoreCittaResidenza(richiesta.getLavoratore().getLivingCity());
            r.setLavoratoreCodiceFiscale(richiesta.getLavoratore().getFiscalcode());
            r.setLavoratoreCognome(richiesta.getLavoratore().getSurname());

            r.setLavoratoreDataNascita(richiesta.getLavoratore().getBirthDate());
            r.setLavoratoreIndirizzo(richiesta.getLavoratore().getAddress());
            r.setLavoratoreLuogoNascita(richiesta.getLavoratore().getBirthPlace());
            r.setLavoratoreNazionalita(richiesta.getLavoratore().getNationality());
            r.setLavoratoreNome(richiesta.getLavoratore().getName());
            r.setLavoratoreCittaResidenza(richiesta.getLavoratore().getLivingCity());
            r.setLavoratoreProvinciaNascita(richiesta.getLavoratore().getBirthProvince());
            r.setLavoratoreProvinciaResidenza(richiesta.getLavoratore().getLivingProvince());
            if (richiesta.getLavoratore().getSex().equals("MASCHIO"))
                r.setLavoratoreSesso(Lavoratore.MALE);
            else
                r.setLavoratoreSesso(Lavoratore.FEMALE);

            r.setLavoratoreCodiceCassaEdile(richiesta.getLavoratore().getCe());
            r.setLavoratoreCodiceEdilcassa(richiesta.getLavoratore().getEc());
            if (richiesta.getLavoratore().getFund() != null)
                r.setLavoratoreFondo(richiesta.getLavoratore().getFund().getDescription());
            r.setLavoratoreNote(richiesta.getLavoratore().getNotes());

            result.add(r);
        }

        return result;
    }


    public ListaLavoro createListalavoro(List<UiRichiesta> richieste, String description) throws Exception {
        List<RichiestaInfo> com = convertUiToRichieste(richieste);
        return lSrv.createListaFromRichieste(com, description);
    }

    private List<RichiestaInfo> convertUiToRichieste(List<UiRichiesta> richieste) {
        List<RichiestaInfo> a = new ArrayList<>();

        for (UiRichiesta uiRichiesta : richieste) {
            RichiestaInfo s = new RichiestaInfo();
            s.setLavoratore(lavRep.get(uiRichiesta.getLavoratoreId()).orElse(null));

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }
    
}
