package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.viewmodel.options.UiEnableWidget;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.admin.viewmodel.widget.*;
import applica.feneal.domain.model.core.Widget;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 02/04/2016.
 */
@Controller
@RequestMapping("/widget")
public class WidgetController {



    @Autowired
    private WidgetFacade widgetFacade;

    @RequestMapping("/header")
    public String header(Model model) {

        //Qui do alla webpart dell'header i valori da inserire nei 4 box
        Header header = new Header();

        header.setValueHeader1(222);
        header.setValueHeader2(421);
        header.setValueHeader3(321);
        header.setValueHeader4(765);

        model.addAttribute("valueHeader1", header.getValueHeader1());
        model.addAttribute("valueHeader2", header.getValueHeader2());
        model.addAttribute("valueHeader3", header.getValueHeader3());
        model.addAttribute("valueHeader4", header.getValueHeader4());

        return "widget/header";

    }

    @RequestMapping("/dataPanel")
    public String dataPanel(Model model) {

        DataPanel dataPanel = new DataPanel();

        dataPanel.setFirstNumberHeader(1200);
        dataPanel.setSecondNumberHeader(3500);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(12);
        integerList.add(15);
        integerList.add(18);
        integerList.add(23);
        dataPanel.setValues(integerList);

        List<RowInteger> rowsData = new ArrayList<>();

        RowInteger rowData = new RowInteger();
        rowData.setName("www.miosito.it");
        rowData.setData(12513);

        rowsData.add(rowData);

        RowInteger rowData2 = new RowInteger();
        rowData2.setName("www.miosito2.it");
        rowData2.setData(32131);

        rowsData.add(rowData2);

        RowInteger rowData3 = new RowInteger();
        rowData3.setName("www.miosito3.it");
        rowData3.setData(45231);

        rowsData.add(rowData3);

        dataPanel.setRowDatas(rowsData);

        model.addAttribute("data",dataPanel);

        return "widget/dataPanel";

    }

