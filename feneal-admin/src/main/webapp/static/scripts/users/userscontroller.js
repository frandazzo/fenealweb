
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
     "users/userviews"], function(core, fmodel, fviews, fcontrollers, userviews) {

    var exports = {};


    var UsersController = fcontrollers.Controller.extend({
    
        ctor: function() {
            UsersController.super.ctor.call(this);
    
            this.gridService = null;
            this.defaultFormIdentifier = "user";
            this.defaultGridIdentifier = "user";
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
    
            var url = BASE + "crud/grid/" + identifier;
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
            return new userviews.UsersCrudGridAppView(this.gridService);
        },
    
        createFormView: function(params) {
            var self = this;
            var view = null;
            if(params.dialog == 1) {
                view = new fviews.CrudFormDialogView(this.formService);
            } else {
    
                if (params.webUserId)
                    view = new userviews.UsersCrudFormAppView(this.formService, params.webUserId);
                else
                    view = new userviews.UsersCrudFormAppView(this.formService);
    
    
                this.pushDispose(function() {
                    view.dispose();
                });
            }
    
            view.on({
                cancel: function() {
                    ui.Navigation.instance().navigate("users", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        cancel: 1
                    });
                },
    
                close: function() {
                    ui.Navigation.instance().navigate("users", "list", {
                        e: self.formService.get("gridIdentifier"),
                        fs: 0,
                        reload: 1
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
    
            var formService = new fmodel.FormService();
            var url = BASE + "crud/form/" + identifier;
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


    var DummyUsersController = fcontrollers.Controller.extend({

        ctor: function() {
            DummyUsersController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = "dummyuser";
            this.defaultGridIdentifier = "dummyuser";
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

            var url = BASE + "crud/grid/" + identifier;
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
            return new userviews.DummyUsersCrudGridAppView(this.gridService);
        },


        list: function(params) {
            return this.index(params);
        },




    });

    exports.UsersController = UsersController;
    exports.DummyUsersController = DummyUsersController;
    return exports;
});