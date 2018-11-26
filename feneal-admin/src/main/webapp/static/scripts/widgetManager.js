/**
 * Created by david on 06/04/2016.
 */


/**
 * Created by david on 06/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var WidgetManager = core.AObject.extend({

        ctor: function (context) {
            WidgetManager.super.ctor.call(this);
            //contesto di utilizzo dei widgets
            this.context = context;
        },

        init: function () {

            var self = this;

            //imposto l'evento change sulla select nelle opzioni
            //affinche possa salvare quali widget visualizzare e in quale modalita
            $("." + self.context + "WidgetContainer").find("input[type=checkbox]").on("change",function(e){

                var self1 = $(this);
                var target = $(this).attr("data-name");
                var url = $(this).attr("data-url");

                var elemToDisplay =  $("[data-container-widget="+target+"]");
                if (elemToDisplay.length == 0){
                    serviceChooseWidget(target, self1)
                    return;
                }

                if(!$(this).is(':checked')){
                    //se ho tolto il flag svuoto la webpart
                    elemToDisplay.empty();

                }
                else{
                    //ricarico l'intera webpart se ho checcato il flag nelle opzioni
                    var webpart = new webparts.WebPart();
                    webpart.set("url", BASE + url);
                    webpart.set("container", elemToDisplay);
                    webpart.on("load", function (resp) {
                        $("[data-container-widget="+target+"]").show();
                    });
                    webpart.on("error", function (e) {
                        alert(e);
                    });
                    webpart.load();

                }

                //memorizzo la scelta effettuata
                serviceChooseWidget(target, self1);

            });

            //imposto levento per la scelta del layout
            $("." + self.context + "WidgetContainer").find("select").on("change",function() {

                var value = $(this).val();
                var contextChoose = $(".contextChoose").find(".active").find("a").text();

                var service = new model.AjaxService();
                service.set("url", BASE + "options/selectlayout");
                service.set("method", "POST");
                service.set("data", {value: value, context: contextChoose});
                service.on("load", function (resp) {
                    $.notify.success("Operazione completata");
                });
                service.on("error", function (e) {
                    alert(e);
                });
                service.load();

            });

            
            
            
        }

    });

    var FenealWidget = core.AObject.extend({

        ctor: function (widgetName, widgetParams, widgetContext) {
            FenealWidget.super.ctor.call(this);

            //l'attributo data-container-widget è nel div padre di tutto il widget che viene messo
            //nella dashboard o nelle pagine del lavoratore o dell'azienda
            //lo stesso div contiene la classe panel.
            //viene dunque fatta la supposizione che ogni widget contenga i div di panel heading e panelbody
            //in modo che linizializzaizone dei pulsanti in panel heading funzioni correttamente

            var widget = $("[data-container-widget='"+ widgetName +"']");
            if (widget.length == 0)
                throw "Widget " + widgetName + " non trovato!";
            if (widget.length >1)
                throw "Widget " + widgetName + " non univoco!";

            this.widget = widget;
            this.widgetParams = widgetParams;
            //estrapolo in una lista di oggetti i parametri che giungono codificati
            this.widgetEditParamObjects = this.initializeWidgetParams(widgetParams);
            this.widgetContext = widgetContext;

        },

        init: function () {

            var self = this;

            //inizializzo tuttii componenrti del pannello in cui è innestato il widget
            var panel = self.widget;


            //una volta definite tutte le funzioni necessarie posso indicare il clikc che genererà l'utilizzo di tali funzioni
            self.widget.on('click', '.panel-controls > a.panel-control-title', function(e) {
                e.preventDefault();

                // if a panel is being dragged disable menu clicks
                if ($('body.ui-drag-active').length) {
                    return;
                }

                //recupero il riferimento al tastino cliccato
                var clikkedLink = $(this);

                // in base alla classe dell'elemento cliccato decido la azione da eseguire
                var action = clikkedLink.attr('class');

                if (action == "panel-control-title"){
                    self.widgetEditParams();
                }

                self.toggleLoader();

            });



        },
        widgetEditParams : function() {
            var self = this;

            var panel = self.widget;

            // Panel header variables
            var panelHeading = panel.children('.panel-heading');
            // function for toggling the editbox menu
            var toggleBox = function() {
                var panelEditBox = panel.find('.panel-editbox');
                panelEditBox.slideToggle('fast', function() {
                    panel.toggleClass('panel-editbox-open');


                    if (!panel.hasClass('panel-editbox-open')) {

                        //se sto chiudendo la maschera salvo i dati sul server ma non ricarico
                        var form = panel.find('.panel-editbox').find('form');

                        form.on("submit", function(){
                            var data = $(this).serializeArray();
                            var paramsToSend = self.normalizeWidgetParamsToSend(data);

                            // invio adesso i paramtri al server e non appena è andato tutto a buon fine richiedo il refresh del
                            //widget
                            var service = new model.AjaxService();
                            service.set("url", BASE + "options/setparams");
                            service.set("method", "POST");
                            service.set("data", {name: self.widget.attr('data-container-widget'), value: paramsToSend, context: self.widgetContext});
                            service.on("load", function (resp) {

                            });
                            service.on("error", function (e) {
                                alert(e);
                            });
                            service.load();

                            return false;
                        })
                    }
                });
            };

            // If editbox not found, create one and attach handlers
            if (!panel.find('.panel-editbox').length) {
                //recupero i paramteri normalizzati
                var params = self.initializeWidgetParams(self.widgetParams);
                //creo il div che conterrà il form con i parametri del widget
                var editBox = self.defineEditBoxForm();
                panelHeading.after(editBox);
                // Definisco gli eventi del pannello di editing del widget
                var panelEditBox = panel.find('.panel-editbox');
                //inserisco nell'edit form tutti gli input necessari a definire il comportamento del widget
                //e i paramtri di default
                self.prepareEditBox(panelEditBox, params);
                //lo nascondo la prima volta dopo creato!!!!!!!!!!!!!
                toggleBox();
            } else {
                // If found simply toggle the menu
                toggleBox();
            }
        },
        prepareEditBox : function(editPanel, params){
            //l'edit panel è lelemento jquery che conterra gli elementi del form...
            //che saranno valorizzati con i params
            var self = this;
            self.renderHtmlForm(editPanel, params);

            //adesso posso recuperare il form dall'edit panel e salvare i parametri di cui si vuole fare il submit
            var form = editPanel.find('form');
            if (form.length == 1)
                form.on("submit", function(){
                    var data = $(this).serializeArray();
                    var paramsToSend = self.normalizeWidgetParamsToSend(data);


                    // invio adesso i paramtri al server e non appena è andato tutto a buon fine richiedo il refresh del
                    //widget
                    var service = new model.AjaxService();
                    service.set("url", BASE + "options/setparams");
                    service.set("method", "POST");
                    service.set("data", {name: self.widget.attr('data-container-widget'), value: paramsToSend, context: self.widgetContext});
                    service.on("load", function (resp) {
                        self.widget.removeClass('panel-editbox-open');
                        //prima di fare il refrresh rimuovo l'evento click d
                        self.widget.off('click', '.panel-controls > a.panel-control-title');
                        self.refresh();
                    });
                    service.on("error", function (e) {
                        alert(e);
                    });
                    service.load();

                    return false;
                })
        },
        refresh: function(){
            // svuoto l'intero panel e nel ricarico la webpart
            var self = this;
            self.widget.empty();

            var webpart = new webparts.WebPart();
            webpart.set("url", self.widget.attr('data-webpart-url'));
            webpart.set("container", self.widget);
            webpart.on("load", function (resp) {

            });
            webpart.on("error", function (e) {
                alert(e);
            });
            webpart.load();
        },
        normalizeWidgetParamsToSend : function(arrayOfData){
            //funzione astratta che recupera i paramteri dalla form e li strasforma in una stringa da poter inviare al server
            var result = "";
            for (var i = 0; i < arrayOfData.length; i++) {

                var obj = arrayOfData[i];
                //questo oggetto è un oggettocosi fatto:
                // {
                //     name: "prova".
                //     value: "ciao"
                // }
                var elemName = "name=" + obj.name;
                var elemValue = "value=" + obj.value;
                var element = elemName + "@" + elemValue;

                result = result + "#" +  element;
            }

            return result;
        },
        initializeWidgetParams:function(params){
            //mappo la sgtringa proveniente dal server che contiene la concatenazione dei parametri
            //del widget su un oggetto che contiene i reali paramtri associabili con la defineEditBoxForm function

            //la string che arriva dal server avrà questo formato
            //#name=prova@value=pippo#name=prova1@value=pippo1

            //da questa stringa devo recuperare un oggetto cosi fatto e resituirlo:
            // var params = [
            //     {
            //         name: prova,
            //         value:pippo
            //     },
            //     {
            //         name: prova1,
            //         value:pippo1
            //     }
            // ];
            //eseguo una split per recuperare tutte le proprietà
            var result = [];
            var objs = params.split('#');
            for (var i = 0; i < objs.length; i++) {
                var obj = objs[i];
                if (obj){ //la split puo ritornare valori vuoti....
                    //l'obj ha un formato cosi fatto:
                    //name=prova@value=pippo
                    //eseguo una ulteriore split per separare le propriètà del singolo oggetto
                    var props = obj.split('@');
                    //props è un array di stringhe "name=prova"
                    //basta adesso splittare in bae al carattere "=" e avere rispettivamente il nome della proprieta
                    //e il suo valore

                    //creo l'oggetto che popolerà definisce il valore di una input
                    var inputParam = {};
                    for (var j = 0; j < props.length; j++) {
                        var obj1 = props[j];

                        var last = obj1.split('=');
                        //nell'oggetto obj1 ho la seguente struttura:
                        //obj[0] = name
                        //obj[1] = prova
                        inputParam[last[0]] = last[1];

                    }
                    result.push(inputParam);
                }

            }

            return result;
        },
        renderHtmlForm : function(editPanel, params){
            //questa funzione astratta appende l'html del form nel pannello
            //a meno che non sia gia stato inserito nella vista....
            //e ne renderizza i paramtri
        },
        defineEditBoxForm: function(params){
            return '<div class="panel-editbox"></div>';
        },
        toggleLoader: function() {
            var self = this;
            var panel = self.widget;

            // Add loader to panel
            panel.addClass('panel-loader-active');

            // Remove loader after specified duration
            setTimeout(function() {
                panel.removeClass('panel-loader-active');
            }, 650);

        }


    });

    var DashboardWidget = FenealWidget.extend({
        ctor:function(widgetName, widgetParams){
            DashboardWidget.super.ctor.call(this, widgetName, widgetParams, "dashboard");

            this.anno = this.findProperty("year");
            this.provincia= this.findProperty("province");
            this.ente= this.findProperty("ente");
        },
        findProperty: function(property){
            var self = this;
            var value = "";
            $.each(self.widgetEditParamObjects, function(index, elem){
                if (elem.name == property){
                    value = elem.value;
                    return false;
                }

            });
            
            return value;
        },
        tryToextractAnnoAndProvincia: function(){
            //qui ho un oggetto di questo tipo
            var self = this;
            var params = self.widgetEditParamObjects;

            // var params = [
            //     {
            //         name: prova,
            //         value:pippo
            //     },
            //     {
            //         name: prova1,
            //         value:pippo1
            //     }
            // ];

            //ciclo su tutti gli oggetti dell'array e cerco una proprietà denominata provincia e una denominata anno
            $.each(params, function(index, elem){
                if (elem.name == "province"){
                    self.provincia = elem.value;
                }
                if (elem.name == "year"){
                    self.anno = elem.value;
                }
            });
        },
        init: function(){
            DashboardWidget.super.init.call(this);


            //qui posso recuperare tutti i dati di cui ho bisogno per i grafici....
            var self = this;
            self.prepareWidget();

        },
        prepareWidget: function(){
            //metodo che deve esssere sovrascritto....

        }


    });



    var FirmWidget = FenealWidget.extend({
        ctor:function(widgetName, widgetParams, firmId){
            FirmWidget.super.ctor.call(this, widgetName, widgetParams, "azienda");

            if (!firmId)
                throw "Id azienda non definito per il widget " + widgetName;

            this.firmId = firmId;

        },
        init: function(){
            FirmWidget.super.init.call(this);


            //qui posso recuperare tutti i dati di cui ho bisogno per i grafici....
            var self = this;
            self.prepareWidget(self.firmId);

        },
        prepareWidget: function(firmId){
            //metodo che deve esssere sovrascritto....
        }


    });

    var WorkerWidget = FenealWidget.extend({
        ctor:function(widgetName, widgetParams, workerId){
            WorkerWidget.super.ctor.call(this, widgetName, widgetParams, "lavoratore");

            if (!workerId)
                throw "Id lavoratore non definito per il widget " + widgetName;

            this.workerId = workerId;

        },
        init: function(){
            WorkerWidget.super.init.call(this);


            //qui posso recuperare tutti i dati di cui ho bisogno per i grafici....
            var self = this;
            self.prepareWidget(self.workerId);

        },
        prepareWidget: function(workerId){
            //metodo che deve esssere sovrascritto....
        }


    });




    function serviceChooseWidget(target, element){

        var toActive = true;

        if(!element.is(':checked')){
            toActive = false;
        }

        var contextChoose = $(".contextChoose").find(".active").find("a").text();

        var service = new model.AjaxService();
        service.set("url", BASE + "options/showwidget");
        service.set("method", "POST");
        service.set("data", {name: target, toActive: toActive, context: contextChoose});
        service.on("load", function (resp) {
            $.notify.success("Operazione completata");
        });
        service.on("error", function (e) {
            alert(e);
        });
        service.load();

    }

    exports.WidgetManager = WidgetManager;
    exports.DashboardWidget = DashboardWidget;
    exports.WorkerWidget = WorkerWidget;
    exports.FirmWidget = FirmWidget;

    return exports;


});