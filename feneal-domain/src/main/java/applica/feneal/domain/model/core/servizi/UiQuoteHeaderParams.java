package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.geo.Province;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiQuoteHeaderParams {

    private String province;
    private String settore;
    private String ente;
    private String dataInizio;
    private String dataFine;
    private String firm;
    private String worker;

    private String level;
    private String notes;



    private Double amount;
    private String contract;


    private Azienda completeFirm;
    private Province completeProvince;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public Province getCompleteProvince() {
        return completeProvince;
    }

    public void setCompleteProvince(Province completeProvince) {
        this.completeProvince = completeProvince;
    }

    public Azienda getCompleteFirm() {
        return completeFirm;
    }

    public void setCompleteFirm(Azienda completeFirm) {
        this.completeFirm = completeFirm;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }



    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }
}

