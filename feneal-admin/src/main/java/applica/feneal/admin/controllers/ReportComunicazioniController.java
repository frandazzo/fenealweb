package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.ComunicazioniFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.reports.UiComunicazione;
import applica.feneal.domain.data.core.CommunicationReasonRepository;
import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.servizi.search.UiComunicazioneReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.feneal.domain.model.setting.TipoComunicazione;
import applica.feneal.services.GeoService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Controller
public class ReportComunicazioniController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ComunicazioniFacade comunicazioniReportFac;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private CommunicationTypeRepository commRepository;

    @Autowired
    private CommunicationReasonRepository causaleRepository;

    @Autowired
    private TraceFacade traceFacade;

    @RequestMapping(value="/comunicazioni/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportComunicazioni(@RequestBody UiComunicazioneReportSearchParams params){
        List<UiComunicazione> f;
        try{
            f = comunicazioniReportFac.reportComunicazioni(params);
            manageActivityReportComunicazioni(params, "Report comunicazioni", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/comunicazioni",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("comunicazionireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("date", String.class, "Da", null, applicationContext.getBean(DateFromMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("date", String.class, "a", null, applicationContext.getBean(DateToMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("typeCommunication", String.class, "Tipo com.", null, applicationContext.getBean(OptionalTipoComunicazioneFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("causaleComunicazione", String.class, "Causale com.", null, applicationContext.getBean(OptionalCausaleComunicazioneFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report comunicazioni");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    private void manageActivityReportComunicazioni(UiComunicazioneReportSearchParams params, String activityName, List<UiComunicazione> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String provDescr = "";
            if (!StringUtils.isEmpty(params.getProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getProvince()));
                if (c != null)
                    provDescr = c.getDescription();
            }

            String typeComDescr = "";
            if (!StringUtils.isEmpty(params.getTypeCommunication())) {
                TipoComunicazione typeCom  = commRepository.get(Long.parseLong(params.getTypeCommunication())).orElse(null);
                if (typeCom != null)
                    typeComDescr = typeCom.getDescription();
            }

            String causaleComDescr = "";
            if (!StringUtils.isEmpty(params.getCausaleComunicazione())) {
                CausaleComunicazione causaleCom  = causaleRepository.get(Long.parseLong(params.getCausaleComunicazione())).orElse(null);
                if (causaleCom != null)
                    causaleComDescr = causaleCom.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportComunicazioni(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Da: %s %s; A: %s %s; Tipo comunicazione: %s; Causale comunicazione: %s",
                            provDescr,
                            StringUtils.isEmpty(params.getDatefromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatefromMonthReport()) - 1),
                            params.getDatefromYearReport(),
                            StringUtils.isEmpty(params.getDatetoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatetoMonthReport()) - 1),
                            params.getDatetoYearReport(),
                            typeComDescr,
                            causaleComDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    private String createDetailForReportComunicazioni(List<UiComunicazione> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiComunicazione lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }

}


