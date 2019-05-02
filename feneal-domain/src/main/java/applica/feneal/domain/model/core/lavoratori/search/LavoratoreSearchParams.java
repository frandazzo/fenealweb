package applica.feneal.domain.model.core.lavoratori.search;

import org.apache.commons.lang.StringUtils;

/**
 * Created by fgran on 07/04/2016.
 */
public class LavoratoreSearchParams {

    private String name;
    private String surname;
    private String fiscalcode;
    private String namesurname;

    // ulteriori campi di ricerca per la ricerca su db nazionale
    private String year;
    private Long sectorId;
    private Long companyId;
    private String sex;
    private String birthDate;
    private String nationality;
    private String livingProvince;
    private String livingCity;


    private Integer page;
    private String cell;
    private String company;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public String getNamesurname() {
        return namesurname;
    }

    public void setNamesurname(String namesurname) {
        this.namesurname = namesurname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (sex.equals("M"))
            this.sex = "MASCHIO";
        else
            this.sex = "FEMMINA";
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLivingProvince() {
        return livingProvince;
    }

    public void setLivingProvince(String livingProvince) {
        this.livingProvince = livingProvince;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean isEmpty() {
        if (StringUtils.isEmpty(name) &&
                StringUtils.isEmpty(surname) &&
                StringUtils.isEmpty(namesurname))
            return true;

        return false;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getCell() {
        return cell;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }
}
