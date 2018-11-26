package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.quote.UiRiepilogoQuota;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.UpdatableDettaglioQuota;
import applica.feneal.domain.model.core.quote.fenealgestImport.DettaglioQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.core.servizi.UiQuoteHeaderParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.*;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by angelo on 26/05/2016.
 */
@Component
public class QuoteFacade {

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private Security security;

    @Autowired
    private GeoService geo;

    @Autowired
    private OptionsManager optManager;

    @Autowired
    private QuoteAssociativeService quoteService;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private QuoteAssocRepository riepilogoQuoteRep;

    @Autowired
    private AziendeRepository aziendeRepository;

    @Autowired
    private ParitheticRepository enteBilateraleRepository;

    @Autowired
    private SectorRepository settoreRepository;


    public List<UiDettaglioQuota> getDettaglioQuota(long id, Long idWorker){

        List<DettaglioQuotaAssociativa> quoteDetails = quoteService.getDettagliQuota(id, idWorker);

        return convertDettaglioQuoteToUiDettaglioQuote(quoteDetails);
    }

    public List<RiepilogoQuoteAssociative> getQuoteAssociative() {
        return riepilogoQuoteRep.find(LoadRequest.build()).getRows();
    }


    private List<UiDettaglioQuota> convertDettaglioQuoteToUiDettaglioQuote(List<DettaglioQuotaAssociativa> quoteDetails) {

        List<UiDettaglioQuota> result = new ArrayList<>();

        for (DettaglioQuotaAssociativa dettaglio : quoteDetails) {
            UiDettaglioQuota q = new UiDettaglioQuota();

            q.setId(dettaglio.getLid());
            q.setIdQuota(dettaglio.getIdRiepilogoQuotaAssociativa());
            q.setEnte(dettaglio.getEnte());
            q.setQuota(dettaglio.getQuota());
            q.setLivello(dettaglio.getLivello());
            q.setContratto(dettaglio.getContratto());
            q.setProvincia(dettaglio.getProvincia());
            q.setDataRegistrazione(dettaglio.getDataRegistrazione());
            q.setDataInizio(dettaglio.getDataInizio());
            q.setDataFine(dettaglio.getDataFine());
            q.setDataDocumento(dettaglio.getDataDocumento());
            q.setTipoDocumento(dettaglio.getTipoDocumento());
            q.setSettore(dettaglio.getSettore());
            Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());
            if (lav != null) {
                q.setLavoratoreNomeCompleto(String.format("%s %s", lav.getSurname(), lav.getName()));
                q.setLavoratoreCodiceFiscale(lav.getFiscalcode());
                q.setLavoratoreId(lav.getLid());
            }

            Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdAzienda());
            if (az != null) {
                q.setAziendaRagioneSociale(az.getDescription());
                q.setAziendaId(az.getLid());
            }

