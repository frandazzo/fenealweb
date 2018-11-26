/**
 * Created by fgran on 14/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/accettDelegheView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




  
   

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var AccettDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            AccettDelegheController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "accettdeleghe",
                data: {}
            });



            var view = new views.AccettDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.AccettDelegheController = AccettDelegheController;

    return exports;
});
