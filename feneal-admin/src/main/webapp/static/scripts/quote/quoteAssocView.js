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


    var QuoteAssocRemoteView = fviews.RemoteContentView.extend({
        ctor: function(gridService){
            QuoteAssocRemoteView.super.ctor.call(this, gridService);

            var self = this;

            self.quoteAssociative = null;
            self.gridData = null;
            self.grid = null;

            self.on("load", function() {

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();


                $.loader.hide({parent:'body'});

                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.quoteAssociative);
                //una volta ottenuti i risultati la griglia devexpress mostra una loader
                //di attesa per la renderizzazione degli stessi! in quel momento rendo
                //visibile l'intera area
                //scrollando fino a rendere visibile la griglia
                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "quoteAssociative", false);
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
                    { dataField:"provincia", visible : true, caption:"Provincia" },
                    { dataField:"dataRegistrazione", visible : true, dataType:'date', caption:"Data registrazione"},
                    { dataField:"dataDocumento", visible : true, dataType:'date', caption:"Data documento"},
                    { dataField:"tipoDocumento", visible : true, caption:"Tipo documento",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.tipoDocumento;

                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("dettaglioquote", "index", {
                                        fs: this.fullScreenForm,
                                        id: options.data.id
                                    });
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"settore", visible : true, caption:"Settore"},
                    { dataField:"ente", visible : true, caption:"Ente"},
                    { dataField:"competenza", visible : true, caption:"Competenza"},
                    { dataField:"importedLogFilePath", visible : true, caption:"File di importazione",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.importedLogFilePath;

                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    location.href = BASE + "quoteassociative/" + options.data.id + "/downloadlog";
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"originalFileServerName", visible : true, caption:"File originale",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.originalFileServerName;

                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    location.href = BASE + "quoteassociative/" + options.data.id + "/downloadoriginal";
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"xmlFileServerName", visible : true, caption:"File xml normalizzato",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.xmlFileServerName;

                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    location.href = BASE + "quoteassociative/" + options.data.id + "/downloadxml";
                                })
                                .appendTo(container);
                        }
                    }
                ],
                "export": {
                    enabled: false,
                    fileName: "quote_associative",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "quoteassociative"
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

        onServiceLoad: function(quoteAssociativeViewResponse) {
            var self = this;
            self.quoteAssociative = quoteAssociativeViewResponse.quoteAssociative;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(quoteAssociativeViewResponse.content);
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
                    label: "Quote associative"
                }
            ];
        }
    });


    var QuotaDettaglioRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, quotaId){
            QuotaDettaglioRemoteView.super.ctor.call(this, service);

            var self = this;
            self.quotaId = quotaId;
            self.quoteDetails = null;
            self.gridData = null;
            self.grid = null;

            self.on("load", function(){

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();


                $.loader.hide({parent:'body'});



                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.quoteDetails);
                //una volta ottenuti i risultati la griglia devexpress mostra una loader
                //di attesa per la renderizzazione degli stessi! in quel momento rendo
                //visibile l'intera area
                //scrollando fino a rendere visibile la griglia
                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "quoteAssociative", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});


            });

        },

        initGrid : function(responseData){
            var self = this;

            function moveEditColumnToLeft(dataGrid) {
                dataGrid.columnOption("command:edit", {
                    visibleIndex: -1,
                    width: 80
                });
            }

            this.gridData = responseData;

            self.grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { allowEditing:false, dataField:"lavoratoreNomeCompleto", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var fiscalCode = options.data.lavoratoreCodiceFiscale;

                            $("<a />")
                                .text(options.data.lavoratoreNomeCompleto)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        },
                        calculateCellValue: function (data) {
                            return data.lavoratoreNomeCompleto;
                        }


                    },
                    { allowEditing:false, dataField:"aziendaRagioneSociale", visible : true, caption:"Azienda",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;

                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryfirm", "index", {
                                        id: options.data.aziendaId
                                    });
                                })
                                .appendTo(container);
                        }

                    },
                    { allowEditing:false, dataField:"id", visible : false},
                    { allowEditing:false, dataField:"provincia", visible : true, dataType:'date', caption:"Provincia"},
                    { allowEditing:false, dataField:"dataRegistrazione", visible : true, dataType:'date', caption:"Data registrazione"},
                    { allowEditing:false, dataField:"dataDocumento", visible : true, dataType:'date', caption:"Data documento"},
                    { allowEditing:false, dataField:"tipoDocumento", visible : true, visibleIndex: 5, caption:"Tipo documento"},
                    { allowEditing:false, dataField:"settore", visible : true, caption:"Settore"},
                    { allowEditing:false, dataField:"ente", visible : true, caption:"Ente"},
                    { allowEditing:false, dataField:"dataInizio", visible : true, dataType:'date', caption:"Data inizio"},
                    { allowEditing:false, dataField:"dataFine", visible : true, dataType:'date', caption:"Data fine"},
                    { allowEditing:true, dataField:"quota", visible : true, caption:"Quota"},
                    { allowEditing:true, dataField:"livello", visible : true, caption:"Livello"},
                    { allowEditing:false, dataField:"contratto", visible : true, caption:"Contratto"}
                ],
                "export": {
                    enabled: false,
                    fileName: "dettaglio_quote",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "dettaglioquote"
                },
                paging:{
                    pageSize: 35
                },
                sorting:{
                    mode:"multiple"
                },
                onRowUpdated: function(e) {
                    console.log(e);
                    //alert("RowUpdated");
                },
                onRowUpdating: function(e) {
                    console.log(e);


                    //alert("RowRemoving");
                    var id = e.key.id;
                    var updatedData = e.newData;

                    var svc = new fmodel.AjaxService();
                    svc.url = BASE + "quoteassociativeitem/update/" + self.quotaId + "/" + id ;
                    svc.set("method", "POST");
                    svc.set("contentType", "application/json");
                    svc.set("data", JSON.stringify(updatedData));
                    svc.on({
                        load: function(response){


                            $.notify.success("Completato");


                            //posso aggiungere la riga alla griglia

                        },
                        error: function (error){
                            e.cancel = true;
                            $.notify.error(error);
                        }
                    });
                    svc.load();



                    //alert("RowUpdating");
                },
                onRowRemoved: function(e) {
                    console.log(e);
                    //alert("RowRemoved");

                },
                onRowRemoving: function(e) {
                    console.log(e);
                    //alert("RowRemoving");

                    var id = e.key.id;

                    var svc = new fmodel.AjaxService();
                    svc.url = BASE + "quoteassociativeitem/delete/" + self.quotaId + "/" + id ;
                    svc.set("method", "DELETE");
                    svc.set("contentType", "application/json");
                    svc.on({
                        load: function(response){


                            $.notify.success("Completato");


                            //posso aggiungere la riga alla griglia

                        },
                        error: function (error){
                            e.cancel = true;
                            $.notify.error(error);
                        }
                    });
                    svc.load();





                },
                keyExpr: "id",
                onContentReady: function (e) {
                    var columnChooserView = e.component.getView("columnChooserView");
                    if (!columnChooserView._popupContainer) {
                        columnChooserView._initializePopupContainer();
                        columnChooserView.render();
                        columnChooserView._popupContainer.option("dragEnabled", false);
                    }

                    moveEditColumnToLeft(e.component);
                },
                onCellPrepared: function(e) {
                    if(e.rowType === "data" && e.column.command === "edit") {
                        var isEditing = e.row.isEditing,
                            $links = e.cellElement.find(".dx-link");

                        $links.text("");

                        if(isEditing){
                            $links.filter(".dx-link-save").addClass("dx-icon-save");
                            $links.filter(".dx-link-cancel").addClass("dx-icon-revert");
                        } else {
                            $links.filter(".dx-link-edit").addClass("dx-icon-edit");
                            $links.filter(".dx-link-delete").addClass("dx-icon-trash");
                        }
                    }
                },
                summary: {
                    totalItems: [{
                        column: "lavoratoreNomeCompleto",
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
                hoverStateEnabled: true,
                editing: {
                    mode: "row",
                    allowUpdating: true,
                    allowDeleting: true,
                    allowAdding: false,
                    texts:{
                        confirmDeleteMessage: 'Sicuro di voler eliminare la riga corrente?'
                    }
                }
            }).dxDataGrid("instance");

            return self.grid;

        },

        onServiceLoad: function(quoteDetailsViewResponse) {
            var self = this;
            self.quoteDetails = quoteDetailsViewResponse.quoteDetails;

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
                text: "Elimina quota",
                command: function() {

                    var dialog = $("<p>Sicuro di voler eliminare la quota?</p>").modalDialog({
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
                                    svc.url = BASE + "quoteassociative/delete/" + self.quotaId;
                                    svc.set("method", "DELETE");
                                    svc.on({
                                        load: function(response){

                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("quoteassociative", "index", {
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

            var button1 = {
                text: "Aggiungi nuova posizione",
                command: function() {

                    var formView = self.createAddNewItemForm();
                    formView.show();



                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Aggiungi item",
                        destroyOnClose: true,
                        height: 550,
                        width: 450,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {

                                    var data = self.normalizeSubmitResult(formView.form);

                                    //verifico se mancano dati obbligatori
                                    formView.form.resetValidation();


                                    var errors = self.validateForm(data);
                                    if (errors.errors.length){
                                        formView.form.handleValidationErrors(errors);
                                        return;
                                    }

                                    $.loader.show({parent:'body', zIndex: 9999});
                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "quoteassociativeitem/add/" + self.quotaId ;
                                    svc.set("method", "POST");
                                    svc.set("contentType", "application/json");
                                    svc.set("data", JSON.stringify(data));
                                    svc.on({
                                        load: function(response){
                                            $.loader.hide({parent:'body'});
                                            self.gridData.splice(0, 0, response);
                                            $('#reportContainer').dxDataGrid('instance').refresh();
                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");


                                            //posso aggiungere la riga alla griglia

                                        },
                                        error: function (error){
                                            $.loader.hide({parent:'body'});
                                            $.notify.error(error);
                                        }
                                    });
                                    svc.load();
                                }
                            }
                        }
                    });



                },
                icon: "a glyphicon glyphicon-plus"
            };


            var button2 = {
                text: "Duplica quota",
                command: function() {

                    var formView1 = self.duplicateQuotaForm();
                    formView1.show();

                    var dialog = formView1.container.modalDialog({
                        autoOpen: true,
                        title: "Duplica quota",
                        destroyOnClose: true,
                        height: 230,
                        width: 580,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {

                                    var data = self.normalizeSubmitResult(formView1.form);
                                    //
                                    // //verifico se mancano dati obbligatori
                                    formView1.form.resetValidation();
                                    //
                                    //
                                    var errors = self.validateDateForm(data);
                                    if (errors.errors.length){
                                        formView1.form.handleValidationErrors(errors);
                                        return;
                                    }
                                    //
                                    //
                                    $.loader.show({parent:'body', zIndex: 9999});
                                    $(dialog).modalDialog("close");
                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "duplicaquoteassociativeitem/" + self.quotaId ;
                                    svc.set("method", "POST");
                                    svc.set("contentType", "application/json");
                                    svc.set("data", JSON.stringify(data));
                                    svc.on({
                                        load: function(response){

                                            $.loader.hide({parent:'body'});

                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("quoteassociative", "index", {
                                                fs: this.fullScreenForm
                                            });

                                        },
                                        error: function (error){
                                            $.loader.hide({parent:'body'});
                                            $.notify.error(error);

                                        }
                                    });
                                    svc.load();



                                }
                            }
                        }
                    });



                },
                icon: "a glyphicon glyphicon-plus"
            };

            var button3 = {
                text: "Modifica competenza quota",
                command: function() {

                    var formView = self.modifyCompetenzaQuotaForm();
                    formView.show();



                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Modifica competenza quota",
                        destroyOnClose: true,
                        height: 230,
                        width: 580,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {

                                    var data = self.normalizeSubmitResult(formView.form);
                                    //
                                    // //verifico se mancano dati obbligatori
                                    formView.form.resetValidation();
                                    //
                                    //
                                    var errors = self.validateDateForm(data);
                                    if (errors.errors.length){
                                        formView.form.handleValidationErrors(errors);
                                        return;
                                    }
                                    //
                                    //

                                    $.loader.show({parent: 'body', zIndex: 99999});
                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "modifycompetencequoteassociativeitem/" + self.quotaId ;
                                    svc.set("method", "POST");
                                    svc.set("contentType", "application/json");
                                    svc.set("data", JSON.stringify(data));
                                    svc.on({
                                        load: function(response){
                                            $.loader.hide({parent:'body'});
                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("dettaglioquote", "index", {
                                                fs: this.fullScreenForm,
                                                id: self.quotaId,
                                                t: new Date().getTime()
                                            });
                                        },
                                        error: function(error){
                                            $.loader.hide({parent:'body'});
                                            $.notify.error(error);
                                        }
                                    });
                                    svc.load();
                                }
                            }
                        }
                    });



                },
                icon: "a glyphicon glyphicon-pencil"
            };


            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            $t.toolbar("add", button);
            $t.toolbar("add", button1);
            $t.toolbar("add", button2);
            $t.toolbar("add", button3);
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
                    label: "Elenco quote",

                    href: ui.Navigation.instance().navigateUrl("quoteassociative", "index", {})
                },
                {
                    label: "Dettaglio quota"

                }
            ];
        }
    });





    exports.QuoteAssocRemoteView = QuoteAssocRemoteView;
    exports.QuotaDettaglioRemoteView = QuotaDettaglioRemoteView;

    return exports;

});
