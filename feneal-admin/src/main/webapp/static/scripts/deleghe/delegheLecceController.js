define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/delegheLecceView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};






    var ReportDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            ReportDelegheController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "magazzinodeleghelecce",
                data: {}
            });



            var view = new views.ReportDelegheAppView(service);
            // view.set("title", "Ricerca lavoratori");

            return view;
        }

    });


    var MagazzinoDelegheController =  fcontrollers.Controller.extend({
        ctor: function(){
            MagazzinoDelegheController.super.ctor.call(this);

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
            var workerId = params.workerId;
            if (!workerId)
                throw "Id lavoratore non specificato";

            var identifier = this.defaultGridIdentifier || params.e;
            if(!identifier) throw "Please specify grid identifier";

            var url = BASE + "parentcrud/grid/" + identifier + "/" + workerId;
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
            return new views.MagazzinoCrudGridAppView(this.gridService, params.workerId);
        },

        createFormView: function(params) {
            var self = this;
            var view = null;
            var workerId = "";
            var documentId = "";

            if (params) {
                if (params.id)
                    documentId = params.id;
                if (params.workerId)
                    workerId = params.workerId;
            }

            if (!workerId)
                throw "Id lavoratore non specificato";


            if(params.dialog == 1) {
                view = new views.CrudFormDialogView(this.formService);
            } else {
                view = new views.MagazzinoCrudFormAppView(this.formService, documentId, workerId);

                this.pushDispose(function() {
                    view.dispose();
                });
            }




            view.on({
                cancel: function() {
                    ui.Navigation.instance().navigate("magazzinocrud", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        cancel: 1,
                        workerId : workerId

                    });
                },

                close: function() {
                    ui.Navigation.instance().navigate("magazzinocrud", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        reload: 1,
                        workerId : workerId
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

            var workerId = params.workerId;
            if (!workerId)
                throw "Id lavoratore non specificato";

            var formService = new fmodel.FormService();
            var url = BASE + "parentcrud/form/" + identifier + "/" + workerId;
            formService.set({
                identifier: identifier,
                gridIdentifier: params.g || identifier,
                url: url,
                data: { id: params.id }
            });

            this.formService = formService;

            var view = this.createFormView(params);


            return view;
        },

        create: function(params) {
            return this.edit(params);
        }



    });


    exports.ReportDelegheController = ReportDelegheController;
    exports.MagazzinoDelegheController=MagazzinoDelegheController;
    return exports;
});
