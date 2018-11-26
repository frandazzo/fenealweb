define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "inps/inpsLecceViews"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};



    var RistorniQuoteController = fcontrollers.Controller.extend({
        ctor: function(){
            RistorniQuoteController.super.ctor.call(this);
        },
        index: function(params) {

            var url = BASE + "ristorniquoteinps";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.RistorniQuoteRemoteView(service);

        }

    });

    var RistornoQuoteController = fcontrollers.Controller.extend({
        ctor: function(id){
            RistornoQuoteController.super.ctor.call(this);
        },
        index: function(params) {

            var url = BASE + "ristorniquoteinps/" + params.id;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.RistornoQuoteRemoteView(service, params.id);

        }

    });


    var IncrociaQuoteController = fcontrollers.Controller.extend({
        ctor: function(){
            IncrociaQuoteController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "incrociaquoteinps",
                data: {}
            });
            var view = new views.IncrociaQuoteInpsAppView(service);
            return view;
        }

    });
    var ReportQuoteInpsController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportQuoteInpsController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "reportquoteinps",
                data: {}
            });



            var view = new views.ReportQuoteInpsAppView(service);


            return view;
        }

    });

    exports.RistornoQuoteController = RistornoQuoteController;
    exports.IncrociaQuoteController = IncrociaQuoteController;
    exports.ReportQuoteInpsController = ReportQuoteInpsController;
    exports.RistorniQuoteController = RistorniQuoteController;

    return exports;
});
