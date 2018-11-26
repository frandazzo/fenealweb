/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportDocumentiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var DocumentiReportController = fcontrollers.Controller.extend({
        ctor: function(){
            DocumentiReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "documenti",
                data: {}
            });



            var view = new views.ReportDocumentiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.DocumentiReportController = DocumentiReportController;

    return exports;
});
