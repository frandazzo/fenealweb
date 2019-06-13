package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.reports.UiComunicazione;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.search.UiComunicazioneReportSearchParams;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.services.ComunicazioniService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportComunicazioniService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private ComunicazioniService comunicazioniService;

    public List<UiComunicazione> reportComunicazioni(UiComunicazioneReportSearchParams params){
        List<Comunicazione> comm = comService.retrieveComunicazioni(params);

        return convertComunicazioniToUiComunicazioni(comm);
    }


    private List<UiComunicazione> convertComunicazioniToUiComunicazioni(List<Comunicazione> comm) {
        List<UiComunicazione> result = new ArrayList<>();

        for (Comunicazione comunicazione : comm) {
            UiComunicazione c = new UiComunicazione();

            String mess = comunicazione.getOggetto();
            String link = findLinkInMessage(mess);

            String htmlText = "<a href="+"\""+link+"\""+" target=\"_blank\">" + link + "</a>";

            if (!StringUtils.isEmpty(link))
                mess = mess.replace(link, htmlText);


            c.setCommOggetto(mess);
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

    private String findLinkInMessage(String mess) {
        String links = "";

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mess);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(")&& urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links= urlStr;
        }
        return links;
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

    public List<UiComunicazione> getAllWorkerComunicazioni(long workerId) {
        List<Comunicazione> delegas = comunicazioniService.getAllWorkerComunicazioni(workerId);
        List<UiComunicazione> uiDelegaFormEntities = new ArrayList<>();
        for (Comunicazione delega: delegas) {
            uiDelegaFormEntities.add(getUIComunicazioniFromModelEntity(delega));
        }
        return uiDelegaFormEntities;
    }

    private UiComunicazione getUIComunicazioniFromModelEntity(Comunicazione comunicazione) {
        UiComunicazione uiCom = new UiComunicazione();
        uiCom.setLavoratoreCodiceFiscale(comunicazione.getLavoratore().getFiscalcode());
        uiCom.setCommTipo(comunicazione.getTipo().getDescription());
        uiCom.setCommData(comunicazione.getData());
        if(comunicazione.getCausale() != null)
            uiCom.setCommTipoCausale(comunicazione.getCausale().getDescription());
        if(comunicazione.getProvince() != null)
            uiCom.setCommProvince(comunicazione.getProvince().getDescription());

        return uiCom;
    }

}
