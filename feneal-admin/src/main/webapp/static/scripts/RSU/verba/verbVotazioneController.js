/**
 * Created by angelo on 08/05/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "RSU/verba/verbVotazioneView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};


    var verbVotazioneController = fcontrollers.Controller.extend({
        ctor: function(){
            verbVotazioneController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = null;
            this.defaultGridIdentifier = null;
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
            var firmId = params.firmId;
            if (!firmId)
                throw "Id azienda non specificato";

            var identifier = this.defaultGridIdentifier || params.e;
            if(!identifier) throw "Please specify grid identifier";

            var url = BASE + "parentaziendarsucrud/grid/" + identifier + "/" + firmId;
            var gridService = new fmodel.GridService();
            gridService.set({
                method: "POST",
                url: url,
                identifier: identifier
            });

            if (params) {
                var loadRequest = fmodel.QueryStringLoadRequest.parse(params);
                gridService.set("loadRequest", loadRequest);
            }

            this.gridService = gridService;

            var view = this.createGridView(params);
            view.set("fullScreenForm", params.fs);

            return view;
        },

        createGridView: function(params) {
            return new views.verbVotazioneCrudGridAppView(this.gridService, params.firmId);
        },

        createFormView: function(params) {
            var self = this;
            var view = null;
            var firmId = "";
            var verbId = "";

            if (params) {
                if (params.id)
                    verbId = params.id;
                if (params.firmId)
                    firmId = params.firmId;
            }

            if (!firmId)
                throw "Id azienda non specificato";


            if(params.dialog == 1) {
                view = new views.CrudFormDialogView(this.formService);
            } else {
                view = new views.VerbVotCrudFormAppView(this.formService, verbId, firmId);

                this.pushDispose(function() {
                    view.dispose();
                });
            }




            view.on({
                cancel: function() {
                    ui.Navigation.instance().navigate("verbvote", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        cancel: 1,
                        firmId : firmId

                    });
                },

                close: function() {
                    ui.Navigation.instance().navigate("verbvote", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        reload: 1,
                        firmId : firmId
                    });
                }
            });

            return view;
        },

        list: function(params) {
            return this.index(params);
        },

        edit: function(params) {
            var identifier = this.defaultFormIdentifier || params.e;
            if(!identifier) throw "Please specify form identifier";

            var firmId = params.firmId;
            if (!firmId)
                throw "Id azienda non specificato";

            var formService = new fmodel.FormService();
            var url = BASE + "parentaziendarsucrud/form/" + identifier + "/" + firmId;
            formService.set({
                identifier: identifier,
                gridIdentifier: params.g || identifier,
                url: url,
                data: { id: params.id }
            });

            this.formService = formService;

            var view = this.createFormView(params);


            return view;
        }


    });


    exports.verbVotazioneController = verbVotazioneController;
    return exports;
});
