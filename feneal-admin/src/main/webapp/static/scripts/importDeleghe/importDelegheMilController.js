/**
 * Created by fgran on 18/04/2017.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "importDeleghe/importDelegheMilView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    var ImportaDelegheMilController = fcontrollers.Controller.extend({
        ctor: function(){
            ImportaDelegheMilController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "deleghemil",
                data: {}
            });



            var view = new views.ImportaDelegheMilAppView(service);


            return view;
        }

    });

    exports.ImportaDelegheMilController = ImportaDelegheMilController;

    return exports;
});
