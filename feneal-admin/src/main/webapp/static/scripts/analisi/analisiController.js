/**
 * Created by fgran on 13/12/2017.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "analisi/analisiView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




    //questo controller gestisce la visualizzazione di un riepilogo dati del lavoratore
    var RiepilogoController = fcontrollers.Controller.extend({
        ctor: function(){
            RiepilogoController.super.ctor.call(this);
        },
        index: function(params) {


            var url = BASE + "analisi/riepilogo";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.RiepilogoRemoteView(service);

        }
    });


    var PivotController = fcontrollers.Controller.extend({
        ctor: function(){
            PivotController.super.ctor.call(this);
        },
        index: function(params) {


            var url = BASE + "analisi/pivot";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.PivotRemoteView(service);

        }
    });
   

  
    exports.RiepilogoController = RiepilogoController;
    exports.PivotController = PivotController;
   
    return exports;
});
