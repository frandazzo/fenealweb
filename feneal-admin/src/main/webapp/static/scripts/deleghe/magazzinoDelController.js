/**
 * Created by fgran on 14/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/magazzinoDelView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




  
   

    //controller che porta alla mascherina custom per la ricerca dei lavoratori
    var MagazzinoDelController = fcontrollers.Controller.extend({
        ctor: function(){
            MagazzinoDelController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "magazzinodeleghe",
                data: {}
            });



            var view = new views.MagazzinoDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.MagazzinoDelController = MagazzinoDelController;

    return exports;
});
