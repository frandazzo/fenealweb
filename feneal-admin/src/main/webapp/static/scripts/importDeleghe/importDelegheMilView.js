/**
 * Created by fgran on 14/04/2016.
 */
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
        searchDelegheMil: function(searchParams){
            var route = BASE + "importdeleghemil" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        __createService: function (isJsonContentType, route, data){

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
            service.set("method", "POST");
            return service;
        }
    });

    var ImportaDelegheMilAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ImportaDelegheMilAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "150px");

                // Modifico il valore della checkbox (on --> true)
                $("input[type=checkbox]").change(function() {
                    if($(this).is(":checked"))
                        $(this).val(true);
                    else
                        $(this).val(false);
                }).change();
            });

            self.formView.on("submit", function(){

                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchDelegheMil(data);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    self.initTabsPanel();
                    var grid = self.initGridconCodice(response);
                    var grid2 = self.initGridsenzaCodice(response);


                     $('html, body').animate({scrollTop: $('#conCodiceGrid').offset().top - 160}, 1400, "swing");



                    // aggiungo tasto IMPORTA DELEGHE
                    if ($(".elabora-richieste").length == 0) {
                        var delGeneration = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Invia dati al server">' +
                            '<button type="button" class="btn btn-primary full-width elabora-richieste">' +
                            '<span class="fa fa-reply" aria-hidden="true"></span>' +
                            '</button></div>';

                        $(".toolbox-buttons-cnt").append($(delGeneration));
                        $(".elabora-richieste").parent().tooltip();
                        $(".elabora-richieste").click(function () {
                            var selectedrows = grid.getSelectedRowsData();

                            if (selectedrows.length == 0) {
                                $.notify.error("Selezionare almeno un elemento");
                                return false;
                            }

                            var container = $('<div class="create-elabora-richieste-cnt"><span class="text-bold">Importare le deleghe selezionate?</span></div>');

                            var dialog = container.modalDialog({
                                autoOpen: true,
                                title: "Importa dati al server",
                                destroyOnClose: true,
                                height: 70,
                                width: 300,
                                buttons:{
                                    Ok: {
                                        primary: true,
                                        command: function() {
                                            var svc = new  fmodel.AjaxService();
                                            var data = {};
                                            data.selectedrows = selectedrows;
                                            svc.set("url", BASE + "importDB");
                                            svc.set("contentType", "application/json");
                                            svc.set("data", JSON.stringify(selectedrows));
                                            svc.set("method", "POST");

                                            svc.on("load", function(response){
                                                $.loader.hide({parent:'body'});
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
                     var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "deleghemil", true);
                     reportResultsConfigurer.init();

                    var reportResultsConfigurer2 = new resultsConfigurer.ReportUiConfigurer(grid2, "deleghemil", true);
                    reportResultsConfigurer2.init();



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
        initGridconCodice : function(responseData){

            var grid = $('#conCodiceGrid').dxDataGrid({
                dataSource:responseData.conCodici,
                columns:[
                    {allowEditing:false, dataField:"collaborator",  width: 230,visible : true, caption:"Collaboratore",
                        cellTemplate: function (container, options) {
                            $("<div>").dxSelectBox({
                                items: responseData.collaboratori,
                                value:responseData.collaboratori[0],
                                displayExpr: "description",
                                acceptCustomValue: false,
                                onCustomItemCreating: function(e) {
                                    if(!e.customItem) { e.customItem = e.text; }
                                    }
                            }).appendTo(container);
                        }
                    },
                    { allowEditing:false, dataField:"numProgressivo", visible : true, caption:"N.Progressivo"},
                    { allowEditing:false, dataField:"numProtocollo", visible : true, caption:"N.Protocollo"},
                    { allowEditing:false, dataField:"dataArchiviazione", visible : true, dataType:'date', caption:"Data Arch."},
                    { allowEditing:false, dataField:"dataArrivo", visible : true, dataType:'date', caption:"Data Arrivo"},
                    { allowEditing:false, dataField:"cognome", visible : true, caption:"Cognome"},
                    { allowEditing:false, dataField:"nome", visible : true, caption:"Nome"},
                    { allowEditing:false, dataField:"dataNascita", visible : true, dataType:'date', caption:"Data Nascita"},

                    { allowEditing:false, dataField:"codiceLavoratore", visible : true, caption:"Cod.Lavoratore",},
                    { allowEditing:false, dataField:"barCode", visible : true, caption:"Barcode"},
                     { allowEditing:false, dataField:"codiceFiscale", visible : true, caption:"Cod.Fiscale"},
                    { allowEditing:false, dataField:"codiceSind",  width: 60,visible : true, caption:"Cod.Sindacato"},
                    { allowEditing:false, dataField:"codiceCompr", width: 60,visible : true, caption:"Cod.Comprensorio"},
                    { allowEditing:false, dataField:"dataConferma", visible : true, dataType:'date', caption:"Data Conferma"},
                    { allowEditing:false, dataField:"dataAdesione", visible : true, dataType:'date', caption:"Data Adesione"},

                    { allowEditing:true, dataField:"numDelega", visible : true, caption:"N.Delega",
                        cellTemplate: function (container, options) {
                            $("<a />")
                                .text(options.data.numDelega)
                                .attr("href", options.data.filePath)
                                .attr("target", "_blank")
                                .appendTo(container);
                        }
                        },
                    { allowEditing:true, dataField:"note", visible : true, caption:"Note"}
                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "numProgressivo",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Deleghe trovate: " + data.value;
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
                    fileName: "reportdelegheMil",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "importdeleghemil"
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
        initGridsenzaCodice : function(responseData){

            var grid = $('#senzaCodiceGrid').dxDataGrid({
                dataSource:responseData.senzaCodici,
                columns:[
                    { allowEditing:false, dataField:"numProgressivo", visible : true, caption:"N.Progressivo"},
                    { allowEditing:false, dataField:"numProtocollo", visible : true, caption:"N.Protocollo"},
                    { allowEditing:false, dataField:"dataArchiviazione", visible : true, dataType:'date', caption:"Data Arch."},
                    { allowEditing:false, dataField:"dataArrivo", visible : true, dataType:'date', caption:"Data Arrivo"},
                    { allowEditing:false, dataField:"cognome", visible : true, caption:"Cognome"},
                    { allowEditing:false, dataField:"nome", visible : true, caption:"Nome"},
                    { allowEditing:false, dataField:"dataNascita", visible : true, dataType:'date', caption:"Data Nascita"},
                    { allowEditing:false, dataField:"barCode", visible : true, caption:"Barcode"},
                    { allowEditing:false, dataField:"codiceSind", width: 60,visible : true, caption:"Cod.Sindacato"},
                    { allowEditing:false, dataField:"codiceCompr", width: 60,visible : true, caption:"Cod.Comprensorio"},
                    { allowEditing:false, dataField:"dataConferma", visible : true, dataType:'date', caption:"Data Conferma"},
                    { allowEditing:false, dataField:"dataAdesione", visible : true, dataType:'date', caption:"Data Adesione"},

                    { allowEditing:true, dataField:"numDelega", visible : true, caption:"N.Delega",
                        cellTemplate: function (container, options) {
                            $("<a />")
                                .text(options.data.numDelega)
                                .attr("href", options.data.filePath)
                                .attr("target", "_blank")
                                .appendTo(container);
                        }
                    },
                    { allowEditing:true, dataField:"note", visible : true, caption:"Note"}
                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "numProgressivo",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Deleghe trovate: " + data.value;
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
                    fileName: "reportdelegheMil",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "importdeleghemil"
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
        initTabsPanel: function(){
            $("#tabpanel-container").dxTabPanel({
                loop: false,
                animationEnabled: true,
                swipeEnabled: true,
                deferRendering: false
            }).dxTabPanel("instance");
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
                    label: "Import deleghe milano"
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
        }
    });

    exports.ImportaDelegheMilAppView = ImportaDelegheMilAppView;

    return exports;

});
