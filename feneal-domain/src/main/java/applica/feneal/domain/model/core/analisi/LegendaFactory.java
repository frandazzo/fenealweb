package applica.feneal.domain.model.core.analisi;

import java.util.ArrayList;

/**
 * Created by fgran on 03/01/2018.
 */
public class LegendaFactory {

    public static Legenda constructLegenda(){

        Legenda l = new Legenda();
        l.setItems(new ArrayList<>());

        LegendaItem i1 = new LegendaItem();
        i1.setLabel("Da 0 a 1000 Iscritti");
        i1.setValMax(1000);
        i1.setValMin(0);
        l.getItems().add(i1);

        LegendaItem i2 = new LegendaItem();
        i2.setLabel("Da 1000 a 2000 Iscritti");
        i2.setValMax(2000);
        i2.setValMin(1001);
        l.getItems().add(i2);


        LegendaItem i3 = new LegendaItem();
        i3.setLabel("Da 2000 a 3000 Iscritti");
        i3.setValMax(3000);
        i3.setValMin(2001);
        l.getItems().add(i3);


        LegendaItem i4 = new LegendaItem();
        i4.setLabel("Da 3000 a 4000 Iscritti");
        i4.setValMax(4000);
        i4.setValMin(3001);
        l.getItems().add(i4);

        LegendaItem i5 = new LegendaItem();
        i5.setLabel("Da 4000 a 5000 Iscritti");
        i5.setValMax(5000);
        i5.setValMin(4001);
        l.getItems().add(i5);

        LegendaItem i6 = new LegendaItem();
        i6.setLabel("Da 5000 a 6000 Iscritti");
        i6.setValMax(6000);
        i6.setValMin(5001);
        l.getItems().add(i6);

        LegendaItem i7 = new LegendaItem();
        i7.setLabel("Da 6000 a 10000 Iscritti");
        i7.setValMax(10000);
        i7.setValMin(6001);
        l.getItems().add(i7);

        LegendaItem i8 = new LegendaItem();
        i8.setLabel("Oltre 10.000 Iscritti");
        i8.setValMax(200000);
        i8.setValMin(10001);
        l.getItems().add(i8);
        return  l;
    }
}
