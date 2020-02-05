define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "ristorniBari/storicoRistorniBariView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var StoricoRistorniBariController = fcontrollers.Controller.extend({
        ctor: function(){
            StoricoRistorniBariController.super.ctor.call(this);
        },
        index: function(params) {

            var url = BASE + "storicoristorni";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.StoricoRistorniBariRemoteView(service);

        }

    });


    //controller per la visualizzazione del dettaglio ristorno
    var RistornoBariDettaglioController = fcontrollers.Controller.extend({
        ctor: function(){
            RistornoBariDettaglioController.super.ctor.call(this);
        },
        index: function(params) {

            var ristornoId = params.id;
            var url = BASE + "storicoristorni/dettaglio/"+ ristornoId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.RistornoDettaglioRemoteView(service, ristornoId);

        }
    });


    exports.StoricoRistorniBariController = StoricoRistorniBariController;
    exports.RistornoBariDettaglioController = RistornoBariDettaglioController;

    return exports;

});
