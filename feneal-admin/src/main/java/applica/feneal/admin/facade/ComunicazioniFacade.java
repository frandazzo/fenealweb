package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.reports.UiComunicazione;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.search.UiComunicazioneReportSearchParams;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportComunicazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class ComunicazioniFacade {

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private ReportComunicazioniService comService;

    public List<UiComunicazione> reportComunicazioni(UiComunicazioneReportSearchParams params){
        List<Comunicazione> comm = comService.retrieveComunicazioni(params);

        return convertComunicazioniToUiComunicazioni(comm);
    }


    private List<UiComunicazione> convertComunicazioniToUiComunicazioni(List<Comunicazione> comm) {
        List<UiComunicazione> result = new ArrayList<>();

        for (Comunicazione comunicazione : comm) {
            UiComunicazione c = new UiComunicazione();

            c.setCommData(comunicazione.getData());
            c.setCommTipo(comunicazione.getTipo().getDescription());
            c.setCommTipoCausale(comunicazione.getCausale().getDescription());
            c.setCommProvince(comunicazione.getProvince().toString());

            c.setLavoratoreId(comunicazione.getLavoratore().getLid());
            c.setLavoratoreCap(comunicazione.getLavoratore().getCap());
            c.setLavoratoreCellulare(comunicazione.getLavoratore().getCellphone());
            c.setLavoratorMail(comunicazione.getLavoratore().getMail());
            c.setLavoratoreTelefono(comunicazione.getLavoratore().getPhone());
            c.setLavoratoreCittaResidenza(comunicazione.getLavoratore().getLivingCity());
            c.setLavoratoreCodiceFiscale(comunicazione.getLavoratore().getFiscalcode());
            c.setLavoratoreCognome(comunicazione.getLavoratore().getSurname());

            c.setLavoratoreDataNascita(comunicazione.getLavoratore().getBirthDate());
            c.setLavoratoreIndirizzo(comunicazione.getLavoratore().getAddress());
            c.setLavoratoreLuogoNascita(comunicazione.getLavoratore().getBirthPlace());
            c.setLavoratoreNazionalita(comunicazione.getLavoratore().getNationality());
            c.setLavoratoreNome(comunicazione.getLavoratore().getName());
            c.setLavoratoreCittaResidenza(comunicazione.getLavoratore().getLivingCity());
            c.setLavoratoreProvinciaNascita(comunicazione.getLavoratore().getBirthProvince());
            c.setLavoratoreProvinciaResidenza(comunicazione.getLavoratore().getLivingProvince());
            if (comunicazione.getLavoratore().getSex().equals("MASCHIO"))
                c.setLavoratoreSesso(Lavoratore.MALE);
            else
                c.setLavoratoreSesso(Lavoratore.FEMALE);

            c.setLavoratoreCodiceCassaEdile(comunicazione.getLavoratore().getCe());
            c.setLavoratoreCodiceEdilcassa(comunicazione.getLavoratore().getEc());
            if (comunicazione.getLavoratore().getFund() != null)
                c.setLavoratoreFondo(comunicazione.getLavoratore().getFund().getDescription());
            c.setLavoratoreNote(comunicazione.getLavoratore().getNotes());

            result.add(c);
        }

        return result;
    }

    public ListaLavoro createListalavoro(List<UiComunicazione> comunicazioni, String description) throws Exception {
        List<Comunicazione> com = convertUiToComunicazione(comunicazioni);
        return lSrv.createListaFromComunicazioni(com,description);
    }

    private List<Comunicazione> convertUiToComunicazione(List<UiComunicazione> comunicazioni) {
        List<Comunicazione> a = new ArrayList<>();

        for (UiComunicazione uiComunicazione : comunicazioni) {
            Comunicazione s = new Comunicazione();
            s.setLavoratore(lavRep.get(uiComunicazione.getLavoratoreId()).orElse(null));

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

    public ListaLavoro createListalavoroFromNotification(long notificationId, String type) throws Exception {
        return lSrv.createListaLavoroFromNotification(notificationId, type);
    }
}
