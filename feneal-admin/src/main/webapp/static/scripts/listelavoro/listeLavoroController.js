/**
 * Created by fgran on 07/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "listelavoro/listeLavoroViews"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};



    //controller che porta alla mascherina custom per la ricerca delle liste di lavoro
    var ListeLavoroSearchController = fcontrollers.Controller.extend({
        ctor: function(){
            ListeLavoroSearchController.super.ctor.call(this);
            this.gridService = null;
            this.defaultFormIdentifier = "listalavoro";
            this.defaultGridIdentifier = "listalavoro";
            this.formService = null;


        },

        index: function(params) {
            if(this.gridService) {
                if(params.reload == 1) {
                    this.gridService.reload();
                    return;
                }

                if(params.cancel == 1) {
                    return;
                }
            }

            var identifier = this.defaultGridIdentifier || params.e;
            if(!identifier) throw "Please specify grid identifier";

            var url = BASE + "crud/grid/" + identifier;
            var gridService = new fmodel.GridService();
            gridService.set({
                method: "POST",
                url: url,
                identifier: identifier
            });

            if (params) {
                var loadRequest = fmodel.QueryStringLoadRequest.parse(params);
                gridService.set("loadRequest", loadRequest);
            }

            this.gridService = gridService;

            var view = this.createGridView(params);
            view.set("fullScreenForm", params.fs);

            return view;
        },
        createGridView: function(params) {
            return new views.SearchListaLavoroGridView(this.gridService);
        }

    });


    //controller per la visualizzazione di riepilogo di una lista lavoro
    var ListeLavoroSummaryController = fcontrollers.Controller.extend({
        ctor: function(){
            ListeLavoroSummaryController.super.ctor.call(this);
        },
        index: function(params) {

            var listaId = params.id;
            var url = BASE + "listalavoro/summary/"+ listaId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });

            return new views.ListeLavoroSummaryRemoteView(service, listaId);

        }

    });

    //controller per la visualizzazione del confronto con lista
    var ListeLavoroComparisonController = fcontrollers.Controller.extend({
        ctor: function(){
            ListeLavoroComparisonController.super.ctor.call(this);
        },
        index: function(params) {

            var listaId = params.listaId;
            var otherListaId = params.otherListaId;
            var description = params.description;
            var data = {
                listaId: listaId,
                otherListaId: otherListaId,
                description: description
            };
            var url = BASE + "listalavoro/compare";
            var service = new fmodel.AjaxService();
            service.set({
                url: url,
                data: data,
                method: 'POST'
            });


            return new views.ListeLavoroComparisonRemoteView(service, listaId);

        }

    });


    exports.ListeLavoroSearchController = ListeLavoroSearchController;
    exports.ListeLavoroSummaryController = ListeLavoroSummaryController;
    exports.ListeLavoroComparisonController = ListeLavoroComparisonController;

    return exports;
});
