define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils",
    "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets,
                                                 plugins, webparts, geoUtils,
                                                 resultsConfigurer) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        searchDeleghe: function(searchParams){
            var route = BASE + "deleghe/reportbari" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        searchRistorniDelegheCassaEdile: function(searchParams){
            var route = BASE + "deleghe/ristornibaricassaedile" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        proiettaDeleghe: function(searchParams){

            var prevDateStartParam = 'prevDateStart=' + encodeURIComponent(searchParams.prevDateStart);
            var prevDateEndParam = 'prevDateEnd=' + encodeURIComponent(searchParams.prevDateEnd);
            var lastDateStartParam = 'lastDateStart=' + encodeURIComponent(searchParams.lastDateStart);
            var lastDateEndParam = 'lastDateEnd=' + encodeURIComponent(searchParams.lastDateEnd);
            var delRevParam = 'delRev=' + encodeURIComponent(searchParams.delRev);
            var delBiaParam = 'delBia=' + encodeURIComponent(searchParams.delBia);
            var iscInParam = 'iscIn=' + encodeURIComponent(searchParams.iscIn);
            var iscConParam = 'iscCon=' + encodeURIComponent(searchParams.iscCon);
            var iscRicParam = 'iscRic=' + encodeURIComponent(searchParams.iscRic);


            var query = prevDateStartParam + '&' + prevDateEndParam +
                '&' + lastDateStartParam + '&' + lastDateEndParam +
                '&' + delRevParam + '&' + delBiaParam +
                '&' + iscInParam + '&' + iscConParam +
                '&' + iscRicParam;

            var route = BASE + "deleghe/proiezioni?" + query;

            var svc =  this.__createService(false, route, null, "GET");
            return svc;
        },
        __createService: function (isJsonContentType, route, data, method){

            //definisco il servizio
            var service = new  fmodel.AjaxService();

            //se sono dati json ne imposto il content type
            if (isJsonContentType)
                service.set("contentType", "application/json");
            else
                service.set("contentType", "application/x-www-form-urlencoded; charset=UTF-8");

            //se ci sono dati li trasformoi in stringa json
            //e li accodo al servizio
            if (data){
                if(typeof(data) == 'string'){

                    service.set("data", data);
                }
                else{
                    var stringified1 = JSON.stringify(data);
                    service.set("data", stringified1);
                }
            }
            service.set("url", route);
            if (!method)
                service.set("method", "POST");
            else
                service.set("method", method);
            return service;
        }



    });


    var ReportDelegheAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportDelegheAppView.super.ctor.call(this, formService);

            var self = this;


            self.formView.on("load", function(){
                self.createCharts();
                self.createToolbar();
                self.createBreadcrumbs();
            });

            self.formView.on("submit", function(){
                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchDeleghe(data);


                svc.on("load", function(response){
                    console.log(response);

                    $.loader.hide({parent:'body'});

                    if (response.deleghe.length == 0){
                        $('.top-graphs').hide();

                    }else{
                        $('.top-graphs').show();
                    }

                    self.initGauge(response);
                    self.initContatoreStato(response.contatoreStato);
                    self.initContatoreOperazione(response.contatore);

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response.deleghe);



                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");


                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "deleghe", true);
                    reportResultsConfigurer.init();


                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
                });

                svc.load();
                $.loader.show({parent:'body'});



            });

            self.formView.form.on("cancel", function() {
                self.close();
            });



        },
        initGrid : function(responseData){

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"worker.surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {

                            var name = options.data.worker.surnamename;



                            var fiscalCode = options.data.worker.fiscalcode;
                            var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode );
                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                .appendTo(container);
                        }
                    },
                    { dataField:"lastAzienda.description", visible : true, caption:"Azienda",

                        cellTemplate: function (container, options) {

                            if (options.data.lastAzienda){
                                //container.addClass("img-container");
                                var name = options.data.lastAzienda.description;


                                var uri = encodeURI(BASE + "#/summaryfirm/index?id=" + options.data.lastAzienda.id );

                                $("<a />")
                                    .text(name)
                                    .attr("href", uri)
                                    .attr("target", "_blank")
                                    .appendTo(container);

                            }

                        }

                    },
                    { dataField:"lastProtocolDate", dataType:'date', visible : true, caption:"Data protocollo"},
                    { dataField:"lastAction", visible : true, caption:"Operazione"},
                    { dataField:"state", visible : true, caption:"Stato"},
                    { dataField:"numDeleghe", visible : false, caption:"Num. deleghe"},
                    { dataField:"lastEffectDate", visible : true, dataType:'date', caption:"Decorrenza"},


                    { dataField:"lastMovement", visible : true, caption:"Ult. mov."},
                    { dataField:"contributeToBalace", visible : false, caption:"Contributo"},

                    { dataField:"worker.name", visible : false, caption:"Lavoratore nome"},
                    { dataField:"worker.surname", visible : false, caption:"Lavoratore cognome"},
                    { dataField:"worker.birthDate", dataType:'date', visible : false, caption:"Lavoratore data di nascita"},
                    { dataField:"worker.fiscalcode", visible : false, caption:"Lavoratore codice fiscale"},
                    { dataField:"worker.ce", visible : false, caption:"Lavoratore telefono"},
                    { dataField:"worker.cellphone", visible : false, caption:"Lavoratore cellulare"},
                    { dataField:"worker.birthProvince", visible : false, caption:"Lavoratore provincia di nascita"},
                    { dataField:"worker.birthPlace", visible : false, caption:"Lavoratore comune di nascita"},
                    { dataField:"worker.nationality", visible : false, caption:"Lavoratore nazionalità"},
                    { dataField:"worker.livingProvince", visible : false, caption:"Lavoratore provincia di residenza"},
                    { dataField:"worker.livingCity", visible : false, caption:"Lavoratore comune di residenza"},
                    { dataField:"worker.address", visible : false, caption:"Lavoratore indirizzo"},
                    { dataField:"worker.cap", visible : false, caption:"Lavoratore CAP"},
                    { dataField:"retrieveDelegaBianca", visible : false, caption:"Del. bianca recupero"},

                ],

                summary: {
                    totalItems: [{
                        column: "worker.surnamename",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Deleghe trovate: " + data.value;
                        }
                    }]
                },
                // searchPanel: {
                //     visible: true
                //
                // },
                // columnChooser: {
                //     enabled: true
                // },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: false,
                    fileName: "deleghe",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportdeleghe"
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
                hoverStateEnabled: true,
                onRowPrepared: (function (e) {
                    if (e.rowType == 'data' && e.data.retrieveDelegaBianca === true) {
                        //e.rowElement[0].style.backgroundColor = '#4bdb63';
                        e.rowElement[0].style.color = '#e60c4f';
                        e.rowElement[0].style.fontSize = 'large';
                    }
                }),
                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;
                        container.addClass("internal-grid-container");
                        $("<div>").text(" Dettagli").appendTo(container);
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [

                                    //{ dataField:"worker.surnamename", visible : true, caption:"Lavoratore"},
                                    { dataField:"workerCompany.description", visible : true, caption:"Azienda"},
                                    { dataField:"protocolDate", dataType:'date', visible : true, caption:"Data protocollo"},
                                    { dataField:"protocolNumber", visible : true, caption:"Num. Protocollo"},
                                    { dataField:"signup", visible : true, caption:"Iscrizione"},
                                    { dataField:"revocation", visible : true, caption:"Revoca"},
                                    { dataField:"duplicate", visible : true, caption:"Doppione"},
                                    { dataField:"anomaly", visible : false, caption:"Anomalia"},
                                    { dataField:"effectDate", visible : true,dataType:'date', caption:"Decorrenza"},
                                    { dataField:"lastMovement", visible : true, caption:"Ult. mov."},
                                ],
                                dataSource: currentData.deleghe
                            }).appendTo(container);
                    }
                }

            }).dxDataGrid("instance");

            return grid;

        },
        initGauge: function(responseData) {
            $("#contatore").dxCircularGauge({
                scale: {
                    startValue: -responseData.deleghe.length,
                    endValue: responseData.deleghe.length,
                    tickInterval: 1,
                    label: {
                        useRangeColors: true
                    }
                },
                rangeContainer: {
                    ranges: [
                        { color: '#b37070', startValue: -responseData.deleghe.length, endValue: 0 },
                        { color: '#70b3a1', startValue: 0, endValue: responseData.deleghe.length }
                    ]
                },
                title: {
                    text: "Bilancio entrate (" + responseData.bilancioEntrate + ")" ,
                    font: { size: 28 }
                },
                value: responseData.bilancioEntrate
            });
        },
        initContatoreStato: function(data) {
            var pieChartSource = [];

            for (property in data) {
                var dataSourceObj = {
                    property: property,
                    value: data[property]
                };
                pieChartSource.push(dataSourceObj);
            }

            $("#contatore-stato").dxPieChart({
                palette: "bright",
                dataSource: pieChartSource,
                sizeGroup: "sizeGroupName",
                resolveLabelOverlapping: "shift",
                "export": {
                    enabled: true
                },
                legend: {
                    horizontalAlignment: "center",
                    verticalAlignment: "bottom"
                },
                series: [
                    {
                        argumentField: "property",
                        valueField: "value",
                        label: {
                            visible: true,
                            font: {
                                size: 16
                            },
                            connector: {
                                visible: true,
                                width: 0.5
                            },
                            position: "columns",
                            customizeText: function(arg) {
                                return arg.valueText + " ("  + arg.percentText + ")";
                            }
                        }
                    }
                ],
                title: "Contatore Stato",
                onPointClick: function (e) {
                    var point = e.target;
                    toggleVisibility(point);
                },
                onLegendClick: function (e) {
                    var arg = e.target;
                    toggleVisibility(this.getAllSeries()[0].getPointsByArg(arg)[0]);
                }
            });

            function toggleVisibility(item) {
                if(item.isVisible()) {
                    item.hide();
                } else {
                    item.show();
                }
            }
        },
        initContatoreOperazione: function(data) {
            var pieChartSource = [];

            for (property in data) {
                var dataSourceObj = {
                    property: property,
                    value: data[property]
                };
                pieChartSource.push(dataSourceObj);
            }

            $("#contatore-operazione").dxPieChart({
                palette: "bright",
                dataSource: pieChartSource,
                sizeGroup: "sizeGroupName",
                resolveLabelOverlapping: "shift",
                "export": {
                    enabled: true
                },
                legend: {
                    horizontalAlignment: "center",
                    verticalAlignment: "bottom",
                    columnCount: 5,
                    columnItemSpacing: 10,
                    rowCount: 2
                },
                series: [
                    {
                        argumentField: "property",
                        valueField: "value",
                        label: {
                            visible: true,
                            font: {
                                size: 16
                            },
                            connector: {
                                visible: true,
                                width: 0.5
                            },
                            position: "columns",
                            customizeText: function(arg) {
                                return arg.valueText + " ("  + arg.percentText + ")";
                            }
                        }
                    }
                ],
                title: "Contatore Operazione",
                onPointClick: function (e) {
                    var point = e.target;
                    toggleVisibility(point);
                },
                onLegendClick: function (e) {
                    var arg = e.target;
                    toggleVisibility(this.getAllSeries()[0].getPointsByArg(arg)[0]);
                }
            });

            function toggleVisibility(item) {
                if(item.isVisible()) {
                    item.hide();
                } else {
                    item.show();
                }
            }
        },
        normalizeSubmitResult: function(form){

            //metto tutto in un data array....
            var dataArray = [];
            var formData = form.serializeArray();

            for(var i=0; i<formData.length; i++) {
                dataArray.push({
                    property: formData[i].name,
                    value: formData[i].value
                });
            }

            //tiro fuori un oggetto javascript correttamente serializzato


            //devo ciclare tra tutti gli oggetti  e verificare se ci sono proprietà con lo stesso nome
            // che provvedero' ad inserire in un array
            //questo buffer conterrà il nome della proprietà e una lista che conterrà tutti gli oggetti con lo stesso nome di proprietà
            var propertyBuffer = {};

            //ciclo adesso sugli oggetti della load request
            for (var prop in dataArray){


                //se la proprietà non cè nel buffer la aggiungo creando una nuova lista a cui aggiungo il valore della proprietà stessa

                //prendo il nome della proprietà che farà da key nel buffer
                var propName =  dataArray[prop].property;
                if (!propertyBuffer[propName]){
                    propertyBuffer[propName] = [];
                    propertyBuffer[propName].push(dataArray[prop]);
                }else{
                    propertyBuffer[propName].push(dataArray[prop]);
                }




            }


            //adesso faccio l'inverso: ricostruisco l'oggetto a partire dal buffer
            var data = {};
            for(var propName in propertyBuffer){

                if (propertyBuffer[propName].length == 1) //se ce n'è solo una ne riprendo la property
                {
                    data[propName] =  propertyBuffer[propName][0].value;
                }else{
                    data[propName] = this.__constructArrayOfValues(propertyBuffer[propName]);

                }
            }
            return data;
        },
        submit: function(e){

        },
        close: function(){
            alert("close");
        },
        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Report deleghe bari"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;

            return [
                // {
                //     text: "Crea anagrafica",
                //     command: function() {
                //
                //         ui.Navigation.instance().navigate("editworker", "index", {
                //             fs: this.fullScreenForm
                //         });
                //     },
                //     icon: "pencil"
                // }

            ];

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
        },
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
        },
        createCharts: function() {
            var graps = $('<div style="display: none" class="panel panel-primary row top-graphs p20 mb20"></div>').insertBefore($('#reportContainer'));
            var thirdDiv = graps.append('<div class="col-xl-4"><div id="contatore-operazione"></div></div>');
            var firstDiv = graps.append('<div class="col-xl-4"><div id="contatore"></div></div>');
            var secondDiv = graps.append('<div class="col-xl-4"><div id="contatore-stato"></div></div>');
        }
    });


    var DelegheBariHomeRemoteView =fviews.RemoteContentView.extend({
        ctor: function(service, workerId){
            DelegheBariHomeRemoteView.super.ctor.call(this, service);

            var self = this;
            this.workerId = workerId;

            this.on("load", function(){

// alert("data loaded");
//qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();



                // Gestione cambio referente
                $("a.gestione-referente").click(function() {


                    var formService = new fmodel.FormService();
                    formService.set("method", "GET");
                    formService.set("data", {});
                    formService.set("url", BASE + "deleghebari/referente");






                    var container = $('<div class="management-contact-cnt"></div>');

                    var formView = new fviews.FormView(formService);
                    formView.container = container;

                    formView.on("render", function() {
                        $(".management-contact-cnt").find(".panel-footer, .panel-heading").hide();
                        $(".panel-body").css("overflow", "hidden");
                    });

                    formView.show();

                    var delegaId = $(this).parents("tr").data("delega-id");
                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Gestione Referente",
                        destroyOnClose: true,
                        height: 120,
                        width: 450,
                        buttons:{
                            Cambia: {
                                primary: true,
                                command: function() {
                                    var referente = $(".management-contact-cnt").find("select[name=referente]").val();

                                    var svc = new  fmodel.AjaxService();
                                    svc.set("url", BASE + "delegabari/" + delegaId +"/managementcontact");
                                    svc.set("data", { newManagement: referente });
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        dialog.modalDialog("close");
                                        $.notify.success("Il referente della delega &#232; stato modificato correttamente");

                                        ui.Navigation.instance()
                                            .navigate("deleghebarihome", "index", {
                                              workerId: workerId
                                              });
                                    });
                                    svc.on("error", function(error){
                                        $.loader.hide({parent:'body'});
                                        $.notify.error(error);
                                    });

                                    svc.load();
                                    $.loader.show({parent:'body'});
                                }
                            }
                        }
                    });
                });


                //elimina singolo referente
                $("a.elimina-referente").click(function() {

                    var container2 = $('<div class="delete-contact-cnt"><span>Eliminare il referente per la delega selezionata?</span></div>');

                    var delegaId = $(this).parents("tr").data("delega-id");
                    var dialog = container2.modalDialog({
                        autoOpen: true,
                        title: "Elimina Referente",
                        destroyOnClose: true,
                        height: 120,
                        width: 450,
                        buttons:{
                            Elimina: {
                                primary: true,
                                command: function() {

                                    var svc = new  fmodel.AjaxService();
                                    svc.set("url", BASE + "delegabari/" + delegaId +"/deletecontact");
                                    svc.set("data", { });
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        dialog.modalDialog("close");
                                        $.notify.success("Il referente della delega &#232; stato eliminato correttamente");

                                        ui.Navigation.instance()
                                            .navigate("deleghebarihome", "index", {
                                                workerId: workerId
                                            });
                                    });
                                    svc.on("error", function(error){
                                        $.loader.hide({parent:'body'});
                                        $.notify.error(error);
                                    });

                                    svc.load();
                                    $.loader.show({parent:'body'});
                                }
                            }
                        }
                    });
                });


            });

        },
        onServiceLoad: function(html) {
            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(html);
            this.container.empty().append(this.content);
            this.invoke("load");

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
        },
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
        },


        getToolbarButtons: function() {


            return [

            ];
        },
        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ricerca lavoratore",
