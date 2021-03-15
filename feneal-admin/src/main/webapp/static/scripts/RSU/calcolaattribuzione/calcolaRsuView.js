define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui"], function(core, fmodel, fviews,ui) {
    var exports = {};
    var ReportRsuHomeRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service,firmId){
            ReportRsuHomeRemoteView.super.ctor.call(this, service);

            var self = this;

            this.on("load", function(){


                $("#listepresentate").hide();
                $("#esitovotazione").hide();
                $("#proceduracompletata").hide();



                var container = document.getElementById("datigenerali");
                var formService = new fmodel.FormService();
                formService.set("method", "GET");
                formService.set("data", {});
                formService.set("url", BASE + "calcattrsu/datigenerali/"+firmId);


                //-------FORM DATI GENERALI------------
                var formView = new fviews.FormView(formService);
                formView.container = container;

                formView.on("render", function() {
                    $(".dati-generali-elezione").find(".panel-footer, .panel-heading").hide();
                    $(".content-header pb20").hide();
                    $(".panel-body").css("overflow", "hidden");
                });


                formView.on("load",function () {
                    if(firmId)
                        self.loadSediRsu(firmId,"", $('select[name="sedersu"]'));
                });

                formView.show();

                //---------------CLICK SU AVANTI GENRALI---------------------------------
                $("#avanti-datigenerali").click(function () {

                        var data = {
                            firmRsu: firmId,
                            sedeRsu: $('select[name="sedersu"]').val(),
                            anno: $('select[name="annofromYearReport"]').val()
                        };

                    //-----------CONTROLLO LA VALIDITà DEI DATI GENERALI----------
                    //-----------GRID LISTE---------------------------------------
                        var svcDatiGenerali = new fmodel.AjaxService();
                        svcDatiGenerali.set("method", "POST");
                        svcDatiGenerali.set("data", data);
                        svcDatiGenerali.set("url", BASE + "calcattrsu/datigenerali");

                        svcDatiGenerali.on("load", function(responseDatiGenerali){
                            $.loader.hide({parent:'body'});

                            var errors = self.validateDto(responseDatiGenerali);

                            if (errors.errors.length){
                                formView.form.handleValidationErrors(errors);
                                return;
                            }

                            $.notify.success("Operazione completata");

                            $("#avanti-datigenerali").hide();

                            //---------------MOSTRO LA GRIGLIA PER LA CREAZIONE DELLE LISTE---------------------------------

                            $("#listepresentate").show();

                            var grid = self.initGridListe(responseDatiGenerali,self,false);

                            //----------BOTTONE PER AGGIUNGERE UNA LISTA---------------------------
                            $("#add_list").click(function () {
                                var formService = new fmodel.FormService();
                                formService.set("method", "GET");
                                formService.set("data", {});
                                formService.set("url", BASE + "calcattrsu/createlista");

                                var container = $('<div class="create-list"></div>');

                                var formView = new fviews.FormView(formService);
                                formView.container = container;

                                formView.on("render", function() {
                                    $(".create-list").find(".panel-footer, .panel-heading").hide();
                                    $(".panel-body").css("overflow", "hidden");
                                });

                                formView.show();

                                var dialog = container.modalDialog({
                                    autoOpen: true,
                                    title: "Crea lista",
                                    destroyOnClose: true,
                                    height: 250,
                                    width: 550,
                                    buttons: {
                                        Aggiungi: {
                                            primary: true,
                                            command: function() {
                                                $.loader.show({parent:'body'});

                                                var nome = $(".create-list").find("input[name=name]").val();
                                                var firmataria = $(".create-list").find("input[name=firmataria]").is(":checked");

                                                if(nome == ""){
                                                    $.notify.warn("Il nome della lista non pu&ograve; essere nullo");
                                                    $.loader.hide({parent:'body'});
                                                    return;
                                                }

                                                var data = {
                                                    dto: responseDatiGenerali,
                                                    nome: nome,
                                                    firmataria: firmataria
                                                };

                                                var svcCreateLista = new fmodel.AjaxService();
                                                svcCreateLista.set("url", BASE + "calcattrsu/createlista");
                                                svcCreateLista.set("data", JSON.stringify(data));
                                                svcCreateLista.set("contentType", "application/json");
                                                svcCreateLista.set("method", "POST");

                                                svcCreateLista.on("load", function(responseAddList){
                                                    $.loader.hide({parent:'body'});



                                                    responseDatiGenerali.liste = responseAddList.liste;
                                                    responseDatiGenerali.validationError = responseAddList.validationError;
                                                    console.log(responseDatiGenerali);
                                                    var errors = self.validateDto(responseDatiGenerali);

                                                    if (errors.errors.length){
                                                        formView.form.handleValidationErrors(errors);
                                                        return;
                                                    }

                                                    $("#gridListe")
                                                        .dxDataGrid("instance")
                                                        .option("dataSource", responseAddList.liste);


                                                    dialog.modalDialog("close");
                                                    $.loader.hide({parent:'body'});
                                                });
                                                svcCreateLista.on("error", function(error){
                                                    $.loader.hide({parent:'body'});
                                                    $.notify.error(error);
                                                });

                                                svcCreateLista.load();
                                            }
                                        }
                                    }
                                });
                            });

                            //----------BOTTONE PER RIMUOVERE UNA LISTA-------------------------
                            /*--DISABLITATO-----*/$("#remove_list").click(function () {
                                    var selectedrows = grid.getSelectedRowsData();

                                    var filterExpression =  grid.getCombinedFilter(true);
                                    if(selectedrows.length) {
                                        var dataSource = new DevExpress.data.DataSource({
                                            filter:filterExpression,
                                            paginate: false,
                                            store: new DevExpress.data.ArrayStore({
                                                data: selectedrows,
                                                key: "name"
                                            })
                                        })
                                        dataSource.load().done(function(r){
                                            selectedrows = r;

                                        })
                                    }

                                    if (selectedrows.length == 0) {
                                        $.notify.error("Selezionare almeno un elemento");
                                        return false;
                                    }

                                    var dialog = $("<p>Sicuro di voler eliminare la lista selezionata?</p>").modalDialog({
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
                                                    var data = {
                                                        dto: response,
                                                        nome: selectedrows[0].name,
                                                        firmataria: selectedrows[0].firmataria
                                                    };

                                                    var svcDeleteLista = new fmodel.AjaxService();
                                                    svcDeleteLista.set("url", BASE + "calcattrsu/deletelista");
                                                    svcDeleteLista.set("data", JSON.stringify(data));
                                                    svcDeleteLista.set("contentType", "application/json");
                                                    svcDeleteLista.set("method", "POST");

                                                    svcDeleteLista.on("load", function (responseFromList) {
                                                        $.loader.hide({parent: 'body'});

                                                        this.saveResponse = responseFromList;

                                                        response.liste = responseFromList.liste;
                                                        response.validationError = responseFromList.validationError;
                                                        console.log(response);
                                                        var errors = self.validateDto(response);

                                                        if (errors.errors.length){
                                                            formView.form.handleValidationErrors(errors);
                                                            return;
                                                        }

                                                            $("#gridListe")
                                                                .dxDataGrid("instance")
                                                                .option("dataSource", responseFromList.liste);


                                                        dialog.modalDialog("close");
                                                        $.loader.hide({parent: 'body'});
                                                    });
                                                    svcDeleteLista.on("error", function (error) {
                                                        $.loader.hide({parent: 'body'});
                                                        $.notify.error(error);
                                                    });

                                                    svcDeleteLista.load();

                                                    $(dialog).modalDialog("close");
                                                }
                                            }
                                        }
                                    });

                                });

                            //----------BOTTONE PER MODIFICARE UNA LISTA------------------------
                            /*--DISABLITATO-----*/$("#edit_list").click(function () {

                                    var selectedrows = grid.getSelectedRowsData();

                                    var filterExpression =  grid.getCombinedFilter(true);
                                    if(selectedrows.length) {
                                        var dataSource = new DevExpress.data.DataSource({
                                            filter:filterExpression,
                                            paginate: false,
                                            store: new DevExpress.data.ArrayStore({
                                                data: selectedrows,
                                                key: "name"
                                            })
                                        })
                                        dataSource.load().done(function(r){
                                            selectedrows = r;

                                        })
                                    }

                                    if (selectedrows.length == 0) {
                                        $.notify.error("Selezionare almeno un elemento");
                                        return false;
                                    }


                                    var olddata = {
                                        nome: selectedrows[0].name,
                                        firmataria: selectedrows[0].firmataria
                                    };


                                    var formService = new fmodel.FormService();
                                    formService.set("method", "GET");
                                    formService.set("data", olddata);
                                    formService.set("url", BASE + "calcattrsu/editlista");

                                    var container = $('<div class="edit-list"></div>');

                                    var formView = new fviews.FormView(formService);
                                    formView.container = container;

                                    formView.on("render", function() {
                                        $(".edit-list").find(".panel-footer, .panel-heading").hide();
                                        $(".panel-body").css("overflow", "hidden");

                                        if(olddata.firmataria) {
                                            $(".edit-list").find("input[name=firmataria]").attr("checked","checked");
                                        }
                                    });


                                    formView.show();

                                    formView.on("load", function() {
                                        if(olddata.firmataria) {
                                            $(".edit-list").find("input[name=firmataria]").attr("checked","checked");
                                        }
                                    });

                                    if(olddata.firmataria) {
                                        $(".edit-list").find("input[name=firmataria]").attr("checked","checked");
                                    }

                                    var dialog = container.modalDialog({
                                        autoOpen: true,
                                        title: "Modifica lista",
                                        destroyOnClose: true,
                                        height: 250,
                                        width: 550,
                                        buttons: {
                                            Modifica: {
                                                primary: true,
                                                command: function() {
                                                    $.loader.show({parent:'body'});

                                                    var nome = $(".edit-list").find("input[name=name]").val();
                                                    var firmataria = $(".edit-list").find("input[name=firmataria]").is(":checked");

                                                    if(nome == ""){
                                                        $.notify.warn("Il nome della lista non pu&ograve; essere nullo");
                                                        $.loader.hide({parent:'body'});
                                                        return;
                                                    }

                                                    var data = {
                                                        dto: response,
                                                        nome: nome,
                                                        firmataria: firmataria,
                                                        oldnome: olddata.nome,
                                                        oldfirmataria: olddata.firmataria

                                                    };

                                                    var svcEditLista = new fmodel.AjaxService();
                                                    svcEditLista.set("url", BASE + "calcattrsu/editlista");
                                                    svcEditLista.set("data", JSON.stringify(data));
                                                    svcEditLista.set("contentType", "application/json");
                                                    svcEditLista.set("method", "POST");

                                                    svcEditLista.on("load", function(responseFromList){
                                                        $.loader.hide({parent:'body'});

                                                        response.liste = responseFromList.liste;
                                                        response.validationError = responseFromList.validationError;
                                                        console.log(response);
                                                        var errors = self.validateDto(response);

                                                        if (errors.errors.length){
                                                            formView.form.handleValidationErrors(errors);
                                                            return;
                                                        }
                                                            $("#gridListe")
                                                                .dxDataGrid("instance")
                                                                .option("dataSource", responseFromList.liste);

                                                        dialog.modalDialog("close");
                                                        $.loader.hide({parent:'body'});
                                                    });
                                                    svcEditLista.on("error", function(error){
                                                        $.loader.hide({parent:'body'});
                                                        $.notify.error(error);
                                                    });

                                                    svcEditLista.load();

                                                    $(dialog).modalDialog("close");
                                                }
                                            }
                                        }
                                    });
                                });


                            //--------------INVIO LE LISTE E CONTROLLO LA VALIDITà----------
                            $("#avanti-liste").off('click');
                            $("#avanti-liste").click(function () {
                                //--------CHIAMATA PER CONTROLLARE LA VALIDITà DELLA LISTA----------
                                //--------SE NON CI SONO ERRORI POSSO MOSTRARE IL FORM--------------
                                //--------E LA GRIGLIA PER INSERIRE I DATI PER CALCOLARE L'ESITO-----
                                var data = {
                                    dto: responseDatiGenerali,
                                    firmRsu: firmId,
                                    sedeRsu: $('select[name="sedersu"]').val(),
                                    anno: $('select[name="annofromYearReport"]').val()
                                }

                                var svcCheckLista = new fmodel.AjaxService();
                                svcCheckLista.set("url", BASE + "calcattrsu/checklistdata");
                                svcCheckLista.set("data", JSON.stringify(data));
                                svcCheckLista.set("contentType", "application/json");
                                svcCheckLista.set("method", "POST");

                                svcCheckLista.on("load", function(responseFromListe) {
                                    $.loader.hide({parent: 'body'});

                                    var errors = self.validateDto(responseFromListe);

                                    var formService = new fmodel.FormService();
                                    var formView = new fviews.FormView(formService);

                                    if (errors.errors.length){
                                        formView.form.handleValidationErrors(errors);
                                        return;
                                    }

                                    //----------SE NON CI SONO ERRORI DISABILITO LA GRIGLIA DELLE LISTE-----
                                    //----------E DI MOSTRO IL FORM E LA GRIGLIA PER INSERIRE I DATI------
                                    //----------PER CALCOLARE GLI ESITI -------------
                                        $("#esitovotazione").show();

                                        $.notify.success("Operazione completata");
                                        console.log(responseFromListe);
                                        $("#datigenerali").find("select[name=contratto]").attr("disabled", "disabled");
                                        $("#avanti-liste").attr("disabled", "disabled");
                                        $("#add_list").attr("disabled", "disabled");
                                        var grid = self.initGridListe(responseFromListe,self,true);


                                        //-------FORM ESITO VOTAZIONE------------
                                        var container = document.getElementById("esitovotazioneform");
                                        var formService = new fmodel.FormService();
                                        formService.set("method", "GET");
                                        formService.set("data", {});
                                        formService.set("url", BASE + "calcattrsu/esitovotazioni");

                                        var formView = new fviews.FormView(formService);
                                        formView.container = container;

                                        formView.on("render", function() {
                                            $("#esitovotazioneform").find(".panel-footer, .panel-heading").hide();
                                            $(".content-header pb20").hide();
                                            $(".panel-body").css("overflow", "hidden");

                                            $("#esitovotazioneform").find(".col-md-10").attr("class","col-md-2");
                                        });

                                        formView.show();

                                        //-------GRID ESITO VOTAZIONE------------
                                        var data = {
                                            dto: responseFromListe
                                        };

                                        var svcEsitoGrid = new fmodel.AjaxService();
                                        svcEsitoGrid.set("url", BASE + "calcattrsu/esitovotazione/listvotazioni");
                                        svcEsitoGrid.set("data", JSON.stringify(data));
                                        svcEsitoGrid.set("contentType", "application/json");
                                        svcEsitoGrid.set("method", "POST");

                                        svcEsitoGrid.on("load", function(responseListaForEsiti){
                                                $.loader.hide({parent:'body'});

                                                var gridEsitiList = self.initGridListEsitoVotazione(responseListaForEsiti,self,false);

                                                //-------BOTTONE PER INSERIRE E CALCOLARE LESITO DELLA VOTAZIONE----
                                                //-------SE NON CI SONO ERRORI DISABILITO IL FORM E LA GRIGLIA PRECEDENTE----
                                                //-------E MOSTRO UN RIEPILOGO CON LA POSSIBILITà DI STAMPARE L'ESITO-----
                                                $("#avanti-esito").off('click');
                                                $("#avanti-esito").click(function () {

                                                    console.log(gridEsitiList.getDataSource());

                                                    var source = gridEsitiList.getDataSource();

                                                    var listeVotazione = source._store._array;
                                                    console.log(listeVotazione);

                                                    var aventiDiritto = parseInt($("#esitovotazione").find("input[name=aventidiritto]").val(),10);
                                                    var rsuEleggibili = parseInt($("#esitovotazione").find("input[name=rsueleggibili]").val(),10);
                                                    var addSchedeNulle = $("#esitovotazione").find("input[name=addschedenulle]").is(":checked");
                                                    var schedeBianche =parseInt( $("#esitovotazione").find("input[name=schedebianche]").val(),10);
                                                    var schedeNulle = parseInt($("#esitovotazione").find("input[name=schedenulle]").val(),10);

                                                    var data ={
                                                        firmRsu: firmId,
                                                        sedeRsu: $('select[name="sedersu"]').val(),
                                                        anno: $("#datigenerali").find('select[name="annofromYearReport"]').val(),
                                                        contrattoRsu: $("#datigenerali").find("select[name=contratto]").val(),
                                                        aventiDiritto: aventiDiritto,
                                                        rsuEleggibili: rsuEleggibili,
                                                        addSchedeNulle: addSchedeNulle,
                                                        schedeBianche: schedeBianche,
                                                        schedeNulle: schedeNulle,
                                                        listeVotazione: listeVotazione
                                                    }

                                                    var errors = self.validateEditiDto(data);

                                                    if (errors.errors.length){
                                                        formView.form.handleValidationErrors(errors);
                                                        $.loader.hide({parent:'body'});
                                                        setTimeout(function(){ formView.form.resetValidation(); }, 10000);
                                                        return;
                                                    }else {
                                                        formView.form.resetValidation();
                                                    }

                                                    //---------CHIAMATA PER CONTROLLARE LA VALIDITà-----
                                                    //---------E PER CALCOLARE GLI ESITI----------------
                                                    var svcEsito = new fmodel.AjaxService();
                                                    svcEsito.set("url", BASE + "calcattrsu/esitovotazione");
                                                    svcEsito.set("data", JSON.stringify(data));
                                                    svcEsito.set("contentType", "application/json");
                                                    svcEsito.set("method", "POST");

                                                    svcEsito.on("load", function(responseFromCalcoloEsito){
                                                        var formService = new fmodel.FormService();
                                                        var formView = new fviews.FormView(formService);

                                                        var errorsDto = self.validateDto(responseFromCalcoloEsito);

                                                        if (errorsDto.errors.length){
                                                            formView.form.handleValidationErrors(errorsDto);
                                                            $.loader.hide({parent:'body'});
                                                            return;
                                                        }

                                                        var errorsEsitoVotazione = self.validateEsitoVotazioneDto(responseFromCalcoloEsito);

                                                        if (errorsEsitoVotazione.errors.length){
                                                            formView.form.handleValidationErrors(errorsEsitoVotazione);
                                                            $.loader.hide({parent:'body'});
                                                            return;
                                                        }

                                                        $.loader.hide({parent:'body'});
                                                        $("#avanti-esito").attr("disabled", "disabled");
                                                        $("#indietro-esito").attr("disabled", "disabled");

                                                        //------MI COSTRUISCO IL DTO PER INSERIRE I DATI NELLA GRIGLIA DISABILITATA
                                                        var dtoForDisabledEsitiGrid = [];

                                                        responseFromCalcoloEsito.esiti.esitoVotazione.votazioni.forEach(function (item) {
                                                            var element = {
                                                                name: item.lista.name,
                                                                firmataria: item.lista.firmataria,
                                                                validationError: item.lista.validationError,
                                                                voti: item.voti
                                                            }

                                                            dtoForDisabledEsitiGrid.push(element);
                                                        });

                                                        var gridEsitiList = self.initGridListEsitoVotazione(dtoForDisabledEsitiGrid,self,true);

                                                        $("#esitovotazione").find("input[name=aventidiritto]").attr("disabled", "disabled");
                                                        $("#esitovotazione").find("input[name=rsueleggibili]").attr("disabled", "disabled");
                                                        $("#esitovotazione").find("input[name=addschedenulle]").attr("disabled", "disabled");
                                                        $("#esitovotazione").find("input[name=schedebianche]").attr("disabled", "disabled");
                                                        $("#esitovotazione").find("input[name=schedenulle]").attr("disabled", "disabled");

                                                        var source = gridEsitiList.getDataSource();
                                                        var listeVotazione = source._store._array;

                                                        self.upgradeDatiVotantiInfo(listeVotazione);

                                                        $("#proceduracompletata").show();

                                                        //-------BOTTONE PER TERMINARE LA PROCEDURA GUIDATA----------
                                                        //-------FORM PER IL RIEPILOGO----------------------------
                                                        $("#avanti-procedura").off('click');
                                                        $("#avanti-procedura").click(function () {

                                                            var dataFormDialog = {
                                                                firmRsu: firmId,
                                                                contrattoRsu: $("#datigenerali").find("select[name=contratto]").val(),
                                                                sedeRsu:  $("#datigenerali").find('select[name="sedersu"]').val(),
                                                                anno: $("#datigenerali").find('select[name="annofromYearReport"]').val()
                                                            }

                                                            var formService = new fmodel.FormService();
                                                            formService.set("method", "GET");
                                                            formService.set("data", dataFormDialog);
                                                            formService.set("url", BASE + "calcattrsu/verbalizza");

                                                            var container = $('<div class="verbalizza-votazione"></div>');

                                                            var formView = new fviews.FormView(formService);
                                                            formView.container = container;

                                                            formView.on("render", function() {
                                                                $(".verbalizza-votazione").find(".panel-footer, .panel-heading").hide();
                                                                $(".panel-body").css("overflow", "hidden");
                                                            });

                                                            formView.show();

                                                            var dialog = container.modalDialog({
                                                                autoOpen: true,
                                                                title: "Verbalizza Votazione",
                                                                destroyOnClose: true,
                                                                height: 450,
                                                                width: 650,
                                                                buttons: {
                                                                    Stampa: {
                                                                        primary: true,
                                                                        command: function() {

                                                                            var data = {
                                                                                firmRsu: firmId,
                                                                                contrattoRsu: $("#datigenerali").find("select[name=contratto]").val(),
                                                                                sedeRsu:  $("#datigenerali").find('select[name="sedersu"]').val(),
                                                                                anno: $("#datigenerali").find('select[name="annofromYearReport"]').val(),
                                                                                dto: responseFromCalcoloEsito
                                                                            };

                                                                            var svcStampa = new fmodel.AjaxService();
                                                                            svcStampa.set("url", BASE + "calcattrsu/verbalizza/stampa");
                                                                            svcStampa.set("data", JSON.stringify(data));
                                                                            svcStampa.set("contentType", "application/json");
                                                                            svcStampa.set("method", "POST");


                                                                            svcStampa.on("load", function(response){
                                                                                var formService = new fmodel.FormService();
                                                                                var formView = new fviews.FormView(formService);

                                                                                var errorsDto = self.validateDto(response);

                                                                                if (errorsDto.errors.length){
                                                                                    formView.form.handleValidationErrors(errorsDto);
                                                                                    $.loader.hide({parent:'body'});
                                                                                    return;
                                                                                }
                                                                                var errorsEsitoVotazione = self.validateEsitoVotazioneDto(responseFromCalcoloEsito);

                                                                                if (errorsEsitoVotazione.errors.length){
                                                                                    formView.form.handleValidationErrors(errorsEsitoVotazione);
                                                                                    $.loader.hide({parent:'body'});
                                                                                    return;
                                                                                }

                                                                                $.notify.success("Operazione completata");
                                                                                $.loader.hide({parent:'body'});
                                                                            });

                                                                            svcStampa.on("error", function(error){
                                                                                $.loader.hide({parent:'body'});
                                                                                alert("Errore: "  + error);
                                                                                return;
                                                                            });

                                                                            svcStampa.load();
                                                                            $.loader.show({parent:'body'});
                                                                        }
                                                                    },
                                                                    Salva: {
                                                                        primary: true,
                                                                        command: function() {

                                                                                var data = {
                                                                                    firmRsu: firmId,
                                                                                    note: $("textarea[name=note]").val(),
                                                                                    nomeverbalizzazione:$("input[name=nomeverbalizzazione]").val(),
                                                                                    verbalizzazione:$("input[name=verbalizzazione]").val(),
                                                                                    contrattoRsu: $("#datigenerali").find("select[name=contratto]").val(),
                                                                                    sedeRsu:  $("#datigenerali").find('select[name="sedersu"]').val(),
                                                                                    anno: $("#datigenerali").find('select[name="annofromYearReport"]').val(),
                                                                                    dto: responseFromCalcoloEsito
                                                                                };

                                                                                var svcSalva = new fmodel.AjaxService();
                                                                                svcSalva.set("url", BASE + "calcattrsu/verbalizza/salva");
                                                                                svcSalva.set("data", JSON.stringify(data));
                                                                                svcSalva.set("contentType", "application/json");
                                                                                svcSalva.set("method", "POST");
                                                                                svcSalva.on("load", function(response){
                                                                                        var formService = new fmodel.FormService();
                                                                                        var formView = new fviews.FormView(formService);

                                                                                        var errorsDto = self.validateDto(response);

                                                                                        if (errorsDto.errors.length){
                                                                                            formView.form.handleValidationErrors(errorsDto);
                                                                                            $.loader.hide({parent:'body'});
                                                                                            return;
                                                                                        }
                                                                                        var errorsEsitoVotazione = self.validateEsitoVotazioneDto(responseFromCalcoloEsito);

                                                                                        if (errorsEsitoVotazione.errors.length){
                                                                                            formView.form.handleValidationErrors(errorsEsitoVotazione);
                                                                                            $.loader.hide({parent:'body'});
                                                                                            return;
                                                                                        }

                                                                                        $(dialog).modalDialog("close");

                                                                                        $.notify.success("Operazione completata");

                                                                                        ui.Navigation.instance().navigate("summaryfirmrsu", "index", {
                                                                                            id: firmId
                                                                                        })
                                                                                        $.loader.hide({parent:'body'});
                                                                                    });
                                                                                svcSalva.on("error", function(error){
                                                                                        $.loader.hide({parent:'body'});
                                                                                        alert("Errore: "  + error);
                                                                                        return;
                                                                                    });
                                                                                svcSalva.load();
                                                                                $.loader.show({parent:'body'});


                                                                                $(dialog).modalDialog("close");
                                                                            }
                                                                    }
                                                                }
                                                            });
                                                        });
                                                    });

                                                    svcEsito.on("error", function(error){
                                                        $.loader.hide({parent:'body'});
                                                        alert("Errore: "  + error);
                                                    });

                                                    svcEsito.load();
                                                    $.loader.show({parent:'body'});


                                                    //-------BOTTONE PER TORNARE INDIETRO------------------------------------
                                                    //-------E MODIFICARE EVENTUALMENTE I DATI PER CALCOLARE L'ESITO----------
                                                    $("#indietro-procedura").click(function () {
                                                        $.loader.show({parent:'body'});
                                                        $("#proceduracompletata").hide();

                                                        var source = gridEsitiList.getDataSource();

                                                        var listeVotazione = source._store._array;

                                                        var gridBack = self.initGridListEsitoVotazione(listeVotazione,self,false);
                                                        $("#avanti-liste").prop("disabled", false);
                                                        $("#add_list").prop("disabled", false);
                                                        $("#avanti-esito").prop("disabled", false);
                                                        $("#indietro-esito").prop("disabled", false);
                                                        $("#esitovotazione").find("input[name=aventidiritto]").prop("disabled", false);
                                                        $("#esitovotazione").find("input[name=rsueleggibili]").prop("disabled", false);
                                                        $("#esitovotazione").find("input[name=addschedenulle]").prop("disabled", false);
                                                        $("#esitovotazione").find("input[name=schedebianche]").prop("disabled", false);
                                                        $("#esitovotazione").find("input[name=schedenulle]").prop("disabled", false);

                                                        $.loader.hide({parent:'body'});

                                                    });

                                                });
                                            });

                                        svcEsitoGrid.on("error", function(error){
                                                $.loader.hide({parent:'body'});
                                                alert("Errore: "  + error);
                                            });

                                        svcEsitoGrid.load();
                                        $.loader.show({parent:'body'});
                                });

                                svcCheckLista.on("error", function(error){
                                    $.loader.hide({parent:'body'});
                                    alert("Errore: "  + error);
                                });

                                svcCheckLista.load();
                                $.loader.show({parent:'body'});
                            });



                            //---------------ANNULLA ESITO INSERITO-------
                            // ------RIABLITO LA GRIGLIA DELLE LISTE-----
                            $("#indietro-esito").click(function () {
                                $.loader.show({parent:'body'});
                                $("#esitovotazione").hide();
                                var grid = self.initGridListe(responseDatiGenerali,self,false);
                                $("#avanti-liste").prop("disabled", false);
                                $("#datigenerali").find("select[name=contratto]").prop("disabled", false);

                                $("#add_list").prop("disabled", false);
                                $.loader.hide({parent:'body'});

                            });


                        });

                        svcDatiGenerali.on("error", function(error){
                                $.loader.hide({parent:'body'});
                                alert("Errore: "  + error);
                            });

                        svcDatiGenerali.load();
                        $.loader.show({parent:'body'});
                });
                self.createToolbar();
                self.createBreadcrumbs();
            });
        },
        upgradeDatiVotantiInfo: function(gridresponse){

            var tot_sc_list = 0;
            var tot_sc_vali = 0;
            var tot_voti = 0;

            gridresponse.forEach(function(item){
                var votoInt = parseInt(item.voti,10);
                tot_sc_list = tot_sc_list + votoInt;
            });


            tot_sc_vali = tot_sc_list;

            if($("#esitovotazione")
                .find("input[name=addschedenulle]")
                .is(":checked") == true)
            {
                tot_sc_vali = tot_sc_vali + parseInt(
                    $("#esitovotazione")
                        .find("input[name=schedenulle]")
                        .val(), 10);
            }

            tot_voti = tot_sc_list + parseInt( $("#esitovotazione").find("input[name=schedebianche]").val(),10) + parseInt($("#esitovotazione").find("input[name=schedenulle]").val(),10);


            $("#tot_sc_list").text(tot_sc_list.toString());

            $("#tot_voti").text(tot_voti.toString());

            $("#tot_sc_vali").text(tot_sc_vali.toString());

        },
        validateDto: function(response){
            var self = this;

            var result = {};
            result.errors = [];


            if (response.validationError != null && response.validationError != ""){
                result.errors.push(
                    {
                        property: "errore",
                        message: "Errore trovato"
                    }
                );
                $.notify.error("Errore: "+response.validationError);
            }

            return result;
        },
        validateEsitoVotazioneDto: function(response){
            var self = this;

            var result = {};
            result.errors = [];

            response.esiti.esitoVotazione.votazioni.forEach(function (item) {
                if (item.lista.validationError != null && item.lista.validationError != ""){
                    result.errors.push(
                        {
                            property: "errore",
                            message: "Errore trovato"
                        }
                    );
                    $.notify.error("Errore: "+item.lista.validationError);
                }
            });

            if (response.esiti.esitoVotazione.validationError != null && response.esiti.esitoVotazione.validationError != ""){
                result.errors.push(
                    {
                        property: "errore",
                        message: "Errore trovato"
                    }
                );
                $.notify.error("Errore: "+response.esiti.esitoVotazione.validationError);
            }
            return result;
        },
        validateEditiDto: function(data){
            var result = {};
            result.errors = [];

            if (!data.aventiDiritto || Number.isInteger(data.aventiDiritto) == false)
                result.errors.push(
                    {
                        property: "aventidiritto",
                        message: "Il numero di aventi diritto non &egrave; valido"
                    }
                );

            if (!data.rsuEleggibili || Number.isInteger(data.rsuEleggibili) == false)
                result.errors.push(
                    {
                        property: "rsueleggibili",
                        message: "Il numero di RSU eleggibili non &egrave; valido"
                    }
                );

            if (data.schedeBianche < 0  || Number.isInteger(data.schedeBianche) == false)
                result.errors.push(
                    {
                        property: "schedebianche",
                        message: "Il numero di schede bianche non &egrave; valido"
                    }
                );

            if (data.schedeNulle < 0 || Number.isInteger(data.schedeNulle) == false)
                result.errors.push(
                    {
                        property: "schedenulle",
                        message: "Il numero di schede nulle non &egrave; valido"
                    }
                );

            if (!data.listeVotazione || data.listeVotazione.length <= 0)
                result.errors.push(
                    {
                        property: "error",
                        message: "Impossibile calcolare l'esito, errore nelle votazioni"
                    }
                );

            data.listeVotazione.forEach(function(item){
                var votoInt = parseInt(item.voti,10);
            if (data.schedeBianche < 0 || Number.isInteger(votoInt) == false){
                result.errors.push(
                    {
                        property: "voti",
                        message: "Il numero di voti inserito non &egrave; valido"
                    }
                )
                $.notify.error("Errore: Il numero di voti inserito nella lista non &egrave; valido");
            }
            });

            var data2 = {
                id: $("#datigenerali").find("select[name=contratto]").val()
            };

            var svcDatiGenerali = new fmodel.AjaxService();
            svcDatiGenerali.set("method", "POST");
            svcDatiGenerali.set("data",data2);
            svcDatiGenerali.set("url", BASE + "contractrsu/details");

            svcDatiGenerali.on("load", function(response){
                if (parseInt(data.rsuEleggibili,10) < parseInt(response.rsuMin,10) || parseInt(response.rsuEleggibili,10) > parseInt(response.rsuMax,10)) {
                    result.errors.push(
                        {
                            property: "rsueleggibili",
                            message: "Il numero di RSU eleggibili non &egrave; valido"
                        }
                    );
                    $.notify.warn("Attenzione! Rispettare i parametri del contratto selezionato <br>Rsu Min: "+ response.rsuMin +"<br>Rsu Max: "+response.rsuMax);

                }
            });

            svcDatiGenerali.on("error", function(error){
                alert("Errore: "  + error);
            });

            svcDatiGenerali.load();
            return result;
        },
        initGridListEsitoVotazione: function(responseData,self,isDisabled){
            function moveEditColumnToLeft(dataGrid) {
                dataGrid.columnOption("command:edit", {
                    visibleIndex: -1,
                    width: 80
                });
            }


            var grid = $('#gridVotazioni').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { allowEditing:false,dataField:"name",caption:"Lista", visible : true,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var isActive = options.data.firmataria;

                            if (isActive){
                                var span =$("<span style='font-size: 20px;'>Lista: "+options.data.name+"; Firmataria CCNL S&igrave</span>");
                                span.appendTo(container);
                            }else{
                                var span =$("<span style='font-size: 20px;'>Lista: "+options.data.name+"; Firmataria CCNL No</span>");
                                span.appendTo(container);
                            }

                        }},
                    { allowEditing:true,dataField:"voti", caption:"Voti ottenuti",visible : true}
                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "name",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Liste inserite: " + data.value;
                        }
                    }]
                },
                // columnChooser: {
                //     enabled: true
                // },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: false,
                    fileName: "liste",
                    allowExportSelectedData: false
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "listeattibuzione"
                },
                paging:{
                    pageSize: 35
                },
                disabled: isDisabled,
                sorting:{
                    mode:"multiple"
                },
                onContentReady: function (e) {
                    var columnChooserView = e.component.getView("columnChooserView");
                    if (!columnChooserView._popupContainer) {
                        columnChooserView._initializePopupContainer();
                        columnChooserView.render();
                        columnChooserView._popupContainer.option("dragEnabled", false);
                    }

                    moveEditColumnToLeft(e.component);
                },
                onCellPrepared: function(e) {
                    if(e.rowType === "data" && e.column.command === "edit") {
                        var isEditing = e.row.isEditing,
                            $links = e.cellElement.find(".dx-link");

                        $links.text("");

                        if(isEditing){
                            $links.filter(".dx-link-save").addClass("dx-icon-save");
                            $links.filter(".dx-link-cancel").addClass("dx-icon-revert");
                        } else {
                            $links.filter(".dx-link-edit").addClass("dx-icon-edit");
                            $links.filter(".dx-link-delete").addClass("dx-icon-trash");
                        }
                    }
                },
                rowAlternationEnabled: true,
                showBorders: true,
                editing: {
                    mode: "row",
                    allowUpdating: true,
                    allowDeleting: false,
                    allowAdding: false
                },
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                hoverStateEnabled: true
            }).dxDataGrid("instance");

            return grid;
        },
        initGridListe: function(responseData,self,isDisabled){
            function moveEditColumnToLeft(dataGrid) {
                dataGrid.columnOption("command:edit", {
                    visibleIndex: -1,
                    width: 80
                });
            }

            var grid = $('#gridListe').dxDataGrid({
                dataSource:responseData.liste,
                columns:[
                    { allowEditing:true,dataField:"name",caption:"Nome lista", visible : true,
                        cellTemplate: function (container, options) {
                            var name = options.data.name;
                                var span =$("<span style='font-size: 16px;text-align: left;'>"+name+"</span>");
                                span.appendTo(container);
                            }
                    },
                    { allowEditing:true,dataField:"firmataria", caption:"Firmataria CCNL",visible : true,
                        cellTemplate: function (container, options) {
                            var isActive = options.data.firmataria;

                            if (isActive){
                                var span =$("<span style='font-size: 20px;'></span>");
                                span.append($('<i class="fa fa-check" style=" color:green;padding: 10px;"></i>'));
                                span.appendTo(container);
                            }else{
                                var span =$("<span style='font-size: 20px;'></span>");
                                span.append($('<i class="fa fa-times" style="color:red; padding: 10px;"></i>'));
                                span.appendTo(container);
                            }
                        }},

                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "name",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Liste inserite: " + data.value;
                        }
                    }]
                },
                // columnChooser: {
                //     enabled: true
                // },
                // onCellClick: function (clickedCell) {
                //     alert(clickedCell.column.dataField);
                // },
                "export": {
                    enabled: false,
                    fileName: "liste",
                    allowExportSelectedData: false
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "listeattibuzione"
                },
                paging:{
                    pageSize: 35
                },
                sorting:{
                    mode:"multiple"
                },
                onRowUpdating: function(e){
                    console.log(e);
                    $.loader.show({parent:'body'});

                    var nome = $(".edit-list").find("input[name=name]").val();

                    if(nome == ""){
                        $.notify.warn("Il nome della lista non pu&ograve; essere nullo");
                        $.loader.hide({parent:'body'});
                        return;
                    }

                    var oldname = e.oldData.name;
                    var oldfirmataria = e.oldData.firmataria;

                    var newname;
                    var newfirmataria;

                    if(e.newData.name){
                        newname = e.newData.name
                    }else{
                        newname = oldname;
                    }

                    if(e.newData.firmataria != null || e.newData.firmataria != undefined){
                        newfirmataria = e.newData.firmataria;
                    }else{
                        newfirmataria = oldfirmataria;
                    }


                    var data = {
                        dto: responseData,
                        nome: newname,
                        firmataria: newfirmataria,
                        oldnome: oldname,
                        oldfirmataria: oldfirmataria
                    };

                    var svc = new fmodel.AjaxService();
                    svc.set("url", BASE + "calcattrsu/editlista");
                    svc.set("data", JSON.stringify(data));
                    svc.set("contentType", "application/json");
                    svc.set("method", "POST");

                    svc.on("load", function(responseFromList){
                        responseData.liste = responseFromList.liste;
                        responseData.validationError = responseFromList.validationError;
                        console.log(responseData);
                        var errors = self.validateDto(responseData);

                        var formService = new fmodel.FormService();
                        var formView = new fviews.FormView(formService);

                        if (errors.errors.length){
                            formView.form.handleValidationErrors(errors);
                            $("#gridListe")
                                .dxDataGrid("instance")
                                .option("dataSource", responseData.liste);
                            $.loader.hide({parent:'body'});
                            return;
                        }

                        $("#gridListe")
                            .dxDataGrid("instance")
                            .option("dataSource", responseData.liste);

                        $.loader.hide({parent:'body'});
                    });
                    svc.on("error", function(error){
                        $.loader.hide({parent:'body'});
                        $.notify.error(error);
                    });

                    svc.load();
                },
                onRowRemoving: function(e){
                    var data = {
                        dto: responseData,
                        nome: e.data.name,
                        firmataria: e.data.firmataria
                    };

                    var svc = new fmodel.AjaxService();
                    svc.set("url", BASE + "calcattrsu/deletelista");
                    svc.set("data", JSON.stringify(data));
                    svc.set("contentType", "application/json");
                    svc.set("method", "POST");

                    svc.on("load", function (responseFromList) {
                        responseData.liste = responseFromList.liste;
                        responseData.validationError = responseFromList.validationError;
                        console.log(responseData);
                        var errors = self.validateDto(responseData);

                        var formService = new fmodel.FormService();
                        var formView = new fviews.FormView(formService);


                        if (errors.errors.length){
                            formView.form.handleValidationErrors(errors);
                            return;
                        }

                        $("#gridListe")
                            .dxDataGrid("instance")
                            .option("dataSource", responseData.liste);


                        $.loader.hide({parent: 'body'});
                    });
                    svc.on("error", function (error) {
                        $.loader.hide({parent: 'body'});
                        $.notify.error(error);
                    });

                    svc.load();
                },
                onContentReady: function (e) {
                    var columnChooserView = e.component.getView("columnChooserView");
                    if (!columnChooserView._popupContainer) {
                        columnChooserView._initializePopupContainer();
                        columnChooserView.render();
                        columnChooserView._popupContainer.option("dragEnabled", false);
                    }

                    moveEditColumnToLeft(e.component);
                },
                onCellPrepared: function(e) {
                    if(e.rowType === "data" && e.column.command === "edit") {
                        var isEditing = e.row.isEditing,
                            $links = e.cellElement.find(".dx-link");

                        $links.text("");

                        if(isEditing){
                            $links.filter(".dx-link-save").addClass("dx-icon-save");
                            $links.filter(".dx-link-cancel").addClass("dx-icon-revert");
                        } else {
                            $links.filter(".dx-link-edit").addClass("dx-icon-edit");
                            $links.filter(".dx-link-delete").addClass("dx-icon-trash");
                        }
                    }
                },
                rowAlternationEnabled: true,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"single",
                    allowSelectAll: false,
                    showCheckBoxesMode:"always"
                },
                loadPanel: {
                    text: "Caricamento...",
                    showIndicator: true,
                    showPane: true,
                    enabled: true
                },
                editing: {
                    mode: "row",
                    texts: {
                        confirmDeleteTitle: "Eliminare la lista",
                        confirmDeleteMessage: "Sei sicuro di voler eliminare la lista selezionata?"
                    },
                    allowUpdating: true,
                    allowDeleting: true,
                    allowAdding: false
                },
                disabled: isDisabled,
                hoverStateEnabled: true

                // masterDetail: {
                //     enabled: true,
                //     template: function(container, options) {
                //         var currentData = options.data;
                //         container.addClass("internal-grid-container");
                //         $("<div>").text(currentData.delegaSettore  + " Dettagli:").appendTo(container);
                //         $("<div>")
                //             .addClass("internal-grid")
                //             .dxDataGrid({
                //                 columnAutoWidth: true,
                //                 columns: [{
                //                     dataField: "id"
                //                 }, {
                //                     dataField: "description",
                //                     caption: "Description",
                //                     calculateCellValue: function(rowData) {
                //                         return rowData.description + "ciao ciao";
                //                     }
                //                 }],
                //                 dataSource: currentData.details
                //             }).appendTo(container);
                //     }
                // }

            }).dxDataGrid("instance");

            return grid;
        },
        loadSediRsu: function(firmId, selectedValue, elem){
            var self = this;
            var model = new fmodel.AjaxService();
            model.set("content-type", "application/json");
            model.set("url", BASE + "values/sedirsu?keyword=" + firmId);

            model.on("load" , function(response){
                elem.empty().append("<option value=''>Select</option>");

                if (selectedValue) {
                    $.each(response, function(index, value){
                        var selected = "";
                        if (value.value == selectedValue)
                            selected = "selected='selected'";
                        else
                            selected = "";

                        elem.append($("<option " + selected + "></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                } else {
                    $.each(response, function(index, value){
                        elem.append($("<option></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                }

                self.invoke("sediLoaded", firmId, selectedValue);


            });

            model.on("error", function(error){
                alert(error);
            });



            model.load();
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
            return [];
        },
        getBreadcrumbItems: function() {
            var self = this;
            return [];
        }

    });



    exports.ReportRsuHomeRemoteView = ReportRsuHomeRemoteView;
    return exports;

});
