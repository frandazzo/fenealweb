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
            var route = BASE + "magazzinodeleghelecce/report" ;

            var svc =  this.__createService(true, route, searchParams);
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

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");


                    // aggiungo tasto GENERA DELEGHE
                    if ($(".generate-del").length == 0) {
                        var delGeneration = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Genera deleghe">' +
                            '<button type="button" class="btn btn-primary full-width generate-del">' +
                            '<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>' +
                            '</button></div>';

                        $(".toolbox-buttons-cnt").append($(delGeneration));
                        $(".generate-del").parent().tooltip();
                        $(".generate-del").click(function() {

                            location.href = BASE + "magazzinodeleghelecce/generadeleghe?province="+data.province+"&parithetic="+data.parithetic+"&collaborator="+data.collaborator;
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
                    // { dataField:"otherfiled", caption:"AAAA", visible : true, calculateCellValue:function(data){
                    //     return data.delegaSettore;
                    // }},

                    { dataField:"delData", visible : true, visibleIndex: 3, dataType:'date'},
                    { dataField:"delEnte", visible : true, visibleIndex: 4},
                    { dataField:"delProvincia", visible : true, visibleIndex: 5},
                    { dataField:"giorni", visible : true, visibleIndex: 6, caption:"Giorni dalla sottoscrizione"},
                    { dataField:"causale", visible : true, visibleIndex: 7},
                    { dataField:"delCollaboratore", visible : false},
                    { dataField:"delegaCollaboratore", visible : false},

                    { dataField:"lavoratoreNomeCompleto", fixed :true, visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var completeName = options.data.lavoratoreNomeCompleto;// surname + " " + name + " (" + datanas + ")";
                            var fiscalCode = options.data.lavoratoreCodiceFiscale;

                            $("<a />")
                                .text(completeName)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        }



                    },
                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    { dataField:"lavoratoreCodiceFiscale", visible : true,visibleIndex: 1},
                    { dataField:"lavoratoreDataNascita", visible : false},
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
                    { dataField:"lavoratoreCodiceCassaEdile", visible : false},
                    { dataField:"lavoratoreCodiceEdilcassa", visible : false},
                    { dataField:"lavoratoreFondo", visible : false},
                    { dataField:"lavoratoreNote", visible : false},
                    { dataField:"lavoratoreId", visible : false},
                    // { dataField:"delegaProvince"},
                    // { dataField:"delegaDataDocumento",
                    //     customizeText: function(data) {
                    //         return Globalize.format(data.value, "dd/MM/yyyy");
                    //     }},
                    // { dataField:"delegaSettore"},
                    // { dataField:"delegaEnteBilaterale"},
                    // { dataField:"aziendaRagioneSociale"}

                    { dataField:"Checkbox", visible : true, dataType: "boolean",
                        calculateCellValue: function(e) {
                            if (e.giorni >= 40) {
                                return true;
                            }
                            return false;
                        }
                    }


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
                    label: "Report deleghe lecce"
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




    var MagazzinoCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, magazzinoDelId, workerId){
            MagazzinoCrudFormAppView.super.ctor.call(this, formService);

            this.magazzinoDelId = magazzinoDelId;
            this.workerId = workerId;
        },
        getToolbarButtons: function() {
            var self = this;

            return [
                {
                    text: "Annulla",
                    command: function() {
                        self.close();
                    },
                    icon: "arrow-left"
                },
                {
                    text: "Salva",
                    command: function() {
                        self.form.submit();
                    },
                    icon: "save"
                }
            ];

        },

        getBreadcrumbItems: function() {
            var self = this;
            var title = "Crea magazzino delega";
            if (self.magazzinoDelId)
                title = "Modifica magazzino delega";

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
                    label: "Lista magazzino deleghe",
                    href: ui.Navigation.instance().navigateUrl("magazzinocrud", "index", {
                        e: self.formService.get("gridIdentifier"),
                        reload: 1,
                        workerId : self.workerId
                    })
                },
                {
                    label: title
                }
            ];
        },

        close: function() {
            var self = this;
            ui.Navigation.instance().navigate("magazzinocrud", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                workerId : self.workerId
            });
        }

    });
    var MagazzinoCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, workerId){
            MagazzinoCrudGridAppView.super.ctor.call(this, gridService);

            this.workerId = workerId;
        },
        create: function() {
            var self = this;
            ui.Navigation.instance().navigate("magazzinocrud", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
            });
        },

        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("magazzinocrud", "edit", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                id: id,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
            });
        },
        getBreadcrumbItems: function() {
            var self = this;

            return [
                {
                    pageTitle: "FenealWeb"
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
                    label: "Lista magazzino deleghe"
                }
            ];
        }

    });



    exports.ReportDelegheAppView = ReportDelegheAppView;
    exports.MagazzinoCrudFormAppView = MagazzinoCrudFormAppView;
    exports.MagazzinoCrudGridAppView = MagazzinoCrudGridAppView;
    return exports;

});
