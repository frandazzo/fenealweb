/**
 * Created by david on 14/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var Matrice = core.AObject.extend({

        ctor: function (workerId) {
            Matrice.super.ctor.call(this);
            this.workerId = workerId;
        },
        initGridIscrizioni : function(responseData){



            var grid = $('#containerdettaglio').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"nomeRegione",  visible : true, visibleIndex: 0},
                    { dataField:"nomeProvincia",  visible : true, visibleIndex: 1},
                    { dataField:"settore",  visible : true, visibleIndex: 2},
                    { dataField:"ente",  visible : true, visibleIndex: 3},
                    { dataField:"periodo",  visible : true, visibleIndex: 4},
                    { dataField:"anno",  visible : true, visibleIndex: 5},
                    { dataField:"azienda", visible : true, visibleIndex: 6,
                    cellTemplate: function (container, options) {
                        //container.addClass("img-container");
                        var name = options.data.azienda;

                        if (!name)
                            return;
                        $("<a />")
                            .text(name)
                            .attr("href", "javascript:;")
                            .on('click', function(){
                                ui.Navigation.instance().navigate("summaryfirm", "remoteIndex", {
                                    description:encodeURIComponent(name.replace("&", "*_").replace("'", "~_"))
                                });
                            })
                            .appendTo(container);
                    }},
                    { dataField:"piva",  visible : true, visibleIndex: 7},
                    { dataField:"livello",  visible : true, visibleIndex: 8},
                    { dataField:"quota",  visible : true, visibleIndex: 9},
                    { dataField:"contratto",  visible : true, visibleIndex: 10}


                ],
                searchPanel: {
                    visible: true

                },
                summary: {
                    totalItems: [{
                        column: "nomeRegione",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Iscrizioni trovate: " + data.value;
                        }
                    }]
                },
                columnChooser: {
                    enabled: true
                },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: true,
                    fileName: "iscrizioni",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "iscrizionilavoratore"
                },
                paging:{
                    pageSize: 35
                },
                sorting:{
                    mode:"multiple"
                },
                onContentReady: function (e) {
                    var columnChooserView = e.component.getView("columnChooserView");
                    if (!columnChooserView._popupContainer) {
                        columnChooserView._initializePopupContainer();
                        columnChooserView.render();
                        columnChooserView._popupContainer.option("dragEnabled", false);
                    }
                },
                rowAlternationEnabled: true,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"multiple",
                    showCheckBoxesMode: "always"
                },
                hoverStateEnabled: true



            }).dxDataGrid("instance");

            return grid;

        },
        initGridNonIscrizioni: function(responseData){


            var viewFirm = true;


            var grid = $('#containerdettaglioliberi').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"liberoData", visible : true, dataType:'date', visibleIndex: 0, caption:"Libero al"},
                    { dataField:"liberoProvincia",  visible : true, visibleIndex: 1},

                    { dataField:"liberoEnteBilaterale", visible : true, visibleIndex: 2},
                    { dataField:"liberoIscrittoA", visible : true , visibleIndex: 4},

                    { dataField:"aziendaRagioneSociale", visible : true, visibleIndex: 3,

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;

                            if (!name)
                                return;

                            var uri = encodeURI(BASE + "#/summaryfirm/remoteIndex?description=" + name.replace("&", "*_").replace("'", "~_") );

                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                // .on('click', function(){
                                //     ui.Navigation.instance().navigate("summaryfirm", "remoteIndex", {
                                //         description:name
                                //     });
                                // })
                                .appendTo(container);
                        }
                    }


                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "liberoProvincia",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Segnalazioni come non iscritto: " + data.value;
                        }
                    }]
                },
                // columnChooser: {
                //     enabled: true
                // },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: true,
                    fileName: "segnalazionenoniscritto",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportliberiperlavoratore"
                },
                paging:{
                    pageSize: 35
                },
                sorting:{
                    mode:"multiple"
                },
                onContentReady: function (e) {
                    var columnChooserView = e.component.getView("columnChooserView");
                    if (!columnChooserView._popupContainer) {
                        columnChooserView._initializePopupContainer();
                        columnChooserView.render();
                        columnChooserView._popupContainer.option("dragEnabled", false);
                    }
                },
                rowAlternationEnabled: true,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                hoverStateEnabled: true

            }).dxDataGrid("instance");

            return grid;

        },
        init: function () {

            var self = this;
            self.initGraph();
            self.initGridData();
            self.initNonIscrittiGridData();

        },
        initNonIscrittiGridData: function(){
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "worker/" + self.workerId + "/noniscrizionidetail");
            service.on("load",function(resp){

                if (resp.length == 0){
                    $('#containerdettaglioliberi').closest('.panel').remove();
                }else{
                    self.initGridNonIscrizioni(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
            
        },
        initGridData: function(){


            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "worker/" + self.workerId + "/iscrizionidetail");
            service.on("load",function(resp){

                if (resp.length == 0){
                    //visualizzo un messaggio che non è stata trovata una iscrizione
                    $('#containerdettaglio').append('' +
                        '<p class="color-red" style="text-align: center;padding-top: 12%;padding-bottom: 3%;">' +
                        '<i class="material-icons font-size-100">sentiment_dissatisfied</i>' +
                        '</p>' +
                        '<p class="text-center">Nessuna iscrizione trovata</p>')
                }else{
                    self.initGridIscrizioni(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
            
     
           
        },
        initGraph: function(){
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "worker/" + self.workerId + "/iscrizionichart");
            service.on("load",function(resp){

                if (resp.anni.length == 0){
                    //visualizzo un messaggio che non è stata trovata una iscrizione
                    $('#containerMatrice').append('' +
                        '<p class="color-red" style="text-align: center;padding-top: 12%;padding-bottom: 3%;">' +
                        '<i class="material-icons font-size-100">sentiment_dissatisfied</i>' +
                        '</p>' +
                        '<p class="text-center">Nessuna iscrizione trovata</p>')
                }else{
                    self.initWidget(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
        },

        constructSeriesData : function(chartElements){

            var result = [];

            $.each(chartElements, function(index, element){
                var a = {};
                a.x = element.x;
                a.y = element.y;
                a.value = element.value;


                //adesso devo renderizzare la lista dei settori
                var settori = element.settori;
                if (settori.length == 0)
                    result.push(a);
                else{
                    var dataLabel = {
                        useHTML: true,
                        enabled: true,
                    };

                    //devo calcolare adesso il valore della variabile format...
                    //la lista dei settori puo contenerne al max 3
                    if (settori.length == 1){
                        var labelClass = "edile";
                        if (settori[0] == "Impianti fissi"){
                            labelClass = "if";
                        }
                        else if (settori[0] == "Inps"){
                            labelClass = "inps";
                        }
                        dataLabel.format = '<span class="worker-chart-label-'+ labelClass +'">' + settori[0] + '</span>'
                    }
                    else if (settori.length == 2){

                        var labelClass1 = "edile";
                        var labelClass2 = "edile";

                        if (settori[0] == "Impianti fissi"){
                            labelClass1 = "if";
                        }
                        else if (settori[0] == "Inps"){
                            labelClass1 = "inps";
                        }

                        if (settori[1] == "Impianti fissi"){
                            labelClass2 = "if";
                        }
                        else if (settori[1] == "Inps"){
                            labelClass2 = "inps";
                        }

                        dataLabel.format = '<span class="worker-chart-label-'+ labelClass1 +'">' + settori[0] + '</span> </br>' +
                            '<span class="worker-chart-label-'+ labelClass2 +'">' + settori[1] + '</span>'

                    }else{
                        var labelClass11 = "edile";
                        var labelClass22 = "edile";
                        var labelClass33 = "edile";

                        if (settori[0] == "Impianti fissi"){
                            labelClass11 = "if";
                        }
                        else if (settori[0] == "Inps"){
                            labelClass11 = "inps";
                        }

                        if (settori[1] == "Impianti fissi"){
                            labelClass22 = "if";
                        }
                        else if (settori[1] == "Inps"){
                            labelClass22 = "inps";
                        }

                        if (settori[2] == "Impianti fissi"){
                            labelClass33 = "if";
                        }
                        else if (settori[2] == "Inps"){
                            labelClass33 = "inps";
                        }

                        dataLabel.format = '<span class="worker-chart-label-'+ labelClass11 +'">' + settori[0] + '</span> </br>' +
                            '<span class="worker-chart-label-'+ labelClass22 +'">' + settori[1] + '</span> </br>' +
                            '<span class="worker-chart-label-'+ labelClass33 +'">' + settori[2] + '</span>'

                    }
                    //una volta costruito il data label posso assegnarlo all'elemento
                    a.dataLabels = dataLabel;
                    result.push(a);
                }

            });


            return result;

        },
        arrayContains : function(array, value){
            var found = false;
            $.each(array, function(index, elem){
               if (elem == value){
                   found = true;
                   return false;
               }

            });

            return found;
        },
        constructDataAxes:function(provincesIds, provinces, loggedUserProvinceIds){
            var self = this;

            var result = [];

            var i = 0;
            $.each(provincesIds, function(index, element){
                var a = {};

                a.from = element;
                a.to = element;
                //se la provincia si trova tra quelle dell'utente loggate gli do il colre #EEEEEF
                //akltrimenti il colore white

                if (self.arrayContains(loggedUserProvinceIds, element)){
                    a.color = '#EEEEEF';
                }else{
                    a.color = 'white';
                }
                a.name = provinces[i];
                // {
                //     from:11,
                //         to: 11,
                //     color: '#EEEEEF',
                //     name: 'Matera'
                // }

                result.push(a);
                i++;
            });


            return result;
        },
        initWidget: function(response){
            var self = this;
            var otherData = response.workerName;

            //devo costruire le serie
            var data = self.constructSeriesData(response.chartElements);
            //rimangono da costruire i dataaxes
            var dataAxex = self.constructDataAxes(response.provincesIds, response.provinces, response.loggedUserProvinceIds);


            $('#containerMatrice').highcharts({

                chart: {
                    type: 'heatmap',
                    marginTop: 40,
                    marginBottom: 80,


                    backgroundColor: "#E3EDEE",
                    borderColor: "#B2E3FA",
                    borderRadius: 20,
                    borderWidth: 2,
                    plotBorderWidth: 3,
                    plotBorderColor: "#1A90D0",
                    plotShadow: true
                },


                title: {
                    text: otherData,
                    floating: true,
                    style: { "color": "#333333", "fontSize": "22px" },
                    useHTML:false
                },


                xAxis: {
                    //categories: ['1990', '1995', '2000', '2010'],
                    categories: response.anni,

                },

                yAxis: {
                    categories: response.provinces,
                    // alternateGridColor: '#FDFFD5'
                    title:{
                        text:""
                    },
                    lineColor: "blu",
                    lineWidth: 0

                },

                colorAxis: {
                    dataClassColor:"category",
                    dataClasses : dataAxex,
                    // dataClasses: [{
                    //     from:1,
                    //     to: 1,
                    //     color: 'white',
                    //     name: 'Roma'
                    // }, {
                    //     from:11,
                    //     to: 11,
                    //     color: '#EEEEEF',
                    //     name: 'Matera'
                    // },
                    //     {
                    //         from:21,
                    //         to: 221,
                    //         color: 'white',
                    //         name: 'Potenza'
                    //     }],
                    labels:{

                        enabled:true
                    }
                },



                tooltip: {
                    enabled:false
                },
                //series: data,
                series: [{
                    name: 'Sales per employee',
                    borderWidth: 1,
                    data: data,
                    // data: [
                    //     {x:0, y:0,  value:1,
                    //         dataLabels: {
                    //             //backgroundColor:'white',
                    //             //shape:"circle",
                    //             useHTML: true,
                    //             enabled: true,
                    //             format: '<span class="worker-chart-label-edile">Edile</span>',
                    //
                    //
                    //         }},
                    //     {x:0, y:1, value:11,
                    //         dataLabels: {
                    //             //backgroundColor:'red',
                    //             // shape:"diamond",
                    //             useHTML: true,
                    //             enabled: true,
                    //             format:  '<span class="worker-chart-label-edile">Edile</span>',
                    //
                    //
                    //         }},
                    //     {x:0, y:2, value:21,
                    //         dataLabels: {
                    //             useHTML: true,
                    //             enabled: true,
                    //             format:  '<span class="worker-chart-label-edile">Edile</span>',
                    //         }},
                    //
                    //     {x:1, y:0,  value:1,
                    //         dataLabels: {
                    //             useHTML: true,
                    //             enabled: true,
                    //             format: '<span class="worker-chart-label-if">Impianti fissi</span> </br>' +
                    //             '<span class="worker-chart-label-inps">Inps</span>',
                    //
                    //
                    //         }},
                    //     {x:1, y:1, value:11},
                    //     {x:1, y:2, value:21},
                    //     {x:2, y:0,  value:1},
                    //     {x:2, y:1, value:11},
                    //     {x:2, y:2, value:21,
                    //         dataLabels: {
                    //             useHTML: true,
                    //             enabled: true,
                    //             format: '<span class="worker-chart-label-inps">Inps</span>',
                    //
                    //
                    //         }}
                    // ],
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        formatter: function(){
                            return this.point.name;
                        }
                    }
                }]

            });
        }

    });

    exports.matrice = Matrice;

    return exports;


});