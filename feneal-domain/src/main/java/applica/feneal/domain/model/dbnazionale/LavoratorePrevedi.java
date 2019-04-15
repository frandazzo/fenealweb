package applica.feneal.domain.model.dbnazionale;

import applica.framework.AEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LavoratorePrevedi extends AEntity {


    private String name;
    private String surname;
    private Date birthDate;
    private String fiscalcode;
    private String livingCity;
    private String livingProvince;
    private String address;
    private String cap;
    private String codCassa;
    private String inquadramento;
    private String cassaEdile;
    private String cassaEdileRegione;
    private String tipoAdesione;
    private int anno;
    private int numIscrizioni;
    private List<Iscrizione> iscrizioni = new ArrayList<>();


    public int getNumIscrizioni() {
        return numIscrizioni;
    }

    public void setNumIscrizioni(int numIscrizioni) {
        this.numIscrizioni = numIscrizioni;
    }



    public List<Iscrizione> getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(List<Iscrizione> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {

        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingProvince(String livingProvince) {
        this.livingProvince = livingProvince;
    }

    public String getLivingProvince() {
        return livingProvince;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCap() {
        return cap;
    }

    public void setCodCassa(String codCassa) {
        this.codCassa = codCassa;
    }

    public String getCodCassa() {
        return codCassa;
    }

    public void setInquadramento(String inquadramento) {
        this.inquadramento = inquadramento;
    }

    public String getInquadramento() {
        return inquadramento;
    }

    public void setCassaEdile(String cassaEdile) {
        this.cassaEdile = cassaEdile;
    }

    public String getCassaEdile() {
        return cassaEdile;
    }

    public void setCassaEdileRegione(String cassaEdileRegione) {
        this.cassaEdileRegione = cassaEdileRegione;
    }

    public String getCassaEdileRegione() {
        return cassaEdileRegione;
    }

    public void setTipoAdesione(String tipoAdesione) {
        this.tipoAdesione = tipoAdesione;
    }

    public String getTipoAdesione() {
        return tipoAdesione;
    }
}
