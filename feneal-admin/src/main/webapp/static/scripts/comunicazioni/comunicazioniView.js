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


    var ComunicazioniCrudGridAppView = fviews.CrudGridAppView.extend({
        ctor: function(gridService, workerId){
            ComunicazioniCrudGridAppView.super.ctor.call(this, gridService);

            this.workerId = workerId;
        },
        create: function() {
            var self = this;
            ui.Navigation.instance().navigate("comunicazionicrud", "create", {
                e: this.gridService.get("formIdentifier"),
                fs: this.fullScreenForm,
                g: this.gridService.get("identifier"),
                workerId : self.workerId
            });
        },

        edit: function(id) {
            var self = this;
            ui.Navigation.instance().navigate("comunicazionicrud", "edit", {
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
                    label: "Lista comunicazioni"
                }
            ];
        }

    });

    var ComunicazioniCrudFormAppView = fviews.CrudFormAppView.extend({
        ctor: function(formService, communicationId, workerId){
            ComunicazioniCrudFormAppView.super.ctor.call(this, formService);

            this.communicationId = communicationId;
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
            var title = "Crea comunicazione";
            if (self.communicationId)
                title = "Modifica comunicazione";

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
                    label: "Lista comunicazioni",
                    href: ui.Navigation.instance().navigateUrl("comunicazionicrud", "index", {
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
            ui.Navigation.instance().navigate("comunicazionicrud", "index", {
                e: this.formService.get("gridIdentifier"),
                reload: 1,
                fs: 1,
                workerId : self.workerId
            });
        }

    });

    var ComunicazioniHomeRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, workerId){
            ComunicazioniHomeRemoteView.super.ctor.call(this, service);

            var self = this;
            this.workerId = workerId;

            this.on("load", function(){

                // alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();

            });

        },
        onServiceLoad: function(html) {
            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(html);
            this.container.empty().append(this.content);
            this.invoke("load");

        },
        createToolbar: function() {
            // var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            // var size = buttons.length;
            // for(var i = 0; i < size; i++) {
            //     var button = buttons[i];
            //     $t.toolbar("add", button);
            // }
        },
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
        },


        // getToolbarButtons: function() {
        //     var self = this;
        //
        //     return [
        //         {
        //             text: "Nuova Com",
        //             command: function() {
        //
        //                 ui.Navigation.instance().navigate("editdelega", "index", {
        //                     fs: this.fullScreenForm,
        //                     workerId : self.workerId
        //                 });
        //             },
        //             icon: "plus"
        //         },
        //     ];
        // },
        getBreadcrumbItems: function() {
            var self = this;
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
                    label: "Comunicazioni"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        }

    });

    exports.ComunicazioniHomeRemoteView = ComunicazioniHomeRemoteView;
    exports.ComunicazioniCrudGridAppView = ComunicazioniCrudGridAppView;
    exports.ComunicazioniCrudFormAppView = ComunicazioniCrudFormAppView;
    return exports;

});
