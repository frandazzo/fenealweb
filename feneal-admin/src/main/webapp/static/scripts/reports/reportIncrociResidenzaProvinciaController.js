/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportIncrociResidenzaProvinciaView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};



    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var IncrociResidenzaProvinciaReportController = fcontrollers.Controller.extend({
        ctor: function(){
            IncrociResidenzaProvinciaReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "incrocio",
                data: {}
            });


            var view = new views.ReportIncrociResidenzaProvinciaAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.IncrociResidenzaProvinciaReportController = IncrociResidenzaProvinciaReportController;

    return exports;
});
