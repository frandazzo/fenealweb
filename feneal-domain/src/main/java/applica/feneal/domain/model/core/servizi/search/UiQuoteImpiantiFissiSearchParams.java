package applica.feneal.domain.model.core.servizi.search;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.geo.Province;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiQuoteImpiantiFissiSearchParams {

    private String province;
    private String firm;
    private String competenceMonth;
    private String competenceYear;
    private String dataInizio;
    private String dataFine;
    private Double amount;
    private String contract;


    private Azienda completeFirm;
    private Province completeProvince;

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

    public String getCompetenceMonth() {
        return competenceMonth;
    }

    public void setCompetenceMonth(String competenceMonth) {
        this.competenceMonth = competenceMonth;
    }

    public String getCompetenceYear() {
        return competenceYear;
    }

    public void setCompetenceYear(String competenceYear) {
        this.competenceYear = competenceYear;
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

