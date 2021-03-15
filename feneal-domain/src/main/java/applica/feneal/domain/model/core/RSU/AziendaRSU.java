package applica.feneal.domain.model.core.RSU;

import applica.framework.AEntity;

import java.util.Date;

/**
 * Created by felicegramegna on 22/02/2021.
 */

public class AziendaRSU extends AEntity {

    private String description;
    private String city;
    private String province;
    private String cap;
    private String address;
    private String phone;
    private String piva;
    private String cf;
    private String notes;

    private String companyCreator;
    private String usernameCreator;
    private Date createDate;
    private String companyLastModification;
    private String usernameLastModification;
    private Date lastModificationDate;

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getCompanyCreator() {
        return companyCreator;
    }

    public void setCompanyCreator(String companyCreator) {
        this.companyCreator = companyCreator;
    }

    public String getUsernameCreator() {
        return usernameCreator;
    }

    public void setUsernameCreator(String usernameCreator) {
        this.usernameCreator = usernameCreator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompanyLastModification() {
        return companyLastModification;
    }

    public void setCompanyLastModification(String companyLastModification) {
        this.companyLastModification = companyLastModification;
    }

    public String getUsernameLastModification() {
        return usernameLastModification;
    }

    public void setUsernameLastModification(String usernameLastModification) {
        this.usernameLastModification = usernameLastModification;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
