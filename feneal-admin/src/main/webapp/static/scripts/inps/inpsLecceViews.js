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
        searchQuote: function(searchParams){
            var route = BASE + "quoteinps/report" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        incrocia: function(searchParams){
            var route = BASE + "incrociaquoteinps/execute" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        deleteRistorno: function(id){
            var route = BASE + "ristorniquoteinpsfull/" + id ;

            var service = new  fmodel.AjaxService();
            service.set("contentType", "application/json");
            service.set("method", "DELETE");
            service.set("url", route);



            return service;
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


    var ReportQuoteInpsAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportQuoteInpsAppView.super.ctor.call(this, formService);

            var self = this;


            self.formView.on("load", function(){
                self.createCharts();
                self.createToolbar();
                self.createBreadcrumbs();
            });

            self.formView.on("submit", function(){
                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchQuote(data);


                svc.on("load", function(response){
                    console.log(response);

                    $.loader.hide({parent:'body'});


                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);

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
                columns: [
                {
                    dataField: "nominativo",
                    caption: "Nominativo",
                    calculateCellValue: function(data) {
                        return [data.lavoratoreCognome, data.lavoratoreNome]
                            .join(" ");
                    },
                    cellTemplate: function(container, options) {
                        console.log(options);
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
                }
            ],

                summary: {
                    totalItems: [{
                        column: "numeroDomanda",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Quote trovate: " + data.value;
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
                })


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
                    label: "Report quote inps puglia"
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


    var IncrociaQuoteInpsAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            IncrociaQuoteInpsAppView.super.ctor.call(this, formService);

            var self = this;


            self.formView.on("load", function(){
               // self.createCharts();
                self.createToolbar();
                self.createBreadcrumbs();



                var templateUrlInps = BASE + "incrociaquoteinps/inps";
                var templateUrlCsc = BASE + "incrociaquoteinps/csc";

                self.formView.container.find('.panel-body').after("<div class='templates'></div>");
                var panelFooter = $('.templates');
                panelFooter.append('<a href="' + templateUrlInps + '">Scarica modello file inps</a>');
                panelFooter.append('<a href="' + templateUrlCsc + '">Scarica modello file csc</a>');

               panelFooter.after("<div class='data-files-incrocio-inps'><div class='inps'><h3>INPS</h3></div> <div class='csc'><h3>CSC</h3></div></div>")




            });

            self.formView.on("submit", function(){
                //al click del pulsante submint rimuovo le validazioni
                self.formView.form.resetValidation();

                var data = self.normalizeSubmitResult(self.formView.form);
                //calcolo la lista dei file inps
                var dataPanel = $('.data-files-incrocio-inps');
                var inpsfiles = dataPanel.find('.inps').find('.file-incrocio')
                    .map(function() {
                        var s = $(this);
                        var validated = s.find('.glyphicon-ok').length == 1;
                        return {
                            filename: s.attr('data-filename'),
                            filepath : s.attr('data-physical-filename'),
                            validated : validated
                        };
                    })
                    .get();
                var cscfiles = dataPanel.find('.csc').find('.file-incrocio')
                    .map(function() {
                        var s = $(this);
                        var validated = s.find('.glyphicon-ok').length == 1;
                        return {
                            filename: s.attr('data-filename'),
                            filepath : s.attr('data-physical-filename'),
                            validated : validated
                        };
                    })
                    .get();

                data.inps = inpsfiles;
                data.csc = cscfiles;

                var errors = self.validate(data);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }


                var factory = new RepositoryServiceFactory();
                var svc = factory.incrocia(data);


                svc.on("load", function(response){
                    console.log(response);

                    $.loader.hide({parent:'body'});


                    var dialog = $("<p>Vuoi visualizzare l'incrocio effettuato?</p>").modalDialog({

                        autoOpen: true,
                        title: "Domanda",
                        destroyOnClose: true,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    ui.Navigation.instance().navigate("ristornoinps", "index", {
                                        id:response
                                    });
                                    dialog.modalDialog("close");
                                }
                            }
                        }

                    });
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
        validate: function(data){
            var result = {};
            result.errors = [];

            if (!data.title)
                result.errors.push(
                    {
                        property: "title",
                        message: "Titolo mancante"
                    }
                );

            if (data.inps.length == 0)
                result.errors.push(
                    {
                        property: "file1",
                        message: "Nessun file inps inserito"
                    }
                );


            if (data.csc.length == 0)
                result.errors.push(
                    {
                        property: "file2",
                        message: "Nessun file csc inserito"
                    }
                );


            if (data.inps.length > 0)
            {
                data.inps.forEach(function(elem){
                    if (elem.validated === false){
                        result.errors.push(
                            {
                                property: "file1",
                                message: "Rimuovere i file non validati oppure validarli prima"
                            }
                        );
                        return false;
                    }

                })
            }

            if (data.csc.length > 0)
            {
                data.csc.forEach(function(elem){
                    if (elem.validated === false){
                        result.errors.push(
                            {
                                property: "file2",
                                message: "Rimuovere i file non validati oppure validarli prima"
                            }
                        );
                        return false;
                    }

                });
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
                    label: "Incrocia quote inps"
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

    });








    var RistorniQuoteRemoteView =fviews.RemoteContentView.extend({
        ctor: function(service){
            RistorniQuoteRemoteView.super.ctor.call(this, service);

            var self = this;


            this.on("load", function(){



                self.createToolbar();
                self.createBreadcrumbs();

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
                    label: "Ristorni quote Inps"
                }
            ];
        }

    });

    var RistornoQuoteRemoteView =fviews.RemoteContentView.extend({
        ctor: function(service, id){
            RistornoQuoteRemoteView.super.ctor.call(this, service);

            this.id = id;
            var self = this;


            this.on("load", function(){



                self.createToolbar();
                self.createBreadcrumbs();

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
            var self= this;

            return [
                {
                    text: "Elimina",
                    command: function() {


                        var dialog = $("<p>Sicuro di voler eliminare l'elemento selezionato?</p>").modalDialog({
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

                                        var svc = new RepositoryServiceFactory().deleteRistorno(self.id);

                                        svc.on({
                                            load: function(response){

                                                $(dialog).modalDialog("close");
                                                $.notify.success("Operazione completata");

                                                //ritonrno alla modalità di ricerca
                                                ui.Navigation.instance().navigate("ristorniinps", "index", {
                                                    fs: this.fullScreenForm
                                                });
                                            },
                                            error: function (error){
                                                $.notify.error(error);
                                                $(dialog).modalDialog("close");
                                            }
                                        });
                                        svc.load();

                                    }

                                }
                            }
                        });
                    },
                    icon: "a glyphicons glyphicons-delete"
                }
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
                    label: "Ristorno quote Inps"
                }
            ];
        }

    });


    exports.IncrociaQuoteInpsAppView = IncrociaQuoteInpsAppView;
    exports.RistorniQuoteRemoteView = RistorniQuoteRemoteView;
    exports.RistornoQuoteRemoteView = RistornoQuoteRemoteView;
    exports.ReportQuoteInpsAppView = ReportQuoteInpsAppView;


    return exports;

});