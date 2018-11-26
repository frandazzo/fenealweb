package applica.feneal.services.impl.setup.options;

import applica.feneal.domain.data.core.WidgetRepository;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.services.WidgetService;
import applica.framework.Conjunction;
import applica.framework.Disjunction;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/04/2016.
 */
@Service
public class WidgetServiceImpl implements WidgetService {

    @Autowired
    private WidgetRepository wiRep;

    @Override
    public List<Widget> getWidgetByRoleId(long loggedUserRoleId, String context) {
        //il contesto puo avere i valori di dashboard, worker  o firm
        //mentre il type rappresenta  i valori locale, regionale e nazionale oppure all

        String tipoRuolo = "";
        //recupero il tipo do filtro da applicare ai widgets oin base al ruolo
        if (loggedUserRoleId == 3 || loggedUserRoleId == 4 || loggedUserRoleId == 5 ){
            //ruoli id funzionario1 e 2 e segretario
            tipoRuolo = Widget.widget_locale;
        }else if (loggedUserRoleId == 6){
            //regionale
            tipoRuolo = Widget.widget_regionale;
        }else if (loggedUserRoleId == 7){
            tipoRuolo = Widget.widget_nazionale;
        }else
            return new ArrayList<>(); //non ritorno nulla negli altri casi



        Disjunction c = new Disjunction();

        Filter f1 = new Filter("type", tipoRuolo, Filter.EQ);
        Filter f2 = new Filter("type", Widget.widget_all, Filter.EQ);

        List<Filter> f = new ArrayList<>();
        f.add(f1);
        f.add(f2);

        c.setChildren(f);

        LoadRequest req = LoadRequest.build();
        req.getFilters().add(c);

        List<Widget> result = wiRep.find(req).getRows();


        //prendo tutti i widgets di un determinato contest
        return result.stream().filter(g-> g.getContext().equals(context) ).collect(Collectors.toList());


    }


}
