/**
 * Created by fgran on 28/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils, resultsConfigurer) {

    var exports = {};


    var VersamentiGridAppView = fviews.GridAppView.extend({
        ctor: function(gridService, workerId){
            VersamentiGridAppView.super.ctor.call(this, gridService);

            var self = this;
            self.workerId = workerId;

            self.on("complete", function() {
                $("a[data-property=tipoDocumento]").click(function() {

                    var quotaId = $(this).parents("tr").attr("data-entity-id");

                    ui.Navigation.instance().navigate("dettaglioversamenti", "index", {
                        fs: this.fullScreenForm,
                        id: quotaId,
                        workerId: self.workerId
                    });
                });
            });
        },

        getToolbarButtons: function() {
            return [];
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
                    label: "Lista storico versamenti"
                }
            ];
        }

    });


    var VersamentiDettaglioRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, workerId){
            VersamentiDettaglioRemoteView.super.ctor.call(this, service);

            var self = this;
            self.workerId = workerId;
            self.quoteDetails = null;

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
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "versamenti", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});


            });

        },

        initGrid : function(responseData){

            

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"aziendaRagioneSociale", visible : true, caption:"Azienda",
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
                    { dataField:"provincia", visible : true, dataType:'date', caption:"Provincia"},
                    { dataField:"dataRegistrazione", visible : true, dataType:'date', caption:"Data registrazione"},
                    { dataField:"dataDocumento", visible : true, dataType:'date', caption:"Data documento"},
                    { dataField:"idQuota", visible : true, visibleIndex: 4, caption:"Id quota",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var idQuota = options.data.idQuota;

                            $("<a />")
                                .text(idQuota)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("dettaglioquote", "index", {
                                        fs: this.fullScreenForm,
                                        id: idQuota
                                    });
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"tipoDocumento", visible : true, visibleIndex: 5, caption:"Tipo documento"},
                    { dataField:"settore", visible : true, caption:"Settore"},
                    { dataField:"ente", visible : true, caption:"Ente"},
                    { dataField:"dataInizio", visible : true, dataType:'date', caption:"Data inizio"},
                    { dataField:"dataFine", visible : true, dataType:'date', caption:"Data fine"},
                    { dataField:"quota", visible : true, caption:"Quota"},
                    { dataField:"livello", visible : true, caption:"Livello"},
                    { dataField:"contratto", visible : true, caption:"Contratto"}
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
                        column: "tipoDocumento",
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
                    mode:"single"
                },
                hoverStateEnabled: true

            }).dxDataGrid("instance");

            return grid;

        },

        onServiceLoad: function(quoteDetailsViewResponse) {
            var self = this;
            self.quoteDetails = quoteDetailsViewResponse.quoteDetails;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(quoteDetailsViewResponse.content);
            this.container.empty().append(this.content);
            this.invoke("load");
        },

        createToolbar: function() {

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
                    label: "Lista storico versamenti"
                }
            ];
        }

    });

    exports.VersamentiGridAppView = VersamentiGridAppView;
    exports.VersamentiDettaglioRemoteView = VersamentiDettaglioRemoteView;

    return exports;

});
