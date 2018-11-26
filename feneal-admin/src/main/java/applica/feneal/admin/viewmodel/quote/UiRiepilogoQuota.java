package applica.feneal.admin.viewmodel.quote;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.fenealgestImport.DettaglioQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.FirmDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.WorkerDTO;
import applica.feneal.domain.model.core.servizi.UiQuoteHeaderParams;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UiRiepilogoQuota {

    private UiQuoteHeaderParams header;
    private List<UiQuoteLavoratori> quoteRows;


    public UiQuoteHeaderParams getHeader() {
        return header;
    }

    public void setHeader(UiQuoteHeaderParams header) {
        this.header = header;
    }

    public List<UiQuoteLavoratori> getQuoteRows() {
        return quoteRows;
    }

    public void setQuoteRows(List<UiQuoteLavoratori> quoteRows) {
        this.quoteRows = quoteRows;
    }

    private static Date addOneDayTodate(Date date) {
        if (date == null)
            return null;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private static Date tryParse(String s, Date defaultDate){
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");


        try {
            return ff.parse(s);
        } catch (ParseException e) {
            return defaultDate;
        }

    }

    public RiepilogoQuotaDTO createRiepilogoQuota(Sector s,
                                                  String ente,
                                                  String province ,
                                                  long companyId,
                                                  String userCompleteName,
                                                  String username,
                                                  String guid,
                                                  String logFile){


        Date documentDate = addOneDayTodate(tryParse(header.getDataFine(), null));

        //posso adesso costruire il riepilogo quota DTO
        RiepilogoQuotaDTO dto = new RiepilogoQuotaDTO();
        dto.setDataRegistrazione(new Date());
        dto.setDataDocumento(documentDate);
        dto.setSettore(s.getDescription());
        if (s.getDescription().equals(Sector.sector_inps)){
            dto.setEnte(Sector.sector_inps);
        }else {
            dto.setEnte(ente);
        }

        dto.setProvincia(province);
        dto.setCompanyId(companyId);
        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        dto.setCompentenza(String.format("%s - %s", ss.format(tryParse(header.getDataInizio(), null)), ss.format(tryParse(header.getDataFine(), null)) ));
        dto.setGuid(guid);
        dto.setLogFilename(logFile);
        dto.setTipoDocumento("IQA");
        dto.setCreaDelegaIfNotExist(false);
        dto.setAssociaDelega(true);


        dto.setExporterName(userCompleteName);
        dto.setExporterMail(username);
        return dto;





    }


    public static RiepilogoQuotaDTO createRiepilogoQuota(Sector s,
                                                         String ente,
                                                         String province ,
                                                         long companyId,
                                                         String userCompleteName,
                                                         String username,
                                                         String guid,
                                                         String logFile,
                                                         String dataInizio,
                                                         String dataFine){


        Date documentDate = addOneDayTodate(tryParse(dataFine,null));

        //posso adesso costruire il riepilogo quota DTO
        RiepilogoQuotaDTO dto = new RiepilogoQuotaDTO();
        dto.setDettagli(new ArrayList<>());
        dto.setDataRegistrazione(new Date());
        dto.setDataDocumento(documentDate);
        dto.setSettore(s.getDescription());
        if (s.getDescription().equals(Sector.sector_inps)){
            dto.setEnte(Sector.sector_inps);
        }else {
            dto.setEnte(ente);
        }

        dto.setProvincia(province);
        dto.setCompanyId(companyId);
        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        dto.setCompentenza(String.format("%s - %s", ss.format(tryParse(dataInizio, null)), ss.format(tryParse(dataFine, null)) ));
        dto.setGuid(guid);
        dto.setLogFilename(logFile);
        dto.setTipoDocumento("IQA");
        dto.setCreaDelegaIfNotExist(false);
        dto.setAssociaDelega(true);


        dto.setExporterName(userCompleteName);
        dto.setExporterMail(username);
        return dto;





    }


    public DettaglioQuotaDTO createDettaglioQuota(UiQuoteLavoratori rowData,
                                                  RiepilogoQuotaDTO dto,
                                                  Lavoratore lavoratore,
                                                  Azienda azienda) throws Exception {
        DettaglioQuotaDTO d = new DettaglioQuotaDTO();

        d.setDataDocumento(dto.getDataDocumento());
        d.setDataRegistrazione(dto.getDataRegistrazione());
        d.setTipoDocumento(dto.getTipoDocumento());

        d.setContratto(rowData.getContratto());

        Date dataInizio = tryParse(header.getDataInizio(),null);
        Date dataFine = tryParse(header.getDataFine(), null);

        d.setDataFine(dataFine);
        d.setDataInizio(dataInizio);

        d.setLivello("");
        d.setQuota(rowData.getImporto());
        d.setSettore(dto.getSettore());
        d.setEnte(dto.getEnte());
        d.setNote("");

        //ovvimante avendoli già iniseriti non creo completamente i dto azienda e worker
        WorkerDTO w = new WorkerDTO();
        w.setFiscalcode(lavoratore.getFiscalcode());
        w.setId(lavoratore.getLid());
        d.setWorker(w);


        if (azienda != null){
            FirmDTO f = new FirmDTO();
            f.setDescription(azienda.getDescription());
            f.setId(azienda.getLid());
            d.setFirm(f);
        }



        return d;
    }

    public static DettaglioQuotaDTO createDettaglioQuota(UiQuoteHeaderParams rowData,
                                                         RiepilogoQuoteAssociative dto,
                                                         Lavoratore lavoratore,
                                                         Azienda azienda
                                                          ) throws Exception {
        DettaglioQuotaDTO d = new DettaglioQuotaDTO();

        d.setDataDocumento(dto.getDataDocumento());
        d.setDataRegistrazione(dto.getDataRegistrazione());
        d.setTipoDocumento(dto.getTipoDocumento());

        d.setContratto(rowData.getContract());

        Date dataInizio = tryParse(dto.calculateDataInizio(),null);
        Date dataFine = tryParse(dto.calculateDataFine(), null);

        d.setDataFine(dataFine);
        d.setDataInizio(dataInizio);

        d.setLivello(rowData.getLevel());

        d.setQuota(tryParse(rowData.getAmount()));
        d.setSettore(dto.getSettore());
        d.setEnte(dto.getEnte());
        d.setNote(rowData.getNotes());

        //ovvimante avendoli già iniseriti non creo completamente i dto azienda e worker
        WorkerDTO w = new WorkerDTO();
        w.setFiscalcode(lavoratore.getFiscalcode());
        w.setId(lavoratore.getLid());
        d.setWorker(w);


        if (azienda != null){
            FirmDTO f = new FirmDTO();
            f.setDescription(azienda.getDescription());
            f.setId(azienda.getLid());
            d.setFirm(f);
        }





        return d;
    }

    public static DettaglioQuotaDTO createDettaglioQuota(UiQuoteHeaderParams rowData,
                                                         RiepilogoQuotaDTO dto,
                                                         Lavoratore lavoratore,
                                                         Azienda azienda) throws Exception {
        DettaglioQuotaDTO d = new DettaglioQuotaDTO();

        d.setDataDocumento(dto.getDataDocumento());
        d.setDataRegistrazione(dto.getDataRegistrazione());
        d.setTipoDocumento(dto.getTipoDocumento());

        d.setContratto(rowData.getContract());

        Date dataInizio = tryParse(rowData.getDataInizio(),null);
        Date dataFine = tryParse(rowData.getDataFine(), null);

        d.setDataFine(dataFine);
        d.setDataInizio(dataInizio);

        d.setLivello("");

        d.setQuota(tryParse(rowData.getAmount()));
        d.setSettore(dto.getSettore());
        d.setEnte(dto.getEnte());
        d.setNote("");

        //ovvimante avendoli già iniseriti non creo completamente i dto azienda e worker
        WorkerDTO w = new WorkerDTO();
        w.setFiscalcode(lavoratore.getFiscalcode());
        w.setId(lavoratore.getLid());
        d.setWorker(w);


        if (azienda != null){
            FirmDTO f = new FirmDTO();
            f.setDescription(azienda.getDescription());
            f.setId(azienda.getLid());
            d.setFirm(f);
        }



        return d;
    }

    private static double tryParse(Double amount) {

            if (amount == null)
                return 0;
            return amount;

    }


}
