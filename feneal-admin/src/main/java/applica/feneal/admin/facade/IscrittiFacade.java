package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.app.dashboard.report.AppReportIscritti;
import applica.feneal.admin.viewmodel.app.dashboard.report.UiAppReportIscrittiInput;
import applica.feneal.admin.viewmodel.reports.UiIscittiStampaTessere;
import applica.feneal.admin.viewmodel.reports.UiIscritto;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.*;
import applica.framework.security.Security;
import it.fenealgestweb.www.FenealgestStatsStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 15/04/2016.
 */
@Component
public class IscrittiFacade {

    @Autowired
    private ReportIscrittiService iscrittiSvc;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private TessereService tessSvc;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private StatisticService statsService;

    @Autowired
    private Security security;


    public List<UiIscritto> reportIscritti(UiIscrittoReportSearchParams params){

        List<DettaglioQuotaAssociativa> iscrittiQuote = iscrittiSvc.retrieveQuoteIscritti(params);

        return convertQuoteIscrittiToUiIscritti(iscrittiQuote);
    }


    private List<UiIscritto> convertQuoteIscrittiToUiIscritti(List<DettaglioQuotaAssociativa> quoteIscritti) {
        List<UiIscritto> result = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        for (DettaglioQuotaAssociativa dettaglio : quoteIscritti) {
            UiIscritto i = new UiIscritto();

            i.setIscrittoEnteBilaterale(dettaglio.getEnte());
            i.setIscrittoQuota(dettaglio.getQuota());
            i.setIscrittoLivello(dettaglio.getLivello());
            i.setIscrittoContratto(dettaglio.getContratto());
            i.setIscrittoProvincia(dettaglio.getProvincia());
            i.setIscrittoDataRegistrazione(dettaglio.getDataRegistrazione());
            i.setIscrittoCompetenza(df.format(dettaglio.getDataInizio()) + " - " + df.format(dettaglio.getDataFine()));
            i.setIscrittoSettore(dettaglio.getSettore());
            i.setTipo(dettaglio.getTipoDocumento());
            Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());
            if (lav != null) {
                i.setLavoratoreId(lav.getLid());
                i.setLavoratoreCap(lav.getCap());
                i.setLavoratoreCellulare(lav.getCellphone());
                i.setLavoratorMail(lav.getMail());
                i.setLavoratoreTelefono(lav.getPhone());
                i.setLavoratoreCittaResidenza(lav.getLivingCity());
                i.setLavoratoreCodiceFiscale(lav.getFiscalcode());
                i.setLavoratoreCognome(lav.getSurname());

                i.setLavoratoreDataNascita(lav.getBirthDate());
                i.setLavoratoreIndirizzo(lav.getAddress());
                i.setLavoratoreLuogoNascita(lav.getBirthPlace());
                i.setLavoratoreNazionalita(lav.getNationality());
                i.setLavoratoreNome(lav.getName());
                i.setLavoratoreCittaResidenza(lav.getLivingCity());
                i.setLavoratoreProvinciaNascita(lav.getBirthProvince());
                i.setLavoratoreProvinciaResidenza(lav.getLivingProvince());
                if ("MASCHIO".equals(lav.getSex()))
                    i.setLavoratoreSesso(Lavoratore.MALE);
                else
                    i.setLavoratoreSesso(Lavoratore.FEMALE);

                i.setLavoratoreCodiceCassaEdile(lav.getCe());
                i.setLavoratoreCodiceEdilcassa(lav.getEc());
                if (lav.getFund() != null)
                    i.setLavoratoreFondo(lav.getFund().getDescription());
                i.setLavoratoreNote(lav.getNotes());
            }

            Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdAzienda());
            if (az != null) {
                i.setAziendaRagioneSociale(az.getDescription());
                i.setAziendaCitta(az.getCity());
                i.setAziendaProvincia(az.getProvince());
                i.setAziendaCap(az.getCap());
                i.setAziendaIndirizzo(az.getAddress());
                i.setAziendaNote(az.getNotes());
                i.setAziendaId(az.getLid());
            }

