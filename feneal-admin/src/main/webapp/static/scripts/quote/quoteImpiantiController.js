/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "quote/quoteImpiantiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    var QuoteImpiantiController = fcontrollers.Controller.extend({
        ctor: function(){
            QuoteImpiantiController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "quoteimpiantifissi",
                data: {}
            });


            var view = new views.QuoteImpiantiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });

    var RenewQuoteImpiantiController = fcontrollers.Controller.extend({
        ctor: function(){
            RenewQuoteImpiantiController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "renewquoteimpiantifissi",
                data: {}
            });


            var view = new views.RenewQuoteImpiantiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });

    var QuoteManualiController = fcontrollers.Controller.extend({
        ctor: function(){
            QuoteManualiController.super.ctor.call(this);
        },
        index: function(params){

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "quotemanuali",
                data: {}
            });


            var view = new views.QuoteManualiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }
    });

    var QuoteBreviManoController = fcontrollers.Controller.extend({
        ctor: function(){
            QuoteBreviManoController.super.ctor.call(this);
        },
        index: function(params){
            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "quotebrevimano",
                data: {}
            });


            var view = new views.QuoteBreviManoAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;

        }
    });


    exports.QuoteImpiantiController = QuoteImpiantiController;
    exports.RenewQuoteImpiantiController = RenewQuoteImpiantiController;
    exports.QuoteBreviManoController = QuoteBreviManoController;
    exports.QuoteManualiController = QuoteManualiController;

    return exports;
});
