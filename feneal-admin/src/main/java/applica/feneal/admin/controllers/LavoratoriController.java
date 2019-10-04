package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.LavoratoriFacade;
import applica.feneal.admin.facade.NotificationsFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.fields.renderers.LoggedUserProvinceNonOptionalSelectFieldRenderer;
import applica.feneal.admin.fields.renderers.RegionalCompaniesOptionalSelectfieldRenderer;
import applica.feneal.admin.fields.renderers.SectorTypeWithoutInpsSelectRenderer;
import applica.feneal.admin.fields.renderers.SexSelectFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalStateFieldRenderer;
import applica.feneal.admin.form.renderers.WorkerSearchFormRenderer;
import applica.feneal.admin.utils.FormUtils;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.AppLavoratore;
import applica.feneal.admin.viewmodel.lavoratori.UiAnagrafica;
import applica.feneal.admin.viewmodel.lavoratori.UiCompleteLavoratoreSummary;
import applica.feneal.admin.viewmodel.lavoratori.UiRequestInfoWorker;
import applica.feneal.admin.viewmodel.lavoratori.UiWorkerIscrizioniChart;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.model.FenealEntities;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.lavoratori.FiscalData;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.search.LavoratoreSearchParams;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.feneal.services.GeoService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.exceptions.FormNotFoundException;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.Security;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 06/04/2016.
 */
