/**
 * Created by fgran on 08/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", 
    "framework/plugins",
    "framework/webparts"], function(core, model, fviews, ui, widgets, plugins, webparts) {

    var exports = {};


    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
    
        },
        createListaLavoro: function(notificationId, type){
            var route = BASE + "listalavoro/notification/" + notificationId + "/" + type;
    
            return this.__createService(false, route, null);
        },
        __createService: function (isJsonContentType, route, data){
    
            //definisco il servizio
            var service = new  model.AjaxService();
    
            //se sono dati json ne imposto il content type
            if (isJsonContentType)
                service.set("contentType", "application/json");
            else
                service.set("contentType", "application/x-www-form-urlencoded; charset=UTF-8");
    
            //se ci sono dati li trasformoi in stringa json
            //e li accodo al servizio
            if (data){
    
                if(typeof(data) == 'string'){
    
                    service.set("data", data);
                }
                else{
                    var stringified1 = JSON.stringify(data);
                    service.set("data", stringified1);
                }
    
    
    
            }
    
            service.set("url", route);
            service.set("method", "POST");
    
    
            return service;
        }
    
    
    
    });


    var NotificationManager = core.AObject.extend({
        ctor: function(){
            NotificationManager.super.ctor.call(this);
        },
        init: function() {
            //prendo tutte le notifiche marcate con la classe notification e ne imposto l'handler di click
            //vedi la vista header.vm
            $('.notification').click(function(){

                var notificationId = $(this).attr('data-id');

                //quando clicco su una notifica apro una popup che carica una webpart
                
                var container = $('<div class="notification-container"></div>');


                var webpart = new webparts.WebPart();
                webpart.container = container;
                webpart.url = BASE + "notification/" + notificationId;
                
                webpart.on("load", function(){
                    //chiudo la finestra delle notifiche
                    $(document).click();
                    
                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Notifica",
                        destroyOnClose: true,
                        buttons: {
                            Ringrazia: {
                                primary: true,
                                command: function() {
                                    // Richiamo il servizio per mandare mail di ringraziamento in caso di segnalazione lavoratore

                                    var svc = new  fmodel.AjaxService();

                                    var data = {
                                        notificationId: notificationId
                                    };

                                    svc.set("data", data);
                                    svc.set("url", BASE + "notification/sendthanksreporting");
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.notify.success("Operazione completata");
                                        dialog.modalDialog("close");
                                    });
                                    svc.on("error", function(error){
                                        $.notify.error(error);
                                    });

                                    svc.load();
                                }
                            }
                        }
                    });

                });
                
                
                webpart.load();


                //chiudo la finestra delle notifiche
                $(document).click();

            });
        }

    });

    var NewImportNotification = core.AObject.extend({
        ctor: function(){
            NewImportNotification.super.ctor.call(this);
        },
        init: function() {
            //guarda la vista notifications/newImport.vm
           //inizializzo i pulsanti per la creazio0ne di una lista di lavoro
            $('.knownusers').click(function(){
                
                var factory = new RepositoryServiceFactory();
                var svc = factory.createListaLavoro($('.knownusers').attr('data-id'), "known");
                
                svc.on("load", function(response){
                    
                    
                    //qui se la response non è nulla visualizzo la listai lavoro
                    if (response) {
                        
                        $.notify.success("Operazione completata!");
                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                            id: response
                        });
                    }
                    else
                        $.notify.error("Nessuna lista creata!");
                });
                
                
                svc.on("error", function(error){
                   $.notify.error(error); 
                });
                
                svc.load();
                
            });

            $('.unknownusers').click(function(){
                var factory = new RepositoryServiceFactory();
                var svc = factory.createListaLavoro($('.unknownusers').attr('data-id'), "unknown");

                svc.on("load", function(response){


                    //qui se la response non è nulla visualizzo la listai lavoro
                    if (response)
                        $.notify.success("Operazione completata!");
                    else
                        $.notify.error("Nessuna lista creata!");
                });


                svc.on("error", function(error){
                    $.notify.error(error);
                });

                svc.load();
            });
            
            $('.withoutdelega').click(function(){
                var factory = new RepositoryServiceFactory();
                var svc = factory.createListaLavoro($('.withoutdelega').attr('data-id'), "withoutdelega");

                svc.on("load", function(response){


                    //qui se la response non è nulla visualizzo la listai lavoro
                    if (response)
                        $.notify.success("Operazione completata!");
                    else
                        $.notify.error("Nessuna lista creata!");
                });


                svc.on("error", function(error){
                    $.notify.error(error);
                });

                svc.load();
            });

            // Il pulsante RINGRAZIA lo visualizzo solo nel caso di notifica di segnalazione lavoratore,
            // altrimenti (come in questo caso) lo rimuovo
            $(".framework-modal").find(".btn-primary").remove();
        }

    });

    var ReportingWorkerNotification = core.AObject.extend({
        ctor: function(){
            ReportingWorkerNotification.super.ctor.call(this);
        },
        init: function() {
            //guarda la vista notifications/reportingworker.vm

            $('.worker-link').click(function(){
                $(document).click();
                ui.Navigation.instance().navigate("summaryworker", "index", {
                    id:$('.worker-link').attr('data-id')

                })
                
            });

            // Il pulsante RINGRAZIA lo visualizzo solo nel caso di notifica di segnalazione lavoratore,
            // altrimenti lo rimuovo
            if ($(".reporting-worker-descr").length == 0)
                $(".framework-modal").find(".btn-primary").remove();
        }

    });
    
    
    exports.ReportingWorkerNotification = ReportingWorkerNotification;
    exports.NewImportNotification = NewImportNotification;
    exports.NotificationManager = NotificationManager;
    return exports;

});