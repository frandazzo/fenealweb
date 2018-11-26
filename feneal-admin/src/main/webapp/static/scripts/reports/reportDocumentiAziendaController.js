/**
 * Created by angelo on 08/05/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportDocumentiAziendaView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var DocumentiAziendaReportController = fcontrollers.Controller.extend({
        ctor: function(){
            DocumentiAziendaReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "documentiazienda",
                data: {}
            });



            var view = new views.ReportDocumentiAziendaAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.DocumentiAziendaReportController = DocumentiAziendaReportController;

    return exports;
});
