/**
 * Created by angelo on 08/05/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils) {

    var exports = {};


    var AziendaDocumentiCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, firmId){
            AziendaDocumentiCrudGridAppView.super.ctor.call(this, gridService);

            this.firmId = firmId;
        },
        create: function() {
            var self = this;
            ui.Navigation.instance().navigate("firmdocscrud", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier"),
                firmId : self.firmId
            });
        },

        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("firmdocscrud", "edit", {
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
                    label: "Ricerca aziende",
                    //vado alla ricerca delle aziende
                    href: ui.Navigation.instance().navigateUrl("searchfirms", "index", {})
                },
                {
                    label: "Anagrafica azienda",
                    href: ui.Navigation.instance().navigateUrl("summaryfirm", "index", {
                        id: self.firmId
                    })
                },
                {
                    label: "Lista documenti"
                }
            ];
        }

    });

    var AziendaDocumentiCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, documentId, firmId){
            AziendaDocumentiCrudFormAppView.super.ctor.call(this, formService);

            this.documentId = documentId;
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
                    label: "Ricerca aziende",
                    //vado alla ricerca delle aziende
                    href: ui.Navigation.instance().navigateUrl("searchfirms", "index", {})
                },
                {
                    label: "Anagrafica azienda",
                    href: ui.Navigation.instance().navigateUrl("summaryfirm", "index", {
                        id: self.firmId
                    })
                },
                {
                    label: "Lista documenti",
                    href: ui.Navigation.instance().navigateUrl("firmdocscrud", "index", {
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
            ui.Navigation.instance().navigate("firmdocscrud", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                firmId : self.firmId
            });
        }

    });


    exports.AziendaDocumentiCrudGridAppView = AziendaDocumentiCrudGridAppView;
    exports.AziendaDocumentiCrudFormAppView = AziendaDocumentiCrudFormAppView;
    return exports;

});
