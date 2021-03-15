/**
 * Created by felciegramegna 12/03/2021
 */
define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};

    var DetailsAzienda = core.AObject.extend({

        ctor: function (firmId) {
            DetailsAzienda.super.ctor.call(this);
            this.firmId = firmId;
        },
        init: function () {
            var self = this;
            self.initSediRsuSearch();
            self.initVerbVotazioniSearch();
        },
        initSediRsuSearch: function () {
            var self = this;

            var service = new model.AjaxService();
            service.set("url",BASE + "remotesedirsusearchforapp/" + self.firmId);
            service.on("load",function(resp){

                if (resp.length == 0){
                    //visualizzo un messaggio che non è stata trovata una iscrizione
                    $('#containerSedi').append('' +
                        '<p class="color-red" style="text-align: center;padding-top: 12%;padding-bottom: 3%;">' +
                        '<i class="material-icons font-size-36">sentiment_dissatisfied</i>' +
                        '</p>' +
                        '<p class="text-center">Nessuna sede trovata</p>')
                }else{
                    self.initGridSediRsu(resp);
                }


            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();
        },
        initVerbVotazioniSearch: function () {

        },
        initGridSediRsu: function (responseData) {
            var grid = $('#containerSedi').dxDataGrid({
                dataSource:responseData,
                columns:[
                    {dataField:"description", visible : true, caption:"Descrizione", visibleIndex: 1},
                    { dataField:"province", visible : true, caption:"Provincia"},
                    { dataField:"city", visible : true, caption:"Citt&agrave;"},
                    { dataField:"address", visible : true, caption:"Indirizzo"}
                ],
                "export": {
                    enabled: false,
                    fileName: "dettaglio_lista",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: true,
                    type: "localStorage",
                    storageKey: "sediazienda"
                },
                paging:{
                    pageSize: 15
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
                        column: "description",
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
                hoverStateEnabled: true

            }).dxDataGrid("instance");

            return grid;
        }
    });

    exports.DetailsAzienda = DetailsAzienda;
    return exports;
});
