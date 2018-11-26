package applica.feneal.admin.facade;

import applica.feneal.admin.data.widget.WidgetsActiveUser;
import applica.feneal.admin.viewmodel.app.dashboard.AppIscrittiPerSettore;
import applica.feneal.admin.viewmodel.app.dashboard.AppSindacalizzazione;
import applica.feneal.admin.viewmodel.app.dashboard.IscrittiSindacato;
import applica.feneal.admin.viewmodel.options.UiEnableWidget;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.admin.viewmodel.widget.RowDouble;
import applica.feneal.admin.viewmodel.widget.RowInteger;
import applica.feneal.admin.viewmodel.widget.RowListInteger;
import applica.feneal.admin.viewmodel.widget.real.UIAndamentoIscrittiPerProvincia;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.core.aziende.NonIscrittiAzienda;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerEnte;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerSettore;
import applica.feneal.domain.model.core.report.RiepilogoIscrizione;
import applica.feneal.domain.model.core.report.Sindacalizzaizone;
import applica.feneal.domain.model.setting.option.UserOptions;
import applica.feneal.domain.model.setting.option.UserWidgetOption;
import applica.feneal.services.UserOptionsService;
import applica.feneal.services.WidgetService;
import applica.feneal.services.impl.report.StatisticServiceImpl;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by david on 02/04/2016.
 */
@Component
public class WidgetFacade {

    @Autowired
    private Security sec;

    @Autowired
    private WidgetService wsvc;

    @Autowired
    private UserOptionsService optSvc;

    @Autowired
    private StatisticServiceImpl statisticService;

    //il contesto puo avere i valori di dashboard, worker  o firm
    public UiWidgetManager getEnabledWidgets(String context) {

        User u = ((User) sec.getLoggedUser());

        if (u.retrieveUserRole().getLid() == 1 || u.retrieveUserRole().getLid() == 2 )
            return new UiWidgetManager();

        //recupero tutti i widgets per l'utente e per la dashboard
        List<Widget> all = wsvc.getWidgetByRoleId(u.retrieveUserRole().getLid(),context );
        //recupero le userOptions per verificare se ci sono tutti i widgets

        UserOptions opt = optSvc.getUserOptionOrCreateIt(u);

        List<UiEnableWidget> result = new ArrayList<>();

        for (Widget widget : all) {
            UiEnableWidget w = new UiEnableWidget();
            w.setWidgetName(widget.getDescription());
            w.setLongDescription(widget.getLongDescription());
            boolean present = opt.contains(widget, context);
            w.setPresent(present);

            if (!present){

                w.setParams(widget.getDefaultParams());
                w.setPosition(widget.getDefaultPosition());
                w.setUrl(widget.getUrl());
                w.setId(widget.getLid());
            }else{
                UserWidgetOption option = opt.findWidgetOptions(widget.getDescription(), context);
                w.setParams(option.getWidgetParams());
                w.setPosition(option.getWidgetPosition());
                w.setUrl(option.getWidget().getUrl());
                w.setId(option.getWidget().getLid());
            }
            result.add(w);

        }

        UiWidgetManager m = new UiWidgetManager();
        m.setWidgets(result);
        m.setLayout(opt.getLayout(context));

        return m;
    }

    public Sindacalizzaizone getSindacalizzazioneData(){

        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/sindacalizzazione/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);

        String province = findPropertyInParams("province", widget.getParams());
        String ente = findPropertyInParams("ente", widget.getParams());

