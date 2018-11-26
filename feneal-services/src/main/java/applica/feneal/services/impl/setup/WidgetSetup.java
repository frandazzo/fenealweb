package applica.feneal.services.impl.setup;

import applica.feneal.domain.data.core.WidgetRepository;
import applica.feneal.domain.model.core.Widget;
import applica.framework.LoadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 13/06/2016.
 */
@Component
public class WidgetSetup implements  AppSetup {

    @Autowired
    private WidgetRepository wRep;


    @Override
    public void setup() {

        Widget w0 = findWidgetByUrl("widget/real/sindacalizzazione/");
        if (w0 == null){
            w0 = new Widget();
            w0.setDescription("Rappresentanza");
            w0.setType("locale");
            w0.setContext("dashboard");
            w0.setDefaultPosition(1);
            w0.setUrl("widget/real/sindacalizzazione/");

            wRep.save(w0);

        }

        Widget w1 = findWidgetByUrl("widget/real/contatoreIscritti/");
        if (w1 == null){
            w1 = new Widget();
            w1.setDescription("ContatoreIscritti");
            w1.setType("locale");
            w1.setContext("dashboard");
            w1.setDefaultPosition(2);
            w1.setUrl("widget/real/contatoreIscritti/");

            wRep.save(w1);

        }

        Widget w11 = findWidgetByUrl("widget/real/contatoreIscrittiEdile/");
        if (w11 == null){
            w11 = new Widget();
            w11.setDescription("ContatoreIscrittiEdile");
            w11.setType("locale");
            w11.setContext("dashboard");
            w11.setDefaultPosition(3);
            w11.setUrl("widget/real/contatoreIscrittiEdile/");

            wRep.save(w11);

        }

        Widget wa = findWidgetByUrl("widget/real/contatoreIscrittiTerritorioAccorpato/");
        if (wa == null){
            wa = new Widget();
            wa.setDescription("ContatoreIscrittiTerritorioAccorpato");
            wa.setType("locale");
            wa.setContext("dashboard");
            wa.setDefaultPosition(1);
            wa.setUrl("widget/real/contatoreIscrittiTerritorioAccorpato/");

            wRep.save(wa);

        }


        Widget wa1 = findWidgetByUrl("widget/real/andamentoIscrittiPerTerritorioAccorpato/");
        if (wa1 == null){
            wa1 = new Widget();
            wa1.setDescription("AndamentoIscrittiTerritorioAccorpato");
            wa1.setType("locale");
            wa1.setContext("dashboard");
            wa1.setDefaultPosition(1);
            wa1.setUrl("widget/real/andamentoIscrittiPerTerritorioAccorpato/");

            wRep.save(wa1);

        }


        Widget wa11 = findWidgetByUrl("widget/real/andamentoIscrittiPerProvincia/");
        if (wa11 == null){
            wa11 = new Widget();
            wa11.setDescription("AndamentoIscrittiProvincia");
            wa11.setType("locale");
            wa11.setContext("dashboard");
            wa11.setDefaultPosition(2);
            wa11.setUrl("widget/real/andamentoIscrittiPerProvincia/");

            wRep.save(wa11);

        }


        Widget wa1w1 = findWidgetByUrl("widget/real/andamentoIscrittiPerSettoreEdile/");
        if (wa1w1 == null){
            wa1w1 = new Widget();
            wa1w1.setDescription("AndamentoIscrittiPerSettoreEdile");
            wa1w1.setType("locale");
            wa1w1.setContext("dashboard");
            wa1w1.setDefaultPosition(3);
            wa1w1.setUrl("widget/real/andamentoIscrittiPerSettoreEdile/");

            wRep.save(wa1w1);

        }


        Widget wa1w11 = findWidgetByUrl("widget/real/noniscritticlassifica/");
        if (wa1w11 == null){
            wa1w11 = new Widget();
            wa1w11.setDescription("ClassificaNonIscritti");
            wa1w11.setType("locale");
            wa1w11.setContext("dashboard");
            wa1w11.setDefaultPosition(2);
            wa1w11.setUrl("widget/real/noniscritticlassifica/");

            wRep.save(wa1w11);

        }




    }

    private Widget findWidgetByUrl(String s) {
        LoadRequest req = LoadRequest.build().filter("url", s);
        return wRep.find(req).findFirst().orElse(null);
    }


}
