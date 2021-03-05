
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "RSU/calcolaattribuzione/calcolaRsuView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

var calcolaRsuController = fcontrollers.Controller.extend({
    ctor: function(){
        calcolaRsuController.super.ctor.call(this);
    },

    index: function(params) {

        if(params.firmId)
            var firmId = params.firmId;

        var url = BASE + "reportelezionirsu";
        var service = new fmodel.AjaxService();
        service.set({
            url: url
        });
        return new views.ReportRsuHomeRemoteView(service,firmId);
    }
});



    exports.calcolaRsuController = calcolaRsuController;
    return exports;
});
