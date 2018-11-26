/**
 * Created by fgran on 14/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/inoltraDelegheView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




  
   

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var InoltraDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            InoltraDelegheController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "inoltradeleghe",
                data: {}
            });



            var view = new views.InoltraDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.InoltraDelegheController = InoltraDelegheController;

    return exports;
});
