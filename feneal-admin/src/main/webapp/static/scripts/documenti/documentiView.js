/**
 * Created by fgran on 28/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils) {

    var exports = {};


    var DocumentiCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, workerId){
            DocumentiCrudGridAppView.super.ctor.call(this, gridService);

            this.workerId = workerId;
        },
        create: function() {
            var self = this;
            ui.Navigation.instance().navigate("documenticrud", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
            });
        },

        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("documenticrud", "edit", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                id: id,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
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
                    label: "Ricerca lavoratore",
                    //vado alla ricerca dei lavoratori
                    href: ui.Navigation.instance().navigateUrl("searchworkers", "index", {})
                },
                {
                    label: "Anagrafica " + localStorage.getItem("workerName"),
                    href: ui.Navigation.instance().navigateUrl("summaryworker", "index", {
                        id: self.workerId
                    })
                },
                {
                    label: "Lista documenti"
                }
            ];
        }

    });

    var DocumentiCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, documentId, workerId){
            DocumentiCrudFormAppView.super.ctor.call(this, formService);

            this.documentId = documentId;
            this.workerId = workerId;
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
            var title = "Crea documento";
            if (self.documentId)
                title = "Modifica documento";

            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ricerca lavoratore",
                    //vado alla ricerca dei lavoratori
                    href: ui.Navigation.instance().navigateUrl("searchworkers", "index", {})
                },
                {
                    label: "Anagrafica " + localStorage.getItem("workerName"),
                    href: ui.Navigation.instance().navigateUrl("summaryworker", "index", {
                        id: self.workerId
                    })
                },
                {
                    label: "Lista documenti",
                    href: ui.Navigation.instance().navigateUrl("documenticrud", "index", {
                        e: self.formService.get("gridIdentifier"),
                        reload: 1,
                        workerId : self.workerId
                    })
                },
                {
                    label: title
                }
            ];
        },

        close: function() {
            var self = this;
            ui.Navigation.instance().navigate("documenticrud", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                workerId : self.workerId
            });
        }

    });


    exports.DocumentiCrudGridAppView = DocumentiCrudGridAppView;
    exports.DocumentiCrudFormAppView = DocumentiCrudFormAppView;
    return exports;

});
