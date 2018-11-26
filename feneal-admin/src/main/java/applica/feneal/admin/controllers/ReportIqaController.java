package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.IqaFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.servizi.search.UiIqaReportSearchParams;
import applica.feneal.services.AziendaService;
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
 * Created by fgran on 15/04/2016.
 */
@Controller
public class ReportIqaController {
    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private IqaFacade iqaReportFac;

    @RequestMapping(value="/iqa/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportIqa(@RequestBody UiIqaReportSearchParams params){
        List<UiDettaglioQuota> f;
        try{
            f = iqaReportFac.reportQuote(params);
            manageActivityReportIqa(params, "Report quote", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/iqa",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("iqareport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceStringSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("typeQuoteCash", String.class, " ", null, applicationContext.getBean(TypeQuoteCashFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("dataDoc", String.class, "Da", "Data documento", applicationContext.getBean(OptionalDateFromMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("dataDoc", String.class, "a", "Data documento", applicationContext.getBean(OptionalDateToMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("competence", String.class, "Da", "Competenza", applicationContext.getBean(OptionalDateFromMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("competence", String.class, "a", "Competenza", applicationContext.getBean(OptionalDateToMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("sector", String.class, "Settore", "Filtri", applicationContext.getBean(SectorTypeWithoutInpsSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("parithetic", String.class, "Ente", "Filtri", applicationContext.getBean(ParitethicListFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("firm", String.class, "Azienda", "Filtri", applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, "  ");
            /*formDescriptor.addField("collaborator", String.class, "Collabor.", "Filtri", applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");*/



            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report incassi quote");
            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    private void manageActivityReportIqa(UiIqaReportSearchParams params, String activityName, List<UiDettaglioQuota> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String azDescr = "";
            if (!StringUtils.isEmpty(params.getFirm())) {
                Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), Long.parseLong(params.getFirm()));
                if (az != null)
                    azDescr = az.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportIqa(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Tipo quota: %s; Data documento: da %s %s a %s %s; Competenza: da %s %s a %s %s; Settore: %s; Ente: %s; Azienda: %s",
                            params.getProvince(),
                            params.getTypeQuoteCash(),
                            StringUtils.isEmpty(params.getDataDocfromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDataDocfromMonthReport()) - 1),
                            params.getDataDocfromYearReport(),
                            StringUtils.isEmpty(params.getDataDoctoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDataDoctoMonthReport()) - 1),
                            params.getDataDoctoYearReport(),
                            StringUtils.isEmpty(params.getCompetencefromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getCompetencefromMonthReport()) - 1),
                            params.getCompetencefromYearReport(),
                            StringUtils.isEmpty(params.getCompetencetoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getCompetencetoMonthReport()) - 1),
                            params.getCompetencetoYearReport(),
                            params.getSector(),
                            params.getParithetic(),
                            azDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportIqa(List<UiDettaglioQuota> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiDettaglioQuota lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }
}

