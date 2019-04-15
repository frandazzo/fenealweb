define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts","geoUtils", "reports/reportResultsConfigurer"]
    , function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils, resultsConfigurer) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        searchLiberi: function(searchParams){
            var route = BASE + "prevedi/report" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        executeReportAnagrafiche: function(reportData){
            var route = BASE + "import/importprevedi" ;

            var svc =  this.__createService(true, route, reportData);
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

    var ReportAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportAppView.super.ctor.call(this, formService);
            this.geoUtils = new geoUtils.GeoUtils();

            var self = this;
            //questa sarà la lista dei risultati provenienti dal server
            self.listOfLiberi = null;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

            });

            self.formView.on("submit", function(){


                //appena avvio la ricerca devo rimuovere le select per filtrare le iscrizioni
                $('.top-toolbar').remove();

                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchLiberi(data);


                svc.on("load", function(response){

                    self.listOfLiberi = response;

                    //inizializzo gli eventuiuali filtri per le iscrizioni
                    self.initFilterOptions(response);

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "liberi", true);
                    reportResultsConfigurer.init();




                    //rimuovo il pulsante Stampa Completa se lo ho gia creato
                    $('.complete-print').remove();
                    //aggiungo un pulsante per la stampa completa
                    var btnStampaCompleta = $('<div class="col-md-12 col-xs-12 margin-bottom-10 p0 complete-print" title="" data-placement="top" data-toggle="tooltip" data-original-title="Stampa completa">'+
                        '<button class="btn btn-primary full-width complete-print-btn" type="button">'+
                        '<span class="glyphicon glyphicon-print" aria-hidden="true"></span>'+
                        '</button>'+
                        '</div>');


                    var toolboxContainer = $('.toolbox-seach-report').find('.back-white').children("div");
                    var smallToolboxContainer = $('.toolbox-seach-report-xs').find('.back-white').children("div");

                    toolboxContainer.append(btnStampaCompleta);
                    smallToolboxContainer.append(btnStampaCompleta.clone());
                    btnStampaCompleta.tooltip();




                    $('.complete-print').click(function() {

                        //ottengo la lista delle righe selezionate
                        var selectedrows = grid.getSelectedRowsData();

                        if (selectedrows.length == 0) {
                            $.notify.error("Selezionare almeno un elemento");
                            return false;
                        }

                        $.loader.show({parent:'body'});

                        var svc = new  fmodel.AjaxService();

                        svc.set("url", BASE + "liberi/retrievefilestampa");
                        svc.set("contentType", "application/json");
                        svc.set("data", JSON.stringify(selectedrows));
                        svc.set("method", "POST");

                        svc.on("load", function(response){
                            $.loader.hide({parent:'body'});

                            // response è il path del file da scaricare
                            location.href = BASE + "liberi/print?path="+encodeURIComponent(response);
                            //dialog.modalDialog("close");
                        });
                        svc.on("error", function(error){
                            $.loader.hide({parent:'body'});
                            $.notify.error(error);
                        });

                        svc.load();

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


        initFilterOptions: function(response){
            var self = this;



            //recupero la barra dove cè il tasto esegui ricerca
            //in tale barra alla fine della ricerca devo inserire una select per la scelta dell'anno in
            //cui filtrare eventiali iscriziojni e una select per la provincia in cui è stata effettuata
            //una iscrizione


            //visualizzo i filtri sui liberi solo se ne ho trovato qualcuno

            if (response.length > 0){
                var province = [];
                var anni = [];

                //devo ricercare tutte le provicne presenti nelle iscrizioni e tutti gli anni di iscrizione
                $.each(response, function(index, elem){
                    var iscrizioni = elem.iscrizioni;
                    //ciclo su tutte le iscrizioni
                    $.each(iscrizioni, function(index1, elem1){
                        province.push(elem1.nomeProvincia);
                        anni.push(elem1.anno);
                    })
                });

                //per prima cosa eseguo una distinct su entrambi gli array per prendere le
                // province ed gli anni una sola volta
                var distinctProvince=province.filter(function(current,index,province){
                    return index==province.indexOf(current);
                });
                var distinctAnni=anni.filter(function(current,index,anni){
                    return index==anni.indexOf(current);
                });

                distinctAnni.sort(function(a,b) {
                    return a - b;
                });

                distinctProvince.sort();


                //adesso se uno dei due array è vuoto non mostro nulla perche non ci sono iscrizioni
                if (province.length > 0){

                    var bottombar = $('.bottom-form-bar');

                    var yearSelect =$(
                        '<select name="yearSelect"  style="margin-right: 5px">'+
                        '</select>');

                    yearSelect.append('<option selected="selected" value="">(Seleziona anno di iscrizione)</option>');

                    $.each(distinctAnni, function(index, elem){
                        yearSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });


                    var provinceSelect =$(
                        '<select name="provinceSelect"  style="margin-right: 5px">'+
                        '</select>');

                    provinceSelect.append('<option selected="selected" value="">(Seleziona provincia)</option>');

                    $.each(distinctProvince, function(index, elem){
                        provinceSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });




                    var topToolbar = $('<div class="pull-left top-toolbar"></div>');


                    topToolbar.append(yearSelect);
                    topToolbar.append(provinceSelect);

                    bottombar.append(topToolbar);




                    //adesso posso attaccare gli handlers dei
                    yearSelect.change(function(){

                        var selected = this.value;
                        self.filterData(provinceSelect.val(),selected);

                    });
                    provinceSelect.change(function(){

                        var selected = this.value;
                        self.filterData(selected,yearSelect.val());

                    });



                }

            }

        },
        filterData : function(province, year){

            var self = this;

            if (!province && !year){
                self.initGrid(self.listOfLiberi);
                return;
            }

            var filterdList = [];
            $.each(self.listOfLiberi, function(index, elem){

                if (elem.numIscrizioni > 0){

                    $.each(elem.iscrizioni, function(index1, elem1){
                        //variabile che indica se il criterio per l'anno è soddisfatto
                        //se l'anno è nullo il criterio restituira sempre true delegando agli altri criteri
                        //la veirfica
                        //ecco perchè lo imposto a true
                        var foundAnno = true;
                        if (year){
                            if (elem1.anno != parseInt(year)){
                                foundAnno = false;
                            }
                        }

                        var foundProvince = true;
                        if (province){
                            if (elem1.nomeProvincia != province){
                                foundProvince = false;
                            }
                        }

                        if (foundAnno && foundProvince)
                        {
                            filterdList.push(elem);
                            return false;
                        }

                    });

                }


            });


            self.initGrid(filterdList);


        },
        initGrid : function(responseData){



            var viewFirm = false;
            if ($('input[name="firm"]').val())
                viewFirm = true;

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[



                    { dataField:"surname",  visible : true, visibleIndex: 0, caption:"Cognome"},
                    { dataField:"name", visible : true, visibleIndex: 1, caption:"Nome"},
                    { dataField:"birthDate", caption:"Data nascita", dataType:'date', visible : true, visibleIndex: 3},
                    { dataField:"fiscalcode", visible : true, visibleIndex: 4, caption:"Codice fiscale"},
                    { dataField:"livingProvince",  visible : true, visibleIndex: 5, caption:"Provincia residenza"},
                    { dataField:"livingCity", visible : true, visibleIndex: 6, caption:"Comune residenza"},
                    { dataField:"address", visible : true, visibleIndex: 7, caption:"Indirizzo"},
                    { dataField:"cap", visible : true, visibleIndex: 8, caption:"Cap"},
                    { dataField:"codCassa", visible : false},
                    { dataField:"cassaEdile", visible : true, visibleIndex: 9},
                    { dataField:"cassaEdileRegione", visible : false},
                    { dataField:"tipoAdesione", visible : true, visibleIndex: 10},
                    { dataField:"inquadramento", visible : true , visibleIndex: 11},
                    {dataField:"numIscrizioni", visible: true,visibleIndex: 2, caption:"Iscritto storico",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var numIscrizioni = options.data.numIscrizioni;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (numIscrizioni){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">sentiment_satisfied</i>'));
                                span.appendTo(container);
                            }

                        }
                    },



                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "surname",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Lavoratori prevedi trovati: " + data.value;
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
                    fileName: "prevedi",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportliberi"
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

                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;

                        container.addClass("internal-grid-container");
                        $("<div>").text("Iscrizioni lavoratore").appendTo(container);
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"nomeRegione",  visible : true, visibleIndex: 0},
                                    { dataField:"nomeProvincia",  visible : true, visibleIndex: 1},
                                    { dataField:"settore",  visible : true, visibleIndex: 2},
                                    { dataField:"ente",  visible : true, visibleIndex: 3},
                                    { dataField:"periodo",  visible : true, visibleIndex: 4},
                                    { dataField:"anno",  visible : true, visibleIndex: 5},
                                    { dataField:"azienda", visible : true, visibleIndex: 6},
                                    { dataField:"piva",  visible : true, visibleIndex: 7},
                                    { dataField:"livello",  visible : true, visibleIndex: 8},
                                    { dataField:"quota",  visible : true, visibleIndex: 9},
                                    { dataField:"contratto",  visible : true, visibleIndex: 10}


                                ],
                                dataSource: currentData.iscrizioni
                            }).appendTo(container);
                    }
                }

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
                    label: "Report non iscritti"
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

    var ImportaAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ImportaAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                var templateUrl = BASE + "import/preveditemplate"
                var panelFooter = self.formView.container.find('.panel-body').after("<div></div>");
                panelFooter.append('<a href="' + templateUrl + '">Scarica template</a>');


            });

            self.formView.on("submit", function(){

                var data = self.normalizeSubmitResult(self.formView.form);




                var factory = new RepositoryServiceFactory();
                var svc = factory.executeReportAnagrafiche(data);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    var href = BASE + response;
                    var dialog = $("<a href='" +  href + "'>"+ "Scarica elaborazione" + "</a>").modalDialog({

                        autoOpen: true,
                        title: "",
                        destroyOnClose: true,
                        // height: 250,
                        // width: 400,

                    });

                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    var dialog = $("<p>"+ error + "</p>").modalDialog({

                        autoOpen: true,
                        title: "",
                        destroyOnClose: true,
                        // height: 250,
                        // width: 400,
                    });
                    //setTimeout(function(){location.reload();}, 5000);
                });

                svc.load();
                $.loader.show({parent:'body'});



            });

            self.formView.form.on("cancel", function() {
                self.close();
            });



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
                    pageTitle: "Importazione anagrafiche"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Importazione anagrafiche prevedi"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;

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
        }
    });




    exports.ImportaAppView = ImportaAppView;
    exports.ReportAppView =ReportAppView;
    return exports;

});