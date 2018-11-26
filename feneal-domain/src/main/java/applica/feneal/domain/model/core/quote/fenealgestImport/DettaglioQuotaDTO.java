package applica.feneal.domain.model.core.quote.fenealgestImport;

import java.util.Date;

/**
 * Created by fgran on 16/11/2016.
 */
public class DettaglioQuotaDTO {

    private FirmDTO firm;
    private WorkerDTO worker;


    private String provincia;
    private Date dataRegistrazione;
    private Date dataDocumento;
    private String tipoDocumento;
    private String settore;
    private String ente;
    private Date dataInizio;
    private Date dataFine;
    private double quota;
    private String livello;
    private String contratto;
    private String tipoPrestazione;
    private String note;

    public FirmDTO getFirm() {
        return firm;
    }

    public void setFirm(FirmDTO firm) {
        this.firm = firm;
    }

    public WorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(WorkerDTO worker) {
        this.worker = worker;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public double getQuota() {
        return quota;
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public String getTipoPrestazione() {
        return tipoPrestazione;
    }

    public void setTipoPrestazione(String tipoPrestazione) {
        this.tipoPrestazione = tipoPrestazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
