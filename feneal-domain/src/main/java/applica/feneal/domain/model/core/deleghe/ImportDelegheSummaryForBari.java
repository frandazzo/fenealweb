package applica.feneal.domain.model.core.deleghe;

import java.util.Date;

/**
 * Created by fgran on 03/02/2017.
 */
public class ImportDelegheSummaryForBari {

//    template.add("DATA PROT.");
//    template.add("NUM. PROT.");
//    template.add("DECORRENZA");
//    template.add("ISCRIZIONE / ISCR.INEFF");
//    template.add("REVOCA");
//    template.add("DOPPIONE");
//    template.add("ANOMALE");

    private String protocol;
    private Date protocolDate;
    private String decorrenza;
    private String subscription;
    private String revoke;
    private String doubleDel;
    private String anomalia;
    private String azienda;
    private String ultimoMovimento;

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getUltimoMovimento() {
        return ultimoMovimento;
    }

    public void setUltimoMovimento(String ultimoMovimento) {
        this.ultimoMovimento = ultimoMovimento;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getProtocolDate() {
        return protocolDate;
    }

    public void setProtocolDate(Date protocolDate) {
        this.protocolDate = protocolDate;
    }

    public String getDecorrenza() {
        return decorrenza;
    }

    public void setDecorrenza(String decorrenza) {
        this.decorrenza = decorrenza;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getRevoke() {
        return revoke;
    }

    public void setRevoke(String revoke) {
        this.revoke = revoke;
    }

    public String getDoubleDel() {
        return doubleDel;
    }

    public void setDoubleDel(String doubleDel) {
        this.doubleDel = doubleDel;
    }

    public String getAnomalia() {
        return anomalia;
    }

    public void setAnomalia(String anomalia) {
        this.anomalia = anomalia;
    }
}
