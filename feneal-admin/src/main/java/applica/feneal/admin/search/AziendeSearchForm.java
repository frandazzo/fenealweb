package applica.feneal.admin.search;

import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.framework.Entity;
import applica.framework.Filter;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.widgets.annotations.*;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

/**
 * Created by fgran on 10/04/2016.
 */
@Form(AziendeSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
@IgnoreMapping
public class AziendeSearchForm implements Entity {
    public static final String EID = "aziendesearchform";

    private Object id;

    @FormField(description = "Descr.")
    @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "4")})
    @SearchCriteria(Filter.LIKE)
    private String description;

    @FormField(description = "Provincia")
    @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "4")})
    @FormFieldRenderer(OptionalProvinceFieldRenderer.class)
    @SearchCriteria(Filter.EQ)
    private String province;

    @FormField(description = "Comune")
    @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "4")})
    @FormFieldRenderer(OptionalCityFieldRenderer.class)
    @SearchCriteria(Filter.EQ)
    private String city;

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
}
