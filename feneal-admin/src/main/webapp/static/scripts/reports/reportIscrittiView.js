/**
 * Created by fgran on 15/04/2016.
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
        searchIscritti: function(searchParams){
            var route = BASE + "iscritti/report" ;

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



    var ReportIscrittiAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportIscrittiAppView.super.ctor.call(this, formService);
            this.geoUtils = new geoUtils.GeoUtils();

            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "470px");

                $("div[data-property=delegationActiveExist]").css("margin-bottom", "10px");

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
                var svc = factory.searchIscritti(data);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");


                    // aggiungo tasto STAMPA TESSERA
                    if ($(".print-tessera").length == 0) {
                        var delGeneration = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Stampa tessera">' +
                            '<button type="button" class="btn btn-primary full-width print-tessera">' +
                            '<span class="glyphicon glyphicon-print" aria-hidden="true"></span>' +
                            '</button></div>';

                        $(".toolbox-buttons-cnt").append($(delGeneration));
                        $(".print-tessera").parent().tooltip();
                        $(".print-tessera").click(function () {

                            //ottengo la lista delle righe selezionate
                            var selectedrows = grid.getSelectedRowsData();
                            var sortOptons = grid.getDataSource().sort();
                            var store = new DevExpress.data.ArrayStore(selectedrows);
                            var result;
                            store.load({sort: sortOptons}).done(function(res) {
                                result = res;
                            })

                            console.log(result);



                            if (selectedrows.length == 0) {
                                $.notify.error("Selezionare almeno un elemento");
                                return false;
                            }

                            var formService = new fmodel.FormService();
                            formService.set("method", "GET");
                            formService.set("data", {});
                            formService.set("url", BASE + "iscritti/tessera/print");

                            var container = $('<div class="tessera-print-container"></div>');

                            var formView = new fviews.FormView(formService);
                            formView.container = container;

                            formView.on("render", function() {
                                $(".tessera-print-container").find(".panel-footer, .panel-heading").hide();
                                $(".panel-body").css("overflow", "hidden");
                                $("div[data-property=onlyWithoutTessera]").css("margin-left", "120px");
                                $("div[data-property=global]").css("margin-left", "120px");

                                // Modifico il valore della checkbox (on --> true)
                                $(".tessera-print-container").find("input[type=checkbox]").change(function() {
                                    if($(this).is(":checked"))
                                        $(this).val(true);
                                    else
                                        $(this).val(false);
                                });
                            });

                            formView.show();

                            var dialog = container.modalDialog({
                                autoOpen: true,
                                title: "Stampa tessera",
                                destroyOnClose: true,
                                height: 350,
                                width: 650,
                                buttons: {
                                    Stampa: {
                                        primary: true,
                                        command: function() {
                                            var province = $(".tessera-print-container").find("select[name=province]").val();
                                            var sector = $(".tessera-print-container").find("select[name=sector]").val();
                                            var onlyWithoutTessera = $(".tessera-print-container").find("input[name=onlyWithoutTessera]").is(":checked");
                                            var global = $(".tessera-print-container").find("input[name=global]").is(":checked");

                                            $.loader.show({parent:'body'});

                                            var svc = new  fmodel.AjaxService();
                                            var data = {
                                                rows: result,
                                                province: province,
                                                sector:sector,
                                                onlyWithoutTessera: onlyWithoutTessera,
                                                global: global
                                            };

                                            svc.set("url", BASE + "iscritti/retrievetesserefile");
                                            svc.set("contentType", "application/json");
                                            svc.set("data", JSON.stringify(data));
                                            svc.set("method", "POST");

                                            svc.on("load", function(response){
                                                $.loader.hide({parent:'body'});

                                                // response è il path del file da scaricare
                                                location.href = BASE + "iscritti/tessera/downloadfile?path="+encodeURIComponent(response)+"&province="+encodeURIComponent(province);
                                                dialog.modalDialog("close");
                                            });
                                            svc.on("error", function(error){
                                                $.loader.hide({parent:'body'});
                                                $.notify.error(error);
                                            });

                                            svc.load();

                                            $(dialog).modalDialog("close");
                                        }
                                    }
                                }
                            });

                        });
                    }


                    // aggiungo tasto CALCOLA STATISTICHE ISCRITTI
                    if ($(".calculate-stats-iscritti").length == 0) {
                        var calcStatGenerationCnt = '<div class="col-md-12 col-xs-3 margin-bottom-10 p0" data-toggle="tooltip" data-placement="top" title="Calcola statistiche iscritti">' +
                            '<button type="button" class="btn btn-primary full-width calculate-stats-iscritti">' +
                            '<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>' +
                            '</button></div>';

                        $(".toolbox-buttons-cnt").append($(calcStatGenerationCnt));
                        $(".calculate-stats-iscritti").parent().tooltip();
                        $(".calculate-stats-iscritti").click(function () {

                            var formService = new fmodel.FormService();
                            formService.set("method", "GET");
                            formService.set("data", {});
                            formService.set("url", BASE + "iscritti/stats");

                            var container = $('<div class="calculate-stats-container"></div>');

                            var formView = new fviews.FormView(formService);
                            formView.container = container;

                            formView.on("render", function() {
                                $(".calculate-stats-container").find(".panel-footer, .panel-heading").hide();
                                $(".panel-body").css("overflow", "hidden");
                            });

                            formView.show();

                            var dialog = container.modalDialog({
                                autoOpen: true,
                                title: "Calcola statistiche iscritti",
                                destroyOnClose: true,
                                height: 200,
                                width: 600,
                                buttons: {
                                    OK: {
                                        primary: true,
                                        command: function() {
                                            var province = $(".calculate-stats-container").find("select[name=province]").val();

                                            $.loader.show({parent:'body'});

                                            var svc = new  fmodel.AjaxService();

                                            svc.set("url", BASE + "iscritti/stats/getdataexport");
                                            svc.set("data", { province: province });
                                            svc.set("method", "GET");

                                            svc.on("load", function(response) {
                                                // response è un array di filenames
                                                $.loader.hide({parent:'body'});

                                                if (response) {
                                                    var formService = new fmodel.FormService();
                                                    formService.set("url", BASE + "iscritti/stats/selectdata");
                                                    formService.set("contentType", "application/json");
                                                    formService.set("method", "POST");
                                                    formService.set("data", JSON.stringify({filenames: response}));

                                                    var container = $('<div class="calculate-stats-selectdata-container"></div>');

                                                    var formView = new fviews.FormView(formService);
                                                    formView.container = container;

                                                    formView.on("render", function() {
                                                        $(".calculate-stats-selectdata-container").find(".panel-footer, .panel-heading").hide();
                                                        $(".panel-body").css("overflow", "hidden");
                                                    });

                                                    formView.show();

                                                    var dialog = container.modalDialog({
                                                        autoOpen: true,
                                                        title: "Calcola statistiche iscritti",
                                                        destroyOnClose: true,
                                                        height: 400,
                                                        width: 600,
                                                        buttons: {
                                                            Calcola: {
                                                                primary: true,
                                                                command: function() {
                                                                    $.loader.show({parent:'body'});

                                                                    var calculateStatStr = "";
                                                                    $.each($("input[name=dataexport]"), function(i,o) {
                                                                        calculateStatStr += ($(o).val() + ";");
                                                                    });

                                                                    var svc = new  fmodel.AjaxService();

                                                                    svc.set("url", BASE + "iscritti/stats/getstatisticsresult");
                                                                    svc.set("data", { province: province, filenames: calculateStatStr });
                                                                    svc.set("method", "GET");

                                                                    svc.on("load", function(response) {
                                                                        $.loader.hide({parent:'body'});

                                                                        //$(dialog).modalDialog("close");

                                                                        // Apertura modale che mostra risultati statistiche
                                                                        var statsContainer = $('<div class="stats-container"></div>');
                                                                        var resultContent = "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Num. Feneal: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.numFeneal + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Num. Filca: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.numFilca + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Num. Fillea: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.numFillea + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Num. Liberi: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.numLiberi + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Totale lavoratori: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.totLavoratori + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Totale sindacalizzati: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.totSindacalizzati + "</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Rappresentatività Feneal: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.rappresentativitaFeneal + "%</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Rappresentatività Filca: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.rappresentativitaFilca + "%</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Rappresentatività Fillea: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.rappresentativitaFillea + "%</span></div></div>" +
                                                                            "<div class='col-md-12 col-xs-12 p5 border-bottom-light-gray'>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class='text-bold'>Sindacalizzazione: </span></div>" +
                                                                            "<div class='col-md-6 col-xs-6 zero-padding'><span class=''>" + response.sindacalizzazione + "%</span></div></div>";

                                                                        statsContainer.append($(resultContent));
                                                                        var statsDialog = statsContainer.modalDialog({
                                                                            autoOpen: true,
                                                                            title: "Risultati statistiche iscritti",
                                                                            destroyOnClose: true,
                                                                            height: 350,
                                                                            width: 600,
                                                                            buttons: {}
                                                                        });
                                                                    });
                                                                    svc.on("error", function(error){
                                                                        $.loader.hide({parent:'body'});
                                                                        $.notify.error(error);
                                                                    });

                                                                    svc.load();

                                                                }
                                                            }
                                                        }
                                                    });

                                                } else {
                                                    $.notify.warn("Non ci sono risultati utili");
                                                }

                                            });
                                            svc.on("error", function(error){
                                                $.loader.hide({parent:'body'});
                                                $.notify.error(error);
                                            });

                                            svc.load();
                                        }
                                    }
                                }
                            });

                        });
                    }


                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "iscritti", true);
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

            //Globalize.culture("it-IT");

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"iscrittoCollaboratore", visible : false},
                    { dataField:"iscrittoDataRegistrazione", visible : true, dataType:'date'},
                    { dataField:"iscrittoQuota", visible : false},
                    { dataField:"iscrittoCompetenza", visible : true},
                    { dataField:"iscrittoContratto", visible : false},
                    { dataField:"iscrittoLivello", visible : false},
                    { dataField:"iscrittoProvincia",  visible : true},
                    { dataField:"iscrittoSettore", visible : true},
                    { dataField:"iscrittoEnteBilaterale", visible : true},


                    { dataField:"aziendaRagioneSociale", visible : true,

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;


                            var uri = encodeURI(BASE + "#/summaryfirm/index?id=" + options.data.aziendaId );

                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                //
                                // $("<a />")
                                //     .text(name)
                                //     .attr("href", "javascript:;")
                                //     .on('click', function(){
                                //         ui.Navigation.instance().navigate("summaryfirm", "index", {
                                //             id: options.data.aziendaId
                                //         });
                                //     })
                                .appendTo(container);
                        }
                    },
                    { dataField:"aziendaCitta", visible : false},
                    { dataField:"aziendaProvincia", visible : false},
                    { dataField:"aziendaCap", visible : false},
                    { dataField:"aziendaIndirizzo", visible : false},
                    { dataField:"aziendaNote", visible : false},
                    { dataField:"aziendaId", visible : false},

                    { dataField:"lavoratoreNomeCompleto", fixed :true, visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {

                            var name = options.data.lavoratoreNomeCompleto;



                            var fiscalCode = options.data.lavoratoreCodiceFiscale;
                            var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode );
                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                // $("<a />")
                                //     .text(completeName)
                                //     .attr("href", "javascript:;")
                                // .on('click', function(){
                                //     ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                //         fiscalCode:fiscalCode
                                //     });
                                // })
                                .appendTo(container);
                        }
                        // calculateCellValue: function (data) {
                        //     var surname = data.lavoratoreCognome;
                        //     var name = data.lavoratoreNome;
                        //    // var datanas =Globalize("it").formatDate(new Date(data.lavoratoreDataNascita));
                        //     return surname + " " + name;// + " (" + datanas + ")";
                        // }


                    },
                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    { dataField:"lavoratoreCodiceFiscale", visible : false},
                    { dataField:"lavoratoreDataNascita", dataType:'date',visible : false},
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
                            return "Iscritti trovati: " + data.value;
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
                    fileName: "iscritti",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportiscritti"
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
                    label: "Report iscritti"
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


    exports.ReportIscrittiAppView = ReportIscrittiAppView;


    return exports;

});
