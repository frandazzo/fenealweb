/**
 * Created by fgran on 15/04/2016.
 */
/**
 * Created by fgran on 14/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportIncassiQuoteView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var IncassiQuoteReportController = fcontrollers.Controller.extend({
        ctor: function(){
            IncassiQuoteReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "iqa",
                data: {}
            });



            var view = new views.ReportIncassiQuoteAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.IncassiQuoteReportController = IncassiQuoteReportController;

    return exports;
});
