/**
 * Created by felicegramegna on 22/02/2021.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "RSU/azienda/aziendeRSUView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller per la ricerca delle aziende che riporta al meccanismo nativo del framework
    var SearchFirmRsuController = fcontrollers.Controller.extend({
        ctor: function(){
            SearchFirmRsuController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = "firmrsu";
            this.defaultGridIdentifier = "firmrsu";
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
            return new views.SearchFirmRsuViewGridView(this.gridService);
        }




    });

    //controller per la modifca e creazione dell'azienda
    var AziendaRsuEditController = fcontrollers.Controller.extend({
        ctor: function(){
            AziendaRsuEditController.super.ctor.call(this);
        },
        index: function(params) {

            //questo è l'id del lavoratore che nel caso sia valorizzato indica un aggiornamento
            var firmId = "";

            if (params)
                if (params.id)
                    firmId = params.id;

            var url = BASE + "firmrsu";

            if (firmId)
                url = url + "/" + firmId;

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: url,
                data: {}
            });



            var view = new views.EditAziendaRsuAppView(service, firmId);
            // view.set("title", "Crea o modifica lavoratore");

            return view;
        }
    });

    //controller per la visualizzazione di riepilogo dell'azienda
    var AziendaRsuSummaryController = fcontrollers.Controller.extend({
        ctor: function(){
            AziendaRsuSummaryController.super.ctor.call(this);
        },
        index: function(params) {

            var firmId = params.id;
            var url = BASE + "firmrsu/summary/"+ firmId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.FirmRsuSummaryRemoteView(service, firmId);

        },
        remoteIndex: function(params) {

            var description = params.description;
            //decodifico eventuali descrizioni che hanno il carattere "*_"
            description = description.replace("*_", "&");
            description = description.replace("~_", "'");

            var url = encodeURI(BASE + "firmrsu/remote");

            var service = new fmodel.AjaxService();
            service.set({
                url: url,
                data:{description:description}
            });
            return new views.FirmRsuSummaryRemoteView(service,null);

        }

    });

    exports.AziendaRsuSummaryController = AziendaRsuSummaryController;
    exports.AziendaRsuEditController = AziendaRsuEditController;
    exports.SearchFirmRsuController = SearchFirmRsuController;
    return exports;
});