        return statisticService.getSindacalizzazione(u.getLid(), province,ente);
    }

    public AppSindacalizzazione getSindacalizzazioneData(String province, String ente){

        User u = ((User) sec.getLoggedUser());

        Sindacalizzaizone r = statisticService.getSindacalizzazione(u.getLid(), province,ente);

        List<IscrittiSindacato> result = new ArrayList<>();

        if (r.getIscrittiFeneal() > 0)
        {
            IscrittiSindacato ss = new IscrittiSindacato();
            ss.setSindacato("Feneal");
            ss.setIscritti(r.getIscrittiFeneal());
            result.add(ss);
        }

        if (r.getIscrittiFilca() > 0)
        {
            IscrittiSindacato ss = new IscrittiSindacato();
            ss.setSindacato("Filca");
            ss.setIscritti(r.getIscrittiFilca());
            result.add(ss);
        }

        if (r.getIscrittiFilca() > 0)
        {
            IscrittiSindacato ss = new IscrittiSindacato();
            ss.setSindacato("Fillea");
            ss.setIscritti(r.getIscrittiFillea());
            result.add(ss);
        }


        AppSindacalizzazione res = new AppSindacalizzazione();
        res.setIscritti(result);
        res.setLiberi(r.getLiberi());
        res.setTassoSindacalizzazione(r.getTassoSindacalizzazione());
        res.setProvincia(r.getProvincia());
        res.setEnte(r.getEnte());
        return res;
    }



    private String findPropertyInParams(String propName, String params) {
        if (StringUtils.isEmpty(params))
            return null;

        try{

            //qui deserializzo la stringa
            String[] paramData = params.split("#");
            for (String s : paramData) {
                if (!StringUtils.isEmpty(s)){

                    String[] props = s.split("@");
                    if (props.length == 2){
                        String name = props[0].replace("name=", "");
                        String value  = props[1].replace("value=", "");
                        if (name.equals(propName))
                            return value;
                    }
                }
            }
            return  null;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RiepilogoIscrittiPerEnte> getContatoreIscrittiPerSettoreEdile() {
        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/contatoreIscrittiEdile/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);

        String province = findPropertyInParams("province", widget.getParams());
        String anno = findPropertyInParams("year", widget.getParams());
        Integer a = null;
        if (!StringUtils.isEmpty(anno))
            a = Integer.parseInt(anno);


        return statisticService.getContatoreIscrittiPerSettoreEdile(null, a, province);
    }

    public RiepilogoIscrittiPerSettore getContatoreIscritti(){

        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/contatoreIscritti/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);

        String province = findPropertyInParams("province", widget.getParams());
        String anno = findPropertyInParams("year", widget.getParams());
        Integer a = null;
        if (!StringUtils.isEmpty(anno))
            a = Integer.parseInt(anno);

        return statisticService.getContatoreIscritti(null,a,province);
    }

    public List<RiepilogoIscrizione> getContatoreIscrittiPerTerritorioAccorpato(){

        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/contatoreIscrittiTerritorioAccorpato/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);


        String anno = findPropertyInParams("year", widget.getParams());
        Integer a = null;
        if (!StringUtils.isEmpty(anno))
            a = Integer.parseInt(anno);


        return statisticService.getContatoreIscrittiPerTerritorioAccorpato(null,a);
    }

    public UIAndamentoIscrittiPerProvincia getAndamentoIscrittiPerProvincia() {



        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/andamentoIscrittiPerProvincia/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);

        String province = findPropertyInParams("province", widget.getParams());



        List<RiepilogoIscrizione> riepilogoIscriziones = statisticService.getAndamentoIscrittiPerProvincia(null,province);

        UIAndamentoIscrittiPerProvincia ui = new UIAndamentoIscrittiPerProvincia();


        List<Integer> anni = getAnniFromRiepilogoIscrizioneAndSort(riepilogoIscriziones);
        ui.setAnni(anni);


        List<String> settori = getSettoriFromRiepilogoIscrizione(riepilogoIscriziones);


        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
        List<RowListInteger> rowListIntegers = new ArrayList<>();


        //per ogni territorio
        for(String t:settori){

            RowListInteger rowInteger = new RowListInteger();
            rowInteger.setName(t);

            List<Integer> integerList = new ArrayList<>();

            for(Integer anno:anni){

                RiepilogoIscrizione r = riepilogoIscriziones
                        .stream()
                        .filter(r2 -> r2.getSettore().equals(t))
                        .filter(r2 -> r2.getAnno() == anno)
                        .findFirst().orElse(null);

                if(r!=null)
                    integerList.add(r.getNumIscritti());
                else
                    integerList.add(0);

            }

            rowInteger.setData(integerList);

            rowListIntegers.add(rowInteger);

        }


