define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var gridOptions = {
        // onToolbarPreparing: function (e) {
        //     var toolbarItems = e.toolbarOptions.items;
        //
        //     // Adds a new item
        //     toolbarItems.push({
        //         widget: "dxButton",
        //         options: {
        //             hint: "Crea lista di lavoro", icon: "user", onClick: function () {
        //
        //                 var container = $('<div class="create-worker-list-cnt"><span class="text-bold">Descrizione</span><input type="text" class="field gui-input mt5" name="descrList"></div>');
        //
        //                 var dialog = container.modalDialog({
        //                     autoOpen: true,
        //                     title: "Crea lista di lavoro",
        //                     destroyOnClose: true,
        //                     height: 120,
        //                     width: 300,
        //                     buttons: {
        //                         Crea: {
        //                             primary: true,
        //                             command: function () {
        //                                 var descrListaLavoro = container.find("input[name=descrList]").val();
        //                                 var selectedrows = self.quotepagate.getSelectedRowsData();
        //
        //                                 var svc = new model.AjaxService();
        //                                 var data = {};
        //                                 data.selectedrows = selectedrows;
        //                                 svc.set("url", BASE + "listalavoro/deleghe/" + encodeURIComponent(descrListaLavoro));
        //                                 svc.set("contentType", "application/json");
        //                                 svc.set("data", JSON.stringify(selectedrows));
        //                                 svc.set("method", "POST");
        //
        //                                 svc.on("load", function (response) {
        //                                     $.loader.hide({parent: 'body'});
        //                                     dialog.modalDialog("close");
        //
        //                                     // response è l'id della lista lavoro da visualizzare
        //                                     ui.Navigation.instance().navigate("summarylistelavoro", "index", {
        //                                         id: response
        //                                     });
        //
        //                                 });
        //                                 svc.on("error", function (error) {
        //                                     $.loader.hide({parent: 'body'});
        //                                     $.notify.error(error);
        //                                 });
        //
        //                                 svc.load();
        //                                 $.loader.show({parent: 'body'});
        //                             }
        //                         }
        //                     }
        //                 });
        //
        //
        //             }
        //         },
        //         location: "after"
        //     });
        // },
        sortByGroupSummaryInfo: [{
            summaryItem: "count"
        }],
        searchPanel: {
            visible: true

        },
        columnChooser: {
            enabled: true
        },
        columnFixing: {
            enabled: true
        },
        "export": {
            enabled: true,
            fileName: "QuoteInps",
            allowExportSelectedData: true
        },
        stateStoring: {
            enabled: false,
            type: "localStorage",
            storageKey: "reportdeleghe"
        },
        paging: {
            pageSize: 20
        },
        sorting: {
            mode: "multiple"
        },
        onContentReady: function (e) {
            var columnChooserView = e.component.getView("columnChooserView");
            if (!columnChooserView._popupContainer) {
                columnChooserView._initializePopupContainer();
                columnChooserView.render();
                columnChooserView._popupContainer.option("dragEnabled", true);
            }
        },
        rowAlternationEnabled: false,
        showBorders: true,
        allowColumnReordering: true,
        allowColumnResizing: true,
        columnAutoWidth: true,
        selection: {
            mode: "multiple",
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
        hoverStateEnabled: true
    };


    var Dashboard = core.AObject.extend({

        ctor: function (id) {
            Dashboard.super.ctor.call(this);
            this.id = id;
        },
        init: function(){
            var self = this;

            var service = new  model.AjaxService();
            service.set("contentType", "application/json");
            var route = BASE + "ristorniquoteinpsfull/" + self.id ;
            service.set("url", route);
            service.set("method", "GET");

            service.on('load', function(response){

                $('.file')
                    .attr("href", response.inpsFiles[0].filepath)
                    .attr("target", "_blank")
                    .appendTo($(this).parent());

                self.initTabsPanel();
                self.initQuotePagateGrid(response);
                self.initQuoteCompleteGrid(response);
                self.initPagamentoReferentiGrid(response);
                self.initPieChart(response);

                $.loader.hide({parent:'body'});
            });
            service.on("error", function(error){
                $.loader.hide({parent:'body'});

                alert("Errore: "  + error);
            });

            service.load();
            $.loader.show({parent:'body'});


        },
        initTabsPanel: function(){
            $("#tabpanel-container").dxTabPanel({
                loop: false,
                animationEnabled: true,
                swipeEnabled: true,
                deferRendering: false
            }).dxTabPanel("instance");
        },
        initQuotePagateGrid: function(response){
            gridOptions.dataSource = response.quotePagate;
            gridOptions.columns = [
                {
                    dataField: "nominativo",
                    caption: "Nominativo",
                    calculateCellValue: function(data) {
                        return [data.lavoratoreCognome, data.lavoratoreNome]
                            .join(" ");
                    },
                    cellTemplate: function(container, options) {
                        var fiscalCode = options.data.lavoratoreCodiceFiscale;
                        var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode );
                        $("<a />")
                            .text(options.text)
                            .attr("href", uri)
                            .attr("target", "_blank")
                            .appendTo(container);
                    }
                },
                {
                    caption: "Num. Domanda",
                    dataField: "numDomanda"
                },
                {
                    caption: "Patronato",
                    dataField: "patronato",
                    visible: false
                },
                {
                    caption: "Importo",
                    dataField: "importo"
                },
                {
                    caption: "Data Valuta",
                    dataField: "dataValuta",
                    dataType: "date"
                },
                {
                    caption: "Referente",
                    dataField: "referente"
                },
                {
                    caption: "Data Domanda",
                    dataField: "dataDomanda",
                    dataType: "date"
                },
                {
                    caption: "Lavoratore edile",
                    dataField: "lavoratoreEdile"

                },
                {
                    caption: "Importo ristornato",
                    dataField: "importRistornato",

                    calculateCellValue: function(data) {
                        if (data.importRistornato !== 0) {
                            return data.importRistornato;
                        }
                    }
                },
                {
                    caption: "Nome File",
                    dataField: "cscFilename",
                    cellTemplate: function (container, options) {
                        $("<a />")
                            .text(options.data.cscFilename)
                            .attr("href", options.data.cscFilepath)
                            .attr("target", "_blank")
                            .appendTo(container);
                    }
                },
                {
                    caption: "Percorso File",
                    dataField: "cscFilepath",
                    visible: false
                },
                {
                    caption: "Id Ristorno",
                    dataField: "idRistorno",
                    visible: false
                },
                {
                    caption: "Ristorno",
                    dataField: "titoloRistorno",
                    cellTemplate: function (container, options) {
                        $("<a />")
                            .click(function() {

                                ui.Navigation.instance().navigate("ristornoinps", "index", {
                                    id:options.data.idRistorno
                                });


                            })
                            .text(options.data.titoloRistorno)
                            .attr("target", "_blank")
                            .appendTo(container);
                    }
                },

                {
                    caption: "Lavoratore Id",
                    dataField: "lavoratoreId",
                    visible: false
                },
                {
                    caption: "Lavoratore Nome",
                    dataField: "lavoratoreNome",
                    visible: false
                },
                {
                    caption: "Lavoratore Cognome",
                    dataField: "lavoratoreCognome",
                    visible: false

                },
                {
                    caption: "Lavoratore Codice Fiscale",
                    dataField: "lavoratoreCoodiceFiscale",
                    visible: false

                },
                {
                    caption: "Lavoratore Data Nascita",
                    dataField: "lavoratoredataNascita",
                    visible: false
                },
                {
                    caption: "Lavoratore Nazione",
                    dataField: "lavoratoreNazione",
                    visible: false
                },
                {
                    caption: "Lavoratore Provincia Nascita",
                    dataField: "lavoratoreProvinciaNascita",
                    visible: false
                },
                {
                    caption: "Lavoratore Comune Nascita",
                    dataField: "lavoratoreComuneNascita",
                    visible: false
                },
                {
                    caption: "Lavoratore Provincia Residenza",
                    dataField: "lavoratoreProvinciaResidenza",
                    visible: false
                },
                {
                    caption: "Lavoratore Comune Residenza",
                    dataField: "lavoratoreComuneResidenza",
                    visible: false
                },
                {
                    caption: "Lavoratore Indirizzo",
                    dataField: "lavoratoreIndirizzo",
                    visible: false
                },
                {
                    caption: "Lavoratore Cap",
                    dataField: "lavoratoreCap",
                    visible: false
                },
                {
                    caption: "Lavoratore Telefono",
                    dataField: "lavoratoreTelefono",
                    visible: false
                },
                {
                    caption: "Lavoratore Cellulare",
                    dataField: "lavoratoreCellulare",
                    visible: false
                },
                {
                    caption: "Lavoratore Mail",
                    dataField: "lavoratoreMail",
                    visible: false
                },
                {
                    caption: "Lavoratore Edile",
                    dataField: "lavoratoreEdile",
                    visible: false
                }
            ];
            gridOptions.summary = {
                totalItems: [
                    {
                        column: "importo",
                        summaryType: "sum",
                        customizeText: function (data) {

                            //arrotondo il valore a 2 cifre decimali
                            var value = Math.round(data.value * 100) / 100 ;

                            return "Importo: " + value;
                        }
                    }, {
                        column: "importRistornato",
                        summaryType: "sum",
                        customizeText: function (data) {
                            //arrotondo il valore a 2 cifre decimali
                            var value = Math.round(data.value * 100) / 100 ;
                            return "Importo Ristornato: " + value;
                        }
                    },
                    {
                        column: "numDomanda",
                        summaryType: "count"
                    }],
                groupItems: [
                    {
                        summaryType: "count",
                        displayFormat: "Numero quote INPS: {0}"
                    },
                    {
                        column: "importo",
                        summaryType: "sum",
                        displayFormat: "Totale: {0}",
                        showInGroupFooter: true,
                        customizeText: function (data) {

                            //arrotondo il valore a 2 cifre decimali
                            var value = Math.round(data.value * 100) / 100 ;

                            return "Importo: " + value;
                        }
                    },
                    {
                        column: "importRistornato",
                        summaryType: "sum",
                        displayFormat: "Totale: {0}",
                        showInGroupFooter: true,
                        customizeText: function (data) {

                            //arrotondo il valore a 2 cifre decimali
                            var value = Math.round(data.value * 100) / 100 ;

                            return "Importo: " + value;
                        }
                    }
                ]
            };
            gridOptions.onToolbarPreparing = function(e){
                var toolbarItems = e.toolbarOptions.items;

                // Adds a new item
                toolbarItems.push({
                    widget: "dxButton",
                    options: {
                        hint: "Crea lista di lavoro", icon: "user", onClick: function () {

                            var container = $('<div class="create-worker-list-cnt"><span class="text-bold">Descrizione</span><input type="text" class="field gui-input mt5" name="descrList"></div>');

                            var dialog = container.modalDialog({
                                autoOpen: true,
                                title: "Crea lista di lavoro",
                                destroyOnClose: true,
                                height: 120,
                                width: 300,
                                buttons: {
                                    Crea: {
                                        primary: true,
                                        command: function () {
                                            var descrListaLavoro = container.find("input[name=descrList]").val();
                                            var selectedrows = self.quotepagate.getSelectedRowsData();

                                            var svc = new model.AjaxService();
                                            var data = {};
                                            data.selectedrows = selectedrows;
                                            svc.set("url", BASE + "listalavoro/deleghe/" + encodeURIComponent(descrListaLavoro));
                                            svc.set("contentType", "application/json");
                                            svc.set("data", JSON.stringify(selectedrows));
                                            svc.set("method", "POST");

                                            svc.on("load", function (response) {
                                                $.loader.hide({parent: 'body'});
                                                dialog.modalDialog("close");

                                                // response è l'id della lista lavoro da visualizzare
                                                ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                                    id: response
                                                });

                                            });
                                            svc.on("error", function (error) {
                                                $.loader.hide({parent: 'body'});
                                                $.notify.error(error);
                                            });

                                            svc.load();
                                            $.loader.show({parent: 'body'});
                                        }
                                    }
                                }
                            });


                        }
                    },
                    location: "after"
                });
            };
             self.quotepagate = $("#quotePagateGrid").dxDataGrid(
                gridOptions).dxDataGrid("instance");
        },
        initQuoteCompleteGrid: function(response){
            gridOptions.dataSource = response.quote;
            gridOptions.onRowPrepared = (function (e) {
                    var ristorno = e.component.cellValue(e.rowIndex, "Ristorno");
                    if (ristorno && ristorno !== response.titolo) {
                        e.rowElement[0].style.backgroundColor = '#dcd74a';
                    }
                });
            gridOptions.onToolbarPreparing = function(e){
                var toolbarItems = e.toolbarOptions.items;

                // Adds a new item
                toolbarItems.push({
                    widget: "dxButton",
                    options: {
                        hint: "Crea lista di lavoro", icon: "user", onClick: function () {

                            var container = $('<div class="create-worker-list-cnt"><span class="text-bold">Descrizione</span><input type="text" class="field gui-input mt5" name="descrList"></div>');

                            var dialog = container.modalDialog({
                                autoOpen: true,
                                title: "Crea lista di lavoro",
                                destroyOnClose: true,
                                height: 120,
                                width: 300,
                                buttons: {
                                    Crea: {
                                        primary: true,
                                        command: function () {
                                            var descrListaLavoro = container.find("input[name=descrList]").val();
                                            var selectedrows = self.quotecomplete.getSelectedRowsData();

                                            var svc = new model.AjaxService();
                                            var data = {};
                                            data.selectedrows = selectedrows;
                                            svc.set("url", BASE + "listalavoro/deleghe/" + encodeURIComponent(descrListaLavoro));
                                            svc.set("contentType", "application/json");
                                            svc.set("data", JSON.stringify(selectedrows));
                                            svc.set("method", "POST");

                                            svc.on("load", function (response) {
                                                $.loader.hide({parent: 'body'});
                                                dialog.modalDialog("close");

                                                // response è l'id della lista lavoro da visualizzare
                                                ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                                    id: response
                                                });

                                            });
                                            svc.on("error", function (error) {
                                                $.loader.hide({parent: 'body'});
                                                $.notify.error(error);
                                            });

                                            svc.load();
                                            $.loader.show({parent: 'body'});
                                        }
                                    }
                                }
                            });


                        }
                    },
                    location: "after"
                });
            };
            self.quotecomplete = $("#quoteCompleteGrid").dxDataGrid(
                gridOptions).dxDataGrid("instance");
        },
        initPagamentoReferentiGrid: function(response){
            console.log(response);

            $("#pagamentoReferentiGrid").dxDataGrid({
                    dataSource: response.pagamentiReferenti,
                    columns: [
                        {
                            caption: "Referente",
                            dataField: "referente"
                        },
                        {
                            caption: "Totale Ristornato",
                            dataField: "totaleRistornato"
                        },
                        {
                            caption: "Totale",
                            dataField: "totale"
                        },
                        {
                            caption: "Numero Lavoratori",
                            dataField: "numeroLavoratori"
                        },
                        {
                            caption: "Numero Lavoratori Edili",
                            dataField: "numeroLavoratoriEdili"
                        },
                        {
                            caption: "Numero Lavoratori Non Edili",
                            dataField: "numeroLavoratoriNonEdili"
                        },
                        {
                            caption: "Ristorno Con Costo Tessere Solo Edili",
                            dataField: "ristornoConCostoTessereSoloEdili"
                        },
                        {
                            caption: "Ristorno Con Costo Tessere Solo Non Edili",
                            dataField: "ristornoConCostoTessereSoloNonEdili"
                        },
                        {
                            caption: "Ristorno Con Costo Tessere",
                            dataField: "ristornoConCostoTessere"
                        }
                    ],

                    searchPanel: {
                        visible: true
                    },
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
                    paging: {
                        pageSize: 20
                    },
                    sorting: {
                        mode: "multiple"
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
                    allowColumnReordering: true,
                    allowColumnResizing: true,
                    columnAutoWidth: true,
                    headerFilter: {
                        allowSearch: true,
                        height: 325,
                        searchTimeout: 500,
                        texts: {},
                        visible: true,
                        width: 252
                    },
                    groupPanel: {
                        allowColumnDragging: true,
                        emptyPanelText: 'Trascina una colonna qui',
                        visible: true
                    },
                    hoverStateEnabled: true
                }).dxDataGrid("instance");
        },
        initPieChart: function(response) {
            console.log(response);
            $("#pieChart").dxPieChart({
                palette: "bright",
                dataSource: response.pagamentiReferenti,
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
                        argumentField: "referente",
                        valueField: "totaleRistornato",
                        label: {
                            visible: true,
                            connector: {
                                visible: true,
                                width: 1
                            }
                        }
                    }
                ],
                title: "Importo Ristornato",
                "export": {
                    enabled: true
                },
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
        }
    });


    var ListaRistorni = core.AObject.extend({

        ctor: function (context) {
            ListaRistorni.super.ctor.call(this);
            this.context = context;
        },
        init: function(){
            var self = this;

            var service = new  model.AjaxService();
            service.set("contentType", "application/json");
            var route = BASE + "ristorniquoteinpsfull" ;
            service.set("url", route);
            service.set("method", "GET");

            service.on('load', function(response){

                $("#lista").dxDataGrid({
                    dataSource: response,
                    columns: [
                        {caption: "Data", dataField: "data" , dataType: "date"},
                        {caption: "Titolo", dataField: "titolo",
                            cellTemplate: function (container, options) {
                                $("<a />")
                                    .click(function() {

                                        ui.Navigation.instance().navigate("ristornoinps", "index", {
                                            id:options.data.id
                                        });


                                    })
                                    .text(options.value)
                                    .attr("target", "_blank")
                                    .appendTo(container);
                            }},
                        {caption: "Percentuale Ristorno", dataField: "percentualeRistorno"},
                        {caption: "Percentuale Ristorno Edili", dataField: "percentualeRistornoEdili"},
                        {caption: "Costo Inps Riga", dataField: "costoInpsRiga"},
                        {caption: "Costo Tessera", dataField: "costoTessera"}
                    ],
                    showBorders: true,
                    searchPanel: {
                        visible: true
                    },
                    columnChooser: {
                        enabled: true
                    },
                    "export": {
                        enabled: true,
                        fileName: "quote",
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
                    }
                }).dxDataGrid("instance");


                $.loader.hide({parent:'body'});
            });
            service.on("error", function(error){
                $.loader.hide({parent:'body'});

                alert("Errore: "  + error);
            });

            service.load();
            $.loader.show({parent:'body'});


        }

    });

    exports.Dashboard = Dashboard;
    exports.ListaRistorni = ListaRistorni;

    return exports;


});