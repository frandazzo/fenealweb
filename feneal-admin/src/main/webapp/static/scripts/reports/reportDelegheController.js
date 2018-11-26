/**
 * Created by fgran on 14/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportDelegheView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




  
   

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var DelegheReportController = fcontrollers.Controller.extend({
        ctor: function(){
            DelegheReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "deleghe",
                data: {}
            });



            var view = new views.ReportDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.DelegheReportController = DelegheReportController;

    return exports;
});
