define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/delegheBariViews"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var DelegheHomeController = fcontrollers.Controller.extend({
        ctor: function(){
            DelegheHomeController.super.ctor.call(this);
        },
        index: function(params) {

            var workerId = params.workerId;
            var url = BASE + "deleghebari/home/"+ workerId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.DelegheBariHomeRemoteView(service, workerId);

        }

    });



    var ProiettaDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            ProiettaDelegheController.super.ctor.call(this);
        },
        index: function(params) {



            var service = new fmodel.AjaxService();
            service.set({
                url: BASE + "proiettadeleghebari",
            });
            return new views.ProiettaDelegheAppView(service);

        }

    });


    var ReportDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportDelegheController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "reportdeleghebari",
                data: {}
            });



            var view = new views.ReportDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });

    var RistorniDelegheCasseEdileController = fcontrollers.Controller.extend({
        ctor: function(){
            RistorniDelegheCasseEdileController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "ristornideleghebaricassaedile",
                data: {}
            });



            var view = new views.RistorniDelegheBariCassaEdileAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });



    exports.RistorniDelegheCasseEdileController = RistorniDelegheCasseEdileController;
    exports.DelegheHomeController = DelegheHomeController;
    exports.ReportDelegheController = ReportDelegheController;
    exports.ProiettaDelegheController= ProiettaDelegheController;
    return exports;
});
