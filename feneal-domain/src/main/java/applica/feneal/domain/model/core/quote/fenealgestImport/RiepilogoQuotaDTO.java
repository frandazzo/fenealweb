package applica.feneal.domain.model.core.quote.fenealgestImport;

import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 16/11/2016.
 */
public class RiepilogoQuotaDTO {


    private List<DettaglioQuotaDTO> dettagli;

    public List<DettaglioQuotaDTO> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<DettaglioQuotaDTO> dettagli) {
        this.dettagli = dettagli;
    }

    private Date dataDocumento;
    private Date dataRegistrazione;
    private String tipoDocumento;
    private String settore;
    private String ente;
    private String compentenza;

    private String provincia;
    private long companyId;

    private String guid;
    private String originalFilename;
    private String xmlFilename;
    private String logFilename;


    private String note1;
    private String note2;

    private boolean updateFirmas;
    private boolean updateWorkers;
    private boolean creaDelegaIfNotExist;
    private boolean creaListaLavoro;
    private boolean associaDelega;

    private String exporterMail;
    private String exporterName;
    private int exportNumber;


    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
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

    public String getCompentenza() {
        return compentenza;
    }

    public void setCompentenza(String compentenza) {
        this.compentenza = compentenza;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getXmlFilename() {
        return xmlFilename;
    }

    public void setXmlFilename(String xmlFilename) {
        this.xmlFilename = xmlFilename;
    }

    public String getLogFilename() {
        return logFilename;
    }

    public void setLogFilename(String logFilename) {
        this.logFilename = logFilename;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public boolean isUpdateFirmas() {
        return updateFirmas;
    }

    public void setUpdateFirmas(boolean updateFirmas) {
        this.updateFirmas = updateFirmas;
    }

    public boolean isUpdateWorkers() {
        return updateWorkers;
    }

    public void setUpdateWorkers(boolean updateWorkers) {
        this.updateWorkers = updateWorkers;
    }

    public boolean isCreaDelegaIfNotExist() {
        return creaDelegaIfNotExist;
    }

    public void setCreaDelegaIfNotExist(boolean creaDelegaIfNotExist) {
        this.creaDelegaIfNotExist = creaDelegaIfNotExist;
    }

    public boolean isCreaListaLavoro() {
        return creaListaLavoro;
    }

    public void setCreaListaLavoro(boolean creaListaLavoro) {
        this.creaListaLavoro = creaListaLavoro;
    }

    public boolean isAssociaDelega() {
        return associaDelega;
    }

    public void setAssociaDelega(boolean associaDelega) {
        this.associaDelega = associaDelega;
    }

    public String getExporterMail() {
        return exporterMail;
    }

    public void setExporterMail(String exporterMail) {
        this.exporterMail = exporterMail;
    }

    public String getExporterName() {
        return exporterName;
    }

    public void setExporterName(String exporterName) {
        this.exporterName = exporterName;
    }

    public int getExportNumber() {
        return exportNumber;
    }

    public void setExportNumber(int exportNumber) {
        this.exportNumber = exportNumber;
    }
}
