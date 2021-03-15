/**
 * Created by felicegramegna on 09/03/2021.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "RSU/contratto/contrattoRsuView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller per la ricerca delle aziende che riporta al meccanismo nativo del framework
    var ContrattoRsuController = fcontrollers.Controller.extend({
        ctor: function(){
            ContrattoRsuController.super.ctor.call(this);

            this.gridService = null;
            this.defaultFormIdentifier = "contractrsu";
            this.defaultGridIdentifier = "contractrsu";
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
            return new views.ContrattoRsuViewGridView(this.gridService);
        }
    });

    //controller per la modifca e creazione dell'azienda
    var ContrattoRsuEditController = fcontrollers.Controller.extend({
        ctor: function(){
            ContrattoRsuEditController.super.ctor.call(this);
        },
        index: function(params) {

            //questo è l'id del lavoratore che nel caso sia valorizzato indica un aggiornamento
            var contrId = "";

            if (params)
                if (params.id)
                    contrId = params.id;

            var url = BASE + "contractrsu";

            if (contrId)
                url = url + "/" + contrId;

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: url,
                data: {}
            });



            var view = new views.EditContrattoRsuAppView(service, contrId);
            // view.set("title", "Crea o modifica lavoratore");

            return view;
        }
    });

    var DeleteContrattoRsuController = fcontrollers.Controller.extend({
        ctor: function(){
            DeleteContrattoRsuController.super.ctor.call(this);
        },
        index: function(params) {
            var sedeId = params.id;
            var dialog = $("<p>Sicuro di voler eliminare l'elemento selezionato?</p>").modalDialog({
                autoOpen: true,
                title: "Elimina",
                destroyOnClose: true,
                height: 100,
                width:  400,
                buttons: {
                    send: {
                        label: "OK",
                        primary: true,
                        command: function () {

                            var svc = new fmodel.AjaxService();
                            svc.url = BASE + "contractrsu/" + sedeId;
                            svc.set("method", "DELETE");
                            svc.on({
                                load: function(response){

                                    $(dialog).modalDialog("close");
                                    $.notify.success("Operazione completata");

                                    //ritonrno alla modalità di ricerca
                                    location.href = BASE + "#/contractrsu";
                                },
                                error: function (error){
                                    $.notify.error(error);
                                }
                            });
                            svc.load();
                        }
                    }
                }
            });

        }

    });

    exports.DeleteContrattoRsuController = DeleteContrattoRsuController;
    exports.ContrattoRsuEditController = ContrattoRsuEditController;
    exports.ContrattoRsuController = ContrattoRsuController;


    return exports;
});
