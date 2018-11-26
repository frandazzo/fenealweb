/**
 * Created by fgran on 13/12/2017.
 */
/**
 * Created by fgran on 07/04/2016.
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
        saveWorker: function(worker){
            var route = BASE + "worker" ;

            return this.__createService(true, route, worker);
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

    var RiepilogoRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service){
            RiepilogoRemoteView.super.ctor.call(this, service);

            var self = this;


            this.on("load", function(){

                //alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista



                self.createToolbar();
                self.createBreadcrumbs();

            });

        },


        onServiceLoad: function(html) {
            var self = this;
            $.loader.hide({ parent: this.container });

            this.content = _E("div").html(html);
            this.container.empty().append(this.content);


            this.invoke("load");

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
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


        getToolbarButtons: function() {
            var self = this;

            return [

            ];

        },
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
                    label: "Riepilogo iscritti",


                }
            ];
        }

    });


    var PivotRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service){
            PivotRemoteView.super.ctor.call(this, service);

            var self = this;


            this.on("load", function(){

                //alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista



                self.createToolbar();
                self.createBreadcrumbs();

            });

        },


        onServiceLoad: function(html) {
            var self = this;
            $.loader.hide({ parent: this.container });

            this.content = _E("div").html(html);
            this.container.empty().append(this.content);


            this.invoke("load");

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
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


        getToolbarButtons: function() {
            var self = this;

            return [

            ];

        },
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
                    label: "Analisi iscritti",


                }
            ];
        }

    });

    var AnalisiIscrittiCompletiRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service){
            AnalisiIscrittiCompletiRemoteView.super.ctor.call(this, service);

            var self = this;


            this.on("load", function(){

                //alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista



                self.createToolbar();
                self.createBreadcrumbs();

            });

        },


        onServiceLoad: function(html) {
            var self = this;
            $.loader.hide({ parent: this.container });

            this.content = _E("div").html(html);
            this.container.empty().append(this.content);


            this.invoke("load");

        },
        createToolbar: function() {
            var buttons = this.getToolbarButtons();

            var $t = $("#toolbar");
            if(!$t.toolbar("isToolbar")) {
                $t.toolbar();
            }

            $t.toolbar("clear");
            var size = buttons.length;
            for(var i = 0; i < size; i++) {
                var button = buttons[i];
                $t.toolbar("add", button);
            }
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


        getToolbarButtons: function() {
            var self = this;

            return [

            ];

        },
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
                    label: "Analisi iscritti completi",


                }
            ];
        }

    });





    exports.RiepilogoRemoteView = RiepilogoRemoteView;
    exports.PivotRemoteView = PivotRemoteView;
    exports.AnalisiIscrittiCompletiRemoteView = AnalisiIscrittiCompletiRemoteView;

    return exports;

});