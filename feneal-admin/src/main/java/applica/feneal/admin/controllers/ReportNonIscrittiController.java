package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.LiberiFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalStateFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.app.dashboard.report.UiAppReportNonIscrittiInput;
import applica.feneal.admin.viewmodel.lavoratori.UiLiberiRichiediInfo;
import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.admin.viewmodel.reports.UiRequestInfoAiTerritori;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
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
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.HtmlFieldRenderer;
import applica.framework.widgets.fields.renderers.MailFieldRenderer;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fgran on 15/04/2016.
 */
@Controller
public class ReportNonIscrittiController {


    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private ParitheticRepository paritheticRepository;

    @Autowired
    private LiberiFacade liberiReportFac;

    @RequestMapping(value="/liberi/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberi(@RequestBody LiberoReportSearchParams params){
        List<UiLibero> f;
        try{
            f = liberiReportFac.reportNonIscritti(params);
            manageActivityReportNonIscritti(params, "Report non iscritti", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }


    }

    @RequestMapping(value="/liberi/reportnew", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberinew(@RequestBody LiberoReportSearchParams params){
        List<UiLibero> f;
        try{
            f = liberiReportFac.reportNonIscrittiNew(params);
            manageActivityReportNonIscritti(params, "Report non iscritti", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }


    }

    @RequestMapping(value="/liberi/reportforapp", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberi(@RequestBody UiAppReportNonIscrittiInput params){

        try{
            return new ValueResponse(liberiReportFac.reportNonIscrittiForApp(params));
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value="/liberi/info/{withOrWithoutInfo}/{province}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberiInfo(@RequestBody  List<UiLibero> list, @PathVariable int withOrWithoutInfo,@PathVariable String province  ){

        try{
            //qui devo calcolare il numero di iscritti che hanno una comunicazione
            List<UiLibero> res = liberiReportFac.completeWithInfoOnRichiesteAiTerritori(list, withOrWithoutInfo, province);
            return new ValueResponse(res);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }


    }

    @RequestMapping(value = "/liberi",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("liberireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggdUserExclusiveProvicesNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("signedTo", String.class, "Iscritto a", null, applicationContext.getBean(SignedToSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("firm", String.class, "Azienda", null, applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt55")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("nationality", String.class, "Nazionalità", "Selezione dati utente", applicationContext.getBean(OptionalStateFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("livingProvince", String.class, "Prov. resid.", "Selezione dati utente", applicationContext.getBean(OptionalProvinceFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("livingCity", String.class, "Comune resid.", "Selezione dati utente", applicationContext.getBean(OptionalCityFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report liberi");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/liberinew",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchviewnew(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("liberireportnew");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggdUserRegionalProvicesNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, " ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report liberi");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/liberi/richiediinfoterritori", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse requestInfoView(@RequestBody UiLiberiRichiediInfo uiRichiediInfo) {

        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("requestinfo");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("province", String.class, "", null, applicationContext.getBean(HiddenFieldRenderer.class));
            formDescriptor.addField("destinatario", String.class, "Destinatario", null, applicationContext.getBean(MailFieldRenderer.class));
            formDescriptor.addField("htmlLiberi", String.class, "", null, applicationContext.getBean(HtmlFieldRenderer.class));

            Map<String, Object> data = new HashMap<>();
            data.put("province", uiRichiediInfo.getProvince());
            data.put("htmlLiberi", renderLiberiTable(uiRichiediInfo.getSelectedLiberi()));
            form.setData(data);

            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());
            response.setTitle("Richiesta info ai territori");

            return response;


        }catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/liberi/sendrichiediinfo", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse sendRichiediInfo(@RequestBody UiRequestInfoAiTerritori info) throws Exception {


        liberiReportFac.sendRequestInfoLiberiMail(info.getDestinatario(), info.getHtmlLiberi(), info.getProvince(),info.getSelectedRows());

        return new ValueResponse("OK");

    }



    /* Nell'editor HTML della 'richiesta info ai territori' renderizzo una tabella
        con indicati i lavoratori selezionati e iscrizioni
     */
    private String renderLiberiTable(List<UiLibero> selectedLiberi) {

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String htmlTable = "<p style='margin-bottom: 10px;'>Si prega cortesemente di dare un messaggio sui seguenti lavoratori:</p>" +

                "<table class='tableSelLiberi' style='border: 1px solid #ddd'>" +
                "<tr style='border: 1px solid #ddd'>" +
                "<th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Lavoratore</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Azienda</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Cod. fiscale</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Data nascita</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Comune nascita</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Comune residenza</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Cellulare</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Indirizzo</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>CAP</th>" +
                "</tr>";

        for (UiLibero uiLibero : selectedLiberi) {

            htmlTable += "<tr style='border: 1px solid #ddd; width: 100%;'>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreCognome() + " " + uiLibero.getLavoratoreNome() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getAziendaRagioneSociale() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreCodiceFiscale() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + df.format(uiLibero.getLavoratoreDataNascita()) + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreLuogoNascita() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreCittaResidenza() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreCellulare() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreIndirizzo() + "</td>" +
                    "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + uiLibero.getLavoratoreCap() + "</td>" +
                    "</tr>" ;

            htmlTable += "<tr style='border: 1px solid #ddd'>" +
                    "<td colspan='9' style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Iscrizioni" +
                    "<table class='tableSelLiberi' style='border: 1px solid #ddd; width: 100%;'>" +
                    "<tr style='border: 1px solid #ddd'>" +
                    "<th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Regione</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Provincia</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Settore</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Ente</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Periodo</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Anno</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Azienda</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Piva</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Livello</th><th style='border: 1px solid #ddd; text-align: center; padding: 5px;'>Quota</th>" +
                    "</tr>";
            for (UiIscrizione iscrizione : uiLibero.getIscrizioni()) {

                htmlTable += "<tr style='border: 1px solid #ddd'>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getNomeRegione() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getNomeProvincia() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getSettore() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getEnte() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getPeriodo() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getAnno() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getAzienda() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getPiva() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getLivello() + "</td>" +
                        "<td style='border: 1px solid #ddd; text-align: center; padding: 5px;'>" + iscrizione.getQuota() + "</td>" +
                        "</tr>";
            }

            htmlTable +=       "</table>" +
                    "</td>" +
                    "</tr>";
        }

        User user = (User) Security.withMe().getLoggedUser();

        htmlTable += "</table>" +
                "<p style='margin-top: 10px;'>Rimango in attesa di un riscontro.<br>Saluti,</p>" +
                "<p>" + user.getCompleteName() + "<br>" + user.getCompany() + "</p>";

        return htmlTable;
    }

    @RequestMapping(value = "/liberi/retrievefilestampa", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse retrievePathFileStampa(@RequestBody List<UiLibero> rows) throws Exception {
        try{
            String pathFile = liberiReportFac.printComplete(rows);
            return new ValueResponse(pathFile);
        } catch(Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/liberi/retrievefilestampa/{type}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse retrievePathFileStampa(@RequestBody List<UiLibero> rows, @PathVariable String type) throws Exception {
        try{
            String pathFile = liberiReportFac.printComplete(rows, type);
            return new ValueResponse(pathFile);
        } catch(Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/liberi/print", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public void printLiberi(String path, HttpServletResponse response) {
        try {
            liberiReportFac.downloadFileLiberi(path, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }

    }


    private void manageActivityReportNonIscritti(LiberoReportSearchParams params, String activityName, List<UiLibero> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String provDescr = "";
            if (!StringUtils.isEmpty(params.getProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getProvince()));
                if (c != null)
                    provDescr = c.getDescription();
            }

            String nationalityDescr = "";
            if (!StringUtils.isEmpty(params.getNationality())) {
                Country c = geoSvc.getCountryById(Integer.parseInt(params.getNationality()));
                if (c != null)
                    nationalityDescr = c.getDescription();
            }

            String livingProvDescr = "";
            if (!StringUtils.isEmpty(params.getLivingProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getLivingProvince()));
                if (c != null)
                    livingProvDescr = c.getDescription();
            }

            String comuneDescr = "";
            if (!StringUtils.isEmpty(params.getLivingCity())) {
                City c = geoSvc.getCityById(Integer.parseInt(params.getLivingCity()));
                if (c != null)
                    comuneDescr = c.getDescription();
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

            traceFacade.traceActivity(user, activityName, createDetailForReportNonIscritti(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Iscritto a: %s; Ente: %s; Azienda: %s; Nazionalità: %s; Prov resid: %s; Comune resid: %s",
                            provDescr,
                            params.getSignedTo(),
                            enteDescr,
                            azDescr,
                            nationalityDescr,
                            livingProvDescr,
                            comuneDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportNonIscritti(List<UiLibero> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti lavoratori: <br><br>");

        for (UiLibero lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }


}





