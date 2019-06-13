package applica.feneal.services.impl.servizi;

import applica.feneal.domain.data.core.CommunicationReasonRepository;
import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.servizi.ComunicazioniRepository;
import applica.feneal.domain.model.Filters;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.varese.UiDettaglioQuotaVarese;
import applica.feneal.domain.model.utils.skebby.ParametricSkebbySmsConfiguration;
import applica.feneal.domain.model.utils.skebby.SkebbyParamtericRecipient;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.feneal.domain.model.setting.TipoComunicazione;
import applica.feneal.services.ComunicazioniService;
import applica.feneal.services.GeoService;
import applica.feneal.services.skebby.SkebbyNewApiSmsSender;
import applica.feneal.services.skebby.SkebbySmsSender;
import applica.framework.LoadRequest;
import applica.framework.security.AuthenticationException;
import applica.framework.security.Security;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 02/06/2016.
 */
@Service
public class ComunicazioniServiceImpl implements ComunicazioniService {

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private ComunicazioniRepository comRep;

    @Autowired
    private CommunicationReasonRepository comReasonRep;

    @Autowired
    private CommunicationTypeRepository comTypeRep;

    @Autowired
    private Security sec;

    @Autowired
    private GeoService geoSvc;





    @Override
    public void sendSms(String telNumber, long workerId, String causaleId, String text, String province) throws Exception {
        if (StringUtils.isEmpty(telNumber))
            throw new Exception("Numero di telefono mancante");

        if (!existSmsCredentials())
            throw new Exception("Funzionalità disabilitata: registrazione non effettuata!");



        //devo creare una comunicazione di tipo SMS verificando che il tipo di comunicazione sms esista
        //se non esiste la creo
        TipoComunicazione c = retrieveTipoComunicazioneForSMSOrCreateIt();

        //recupero l causale comunicazione
        CausaleComunicazione causCom = retrieveCausaleComunicazione(causaleId);
        if (causCom == null)
            throw new Exception("Causale nulla");

        Lavoratore l = lavRep.get(workerId).orElse(null);
        if  (l == null)
            throw new Exception("Lavoratore inesistente");

        Comunicazione cc = new Comunicazione();
        Province pp = getProvinceByName(province);
        if (pp == null)
            throw new Exception("Provincia nulla");

        cc.setProvince(pp);
        cc.setLavoratore(l);
        cc.setData(new Date());
        cc.setCausale(causCom);
        cc.setTipo(c);
        cc.setOggetto(text);

        comRep.save(cc);

        //a questo pun to posso inviare l'sms
       sendSmsViaSkebby(telNumber, text);




    }
    @Override
    public void sendSmsViaSkebby(String telNumber, String text) throws Exception {
        User u = ((User) sec.getLoggedUser());
        //valido il num di telefono rimuovendo tutti i caratteri

        telNumber = telNumber.replace("+39", "");
        String telefono = telNumber.replaceAll("[^\\d.]", "").replace(".","");
        if (telefono.length() > 10 && telefono.startsWith("39"))
            telefono = telefono.substring(2);

        telefono = "39" + telefono;

        SkebbySmsSender sender = new SkebbySmsSender();
        sender.send(u.getCompany().getSmsUsername(), u.getCompany().getSmsPassword(), u.getCompany().getSmsSenderNumber(), u.getCompany().getSmsSenderAlias(),new String[]{telefono}, text);

    }


    private void sendSmsViaSkebby(List<Lavoratore> list, String text) throws Exception {
        User u = ((User) sec.getLoggedUser());


        List<String> telNumbwers = new ArrayList<>();
        for (Lavoratore lavoratore : list) {
            String cell = lavoratore.getCellphone();
            if (!StringUtils.isEmpty(cell)){
                cell = cell.replace("+39", "");
                cell = cell.replaceAll("[^\\d.]", "").replace(".","");
                if (cell.length() > 10 && cell.startsWith("39"))
                    cell = cell.substring(2);

                cell= "39" + cell;
                telNumbwers.add(cell);
            }
        }

        String[] tels = telNumbwers.toArray(new String[telNumbwers.size()]);

        SkebbySmsSender sender = new SkebbySmsSender();
        sender.send(u.getCompany().getSmsUsername(), u.getCompany().getSmsPassword(), u.getCompany().getSmsSenderNumber(), u.getCompany().getSmsSenderAlias(),tels, text);

    }

    private Province getProvinceByName(String province) throws Exception {
        Province pp = geoSvc.getProvinceByName(province);
        User u = ((User) sec.getLoggedUser());
        if (!u.getCompany().containProvince(province))
            throw new Exception("Provincia non consentita");

        return pp;
    }

    private CausaleComunicazione retrieveCausaleComunicazione(String causaleId) {
        if (org.apache.commons.lang.StringUtils.isEmpty(causaleId))
            return null;


        CausaleComunicazione com = comReasonRep.get(causaleId).orElse(null);

        return com;
    }

