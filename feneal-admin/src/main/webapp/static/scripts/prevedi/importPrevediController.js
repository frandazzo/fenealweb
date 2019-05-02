define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "prevedi/importPrevediView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    var ReportController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "prevediview",
                data: {}
            });



            var view = new views.ReportAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });


    var ImportaController = fcontrollers.Controller.extend({
        ctor: function(){
            ImportaController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "import/prevedi",
                data: {}
            });



            var view = new views.ImportaAppView(service);


            return view;
        }

    });




    exports.ImportaController = ImportaController;
    exports.ReportController = ReportController;

    return exports;
});
