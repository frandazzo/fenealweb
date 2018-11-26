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


    var RichiesteCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, workerId){
            RichiesteCrudGridAppView.super.ctor.call(this, gridService);

            this.workerId = workerId;
        },
        create: function() {
            var self = this;
            ui.Navigation.instance().navigate("richiestecrud", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
            });
        },

        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("richiestecrud", "edit", {
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
                    label: "Lista richieste"
                }
            ];
        }

    });

    var RichiesteCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, requestId, workerId){
            RichiesteCrudFormAppView.super.ctor.call(this, formService);

            this.requestId = requestId;
            this.workerId = workerId;
            var self = this;

            this.formService.on("save", function() {
                // Mando email per  la richiesta info territori (solo in fase di creazione)
                if (!self.requestId) {
                    destinatario = $("input[name=destinatario]").val();

                    var svc = new  fmodel.AjaxService();
                    var data = {
                        requestToProvince: $("select[name=requestToProvince]").val(),
                        destinatario: $("input[name=destinatario]").val(),
                        note: $("div[data-property=note]").find(".note-editable").html()
                    };

                    svc.set("url", BASE + "worker/sendrichiediinfo");
                    svc.set("contentType", "application/json");
                    svc.set("data", JSON.stringify(data));
                    svc.set("method", "POST");

                    svc.on("load", function(response){
                        $.loader.hide({parent:'body'});
                        dialog.modalDialog("close");
                    });
                    svc.on("error", function(error){
                        $.loader.hide({parent:'body'});
                        $.notify.error(error);
                    });

                    svc.load();
                    $.loader.show({parent:'body'});
                }
            });

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
            var title = "Crea richiesta";
            if (self.requestId)
                title = "Modifica richiesta";

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
                    label: "Lista richieste",
                    href: ui.Navigation.instance().navigateUrl("richiestecrud", "index", {
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
            ui.Navigation.instance().navigate("richiestecrud", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                workerId : self.workerId
            });
        }

    });


    exports.RichiesteCrudGridAppView = RichiesteCrudGridAppView;
    exports.RichiesteCrudFormAppView = RichiesteCrudFormAppView;
    return exports;

});
