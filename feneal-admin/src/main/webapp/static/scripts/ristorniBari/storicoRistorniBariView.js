/**
 * Created by angelo on 28/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils, resultsConfigurer) {

    var exports = {};


    var StoricoRistorniBariRemoteView = fviews.RemoteContentView.extend({
        ctor: function(gridService){
            StoricoRistorniBariRemoteView.super.ctor.call(this, gridService);

            var self = this;

            self.listaRistorni = null;
            self.gridData = null;
            self.grid = null;

            self.on("load", function() {

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();


                $.loader.hide({parent:'body'});

                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.listaRistorni);
                //una volta ottenuti i risultati la griglia devexpress mostra una loader
                //di attesa per la renderizzazione degli stessi! in quel momento rendo
                //visibile l'intera area
                //scrollando fino a rendere visibile la griglia
                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "storicoRistorniBari", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});


            });


        },

        //inizializzazione della griglia dei risultati
        initGrid : function(responseData){
            var self = this;

            this.gridData = responseData;

            self.grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"id", visible : false},
                    { dataField:"dataRistorno", visible : true, dataType:'date',caption:"Data registrazione"

                        },
                    { dataField:"anno", visible : true, caption:"Anno"},
                    { dataField:"attachmentName", visible : true, caption:"Nome file",
                    cellTemplate: function (container, options) {
                        container.addClass("img-container");
                        var name = options.data.attachmentName;

                        $("<a />")
                            .text(name)
                            .attr("href", "javascript:;")
                            .on('click', function(){
                                ui.Navigation.instance().navigate("dettaglioristornobari", "index", {
                                    fs: this.fullScreenForm,
                                    id: options.data.id
                                });
                            })
                            .appendTo(container);
                    }
                    },
                    { dataField:"ente", visible : true, caption:"Ente"}
                    ],
                "export": {
                    enabled: false,
                    fileName: "storico_ristorni",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "storicoristornibari"
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
                summary: {
                    totalItems: [{
                        column: "provincia",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Elementi trovati: " + data.value;
                        }
                    }]
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

            return self.grid;

        },

        onServiceLoad: function(ristorniViewResponse) {
            var self = this;
            self.listaRistorni = ristorniViewResponse.listaRistorni;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(ristorniViewResponse.content);
            this.container.empty().append(this.content);
            this.invoke("load");
        },

        edit: function(id) {
            return false;
        },

        getToolbarButtons: function() {
            return [];
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

        getBreadcrumbItems: function() {
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Storico ristorni"
                }
            ];
        }
    });


    var RistornoDettaglioRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, quotaId){
            RistornoDettaglioRemoteView.super.ctor.call(this, service);

            var self = this;
            self.quotaId = quotaId;
            self.quoteDetails = null;
            self.ristornoDetails = null;
            self.gridData = null;
            self.grid = null;

            self.on("load", function(){

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();


                $.loader.hide({parent:'body'});



                //inizializzo la griglia devexpress
                self.initTabsPanel();
                var grid = self.initGridReferenti(self.ristornoDetails);
                var grid2 = self.initGridQuote(self.quoteDetails);



                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                if ($(".print-tessera").length == 0) {
                    var delGeneration = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Stampa Ristorno">' +
                        '<button type="button" class="btn btn-primary full-width print-tessera">' +
                        '<span class="glyphicon glyphicon-print" aria-hidden="true"></span>' +
                        '</button></div>';

                    $(".toolbox-buttons-cnt").append($(delGeneration));
                    $(".print-tessera").parent().tooltip();
                    $(".print-tessera").click(function() {

                        var container2 = $('<div class="save-ristorno-ctn"><span>Stampare il ristorno?</span></div>');

                        var params = {};


                        var dialog = container2.modalDialog({
                            autoOpen: true,
                            title: "Stampa Dettaglio Ristorno",
                            destroyOnClose: true,
                            height: 120,
                            width: 450,
                            buttons:{
                                Salva: {
                                    primary: true,
                                    command: function() {

                                        dialog.modalDialog("close");
                                        var v = $('[aria-selected="true"]').find('span.dx-tab-text').text();

                                        var listQuote = self.quoteDetails;
                                        var listReferenti = self.ristornoDetails;

                                        params = {
                                            listQuote: listQuote,
                                            listRefrenti: listReferenti,
                                            type: v
                                        }


                                        var svc = new  fmodel.AjaxService();
                                        svc.set("url", BASE + "deleghe/stamparistorni");
                                        svc.set("contentType", "application/json");
                                        svc.set("data", JSON.stringify( params));
                                        svc.set("method", "POST");

                                        svc.on("load", function(response){
                                            $.loader.hide({parent:'body'});

                                            location.href = BASE + "deleghe/print?path="+encodeURIComponent(response);
                                            dialog.modalDialog("close");

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
                }


                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "quoteAssociative", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});


            });

        },

        initTabsPanel: function(){
            var self = this;

            self.dxTab =$("#tabpanel-container").dxTabPanel({
                loop: false,
                animationEnabled: true,
                swipeEnabled: true,
                deferRendering: false,

            }).dxTabPanel("instance");
        },
        initGridReferenti : function(responseData){



            var grid = $('#riepilogoReferenti').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"nominativo",  caption: "Nominativo referente",visible : true,visibleIndex: 1},
                    { dataField:"comune", caption: "Comune referente",visible : true,visibleIndex: 2},
                    { dataField:"proRataShare", caption: "Percentuale referente",visible : true,visibleIndex: 3},
                    { dataField:"importoTot",  caption: "Importo Totale",visible : true,visibleIndex: 4}
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
                hoverStateEnabled: true,

                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;

                        container.addClass("internal-grid-container");
                        $("<div>").text("Quote Associative").appendTo(container);
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"lavoratore.surname",caption:"Cognome",  visible : true, visibleIndex: 0},
                                    { dataField:"lavoratore.name", caption:"Nome", visible : true, visibleIndex: 1},
                                    { dataField:"ultimaDelega.protocolDate", dataType:'date',caption:"Data protocollo", visible : true, visibleIndex: 2},
                                    { dataField:"ultimaDelega.protocolNumber", caption:"Numero protocollo", visible : true, visibleIndex: 3},
                                    { dataField:"ultimaDelega.workerCompany.description", caption:"Azienda", visible : true, visibleIndex: 4},
                                    { dataField:"quotaAssoc", caption:"Quota", visible : true, visibleIndex: 9}
                                ],
                                dataSource: currentData.listQuote
                            }).appendTo(container);
                    }
                }

            }).dxDataGrid("instance");

            return grid;

        },
        initGridQuote : function(responseData){



            var grid = $('#riepilogoQuote').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"lavoratore.surname",  caption: "Cognome",visible : true,visibleIndex: 1},
                    { dataField:"lavoratore.name", caption: "Nome",visible : true,visibleIndex: 2},
                    { dataField:"lavoratore.fiscalcode",  caption: "Codice Fiscale",visible : true,visibleIndex: 3},
                    { dataField:"quotaAssoc",  caption: "Importo Quota",visible : true,visibleIndex: 4}

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
                    enabled: true,
                    fileName: "Riepilogo Quote Associative",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "riepilogoQuoteAssociativeBari"
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
                hoverStateEnabled: true,

                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;

                        container.addClass("internal-grid-container");
                        $("<div>").text("Dettagli Quote Associative").appendTo(container);
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"workerCompany.description", visible : true, caption:"Azienda"},
                                    { dataField:"protocolDate", dataType:'date', visible : true, caption:"Data protocollo"},
                                    { dataField:"protocolNumber", visible : true, caption:"Num. Protocollo"},
                                    { dataField:"signup", visible : true, caption:"Iscrizione"},
                                    { dataField:"revocation", visible : true, caption:"Revoca"},
                                    { dataField:"duplicate", visible : true, caption:"Doppione"},
                                    { dataField:"anomaly", visible : false, caption:"Anomalia"},
                                    { dataField:"effectDate", visible : true,dataType:'date', caption:"Decorrenza"},
                                    { dataField:"lastMovement", visible : true, caption:"Ult. mov."},
                                    { dataField:"managementContact.completeName", visible : true, caption:"Referente"}

                                ],
                                onRowPrepared: function (e) {
                                    if (e.rowType == 'data' &&  e.rowIndex == 0) {
                                        e.rowElement[0].style.backgroundColor = '#FFFF00';
                                    }
                                },
                                dataSource: currentData.delegheBari
                            }).appendTo(container);
                    }
                }

            }).dxDataGrid("instance");

            return grid;

        },

        onServiceLoad: function(quoteDetailsViewResponse) {
            var self = this;
            self.ristornoDetails = quoteDetailsViewResponse.ristornoBariObject.listaReferenti;
            self.quoteDetails = quoteDetailsViewResponse.ristornoBariObject.listaQuote;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(quoteDetailsViewResponse.content);
            this.container.empty().append(this.content);
            this.invoke("load");
        },
        duplicateQuotaForm : function() {
            var self = this;
            var formService = new fmodel.FormService();
            formService.set("method", "GET");
            formService.set("data", {});
            formService.set("url", BASE + "quoteassociativeform");

            var container = $('<div class="duplica-container"></div>');

            var formView = new fviews.FormView(formService);
            formView.container = container;

            formView.on("render", function() {
                $(".duplica-container").find(".panel-footer, .panel-heading").hide();
                $(".panel-body").css("overflow", "hidden");
                $(".duplica-container").find("div[data-property=description] .field-label").css("line-height", "20px");
            });



            return formView;
        },
        createAddNewItemForm : function() {
            var self = this;
            var formService = new fmodel.FormService();
            formService.set("method", "GET");
            formService.set("data", {});
            formService.set("url", BASE + "quoteassociativeitem/" + self.quotaId);

            var container = $('<div class="quote-item-container"></div>');

            var formView = new fviews.FormView(formService);
            formView.container = container;

            formView.on("render", function() {
                $(".quote-item-container").find(".panel-footer, .panel-heading").hide();
                $(".panel-body").css("overflow", "hidden");
                $(".quote-item-container").find("div[data-property=description] .field-label").css("line-height", "20px");
            });

            return formView;
        },
        modifyCompetenzaQuotaForm : function() {
            var self = this;
            var formService = new fmodel.FormService();
            formService.set("method", "GET");
            formService.set("data", {});
            formService.set("url", BASE + "quoteassociativeform");

            var container = $('<div class="modify-container"></div>');

            var formView = new fviews.FormView(formService);
            formView.container = container;

            formView.on("render", function() {
                $(".modify-container").find(".panel-footer, .panel-heading").hide();
                $(".panel-body").css("overflow", "hidden");
                $(".modify-container").find("div[data-property=description] .field-label").css("line-height", "20px");
            });



            return formView;
        },
        validateDateForm: function(data){
            var result = {};
            result.errors = [];


            var dateRegExp = /^\d{2}[/]\d{2}[/]\d{4}$/
            if (!data.dataInizio.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataInizio",
                        message: "Data inizio mancante"
                    }
                );
            }




            if (!data.dataFine.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataFine",
                        message: "Data fine mancante"
                    }
                );
            }

            return result;
        },
        validateForm: function(data){
            var result = {};
            result.errors = [];



            if (data.worker == ""){
                result.errors.push(
                    {
                        property: "worker",
                        message: "Lavoratore mancante"
                    }
                );
            }

            if (data.settore != "INPS"){


                if (data.firm == ""){
                    result.errors.push(
                        {
                            property: "firm",
                            message: "Azienda mancante"
                        }
                    );
                }

            }



            return result;
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
                    propertyBuffer[propName] = new Array();
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
        createToolbar: function() {

            var self = this;
            var button = {
                text: "Elimina ristorno",
                command: function() {

                    var dialog = $("<p>Sicuro di voler il ristorno la quota?</p>").modalDialog({
                        autoOpen: true,
                        title: "Elimina",
                        destroyOnClose: true,
                        height: 100,
                        width:  400,
                        buttons: {
                            send: {
                                label: "OK",
                                primary: true,
                                command: function () {

                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "deleghe/deleteristorno/" + self.quotaId;
                                    svc.set("method", "DELETE");
                                    svc.on({
                                        load: function(response){

                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("storicoristorni", "index", {
                                                fs: this.fullScreenForm
                                            });
                                        },
                                        error: function (error){
                                            $.notify.error(error);
                                        }
                                    });
                                    svc.load();

                                }

                            }
                        }
                    });
                },
                icon: "a glyphicons glyphicons-delete"
            };

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            $t.toolbar("add", button);
        },

        getToolbarButtons: function() {
            return [
            ];

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
                    label: "Elenco ristorni",

                    href: ui.Navigation.instance().navigateUrl("storicoristorni", "index", {})
                },
                {
                    label: "Dettaglio ristorno"

                }
            ];
        }
    });





    exports.StoricoRistorniBariRemoteView = StoricoRistorniBariRemoteView;
    exports.RistornoDettaglioRemoteView = RistornoDettaglioRemoteView;

    return exports;

});
