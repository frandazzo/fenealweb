package applica.feneal.services.impl.quote;

import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.fenealgestImport.DettaglioQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.services.GeoService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.UUID;

/**
 * Created by fgran on 17/11/2016.
 */
@Component
public class ImportQuoteHelper {

    @Autowired
    private Security sec;

    @Autowired
    private GeoService geo;

    @Autowired
    private SectorRepository settRep;

    @Autowired
    private QuoteAssocRepository quoteRep;


    public void ValidateDTO(RiepilogoQuotaDTO dto) throws Exception {
        if (org.apache.commons.lang.StringUtils.isEmpty(dto.getSettore())){
            throw  new Exception("Settore nullo");
        }

        Sector ss = settRep.find(LoadRequest.build().filter("description", dto.getSettore())).findFirst().orElse(null);
        if (ss == null){
            throw  new Exception("Settore nullo");
        }


        if (org.apache.commons.lang.StringUtils.isEmpty(dto.getGuid())){

            dto.setGuid(UUID.randomUUID().toString());
        }


        if (dto.getDataDocumento() == null)
            throw new Exception("Data documento non impostata");
        if (dto.getDataRegistrazione() == null)
            throw new Exception("Data registrazione non impostata");
        if (org.apache.commons.lang.StringUtils.isEmpty(dto.getTipoDocumento())){
            throw new Exception("Tipo documento non impostato");
        }


        if (org.apache.commons.lang.StringUtils.isEmpty(dto.getProvincia())){
            throw new Exception("Provincia non impostata");
        }

        if (geo.getProvinceByName(dto.getProvincia()) == null){
            throw new Exception("Provincia non riconosciuta");
        }


        //adesso verifico che ogni item se si tratta di impianti fissi deve avere una azienda
        for (DettaglioQuotaDTO dettaglioQuotaDTO : dto.getDettagli()) {

            if (dettaglioQuotaDTO.getDataInizio().getTime() > dettaglioQuotaDTO.getDataFine().getTime()){
                throw new Exception("La data  di inizio e posteriore alla data fine competenza");
            }
        }

        //pulisco le aziende se si tratta di inps
        for (DettaglioQuotaDTO dettaglioQuotaDTO : dto.getDettagli()) {
            if (ss.getDescription().equals(Sector.sector_inps)){
                dettaglioQuotaDTO.setFirm(null);
            }
        }
    }

    public RiepilogoQuoteAssociative createRiepilogoQuota(RiepilogoQuotaDTO dto, String logFilename) {
        RiepilogoQuoteAssociative result = new RiepilogoQuoteAssociative();

        result.setDataRegistrazione(dto.getDataRegistrazione());
        result.setProvincia(dto.getProvincia().toUpperCase());

        result.setCompetenza(dto.getCompentenza());
        result.setGuid(dto.getGuid());
        result.setDataDocumento(dto.getDataDocumento());
        result.setOriginalFileServerPath(dto.getOriginalFilename());
        if (dto.getTipoDocumento() != null)
            result.setTipoDocumento(dto.getTipoDocumento().toUpperCase());
        if (dto.getSettore() != null)
            result.setSettore(dto.getSettore().toUpperCase());
        if (dto.getEnte() != null){
            result.setEnte(dto.getEnte().toUpperCase());
        }else{
            if (!StringUtils.isEmpty(dto.getTipoDocumento()) && !StringUtils.isEmpty(dto.getSettore())) {
                if (dto.getTipoDocumento().equals(RiepilogoQuoteAssociative.IQI)){
                    result.setEnte(Sector.sector_inps);
                }else if (dto.getTipoDocumento().equals(RiepilogoQuoteAssociative.IQA) && dto.getSettore().equals(Sector.sector_IMPIANTIFISSI)){
                    //se si trattai iqa impianti fissi prendo la prima riga e ne recupero l'azienda
                    if(dto.getDettagli() != null)
                        if (dto.getDettagli().size() > 0){
                            if (dto.getDettagli().get(0).getFirm() != null)
                                if (!StringUtils.isEmpty(dto.getDettagli().get(0).getFirm().getDescription())){
                                    result.setEnte(dto.getDettagli().get(0).getFirm().getDescription());
                                }
                        }
                }
            }
        }


        result.setXmlFileServerPath(dto.getXmlFilename());
        result.setImportedLogFilePath(new File(logFilename).getName());






        return result;
    }