            result.add(q);
        }

        return result;
    }


    public void deleteQuota(long id) {
        quoteService.deleteQuota(id);
    }

    public void downloadFile(long id, HttpServletResponse response) throws IOException {

        RiepilogoQuoteAssociative r = quoteService.getRiepilogoQuotaById(id);

        if (r != null) {

            String logFolder = optManager.get("applica.fenealquote.logfolder");
            String fullPath = logFolder + r.getImportedLogFilePath();

            InputStream is = new FileInputStream(fullPath);

            response.setHeader("Content-Disposition", "attachment;filename=" + r.getImportedLogFilePath());
            //response.setContentType("text/plain");
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

    }


    public ListaLavoro createListalavoro(List<UiDettaglioQuota> quote, String description) throws Exception {
        List<DettaglioQuotaAssociativa> com = convertUiToQuote(quote);
        return lSrv.createListaFromQuote(com, description);
    }

    private List<DettaglioQuotaAssociativa> convertUiToQuote(List<UiDettaglioQuota> quote) {
        List<DettaglioQuotaAssociativa> a = new ArrayList<>();

        for (UiDettaglioQuota uiDettaglio : quote) {
            DettaglioQuotaAssociativa s = new DettaglioQuotaAssociativa();
            s.setIdLavoratore(uiDettaglio.getLavoratoreId());

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

    public void downloadOriginalFile(long id, HttpServletResponse response) throws IOException {
        RiepilogoQuoteAssociative r = quoteService.getRiepilogoQuotaById(id);

        if (r != null) {


            String fullPath =  r.getOriginalFileServerPath();

            InputStream is = new FileInputStream(fullPath);

            response.setHeader("Content-Disposition", "attachment;filename=" + r.getOriginalFileServerPath());
            //response.setContentType("text/plain");
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
    }

    public void downloadXmlFile(long id, HttpServletResponse response) throws IOException {
        RiepilogoQuoteAssociative r = quoteService.getRiepilogoQuotaById(id);

        if (r != null) {


            String fullPath =  r.getXmlFileServerPath();
            InputStream is = new FileInputStream(fullPath);

            response.setHeader("Content-Disposition", "attachment;filename=" + r.getXmlFileServerPath());
            //response.setContentType("text/plain");
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
    }


    public UiDettaglioQuota addDettaglioQuota(long quotaId, UiQuoteHeaderParams data) throws Exception {

        RiepilogoQuoteAssociative r =  riepilogoQuoteRep.get(quotaId).orElse(null);
        if (r == null)
            throw  new Exception("Riepilogo quota non trovata");

        DettaglioQuotaDTO d = UiRiepilogoQuota.createDettaglioQuota(data,
                r,
                lavRep.get(data.getWorker()).orElse(null),
                data.getFirm() != "" ?aziendeRepository.get(data.getFirm()).orElse(null) : null);



        DettaglioQuotaAssociativa dettaglio = quoteService.addItem(r, d);

        return convertDettaglioToUiDettaglioQuota(dettaglio);
    }


    public UiDettaglioQuota convertDettaglioToUiDettaglioQuota(DettaglioQuotaAssociativa dettaglio) {
        UiDettaglioQuota q = new UiDettaglioQuota();
        q.setId(dettaglio.getLid());
        q.setIdQuota(dettaglio.getIdRiepilogoQuotaAssociativa());
        q.setEnte(dettaglio.getEnte());
        q.setQuota(dettaglio.getQuota());
        q.setLivello(dettaglio.getLivello());
        q.setContratto(dettaglio.getContratto());
        q.setProvincia(dettaglio.getProvincia());
        q.setDataRegistrazione(dettaglio.getDataRegistrazione());
        q.setDataInizio(dettaglio.getDataInizio());
        q.setDataFine(dettaglio.getDataFine());
        q.setDataDocumento(dettaglio.getDataDocumento());
        q.setTipoDocumento(dettaglio.getTipoDocumento());
        q.setSettore(dettaglio.getSettore());
        Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());
        if (lav != null) {
            q.setLavoratoreNomeCompleto(String.format("%s %s", lav.getSurname(), lav.getName()));
            q.setLavoratoreCodiceFiscale(lav.getFiscalcode());
            q.setLavoratoreId(lav.getLid());
        }

        Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdAzienda());
        if (az != null) {
            q.setAziendaRagioneSociale(az.getDescription());
            q.setAziendaId(az.getLid());
        }
        return q;
    }

    public void duplicaQuota(long quotaId, UiQuoteHeaderParams data) throws ParseException {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date inizio = f.parse(data.getDataInizio());
        Date fine = f.parse(data.getDataFine());
        quoteService.duplicaQuota(quotaId, inizio, fine);
    }

    public void modifyCompetenceQuotaItems(long quotaId, UiQuoteHeaderParams data) throws ParseException {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date inizio = f.parse(data.getDataInizio());
        Date fine = f.parse(data.getDataFine());
        quoteService.modifyCompetenceQuotaItems(quotaId, inizio, fine);
    }

    public void deleteDettaglioQuota(long quotaId, long itemId) {
        quoteService.deleteItem(quotaId, itemId);
    }

    public void updateDettaglioQuota(long quotaId, long itemId, UpdatableDettaglioQuota updatedData) {
        quoteService.updateItem(quotaId,itemId, updatedData);
    }



    public long createRiepilogoQuoteManuali(UiQuoteHeaderParams params) throws Exception {


        RiepilogoQuotaDTO dto =  createRiepilogoQuotaHeaderDTO(params);

        RiepilogoQuoteAssociative r = quoteService.creaQuoteManuali(dto);

        return r.getLid();

    }



    private RiepilogoQuotaDTO createRiepilogoQuotaHeaderDTO(UiQuoteHeaderParams params) {
        User u = ((User) security.getLoggedUser());

        Sector s = settoreRepository.find(LoadRequest.build().filter("description",params.getSettore())).findFirst().orElse(null);
        Paritethic ente = null;
        Azienda firm = null;
        Province p = geo.getProvinceById(Integer.parseInt(params.getProvince()));
        if (s.getType().equals(Sector.sector_edile)){
            ente = enteBilateraleRepository.get(params.getEnte()).orElse(null);
            firm = aziendeRepository.get(params.getFirm()).orElse(null);
        }else{
            if (params.getFirm() != "")
                firm = aziendeRepository.get(params.getFirm()).orElse(null);
        }


        //creo il dto per fare in modo che la classe importData possa terminare il lavoro
        RiepilogoQuotaDTO dto = null;

        if (s.getType().equals(Sector.sector_edile)){
            dto = UiRiepilogoQuota.createRiepilogoQuota(s,
                    ente !=null? ente.getDescription():"",
                    p.getDescription(),
                    u.getCompany().getLid(),
                    u.getCompleteName(),
                    u.getUsername(),
                    UUID.randomUUID().toString(),
                    "",
                    params.getDataInizio(),
                    params.getDataFine());
        }else{
            dto = UiRiepilogoQuota.createRiepilogoQuota(s,
                    firm !=null? firm.getDescription():"",
                    p.getDescription(),
                    u.getCompany().getLid(),
                    u.getCompleteName(),
                    u.getUsername(),
                    UUID.randomUUID().toString(),"",
                    params.getDataInizio(),
                    params.getDataFine());
        }


        //non ci sono dettagli....

        return  dto;
    }
}
