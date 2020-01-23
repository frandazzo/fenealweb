/**
 * Created by fgran on 07/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, resultsConfigurer) {

    var exports = {};


    var SearchListaLavoroGridView = fviews.GridAppView.extend({
        ctor: function(gridService) {
            SearchListaLavoroGridView.super.ctor.call(this, gridService);

            var self = this;

            self.on("complete", function(){
                self.get("grid").showSearchForm();
                $(".panel-title").text("Ricerca liste di lavoro");

            });

        },
        edit: function(id){
            ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                fs: this.fullScreenForm,
                id: id
            });
        },
        getToolbarButtons: function() {
            var self = this;
            var buttons = [];

            return buttons;
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
                    label: "Ricerca liste di lavoro"
                }
            ];
        }

    });



    var ListeLavoroSummaryRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, listaId){
            ListeLavoroSummaryRemoteView.super.ctor.call(this, service);

            var self = this;
            self.listaId = listaId;
            self.lavoratoriLista = null;

            self.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                //inizializzo la griglia devexpress
                var grid = self.initGrid(self.lavoratoriLista);
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
                        });

                        console.log(result);


                        var filterExpression =  grid.getCombinedFilter(true);
                        if(selectedrows.length) {
                            var dataSource = new DevExpress.data.DataSource({
                                filter:filterExpression,
                                paginate: false,
                                store: new DevExpress.data.ArrayStore({
                                    data: selectedrows,
                                    key: "fiscalcode"
                                })
                            })
                            dataSource.load().done((r)=>{
                                selectedrows = r;

                            })
                        }


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

                                        var users = [];
                                        result.forEach(function(element){
                                            users.push({lavoratoreId: element.id});
                                        });


                                        var data = {
                                            rows: users,
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





                var loadFormListaLavoroOperations = function() {
                    var formService = new fmodel.FormService();
                    formService.set("method", "GET");
                    formService.set("data", {});
                    formService.set("url", BASE + "listalavoro/operations");

                    var container = $('<div class="listalavoro-operations-container"></div>');

                    var formView = new fviews.FormView(formService);
                    formView.container = container;

                    formView.on("render", function() {
                        $(".listalavoro-operations-container").find(".panel-footer, .panel-heading").hide();
                        $(".panel-body").css("overflow", "hidden");
                        $(".listalavoro-operations-container").find("div[data-property=description] .field-label").css("line-height", "20px");
                    });

                    return formView;
                };

                // GESTIONE PULSANTE  UNISCI CON LISTA
                $(".lista-merge").click(function () {

                    var formView = loadFormListaLavoroOperations();
                    formView.show();

                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Unisci con lista",
                        destroyOnClose: true,
                        height: 250,
                        width: 650,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    var description = $(".listalavoro-operations-container").find("input[name=description]").val();
                                    var otherListaId = $("input[name=listaOp]").val();
                                    $.loader.show({parent:'body'});

                                    var svc = new  fmodel.AjaxService();
                                    var data = {
                                        listaId: self.listaId,
                                        otherListaId: otherListaId,
                                        description: description
                                    };

                                    svc.set("url", BASE + "listalavoro/merge");
                                    svc.set("data", data);
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});

                                        // response è l'id della lista lavoro da visualizzare
                                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                            id: response
                                        });

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


                // GESTIONE PULSANTE  ESCLUDI CON LISTA
                $(".lista-exclude").click(function () {

                    var formView = loadFormListaLavoroOperations();
                    formView.show();

                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Escludi con lista",
                        destroyOnClose: true,
                        height: 250,
                        width: 650,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    var description = $(".listalavoro-operations-container").find("input[name=description]").val();
                                    var otherListaId = $("input[name=listaOp]").val();

                                    $.loader.show({parent:'body'});

                                    var svc = new  fmodel.AjaxService();

                                    var data = {
                                        listaId: self.listaId,
                                        otherListaId: otherListaId,
                                        description: description
                                    };

                                    svc.set("url", BASE + "listalavoro/exclude");
                                    svc.set("data", data);
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});

                                        // response è l'id della lista lavoro da visualizzare
                                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                            id: response
                                        });

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

                // GESTIONE PULSANTE  INTERSECA CON LISTA
                $(".lista-intersect").click(function () {

                    var formView = loadFormListaLavoroOperations();
                    formView.show();

                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Interseca con lista",
                        destroyOnClose: true,
                        height: 250,
                        width: 650,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    var description = $(".listalavoro-operations-container").find("input[name=description]").val();
                                    var otherListaId = $("input[name=listaOp]").val();

                                    $.loader.show({parent:'body'});

                                    var svc = new  fmodel.AjaxService();

                                    var data = {
                                        listaId: self.listaId,
                                        otherListaId: otherListaId,
                                        description: description
                                    };

                                    svc.set("url", BASE + "listalavoro/intersect");
                                    svc.set("data", data);
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});

                                        // response è l'id della lista lavoro da visualizzare
                                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                            id: response
                                        });

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


                // GESTIONE PULSANTE  CONFRONTA CON LISTA
                $(".lista-compare").click(function () {

                    var formView = loadFormListaLavoroOperations();
                    formView.show();

                    var dialog = formView.container.modalDialog({
                        autoOpen: true,
                        title: "Confronta con lista",
                        destroyOnClose: true,
                        height: 250,
                        width: 650,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    var description = $(".listalavoro-operations-container").find("input[name=description]").val();
                                    var otherListaId = $("input[name=listaOp]").val();


                                    ui.Navigation.instance().navigate("comparelistelavoro", "index", {
                                        fs: this.fullScreenForm,
                                        listaId: self.listaId,
                                        otherListaId: otherListaId,
                                        description: description
                                    });

                                    $(dialog).modalDialog("close");
                                }
                            }
                        }
                    });

                });


                // GESTIONE PULSANTE  INVIA SMS MULTIPLI
                $(".lista-send-sms").click(function () {

                    //ottengo la lista delle righe selezionate
                    var selectedrows = grid.getSelectedRowsData();

                    var filterExpression =  grid.getCombinedFilter(true);
                    if(selectedrows.length) {
                        var dataSource = new DevExpress.data.DataSource({
                            filter:filterExpression,
                            paginate: false,
                            store: new DevExpress.data.ArrayStore({
                                data: selectedrows,
                                key: "fiscalcode"
                            })
                        })
                        dataSource.load().done((r)=>{
                            selectedrows = r;

                        })
                    }


                    if (selectedrows.length == 0) {
                        $.notify.error("Selezionare almeno un elemento");
                        return false;
                    }

                    var formService = new fmodel.FormService();
                    formService.set("method", "GET");
                    formService.set("data", {});
                    formService.set("url", BASE + "comunicazioni/listalavoro/sms");

                    var container = $('<div class="listalavoro-sendsms-container"></div>');

                    var formView = new fviews.FormView(formService);
                    formView.container = container;

                    formView.on("render", function() {
                        $(".listalavoro-sendsms-container").find(".panel-footer, .panel-heading").hide();
                        $(".panel-body").css("overflow", "hidden");
                    });

                    formView.show();

                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Invia SMS",
                        destroyOnClose: true,
                        height: 300,
                        width: 650,
                        buttons: {
                            Invia: {
                                primary: true,
                                command: function() {

                                    formView.form.resetValidation();

                                    // Validazione dati

                                    var province = $(".listalavoro-sendsms-container").find("select[name=province]").val();
                                    var campagna = $(".listalavoro-sendsms-container").find("input[name=campagna]").val();
                                    var text = $(".listalavoro-sendsms-container").find("input[name=text]").val();

                                    var data = {
                                        lavoratori: selectedrows,
                                        province: province,
                                        campagna: campagna,
                                        text: text
                                    };

                                    var errors = self.validate(data);
                                    if (errors.errors.length){
                                        formView.form.handleValidationErrors(errors);
                                        return;
                                    }

                                    var svc = new  fmodel.AjaxService();


                                    svc.set("url", BASE + "comunicazioni/listalavoro/sendsms");
                                    svc.set("data", JSON.stringify(data));
                                    svc.set("contentType", "application/json");
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        $.notify.success("Operazione completata");
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


                // GESTIONE PULSANTE  AGGIUNGI LAVORATORE
                $(".add-worker").click(function () {

                    var formService = new fmodel.FormService();
                    formService.set("method", "GET");
                    formService.set("data", {});
                    formService.set("url", BASE + "listalavoro/addworker");

                    var container = $('<div class="listalavoro-addworker-container"></div>');

                    var formView = new fviews.FormView(formService);
                    formView.container = container;

                    formView.on("render", function() {
                        $(".listalavoro-addworker-container").find(".panel-footer, .panel-heading").hide();
                        $(".panel-body").css("overflow", "hidden");
                    });

                    formView.show();

                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Aggiungi lavoratore",
                        destroyOnClose: true,
                        height: 200,
                        width: 650,
                        buttons: {
                            OK: {
                                primary: true,
                                command: function() {
                                    var workerId = $(".listalavoro-addworker-container").find("input[name=worker]").val();

                                    if (!workerId)
                                        return;

                                    var svc = new  fmodel.AjaxService();

                                    svc.set("url", BASE + "listalavoro/" + self.listaId + "/addworker");
                                    svc.set("data", {workerId: workerId});
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        $.notify.success("Operazione completata");
                                        dialog.modalDialog("close");

                                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {id: self.listaId, t: new Date().getTime()})
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


                // GESTIONE PULSANTE  RIMUOVI LAVORATORE
                $(".remove-worker").click(function () {

                    //ottengo la lista delle righe selezionate
                    var selectedrows = grid.getSelectedRowsData();

                    var filterExpression =  grid.getCombinedFilter(true);
                    if(selectedrows.length) {
                        var dataSource = new DevExpress.data.DataSource({
                            filter:filterExpression,
                            paginate: false,
                            store: new DevExpress.data.ArrayStore({
                                data: selectedrows,
                                key: "fiscalcode"
                            })
                        })
                        dataSource.load().done((r)=>{
                            selectedrows = r;

                        })
                    }

                    if (selectedrows.length == 0) {
                        $.notify.error("Selezionare almeno un elemento");
                        return false;
                    }

                    var dialog = $("<p>Sicuro di voler eliminare gli elementi selezionati?</p>").modalDialog({
                        autoOpen: true,
                        title: "Elimina",
                        destroyOnClose: true,
                        height: 100,
                        width:  400,
                        buttons: {
                            send: {
                                label: "OK",
                                primary: true,
                                command: function() {

                                    var svc = new fmodel.AjaxService();
                                    svc.url = BASE + "listalavoro/" + self.listaId + "/deleteworkers";
                                    svc.set("contentType", "application/json");
                                    svc.set("method", "POST");
                                    svc.set("data", JSON.stringify({lavoratori: selectedrows}));
                                    svc.on({
                                        load: function(response){

                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("summarylistelavoro", "index", {id: self.listaId, t: new Date().getTime()})
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
                });

                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "liste lavoro", true);
                reportResultsConfigurer.init();


            });

        },

        validate: function(data){
            var result = {};
            result.errors = [];

            if (!data.campagna)
                result.errors.push(
                    {
                        property: "campagna",
                        message: "Campagna mancante"
                    }
                );

            if (!data.text)
                result.errors.push(
                    {
                        property: "text",
                        message: "Testo mancante"
                    }
                );

            return result;
        },

        initGrid : function(responseData){

            

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var fiscalCode = options.data.fiscalcode;

                            $("<a />")
                                .text(options.data.surname + " " + options.data.name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        }

                    },
                    { dataField:"fiscalcode", visible : true, caption:"Codice fiscale"},
                    { dataField:"birthDate", dataType:'date', visible : true, caption:"Data nascita"},
                    { dataField:"nationality", visible : true, caption:"Nazione nascita"},
                    { dataField:"birthPlace", visible : true, caption:"Com. nascita"},
                    { dataField:"livingProvince", visible : true, caption:"Prov. residenza"},
                    { dataField:"livingCity", visible : true, caption:"Com. residenza"},
                    { dataField:"address", visible : true, caption:"Indirizzo"},
                    { dataField:"cap", visible : true, caption:"Cap"},
                    { dataField:"cellphone", visible : true, caption:"Cellulare"},
                    { dataField:"phone", visible : true, caption:"Telefono"}
                ],
                "export": {
                    enabled: false,
                    fileName: "dettaglio_lista",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "dettagliolista"
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
                        column: "fiscalcode",
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

            return grid;

        },

        onServiceLoad: function(lavoratoriListaViewResponse) {
            var self = this;
            self.lavoratoriLista = lavoratoriListaViewResponse.lavoratoriLista;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(lavoratoriListaViewResponse.content);
            this.container.empty().append(this.content);
            if (!self.listaId)
                self.listaId = self.content.find('#id').val();
            this.invoke("load");

        },

        createToolbar: function() {
            var self = this;
            var button = {
                text: "Elimina lista",
                command: function() {

                    var dialog = $("<p>Sicuro di voler eliminare la lista?</p>").modalDialog({
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
                                    svc.url = BASE + "listalavoro/delete/" + self.listaId;
                                    svc.set("method", "DELETE");
                                    svc.on({
                                        load: function(response){

                                            $(dialog).modalDialog("close");
                                            $.notify.success("Operazione completata");

                                            ui.Navigation.instance().navigate("searchlistelavoro", "index", {
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

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            $t.toolbar("add", button);
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
                    label: "Ricerca liste di lavoro",
                    //vado alla ricerca delle liste di lavoro
                    href: ui.Navigation.instance().navigateUrl("searchlistelavoro", "index", {})
                },
                {
                    label: "Dettagli lista di lavoro"

                }
            ];
        }

    });


    var ListeLavoroComparisonRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, listaId){
            ListeLavoroComparisonRemoteView.super.ctor.call(this, service);

            var self = this;
            self.listaId = listaId;
            self.lavoratoriLista_aMinusB = null;
            self.lavoratoriLista_bMinusA = null;
            self.lavoratoriLista_intersection = null;

            self.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                //inizializzo le griglie devexpress
                var grid_aMinusB = self.initGrid_aMinusB(self.lavoratoriLista_aMinusB);
                var grid_bMinusA = self.initGrid_bMinusA(self.lavoratoriLista_bMinusA);
                var grid_intersection = self.initGrid_intersection(self.lavoratoriLista_intersection);

                //configuro la navigabilità e la toolbar delle actions del report
                var reportResultsConfigurer1 = new resultsConfigurer.ReportUiConfigurer(grid_aMinusB);
                reportResultsConfigurer1.init();

                var reportResultsConfigurer2 = new resultsConfigurer.ReportUiConfigurer(grid_bMinusA);
                reportResultsConfigurer2.init();

                var reportResultsConfigurer3 = new resultsConfigurer.ReportUiConfigurer(grid_intersection);
                reportResultsConfigurer3.init();

            });

        },

        initGrid_aMinusB : function(responseData){

            
            var grid = $('#reportContainer_aMinusB').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var fiscalCode = options.data.fiscalcode;

                            $("<a />")
                                .text(options.data.surname + " " + options.data.name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        }

                    },
                    { dataField:"fiscalcode", visible : true, caption:"Codice fiscale"},
                    { dataField:"birthDate", dataType:'date', visible : true, caption:"Data nascita"},
                    { dataField:"nationality", visible : true, caption:"Nazione nascita"},
                    { dataField:"birthPlace", visible : true, caption:"Com. nascita"},
                    { dataField:"livingCity", visible : true, caption:"Com. residenza"},
                    { dataField:"address", visible : true, caption:"Indirizzo"},
                    { dataField:"cap", visible : true, caption:"Cap"},
                    { dataField:"cellphone", visible : true, caption:"Cellulare"},
                    { dataField:"phone", visible : true, caption:"Telefono"}
                ],
                "export": {
                    enabled: true,
                    fileName: "lista_aMinusB",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "dettagliolista"
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
                        column: "surnamename",
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

            return grid;

        },

        initGrid_bMinusA : function(responseData){

            

            var grid = $('#reportContainer_bMinusA').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var fiscalCode = options.data.fiscalcode;

                            $("<a />")
                                .text(options.data.surname + " " + options.data.name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        }

                    },
                    { dataField:"fiscalcode", visible : true, caption:"Codice fiscale"},
                    { dataField:"birthDate", dataType:'date', visible : true, caption:"Data nascita"},
                    { dataField:"nationality", visible : true, caption:"Nazione nascita"},
                    { dataField:"birthPlace", visible : true, caption:"Com. nascita"},
                    { dataField:"livingCity", visible : true, caption:"Com. residenza"},
                    { dataField:"address", visible : true, caption:"Indirizzo"},
                    { dataField:"cap", visible : true, caption:"Cap"},
                    { dataField:"cellphone", visible : true, caption:"Cellulare"},
                    { dataField:"phone", visible : true, caption:"Telefono"}
                ],
                "export": {
                    enabled: true,
                    fileName: "lista_bMinusA",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "dettagliolista"
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
                        column: "surnamename",
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

            return grid;

        },

        initGrid_intersection : function(responseData){



            var grid = $('#reportContainer_intersection').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"surnamename", visible : true, caption:"Lavoratore",

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var fiscalCode = options.data.fiscalcode;

                            $("<a />")
                                .text(options.data.surname + " " + options.data.name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode
                                    });
                                })
                                .appendTo(container);
                        }

                    },
                    { dataField:"fiscalcode", visible : true, caption:"Codice fiscale"},
                    { dataField:"birthDate", dataType:'date', visible : true, caption:"Data nascita"},
                    { dataField:"nationality", visible : true, caption:"Nazione nascita"},
                    { dataField:"birthPlace", visible : true, caption:"Com. nascita"},
                    { dataField:"livingCity", visible : true, caption:"Com. residenza"},
                    { dataField:"address", visible : true, caption:"Indirizzo"},
                    { dataField:"cap", visible : true, caption:"Cap"},
                    { dataField:"cellphone", visible : true, caption:"Cellulare"},
                    { dataField:"phone", visible : true, caption:"Telefono"}
                ],
                "export": {
                    enabled: true,
                    fileName: "lista_intersection",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "dettagliolista"
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
                        column: "surnamename",
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

            return grid;

        },


        onServiceLoad: function(lavoratoriListaComparisonViewResponse) {
            var self = this;
            self.lavoratoriLista_aMinusB = lavoratoriListaComparisonViewResponse.lavoratoriLista_aMinusB;
            self.lavoratoriLista_bMinusA = lavoratoriListaComparisonViewResponse.lavoratoriLista_bMinusA;
            self.lavoratoriLista_intersection = lavoratoriListaComparisonViewResponse.lavoratoriLista_intersection;

            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(lavoratoriListaComparisonViewResponse.content);
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
                    label: "Ricerca liste di lavoro",
                    //vado alla ricerca delle liste di lavoro
                    href: ui.Navigation.instance().navigateUrl("searchlistelavoro", "index", {})
                },
                {
                    label: "Dettagli lista di lavoro",
                    href: ui.Navigation.instance().navigateUrl("summarylistelavoro", "index", {id: self.listaId})
                },
                {
                    label: "Confronto di liste"
                }
            ];
        }

    });



    exports.SearchListaLavoroGridView = SearchListaLavoroGridView;
    exports.ListeLavoroSummaryRemoteView = ListeLavoroSummaryRemoteView;
    exports.ListeLavoroComparisonRemoteView = ListeLavoroComparisonRemoteView;

    return exports;

});
