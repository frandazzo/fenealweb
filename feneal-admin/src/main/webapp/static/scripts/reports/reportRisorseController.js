/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportRisorseView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var ReportRisorseController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportRisorseController.super.ctor.call(this);
        },
        index: function(params) {

            var workerId = params.id;
            var url = BASE + "risorse";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });

            return  new views.ReportRisorseRemoteView(service);
        }

    });

    exports.ReportRisorseController = ReportRisorseController;

    return exports;
});
