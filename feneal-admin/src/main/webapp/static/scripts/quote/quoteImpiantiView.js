/**
 * Created by angelo on 15/04/2016.
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
        quoteImpiantiProceed: function(data){
            var route = BASE + "quoteimpiantifissi/proceed";

            var svc =  this.__createService(true, route, data);
            return svc;
        },
        renewQuoteImpiantiProceed: function(data){
            var route = BASE + "renewquoteimpiantifissi/proceed";

            var svc =  this.__createService(true, route, data);
            return svc;
        },
        quoteManualiProceed: function(data){
            var route = BASE + "quotemanuali/proceed";

            var svc =  this.__createService(true, route, data);
            return svc;
        },
        quoteBreviManoProceed: function(data){
            var route = BASE + "quotebrevimano/proceed";

            var svc =  this.__createService(true, route, data);
            return svc;
        },
        sendDataForCreationQuote: function(data){
            var route = BASE + "quoteimpiantifissi/createquote" ;

            var svc =  this.__createService(true, route, data);
            return svc;
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

    var QuoteImpiantiAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            QuoteImpiantiAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "290px");

                // Di default seleziono l'anno corrente
                var currentYear = new Date().getFullYear();
                $("select[name=competenceYear]").val(currentYear);
            });

            self.formView.on("submit", function(){
                self.formView.form.resetValidation();
                var azienda = $("input[name=firm]").val();

                var errors = self.validateFormQuote(azienda);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }

                // Se la validazione è OK  allora procedo
                var searchData = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.quoteImpiantiProceed(searchData);

                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    $("#resultsContainer").find("tr.data-row").remove();

                    $("#resultsContainer").show();
                    if (response.length == 0) {
                        $(".quote-table").append("<tr class='data-row'><td colspan='3'>Nessun dato trovato</td></tr>");
                        $(".send-data-btn").hide();
                    } else {
                        $(".proceed-btn").remove();
                        $(".cancel-form-btn").show().click(function() {
                            self.formView.formService.load();
                        });

                        $(".send-data-btn").show();

                        $(".send-data-btn").click(function() {
                            $.loader.show({parent:'body'});
                            if (searchData.amount == "")
                                searchData.amount = 0;

                            var data = {};
                            data.params = searchData;

                            quoteArr = [];
                            $.each($(".quote-table .data-row"), function(i, o) {
                                var row = {};
                                row.lavoratoreId = $(o).data("workerid");
                                row.importo = $(o).find(".workerAmount").find('input').val();
                                row.contratto = $(o).find("select[name='contratto']").val();

                                quoteArr.push(row);
                            });

                            data.quoteRows = quoteArr;

                            var svcGen = factory.sendDataForCreationQuote(data);

                            svcGen.on("load", function (response) {

                                $.loader.hide({parent: 'body'});
                                if (response){
                                    $.notify.error(response);
                                }else{
                                    $.notify.success("Operazione completata");
                                    window.location.href = BASE;
                                }


                            });

                            svcGen.on("error", function (error) {
                                $.loader.hide({parent: 'body'});
                                $.notify.error(error);
                            });

                            svcGen.load();
                        });

                        var contratto = $("select[name='contract']").val();
                        var optionsContractSelect = $.map($('select[name=contract] option'), function(e) { return e.value; });

                        $.each(response, function (i, o) {
                            var row = "<tr class='data-row' style='border: 1px solid black;' data-workerid='" + o.lavoratoreId + "'>" +
                                "<td class='td-bordered workerName'>" + o.lavoratoreNomeCompleto + "</td>" +
                                "<td class='td-bordered workerAmount'><input type='text' name='importo' data-component='text' class='field gui-input' value='" + o.importo + "'></td>" +
                                "<td class='td-bordered contractSel'><label class='field select'><select name='contratto'></select><i class='arrow double'></i></label></td>" +
                                "</tr>";

                            $(".quote-table").append(row);
                            var selectObj = $(row).find("select[name='contratto']");
                        });

                        $.each(optionsContractSelect, function(i,o) {
                            $("select[name=contratto]").append($("<option>", {
                                value: o,
                                text: o
                            }));
                        });
                        $("select[name=contratto]").val(contratto);
                    }

                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
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

        validateFormQuote: function(azienda) {
            var self = this;
            var result = {};
            result.errors = [];

            if (azienda != null && azienda == "")
                result.errors.push(
                    {
                        property: "firm",
                        message: "Azienda mancante"
                    }
                );


            return result;
        },

        submit: function(e){

        },
        close: function(){
            alert("close");
        }

    });




    var RenewQuoteImpiantiAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            RenewQuoteImpiantiAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "320px");

                $(".panel-title").text("Quote impianti fissi");
                var currentYear = new Date().getFullYear();
                $("select[name=competenceYear]").val(currentYear);

            });

            self.formView.on("submit", function(){

                var searchData = self.normalizeSubmitResult(self.formView.form);
                self.formView.form.resetValidation();


                var errors = self.validateFormQuote(searchData);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }




                var factory = new RepositoryServiceFactory();
                var svc = factory.renewQuoteImpiantiProceed(searchData);

                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    $("#resultsContainer").find("tr.data-row").remove();

                    $("#resultsContainer").show();
                    if (response.length == 0) {
                        $(".quote-table").append("<tr class='data-row'><td colspan='3'>Nessun dato trovato</td></tr>");
                        $(".send-data-btn").hide();
                    } else {
                        $(".proceed-btn").remove();
                        $(".cancel-form-btn").show().click(function() {
                            self.formView.formService.load();
                            $('form').show();
                        });

                        $(".send-data-btn").show();

                        $(".send-data-btn").click(function() {
                            $.loader.show({parent:'body'});
                            if (searchData.amount == "")
                                searchData.amount = 0;

                            var data = {};
                            data.header = searchData;

                            quoteArr = [];
                            $.each($(".quote-table .data-row"), function(i, o) {
                                var row = {};
                                row.lavoratoreId = $(o).data("workerid");
                                row.importo = $(o).find(".workerAmount").find('input').val();
                                row.contratto = $(o).find("select[name='contratto']").val();

                                quoteArr.push(row);
                            });

                            data.quoteRows = quoteArr;

                            var svcGen = factory.sendDataForCreationQuote(data);

                            svcGen.on("load", function (response) {

                                $.loader.hide({parent: 'body'});
                                if (response){
                                    $.notify.error(response);
                                }else{
                                    $.notify.success("Operazione completata");
                                    ui.Navigation.instance().navigate("quoteassociative", "index");
                                }


                            });

                            svcGen.on("error", function (error) {
                                $.loader.hide({parent: 'body'});
                                $.notify.error(error);
                            });

                            svcGen.load();
                        });

                        var contratto = $("select[name='contract']").val();
                        var optionsContractSelect = $.map($('select[name=contract] option'), function(e) { return e.value; });

                        $.each(response, function (i, o) {
                            var row = "<tr class='data-row' style='border: 1px solid black;' data-workerid='" + o.lavoratoreId + "'>" +
                                "<td class='td-bordered workerName'>" + o.lavoratoreNomeCompleto + "</td>" +
                                "<td class='td-bordered workerAmount'><input type='number' name='importo' style='width: 50%' value='" + o.importo + "'></td>" +
                                "<td class='td-bordered contractSel'><select name='contratto' style='width: 80%'></td>" +
                                "</tr>";

                            $(".quote-table").append(row);
                            var selectObj = $(row).find("select[name='contratto']");
                        });

                        $.each(optionsContractSelect, function(i,o) {
                            $("select[name=contratto]").append($("<option>", {
                                value: o,
                                text: o
                            }));
                        });
                        $("select[name=contratto]").val(contratto);



                        $('form').hide();
                    }

                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
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

        validateFormQuote: function(data) {
            var result = {};
            result.errors = [];


            // //devo valorizzare le date di inizio e fine
            // var dateRegExp = /^\d{2}[/]\d{2}[/]\d{4}$/
            // if (!data.dataInizio.match(dateRegExp)){
            //     result.errors.push(
            //         {
            //             property: "dataInizio",
            //             message: "Data inizio non corretta (inserire data in formato dd/MM/yyyy)"
            //         }
            //     );
            // }
            // if (!data.dataFine.match(dateRegExp)){
            //     result.errors.push(
            //         {
            //             property: "dataFine",
            //             message: "Data fine non corretta (inserire data in formato dd/MM/yyyy)"
            //         }
            //     );
            // }

            return result;
        },

        submit: function(e){

        },
        close: function(){
            alert("close");
        }

    });




    var QuoteManualiAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            QuoteManualiAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "320px");

                //recupro il valire del settore dall'input
                var sett = $('select[name="settore"]').val();
                var enteRef = $('select[name="ente"]').closest('.row');
                var firmComponent = $("div[data-property=firm][data-component=field]");
                //recupero il riferimeento alla row dell'ente che deve esserree visualizzata
                //solo se il settore è edile
                if (sett != "EDILE"){
                    enteRef.hide();
                    firmComponent.show();
                }else{
                    enteRef.show();
                    firmComponent.show();
                }

                $('select[name="settore"]').change(function(){

                    var sett = $(this).val();
                    if (sett != "EDILE"){
                        enteRef.hide();
                        firmComponent.show();
                    }else{
                        enteRef.show();
                        firmComponent.show();
                    }
                });
            });

            self.formView.on("submit", function(){

                var searchData = self.normalizeSubmitResult(self.formView.form);
                self.formView.form.resetValidation();


                var errors = self.validateFormQuote(searchData);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }




                var factory = new RepositoryServiceFactory();
                var svc = factory.quoteManualiProceed(searchData);

                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});

                    ui.Navigation.instance().navigate("dettaglioquote", "index",{
                        id:response
                    });


                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
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

        validateFormQuote: function(data) {
            var result = {};
            result.errors = [];

            //devo verificare se il settore è un settore edile oppure un qualunque altro settore
            //se si tratta di settore edile devo valorizzare l'ente insieme con l'azienda
            if (data.settore == "EDILE"){
                if (data.ente == ""){
                    result.errors.push(
                        {
                            property: "ente",
                            message: "Ente bilaterale mancante"
                        }
                    );
                }

                if (data.firm == ""){
                    result.errors.push(
                        {
                            property: "firm",
                            message: "Azienda mancante"
                        }
                    );
                }

            }else if (data.settore == "INPS"){
                //non richiedo azienda...
            }else{
                if (data.firm == ""){
                    result.errors.push(
                        {
                            property: "firm",
                            message: "Azienda mancante"
                        }
                    );
                }
            }

            //devo valorizzare le date di inizio e fine
            var dateRegExp = /^\d{2}[/]\d{2}[/]\d{4}$/
            if (!data.dataInizio.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataInizio",
                        message: "Data inizio non corretta (inserire data in formato dd/MM/yyyy)"
                    }
                );
            }
            if (!data.dataFine.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataFine",
                        message: "Data fine non corretta (inserire data in formato dd/MM/yyyy)"
                    }
                );
            }

            return result;
        },

        submit: function(e){

        },
        close: function(){
            alert("close");
        }

    });



    var QuoteBreviManoAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            QuoteBreviManoAppView.super.ctor.call(this, formService);

            var self = this;

            self.formView.on("load", function(){

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "320px");

                //recupro il valire del settore dall'input
                var sett = $('select[name="settore"]').val();
                var enteRef = $('select[name="ente"]').closest('.row');
                var firmComponent = $("div[data-property=firm][data-component=field]");
                //recupero il riferimeento alla row dell'ente che deve esserree visualizzata
                //solo se il settore è edile
                if (sett != "EDILE"){
                    enteRef.hide();
                    firmComponent.show();
                }else{
                    enteRef.show();
                    firmComponent.show();
                }

                $('select[name="settore"]').change(function(){

                    var sett = $(this).val();
                    if (sett != "EDILE"){
                        enteRef.hide();
                        firmComponent.show();
                    }else{
                        enteRef.show();
                        firmComponent.show();
                    }
                });
            });

            self.formView.on("submit", function(){

                var searchData = self.normalizeSubmitResult(self.formView.form);
                self.formView.form.resetValidation();


                var errors = self.validateFormQuote(searchData);

                if (errors.errors.length){
                    self.formView.form.handleValidationErrors(errors);
                    return;
                }




                var factory = new RepositoryServiceFactory();
                var svc = factory.quoteBreviManoProceed(searchData);

                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});
                    ui.Navigation.instance().navigate("dettaglioquote", "index",{
                        id:response
                    });



                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
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

        validateFormQuote: function(data) {
            var result = {};
            result.errors = [];

            //devo verificare se il settore è un settore edile oppure un qualunque altro settore
            //se si tratta di settore edile devo valorizzare l'ente insieme con l'azienda

            if (!data.worker){
                result.errors.push(
                    {
                        property: "worker",
                        message: "Lavoratore mancante"
                    }
                );
            }


            if (data.settore == "EDILE"){
                if (data.ente == ""){
                    result.errors.push(
                        {
                            property: "ente",
                            message: "Ente bilaterale mancante"
                        }
                    );
                }

                if (data.firm == ""){
                    result.errors.push(
                        {
                            property: "firm",
                            message: "Azienda mancante"
                        }
                    );
                }

            }else if (data.settore == "INPS"){
                //non richiedo azienda...
            }else{
                if (data.firm == ""){
                    result.errors.push(
                        {
                            property: "firm",
                            message: "Azienda mancante"
                        }
                    );
                }
            }

            //devo valorizzare le date di inizio e fine
            var dateRegExp = /^\d{2}[/]\d{2}[/]\d{4}$/
            if (!data.dataInizio.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataInizio",
                        message: "Data inizio non corretta (inserire data in formato dd/MM/yyyy)"
                    }
                );
            }
            if (!data.dataFine.match(dateRegExp)){
                result.errors.push(
                    {
                        property: "dataFine",
                        message: "Data fine non corretta (inserire data in formato dd/MM/yyyy)"
                    }
                );
            }

            return result;
        },

        submit: function(e){

        },
        close: function(){
            alert("close");
        }

    });


    exports.QuoteImpiantiAppView = QuoteImpiantiAppView;
    exports.RenewQuoteImpiantiAppView = RenewQuoteImpiantiAppView;
    exports.QuoteBreviManoAppView = QuoteBreviManoAppView;
    exports.QuoteManualiAppView = QuoteManualiAppView;

    return exports;

});