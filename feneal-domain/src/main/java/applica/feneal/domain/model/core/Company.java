package applica.feneal.domain.model.core;

import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.framework.AEntity;
import applica.framework.annotations.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class Company extends AEntity {


    private String description;
    @ManyToMany
    private List<Province> provinces;
    //credenziali db nazionale
    private String nationaUsername;
    private String nationUserPassword;

    //opzioni per l'invio degli sms
    private String smsUsername;
    private String smsPassword;
    private String smsSenderNumber;
    private String smsSenderAlias;

    // mail per notificazioni
    private String notificationMail;


    private String segretarioCell;
    private String segretarioMail;

    public String getSegretarioMail() {
        return segretarioMail;
    }

    public void setSegretarioMail(String segretarioMail) {
        this.segretarioMail = segretarioMail;
    }

    public String getSegretarioCell() {
        return segretarioCell;
    }

    public void setSegretarioCell(String segretarioCell) {
        this.segretarioCell = segretarioCell;
    }

    //campo per la gestione delle riprese dati
    private String automaticRipresaDati;

    public String getAutomaticRipresaDati() {
        return automaticRipresaDati;
    }

    public void setAutomaticRipresaDati(String automaticRipresaDati) {
        this.automaticRipresaDati = automaticRipresaDati;
    }

    //funzionalità disponibili
    private List<VisibleFunction> visibleFunctions;


    public List<VisibleFunction> getVisibleFunctions() {
        return visibleFunctions;
    }

    public void setVisibleFunctions(List<VisibleFunction> visibleFunctions) {
        this.visibleFunctions = visibleFunctions;
    }

    public String getSmsUsername() {
        return smsUsername;
    }

    public void setSmsUsername(String smsUsername) {
        this.smsUsername = smsUsername;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

    public String getSmsSenderNumber() {
        return smsSenderNumber;
    }

    public void setSmsSenderNumber(String smsSenderNumber) {
        this.smsSenderNumber = smsSenderNumber;
    }

    public String getSmsSenderAlias() {
        return smsSenderAlias;
    }

    public void setSmsSenderAlias(String smsSenderAlias) {
        this.smsSenderAlias = smsSenderAlias;
    }

    public String getNationUserPassword() {
        return nationUserPassword;
    }

    public void setNationUserPassword(String nationUserPassword) {
        this.nationUserPassword = nationUserPassword;
    }

    public String getNationaUsername() {
        return nationaUsername;
    }

    public void setNationaUsername(String nationaUsername) {
        this.nationaUsername = nationaUsername;
    }

    public String getNotificationMail() {
        return notificationMail;
    }

    public void setNotificationMail(String notificationMail) {
        this.notificationMail = notificationMail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }


    @Override
    public String toString() {
        return this.description;
    }

    public boolean containProvince(String provinceName) {
        for (Province province : provinces) {
            if (province.getDescription().toLowerCase().equals(provinceName.toLowerCase()))
                return true;
        }

        return false;
    }

    public int  getRegionId() {
        return getProvinces().stream().findFirst().get().getIdRegion();
    }
}
