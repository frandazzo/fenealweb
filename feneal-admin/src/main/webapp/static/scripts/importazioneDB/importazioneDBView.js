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

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        synch: function (importId) {
            var route = BASE + "avviaimportazione/" + importId;

            return this.__createService(false, route);
        },
        synchstatus: function () {
            var route = BASE + "importazionestatus";

            return this.__createService(false, route);
        },
        __createService: function (isJsonContentType, route, data){

            //definisco il servizio
            var service = new  fmodel.AjaxService();

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




    var ImportazioneDBGridAppView = fviews.GridAppView.extend({
        ctor: function(gridService){
            ImportazioneDBGridAppView.super.ctor.call(this, gridService);

            this.on("complete", function() {
                $(".db-imported-cell").click(function() {
                    var recordId = $(this).parents("tr").attr("data-entity-id");

                    //adesso posso richiamare un controller che 
                    //mi visualizzerà un overlay che avvierà effettivamente il processo 
                    //di importazione
                    //tale overlay mostrerà lo stato della importazione attraverso un 
                    //timer che richiederà ogni secondo un aggiornamento sullo stato
                    //creo il div che conterrà la webpart
                    var div = $('<div class="import-parent"></div>');
                    $('body').append(div);
                    //avvio la webpart per mostrare il pannello di log della sincronizzazione
                    var webpart = new webparts.WebPart();
                    webpart.container = $('.import-parent');
                    webpart.url = BASE + "importpanel/" + recordId;
                    webpart.load();
 
                });
            });
        },

        getToolbarButtons: function() {
            return [];
        }

    });

    exports.ImportazioneDBGridAppView = ImportazioneDBGridAppView;

    return exports;

});
