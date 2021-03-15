/**
 * Created by felicegramegna on 22/02/2021.
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
        saveFirmRsu: function(worker){
            var route = BASE + "firmrsu" ;

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

    var SearchFirmRsuViewGridView = fviews.GridAppView.extend({
        ctor: function(gridService) {
            SearchFirmRsuViewGridView.super.ctor.call(this, gridService);

            this.geoUtils = new geoUtils.GeoUtils();
            var self = this;

            self.on("complete", function(){
                self.get("grid").showSearchForm();
                $(".panel-title").text("Ricerca aziende RSU");


                var nationalityItalianId = 100;
                // Carico la lista delle province italiane
                self.geoUtils.loadProvinces(nationalityItalianId, "", $('select[name="province"]'));

                //qui attacco levento on change della select delle province
                $('select[name="province"]').change(function(){

                    var selectedVal = $(this).val();
                    //carico la lista delle città
                    if (selectedVal){
                        self.geoUtils.loadCities(selectedVal, "", $('select[name="city"]'));

                    }
                    else
                        $('select[name="city"]').empty().append("<option selected='selected' value=''>Select</option>");
                });

            })

        },
        edit: function(id){
            //alert("edit discussion number :" + id);
            ui.Navigation.instance().navigate("summaryfirmrsu", "index", {
                fs: this.fullScreenForm,
                id: id

            });
        },
        getToolbarButtons: function() {
            var self = this;
            var buttons = [];
            buttons.push(
                {
                    text: "Crea nuova azienda RSU",
                    command: function () {
                        ui.Navigation.instance().navigate("editfirmrsu", "index", {
                            fs: this.fullScreenForm

                        });
                    },
                    icon: "plus"
                }
            );
            // buttons.push(
            //     {
            //         text: msg.TOOLBAR_REFRESH,
            //         command: function() {
            //             self.gridService.reload();
            //         },
            //         icon: "refresh"
            //     }
            // );

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
                    label: "Ricerca nazionale aziende RSU"
                }
            ];
        }

    });

    var EditAziendaRsuAppView = fviews.FormAppView.extend({
        ctor: function(formService, firmId) {
            EditAziendaRsuAppView.super.ctor.call(this, formService);

            this.geoUtils = new geoUtils.GeoUtils();
            var self = this;
            self.firmId = firmId;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                var nationalityItalianId = 100;

                var provinceVal = $('select[name="province"]').data("value");

                // Carico la lista delle province
                self.geoUtils.loadProvinces(nationalityItalianId, provinceVal, $('select[name="province"]'));

                //qui attacco levento on change della select delle province
                $('select[name="province"]').change(function(){

                    var selectedVal = $(this).val();

                    //carico la lista delle città
                    if (selectedVal){
                        self.geoUtils.loadCities(selectedVal, "", $('select[name="city"]'));
                    }
                    else
                        $('select[name="city"]').empty().append("<option selected='selected' value=''>Select</option>");
                });
                if(self.firmId)
                    self.geoUtils.loadCities($('select[name="province"]').data("value"), $('select[name="city"]').data("value"), $('select[name="city"]'));


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
                var svc = factory.saveFirmRsu(data);


                svc.on("load", function(response){
                    //imposto l'handler per la navigazione verso l'utente selezionato
                    $.loader.hide({parent:'body'});
                    ui.Navigation.instance().navigate("summaryfirmrsu", "index", {
                        id:response
                    })
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
                        message: "Ragione sociale mancante"
                    }
                );

            if (!data.cf)
                result.errors.push(
                    {
                        property: "cf",
                        message: "Cod.Fiscale obbligatorio"
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
                ui.Navigation.instance().navigate("summaryfirmrsu", "index", {
                    id:self.firmId
                })
            }else{
                //vado alla ricerca dell'utente
                ui.Navigation.instance().navigate("searchfirmsrsu", "index", {

                });

            }
        },
        getBreadcrumbItems: function() {
            var self = this;
            var title = "Crea anagrafica azienda RSU";
            if (self.firmId)
                title = "Modfica anagrafica  azienda RSU";
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

    var FirmRsuSummaryRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, firmId, firmDescription){
            FirmRsuSummaryRemoteView.super.ctor.call(this, service);

            var self = this;
            this.firmId = firmId;

            this.on("load", function(){

                //alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();

                $("button.sedi-rsu").click(function(){
                    ui.Navigation.instance().navigate("sedersu", "index", {
                        firmId: self.firmId
                    })
                });

                $("button.attr-rsu").click(function(){
                    ui.Navigation.instance().navigate("calcolaattribuzionersu", "index", {
                        firmId: self.firmId
                    })
                });

                $("button.verb-vot").click(function(){
                    ui.Navigation.instance().navigate("verbvote", "list", {
                        firmId: self.firmId,
                        e : "verbvot"
                    })
                });

            });

        },
        onServiceLoad: function(html) {
            var self = this;
            $.loader.hide({ parent: this.container });
            this.content = _E("div").html(html);
            this.container.empty().append(this.content);
            if (!self.firmId)
                self.firmId = self.content.find('#id').val();
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
                {
                    text: "Crea nuova anagrafica",
                    command: function() {

                        ui.Navigation.instance().navigate("editfirmrsu", "index", {
                            fs: this.fullScreenForm
                        });
                    },
                    icon: "pencil"
                },
                {
                    text: "Modifica",
                    command: function() {

                        ui.Navigation.instance().navigate("editfirmrsu", "index", {
                            fs: this.fullScreenForm,
                            id: self.firmId
                        });
                    },
                    icon: "pencil"
                },
                {
                    text: "Elimina",
                    command: function() {


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
                                        svc.url = BASE + "firmrsu/" + self.firmId;
                                        svc.set("method", "DELETE");
                                        svc.on({
                                            load: function(response){

                                                $(dialog).modalDialog("close");
                                                $.notify.success("Operazione completata");

                                                //ritonrno alla modalità di ricerca
                                                ui.Navigation.instance().navigate("searchfirmsrsu", "index", {
                                                    fs: this.fullScreenForm
                                                });
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
                    },
                    icon: "a glyphicons glyphicons-delete"
                }
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
                    label: "Ricerca aziende",
                    //vado alla ricerca delle aziende
                    href: ui.Navigation.instance().navigateUrl("searchfirmsrsu", "index", {})
                },
                {
                    label: "Anagrafica azienda"
                }
            ];
        }

    });

    exports.FirmRsuSummaryRemoteView = FirmRsuSummaryRemoteView;
    exports.EditAziendaRsuAppView = EditAziendaRsuAppView;
    exports.SearchFirmRsuViewGridView = SearchFirmRsuViewGridView;
    return exports;

});
