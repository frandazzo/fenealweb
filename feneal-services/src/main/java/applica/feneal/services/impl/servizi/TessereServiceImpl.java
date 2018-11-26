package applica.feneal.services.impl.servizi;

import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.tessere.TessereRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.TessereService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 03/06/2016.
 */
@Service
public class TessereServiceImpl implements TessereService {
    @Autowired
    private TessereRepository tessRep;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private Security sec;

    @Autowired
    private IscrizioniRepository isRep;

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private AziendeRepository azRep;

    @Override
    public String printTesseraForWorker(long workerId, String settore, String provincia) throws Exception {
        User u = ((User) sec.getLoggedUser());
        Lavoratore l = lavSvc.getLavoratoreById(u.getLid(), workerId);

        if (l == null)
            throw new Exception("Lavoratore nullo");

        //posso adesso salvalare la stampa della tessera:
        //essa avviene cancellando una eventuale tessera stampata nell'anno in corso
        //dalla stessa company e ricreandola
        //pertanto ricerco tutte le tessere (una sola al max) stampate dalla company nell'anno in corso
        removeAndRecreateTessera(u, l);

        return printTessera(l, settore, provincia);




    }

    private void removeAndRecreateTessera(User u, Lavoratore l) {
        LoadRequest req = LoadRequest.build().filter("companyId", u.getCompany().getLid()).filter("fiscalCode", l.getFiscalcode()).filter("year", Calendar.getInstance().get(Calendar.YEAR));
        Tessera t = tessRep.find(req).findFirst().orElse(null);
        if (t != null)
            tessRep.delete(t.getId());

        Tessera t1 = new Tessera();
        t1.setCompanyId(u.getCompany().getLid());
        t1.setDate(new Date());
        t1.setFiscalCode(l.getFiscalcode());
        t1.setYear(Calendar.getInstance().get(Calendar.YEAR));
        t1.setPrintedFrom(u.getName() + " " + u.getSurname());
        tessRep.save(t1);
    }

    private String printTessera(Lavoratore l, String settore, String provincia) throws IOException {
        User u = ((User) sec.getLoggedUser());
        List<Iscrizione> iscrizioni = isRep.findIscrizioniByFiscalCode(l.getFiscalcode());
        String azienda = retrieveAzizendaFromIscrizioni(iscrizioni);

        FenealgestUtils svc = createFenealgestUtilsService();

        ExportTessere f = createExportTessereInputbean(l, settore, provincia, u, azienda);

        ExportTessereResponse result = svc.exportTessere(f);

        DataHandler webResult = result.getExportTessereResult();


        return extractFile(webResult, provincia);




    }

    private FenealgestUtils createFenealgestUtilsService() {
        FenealgestUtils svc = null;
        ExportTessereResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,optMan.get("applica.fenealgestutils"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        return svc;
    }

    private ExportTessere createExportTessereInputbean(Lavoratore l, String settore, String provincia, User u, String azienda) {
        ExportTessere f = new ExportTessere();
        f.setSettore(settore);
        f.setProvincia(provincia);

        ArrayOfTesserato t = new ArrayOfTesserato();

        Tesserato t1 = createTesseratoTesserato(l, settore, provincia, u, azienda);

        Tesserato[] arr = new Tesserato[]{t1};

        t.setTesserato(arr);
        f.setTesserati(t);
        return f;
    }

    private Tesserato createTesseratoTesserato(Lavoratore l, String settore, String provincia, User u, String azienda) {
        Tesserato t1 = new Tesserato();
        t1.setAzienda(azienda);
        t1.setProvincia(l.getLivingProvince());
        t1.setComune(l.getLivingCity());
        t1.setCap(l.getCap());
        t1.setVia(l.getAddress());
        t1.setNome(l.getName());
        t1.setCognome(l.getSurname());
        t1.setProvinciaSindacale(provincia);
        t1.setSettoreTessera(settore);
        t1.setStampataDa(u.getName() + " " + u.getSurname());
        return t1;
    }

    private String extractFile(DataHandler webResult, String provincia) throws IOException {
        InputStream inputStream = webResult.getInputStream();
        //recupro una cartella temporanera
        File nn = File.createTempFile("tessere-" + provincia.replace(" ", "").replace(".",""), ".zip");



        nn.createNewFile();
        OutputStream outputStream = new FileOutputStream(nn);

        try{

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        return nn.getAbsolutePath();
    }

    private String retrieveAzizendaFromIscrizioni(List<Iscrizione> iscrizioni) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (Iscrizione iscrizione : iscrizioni) {
            if (iscrizione.getAnno() == year)
                if (!iscrizione.getSettore().equals(Sector.sector_inps))
                    if (!StringUtils.isEmpty(iscrizione.getAzienda()))
                        return iscrizione.getAzienda();
        }

        //rifaccio il ciclo per l'anno precedente
        for (Iscrizione iscrizione : iscrizioni) {
            if (iscrizione.getAnno() == year -1)
                if (!iscrizione.getSettore().equals(Sector.sector_inps))
                    if (!StringUtils.isEmpty(iscrizione.getAzienda()))
                        return iscrizione.getAzienda();
        }

        return "";
    }

    @Override
    public Tessera findPrintedTessera(String fiscalCode, int year) {
        User u = ((User) sec.getLoggedUser());
        LoadRequest req = LoadRequest.build().filter("companyId", u.getCompany().getLid()).filter("fiscalCode", fiscalCode).filter("year", Calendar.getInstance().get(Calendar.YEAR));
        return tessRep.find(req).findFirst().orElse(null);
    }

    @Override
    public List<Tessera> findOtherPrintedTessere(String fiscalCode, int year) {
        User u = ((User) sec.getLoggedUser());
        LoadRequest req = LoadRequest.build().filter("companyId", u.getCompany().getLid(), Filter.NE).filter("fiscalCode", fiscalCode).filter("year", Calendar.getInstance().get(Calendar.YEAR));
        return tessRep.find(req).getRows();
    }

    @Override
    public String printTessere(List<DettaglioQuotaAssociativa> iscrizioni, String settore, String provincia, boolean onlyWithoutTessera, boolean global) throws IOException {
        User u = ((User) sec.getLoggedUser());


        //devo rivalutrare la  lista delle iscrizioni in base ai flag se voglio stamparla per tutti o solo
        //per coloro che non hanno una tessera stampata nell'anno in corso
        //se il flag onlyWithoutTessera è true devo attivare il filtro...

        //e solamente in questo caso posso verificare se voglio escludere anche coloro che hanno una tessera stampata
        // livello globale (ossia stampata anche da altri teritori
        List<DettaglioQuotaAssociativa> iscrizioniFiltrate = new ArrayList<>();

        if (!onlyWithoutTessera)
            iscrizioniFiltrate = iscrizioni;
        else{

            //se il flag è globale devo verificare se cè gia una tessera per l'utente
            for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : iscrizioni) {
                Lavoratore l = lavSvc.getLavoratoreById(u.getLid(), dettaglioQuotaAssociativa.getIdLavoratore());
                if (l != null)
                {
                    Tessera t = null;
                    if (global)
                        t = findGlobal(l.getFiscalcode());
                    else
                        t = findLocal(l.getFiscalcode());

                    if (t == null)
                        iscrizioniFiltrate.add(dettaglioQuotaAssociativa);
                }
            }

        }



        //devo per prima cosa salvare le tessere per ognuno dei lavoratori
        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : iscrizioniFiltrate) {

            removeAndRecreateTessera(u,lavSvc.getLavoratoreById(u.getLid(), dettaglioQuotaAssociativa.getIdLavoratore()));
        }

