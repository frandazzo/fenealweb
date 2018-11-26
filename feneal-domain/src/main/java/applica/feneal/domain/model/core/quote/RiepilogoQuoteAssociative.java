package applica.feneal.domain.model.core.quote;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by angelo on 05/05/16.
 */
public class RiepilogoQuoteAssociative extends SecuredDomainEntity {

    public static final String IQA = "IQA";
    public static final String IQI = "IQI";
    public static final String IQP = "IQP";

    private Date dataRegistrazione;
    private Date dataDocumento;
    private String tipoDocumento;
    private String settore;
    private String ente;
    private String competenza;

    //provincia di riferimento dell'incasso
    private String provincia;
    //identificativo univoco dell'incasso quote
    //tale identificativo è necessario durante i meccanismi di creazione delgli incassi
    //lato fenealgest
    private String guid;
    private String originalFileServerPath;
    private String xmlFileServerPath;
    private String importedLogFilePath;

    private transient String xmlFileServerName;
    private transient String  originalFileServerName;

//    public void setXmlFileServerName(String xmlFileServerName) {
//        this.xmlFileServerName = xmlFileServerName;
//    }
//
//    public void setOriginalFileServerName(String originalFileServerName) {
//        this.originalFileServerName = originalFileServerName;
//    }

    public String getXmlFileServerName() {
        if (StringUtils.isEmpty(xmlFileServerPath))
            return "";
        return new File(xmlFileServerPath).getName();

    }

    public String getOriginalFileServerName() {
        if (StringUtils.isEmpty(originalFileServerPath))
            return "";
        return new File(originalFileServerPath).getName();
    }

    public String getXmlFileServerPath() {
        return xmlFileServerPath;
    }

    public void setXmlFileServerPath(String xmlFileServerPath) {
        this.xmlFileServerPath = xmlFileServerPath;
    }
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getOriginalFileServerPath() {
        return originalFileServerPath;
    }

    public void setOriginalFileServerPath(String originalFileServerPath) {
        this.originalFileServerPath = originalFileServerPath;
    }

    public String getImportedLogFilePath() {
        return importedLogFilePath;
    }

    public void setImportedLogFilePath(String importedLogFilePath) {
        this.importedLogFilePath = importedLogFilePath;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getCompetenza() {
        return competenza;
    }

    public void setCompetenza(String competenza) {
        this.competenza = competenza;
    }

    public static RiepilogoQuoteAssociative createFromImportazione(Importazione impotazioneDbNazionale, String ente, String logFileName, long companyId) {
        RiepilogoQuoteAssociative a = new RiepilogoQuoteAssociative();
        a.setCompanyId(companyId);
        a.setSettore(impotazioneDbNazionale.getSettore());
        a.setDataDocumento(impotazioneDbNazionale.getDataFine());
        a.setDataRegistrazione(new Date());

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        a.setCompetenza(String.format("%s - %s", f.format(impotazioneDbNazionale.getDataInizio()),f.format(impotazioneDbNazionale.getDataFine())));
        a.setEnte(ente);
        a.setGuid(UUID.randomUUID().toString());
        a.setImportedLogFilePath(logFileName);
        a.setProvincia(impotazioneDbNazionale.getNomeProvincia());
        if (impotazioneDbNazionale.getSettore().equals(Sector.sector_inps))
            a.setTipoDocumento(RiepilogoQuoteAssociative.IQI);
        else
            a.setTipoDocumento(RiepilogoQuoteAssociative.IQA);


        return a;
    }

    public String calculateDataInizio(){
        if (StringUtils.isEmpty(competenza))
            return "";

        String[] l = competenza.split("-");
        if (l.length == 2)
            return l[0].trim();

        return "";
    }

    public String calculateDataFine(){
        if (StringUtils.isEmpty(competenza))
            return "";

        String[] l = competenza.split("-");
        if (l.length == 2)
            return l[1].trim();

        return "";
    }
}
