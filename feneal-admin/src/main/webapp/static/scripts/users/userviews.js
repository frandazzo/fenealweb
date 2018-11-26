/**
 * Created by fgran on 01/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts"], function(core, fmodel, fviews, ui, widgets, plugins, webparts) {

    var exports = {};


    var UsersCrudGridAppView = fviews.CrudGridAppView.extend({

        ctor: function(gridService) {
            UsersCrudGridAppView.super.ctor.call(this, gridService);

            var self = this;

            this.lastTimeSelected = false;
            this.selectionTimer = null;

            gridService.on("load", function() {
                if (self.get("fullScreenForm")) {
                    self.lastTimeSelected = false;
                }
            });

            this.grid = this.get("grid");
            this.gridView = this.get("gridView");
            this.gridView.on({
                select: function() {
                    if(self.selectionTimer) {
                        window.clearTimeout(self.selectionTimer);
                    }

                    self.selectionTimer = window.setTimeout(function() {
                        var somethingSelected = self.grid.getSelection().length > 0;
                        if(somethingSelected !== self.lastTimeSelected) {
                            if(somethingSelected) {
                                //$('#toolbar').toolbar('showGroups', 'selected');
                            } else {
                                //$('#toolbar').toolbar('hideGroups', 'selected');
                            }
                        }
                        self.lastTimeSelected = somethingSelected;
                    }, 100);
                }
            });

            this.fullScreenForm = 0;

            this.on("complete", function() {
                self.set("title", self.gridService.get("title"));
                // $('div[data-component="search_form_container"]').show();

                // Gestione cambio stato utente
                $("a.user-status-link").click(function() {
                    var userId = $(this).parents("tr").data("entity-id");
                    var service = new fmodel.AjaxService();
                    service.set("url", BASE + "user/" + userId + "/changestatus");

                    service.on("load",function(resp) {
                        $.loader.hide({parent:'body'});
                        $.notify.success("Lo stato dell'utente è stato modificato con successo");
                        self.gridService.reload();
                    });
                    service.on("error",function(e) {
                        $.loader.hide({parent:'body'});
                        $.notify.error(e);
                    });
                    service.load();
                    $.loader.show({parent:'body'});
                });

                // Gestione cambio password
                $("a.user-change-password-link").click(function() {
                    var userId = $(this).parents("tr").data("entity-id");
                    var container = $('<div class="user-change-password-cnt"><span class="text-bold">Nuova password</span><input type="text" class="field gui-input mt5" name="newPassword"></div>');

                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Cambia password",
                        destroyOnClose: true,
                        height: 120,
                        width: 300,
                        buttons:{
                            Cambia: {
                                primary: true,
                                command: function() {
                                    var newPassword = $(this).find("input[name=newPassword]").val();

                                    if (!newPassword.trim()){
                                        return;
                                    }

                                    var svc = new  fmodel.AjaxService();
                                    svc.set("url", BASE + "user/" + userId +"/changepassword");
                                    svc.set("data", { newPassword: newPassword });
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        dialog.modalDialog("close");
                                        $.notify.success("La password dell'utente è stata modificata con successo");
                                    });
                                    svc.on("error", function(error){
                                        $.loader.hide({parent:'body'});
                                        $.notify.error(error);
                                    });

                                    svc.load();
                                    $.loader.show({parent:'body'});
                                }
                            }
                        }
                    });
                });
            });
        },
        create: function() {
            ui.Navigation.instance().navigate("users", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier")
            });
        },

        edit: function(id) {
            ui.Navigation.instance().navigate("users", "edit", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                id: id,
                g: this.gridService.get("identifier")
            });
        },

        remove: function(ids) {
            $.loader.show();
            this.gridService.remove(ids);
        },

        getToolbarButtons: function() {
            var self = this;
            var buttons = [];

            //se sono segretario rimuovo i pulsanti operazione
            // if (window.appcontext.roleid == 3)
            //     return buttons;


            buttons.push(
                {
                    text: msg.TOOLBAR_CREATE,
                    command: function () {
                        self.create();
                    },
                    icon: "plus"
                }
            );

            if (this.gridService.get("searchFormIncluded")) {
                buttons.push(
                    {
                        text: msg.TOOLBAR_SEARCH,
                        icon: "search",
                        command: function () {
                            self.get("grid").showSearchForm();
                        }
                    });
            }

            buttons.push(
                {
                    text: msg.TOOLBAR_REFRESH,
                    command: function() {
                        self.gridService.reload();
                    },
                    icon: "refresh"
                }
            );

            buttons.push(
                {
                    text: msg.TOOLBAR_ACTIONS,
                    group: 'selected',
                    type: "menu",
                    alignRight: true,
                    items: [
                        {
                            label: msg.TOOLBAR_SELECT_ALL,
                            command: function() {
                                self.grid.selectAll();
                            }
                        },
                        {
                            label: msg.TOOLBAR_UNSELECT_ALL,
                            command: function() {
                                self.grid.unselectAll();
                            }
                        },
                        { separator: true },
                        {
                            label: msg.TOOLBAR_DELETE_SELECTION,
                            command: function() {
                                var ids = self.grid.getSelection();
                                if(ids.length == 0) {
                                    $.notify.warn(msg.MSG_PLEASE_SELECT_ROW);
                                } else {
                                    self.remove(ids);
                                }
                            },
                            important: true
                        }
                    ],
                    icon: "asterisk"
                }
            );

            return buttons;
        },

        getBreadcrumbItems: function() {
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: msg.LABEL_HOME,
                    href: BASE
                },
                {
                    label: msg.CRUD_BREADCRUMB_GRID
                }
            ];
        }

    });

    var UsersCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService) {
            UsersCrudFormAppView.super.ctor.call(this, formService);

            var self = this;




            self.form.on("cancel", function() {
                self.close();
            });

            self.form.on("render", function() {




                // //qui attacco levento on change della select delle province
                // $('select[name="province"]').change(function(){
                //
                //     var selectedVal = $(this).val();
                //     //carico la lista delle città
                //     if (selectedVal){
                //
                //         self.loadCities(selectedVal, "");
                //
                //     }
                //     else
                //         $('select[name="city"]').empty().append("<option selected='selected' value=''>Select</option>");
                // });
                //
                // //se la provincia risulta già valorizzata...
                // if ($('select[name="province"]').val())
                //     self.loadCities($('select[name="province"]').val(), $('select[name="city"]').attr("data-value"));
                //



            });

            self.on("complete", function() {
                self.set("title", self.formService.get("title"));
            });
        },

        loadCities: function loadCities(provinceId, selectedValue){

            var model = new fmodel.AjaxService();
            model.set("content-type", "application/json")
            model.set("url", BASE + "values/cities?keyword=" + provinceId)


            model.on("load" , function(response){

                $('select[name="city"]').empty().append("<option selected='selected' value=''>Select</option>");

                if (selectedValue){
                    $.each(response, function(index, value){
                        var selected = ""
                        if (value.value == selectedValue)
                            selected = "selected='selected'";
                        else
                            selected = "";

                        $('select[name="city"]')
                            .append($("<option " + selected + "></option>")
                                .attr("value",value.value)
                                .text(value.label));
                    });
                }else{
                    $.each(response, function(index, value){
                        $('select[name="city"]')
                            .append($("<option></option>")
                                .attr("value",value.value)
                                .text(value.label));
                    });
                }




            });

            model.on("error", function(error){
                alert(error);
            });



            model.load();
        },



        getToolbarButtons: function() {
            var self = this;

            return [
                {
                    text: msg.TOOLBAR_BACK,
                    command: function() {
                        self.close();
                    },
                    icon: "arrow-left"
                },
                {
                    text: msg.TOOLBAR_SAVE,
                    command: function() {
                        self.form.submit();
                    },
                    icon: "save"
                }
            ];

        },

        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Utenti"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: msg.CRUD_BREADCRUMB_GRID,
                    href: ui.Navigation.instance().navigateUrl("users", "index", {
                        e: self.formService.get("gridIdentifier"),
                        reload: 1
                    })
                },
                {
                    label: msg.CRUD_BREADCRUMB_FORM
                }
            ];
        },

        close: function() {
            ui.Navigation.instance().navigate("users", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1
            });
        },

        submit: function() {
            var data = this.form.serialize();
            this.form.resetValidation();
            $.loader.show();
            this.formService.save(data);
        }
    });

    var DummyUsersCrudGridAppView = fviews.CrudGridAppView.extend({

        ctor: function(gridService) {
            DummyUsersCrudGridAppView.super.ctor.call(this, gridService);

            var self = this;

            this.lastTimeSelected = false;
            this.selectionTimer = null;

            gridService.on("load", function() {
                if (self.get("fullScreenForm")) {
                    self.lastTimeSelected = false;
                }
            });

            this.grid = this.get("grid");
            this.gridView = this.get("gridView");
            this.gridView.on({
                select: function() {
                    if(self.selectionTimer) {
                        window.clearTimeout(self.selectionTimer);
                    }

                    self.selectionTimer = window.setTimeout(function() {
                        var somethingSelected = self.grid.getSelection().length > 0;
                        if(somethingSelected !== self.lastTimeSelected) {
                            if(somethingSelected) {
                                //$('#toolbar').toolbar('showGroups', 'selected');
                            } else {
                                //$('#toolbar').toolbar('hideGroups', 'selected');
                            }
                        }
                        self.lastTimeSelected = somethingSelected;
                    }, 100);
                }
            });

            this.fullScreenForm = 0;

            this.on("complete", function() {
                self.set("title", self.gridService.get("title"));
                // $('div[data-component="search_form_container"]').show();


            });
        },


        getToolbarButtons: function() {

            return [];


        },

        getBreadcrumbItems: function() {
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: msg.LABEL_HOME,
                    href: BASE
                },
                {
                    label: msg.CRUD_BREADCRUMB_GRID
                }
            ];
        }

    });

    exports.UsersCrudFormAppView = UsersCrudFormAppView;
    exports.UsersCrudGridAppView = UsersCrudGridAppView;
    exports.DummyUsersCrudGridAppView = DummyUsersCrudGridAppView;
    return exports;
});