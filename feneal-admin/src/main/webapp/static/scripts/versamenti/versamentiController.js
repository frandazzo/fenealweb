/**
 * Created by fgran on 28/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "versamenti/versamentiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




    //questo controller gestisce la visualizzazione di un riepilogo dati del lavoratore
    var VersamentiController = fcontrollers.Controller.extend({
        ctor: function(){
            VersamentiController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = null;
            this.defaultGridIdentifier = null;
            this.formService = null;
        },

        index: function(params) {

            var workerId = params.workerId;
            if (!workerId)
                throw "Id lavoratore non specificato";

            var service = new fmodel.AjaxService();
            var url = BASE + "versamenti/"+ workerId;

            service.set({
                url: url
            });
            return new views.VersamentiDettaglioRemoteView(service, workerId);
        },

        list: function(params) {
            return this.index(params);
        }

    });

    //controller per la visualizzazione del dettaglio quota
    var VersamentiDettaglioController = fcontrollers.Controller.extend({
        ctor: function(){
            VersamentiDettaglioController.super.ctor.call(this);

        },
        index: function(params) {

            var workerId = params.workerId;
            if (!workerId)
                throw "Id lavoratore non specificato";

            var quotaId = params.id;
            var url = BASE + "quoteassociative/dettaglio/"+ quotaId;
            var service = new fmodel.AjaxService();

            service.set({
                url: url,
                data: {idWorker: workerId},
                workerId: workerId
            });
            return new views.VersamentiDettaglioRemoteView(service, workerId, quotaId);

        }
    });


    exports.VersamentiController = VersamentiController;
    exports.VersamentiDettaglioController = VersamentiDettaglioController;

    return exports;
});