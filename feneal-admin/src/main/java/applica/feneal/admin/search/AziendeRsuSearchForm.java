package applica.feneal.admin.search;

import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.framework.Entity;
import applica.framework.Filter;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.widgets.annotations.*;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

/**
 * Created by felicegramegna on 03/03/2021.
 */

@Form(AziendeSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
@IgnoreMapping
public class AziendeRsuSearchForm  implements Entity {
        public static final String EID = "aziendesearchform";

        private Object id;

        @FormField(description = "Descr.")
        @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "6")})
        @SearchCriteria(Filter.LIKE)
        private String description;

        @FormField(description = "Provincia")
        @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "3")})
        @FormFieldRenderer(OptionalProvinceFieldRenderer.class)
        @SearchCriteria(Filter.EQ)
        private String province;

        @FormField(description = "Comune")
        @Params({@Param(key = "row", value = "dt"), @Param(key = "cols", value = "3")})
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

        @FormField(description = "Partita iva.")
        @Params({@Param(key = "row", value = "dt1"), @Param(key = "cols", value = "6")})
        @SearchCriteria(Filter.LIKE)
        private  String piva;

        @FormField(description = "Cod.Fiscale")
        @Params({@Param(key = "row", value = "dt1"), @Param(key = "cols", value = "6")})
        @SearchCriteria(Filter.LIKE)
        private String cf;

        public String getCf() {
            return cf;
        }

        public void setCf(String cf) {
            this.cf = cf;
        }

        public String getPiva() {
                return piva;
            }

        public void setPiva(String piva) {
            this.piva = piva;
        }

        public void setCity(String city) {
            this.city = city;
        }
}

