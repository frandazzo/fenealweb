package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.DocumentiFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.reports.UiDocumento;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.DocumentTypeRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.domain.model.setting.TipoDocumento;
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
public class ReportDocumentiController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DocumentiFacade documentiReportFac;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private DocumentTypeRepository docRepository;

    @Autowired
    private TraceFacade traceFacade;

    @RequestMapping(value="/documenti/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportDocumenti(@RequestBody UiDocumentoReportSearchParams params){
        List<UiDocumento> f;
        try{
            f = documentiReportFac.reportDocumenti(params);
            manageActivityReportDocumenti(params, "Report documenti", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/documenti",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("documentireport");

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
            formDescriptor.addField("typeDoc", String.class, "Tipo documento", null, applicationContext.getBean(OptionalTipoDocumentoFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("collaborator", String.class, "Collabor.", null, applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report documenti");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    private void manageActivityReportDocumenti(UiDocumentoReportSearchParams params, String activityName, List<UiDocumento> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String provDescr = "";
            if (!StringUtils.isEmpty(params.getProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getProvince()));
                if (c != null)
                    provDescr = c.getDescription();
            }

            String collabDescr = "";
            if (!StringUtils.isEmpty(params.getCollaborator())) {
                Collaboratore collab  = collaboratorRepository.get(Long.parseLong(params.getCollaborator())).orElse(null);
                if (collab != null)
                    collabDescr = collab.getDescription();
            }

            String typeDocDescr = "";
            if (!StringUtils.isEmpty(params.getTypeDoc())) {
                TipoDocumento typeDoc  = docRepository.get(Long.parseLong(params.getTypeDoc())).orElse(null);
                if (typeDoc != null)
                    typeDocDescr = typeDoc.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportDocumenti(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Da: %s %s; A: %s %s; Tipo documento: %s; Collaboratore: %s",
                            provDescr,
                            StringUtils.isEmpty(params.getDatefromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatefromMonthReport()) - 1),
                            params.getDatefromYearReport(),
                            StringUtils.isEmpty(params.getDatetoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatetoMonthReport()) - 1),
                            params.getDatetoYearReport(),
                            typeDocDescr,
                            collabDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    private String createDetailForReportDocumenti(List<UiDocumento> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiDocumento lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }

}