            result.add(i);
        }



        return result;
    }


    public String retrieveTessereFile(UiIscittiStampaTessere uiIscittiTessere) throws Exception {

        String province = null;

        try {
            Integer idProvince = Integer.parseInt(uiIscittiTessere.getProvince());
            if (idProvince != null)
                if (idProvince > 0){
                    Province p = geoSvc.getProvinceById(idProvince);
                    if (p!= null)
                        province = p.getDescription();
                }

        } catch(NumberFormatException e){
            province = null;
        }

        String pathFile = tessSvc.printTessere(convertUiIscrittoToQuoteIscritti(uiIscittiTessere.getRows()), uiIscittiTessere.getSector(), province, uiIscittiTessere.isOnlyWithoutTessera(), uiIscittiTessere.isGlobal());

        return pathFile;
    }


    public void downloadTesseraFile(String pathFileTessera, String province, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(pathFileTessera);

        try {
            Integer idProvince = Integer.parseInt(province);
            if (idProvince != null)
                if (idProvince > 0){
                    Province p = geoSvc.getProvinceById(idProvince);
                    if (p!= null)
                        province = p.getDescription();
                }

        } catch(NumberFormatException e){
            province = "";
        }

        response.setHeader("Content-Disposition", "attachment;filename=Tessere-"+province.replace(" ", "").replace(".","")+".zip");
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

    private List<DettaglioQuotaAssociativa> convertUiIscrittoToQuoteIscritti(List<UiIscritto> uiIscritti) {

        List<DettaglioQuotaAssociativa> result = new ArrayList<>();

        for (UiIscritto iscritto : uiIscritti) {

            DettaglioQuotaAssociativa d = new DettaglioQuotaAssociativa();

            d.setContratto(iscritto.getIscrittoContratto());
            d.setEnte(iscritto.getIscrittoEnteBilaterale());
            d.setQuota(iscritto.getIscrittoQuota());
            d.setLivello(iscritto.getIscrittoLivello());
            d.setProvincia(iscritto.getIscrittoProvincia());
            d.setIdAzienda(iscritto.getAziendaId());
            d.setIdLavoratore(iscritto.getLavoratoreId());

            result.add(d);
        }

        return result;
    }

    public ListaLavoro createListalavoro(List<UiIscritto> iscritti, String description) throws Exception {
        List<DettaglioQuotaAssociativa> com = convertUiToDettaglioQuote(iscritti);
        return lSrv.createListaFromQuote(com, description);
    }

    private List<DettaglioQuotaAssociativa> convertUiToDettaglioQuote(List<UiIscritto> iscritti) {
        List<DettaglioQuotaAssociativa> a = new ArrayList<>();

        for (UiIscritto uiIscritto : iscritti) {
            DettaglioQuotaAssociativa s = new DettaglioQuotaAssociativa();
            s.setIdLavoratore(uiIscritto.getLavoratoreId());

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

    public List<AppReportIscritti> reportIscrittiForApp(UiAppReportIscrittiInput params) {
        UiIscrittoReportSearchParams newParams = convertParams(params);
        List<UiIscritto>  data = reportIscritti(newParams);

        if (!StringUtils.isEmpty(params.getGeoNazioneSelected())){
            Country c = geoSvc.getCountryByName(params.getGeoNazioneSelected());
            if (c!= null){
                data = data.stream().filter(f -> c.getDescription().toUpperCase().equals(f.getLavoratoreNazionalita())).collect(Collectors.toList());
            }
        }
        if (!StringUtils.isEmpty(params.getGeoProvinceSelected())){
            Province c = geoSvc.getProvinceByName(params.getGeoProvinceSelected());
            if (c!= null){
                data = data.stream().filter(f -> c.getDescription().toUpperCase().equals(f.getLavoratoreProvinciaResidenza())).collect(Collectors.toList());
            }
        }
        if (!StringUtils.isEmpty(params.getGeoComuneSelected())){
            City c = geoSvc.getCityByName(params.getGeoComuneSelected());
            if (c!= null){
                data = data.stream().filter(f -> c.getDescription().toUpperCase().equals(f.getLavoratoreCittaResidenza())).collect(Collectors.toList());
            }
        }


        List<AppReportIscritti> rr = convertToAppUiIscritti(data);
        if (rr.size() > 0){
            Collections.sort(rr, new Comparator<AppReportIscritti>() {
                @Override
                public int compare(final AppReportIscritti object1, final AppReportIscritti object2) {
                    return object1.getCompleteName().compareTo(object2.getCompleteName());
                }
            } );
        }


        if (rr.size() == 0){
            return rr;

        }


        return paginate(rr,params.getSkip(),params.getTake());
    }

    private List<AppReportIscritti> paginate(List<AppReportIscritti> list, int skip, int take){


        //calcolo il numero dell'indice del primo elemento che
        //è uguale a skip
        //calcolo l'ultimo elemento dell'array che è uguale a
        //skip + take

        //ma attenzione che se skip + take è maggiore della dimensione
        //della lista di origine allora devo impostare l'ultino elemento al valore della dimensione
        //della lista

        //ma se skip > list.size ritorno unal ista vuota

        if (skip > list.size())
            return new ArrayList<>();

        int max = skip + take;
        if (max > list.size())
            max = list.size();

        return list.subList(skip, max);

    }

    private List<AppReportIscritti> convertToAppUiIscritti(List<UiIscritto> data) {
        List<AppReportIscritti> result = new ArrayList<>();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        for (UiIscritto uiIscritto : data) {
            AppReportIscritti a = new AppReportIscritti();
            a.setResults(data.size());
            a.setData(f.format(uiIscritto.getIscrittoDataRegistrazione()));
            a.setDataRegistrazione(f.format(uiIscritto.getIscrittoDataRegistrazione()));
            a.setAzienda(uiIscritto.getAziendaRagioneSociale());
            a.setProvincia(uiIscritto.getIscrittoProvincia());
            a.setCompetenza( uiIscritto.getIscrittoCompetenza());
            a.setContratto(uiIscritto.getIscrittoContratto());
            a.setCompleteName(String.format("%s %s", uiIscritto.getLavoratoreCognome(), uiIscritto.getLavoratoreNome()));
            a.setEnte(uiIscritto.getIscrittoEnteBilaterale());
            a.setFiscale(uiIscritto.getLavoratoreCodiceFiscale());
            a.setLivello(uiIscritto.getIscrittoLivello());
            a.setQuota(String.valueOf(uiIscritto.getIscrittoQuota()));
            a.setTipo(uiIscritto.getTipo());
            a.setShowChevron(true);
            result.add(a);
        }

        return result;
    }

    private UiIscrittoReportSearchParams convertParams(UiAppReportIscrittiInput params) {
        UiIscrittoReportSearchParams p = new UiIscrittoReportSearchParams();

        p.setProvince(params.getSelectedProvincia());

        p.setDatefromMonthReport(getMonth(params.getMesiDa()));
        p.setDatetoMonthReport(getMonth(params.getMesiA()));
        p.setDatefromYearReport(params.getAnniDa());
        p.setDatetoYearReport(params.getAnniA());

        p.setFirm(params.getSelectedAzienda());

        p.setParithetic(params.getSelectedEnte());
        p.setSector(params.getSelectedSettore());

        p.setTypeQuoteCash(getTipoQuota(params.getTipoQuota()));

        return p;
    }

    private String getTipoQuota(String tipoQuota) {
        if (tipoQuota.equals("Quote associative"))
            return "IQA";
        if (tipoQuota.equals("Quote inps"))
            return "IQI";

        return "IQP";
    }

    private String getMonth(String monthName){
        if (monthName.equals("Gennaio"))
            return "1";
        if (monthName.equals("Febbraio"))
            return "2";
        if (monthName.equals("Marzo"))
            return "3";
        if (monthName.equals("Aprile"))
            return "4";
        if (monthName.equals("Maggio"))
            return "5";
        if (monthName.equals("Giugno"))
            return "6";
        if (monthName.equals("Luglio"))
            return "7";
        if (monthName.equals("Agosto"))
            return "8";
        if (monthName.equals("Settembre"))
            return "9";
        if (monthName.equals("Ottobre"))
            return "10";
        if (monthName.equals("Novembre"))
            return "11";

        return "12";

    }


    public List<String> statsGetDataExport(String province) throws IOException {
        Province p = geoSvc.getProvinceById(Integer.parseInt(province));

        if (p != null)
            return statsService.statsGetDataExportIscritti(p.getDescription());

        return null;

    }

    public FenealgestStatsStub.DataExportResult statsGetStatisticResult(String province, String filenames) throws IOException {
        Province p = geoSvc.getProvinceById(Integer.parseInt(province));

        if (p != null)
            return statsService.statsGetStatisticsResult(p.getDescription(), filenames);

        return null;
    }
}
