define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "importazioneDB/importazioneDBView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var ImportazioneDBController = fcontrollers.Controller.extend({
        ctor: function () {
            ImportazioneDBController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = "importazioneDB";
            this.defaultGridIdentifier = "importazioneDB";
            this.formService = null;
        },

        index: function(params) {
            if(this.gridService) {
                if(params.reload == 1) {
                    this.gridService.reload();
                    return;
                }

                if(params.cancel == 1) {
                    return;
                }
            }

            var identifier = this.defaultGridIdentifier || params.e;
            if(!identifier) throw "Please specify grid identifier";

            var url = BASE + "importazioneDB";
            var gridService = new fmodel.GridService();
            gridService.set({
                method: "GET",
                url: url,
                identifier: identifier
            });

            this.gridService = gridService;

            var view = new views.ImportazioneDBGridAppView(gridService);
            view.set("fullScreenForm", params.fs);

            return view;
        }

    });


    exports.ImportazioneDBController = ImportazioneDBController;

    return exports;

});