    private TipoComunicazione retrieveTipoComunicazioneForSMSOrCreateIt() {

        LoadRequest req = LoadRequest.build().filter("description", "SMS");
        TipoComunicazione c = comTypeRep.find(req).findFirst().orElse(null);

        if (c != null)
            return c;

        TipoComunicazione com = new TipoComunicazione();
        com.setDescription("SMS");

        comTypeRep.save(com);

        return com;
    }

    @Override
    public String getResidualCredit() throws IOException, AuthenticationException {
//http://gateway.skebby.it/api/send/smseasy/advanced/http.php?method=get_credit&username=mauri.daurelio@gmail.com&password=c471bz6c
        //status=success&credit_left=159.74398&classic_sms=2496&basic_sms=3630&classic_plus_sms=2349
        User u = ((User) sec.getLoggedUser());
        Company c = u.getCompany();
        String url = String.format("http://gateway.skebby.it/api/send/smseasy/advanced/http.php?method=get_credit&username=%s&password=%s", c.getSmsUsername(), c.getSmsPassword());

        Map<String, Object> result = executeResidualCreditRequestWithComplexResult(url, "GET");

        if (result.containsKey("status")){
            String status = String.valueOf(result.get("status"));
            if (status.equals("success"))
            {
                String credit = String.valueOf(result.get("credit_left"));
                return String.format("Il credito residuo è %s", credit);
            }else{
                return String.format("Errore nel recupero del credito residuo: %s", result.get("message"));
            }
        }
        return "Nessun dato trovato";
    }

