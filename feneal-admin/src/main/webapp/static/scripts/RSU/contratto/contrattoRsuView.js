/**
 * Created by felicegramegna on 09/03/2021.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "geoUtils"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        saveContractRsu: function(worker){
            var route = BASE + "contractrsu" ;
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

    var ContrattoRsuViewGridView = fviews.GridAppView.extend({
        ctor: function(gridService) {
            ContrattoRsuViewGridView.super.ctor.call(this, gridService);

            this.geoUtils = new geoUtils.GeoUtils();
            var self = this;

            self.on("complete", function(){
                self.get("grid").showSearchForm();
                $(".panel-title").text("Lista contratto RSU");

            })

        },
        edit: function(id){
            //alert("edit discussion number :" + id);
            ui.Navigation.instance().navigate("editcontrattorsu", "index", {
                fs: this.fullScreenForm,
                id: id
            });
        },
        getToolbarButtons: function() {
            var self = this;
            var buttons = [];
            buttons.push(
                {
                    text: "Crea nuovo contratto RSU",
                    command: function () {
                        ui.Navigation.instance().navigate("editcontrattorsu", "index", {
                            fs: this.fullScreenForm

                        });
                    },
                    icon: "plus"
                }
            );
            buttons.push(
                {
                    text: msg.TOOLBAR_REFRESH,
                    command: function() {
                        self.gridService.reload();
                    },
                    icon: "refresh"
                }
            );

            buttons.push(
                {
                    text: msg.TOOLBAR_ACTIONS,
                    group: 'selected',
                    type: "menu",
                    alignRight: true,
                    items: [
                        {
                            label: msg.TOOLBAR_SELECT_ALL,
                            command: function() {
                                self.grid.selectAll();
                            }
                        },
                        {
                            label: msg.TOOLBAR_UNSELECT_ALL,
                            command: function() {
                                self.grid.unselectAll();
                            }
                        },
                        { separator: true },
                        {
                            label: msg.TOOLBAR_DELETE_SELECTION,
                            command: function() {
                                var ids = self.grid.getSelection();
                                if(ids.length == 0) {
                                    $.notify.warn(msg.MSG_PLEASE_SELECT_ROW);
                                } else {

                                    ids.forEach(function (item) {
                                        var svc = new fmodel.AjaxService();
                                        svc.url = BASE + "contractrsu/" + item;
                                        svc.set("method", "DELETE");
                                        svc.on({
                                            load: function(response){

                                                $.notify.success("Operazione completata");

                                                //ritonrno alla modalità di ricerca
                                                self.gridService.reload();
                                            },
                                            error: function (error){
                                                $.notify.error(error);
                                            }
                                        });
                                        svc.load();
                                    });


                                }
                            },
                            important: true
                        }
                    ],
                    icon: "asterisk"
                }
            );

            return buttons;
        },

        getBreadcrumbItems: function() {
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Lista contratti RSU"
                }
            ];
        }
    });

    var EditContrattoRsuAppView = fviews.FormAppView.extend({
        ctor: function(formService, contrId) {
            EditContrattoRsuAppView.super.ctor.call(this, formService);

            this.geoUtils = new geoUtils.GeoUtils();
            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();
            });

            self.formView.form.on("cancel", function() {
                self.close();
            });

            self.formView.on("submit", function(){
                //al click del pulsante submint rimuovo le validazioni
                self.formView.form.resetValidation();

                //recupero i dati normalizzati
                //questa è una versione un po più elaborata della semplice serializzazione del form perchè restituisce
                //le proprièta con lo stesso nome raggruppate...
                //ad esempio se ho tre valori category : 1 , category : 3, category :7 ricevero una sola porpietà category : [1,3,7]
                //nello stesso oggetto
                var data = self.normalizeSubmitResult(self.formView.form);
                var errors = self.validate(data);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }

                var factory = new RepositoryServiceFactory();
                var svc = factory.saveContractRsu(data);

                svc.on("load", function(response){
                    //imposto l'handler per la navigazione verso l'utente selezionato
                    $.loader.hide({parent:'body'});
                    ui.Navigation.instance().navigate("contrattorsu", "index", {});
                });
                svc.on("error", function(response){
                    $.loader.hide({parent:'body'});
                    $.notify.error(response);
                });

                svc.load();
                $.loader.show({parent:'body'});
            });
        },
        validate: function(data){
            var result = {};
            result.errors = [];


            if (!data.description)
                result.errors.push(
                    {
                        property: "description",
                        message: "Descrizione mancante"
                    }
                );

            if (!data.rsuMin || parseInt(data.rsuMin,10) < 1)
                result.errors.push(
                    {
                        property: "rsuMin",
                        message: "Rsu Min. non valido"
                    }
                );

            if (!data.rsuMax || parseInt(data.rsuMax,10) < parseInt(data.rsuMin,10))
                result.errors.push(
                    {
                        property: "rsuMax",
                        message: "Rsu Max. non valido"
                    }
                );

            if (parseInt(data.rsuMax,10) < 1)
                result.errors.push(
                    {
                        property: "rsuMax",
                        message: "Rsu Max. non valido"
                    }
                );


            return result;
        },
        submit: function(e){
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
                    propertyBuffer[propName] = new Array();
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
        close: function(){
            var self = this;
            if (self.firmId){
                //se sto in modifica vado alla anagrafica dell'utente
                ui.Navigation.instance().navigate("contrattorsu", "index", {});
            }else{
                //vado alla ricerca dell'utente
                ui.Navigation.instance().navigate("contrattorsu", "index", {});
            }
        },
        getBreadcrumbItems: function() {
            var self = this;
            var title = "Crea anagrafica contratto RSU";
            if (self.firmId)
                title = "Modfica anagrafica contratto RSU";
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: title
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;

            return [
                // {
                //     text: "Crea anagrafica",
                //     command: function() {
                //
                //         ui.Navigation.instance().navigate("editworker", "index", {
                //             fs: this.fullScreenForm,
                //         });
                //     },
                //     icon: "pencil"
                // }

            ];

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
        }
    });

    exports.EditContrattoRsuAppView = EditContrattoRsuAppView;
    exports.ContrattoRsuViewGridView = ContrattoRsuViewGridView;
    return exports;

});
