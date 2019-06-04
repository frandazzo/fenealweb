/**
 * Created by fgran on 07/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts"], function(core, fmodel, fviews, ui, widgets, plugins, webparts) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        executeReport: function(reportData){
            var route = BASE + "executeimportdeleghe" ;

            var svc =  this.__createService(true, route, reportData);
            return svc;
        },
        saveDelega: function(delega){
            var route = BASE + "delega" ;

            return this.__createService(true, route, delega);
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
    
    var DelegheHomeRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, workerId){
            DelegheHomeRemoteView.super.ctor.call(this, service);

            var self = this;
            this.workerId = workerId;

            this.on("load", function(){

               // alert("data loaded");
                //qui inserisco tutto il codice di inizializzazione della vista
                self.createToolbar();
                self.createBreadcrumbs();

            });

        },
        onServiceLoad: function(html) {
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
                {
                    text: "Nuova delega",
                    command: function() {

                        ui.Navigation.instance().navigate("editdelega", "index", {
                            fs: this.fullScreenForm,
                            workerId : self.workerId
                        });
                    },
                    icon: "plus"
                },
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
                    label: "Ricerca lavoratore",
                    //vado alla ricerca dei lavoratori
                    href: ui.Navigation.instance().navigateUrl("searchworkers", "index", {})
                },
                {
                    label: "Anagrafica " + localStorage.getItem("workerName"),
                    href: ui.Navigation.instance().navigateUrl("summaryworker", "index", {
                        id: self.workerId
                    })
                },
                {
                    label: "Deleghe"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        }

    });

    var EditDelegaAppView = fviews.FormAppView.extend({
        ctor: function(formService, delegaId, workerId) {
            EditDelegaAppView.super.ctor.call(this, formService);
            

            var self = this;


            self.delegaId = delegaId;
            self.workerId = workerId;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                if ( !self.delegaId){
                    //gestisco le scelte solo se non sto creando  una delega
                    self.togglePariethicVisibility($("select[name=sector]").val());

                    //inizializzazione del form
                    $("select[name=sector]").on("change", function() {
                        var selectedSector = $(this).val();
                        self.togglePariethicVisibility(selectedSector);
                    })
                }

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

                if (errors.errors && errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }


                var factory = new RepositoryServiceFactory();
                var svc = factory.saveDelega(data);


                svc.on("load", function(response){
                    //imposto l'handler per la navigazione verso l'utente selezionato
                    $.loader.hide({parent:'body'});
                    ui.Navigation.instance().navigate("deleghehome", "index", {
                        workerId: response.workerId
                    })
                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
                });

                svc.load();
                $.loader.show({parent:'body'});



            });



        },
        __constructArrayOfValues: function (data) {
            var result = [];

            $.each(data, function (index, elem) {
                result.push(elem.value);
            });

            return result;
        },
        togglePariethicVisibility: function(selectedSector) {
            var pariethicDiv =  $("div[data-property=paritethic]");
            var workerCompany = $("div[data-property=workerCompany]");
            var firstDelegaEdileCompany = $("div[data-property=firstAziendaEdile]");
            //solo se si sceglie il settore "edile" si può specificare l'ente
            if (selectedSector && selectedSector == "EDILE") {
                $(pariethicDiv).show();
                $(workerCompany).hide();
                $(firstDelegaEdileCompany).show();
            } else {
                $(pariethicDiv).hide();
                $(workerCompany).show();
                $(firstDelegaEdileCompany).hide();
                // $(pariethicDiv).find("input[name=paritethic]").val("");
                // $(pariethicDiv).find("span.mainText").text("NA")
            }

        },
        validate: function(data){
            
            var result = {};
            result.errors = [];



            if (data.sector == "IMPIANTI FISSI")
                if (!data.workerCompany )
                    result.errors.push(
                        {
                            property: "workerCompany",
                            message: "Azienda mancante"
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
        close: function(){
            var self = this;
            if (self.workerId){
                //se sto in modifica vado alla anagrafica dell'utente
                ui.Navigation.instance().navigate("deleghehome", "index", {
                    workerId:self.workerId
                })
            }else{
                //vado alla ricerca dell'utente
                ui.Navigation.instance().navigate("searchworkers", "index", {

                });

            }
        },
        getBreadcrumbItems: function() {
            var self = this;
            var title = "Crea delega";
            if (self.delegaId)
                title = "Modifica delega";
            return [
                {
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Ricerca lavoratore",
                    //vado alla ricerca dei lavoratori
                    href: ui.Navigation.instance().navigateUrl("searchworkers", "index", {})
                },
                {
                    label: "Anagrafica " + localStorage.getItem("workerName"),
                    href: ui.Navigation.instance().navigateUrl("summaryworker", "index", {
                        id:self.workerId
                    })
                },
                {
                    label: "Lista deleghe",
                    href: ui.Navigation.instance().navigateUrl("deleghehome", "index", {
                        workerId:self.workerId
                    })
                },
                {
                    label: title
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;

            //Cambieranno in base allo stato
            return [
                {
                    text: "Indietro",
                    command: function() {
                        //Pezza momentanea: il close non va
                        ui.Navigation.instance().navigate("deleghehome", "index", {
                            workerId:self.workerId
                        })

                       
                    },
                    icon: "arrow-left"
                },
                {
                    text: "Salva",
                    command: function() {

                        self.formView.invoke("submit")
                    },
                    icon: "save"
                }

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

    var ImportaDelegheAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ImportaDelegheAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

            });

            self.formView.on("submit", function(){

                var data = self.normalizeSubmitResult(self.formView.form);




                var factory = new RepositoryServiceFactory();
                var svc = factory.executeReport(data);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    var href = BASE + response;
                    var dialog = $("<a href='" +  href + "'>"+ "Scarica elaborazione" + "</a>").modalDialog({

                        autoOpen: true,
                        title: "",
                        destroyOnClose: true,
                        // height: 250,
                        // width: 400,

                    });

                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    var dialog = $("<p>"+ error + "</p>").modalDialog({

                        autoOpen: true,
                        title: "",
                        destroyOnClose: true,
                        // height: 250,
                        // width: 400,
                    });
                    //setTimeout(function(){location.reload();}, 5000);
                });

                svc.load();
                $.loader.show({parent:'body'});



            });

            self.formView.form.on("cancel", function() {
                self.close();
            });



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
        submit: function(e){

        },
        close: function(){
            alert("close");
        },
        getBreadcrumbItems: function() {
            var self = this;
            return [
                {
                    pageTitle: "Deleghe"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Importazione deleghe Bari"
                    //href: ui.Navigation.instance().navigateUrl("editworker", "index", {})
                }
            ];
        },
        getToolbarButtons: function() {
            var self = this;

            return [
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

    exports.ImportaDelegheAppView = ImportaDelegheAppView;
    exports.DelegheHomeRemoteView = DelegheHomeRemoteView;
    exports.EditDelegaAppView = EditDelegaAppView;
    return exports;

});
