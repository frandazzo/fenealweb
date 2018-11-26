package applica.feneal.services.impl.setup;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.framework.LoadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 13/06/2016.
 */
@Component
public class EntiSetup implements AppSetup{

    @Autowired
    private ParitheticRepository parRep;



    @Override
    public void setup() {

        //cerco tutti gli enti e se non li trovo li aggiungo
        Paritethic p0 = findParitethic(Paritethic.ente_calec);
        if (p0 == null){
            p0 = new Paritethic(Paritethic.ente_calec,Paritethic.ente_calec);
            parRep.save(p0);
        }

        Paritethic p1 = findParitethic(Paritethic.ente_cassaedile);
        if (p1 == null){
            p1 = new Paritethic(Paritethic.ente_cassaedile,Paritethic.ente_cassaedile);
            parRep.save(p1);
        }

        Paritethic p2 = findParitethic(Paritethic.ente_cea);
        if (p2 == null){
            p2 = new Paritethic(Paritethic.ente_cea,Paritethic.ente_cea);
            parRep.save(p2);
        }

        Paritethic p3 = findParitethic(Paritethic.ente_cec);
        if (p3 == null){
            p3 = new Paritethic(Paritethic.ente_cec,Paritethic.ente_cec);
            parRep.save(p3);
        }

        Paritethic p4 = findParitethic(Paritethic.ente_ceda);
        if (p4 == null){
            p4 = new Paritethic(Paritethic.ente_ceda,Paritethic.ente_ceda);
            parRep.save(p4);
        }


        Paritethic p5 = findParitethic(Paritethic.ente_cedaf);
        if (p5 == null){
            p5 = new Paritethic(Paritethic.ente_cedaf,Paritethic.ente_cedaf);
            parRep.save(p5);
        }

        Paritethic p6 = findParitethic(Paritethic.ente_cedaiier);
        if (p6 == null){
            p6 = new Paritethic(Paritethic.ente_cedaiier,Paritethic.ente_cedaiier);
            parRep.save(p6);
        }

        Paritethic p7 = findParitethic(Paritethic.ente_cedam);
        if (p7 == null){
            p7 = new Paritethic(Paritethic.ente_cedam,Paritethic.ente_cedam);
            parRep.save(p7);
        }

        Paritethic p8 = findParitethic(Paritethic.ente_celcof);
        if (p8 == null){
            p8 = new Paritethic(Paritethic.ente_celcof,Paritethic.ente_celcof);
            parRep.save(p8);
        }

        Paritethic p9 = findParitethic(Paritethic.ente_cema);
        if (p9 == null){
            p9 = new Paritethic(Paritethic.ente_cema,Paritethic.ente_cema);
            parRep.save(p9);
        }

        Paritethic p11 = findParitethic(Paritethic.ente_cert);
        if (p11 == null){
            p11 = new Paritethic(Paritethic.ente_cert,Paritethic.ente_cert);
            parRep.save(p11);
        }

        Paritethic p22 = findParitethic(Paritethic.ente_ceva);
        if (p22 == null){
            p22 = new Paritethic(Paritethic.ente_ceva,Paritethic.ente_ceva);
            parRep.save(p22);
        }

        Paritethic p33 = findParitethic(Paritethic.ente_falea);
        if (p33 == null){
            p33 = new Paritethic(Paritethic.ente_falea,Paritethic.ente_falea);
            parRep.save(p33);
        }

        Paritethic p44 = findParitethic(Paritethic.ente_edilcassa);
        if (p44 == null){
            p44 = new Paritethic(Paritethic.ente_edilcassa,Paritethic.ente_edilcassa);
            parRep.save(p44);
        }


    }

    private Paritethic findParitethic(String ente) {
        LoadRequest req = LoadRequest.build().filter("type",ente);

        return parRep.find(req).findFirst().orElse(null);
    }
}
