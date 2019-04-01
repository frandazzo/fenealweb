/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reports/reportLiberiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};







    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var LiberiReportController = fcontrollers.Controller.extend({
        ctor: function(){
            LiberiReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "liberi",
                data: {}
            });



            var view = new views.ReportLiberiAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });


    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var NewLiberiReportController = fcontrollers.Controller.extend({
        ctor: function(){
            NewLiberiReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "liberinew",
                data: {}
            });



            var view = new views.ReportLiberiNewAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });

    exports.NewLiberiReportController = NewLiberiReportController;
    exports.LiberiReportController = LiberiReportController;

    return exports;
});
