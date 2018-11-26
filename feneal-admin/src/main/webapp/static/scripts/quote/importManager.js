/**
 * Created by fgran on 22/05/2016.
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
        clearsynchstatus: function () {
            var route = BASE + "clearimportazionestatus";

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




    var ImportView = core.AObject.extend({
        ctor: function(importId){
            ImportView.super.ctor.call(this);
            this.importId = importId;

        },
        init: function() {
           // alert(this.importId);
            var self = this;
            var synchStop = false;
            //avvio la sincronizzazione
            var factory = new RepositoryServiceFactory();

            //imposto il pulsante di chiusura
            $('.close-synch').click(function () {
                // //azzero gli step nel db
                // var svc2 = factory.clearregister();
                // svc2.load();
                $('.import-parent').remove();
                window.location.reload();
            });

            var svc2 = factory.clearsynchstatus();
            svc2.on('load', function (response) {


                var svc = factory.synch(self.importId);
                svc.load();
                svc.on('error', function(){
                    synchStop = true;
                    $('.close-synch').prop('disabled', '');
                });

                //contestualmente avvio un timeout ogni due secondi che mi indica lo stato
                //della sincronizzazione


                var svc1 = factory.synchstatus();

                svc1.on('load', function (response) {
                    //posso renderizzare i risultti
                    var startAnagraficheLav = response.startAnagraficheLav;
                    var anagraficheLav = response.anagraficheLav;
                    var endAnagraficheLav = response.endAnagraficheLav;
                    var startAnagraficheAz = response.startAnagraficheAz;
                    var anagraficheAz = response.anagraficheAz;
                    var endAnagraficheAz = response.endAnagraficheAz;
                    var startImport = response.startImport;
                    var endImport = response.endImport;
                    var importData = response.importData;
                    var endAllImport = response.endAllImport;


                    $('.synch-text').val('');
                    var val = startAnagraficheLav + "\n";
                    val = val + anagraficheLav + "\n";
                    val = val + endAnagraficheLav + "\n";
                    val = val + startAnagraficheAz + "\n";
                    val = val + anagraficheAz + "\n";
                    val = val + endAnagraficheAz + "\n";
                    val = val + startImport + "\n";
                    val = val + endImport + "\n";
                    val = val + importData + "\n";
                    val = val + endAllImport + "\n";

                    $('.synch-text').val(val);


                    if (response.terminated == true) {
                        $('.close-synch').prop('disabled', '');
                        synchStop = true;
                    }
                });

                svc1.on('error', function (error) {
                    synchStop = true;
                    $('.synch-text').append(error);
                    $('.close-synch').prop('disabled', '');
                });


                var statusSynchCheck = function () {
                    if (synchStop == true)
                        return;
                    console.log('synch check');
                    svc1.load();
                    window.setTimeout(statusSynchCheck, 2000);

                }

                statusSynchCheck();






            });
            svc2.load();
        }

    });

    exports.importView = ImportView;

    return exports;

});
