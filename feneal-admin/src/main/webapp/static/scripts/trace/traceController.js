/**
 * Created by angelo on 15/11/2017.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "trace/traceView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var TraceLoginController = fcontrollers.Controller.extend({
        ctor: function(){
            TraceLoginController.super.ctor.call(this);
        },
        index: function(params) {

            var url = BASE + "trace/logins";
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.TraceLoginRemoteView(service);
        }
    });

    var TraceActivityController = fcontrollers.Controller.extend({
        ctor: function(){
            TraceActivityController.super.ctor.call(this);
        },
        index: function(params) {
            var userId = params.userId;

            if (!userId)
                throw "Id utente non specificato";

            var url = BASE + "trace/activities";
            var service = new fmodel.AjaxService();
            service.set({
                url: url,
                data: { userId: userId }
            });
            return new views.TraceActivityRemoteView(service);
        }
    });

    // ESTRAI I NOMI DAL FILE

    // var estraiFileController = fcontrollers.Controller.extend({
    //     ctor: function(){
    //         estraiFileController.super.ctor.call(this);
    //     },
    //     index: function(params) {
    //
    //         var url = BASE + "estraifile";
    //         var service = new fmodel.AjaxService();
    //         service.set({
    //             url: url
    //         });
    //         return new views.estraifileRemoteView(service);
    //     }
    // });



//
//     exports.estraiFileController = estraiFileController;

    exports.TraceLoginController = TraceLoginController;
    exports.TraceActivityController = TraceActivityController;

    return exports;
});
