package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.DelegheFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.reports.UiDelega;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.UiDelegheReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.AziendaService;
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
 * Created by fgran on 14/04/2016.
 */
@Controller
public class ReportDelegheController {

    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private DelegheFacade delFac;

    @Autowired
    private ParitheticRepository paritheticRepository;

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @RequestMapping(value="/deleghe/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportDeleghe(@RequestBody UiDelegheReportSearchParams params){

        try {
            List<UiDelega> f = delFac.reportDeleghe(params);
            manageActivityReportDeleghe(params, "Report deleghe", f);
            return new ValueResponse(f);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/deleghe",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("deleghereport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            User u = ((User) security.getLoggedUser());
            if(u.getUsername().equals("fenealmilanolodipavia")){
                formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserDelegheLombardiaOptionalSelectFIeldRenderer.class))
                        .putParam(Params.COLS, Values.COLS_12)
                        .putParam(Params.ROW, "dt")
                        .putParam(Params.FORM_COLUMN, " ");
            }else if(u.getUsername().equals("fenealsassari")) {
                formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserDelegheLombardiaOptionalSelectFIeldRenderer.class))
                        .putParam(Params.COLS, Values.COLS_12)
                        .putParam(Params.ROW, "dt")
                        .putParam(Params.FORM_COLUMN, " ");
            }
            else{
                    formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceSelectFieldRenderer.class))
                            .putParam(Params.COLS, Values.COLS_12)
                            .putParam(Params.ROW, "dt")
                            .putParam(Params.FORM_COLUMN, " ");
            }




            formDescriptor.addField("sector", String.class, "Settore", null, applicationContext.getBean(SectorTypeWithoutInpsSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("firm", String.class, "Azienda", null, applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("collaborator", String.class, "Collabor.", null, applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("causaleIscrizione", String.class, "Caus. sottosc.", null, applicationContext.getBean(CausaleIscrizSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("subscribed", Boolean.class, "Sottoscritta", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("subscribedFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("subscribedToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("sent", Boolean.class, "Inoltrata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("sentFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("sentToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("accepted", Boolean.class, "Accettata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("acceptedFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("acceptedToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("activated", Boolean.class, "Attivata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("activatedFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("activatedToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("cancelled", Boolean.class, "Annullata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt10")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("cancelledFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt10")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("cancelledToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt10")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("revoked", Boolean.class, "Revocata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("revokedFromDate", String.class, "Da", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("revokedToDate", String.class, "a", null, applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt9")
                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report deleghe");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    private void manageActivityReportDeleghe(UiDelegheReportSearchParams params, String activityName, List<UiDelega> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String provDescr = "";
            if (!StringUtils.isEmpty(params.getProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getProvince()));
                if (c != null)
                    provDescr = c.getDescription();
            }

            String azDescr = "";
            if (!StringUtils.isEmpty(params.getFirm())) {
                Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), Long.parseLong(params.getFirm()));
                if (az != null)
                    azDescr = az.getDescription();
            }

            String enteDescr = "";
            if (!StringUtils.isEmpty(params.getParithetic())) {
                Paritethic ente  = paritheticRepository.get(Long.parseLong(params.getParithetic())).orElse(null);
                if (ente != null)
                    enteDescr = ente.getType();
            }

            String collabDescr = "";
            if (!StringUtils.isEmpty(params.getCollaborator())) {
                Collaboratore collab  = collaboratorRepository.get(Long.parseLong(params.getCollaborator())).orElse(null);
                if (collab != null)
                    collabDescr = collab.getDescription();
            }

            String causSottoscDescr = "";
            if (!StringUtils.isEmpty(params.getCausaleIscrizione())) {
                CausaleIscrizioneDelega caus  = causaleIscrizioneDelegaRepository.get(Long.parseLong(params.getCausaleIscrizione())).orElse(null);
                if (caus != null)
                    causSottoscDescr = caus.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportDeleghe(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Settore: %s; Ente: %s; Azienda: %s; Collaboratore: %s; Causale sottoscrizione: %s; Sottoscritta: da %s a %s; Inoltrata: da %s a %s; Accettata: da %s a %s; Attivata: da %s a %s; Annullata: da %s a %s; Revocata: da %s a %s;",
                            provDescr,
                            params.getSector(),
                            enteDescr,
                            azDescr,
                            collabDescr,
                            causSottoscDescr,
                            params.getSubscribedFromDate(),
                            params.getSubscribedToDate(),
                            params.getSentFromDate(),
                            params.getSentToDate(),
                            params.getAcceptedFromDate(),
                            params.getAcceptedToDate(),
                            params.getActivatedFromDate(),
                            params.getActivatedToDate(),
                            params.getCancelledFromDate(),
                            params.getCancelledToDate(),
                            params.getRevokedFromDate(),
                            params.getRevokedToDate()));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportDeleghe(List<UiDelega> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiDelega lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }

}
