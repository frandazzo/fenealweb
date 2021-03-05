package applica.feneal.admin.viewmodel.RSU;

import java.util.Date;

/**
 * Created by felicegramegna on 23/02/2021.
 */
public class UiAnagraficaAziendaRsu {
    private long id;
    private String description;
    private String province;
    private String city;
    private String address;
    private String cap;
    private String notes;

    private String phone;
    private String piva;
    private String cf;

    private String companyCreator;
    private String usernameCreator;
    private String createDate;
    private String companyLastModification;
    private String usernameLastModification;
    private String lastModificationDate;

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
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



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
