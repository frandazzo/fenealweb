package applica.feneal.domain.model;

import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.geo.Province;
import applica.framework.AEntity;
import applica.framework.annotations.ManyToMany;
import applica.framework.annotations.ManyToOne;
import applica.framework.security.Role;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 28/10/13
 * Time: 17:08
 */
public class User extends AEntity implements applica.framework.security.User {

    private String username;
    private String mail;
    private String password;
    private boolean active;
    private Date registrationDate;
    private String activationCode;
    private String image;
    private String name;
    private String surname;

    private String decPass;

    public String getDecPass() {
        return decPass;
    }

    public void setDecPass(String decPass) {
        this.decPass = decPass;
    }

    private Province defaultProvince;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne
    private Company company;

    @ManyToMany
    private List<applica.feneal.domain.model.Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public List<? extends Role> getRoles() {
        return roles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Province getDefaultProvince() {
        return defaultProvince;
    }

    public void setDefaultProvince(Province defaultProvince) {
        this.defaultProvince = defaultProvince;
    }

    public void setRoles(List<applica.feneal.domain.model.Role> roles) {
        this.roles = roles;
    }

    public String getInitials() {
        if (StringUtils.hasLength(mail)) {
            return mail.substring(0, 1);
        }

        return "@";
    }

    public applica.feneal.domain.model.Role retrieveUserRole(){
        return roles.get(0);
    }

    @Override
    public String toString() {
        return mail;
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

    public String getCompleteName() {

            return String.format("%s %s", name, surname);


    }
}
