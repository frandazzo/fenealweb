package applica.feneal.admin.fields.renderers.geo;

import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.model.geo.City;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;

import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComuneProvniciaBariSelectFieldRenderer extends SelectFieldRenderer {


    @Autowired
    CitiesRepository citiesRepository;


    @Override
    public List<SimpleItem> getItems(){

        List<City> citta = citiesRepository.find(LoadRequest.build().filter("idProvince", 72)).getRows();
        return SimpleItem.createList(citta, (a)-> a.getDescription(),(a)-> a.getDescription());
    }
}
