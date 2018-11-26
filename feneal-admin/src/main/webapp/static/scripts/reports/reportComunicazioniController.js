/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportComunicazioniView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var ComunicazioniReportController = fcontrollers.Controller.extend({
        ctor: function(){
            ComunicazioniReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "comunicazioni",
                data: {}
            });



            var view = new views.ReportComunicazioniAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.ComunicazioniReportController = ComunicazioniReportController;

    return exports;
});
