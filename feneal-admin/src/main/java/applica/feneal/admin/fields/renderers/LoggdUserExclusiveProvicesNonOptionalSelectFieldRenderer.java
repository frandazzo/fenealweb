package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.feneal.services.GeoService;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoggdUserExclusiveProvicesNonOptionalSelectFieldRenderer extends SelectFieldRenderer {


    @Autowired
    private Security security;

    @Autowired
    private ApplicationOptionRepository appOpRep;

    @Autowired
    private GeoService geo;


    @Override
    public List<SimpleItem> getItems() {

        ApplicationOptions opt = appOpRep.find(null).findFirst().orElse(null);

        if (opt == null){
            return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                    "description", "id");

        }

        //se si tratta di veneto passo solo la provincia di default
        Province prov = ((User) security.getLoggedUser()).getDefaultProvince();



        if (prov == null){
            return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                    "description", "id");
        }


        if (opt.getEnableExclusiveLiberiQuery() != null && opt.getEnableExclusiveLiberiQuery() == true){

            //poichè ci sono alcuni territori che erano inizialmente accorpati
            //come treviso e belluno
            //oppure padova rovigo
            //se la provincia risulta padova allora devo aggiungere anche la provincia rovigo e viceversa
            //se la provincia di default risulta belluno allora devo aggiungere la provincia treviso e viceversa
            List<Province> provinces = ((User) security.getLoggedUser()).getCompany()
                    .getProvinces().stream()
                    .filter(a -> a.getId() == prov.getId()).collect(Collectors.toList());


            //l'id della provincia padova è : 28
            //l'id della provincia rovigo è : 29

            //l'id della provincia treviso è : 26
            //l'id della provincia belluno è : 25


            if (prov.getIid() != 25 && prov.getIid() != 26 && prov.getIid() != 28 && prov.getIid() != 29)
                    return SimpleItem.createList(provinces,
                        "description", "id");
            else{
                //poiche si tratta di una delle provice accorpate allora devo aggiungere alla lista la provincia accorpata
                if (prov.getIid() == 25){
                    //belluno
                    //aggiungo treviso
                    provinces.add(geo.getProvinceById(26));
                } else  if (prov.getIid() == 26){
                    //treviso
                    //aggiungo belluno
                    provinces.add(geo.getProvinceById(25));
                }else  if (prov.getIid() == 28){
                    //padova
                    //aggiungo rovigo
                    provinces.add(geo.getProvinceById(29));
                }else  if (prov.getIid() == 29){
                    //rovigo
                    //aggiungo padova
                    provinces.add(geo.getProvinceById(28));
                }

                return SimpleItem.createList(provinces,
                        "description", "id");
            }
        }


        return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                "description", "id");


    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        if (value == null) {
            Province prov = ((User) security.getLoggedUser()).getDefaultProvince();

            value = (prov == null)? null : prov.getIid();
        } else {
            Province prov = (Province) value;
            value = prov.getIid();
        }


        super.render(writer, field, value);
    }
}