    public DettaglioQuotaAssociativa convertDettaglioFromDto(RiepilogoQuoteAssociative dto , DettaglioQuotaDTO dettaglioQuotaDTO) {
        User u = ((User) sec.getLoggedUser());


        DettaglioQuotaAssociativa dett = new DettaglioQuotaAssociativa();
        dett.setIdRiepilogoQuotaAssociativa(dto.getLid());



        dett.setDataRegistrazione(dto.getDataRegistrazione());
        dett.setDataInizio(dettaglioQuotaDTO.getDataInizio());
        dett.setDataFine(dettaglioQuotaDTO.getDataFine());
        dett.setDataDocumento(dto.getDataDocumento());
        dett.setContratto(dettaglioQuotaDTO.getContratto());
        dett.setSettore(dto.getSettore().toUpperCase());

        if (dettaglioQuotaDTO.getEnte() != null)
            dett.setEnte(dettaglioQuotaDTO.getEnte().toUpperCase());

        dett.setLivello(dettaglioQuotaDTO.getLivello());
        dett.setNote(dettaglioQuotaDTO.getNote());
        dett.setProvincia(dto.getProvincia().toUpperCase());
        dett.setQuota(dettaglioQuotaDTO.getQuota());
        dett.setTipoDocumento(dto.getTipoDocumento().toUpperCase());


        if (dettaglioQuotaDTO.getTipoPrestazione() != null)
            dett.setTipoPrestazione(dettaglioQuotaDTO.getTipoPrestazione().toUpperCase());




        Azienda a = null;
        if (dettaglioQuotaDTO.getFirm() != null){
            if (!org.apache.commons.lang.StringUtils.isEmpty(dettaglioQuotaDTO.getFirm().getDescription()))

                a = new Azienda();
            a.setId(dettaglioQuotaDTO.getFirm().getId());
        }

        if (a != null)
            dett.setIdAzienda(a.getLid());



        Lavoratore la = null;
        la = new Lavoratore();
        la.setId(dettaglioQuotaDTO.getWorker().getId());


        if (la != null){
            dett.setIdLavoratore(la.getLid());
        }



        return dett;
    }


    public RiepilogoQuoteAssociative createRiepilogoQuotaFromDto(RiepilogoQuotaDTO dto, String logFilename) {
        User u = ((User) sec.getLoggedUser());

        RiepilogoQuoteAssociative result = new RiepilogoQuoteAssociative();

        result.setDataRegistrazione(dto.getDataRegistrazione());
        result.setDataDocumento(dto.getDataDocumento());
        result.setProvincia(dto.getProvincia().toUpperCase());

        result.setCompetenza(dto.getCompentenza());
        result.setGuid(dto.getGuid());

        result.setOriginalFileServerPath(dto.getOriginalFilename());
        if (dto.getTipoDocumento() != null)
            result.setTipoDocumento(dto.getTipoDocumento().toUpperCase());
        if (dto.getSettore() != null)
            result.setSettore(dto.getSettore().toUpperCase());
        if (dto.getEnte() != null){
            result.setEnte(dto.getEnte().toUpperCase());
        }

        result.setImportedLogFilePath(new File(logFilename).getName());

        return result;
    }


    public RiepilogoQuoteAssociative saveRiepilogoQuoteManuale(RiepilogoQuotaDTO dto, String logFile) {

        RiepilogoQuoteAssociative q = createRiepilogoQuotaFromDto(dto, logFile);
        quoteRep.save(q);

        return q;
    }

}
