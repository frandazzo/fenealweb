package applica.feneal.admin.search;

import applica.feneal.admin.fields.renderers.OptionalCompanySelectRenderer;
import applica.framework.Entity;
import applica.framework.Filter;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.widgets.annotations.*;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/20/15
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Form(AdminUserSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
@IgnoreMapping
public class AdminUserSearchForm implements Entity {
    public static final String EID = "adminUsersearchform";

    private Object id;

    @FormField(description = "label.mail")
    @SearchCriteria(Filter.LIKE)
    private String mail;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @FormField(description = "label.company")
    @SearchCriteria(Filter.EQ)
    @FormFieldRenderer(OptionalCompanySelectRenderer.class)
    private String company;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

