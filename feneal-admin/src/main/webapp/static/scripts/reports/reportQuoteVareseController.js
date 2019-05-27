define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportQuoteVareseView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var QuoteVareseReportController = fcontrollers.Controller.extend({
        ctor: function(){
            QuoteVareseReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "quotevarese",
                data: {}
            });



            var view = new views.ReportQuoteVareseAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.QuoteVareseReportController = QuoteVareseReportController;

    return exports;
});