    private Map<String, Object> executeRequestWithComplexResult(String url, String verb) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {

            //creo la request e la eseguo
            HttpResponse response = executeRequest(url, verb, httpClient);


            //ritorno la stringa risultato dell'operazione

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> result = mapper.readValue(EntityUtils.toString(response.getEntity()), new TypeReference<Map<String, Object>>() {
            });

            return result;

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    private Map<String, Object> executeResidualCreditRequestWithComplexResult(String url, String verb) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {

            //creo la request e la eseguo
            HttpResponse response = executeRequest(url, verb, httpClient);


            //ritorno la stringa risultato dell'operazione



            String[] responseStr = EntityUtils.toString(response.getEntity()).split("&");
            Map<String, Object> result = new HashedMap();

            for (String s : responseStr) {
                if (!StringUtils.isEmpty(s)){
                    String[] res = s.split("=");
                    result.put(res[0], res[1]);
                }
            }





            return result;

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    private HttpResponse executeRequest(String url, String verb, DefaultHttpClient httpClient) throws IOException {

        //creo il metodo della riciesta (se post, get ecc...)
        HttpRequestBase method = getVerb(verb, url);

        //eseguo la richiesta (qui posso avere una ioo exception)
        HttpResponse response = httpClient.execute(method);



        return response;
    }

    private HttpRequestBase getVerb(String method, String url) {

        switch (method) {
            case "GET":
                return new HttpGet(url);

            case "POST":
                return new HttpPost(url);


            case "DELETE":
                return new HttpDelete(url);
        }

        throw new IllegalArgumentException("Method not supported");

    }

    @Override
    public boolean existSmsCredentials(){
        User u = ((User) sec.getLoggedUser());
        Company c = u.getCompany();


        if (!StringUtils.isEmpty(c.getSmsUsername()) && !StringUtils.isEmpty(c.getSmsPassword()))
            if (!StringUtils.isEmpty(c.getSmsSenderAlias()) || !StringUtils.isEmpty(c.getSmsSenderNumber()))
                return true;

        return false;
    }

    @Override
    public void sendSmsToMultipleWorkers(List<Lavoratore> lavoratori, String text, String province, String descrizioneCampagna) throws Exception {

        if (lavoratori.size() == 0)
            throw new Exception("Nessun lavoratore selezionato");

        if (!existSmsCredentials())
            throw new Exception("Funzionalità disabilitata: registrazione non effettuata!");

        SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy");

        //devo creare una comunicazione di tipo SMS verificando che il tipo di comunicazione sms esista
        //se non esiste la creo
        TipoComunicazione c = retrieveTipoComunicazioneForSMSOrCreateIt();

        //la causale comunicazione deve essere "Campapgna del .... 10-10-2016"
        if (StringUtils.isEmpty(descrizioneCampagna)){
            descrizioneCampagna = "Campagna del " + ff.format(new Date());
        }else{
            descrizioneCampagna = descrizioneCampagna + " " +ff.format(new Date()) ;
        }

        //ricerco la causale per descrizione altrimenti ne creo una...

        CausaleComunicazione causCom = retrieveCausaleComunicazioneByDescription(descrizioneCampagna);
        if (causCom == null)
            throw new Exception("Causale nulla");


        //salvo le comunicazioni per ogmni lavoaretore

        for (Lavoratore lavoratore : lavoratori) {
            String cell = lavoratore.getCellphone();
            if (!StringUtils.isEmpty(cell)){
                //salvo la comunicazione solo se non è nullo il cell
                Comunicazione cc = new Comunicazione();
                Province pp = getProvinceByName(province);
                if (pp == null)
                    throw new Exception("Provincia nulla");

                cc.setProvince(pp);
                cc.setLavoratore(lavoratore);
                cc.setData(new Date());
                cc.setCausale(causCom);
                cc.setTipo(c);
                cc.setOggetto(executeMailMerge(text));

                comRep.save(cc);

            }
        }


        //a questo pun to posso inviare l'sms
        sendSmsViaSkebby(lavoratori, text);


    }

    @Override
    public void sendParametricSms(List<UiDettaglioQuotaVarese> quote ) throws Exception {
        if (quote.size() == 0)
            throw new Exception("Nessun lavoratore selezionato");

        if (!existSmsCredentials())
            throw new Exception("Funzionalità disabilitata: registrazione skebby non effettuata!");



        //devo creare una comunicazione di tipo SMS verificando che il tipo di comunicazione sms esista
        //se non esiste la creo
        TipoComunicazione c = retrieveTipoComunicazioneForSMSOrCreateIt();
        //la causale comunicazione deve essere "Campapgna del .... 10-10-2016"
        SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy");
        String descrizioneCampagna = "Comunicazione certificazione del " + ff.format(new Date());
        //ricerco la causale per descrizione altrimenti ne creo una...
        CausaleComunicazione causCom = retrieveCausaleComunicazioneByDescription(descrizioneCampagna);
        String message = "Caro Lavoratore,\n" +
                "scarica la certificazione per usufruire dei nostri servizi.\n" +
                "Distinti Saluti,\n" +
                "FENEALUIL Alta Lombardia\n" +
                "http://fenealweb.it/c?a=${param1}";

        for (UiDettaglioQuotaVarese ui : quote) {
            Lavoratore l = lavRep.get(ui.getLavoratoreId()).get();
            l.setUltimaComunicazione(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            lavRep.save(l);
            Comunicazione cc = new Comunicazione();
            Province pp = getProvinceByName(ui.getProvincia());
            if (pp == null)
                throw new Exception("Provincia nulla");

            cc.setProvince(pp);
            cc.setLavoratore(l);
            cc.setData(new Date());
            cc.setCausale(causCom);
            cc.setTipo(c);
            cc.setOggetto(message.replace("${param1}",ui.getLavoratoreCodiceFiscale() + String.valueOf(ui.getLavoratoreId())));

            comRep.save(cc);
        }



        //inizio a costruire il payload
        sendSms(quote, message);



    }

    @Override
    public List<Comunicazione> getAllWorkerComunicazioni(long workerId) {
        return comRep.find(LoadRequest.build().id(Filters.COMUNICAZONE_ID_LAVORATORE, workerId)).getRows();
    }

    private void sendSms(List<UiDettaglioQuotaVarese> quote, String message) throws Exception {
        ParametricSkebbySmsConfiguration payload =createPayload(quote, message);
        User u = ((User) sec.getLoggedUser());
        SkebbyNewApiSmsSender s = new SkebbyNewApiSmsSender();
        s.sendSms(u.getCompany().getSmsUsername(), u.getCompany().getSmsPassword(),payload);
    }

    private ParametricSkebbySmsConfiguration createPayload(List<UiDettaglioQuotaVarese> quote, String message) {
        ParametricSkebbySmsConfiguration d = new ParametricSkebbySmsConfiguration();
        d.setMessage(message);
        d.setMessage_type("TI");
        d.setSender("");
        d.setAllowInvalidRecipients(false);
        d.setOrder_id("123456789");
        d.setReturnCredits(true);
        d.setReturnRemaining(false);

        HashMap<String, SkebbyParamtericRecipient> hm = new HashMap<String, SkebbyParamtericRecipient>();
        int i = 0;
        for (UiDettaglioQuotaVarese uiDettaglioQuotaVarese : quote) {
            String param1 = uiDettaglioQuotaVarese.getLavoratoreCodiceFiscale() + String.valueOf(uiDettaglioQuotaVarese.getLavoratoreId());
            SkebbyParamtericRecipient r1 = new SkebbyParamtericRecipient();
            r1.setParam1(param1);
            r1.setRecipient("+39" + uiDettaglioQuotaVarese.getLavoratoreCell());
            hm.put(String.valueOf(i), r1);
            i++;
        }
        d.setRecipients(hm);
        return d;
    }

    private String executeMailMerge(String text) {
        return text;
    }

    private CausaleComunicazione retrieveCausaleComunicazioneByDescription(String descrizioneCampagna) {
        LoadRequest req = LoadRequest.build().filter("description", descrizioneCampagna);
        CausaleComunicazione c = comReasonRep.find(req).findFirst().orElse(null);

        if (c != null)
            return c;

        CausaleComunicazione com = new CausaleComunicazione();
        com.setDescription(descrizioneCampagna);

        comReasonRep.save(com);

        return com;
    }
}
