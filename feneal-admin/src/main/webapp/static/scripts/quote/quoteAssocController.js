define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "quote/quoteAssocView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var QuoteAssocController = fcontrollers.Controller.extend({
        ctor: function(){
            QuoteAssocController.super.ctor.call(this);
        },
        index: function(params) {

            var url = BASE + "quoteassociative";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.QuoteAssocRemoteView(service);

        }

    });


    //controller per la visualizzazione del dettaglio quota
    var QuotaDettaglioController = fcontrollers.Controller.extend({
        ctor: function(){
            QuotaDettaglioController.super.ctor.call(this);
        },
        index: function(params) {

            var quotaId = params.id;
            var url = BASE + "quoteassociative/dettaglio/"+ quotaId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.QuotaDettaglioRemoteView(service, quotaId);

        }
    });


    exports.QuoteAssocController = QuoteAssocController;
    exports.QuotaDettaglioController = QuotaDettaglioController;

    return exports;

});