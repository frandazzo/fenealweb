/**
 * Created by angelo on 16/11/2017.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, resultsConfigurer) {

    var exports = {};

    var TraceLoginRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service){
            TraceLoginRemoteView.super.ctor.call(this, service);

            var self = this;

            self.traceLogins = null;

            self.on("load", function(){

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();


                $.loader.hide({parent:'body'});

                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.traceLogins);
                //una volta ottenuti i risultati la griglia devexpress mostra una loader
                //di attesa per la renderizzazione degli stessi! in quel momento rendo
                //visibile l'intera area
                //scrollando fino a rendere visibile la griglia
                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                //configuro la navigabilità e la toolbar delle actions del report che visualizza la tracciatura login
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "tracciatura login", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});


            });

        },

        initGrid : function(responseData) {


            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"id", visible : false},
                    { dataField:"year", visible : true, caption:"Anno"},
                    { dataField:"month", visible : true, caption:"Mese"},
                    { dataField:"company", visible : true, caption:"Azienda"},
                    { dataField:"counterApp", visible : true, caption:"Contatore App"},
                    { dataField:"counterWebsite", visible : true, caption:"Contatore sito web"}
                ],
                "export": {
                    enabled: false,
                    fileName: "tracciatura_login",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "tracelogins"
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
                        column: "id",
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
                    mode:"none"
                },
                hoverStateEnabled: true

            }).dxDataGrid("instance");

            return grid;

        },

        onServiceLoad: function(traceLoginsViewResponse) {
            var self = this;

            self.traceLogins = traceLoginsViewResponse.traceLogins;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(traceLoginsViewResponse.content);
            this.container.empty().append(this.content);
            this.invoke("load");
        },

        createToolbar: function() {
            var self = this;
            var $t = $("#toolbar");

            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
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
                    label: "Tracciatura Login"
                }
            ];
        }
    });

    var TraceActivityRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service) {
            TraceActivityRemoteView.super.ctor.call(this, service);

            var self = this;

            self.traceActivities = null;

            self.on("load", function() {

                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();

                $.loader.hide({parent:'body'});

                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.traceActivities);

                //una volta ottenuti i risultati la griglia devexpress mostra una loader
                //di attesa per la renderizzazione degli stessi! in quel momento rendo
                //visibile l'intera area
                //scrollando fino a rendere visibile la griglia
                $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                //configuro la navigabilità e la toolbar delle actions del report che visualizza la tracciatura delle attività utente
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "tracciatura attività", false);
                reportResultsConfigurer.init();


                //$.loader.show({parent:'body'});

            });
        },

        initGrid : function(responseData) {

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                wordWrapEnabled: true,
                columns:[
                    { dataField:"id", visible : false},
                    { dataField:"operationDate", visible : true, caption:"Data operazione", dataType:'date', visibleIndex: 1},
                    { dataField:"operationYear", visible : true, caption:"Anno"},
                    { dataField:"operationMonth", visible : true, caption:"Mese"},
                    { dataField:"activity", visible : true, caption:"Attività",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var traceId = options.data.id;

                            $("<a />")
                                .text(options.data.activity)
                                .attr("href", "javascript:;")
                                .on('click', function() {
                                    $.loader.show({parent:'body', zIndex: 9999});
                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "trace/" + traceId + "/detail";
                                    svc.set("method", "GET");
                                    svc.on({
                                        load: function(response) {
                                            $.loader.hide({parent:'body'});

                                            var activityDetail = response || "";

                                            var dialog = $("<p>" + activityDetail + "</p>").modalDialog({
                                                autoOpen: true,
                                                title: "Dettaglio attività",
                                                destroyOnClose: true,
                                                height: 250,
                                                width:  400,
                                                buttons: {
                                                }
                                            });
                                        },
                                        error: function (error){
                                            $.loader.hide({parent:'body'});
                                            $.notify.error(error);
                                        }
                                    });
                                    svc.load();
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"activityParams", visible : true, caption:"Parametri"},
                    // { dataField:"activityDetail", visible : true, caption:"Dettaglio"}
                ],
                "export": {
                    enabled: false,
                    fileName: "tracciatura_attività_utente",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "traceactivities"
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
                        column: "id",
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
                    mode:"none"
                },
                hoverStateEnabled: true

            }).dxDataGrid("instance");

            return grid;

        },

        onServiceLoad: function(traceActivitiesViewResponse) {
            var self = this;

            self.traceActivities = traceActivitiesViewResponse.traceActivities;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(traceActivitiesViewResponse.content);
            this.container.empty().append(this.content);
            this.invoke("load");
        },

        createToolbar: function() {
            var self = this;
            var $t = $("#toolbar");

            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
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
                    label: "Lista degli utenti",

                    href: ui.Navigation.instance().navigateUrl(window.appcontext.roleid!= 3 ? "users" : "crud", "index", {e:"dummyuser"})
                },
                {
                    label: "Lista di dettaglio attività"
                }
            ];
        }
    });

    exports.TraceLoginRemoteView = TraceLoginRemoteView;
    exports.TraceActivityRemoteView = TraceActivityRemoteView;

    return exports;

});