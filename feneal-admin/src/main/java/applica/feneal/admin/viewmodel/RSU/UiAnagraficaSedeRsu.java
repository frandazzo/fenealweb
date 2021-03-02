package applica.feneal.admin.viewmodel.RSU;

public class UiAnagraficaSedeRsu {
    private long id;
    private String description;
    private String province;
    private String city;
    private String address;
    private long firmId;

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

    public long getFirmId() {
        return firmId;
    }

    public void setFirmId(long firmId) {
        this.firmId = firmId;
    }
}
