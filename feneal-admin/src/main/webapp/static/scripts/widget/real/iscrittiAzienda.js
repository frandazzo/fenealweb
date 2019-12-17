/**
 * Created by fgran on 13/06/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var Iscritti = core.AObject.extend({

        ctor: function (firmId) {
            Iscritti.super.ctor.call(this);
            this.firmId = firmId;
        },

        init: function () {
            var self = this;
            self.initIscrittiSearch();
            self.initNonIscrittiSearch();
            self.initDelegheEdiliSearch();
        },
        initDelegheEdiliSearch : function(){
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "firm/" + self.firmId + "/delegheedili");
            service.on("load",function(resp){

                if (resp.length == 0){
                    //nascondo tutto
                    $('.panelDelegheEdili').hide();
                }else{
                    self.initGridDelegheEdili(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
        },
        initGridDelegheEdili : function(responseData){
            var grid = $('#containerDelegheEdili').dxDataGrid({
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
                    { dataField:"phone", visible : true, caption:"Telefono"},

                ],
                "export": {
                    enabled: true,
                    fileName: "dettaglio_lista",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "iscrittiazienda"
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
        initNonIscrittiSearch: function(){
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "firm/" + self.firmId + "/noniscrizioni");
            service.on("load",function(resp){

                if (resp.length == 0){
                    //visualizzo un messaggio che non è stata trovata una iscrizione
                    $('#containerNonIscritti').append('' +
                        '<p class="color-green" style="text-align: center;padding-top: 12%;padding-bottom: 3%;">' +
                        '<i class="material-icons font-size-100">sentiment_satisfied</i>' +
                        '</p>' +
                        '<p class="text-center">Nessun lavoratore non iscritto trovato</p>')
                }else{
                    self.initGridNonIscritti(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
        },
        initIscrittiSearch: function(){
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "firm/" + self.firmId + "/iscrizioni");
            service.on("load",function(resp){

                if (resp.length == 0){
                    //visualizzo un messaggio che non è stata trovata una iscrizione
                    $('#containerIscritti').append('' +
                        '<p class="color-red" style="text-align: center;padding-top: 12%;padding-bottom: 3%;">' +
                        '<i class="material-icons font-size-100">sentiment_dissatisfied</i>' +
                        '</p>' +
                        '<p class="text-center">Nessun iscritto trovato</p>')
                }else{
                    self.initGrid(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
        },
        initGrid : function(responseData){



            var grid = $('#containerIscritti').dxDataGrid({
                dataSource:responseData,
                columns:[
                    {dataField:"notes", visible : true, caption:"Tipo documento", visibleIndex: 1},
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
                    { dataField:"phone", visible : true, caption:"Telefono"},

                ],
                "export": {
                    enabled: true,
                    fileName: "dettaglio_lista",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: true,
                    type: "localStorage",
                    storageKey: "iscrittiazienda"
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
        initGridNonIscritti : function(responseData){



            var grid = $('#containerNonIscritti').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"liberoData", visible : true, dataType:'date', visibleIndex: 5},
                    { dataField:"liberoProvincia",  visible : false, visibleIndex: 1},
                    { dataField:"liberoEnteBilaterale", visible : true, visibleIndex: 2},
                    { dataField:"liberoIscrittoA", visible : true , visibleIndex: 4},

                    { dataField:"lavoratoreNomeCompleto", visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var completeName = options.data.lavoratoreNomeCompleto;
                            var fiscalCode = options.data.lavoratoreCodiceFiscale;
                            var province = options.data.liberoProvincia;

                            $("<a />")
                                .text(completeName)
                                .attr("href", "javascript:;")
                                .on('click', function(){

                                    ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                        fiscalCode:fiscalCode,
                                        province: province
                                    })

                                })
                                .appendTo(container);
                        }



                    },
                    { dataField:"lavoratoreCodiceFiscale",caption:"Codice fiscale", visible : false,visibleIndex: 8},
                    { dataField:"lavoratoreDelegheOwner", caption:"Possiede delega", visible : true,visibleIndex: 6,
                        cellTemplate: function (container, options) {
                        //container.addClass("img-container");
                        var lavoratoreDelegheOwner = options.data.lavoratoreDelegheOwner;
                        // <span class="color-black">
                        //     <i class="material-icons">sentiment_satisfied</i>
                        //     </span>
                        if (lavoratoreDelegheOwner){
                            var span =$("<span style='color:green' />");
                            span.append($('<i class="material-icons" style=" text-align: center;display: block;">done</i>'));
                            span.appendTo(container);
                        }

                    }},
                    { dataField:"lavoratoreDataNascita",caption:"Data nascita", dataType:'date', visible : false,visibleIndex: 9},
                    { dataField:"lavoratoreLuogoNascita", caption:"Com. nascita",visible : false,visibleIndex: 11},
                    { dataField:"lavoratoreCittaResidenza",  caption:"Com. residenza",visible : false,visibleIndex: 12},
                    { dataField:"lavoratoreCellulare", caption:"Cellulare",visible : false, visibleIndex: 15},
                    { dataField:"lavoratoreTelefono", caption:"Telefono",visible : false,visibleIndex: 16},
                    { dataField:"lavoratoreIndirizzo",  caption:"Indirizzo",visible : false,visibleIndex: 13},
                    { dataField:"lavoratoreCap", caption:"Cap",visible : false,visibleIndex: 14},
                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    {dataField:"numIscrizioni",  visible: true,visibleIndex: 7, caption:"Iscritto storico",
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
                    { dataField:"aziendaRagioneSociale", visible : false,

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;

                            if (!name)
                                return;
                            $("<a />")
                                .text(name)
                                .attr("href", "javascript:;")
                                .on('click', function(){
                                    ui.Navigation.instance().navigate("summaryfirm", "remoteIndex", {
                                        description:name
                                    });
                                })
                                .appendTo(container);
                        }
                    },
                    { dataField:"lavoratoreNazionalita",caption:"Nazione nascita", visible : false,visibleIndex: 10},
                    { dataField:"lavoratoreProvinciaNascita", visible : false},
                    { dataField:"lavoratoreProvinciaResidenza", visible : false},
                    { dataField:"lavoratoreCellulare", visible : false}

                ],
                searchPanel: {
                    visible: true
                
                },
                summary: {
                    totalItems: [{
                        column: "lavoratoreNomeCompleto",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Non iscritti trovati: " + data.value;
                        }
                    }]
                },
                columnChooser: {
                    enabled: true
                },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: true,
                    fileName: "liberiazienda",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: true,
                    type: "localStorage",
                    storageKey: "reportnoniscirttiazienda"
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
                                    { dataField:"contratto",  visible : true, visibleIndex: 10},
                                    // {dataField: "numComunicazioni", visible: true, visibleIndex:11, caption: "Richieste ai territori",
                                    //
                                    //     cellTemplate: function (container, options) {
                                    //         //container.addClass("img-container");
                                    //         var numComunicazioni = options.data.numComunicazioni;
                                    //         // <span class="color-black">
                                    //         //     <i class="material-icons">sentiment_satisfied</i>
                                    //         //     </span>
                                    //         if (numComunicazioni){
                                    //             var span =$("<span style='color:orange' />");
                                    //             span.append(numComunicazioni);
                                    //             span.appendTo(container);
                                    //         }
                                    //
                                    //     }
                                    //
                                    // }


                                ],
                                dataSource: currentData.iscrizioni
                            }).appendTo(container);
                    }
                }

            }).dxDataGrid("instance");

            return grid;

        },

    });

    exports.Iscritti = Iscritti;

    return exports;


});
