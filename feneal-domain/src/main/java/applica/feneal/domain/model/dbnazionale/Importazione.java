package applica.feneal.domain.model.dbnazionale;

import applica.framework.AEntity;
import applica.framework.IEntity;

import java.util.Date;

/**
 * Created by fgran on 28/04/2016.
 */
public class Importazione extends IEntity {

    //campi esistenti..................
    private String nomeProvincia;
    private int idProvincia;
    private String settore;
    private Date dataInvio;
    private String tipoInvio; //fenealgest, fenealweb, fenealconnector
    private String responsabile;
    private String tipoPeriodo;
    private int anno;
    private int periodo;
    private Date dataInizio;
    private Date dataFine;


    //campi ggiuntivi
    private boolean synched; //se fenealweb ha sincronizzato questo invio al dbnazionale
    private String dataFileUrl; //url del file originario
    private String logFileUrl;


    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public Date getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(Date dataInvio) {
        this.dataInvio = dataInvio;
    }

    public String getTipoInvio() {
        return tipoInvio;
    }

    public void setTipoInvio(String tipoInvio) {
        this.tipoInvio = tipoInvio;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
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

    public boolean isSynched() {
        return synched;
    }

    public void setSynched(boolean synched) {
        this.synched = synched;
    }

    public String getDataFileUrl() {
        return dataFileUrl;
    }

    public void setDataFileUrl(String dataFileUrl) {
        this.dataFileUrl = dataFileUrl;
    }

    public String getLogFileUrl() {
        return logFileUrl;
    }

    public void setLogFileUrl(String logFileUrl) {
        this.logFileUrl = logFileUrl;
    }
}