    @RequestMapping("/columnGraph")
    public String columnGraph(Model model) {

        //Qui do alla webpart dell'header i valori da inserire nei 4 box
        ColumnGraph columnGraph = new ColumnGraph();

        List<RowInteger> rowDatas = new ArrayList<RowInteger>();

        RowInteger rowData = new RowInteger();
        rowData.setName("testo 1");
        rowData.setData(32);
        rowDatas.add(rowData);

        RowInteger rowData2 = new RowInteger();
        rowData2.setName("testo 2");
        rowData2.setData(39);
        rowDatas.add(rowData2);

        RowInteger rowData3 = new RowInteger();
        rowData3.setName("testo 3");
        rowData3.setData(42);
        rowDatas.add(rowData3);

        RowInteger rowData4 = new RowInteger();
        rowData4.setName("testo 4");
        rowData4.setData(45);
        rowDatas.add(rowData4);

        columnGraph.setRowDatas(rowDatas);

        ObjectMapper o = new ObjectMapper();

        try {
            String jsonData = o.writeValueAsString(columnGraph.getRowDatas());
            model.addAttribute("dataJSON", jsonData);
            model.addAttribute("data", columnGraph.getRowDatas());
            return "widget/columnGraph";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    };

    @RequestMapping("/areaGraph")
    public String areaGraph(Model model) {

        List<AreaGraph> areaGraphs = new ArrayList<>();

        RowListDouble row = new RowListDouble();
        row.setName("Yahoo");
        List<Double> doubles = new ArrayList<>();
        doubles.add(2.5);
        doubles.add(5.0);
        doubles.add(7.2);
        doubles.add(9.2);
        row.setData(doubles);

        AreaGraph areaGraph = new AreaGraph();
        areaGraph.setKey("Male");
        areaGraph.setRow(row);
        areaGraph.setPercentage(25.6);



        RowListDouble rows2 = new RowListDouble();
        rows2.setName("CNN");
        List<Double> doubles2 = new ArrayList<>();
        doubles2.add(2.5);
        doubles2.add(5.9);
        doubles2.add(7.2);
        doubles2.add(12.0);
        rows2.setData(doubles2);

        AreaGraph areaGraph2 = new AreaGraph();
        areaGraph2.setKey("Female");
        areaGraph2.setRow(rows2);
        areaGraph2.setPercentage(62.1);

        areaGraphs.add(areaGraph);
        areaGraphs.add(areaGraph2);

        ObjectMapper o = new ObjectMapper();

        try {

            String jsonData = o.writeValueAsString(areaGraphs);
            model.addAttribute("dataJSON",jsonData);
            model.addAttribute("data",areaGraphs);

            return "widget/areaGraph";

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }


    @RequestMapping("/pieCharts")
    public String pieCharts(Model model) {

        PieCharts pieCharts = new PieCharts();

        pieCharts.setName("Test");

        List<RowDouble> rowDoubles = new ArrayList<>();
        RowDouble rowDouble = new RowDouble();
        rowDouble.setName("prova 1");
        rowDouble.setData(12.5);
        rowDoubles.add(rowDouble);
        RowDouble rowDouble2 = new RowDouble();
        rowDouble2.setName("prova 2");
        rowDouble2.setData(17.1);
        rowDoubles.add(rowDouble2);
        RowDouble rowDouble3 = new RowDouble();
        rowDouble3.setName("prova 3");
        rowDouble3.setData(25.0);
        rowDoubles.add(rowDouble3);

        pieCharts.setRows(rowDoubles);

        try {
            ObjectMapper o = new ObjectMapper();
            String jsonData = o.writeValueAsString(pieCharts);
            model.addAttribute("dataJSON",jsonData);
            return "widget/pieCharts";
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }


    @RequestMapping("/responseTime")
    public String responseTime(Model model) {

        ResponseTime responseTime = new ResponseTime();

        List<RowListDouble> rowListDoubles = new ArrayList<>();

        RowListDouble row = new RowListDouble();
        row.setName("Yahoo");
        List<Double> doubles = new ArrayList<>();
        doubles.add(2.5);
        doubles.add(5.0);
        doubles.add(7.2);
        doubles.add(9.2);
        row.setData(doubles);

        rowListDoubles.add(row);

        RowListDouble row2 = new RowListDouble();
        row2.setName("Google");
        List<Double> doubles2 = new ArrayList<>();
        doubles2.add(4.5);
        doubles2.add(9.0);
        doubles2.add(12.2);
        doubles2.add(30.2);
        row2.setData(doubles2);

        rowListDoubles.add(row2);

        responseTime.setRows(rowListDoubles);
        responseTime.setAverageValue(32.1);
        responseTime.setDescription("Testo di prova");

        try {
            ObjectMapper o = new ObjectMapper();
            String jsonData = o.writeValueAsString(responseTime);
            model.addAttribute("dataJSON",jsonData);
            return "widget/responseTime";
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }


    @RequestMapping("/barGraph")
    public String barGraph(Model model) {

        //in questo grafico bisogna inserire tante categorie quanti sono il numero di valori presenti nella lista di double in RowBarGraph

        BarGraph barGraph = new BarGraph();
        List<String> categories = new ArrayList<>();
        categories.add("categoria 1");
        categories.add("categoria 2");
        categories.add("categoria 3");
        categories.add("categoria 4");
        barGraph.setCategories(categories);
        barGraph.setSuffix(" suffisso");


        List<RowBarGraph> rowBarGraphs = new ArrayList<>();

        RowBarGraph row = new RowBarGraph();
        row.setName("testo 1");
        List<Double> doubles = new ArrayList<>();
        doubles.add(2.5);
        doubles.add(5.0);
        doubles.add(7.2);
        doubles.add(9.2);
        row.setData(doubles);
        row.setId(0);

        rowBarGraphs.add(row);


        RowBarGraph row2 = new RowBarGraph();
        row2.setName("testo 2");
        List<Double> doubles2 = new ArrayList<>();
        doubles2.add(5.5);
        doubles2.add(6.0);
        doubles2.add(9.2);
        doubles2.add(12.2);
        row2.setData(doubles2);
        row2.setId(1);

        rowBarGraphs.add(row2);

        barGraph.setRows(rowBarGraphs);

        try {
            ObjectMapper o = new ObjectMapper();
            String jsonData = o.writeValueAsString(barGraph);
            model.addAttribute("dataJSON",jsonData);
            model.addAttribute("data",barGraph);
            return "widget/barGraph";
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }

    @RequestMapping("/sparkLines")
    public String sparkLines(Model model) {

        List<SparkLines> sparkLinesArrayList = new ArrayList<>();

        SparkLines sparkLines = new SparkLines();
        sparkLines.setName("testo 1");
        sparkLines.setColor("warning");
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(5);
        integerList.add(10);
        sparkLines.setData(integerList);

        sparkLinesArrayList.add(sparkLines);

        SparkLines sparkLines2 = new SparkLines();
        sparkLines2.setName("testo 2");
        sparkLines2.setColor("info");
        List<Integer> integerList2 = new ArrayList<>();
        integerList2.add(4);
        integerList2.add(1);
        integerList2.add(20);
        sparkLines2.setData(integerList2);

        sparkLinesArrayList.add(sparkLines2);

        try {
            ObjectMapper o = new ObjectMapper();
            String jsonData = o.writeValueAsString(sparkLinesArrayList);
            model.addAttribute("dataJSON",jsonData);
            model.addAttribute("data",sparkLinesArrayList);
            return "widget/sparkLines";
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }


    @RequestMapping("/areaGraphOnlyCurve")
    public String areaGraphOnlyCurve(Model model) {

        AreaGraphOnlyCurve areaGraphOnlyCurves = new AreaGraphOnlyCurve();

        List<RowBarGraph> rowBarGraphs = new ArrayList<>();

        RowBarGraph rowBarGraph = new RowBarGraph();
        rowBarGraph.setName("testo 1");
        List<Double> doubles = new ArrayList<>();
        doubles.add(2.3);
        doubles.add(5.5);
        doubles.add(10.0);
        doubles.add(12.0);
        rowBarGraph.setData(doubles);

        rowBarGraphs.add(rowBarGraph);

        RowBarGraph rowBarGraph2 = new RowBarGraph();
        rowBarGraph2.setName("testo 2");
        List<Double> doubles2 = new ArrayList<>();
        doubles2.add(12.1);
        doubles2.add(2.0);
        doubles2.add(13.0);
        doubles2.add(12.0);
        rowBarGraph2.setData(doubles2);

        rowBarGraphs.add(rowBarGraph2);

        RowBarGraph rowBarGraph3 = new RowBarGraph();
        rowBarGraph3.setName("testo 3");
        List<Double> doubles3 = new ArrayList<>();
        doubles3.add(1.2);
        doubles3.add(2.2);
        doubles3.add(10.0);
        doubles3.add(12.0);
        rowBarGraph3.setData(doubles3);

        rowBarGraphs.add(rowBarGraph3);

        areaGraphOnlyCurves.setRows(rowBarGraphs);

        try {
            ObjectMapper o = new ObjectMapper();
            String jsonData = o.writeValueAsString(areaGraphOnlyCurves.getRows());
            model.addAttribute("dataJSON",jsonData);
            model.addAttribute("data",areaGraphOnlyCurves.getRows());
            return "widget/areaGraphOnlyCurve";
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }



    }


    @RequestMapping("/dotList")
    public String dotList(Model model) {

        DotList dotList = new DotList();

        List<RowDouble> rowDoubles = new ArrayList<>();
        RowDouble rowDouble = new RowDouble();
        rowDouble.setName("prova 1");
        rowDouble.setData(12.5);
        rowDoubles.add(rowDouble);
        RowDouble rowDouble2 = new RowDouble();
        rowDouble2.setName("prova 2");
        rowDouble2.setData(17.1);
        rowDoubles.add(rowDouble2);
        RowDouble rowDouble3 = new RowDouble();
        rowDouble3.setName("prova 3");
        rowDouble3.setData(25.0);
        rowDoubles.add(rowDouble3);

        dotList.setRows(rowDoubles);
        model.addAttribute("data",dotList.getRows());

        return "widget/dotList";

    }

    @RequestMapping("/textList")
    public String textList(Model model) {

        TextList textList = new TextList();

        List<String> strings = new ArrayList<>();

        strings.add("testo prova 1");
        strings.add("testo prova 2");
        strings.add("testo prova 3");
        strings.add("testo prova 4");

        textList.setRows(strings);

        model.addAttribute("data",textList.getRows());

        return "widget/textList";

    }


    @RequestMapping("/circulars")
    public String circulars(Model model) throws Exception {

        Circulars circulars = new Circulars();

        List<RowInteger> rowsData = new ArrayList<>();

        RowInteger rowData = new RowInteger();
        rowData.setName("testo 1");
        rowData.setData(10);

        rowsData.add(rowData);

        RowInteger rowData2 = new RowInteger();
        rowData2.setName("testo 2");
        rowData2.setData(20);

        rowsData.add(rowData2);

        RowInteger rowData3 = new RowInteger();
        rowData3.setName("testo 3");
        rowData3.setData(50);

        rowsData.add(rowData3);

        circulars.setRows(rowsData);

        model.addAttribute("data",circulars.getRows());


        //recupero il widget in base al suo url

        String url = "widget/circulars";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
       UiWidgetManager w = widgetFacade.getEnabledWidgets("dashboard");
       UiEnableWidget widget = w.getWidgetByUrl(url);
        if (widget != null){
            model.addAttribute("widgetName", widget.getWidgetName());
            model.addAttribute("widgetParams", widget.getParams());
        }
        else
            throw new Exception("Nessun widjet trovato in Widget controller....");

        return "widget/circulars";

    }



}
