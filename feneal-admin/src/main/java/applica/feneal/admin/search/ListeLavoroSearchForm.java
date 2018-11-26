package applica.feneal.admin.search;

import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.framework.Entity;
import applica.framework.Filter;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.widgets.annotations.*;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

/**
 * Created by angelo on 11/06/2016.
 */
@Form(ListeLavoroSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
@IgnoreMapping
public class ListeLavoroSearchForm implements Entity {
    public static final String EID = "listelavorosearchform";

    private Object id;

    @FormField(description = "Descrizione")
    @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "12")})
    @SearchCriteria(Filter.LIKE)
    private String description;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
