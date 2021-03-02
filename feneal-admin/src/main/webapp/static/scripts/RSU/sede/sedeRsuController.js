/**
 * Created by felicegramegna on 22/02/2021.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "RSU/sede/sedeRsuView"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};

    //controller per la ricerca delle aziende che riporta al meccanismo nativo del framework
    var SedeRsuController = fcontrollers.Controller.extend({
        ctor: function(){
            SedeRsuController.super.ctor.call(this);
        },

        index: function(params) {

            var firmId = params.firmId;
            var url = BASE + "sedirsu/home/"+ firmId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.SedeRsuHomeRemoteView(service, firmId);
        }
    });

    var SedeRsuEditController = fcontrollers.Controller.extend({
        ctor: function(){
            SedeRsuEditController.super.ctor.call(this);
        },
        index: function(params) {

            //questo è l'id della sede che nel caso sia valorizzato indica un aggiornamento
            var sedeId = "";
            //questo è l'id del azienda che nel caso sia valorizzato indica un aggiornamento
            var firmId = "";

            if (params) {
                if (params.id)
                    sedeId = params.id;
                if (params.firmId)
                    firmId = params.firmId
            }

            var url = BASE + "sedersu";

            if (sedeId)
                url = url + "/" + sedeId;

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: url,
                data: {firmId:  firmId}
            });


            var view = new views.EditSedeRsuAppView(service, firmId);
            // view.set("title", "Crea o modifica lavoratore");

            return view;
        }
    });

    var DeleteSedeRsuController = fcontrollers.Controller.extend({
        ctor: function(){
            DeleteSedeRsuController.super.ctor.call(this);
        },
        index: function(params) {
            var sedeId = params.id;
            var firmId =params.firmId;
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
                            svc.url = BASE + "sedersu/" + sedeId;
                            svc.set("method", "DELETE");
                            svc.on({
                                load: function(response){

                                    $(dialog).modalDialog("close");
                                    $.notify.success("Operazione completata");

                                    //ritonrno alla modalità di ricerca
                                    location.href = BASE + "#/sedersu/index?firmId=" + firmId
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


    exports.DeleteSedeRsuController = DeleteSedeRsuController;
    exports.SedeRsuEditController = SedeRsuEditController;
    exports.SedeRsuController = SedeRsuController;
    return exports;
});
