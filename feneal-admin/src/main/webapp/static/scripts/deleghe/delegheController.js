/**
 * Created by fgran on 07/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/controllers",
    "deleghe/delegheViews"], function(core, fmodel, fviews, fcontrollers, views) {

    var exports = {};




    //questo controller gestisce la visualizzazione di un riepilogo dati del lavoratore
    var DelegheHomeController = fcontrollers.Controller.extend({
        ctor: function(){
            DelegheHomeController.super.ctor.call(this);
        },
        index: function(params) {

            var workerId = params.workerId;
            var url = BASE + "deleghe/home/"+ workerId;
            var service = new fmodel.AjaxService();
            service.set({
                url: url
            });
            return new views.DelegheHomeRemoteView(service, workerId);

        }

    });

    var DelegaStateController = fcontrollers.Controller.extend({
        ctor: function(){
            DelegaStateController.super.ctor.call(this);
        },
        normalizeSubmitResult: function(form){

            //metto tutto in un data array....
            var dataArray = [];
            var formData = form.serializeArray();

            for(var i=0; i<formData.length; i++) {
                dataArray.push({
                    property: formData[i].name,
                    value: formData[i].value
                });
            }

            //tiro fuori un oggetto javascript correttamente serializzato


            //devo ciclare tra tutti gli oggetti  e verificare se ci sono proprietà con lo stesso nome
            // che provvedero' ad inserire in un array
            //questo buffer conterrà il nome della proprietà e una lista che conterrà tutti gli oggetti con lo stesso nome di proprietà
            var propertyBuffer = {};

            //ciclo adesso sugli oggetti della load request
            for (var prop in dataArray){


                //se la proprietà non cè nel buffer la aggiungo creando una nuova lista a cui aggiungo il valore della proprietà stessa

                //prendo il nome della proprietà che farà da key nel buffer
                var propName =  dataArray[prop].property;
                if (!propertyBuffer[propName]){
                    propertyBuffer[propName] = [];
                    propertyBuffer[propName].push(dataArray[prop]);
                }else{
                    propertyBuffer[propName].push(dataArray[prop]);
                }




            }


            //adesso faccio l'inverso: ricostruisco l'oggetto a partire dal buffer
            var data = {};
            for(var propName in propertyBuffer){

                if (propertyBuffer[propName].length == 1) //se ce n'è solo una ne riprendo la property
                {
                    data[propName] =  propertyBuffer[propName][0].value;
                }else{
                    data[propName] = this.__constructArrayOfValues(propertyBuffer[propName]);

                }
            }
            return data;
        },
        index: function(params) {
            var self = this;
            var delegaId = params.id;
            var newState =params.newState;

            if (newState == 0){
                //sto ripristinando lo stato precedente
                var dialog = $("<p>Sicuro di voler ripristinare lo stato della delega?</p>").modalDialog({
                    autoOpen: true,
                    title: "Ripristina",
                    destroyOnClose: true,
                    height: 100,
                    width:  400,
                    buttons: {
                        send: {
                            label: "OK",
                            primary: true,
                            command: function () {



                                var data = {};
                                data.delegaId = delegaId;
                                data.state = newState;
                                var svc = new fmodel.AjaxService();
                                svc.url = BASE + "delega/changeState";
                                svc.set("method", "POST");
                                svc.set("contentType", "application/json");
                                svc.set("data", JSON.stringify(data));

                                svc.on({
                                    load: function(response){

                                        $(dialog).modalDialog("close");
                                        $.notify.success("Operazione completata");

                                        //ritonrno alla modalità di ricerca
                                        history.back()
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

            }else {
                //è un normale cambio di stato
                var container1 = $('<div class="delega-state-container"></div>');


                var path = BASE + "delega/changeState/" + newState;
                var formService = new fmodel.FormService();
                formService.set("url", path);

                var form = new fviews.FormView(formService);
                form.container = container1;

                form.on("render", function () {
                    //codice per rimuovoere il pulsante salva - annulla
                    container1.find(".panel-footer, .panel-heading").hide();
                });

                form.show();

                var dialog = container1.modalDialog({
                    autoOpen: true,
                    title: "Nuovo stato",
                    destroyOnClose: true,
                    height: 200,
                    width: 800,
                    buttons: {
                        Salva: {
                            primary: true,
                            command: function () {
                                var data = self.normalizeSubmitResult(form.form);
                                data.delegaId = delegaId;
                                data.state = newState;
                                var svc = new fmodel.AjaxService();
                                svc.url = BASE + "delega/changeState";
                                svc.set("method", "POST");
                                svc.set("contentType", "application/json");
                                svc.set("data", JSON.stringify(data));
                                svc.on({
                                    load: function (response) {

                                        $(dialog).modalDialog("close");
                                        $.notify.success("Operazione completata");

                                        history.back();
                                    },
                                    error: function (error) {
                                        $.notify.error(error);
                                    }
                                });
                                svc.load();

                            }
                        }

                    }
                });
            }

        }

    });
    
    var DeleteDelegaController = fcontrollers.Controller.extend({
        ctor: function(){
            DeleteDelegaController.super.ctor.call(this);
        },
        index: function(params) {
            var delegaId = params.id;
            var workerId =params.workerId;
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
                            svc.url = BASE + "delega/" + delegaId;
                            svc.set("method", "DELETE");
                            svc.on({
                                load: function(response){

                                    $(dialog).modalDialog("close");
                                    $.notify.success("Operazione completata");

                                    //ritonrno alla modalità di ricerca
                                    location.href = BASE + "#/deleghehome/index?workerId=" + workerId
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

    //controllerv per la gestione della mascera di creazione o aggirnamento di una delega
    var EditDelegaController = fcontrollers.Controller.extend({
        ctor: function(){
            EditDelegaController.super.ctor.call(this);
        },
        index: function(params) {

            //questo è l'id della delega che nel caso sia valorizzato indica un aggiornamento
            var delegaId = "";
            //permette di valorizzare il lavoratore in fase di creazione della delega
            var workerId = "";

            if (params) {
                if (params.id)
                    delegaId = params.id;
                if (params.workerId)
                    workerId = params.workerId
                }
            var url = BASE + "delega";

            if (delegaId)
                url = url + "/" + delegaId;

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: url,
                data: {workerId: workerId}
            });



            var view = new views.EditDelegaAppView(service, delegaId, workerId);
            
            // view.set("title", "Crea o modifica lavoratore");

            return view;
        }
    });



    var ImportaDelegheController = fcontrollers.Controller.extend({
        ctor: function(){
            ImportaDelegheController.super.ctor.call(this);
        },
        index: function(params) {

            var service = new fmodel.FormService();
            service.set({
                method: "GET",
                url: BASE + "importadeleghe",
                data: {}
            });



            var view = new views.ImportaDelegheAppView(service);


            return view;
        }

    });



    exports.ImportaDelegheController = ImportaDelegheController;
    exports.DelegheHomeController = DelegheHomeController;
    exports.EditDelegaController = EditDelegaController;
    exports.DelegaStateController = DelegaStateController;
    exports.DeleteDelegaController = DeleteDelegaController;

    return exports;
});