//        List<String> territori = getTerritoriFromRiepilogoIscrizione(riepilogoIscriziones);
//
//        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
//        List<RowListInteger> rowListIntegers = new ArrayList<>();
//
//        //per ogni territorio
//        for(String t:territori){
//
//            RowListInteger rowInteger = new RowListInteger();
//            rowInteger.setName(t);
//
//            List<Integer> integerList = new ArrayList<>();
//
//            for(Integer anno:anni){
//
////                for(RiepilogoIscrizione r:)
//
//                RiepilogoIscrizione r = riepilogoIscriziones
//                        .stream()
//                        .filter(r2 -> r2.getTerritorio().equals(t))
//                        .filter(r2 -> r2.getAnno() == anno)
//                        .findFirst().orElse(null);
//
//                if(r!=null)
//                    integerList.add(r.getNumIscritti());
//                else
//                    integerList.add(0);
//
//            }
//
//            rowInteger.setData(integerList);
//
//            rowListIntegers.add(rowInteger);
//
//        }

        ui.setValues(rowListIntegers);

        return ui;

    }



    public UIAndamentoIscrittiPerProvincia getAndamentoIscrittiPerSettoreEdile() {

        List<RiepilogoIscrizione> riepilogoIscriziones = statisticService.getAndamentoIscrittiPerSettoreEdile(null,null);

        UIAndamentoIscrittiPerProvincia ui = new UIAndamentoIscrittiPerProvincia();


        List<Integer> anni = getAnniFromRiepilogoIscrizioneAndSort(riepilogoIscriziones);

        ui.setAnni(anni);


        List<String> enti = getEntiFromRiepilogoIscrizione(riepilogoIscriziones);


        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
        List<RowListInteger> rowListIntegers = new ArrayList<>();


        //per ogni territorio
        for(String t:enti){

            RowListInteger rowInteger = new RowListInteger();
            rowInteger.setName(t);

            List<Integer> integerList = new ArrayList<>();

            for(Integer anno:anni){

                RiepilogoIscrizione r = riepilogoIscriziones
                        .stream()
                        .filter(r2 -> r2.getEnte().equals(t))
                        .filter(r2 -> r2.getAnno() == anno)
                        .findFirst().orElse(null);

                if(r!=null)
                    integerList.add(r.getNumIscritti());
                else
                    integerList.add(0);

            }



            rowInteger.setData(integerList);

            rowListIntegers.add(rowInteger);

        }

        ui.setValues(rowListIntegers);

        return ui;

    }

    private List<String> getSettoriFromRiepilogoIscrizione(List<RiepilogoIscrizione> riepilogoIscriziones) {

        List<String> settori = new ArrayList<>();

        //per tutti i riepiloghi presenti nel db acquisisco tutti i territori
        for(RiepilogoIscrizione r:riepilogoIscriziones) {

            if (settori.isEmpty())
                settori.add(r.getSettore());

            else {

                Boolean toAdd = true;

                for (String t : settori) {

                    if (t.equals(r.getSettore())) {
                        toAdd = false;
                        break;
                    }

                }

                if (toAdd)
                    settori.add(r.getSettore());

            }

        }

        return settori;

    }

    private List<String> getEntiFromRiepilogoIscrizione(List<RiepilogoIscrizione> riepilogoIscriziones) {

        List<String> settori = new ArrayList<>();

        //per tutti i riepiloghi presenti nel db acquisisco tutti i territori
        for(RiepilogoIscrizione r:riepilogoIscriziones) {

            if (settori.isEmpty())
                settori.add(r.getEnte());

            else {

                Boolean toAdd = true;

                for (String t : settori) {

                    if (t.equals(r.getEnte())) {
                        toAdd = false;
                        break;
                    }

                }

                if (toAdd)
                    settori.add(r.getEnte());

            }

        }

        return settori;

    }

    private List<Integer> getAnniFromRiepilogoIscrizioneAndSort(List<RiepilogoIscrizione> riepilogoIscriziones){

        List<Integer> anni = new ArrayList<>();

        for(RiepilogoIscrizione r:riepilogoIscriziones){

            if(anni.isEmpty())
                anni.add(r.getAnno());
            else{

                Boolean toAdd = true;

                for(Integer anno:anni){
                    if(anno==r.getAnno()){
                        toAdd = false;
                        break;
                    }
                }

                if(toAdd)
                    anni.add(r.getAnno());

            }

        }

        Collections.sort(anni);

        return anni;

    }

    private List<String> getTerritoriFromRiepilogoIscrizione(List<RiepilogoIscrizione> riepilogoIscriziones){

        List<String> territori = new ArrayList<>();

        //per tutti i riepiloghi presenti nel db acquisisco tutti i territori
        for(RiepilogoIscrizione r:riepilogoIscriziones) {

            if (territori.isEmpty())
                territori.add(r.getTerritorio());

            else {

                Boolean toAdd = true;

                for (String t : territori) {

                    if (t.equals(r.getTerritorio())) {
                        toAdd = false;
                        break;
                    }

                }

                if (toAdd)
                    territori.add(r.getTerritorio());

            }

        }

        return territori;

    }

    public UIAndamentoIscrittiPerProvincia getAndamentoIscrittiPerTerritorioAccorpato() {

        List<RiepilogoIscrizione> riepilogoIscriziones = statisticService.getAndamentoIscrittiPerTerritorioAccorpato(null);

        UIAndamentoIscrittiPerProvincia ui = new UIAndamentoIscrittiPerProvincia();


        List<Integer> anni = getAnniFromRiepilogoIscrizioneAndSort(riepilogoIscriziones);

        ui.setAnni(anni);

        List<String> territori = getTerritoriFromRiepilogoIscrizione(riepilogoIscriziones);

        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
        List<RowListInteger> rowListIntegers = new ArrayList<>();

        //per ogni territorio
        for(String t:territori){

            RowListInteger rowInteger = new RowListInteger();
            rowInteger.setName(t);

            List<Integer> integerList = new ArrayList<>();

            for(Integer anno:anni){

//                for(RiepilogoIscrizione r:)

                RiepilogoIscrizione r = riepilogoIscriziones
                        .stream()
                        .filter(r2 -> r2.getTerritorio().equals(t))
                        .filter(r2 -> r2.getAnno() == anno)
                        .findFirst().orElse(null);

                if(r!=null)
                    integerList.add(r.getNumIscritti());
                else
                    integerList.add(0);

            }

            rowInteger.setData(integerList);

            rowListIntegers.add(rowInteger);

        }


//        List<String> settori = getSettoriFromRiepilogoIscrizione(riepilogoIscriziones);


//        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
//        List<RowListInteger> rowListIntegers = new ArrayList<>();
//
//
//        //per ogni territorio
//        for(String t:settori){
//
//            RowListInteger rowInteger = new RowListInteger();
//            rowInteger.setName(t);
//
//            List<Integer> integerList = new ArrayList<>();
//
//            for(Integer anno:anni){
//
//                RiepilogoIscrizione r = riepilogoIscriziones
//                        .stream()
//                        .filter(r2 -> r2.getSettore().equals(t))
//                        .filter(r2 -> r2.getAnno() == anno)
//                        .findFirst().orElse(null);
//
//                if(r!=null)
//                    integerList.add(r.getNumIscritti());
//                else
//                    integerList.add(0);
//
//            }
//
//            rowInteger.setData(integerList);
//
//            rowListIntegers.add(rowInteger);
//
//        }
//
        ui.setValues(rowListIntegers);

        return ui;

    }

    public List<NonIscrittiAzienda> getClassificaNonIscritti() {

        User u = ((User) sec.getLoggedUser());

        //tento di recuperare i paramteri del widget dalle useroptions
        String url = "widget/real/noniscritticlassifica/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);

        String province = findPropertyInParams("province", widget.getParams());
        String ente = findPropertyInParams("ente", widget.getParams());


        return statisticService.getClassificaNonIscritti(province, ente);
    }

    public UIAndamentoIscrittiPerProvincia getAndamentoIscrittiPerSettoreEdile(String province) {
        List<RiepilogoIscrizione> riepilogoIscriziones = statisticService.getAndamentoIscrittiPerSettoreEdile(null,province);

        UIAndamentoIscrittiPerProvincia ui = new UIAndamentoIscrittiPerProvincia();


        List<Integer> anni = getAnniFromRiepilogoIscrizioneAndSort(riepilogoIscriziones);

        ui.setAnni(anni);


        List<String> enti = getEntiFromRiepilogoIscrizione(riepilogoIscriziones);


        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
        List<RowListInteger> rowListIntegers = new ArrayList<>();


        //per ogni territorio
        for(String t:enti){

            RowListInteger rowInteger = new RowListInteger();
            rowInteger.setName(t);

            List<Integer> integerList = new ArrayList<>();

            for(Integer anno:anni){

                RiepilogoIscrizione r = riepilogoIscriziones
                        .stream()
                        .filter(r2 -> r2.getEnte().equals(t))
                        .filter(r2 -> r2.getAnno() == anno)
                        .findFirst().orElse(null);

                if(r!=null)
                    integerList.add(r.getNumIscritti());
                else
                    integerList.add(0);

            }



            rowInteger.setData(integerList);

            rowListIntegers.add(rowInteger);

        }

        ui.setValues(rowListIntegers);


        if (riepilogoIscriziones.size() > 0)
            ui.setProvincia(riepilogoIscriziones.get(0).getTerritorio());


        return ui;
    }

    public UIAndamentoIscrittiPerProvincia getAndamentoIscrittiPerProvincia(String province) {
        User u = ((User) sec.getLoggedUser());


        return getUiAndamentoIscrittiPerProvincia(province);

    }

    private UIAndamentoIscrittiPerProvincia getUiAndamentoIscrittiPerProvincia(String province) {
        List<RiepilogoIscrizione> riepilogoIscriziones = statisticService.getAndamentoIscrittiPerProvincia(null,province);

        UIAndamentoIscrittiPerProvincia ui = new UIAndamentoIscrittiPerProvincia();


        List<Integer> anni = getAnniFromRiepilogoIscrizioneAndSort(riepilogoIscriziones);
        ui.setAnni(anni);


        List<String> settori = getSettoriFromRiepilogoIscrizione(riepilogoIscriziones);


        //qui dichiaro la lista che contiente il territorio con i rispettivi iscritti es: "territorio", valori: [200,500,0,600]
        List<RowListInteger> rowListIntegers = new ArrayList<>();


        //per ogni territorio
        for(String t:settori){

            RowListInteger rowInteger = new RowListInteger();
            rowInteger.setName(t);

            List<Integer> integerList = new ArrayList<>();

            for(Integer anno:anni){

                RiepilogoIscrizione r = riepilogoIscriziones
                        .stream()
                        .filter(r2 -> r2.getSettore().equals(t))
                        .filter(r2 -> r2.getAnno() == anno)
                        .findFirst().orElse(null);

                if(r!=null)
                    integerList.add(r.getNumIscritti());
                else
                    integerList.add(0);

            }

            rowInteger.setData(integerList);

            rowListIntegers.add(rowInteger);

        }


        ui.setValues(rowListIntegers);

        if (riepilogoIscriziones.size()>0)
            ui.setProvincia(riepilogoIscriziones.get(0).getTerritorio());

        return ui;
    }

    public Object getContatoreIscrittiPerTerritorioAccorpato(Integer anno) {

        if (anno == null)
            anno = Calendar.getInstance().get(Calendar.YEAR);


        return statisticService.getContatoreIscrittiPerTerritorioAccorpato(null,anno);
    }
}
