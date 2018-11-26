/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reportnazionali/reportInviiDBNazionaleView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var ReportInviiDBNazionaleController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportInviiDBNazionaleController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "inviidbnazionali",
                data: {}
            });



            var view = new views.ReportInviiDBNazionaleAppView(service);


            return view;
        }

    });



    exports.ReportInviiDBNazionaleController = ReportInviiDBNazionaleController;

    return exports;
});
