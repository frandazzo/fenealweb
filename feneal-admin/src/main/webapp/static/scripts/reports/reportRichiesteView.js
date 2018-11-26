/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils, resultsConfigurer) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        searchRichieste: function(searchParams){
            var route = BASE + "richieste/report" ;

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



    var ReportRichiesteAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportRichiesteAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "260px");
            });

            self.formView.on("submit", function(){

                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchRichieste(data);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "richieste", true);
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

                    { dataField:"richData", visible : true, dataType:'date'},
                    { dataField:"richRecipient", visible : true, visibleIndex: 5},
                    { dataField:"richProvince", visible : true},

                    { dataField:"lavoratoreNomeCompleto",fixed :true, fixedPosition:"left",visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var completeName = options.data.lavoratoreNomeCompleto;
                            var idLavoratore  = options.data.lavoratoreId;


                            $("<a />")
                                .text(completeName)
                                .attr("href", "javascript:;")
                                .on('click', function(){

                                    ui.Navigation.instance().navigate("summaryworker", "index", {
                                        id:idLavoratore

                                    })

                                })
                                .appendTo(container);
                        }

                    },

                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    { dataField:"lavoratoreCodiceFiscale", visible : false},
                    { dataField:"lavoratoreDataNascita", dataType:'date', visible : false},
                    { dataField:"lavoratoreNazionalita", visible : false},
                    { dataField:"lavoratoreProvinciaNascita", visible : false},
                    { dataField:"lavoratoreLuogoNascita", visible : false},
                    { dataField:"lavoratoreProvinciaResidenza", visible : false},
                    { dataField:"lavoratoreCittaResidenza", visible : false},
                    { dataField:"lavoratoreIndirizzo", visible : false},
                    { dataField:"lavoratoreCap", visible : false},
                    { dataField:"lavoratoreTelefono", visible : false},
                    { dataField:"lavoratoreCellulare", visible : false},
                    { dataField:"lavoratorMail", visible : false},
                    // { dataField:"lavoratoreCodiceCassaEdile", visible : false},
                    // { dataField:"lavoratoreCodiceEdilcassa", visible : false},
                    // { dataField:"lavoratoreFondo", visible : false},
                    // { dataField:"lavoratoreNote", visible : false},
                    { dataField:"lavoratoreId", visible : false},

                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "lavoratoreNomeCompleto",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Richieste trovate: " + data.value;
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
                    fileName: "richieste",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportrichieste"
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
                    label: "Report richieste"
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


    exports.ReportRichiesteAppView = ReportRichiesteAppView;


    return exports;

});