@Controller
public class LavoratoriController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private FormUtils formUtils;

    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LavoratoriFacade tcFacade;

    @Autowired
    private LavoratoreService svc;

    @Autowired
    private NotificationsFacade notFacade;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private WidgetFacade widgetFacade;

    @Autowired

    private ApplicationOptionRepository appRep;


    //ogni minuto
    @Scheduled(fixedDelay=120 * 60000)
    public void updatecellphones() {


        svc.updateCellsForAll();


    }



    @RequestMapping(value="/localworkersnew", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody  SimpleResponse findLocalLavoratorinew(@RequestParam(value="name", required=false, defaultValue="") String name,
                                                            @RequestParam(value="surname", required=false, defaultValue="") String surname,
                                                            @RequestParam(value="fiscalcode", required=false, defaultValue="") String fiscalcode,
                                                            @RequestParam(value="namesurname", required=false, defaultValue="") String namesurname,
                                                            @RequestParam(value="page", required=false) Integer page,
                                                            @RequestParam(value="company", required=false) String company ,
                                                            @RequestParam(value="cell", required=false, defaultValue="") String cell){


        //se mnon cÃ¨ una pagina la imposto alla prima pagina

        if (page == null)
            page = 1;

        if (page == 0)
            page = 1;


        try {

            LavoratoreSearchParams s = prepareSearchParams(name, surname, fiscalcode, namesurname, page, cell);
            s.setCompany(company);

            List<Lavoratore> lavs = tcFacade.findLavoratoriMultiTerritorioRegionale(s);

            manageActivityLocalWorkers(name, surname, fiscalcode, namesurname, "Ricerca locale iscritti", lavs);

            return new ValueResponse(lavs);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    private LavoratoreSearchParams prepareSearchParams(@RequestParam(value = "name", required = false, defaultValue = "") String name, @RequestParam(value = "surname", required = false, defaultValue = "") String surname, @RequestParam(value = "fiscalcode", required = false, defaultValue = "") String fiscalcode, @RequestParam(value = "namesurname", required = false, defaultValue = "") String namesurname, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "cell", required = false, defaultValue = "") String cell) {
        LavoratoreSearchParams s = new LavoratoreSearchParams();
        s.setName(name);
        s.setSurname(surname);
        s.setFiscalcode(fiscalcode);
        s.setNamesurname(namesurname);
        s.setCell(cell);
        s.setPage(page);
        return s;
    }

    @RequestMapping(value="/localworkers", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse findLocalLavoratori(@RequestParam(value="name", required=false, defaultValue="") String name,
                                                            @RequestParam(value="surname", required=false, defaultValue="") String surname,
                                                            @RequestParam(value="fiscalcode", required=false, defaultValue="") String fiscalcode,
                                                            @RequestParam(value="namesurname", required=false, defaultValue="") String namesurname,
                                                            @RequestParam(value="page", required=false) Integer page,
                                                            @RequestParam(value="company", required=false) String company ,
                                                            @RequestParam(value="cell", required=false, defaultValue="") String cell){


        ApplicationOptions opt = appRep.find(null).findFirst().orElse(null);
        if (opt != null && opt.getFenealwebRegionale() != null && opt.getFenealwebRegionale() == true)
            return findLocalLavoratorinew(name, surname, fiscalcode,namesurname, page ,company,cell);

        if (page == null)
            page = 1;

        if (page == 0)
            page = 1;


        try {

            LavoratoreSearchParams s = prepareSearchParams(name, surname, fiscalcode, namesurname, page, cell);

            List<Lavoratore> lavs = tcFacade.findLocalLavoratori(s);

            manageActivityLocalWorkers(name, surname, fiscalcode, namesurname, "Ricerca locale iscritti", lavs);

            return new ValueResponse(lavs);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }




    @RequestMapping(value="/localworkersforapp", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse findLocalLavoratori1(@RequestParam(value="name", required=false, defaultValue="") String name,
                                                            @RequestParam(value="surname", required=false, defaultValue="") String surname,
                                                            @RequestParam(value="fiscalcode", required=false, defaultValue="") String fiscalcode){



        try {

            LavoratoreSearchParams s = new LavoratoreSearchParams();
            s.setName(name);
            s.setSurname(surname);
            s.setFiscalcode(fiscalcode);
            s.setNamesurname(null);
            s.setPage(1);

            List<Lavoratore> lavs = tcFacade.findLocalLavoratori(s);

            //trasformo il risultato in viewmodel per l'app
            List<AppLavoratore> result = new ArrayList<>();


            for (Lavoratore lav : lavs) {
                AppLavoratore l = new AppLavoratore();
                l.setMail(lav.getMail());
                l.setCap(lav.getCap());
                l.setCellulare(lav.getCellphone());
                l.setCognome(lav.getSurname());
                l.setComuneNascita(lav.getBirthPlace());
                l.setComuneResidenza(lav.getLivingCity());
                SimpleDateFormat gg = new SimpleDateFormat("dd/MM/yyyy");
                l.setDataNascita(gg.format(lav.getBirthDate()));
                l.setFiscale(lav.getFiscalcode());
                l.setId(String.valueOf(lav.getLid()));
                l.setIndirizzo(lav.getAddress());
                l.setNazione(lav.getNationality());
                l.setNome(lav.getName());
                l.setProvinciaNascita(lav.getBirthProvince());
                l.setProvinciaResidenza(lav.getLivingProvince());
                l.setSesso(lav.getSex());
                l.setTelefono(lav.getPhone());
                l.setShowChevron(true);
                result.add(l);
            }

            return new ValueResponse(result);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/wtelefoni/{fiscal}", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse findRemoteLavoratori(@PathVariable String fiscal ) {



        try {



            return new ValueResponse(svc.getNumeriTelefono(fiscal));

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }






    /* Ricerca dei lavoratori da DB nazionale */
    @RequestMapping(value="/remoteworkers", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse findRemoteLavoratori(/*@RequestParam(value="year", required=false) String year,
                                                             @RequestParam(value="sector", required=false) Long sectorId,
                                                             @RequestParam(value="company", required=false) Long companyId,*/
                                                             @RequestParam(value="nameDB", required=false) String name,
                                                             @RequestParam(value="surnameDB", required=false) String surname,
                                                             @RequestParam(value="fiscalcodeDB", required=false) String fiscalcode,
                                                             @RequestParam(value="sex", required=false) String sex,
                                                             @RequestParam(value="birthDate", required=false) String birthDate,
                                                             @RequestParam(value="nationality", required=false) String nationality,
                                                             @RequestParam(value="livingProvince", required=false) String livingProvince,
                                                             @RequestParam(value="livingCity", required=false) String livingCity,
                                                             @RequestParam(value="page", required=false) Integer page) {



        //se mnon cÃ¨ una pagina la imposto alla prima pagina

        if (page == null)
            page = 1;

        if (page == 0)
            page = 1;

        try {

            LavoratoreSearchParams s = new LavoratoreSearchParams();

           /* s.setYear(year);
            s.setSectorId(sectorId);
            s.setCompanyId(companyId); */
            s.setName(name);
            s.setSurname(surname);
            s.setFiscalcode(fiscalcode);
            s.setSex(sex);
            s.setBirthDate(birthDate);
            s.setNationality(nationality);
            s.setLivingProvince(livingProvince);
            s.setLivingCity(livingCity);
            s.setPage(page);

            List<UtenteDbNazionale> lavs = tcFacade.findRemoteLavoratori(s);

            manageActivityRemoteWorkers(name, surname, fiscalcode, sex, birthDate, nationality, livingProvince, livingCity, "Ricerca DB nazionale iscritti", lavs);
            notFacade.notifyCompanies(lavs);

            return new ValueResponse(lavs);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value="/remoteworkersforapp", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse findRemoteLavoratori1(@RequestParam(value="name", required=false, defaultValue="") String name,
                                                             @RequestParam(value="surname", required=false, defaultValue="") String surname,
                                                             @RequestParam(value="fiscalcode", required=false, defaultValue="") String fiscalcode,
                                                             @RequestParam(value="nationality", required=false, defaultValue="") String nationality,
                                                             @RequestParam(value="livingProvince", required=false, defaultValue="") String livingProvince,
                                                             @RequestParam(value="livingCity", required=false, defaultValue="") String livingCity) {



        try {

            LavoratoreSearchParams s = new LavoratoreSearchParams();

            s.setName(name);
            s.setSurname(surname);
            s.setFiscalcode(fiscalcode);
           // s.setSex("");
            s.setBirthDate("");
            s.setNationality(nationality);
            s.setLivingProvince(livingProvince);
            s.setLivingCity(livingCity);
            s.setPage(1);

            List<UtenteDbNazionale> lavs = tcFacade.findRemoteLavoratori(s);

            notFacade.notifyCompanies(lavs);

            //trasformo il risultato in viewmodel per l'app
            List<AppLavoratore> result = new ArrayList<>();


            for (UtenteDbNazionale lav : lavs) {
                AppLavoratore l = new AppLavoratore();
                l.setMail("");
                l.setCap(lav.getCap());
                l.setCellulare(lav.getTelefono());
                l.setCognome(lav.getCognome());
                l.setComuneNascita(lav.getNomeComune());
                l.setComuneResidenza(lav.getNomeComuneResidenza());
                SimpleDateFormat gg = new SimpleDateFormat("dd/MM/yyyy");
                l.setDataNascita(gg.format(lav.getDataNascita()));
                l.setFiscale(lav.getCodiceFiscale());
                l.setId(String.valueOf(lav.getIid()));
                l.setIndirizzo(lav.getIndirizzo());
                l.setNazione(lav.getNomeNazione());
                l.setNome(lav.getNome());
                l.setProvinciaNascita(lav.getNomeProvincia());
                l.setProvinciaResidenza(lav.getNomeProvinciaResidenza());
                l.setSesso(lav.getSesso());
                l.setTelefono(lav.getTelefono());
                l.setShowChevron(true);
                l.setBadge(String.valueOf(lav.getNumIscrizioni()));
                l.setShowChevron(false);
                if (lav.getNumIscrizioni() == 0)
                    l.setBadge("");
                result.add(l);
            }



            if (result.size() > 0){
                Collections.sort(result, new Comparator<AppLavoratore>() {
                    @Override
                    public int compare(final AppLavoratore object1, final AppLavoratore object2) {
                        return object1.getCognome().compareTo(object2.getCognome());
                    }
                } );
            }

            return new ValueResponse(result);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/worker/summarymultiterritorio/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewTimeline(HttpServletRequest request, @PathVariable long id) {

        try {

            UiCompleteLavoratoreSummary c = tcFacade.getLavoratoreSummaryMultiterriotrioById(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("summary", c);
            model.put("username", security.getLoggedUser().getUsername());

            model.put("displaySignalUser", false);

            // Se esistono le credenziali per l'invio SMS allora visualizzo il pulsante relativo
            if (tcFacade.existSmsCredentials())
                model.put("existSMSCredentials", true);
            else
                model.put("existSMSCredentials", false);



            PartialViewRenderer renderer = new PartialViewRenderer();

            String content;
            if(((User) security.getLoggedUser()).getCompany().getRegionId() != 30) {
                content = renderer.render(viewResolver, "lavoratori/workerSummaryTimeline", model, LocaleContextHolder.getLocale(), request);
            }else {
                content = renderer.render(viewResolver, "lavoratori/workerSummaryTimelineLombardia", model, LocaleContextHolder.getLocale(), request);
            }
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/worker/summary/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id) {

        try {

            //se sono in un contesto regionale al posto della normale summary restituisco la timeline
            ApplicationOptions opt = appRep.find(null).findFirst().orElse(null);
            if (opt != null && opt.getFenealwebRegionale() != null && opt.getFenealwebRegionale() == true)
                return viewTimeline(request, id);


            //procedo con la normale summary.....
            UiCompleteLavoratoreSummary c = tcFacade.getLavoratoreSummaryById(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("summary", c);
            UiWidgetManager m = widgetFacade.getEnabledWidgets("lavoratore");
            model.put("widgets", m);



            // Se la prov di residenza è contenuta in quelle della Company dell'utente loggato oppure è nulla
            // allora non visualizzo il tasto di segnalazione lavoratore
            Company companyLoggedUser = ((User) security.getLoggedUser()).getCompany();
            if (c.getData().getLivingProvince() == null || companyLoggedUser.containProvince(c.getData().getLivingProvince()))
                model.put("displaySignalUser", false);
            else {
                model.put("displaySignalUser", true);

                // Se il lavoratore è anagrafato presso la sua feneal di residenza visualizzo il pulsante in blu,
                // altrimenti in rosso
                if (tcFacade.isLavoratoreSignedToFenealProvince(c.getData().getLivingProvince(), c.getData().getFiscalcode()))
                    model.put("isWorkerSigned", true);
                else
                    model.put("isWorkerSigned", false);


            }
            // Se esistono le credenziali per l'invio SMS allora visualizzo il pulsante relativo
            if (tcFacade.existSmsCredentials())
                model.put("existSMSCredentials", true);
            else
                model.put("existSMSCredentials", false);



            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "lavoratori/workerSummary", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/workers/searchnew",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchviewnew(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(WorkerSearchFormRenderer.class));
            form.setIdentifier("workernew");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            //tab per le varie ricerche da locale e da db nazionale
            formDescriptor.addField("surname", String.class, "Cognome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.TAB, "Ricerca regionale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("name", String.class, "Nome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.TAB, "Ricerca regionale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("fiscalcode", String.class, "Cod. fisc.", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.TAB, "Ricerca regionale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("company", String.class, "Territorio", null, applicationContext.getBean(RegionalCompaniesOptionalSelectfieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.TAB, "Ricerca regionale")
                    .putParam(Params.FORM_COLUMN, " ");

            // Campi per la ricerca da DB nazionale
           /* formDescriptor.addField("year", String.class, "Anno", null, applicationContext.getBean(YearSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("sector", String.class, "Settore", null, applicationContext.getBean(SectorSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("company", String.class, "Territorio", null, applicationContext.getBean(CompanySelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            */

            defineRicercaDbnazionaleForm(formDescriptor);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Ricerca lavoratori");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    private void defineRicercaDbnazionaleForm(FormDescriptor formDescriptor) {
        formDescriptor.addField("surnameDB", String.class, "Cognome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt5")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("nameDB", String.class, "Nome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt5")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("fiscalcodeDB", String.class, "C.F.", null, applicationContext.getBean(DefaultFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt5")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("sex", String.class, "Sesso", null, applicationContext.getBean(SexSelectFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_5)
                .putParam(Params.ROW, "dt6")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("birthDate", String.class, "Data nasc.", null, applicationContext.getBean(DatePickerRenderer.class))
                .putParam(Params.COLS, Values.COLS_7)
                .putParam(Params.ROW, "dt6")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("nationality", String.class, "Nazionalità", null, applicationContext.getBean(OptionalStateFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt7")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("livingProvince", String.class, "Prov. resid.", null, applicationContext.getBean(OptionalProvinceFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt7")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
        formDescriptor.addField("livingCity", String.class, "Com. resid.", null, applicationContext.getBean(OptionalCityFieldRenderer.class))
                .putParam(Params.COLS, Values.COLS_4)
                .putParam(Params.ROW, "dt7")
                .putParam(Params.TAB, "Ricerca DB nazionale")
                .putParam(Params.FORM_COLUMN, "   ");
    }


    @RequestMapping(value = "/workers/search",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {

        ApplicationOptions opt = appRep.find(null).findFirst().orElse(null);
        if (opt != null && opt.getFenealwebRegionale() != null && opt.getFenealwebRegionale() == true)
            return searchviewnew(request);




        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(WorkerSearchFormRenderer.class));
            form.setIdentifier("worker");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            //tab per le varie ricerche da locale e da db nazionale
            formDescriptor.addField("surname", String.class, "Cognome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.TAB, "Ricerca locale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("name", String.class, "Nome", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.TAB, "Ricerca locale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("fiscalcode", String.class, "Cod. fisc.", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.TAB, "Ricerca locale")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("cell", String.class, "Cellulare", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.TAB, "Ricerca locale")
                    .putParam(Params.FORM_COLUMN, " ");

            // Campi per la ricerca da DB nazionale
           /* formDescriptor.addField("year", String.class, "Anno", null, applicationContext.getBean(YearSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("sector", String.class, "Settore", null, applicationContext.getBean(SectorSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("company", String.class, "Territorio", null, applicationContext.getBean(CompanySelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.TAB, "Ricerca DB nazionale")
                    .putParam(Params.FORM_COLUMN, "  ");
            */

            defineRicercaDbnazionaleForm(formDescriptor);

            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());
            response.setTitle("Ricerca lavoratori");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/worker",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse create(HttpServletRequest request) {

        try {
            Form form = formUtils.generateFormForEntity(FenealEntities.LAVORATORE, null);
            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione anagrafica");


            return response;


        } catch (FormCreationException | CrudConfigurationException | FormNotFoundException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/workerforapp/{fiscalCode}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    SimpleResponse getWorkerForApp(@PathVariable String fiscalCode)  {


        try {

           AppLavoratore lav = tcFacade.getAppLavoratoreDataOrCreateItIfNotExist(fiscalCode);

            return new ValueResponse(lav);


        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/workernoniscrittoforapp/{fiscalCode}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    SimpleResponse getWorkerNonIscrittoForApp(@PathVariable String fiscalCode, @RequestParam(value="province", required=true) String province)  {


        try {

            AppLavoratore lav = tcFacade.getAppLavoratoreDataOrCreateItIfNotExist(fiscalCode, province);
            if (!StringUtils.isEmpty(lav.getCellulare()))
                lav.setCellulare(lav.getCellulare().replace("+39",""));
            return new ValueResponse(lav);

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }





    @RequestMapping(value = "/worker/remote/{fiscalCode}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String viewRemoteWorkerAnagrafica(HttpServletRequest request, @PathVariable String fiscalCode) throws Exception {


        try {

            //qui recupero se cè l'id del lavoratore con quel codice fiscale
            //e se non cè lo predno dalla tabella dei lavoratori del db nazionel e lo creo per il territorio dell'utente
            //corrente
            long idWorker = tcFacade.getIdLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode);
            return executeRedirect(idWorker);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    @RequestMapping(value = "/worker/remote/{fiscalCode}/{province}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String viewRemoteWorkerAnagraficaByFiscalCodeAndProvince(HttpServletRequest request, @PathVariable String fiscalCode, @PathVariable String province) throws Exception {


        try {

            //qui recupero se cè l'id del lavoratore con quel codice fiscale
            //e se non cè lo predno dalla tabella dei lavoratori del db nazionel e lo creo per il territorio dell'utente
            //corrente
            long idWorker = tcFacade.getIdLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode, province);
            return executeRedirect(idWorker);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private String executeRedirect(long idWorker) throws Exception {
        if (idWorker == -1)
            throw new Exception("Lavoratore non trovato");


        //se sono in un contesto regionale al posto della normale summary restituisco la timeline
        ApplicationOptions opt = appRep.find(null).findFirst().orElse(null);
        if (opt != null && opt.getFenealwebRegionale() != null && opt.getFenealwebRegionale() == true)
            return "redirect:/worker/summarymultiterritorio/" + String.valueOf(idWorker);

        return "redirect:/worker/summary/" + String.valueOf(idWorker);
    }


    @RequestMapping(value = "/worker/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse update(HttpServletRequest request, @PathVariable long id) {


        try {
            Form form = formUtils.generateFormForEntity(FenealEntities.LAVORATORE, null);
            Lavoratore d = svc.getLavoratoreById(((User) security.getLoggedUser()).getLid(),id);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();
                data.put("id", id);

                data.put("name", d.getName());
                data.put("surname", d.getSurname());
                data.put("sex", d.getSex());
                data.put("fiscalcode", d.getFiscalcode());
                data.put("birthDate", d.getBirthDate());
                data.put("image", d.getImage());

                if (StringUtils.isEmpty(d.getNationality()))
                    data.put("nationality", null);
                else
                    data.put("nationality", geoSvc.getCountryByName(d.getNationality()));

                if (StringUtils.isEmpty(d.getBirthProvince()))
                    data.put("birthProvince", null);
                else
                    data.put("birthProvince", geoSvc.getProvinceByName(d.getBirthProvince()));

                if (StringUtils.isEmpty(d.getBirthPlace()))
                    data.put("birthPlace", null);
                else
                    data.put("birthPlace", geoSvc.getCityByName(d.getBirthPlace()));

                if (StringUtils.isEmpty(d.getLivingProvince()))
                    data.put("livingProvince", null);
                else
                    data.put("livingProvince", geoSvc.getProvinceByName(d.getLivingProvince()));

                if (StringUtils.isEmpty(d.getLivingCity()))
                    data.put("livingCity", null);
                else
                    data.put("livingCity", geoSvc.getCityByName(d.getLivingCity()));

                data.put("address", d.getAddress());
                data.put("cap", d.getCap());
                data.put("phone", d.getPhone());
                data.put("cellphone", d.getCellphone());
                data.put("mail", d.getMail());
                data.put("ce", d.getCe());
                data.put("ec", d.getEc());
                data.put("fund", d.getFund());
                data.put("notes", d.getNotes());


                form.setData(data);
            }


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Aggiornamento anagrafica");


            return response;


        } catch (FormCreationException | CrudConfigurationException | FormNotFoundException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/worker", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@RequestBody UiAnagrafica anag) {

        try {
            long id = tcFacade.saveAnagrafica(anag);
            return new ValueResponse(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/worker/{id}/iscrizionichart", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@PathVariable long id) {

        try {
            UiWorkerIscrizioniChart chartData = tcFacade.getIscrizioniChart(id);
            return new ValueResponse(chartData);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/worker/{id}/iscrizionidetail", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse iscrizionidetail(@PathVariable long id) {

        try {
            List<UiIscrizione>chartData = tcFacade.getIscrizioniDetails(id);
            return new ValueResponse(chartData);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/worker/{id}/noniscrizionidetail", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse noniscrizionidetail(@PathVariable long id) {

        try {
            List<UiLibero> chartData = tcFacade.getNonIscrizioniDetails(id);
            return new ValueResponse(chartData);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }




    @RequestMapping(value = "/worker/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            tcFacade.deleteLavoratore(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/worker/fiscalcodeforapp", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse calculateFiscalcodeforapp(String name, String surname, String dateBirth, String birthPlaceName,  String sex, String birthNationName) throws ParseException, RemoteException {


        if (birthNationName == null)
            birthNationName = "";
        if (birthPlaceName == null)
            birthPlaceName= "";

        Country c = geoSvc.getCountryByName(birthNationName);
        City cc = geoSvc.getCityByName(birthPlaceName);

        String countryName = c != null? c.getDescription():"";
        String cityName = cc != null? cc.getDescription():"";

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        Date d = ff.parse(dateBirth);

        return new ValueResponse(geoSvc.calculateFiscalCode(name, surname,d,sex,cityName, countryName));

    }





    @RequestMapping(value = "/worker/fiscalcode", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse calculateFiscalcode(String name, String surname, String dateBirth, Integer birthPlaceId,  String sex, Integer birthNationId) throws ParseException, RemoteException {


        if (birthNationId == null)
            birthNationId = 0;
        if (birthPlaceId == null)
            birthPlaceId = 0;

        Country c = geoSvc.getCountryById(birthNationId);
        City cc = geoSvc.getCityById(birthPlaceId);

        String countryName = c != null? c.getDescription():"";
        String cityName = cc != null? cc.getDescription():"";

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        Date d = ff.parse(dateBirth);

        return new ValueResponse(geoSvc.calculateFiscalCode(name, surname,d,sex,cityName, countryName));

    }

    @RequestMapping(value = "/worker/fiscaldata", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse calculateFiscalData(String fiscalcode) throws RemoteException {

        FiscalData fd = tcFacade.getFiscalData(fiscalcode);

        return new ValueResponse(fd);

    }

    @RequestMapping(value = "/worker/tessera/print", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showPrintTessera(HttpServletRequest request) {

        //Lavoratore lavoratore = lavoratoriRepository.get(workerId).orElseThrow(() -> new RuntimeException("Worker does not exist"));

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("printtessera");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class)).putParam(Params.ROW, "dt").putParam(Params.COLS, Values.COLS_12);
            formDescriptor.addField("sector", String.class, "Settore", null, applicationContext.getBean(SectorTypeWithoutInpsSelectRenderer.class)).putParam(Params.ROW, "dt1").putParam(Params.COLS, Values.COLS_12);

            Map<String, Object> data = new HashMap<>();

            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/worker/tessera/downloadfile", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public void printTessera(long workerId, String province, String sector, HttpServletRequest request, HttpServletResponse response) {

        try {
            tcFacade.printTessera(workerId, province, sector, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }

    }

    @RequestMapping(value = "/worker/sendrichiediinfo", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse sendRichiediInfo(@RequestBody UiRequestInfoWorker info) throws Exception {

        tcFacade.sendRequestInfoMail(info.getDestinatario(), info.getNote(), info.getRequestToProvince());

        return new ValueResponse("OK");
    }

    private void manageActivityLocalWorkers(String name, String surname, String fiscalcode, String namesurname, String activityName, List<Lavoratore> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            traceFacade.traceActivity(user, activityName, createDetailForRicercaLocaleIscritti(lavs),
                    String.format("Parametri di ricerca: Nome: %s; Cognome: %s; CodiceFiscale: %s; Nominativo completo: %s", name, surname, fiscalcode, namesurname));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    private void manageActivityRemoteWorkers(String name, String surname, String fiscalcode, String sex, String birthDate, String nationality, String livingProvince, String livingCity, String activityName, List<UtenteDbNazionale> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String nationalityDescr = "";
            if (!StringUtils.isEmpty(nationality)) {
                Country c = geoSvc.getCountryById(Integer.parseInt(nationality));
                if (c != null)
                    nationalityDescr = c.getDescription();
            }

            String provDescr = "";
            if (!StringUtils.isEmpty(livingProvince)) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(livingProvince));
                if (c != null)
                    provDescr = c.getDescription();
            }

            String comuneDescr = "";
            if (!StringUtils.isEmpty(livingCity)) {
                City c = geoSvc.getCityById(Integer.parseInt(livingCity));
                if (c != null)
                    comuneDescr = c.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForRicercaRemoteIscritti(lavs),
                    String.format("Parametri di ricerca: Nome: %s; Cognome: %s; CodiceFiscale: %s; Sesso: %s; Data nascita: %s; Nazionalità: %s; Prov resid: %s; Comune resid: %s", name, surname, fiscalcode, sex, birthDate, nationalityDescr, provDescr, comuneDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForRicercaLocaleIscritti(List<Lavoratore> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nella ricerca sono comparsi i seguenti iscritti: <br><br>");

        for (Lavoratore lav : lavs) {
            b.append(String.format("%s <br>", lav.getFiscalcode()));
        }

        return b.toString();
    }

    private String createDetailForRicercaRemoteIscritti(List<UtenteDbNazionale> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nella ricerca sono comparsi i seguenti iscritti: <br><br>");

        for (UtenteDbNazionale lav : lavs) {
            b.append(String.format("%s <br>", lav.getCodiceFiscale()));
        }

        return b.toString();
    }



}


