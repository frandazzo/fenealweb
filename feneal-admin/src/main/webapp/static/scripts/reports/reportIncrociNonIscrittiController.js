/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportIncrociNonIscrittiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};



    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var IncrociNonIscrittiReportController = fcontrollers.Controller.extend({
        ctor: function(){
            IncrociNonIscrittiReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "incrocio/noniscritti",
                data: {}
            });


            var view = new views.ReportIncrociNonIscrittiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.IncrociNonIscrittiReportController = IncrociNonIscrittiReportController;

    return exports;
});