//vado alla ricerca dei lavoratori
                    href: ui.Navigation.instance().navigateUrl("searchworkers", "index", {})
                },
                {
                    label: "Anagrafica " + localStorage.getItem("workerName"),
                    href: ui.Navigation.instance().navigateUrl("summaryworker", "index", {
                        id: self.workerId
                    })
                },
                {
                    label: "Deleghe Cassa Bari"
//href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        }

    });


    var ProiettaDelegheAppView = fviews.RemoteContentView.extend({
        ctor: function(service){
            ProiettaDelegheAppView.super.ctor.call(this, service);

            var self = this;
            // mantengo un riferimento alla griglia (initGrid)
            self.grid = null;

            this.on("load", function(){

                self.initFormEditors();
                self.initSearchDataClick();

                self.initForm();

                self.createToolbar();
                self.createBreadcrumbs();
            });
        },
        initForm: function(){
            var formModel = $("#form").dxForm({
                colCount: 2,
                // formData: employee,
                items: [
                    {
                        label: {
                            text: "Data protocollo da"
                        },
                        dataField: "prevDateStart",
                        editorType: "dxDateBox",
                        editorOptions: {
                            value: null,
                            width: "100%"
                        },
                        validationRules: [{
                            type: "required",
                            message: "Campo obbligatorio."
                        }]
                    },
                    {
                        label: {
                            text: "a"
                        },
                        dataField: "prevDateEnd",
                        editorType: "dxDateBox",
                        editorOptions: {
                            value: null,
                            width: "100%"
                        },
                        validationRules: [{
                            type: "required",
                            message: "Campo obbligatorio."
                        },{
                            type: "custom",
                            "validationCallback":  function () {
                                if ($("#form").dxForm("instance").getEditor("prevDateStart").option("value")
                                    <= $("#form").dxForm("instance").getEditor("prevDateEnd").option("value")) {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            },
                            message: "La data deve essere posteriore o uguale alla data di inizio protocollo."
                        }]
                    },
                    {
                        label: {
                            text: "Proietta da"
                        },
                        dataField: "lastDateStart",
                        editorType: "dxDateBox",
                        editorOptions: {
                            value: null,
                            width: "100%"
                        },
                        validationRules: [{
                            type: "required",
                            message: "Campo obbligatorio."
                        },{
                            type: "custom",
                            "validationCallback":  function () {
                                if ($("#form").dxForm("instance").getEditor("lastDateStart").option("value")
                                    >= $("#form").dxForm("instance").getEditor("prevDateEnd").option("value")) {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            },
                            message: "La data deve essere posteriore o uguale alla data fine protocollo."
                        }]
                    },
                    {
                        label: {
                            text: "a"
                        },
                        dataField: "lastDateEnd",
                        editorType: "dxDateBox",
                        editorOptions: {
                            value: null,
                            width: "100%"
                        },
                        validationRules: [{
                            type: "required",
                            message: "Campo obbligatorio."
                        },{
                            type: "custom",
                            "validationCallback":  function () {
                                if ($("#form").dxForm("instance").getEditor("lastDateStart").option("value") <= $("#form").dxForm("instance").getEditor("lastDateEnd").option("value")) {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            },
                            message: "La data deve essere posteriore o uguale alla data inizio di proiezione."
                        }]
                    }]
            });
        },
        initGrid : function(source){
            self.grid = $("#gridContainer").dxDataGrid({
                dataSource: source,
                columns:[
                    { dataField:"worker.surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {

                            var name = options.data.worker.surnamename;



                            var fiscalCode = options.data.worker.fiscalcode;
                            var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode );
                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                .appendTo(container);
                        }

                    },
                    { dataField:"worker.name", visible : false, caption:"Lavoratore nome"},
                    { dataField:"worker.surname", visible : false, caption:"Lavoratore cognome"},
                    { dataField:"worker.birthDate", dataType:'date', visible : false, caption:"Lavoratore data di nascita"},
                    { dataField:"worker.fiscalcode", visible : false, caption:"Lavoratore codice fiscale"},
                    { dataField:"worker.ce", visible : false, caption:"Lavoratore telefono"},
                    { dataField:"worker.cellphone", visible : false, caption:"Lavoratore cellulare"},
                    { dataField:"worker.birthProvince", visible : false, caption:"Lavoratore provincia di nascita"},
                    { dataField:"worker.birthPlace", visible : false, caption:"Lavoratore comune di nascita"},
                    { dataField:"worker.nationality", visible : false, caption:"Lavoratore nazionalità"},
                    { dataField:"worker.livingProvince", visible : false, caption:"Lavoratore provincia di residenza"},
                    { dataField:"worker.livingCity", visible : false, caption:"Lavoratore comune di residenza"},
                    { dataField:"worker.address", visible : false, caption:"Lavoratore indirizzo"},
                    { dataField:"worker.cap", visible : false, caption:"Lavoratore CAP"},

                    { dataField:"prevCompany.description", visible : true, caption:"Azienda",

                        cellTemplate: function (container, options) {

                            if (options.data.prevCompany){
                                //container.addClass("img-container");
                                var name = options.data.prevCompany.description;


                                var uri = encodeURI(BASE + "#/summaryfirm/index?id=" + options.data.prevCompany.id );

                                $("<a />")
                                    .text(name)
                                    .attr("href", uri)
                                    .attr("target", "_blank")
                                    .appendTo(container);

                            }

                        }



                    },
                    { dataField:"prevDate", dataType:'date', visible : true, caption:"Data protocollo"},
                    { dataField:"prevAction", visible : true, caption:"Operazione"},
                    { dataField:"prevState", visible : true, caption:"Stato"},
                    { dataField:"prevMovement", visible : true, caption:"Ult. mov."},
                    { dataField:"lastCompany.description", visible : true, caption:"Azienda attuale",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");

                            if (options.data.lastCompany){
                                var name = options.data.lastCompany.description;


                                var uri = encodeURI(BASE + "#/summaryfirm/index?id=" + options.data.lastCompany.id );

                                $("<a />")
                                    .text(name)
                                    .attr("href", uri)
                                    .attr("target", "_blank")
                                    .appendTo(container);
                            }


                        }



                    },
                    { dataField:"lastDate", dataType:'date', visible : true, caption:"Data protocollo attuale"},
                    { dataField:"lastAction", visible : true, caption:"Operazione attuale"},
                    { dataField:"lastState", visible : true, caption:"Stato attuale"},
                    { dataField:"lastMovement", visible : true, caption:"Ult. mov. attuale"},
                    { dataField:"effectDate", dataType:'date', visible : true, caption:"Decorrenza"},





                    // { dataField:"state", visible : true, caption:"Stato"},
                    // { dataField:"numDeleghe", visible : false, caption:"Numero deleghe"},
                    // { dataField:"lastEffectDate", visible : true,dataType:'date', caption:"Decorrenza"},
                    // { dataField:"contributeToBalace", visible : false, caption:"Contributo"},
                ],
                onToolbarPreparing: function (e) {
                    var toolbarItems = e.toolbarOptions.items;

// Adds a new item
                    toolbarItems.push({
                        widget: "dxButton",
                        options: { hint: "Crea lista di lavoro", icon: "user", onClick: function() {




                                var container = $('<div class="create-worker-list-cnt"><span class="text-bold">Descrizione</span><input type="text" class="field gui-input mt5" name="descrList"></div>');

                                var dialog = container.modalDialog({
                                    autoOpen: true,
                                    title: "Crea lista di lavoro",
                                    destroyOnClose: true,
                                    height: 120,
                                    width: 300,
                                    buttons:{
                                        Crea: {
                                            primary: true,
                                            command: function() {
                                                var descrListaLavoro = container.find("input[name=descrList]").val();
                                                var selectedrows = self.grid.getSelectedRowsData();

                                                var svc = new  fmodel.AjaxService();
                                                var data = {};
                                                data.selectedrows = selectedrows;
                                                svc.set("url", BASE + "listalavoro/deleghe/"+encodeURIComponent(descrListaLavoro));
                                                svc.set("contentType", "application/json");
                                                svc.set("data", JSON.stringify(selectedrows));
                                                svc.set("method", "POST");

                                                svc.on("load", function(response){
                                                    $.loader.hide({parent:'body'});
                                                    dialog.modalDialog("close");

                                                    // response è l'id della lista lavoro da visualizzare
                                                    ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                                        id: response
                                                    });

                                                });
                                                svc.on("error", function(error){
                                                    $.loader.hide({parent:'body'});
                                                    $.notify.error(error);
                                                });

                                                svc.load();
                                                $.loader.show({parent:'body'});
                                            }
                                        }
                                    }
                                });






                            } },
                        location: "after"
                    });
                },

                summary: {
                    totalItems: [{
                        column: "worker.surnamename",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Deleghe trovate: " + data.value;
                        }
                    }]
                },
                searchPanel: {
                    visible: true

                },
                columnChooser: {
                    enabled: true
                },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: true,
                    fileName: "deleghe",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportdeleghe"
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
                rowAlternationEnabled: false,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"multiple",
                    showCheckBoxesMode: "always"
                },
                headerFilter: {
                    allowSearch: true,
                    height: 325,
                    searchTimeout: 500,
                    texts: {},
                    visible: true,
                    width: 252
                },
                filterRow: {
                    applyFilter: "auto",
                    applyFilterText: "Apply filter",
                    betweenEndText: "End",
                    betweenStartText: "Start",
                    operationDescriptions: {},
                    resetOperationText: "Reset",
                    showAllText: "",
                    showOperationChooser: true,
                    visible: true
                },
                groupPanel: {
                    allowColumnDragging: true,
                    emptyPanelText: 'Trascina una colonna qui',
                    visible: true
                },
                hoverStateEnabled: true,
                onRowPrepared: (function (e) {
                    if (e.rowType == 'data' && e.data.lastDate) {
                        e.rowElement[0].style.backgroundColor = '#4bdb63';
                        e.rowElement[0].style.color = '#333333';
                    }
                })
            }).dxDataGrid("instance");
        },
        initPie:function(data){
            var pieChartSource = [];

            for (property in data) {
                var dataSourceObj = {
                    status: property,
                    number: data[property]
                };
                pieChartSource.push(dataSourceObj);
            }

            $("#pie").dxPieChart({
                palette: "bright",
                dataSource: pieChartSource,
                sizeGroup: "sizeGroupName",
                resolveLabelOverlapping: "shift",
                "export": {
                    enabled: true
                },
                legend: {
                    horizontalAlignment: "center",
                    verticalAlignment: "bottom"
                },
                series: [
                    {
                        argumentField: "status",
                        valueField: "number",
                        label: {
                            visible: true,
                            font: {
                                size: 16
                            },
                            connector: {
                                visible: true,
                                width: 0.5
                            },
                            position: "columns",
                            customizeText: function(arg) {
                                return arg.valueText + " ("  + arg.percentText + ")";
                            }
                        }
                    }
                ],
                title: "Contatore Operazione",
                onPointClick: function (e) {
                    var point = e.target;

                    toggleVisibility(point);
                },
                onLegendClick: function (e) {
                    var arg = e.target;

                    toggleVisibility(this.getAllSeries()[0].getPointsByArg(arg)[0]);
                }
            });
            function toggleVisibility(item) {
                if(item.isVisible()) {
                    item.hide();
                } else {
                    item.show();
                }
            }
        },
        initSearchDataClick: function(){
            var self = this;
            $("#elabora").click(function() {
                var result = $("#form").dxForm("instance").validate();

                if (result.isValid) {
                    var data = {
                        prevDateStart: $("#form").dxForm("instance").getEditor("prevDateStart").option("text"),
                        prevDateEnd: $("#form").dxForm("instance").getEditor("prevDateEnd").option("text"),
                        lastDateStart: $("#form").dxForm("instance").getEditor("lastDateStart").option("text"),
                        lastDateEnd: $("#form").dxForm("instance").getEditor("lastDateEnd").option("text"),
                        delRev: $("#delRev").dxCheckBox("instance").option("value"),
                        delBia: $("#delBia").dxCheckBox("instance").option("value"),
                        iscIn: $("#iscIn").dxCheckBox("instance").option("value"),
                        iscCon: $("#iscCon").dxCheckBox("instance").option("value"),
                        iscRic: $("#iscRic").dxCheckBox("instance").option("value")
                    };

                    var factory = new RepositoryServiceFactory();
                    var svc = factory.proiettaDeleghe(data);


                    svc.on("load", function(response){

                        var dataPie = response.contatoreStato;
                        self.initPie(dataPie);

                        self.initGrid(response.deleghe);


                        $.loader.hide({parent:'body'});
                        $("#tableContainer").removeClass("hidden");
                    });
                    svc.on("error", function(error){
                        $.loader.hide({parent:'body'});
                        alert("Errore: "  + error);
                    });

                    svc.load();
                    $.loader.show({parent:'body'});
                }


            });

        },
        initFormEditors: function(){
            var now = new Date();


            $("#dataProtocolloDa").dxDateBox({
                type: "date",
                value: now
            });
            $("#dataProtocolloA").dxDateBox({
                type: "date",
                value: now
            });
            $("#dataProiettaDa").dxDateBox({
                type: "date",
                value: now
            });
            $("#dataProiettaA").dxDateBox({
                type: "date",
                value: now
            });
            $("#delRev").dxCheckBox({
                value: false,
                text: "Deleghe/Revoche"
            });
            $("#delBia").dxCheckBox({
                value: false,
                text: "Revoche bianche"
            });
            $("#iscIn").dxCheckBox({
                value: false,
                text: "Iscrizioni inefficaci"
            });
            $("#iscCon").dxCheckBox({
                value: false,
                text: "Iscrizioni congelate"
            });
            $("#iscRic").dxCheckBox({
                value: false,
                text: "Iscrizioni I riconferma"
            });
        },
        onServiceLoad: function(html) {
            var self = this;
            $.loader.hide({ parent: this.container });

            this.content = _E("div").html(html);
            this.container.empty().append(this.content);

            this.invoke("load");

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
        },
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
        },


        getToolbarButtons: function() {
            var self = this;

            return [


            ];

        },
        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Proiezione stato deleghe bari",

                }
            ];
        }

    });

    var RistorniDelegheBariCassaEdileAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            RistorniDelegheBariCassaEdileAppView.super.ctor.call(this, formService);

            var self = this;


            self.formView.on("load", function(){
                self.createCharts();
                self.createToolbar();
                self.createBreadcrumbs();
            });

            self.formView.on("submit", function(){
                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchRistorniDelegheCassaEdile(data);


                svc.on("load", function(response){
                    console.log(response);

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");


                    // aggiungo tasto SALVA RISTORNO
                    if ($(".save-ristorno").length == 0) {
                        var delGeneration = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Salva Ristorno">' +
                            '<button type="button" class="btn btn-primary full-width save-ristorno">' +
                            '<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>' +
                            '</button></div>';

                        $(".toolbox-buttons-cnt").append($(delGeneration));
                        $(".save-ristorno").parent().tooltip();
                        $(".save-ristorno").click(function() {

                            var container2 = $('<div class="save-ristorno-ctn"><span>Salvare il ristorno?</span></div>');


                            var dialog = container2.modalDialog({
                                autoOpen: true,
                                title: "Salva Ristorno",
                                destroyOnClose: true,
                                height: 120,
                                width: 450,
                                buttons:{
                                    Salva: {
                                        primary: true,
                                        command: function() {

                                            dialog.modalDialog("close");
                                            $.notify.success("SIMULAZIONE SALVATAGGIO REFERENTE");

                                            // var svc = new  fmodel.AjaxService();
                                            // svc.set("url", BASE + "delegabari/" + delegaId +"/deletecontact");
                                            // svc.set("data", { });
                                            // svc.set("method", "POST");
                                            //
                                            // svc.on("load", function(response){
                                            //     $.loader.hide({parent:'body'});
                                            //     dialog.modalDialog("close");
                                            //     $.notify.success("Il referente della delega &#232; stato eliminato correttamente");
                                            //
                                            //     ui.Navigation.instance()
                                            //         .navigate("deleghebarihome", "index", {
                                            //             workerId: workerId
                                            //         });
                                            // });
                                            // svc.on("error", function(error){
                                            //     $.loader.hide({parent:'body'});
                                            //     $.notify.error(error);
                                            // });
                                            //
                                            // svc.load();
                                            // $.loader.show({parent:'body'});
                                        }
                                    }
                                }
                            });

                        });
                    }

                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "magazzino deleghe", false);
                    reportResultsConfigurer.init();



                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
                });

                svc.load();
                $.loader.show({parent:'body'});



            });

            self.formView.form.on("cancel", function() {
                self.close();
            });



        },
        initGrid : function(responseData){



            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"nominativo",  caption: "Nominativo referente",visible : true,visibleIndex: 1},
                    { dataField:"comune", caption: "Comune referente",visible : true,visibleIndex: 2},
                    { dataField:"importoTot",  caption: "Importo Totale",visible : true,visibleIndex: 3}
                ],

                summary: {
                    totalItems: [{
                        column: "nominativo",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Referenti totali: " + data.value;
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
                    enabled: false,
                    fileName: "Referenti",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "referentiDelegheCassaEdile"
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
                onRowPrepared: function (e) {
                    if (e.rowType == 'data' && e.data.giorni >= 40) {
                        e.rowElement[0].style.backgroundColor = '#70ca63';
                    }
                },
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"multiple",
                    showCheckBoxesMode: "always"
                },
                hoverStateEnabled: true

                // masterDetail: {
                //     enabled: true,
                //     template: function(container, options) {
                //         var currentData = options.data;
                //         container.addClass("internal-grid-container");
                //         $("<div>").text(currentData.delegaSettore  + " Dettagli:").appendTo(container);
                //         $("<div>")
                //             .addClass("internal-grid")
                //             .dxDataGrid({
                //                 columnAutoWidth: true,
                //                 columns: [{
                //                     dataField: "id"
                //                 }, {
                //                     dataField: "description",
                //                     caption: "Description",
                //                     calculateCellValue: function(rowData) {
                //                         return rowData.description + "ciao ciao";
                //                     }
                //                 }],
                //                 dataSource: currentData.details
                //             }).appendTo(container);
                //     }
                // }

            }).dxDataGrid("instance");

            return grid;

        },

        normalizeSubmitResult: function(form){

            //metto tutto in un data array....
            var dataArray = [];
            var formData = form.serializeArray();

            for(var i=0; i<formData.length; i++) {
                dataArray.push({
                    property: formData[i].name,
                    value: formData[i].value
                });
            }

            //tiro fuori un oggetto javascript correttamente serializzato


            //devo ciclare tra tutti gli oggetti  e verificare se ci sono proprietà con lo stesso nome
            // che provvedero' ad inserire in un array
            //questo buffer conterrà il nome della proprietà e una lista che conterrà tutti gli oggetti con lo stesso nome di proprietà
            var propertyBuffer = {};

            //ciclo adesso sugli oggetti della load request
            for (var prop in dataArray){


                //se la proprietà non cè nel buffer la aggiungo creando una nuova lista a cui aggiungo il valore della proprietà stessa

                //prendo il nome della proprietà che farà da key nel buffer
                var propName =  dataArray[prop].property;
                if (!propertyBuffer[propName]){
                    propertyBuffer[propName] = [];
                    propertyBuffer[propName].push(dataArray[prop]);
                }else{
                    propertyBuffer[propName].push(dataArray[prop]);
                }




            }


            //adesso faccio l'inverso: ricostruisco l'oggetto a partire dal buffer
            var data = {};
            for(var propName in propertyBuffer){

                if (propertyBuffer[propName].length == 1) //se ce n'è solo una ne riprendo la property
                {
                    data[propName] =  propertyBuffer[propName][0].value;
                }else{
                    data[propName] = this.__constructArrayOfValues(propertyBuffer[propName]);

                }
            }
            return data;
        },
        submit: function(e){

        },
        close: function(){
            alert("close");
        },
        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ristroni quote CASSA EDILE"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        },
        getToolbarButtons: function() {
            return [
            ];

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
        },
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
        },
        createCharts: function() {
            var graps = $('<div style="display: none" class="panel panel-primary row top-graphs p20 mb20"></div>').insertBefore($('#reportContainer'));
            var thirdDiv = graps.append('<div class="col-xl-4"><div id="contatore-operazione"></div></div>');
            var firstDiv = graps.append('<div class="col-xl-4"><div id="contatore"></div></div>');
            var secondDiv = graps.append('<div class="col-xl-4"><div id="contatore-stato"></div></div>');
        }
    });

    exports.RistorniDelegheBariCassaEdileAppView = RistorniDelegheBariCassaEdileAppView;
    exports.DelegheBariHomeRemoteView = DelegheBariHomeRemoteView;
    exports.ReportDelegheAppView = ReportDelegheAppView;
    exports.ProiettaDelegheAppView = ProiettaDelegheAppView;

    return exports;

});
