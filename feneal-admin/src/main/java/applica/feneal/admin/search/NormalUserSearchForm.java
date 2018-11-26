package applica.feneal.admin.search;



import applica.framework.Entity;
import applica.framework.Filter;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.widgets.annotations.*;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

import java.util.List;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 3/4/13
 * Time: 4:18 PM
 */
@Form(NormalUserSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
@IgnoreMapping
public class NormalUserSearchForm implements Entity {
    public static final String EID = "normalusersearchform";

    private Object id;

    @FormField(description = "label.mail")
    @SearchCriteria(Filter.LIKE)
    private String mail;

    @FormField(description = "label.name")
    @SearchCriteria(Filter.LIKE)
    private String name;

    @FormField(description = "label.surname")
    @SearchCriteria(Filter.LIKE)
    private String surname;









    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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