        //adesso posso stampare il file
        FenealgestUtils svc = createFenealgestUtilsService();

        ExportTessere f = createExportTessereInputbean(iscrizioniFiltrate, settore, provincia, u);

        ExportTessereResponse result = svc.exportTessere(f);

        DataHandler webResult = result.getExportTessereResult();


        return extractFile(webResult, provincia);

    }



    private Tesserato createTesseratoFromQuota(DettaglioQuotaAssociativa l, String settore, String provincia, User u) {

        long idLavoratore = l.getIdLavoratore();
        long idAzienda = l.getIdAzienda();
        //trovo il lavoratora


        String azienda = getAzienda(idAzienda);
        Lavoratore lav = lavSvc.getLavoratoreById(u.getLid(), idLavoratore);

        Tesserato t1 = new Tesserato();
        t1.setAzienda(azienda);
        t1.setProvincia(lav.getLivingProvince());
        t1.setComune(lav.getLivingCity());
        t1.setCap(lav.getCap());
        t1.setVia(lav.getAddress());
        t1.setNome(lav.getName());
        t1.setCognome(lav.getSurname());
        t1.setProvinciaSindacale(provincia);
        t1.setSettoreTessera(settore);
        t1.setStampataDa(u.getName() + " " + u.getSurname());
        return t1;
    }


    private ExportTessere createExportTessereInputbean(List<DettaglioQuotaAssociativa> l, String settore, String provincia, User u) {
        ExportTessere f = new ExportTessere();
        f.setSettore(settore);
        f.setProvincia(provincia);

        ArrayOfTesserato t = new ArrayOfTesserato();
        Tesserato[] arr = createListaTesserati(l, settore, provincia, u);

        t.setTesserato(arr);
        f.setTesserati(t);
        return f;
    }

    private Tesserato[] createListaTesserati(List<DettaglioQuotaAssociativa> l, String settore, String provincia, User u) {
        List<Tesserato> result = new ArrayList<>();

        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : l) {
            result.add(createTesseratoFromQuota(dettaglioQuotaAssociativa, settore, provincia, u));
        }



        return result.toArray(new Tesserato[result.size()]);

    }

    private String getAzienda(long idAzienda) {
        String azienda = "";
        if (idAzienda > 0){
            Azienda az = azRep.get(idAzienda).orElse(null);
            if (az != null)
                azienda = az.getDescription();
        }
        return azienda;
    }


    private Tessera findLocal(String fiscalCode){
        User u = ((User) sec.getLoggedUser());
        LoadRequest req = LoadRequest.build().filter("companyId", u.getCompany().getLid()).filter("fiscalCode", fiscalCode).filter("year", Calendar.getInstance().get(Calendar.YEAR));
        Tessera t = tessRep.find(req).findFirst().orElse(null);
        return t;
    }

    private Tessera findGlobal(String fiscalCode){
        User u = ((User) sec.getLoggedUser());
        LoadRequest req = LoadRequest.build().filter("fiscalCode", fiscalCode).filter("year", Calendar.getInstance().get(Calendar.YEAR));
        Tessera t = tessRep.find(req).findFirst().orElse(null);
        return t;
    }


}
