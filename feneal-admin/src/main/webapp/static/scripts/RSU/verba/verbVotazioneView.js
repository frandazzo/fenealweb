/**
 * Created by felicegramegna on 12/03/2021.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils) {

    var exports = {};

    var verbVotazioneCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, firmId){
            verbVotazioneCrudGridAppView.super.ctor.call(this, gridService);

            this.firmId = firmId;
        },
        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("verbvote", "edit", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                id: id,
                g: this.gridService.get("identifier"),
                firmId : self.firmId
            });
        },
        getBreadcrumbItems: function() {
            var self = this;

            return [
                {
                    pageTitle: "FenealWeb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ricerca aziende RSU",
                    //vado alla ricerca delle aziende
                    href: ui.Navigation.instance().navigateUrl("searchfirmsrsu", "index", {})
                },
                {
                    label: "Anagrafica azienda RSU",
                    href: ui.Navigation.instance().navigateUrl("summaryfirmrsu", "index", {
                        id: self.firmId
                    })
                },
                {
                    label: "Lista voti verbalizzati"
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;
            var buttons = [];

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

                                    ids.forEach(function (item) {
                                        var svc = new fmodel.AjaxService();
                                        svc.url = BASE + "contractrsu/" + item;
                                        svc.set("method", "DELETE");
                                        svc.on({
                                            load: function(response){

                                                $.notify.success("Operazione completata");

                                                //ritonrno alla modalità di ricerca
                                                self.gridService.reload();
                                            },
                                            error: function (error){
                                                $.notify.error(error);
                                            }
                                        });
                                        svc.load();
                                    });


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

    });

    var VerbVotCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, verbId, firmId){
            VerbVotCrudFormAppView.super.ctor.call(this, formService);

            this.verbId = verbId;
            this.firmId = firmId;
        },
        getToolbarButtons: function() {
            var self = this;

            return [
                {
                    text: "Annulla",
                    command: function() {
                        self.close();
                    },
                    icon: "arrow-left"
                },
                {
                    text: "Salva",
                    command: function() {
                        self.form.submit();
                    },
                    icon: "save"
                }
            ];

        },

        getBreadcrumbItems: function() {
            var self = this;
            var title = "Crea verb";
            if (self.verbId)
                title = "Modifica verbalizzazione";

            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ricerca aziende RSU",
                    //vado alla ricerca delle aziende
                    href: ui.Navigation.instance().navigateUrl("searchfirmsrsu", "index", {})
                },
                {
                    label: "Anagrafica azienda RSU",
                    href: ui.Navigation.instance().navigateUrl("summaryfirmrsu", "index", {
                        id: self.firmId
                    })
                },
                {
                    label: "Lista verbalizzazioni",
                    href: ui.Navigation.instance().navigateUrl("verbvote", "index", {
                        e: self.formService.get("gridIdentifier"),
                        reload: 1,
                        firmId : self.firmId
                    })
                },
                {
                    label: title
                }
            ];
        },

        close: function() {
            var self = this;
            ui.Navigation.instance().navigate("verbvote", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                firmId : self.firmId
            });
        }

    });


    exports.verbVotazioneCrudGridAppView = verbVotazioneCrudGridAppView;
    exports.VerbVotCrudFormAppView = VerbVotCrudFormAppView;
    return exports;
});
