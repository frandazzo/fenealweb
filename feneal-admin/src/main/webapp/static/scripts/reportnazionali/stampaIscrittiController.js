/**
 * Created by angelo on 29/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "reportnazionali/stampaIscrittiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};





    var StampaIscrittiController = fcontrollers.Controller.extend({
        ctor: function(){
            StampaIscrittiController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "stampaiscritti",
                data: {}
            });



            var view = new views.StampaIscrittiAppView(service);


            return view;
        }

    });



    exports.StampaIscrittiController = StampaIscrittiController;

    return exports;
});
