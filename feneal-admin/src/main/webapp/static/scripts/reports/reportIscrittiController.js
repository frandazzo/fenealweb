/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportIscrittiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};



    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var IscrittiReportController = fcontrollers.Controller.extend({
        ctor: function(){
            IscrittiReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "iscritti",
                data: {}
            });


            var view = new views.ReportIscrittiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.IscrittiReportController = IscrittiReportController;

    return exports;
});
