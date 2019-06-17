package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.domain.model.core.quote.varese.dateParam;
import applica.feneal.domain.model.core.quote.varese.workerParam;
import applica.feneal.services.ReportQuoteVareseService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.management.mailmerge.DocxToPdfConverter;
import applica.framework.management.mailmerge.MailMergeFacade;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.utils.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;


@Service
public class ReportQuoteVareseServiceImpl implements ReportQuoteVareseService {


    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;
    @Autowired
    private LavoratoriRepository lavRep;


    @Override
    public List<DettaglioQuotaAssociativa> retrieveQuoteVarese(UiQuoteVareseReportSearchParams params){


        String quota2= params.getQuota2();
//        String quota1= params.getQuota1();

        LoadRequest req = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota1()));
        List<DettaglioQuotaAssociativa> list1 =  dettRep.find(req).getRows();

        if(!StringUtils.isEmpty(quota2)) {
            LoadRequest req2 = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota2()));
            List<DettaglioQuotaAssociativa>list2 = dettRep.find(req2).getRows();

            if(list2.size() > list1.size()) {
                return intersectLists(list2, list1);
            }
            else{
                return intersectLists(list1, list2);
            }
        }

        return list1;
    }

    @Override
    public String compileFileForLavoratore(String id, String templatePath) throws Exception {

        //l'idi è costituito per le prime 16 cifre di codice fiscale e per le rimanenti l'id del lavoratore
        String cf = id.substring(0,16);
        String wi = id.substring(16);

        LoadRequest req = LoadRequest.build()
                .disableOwnershipQuery()
                .filter("id", Long.parseLong(wi))
                .filter("fiscalcode", cf);

        Lavoratore ll = lavRep.find(req).findFirst().orElse(null);
        if (ll == null)
            throw new Exception("Lavoratore non trovato");

        String PdfPath = CreatePdfForSMSVarese(cf,wi, ll, templatePath);


        return PdfPath;

    }

    private String CreatePdfForSMSVarese(String cf, String id, Lavoratore ll, String templatePath) throws IOException, Docx4JException, XDocReportException {



        //creo una directory temporanea
        File temp1 = File.createTempFile("test","");
        temp1.delete();
        temp1.mkdir();

        String tempFolder=temp1.getAbsolutePath();

        //entra qua dentro e si blocca
        MailMergeFacade facade = new MailMergeFacade();


        //genero il pdf nella directory di test;
        String outputFile = tempFolder + "/certificazione.pdf";

        //imposto la tabella delle proprieà con cui fare il mail merge
        Hashtable<String, Object> prop = new Hashtable<String, Object>();
        //se guaro il file della documentazione vedo che richiede un oggetto doc, e una proprietà foter

        workerParam lav = new workerParam();
        lav.setCodFiscale(cf);

        String nomCompleto = ll.getSurname()+ " " + ll.getName();
        if(nomCompleto.length() < 22) {
            lav.setNomeCompleto(nomCompleto);
            lav.setSecondRiga("");
        }
        else {
            lav.setNomeCompleto(ll.getSurname());
            lav.setSecondRiga(ll.getName());
        }

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        dateParam data = new dateParam();
        data.setDataYear(Integer.parseInt(ll.getUltimaComunicazione()));
        data.setDataDay(ff.format(new Date()));

        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        prop.put("data", data);
        prop.put("lav", lav);

        facade.executeMailMergeAndGeneratePdf(templatePath, outputFile, prop);

        File createdFile = new File(outputFile);


        return createdFile.getAbsolutePath();
    }



    private List<DettaglioQuotaAssociativa> intersectLists(List<DettaglioQuotaAssociativa> list1, List<DettaglioQuotaAssociativa> list2) {
        List<DettaglioQuotaAssociativa> listApp = new ArrayList<>();
        for (DettaglioQuotaAssociativa lav2 : list2) {

            DettaglioQuotaAssociativa l = list1.stream()
                    .filter(a -> lav2.getIdLavoratore() == a.getIdLavoratore())
                    .findFirst()
                    .orElse(null);

            if ( l != null)
                    listApp.add(l);

        }
        list1.removeAll(listApp);
        return list1;
    }
}


//   if(!StringUtils.isEmpty(quota1)) {
//            Filter f1= new Filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota1()));
//            req.getFilters().add(f1);
////            LoadRequest req2 = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota2()));
////            List<DettaglioQuotaAssociativa> q2 = dettRep.find(req2).getRows();
//        }
