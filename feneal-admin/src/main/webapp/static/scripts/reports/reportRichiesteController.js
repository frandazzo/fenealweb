/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportRichiesteView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var RichiesteReportController = fcontrollers.Controller.extend({
        ctor: function(){
            RichiesteReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "richieste",
                data: {}
            });



            var view = new views.ReportRichiesteAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.RichiesteReportController = RichiesteReportController;

    return exports;
});
