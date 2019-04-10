/**
 * Created by fgran on 15/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins","framework/webparts", "geoUtils", "reports/reportResultsConfigurer"], function(core, fmodel, fviews, ui, widgets, plugins, webparts, geoUtils, resultsConfigurer) {

    var exports = {};

    var RepositoryServiceFactory = core.AObject.extend({
        ctor: function() {
            RepositoryServiceFactory.super.ctor.call(this);
        },
        searchLiberi: function(searchParams){
            var route = BASE + "liberi/report" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        searchLiberiNew: function(searchParams){
            var route = BASE + "liberi/reportnew" ;

            var svc =  this.__createService(true, route, searchParams);
            return svc;
        },
        requestInfoAiTettitori: function(list, infotype, province){
            var route = BASE + "liberi/info/" + infotype + "/" + province ;

            var svc =  this.__createService(true, route, list);
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



    var ReportLiberiAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportLiberiAppView.super.ctor.call(this, formService);
            this.geoUtils = new geoUtils.GeoUtils();

            var self = this;
            //questa sarà la lista dei risultati provenienti dal server
            self.listOfLiberi = null;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "370px");

                // Modifico il valore della checkbox (on --> true)
                $("input[type=checkbox]").change(function() {
                    if($(this).val() == "on")
                        $(this).val(true);
                    else
                        $(this).val(false);
                }).change();

                var nationalityItalianId = 100;

                // Carico la lista delle province italiane
                self.geoUtils.loadProvinces(nationalityItalianId, "", $('select[name="livingProvince"]'));

                //qui attacco levento on change della select delle province
                $('select[name="livingProvince"]').change(function(){

                    var selectedVal = $(this).val();
                    //carico la lista delle città
                    if (selectedVal){
                        self.geoUtils.loadCities(selectedVal, "", $('select[name="livingCity"]'));

                    }
                    else
                        $('select[name="livingCity"]').empty().append("<option selected='selected' value=''>Select</option>");
                });
            });

            self.formView.on("submit", function(){


                //appena avvio la ricerca devo rimuovere le select per filtrare le iscrizioni
                $('.top-toolbar').remove();

                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchLiberi(data);


                svc.on("load", function(response){

                    self.listOfLiberi = response;

                    //inizializzo gli eventuiuali filtri per le iscrizioni
                    self.initFilterOptions(response);

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "liberi", true);
                    reportResultsConfigurer.init();


                    //rimuovo il pulsante Richiesta Info se lo ho gia creato
                    $('.request-info').remove();
                    //aggiungo un pulsante per l'invio di reìichiesta info ai territori
                    var btn = $('<div class="col-md-12 col-xs-12 margin-bottom-10 p0 request-info" title="" data-placement="top" data-toggle="tooltip" data-original-title="Richiedi info ai territori">'+
                                        '<button class="btn btn-primary full-width request-info-territori" type="button">'+
                                        '<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>'+
                                        '</button>'+
                        '</div>');

                    //rimuovo il pulsante Stampa Completa se lo ho gia creato
                    $('.complete-print').remove();
                    //aggiungo un pulsante per la stampa completa
                    var btnStampaCompleta = $('<div class="col-md-12 col-xs-12 margin-bottom-10 p0 complete-print" title="" data-placement="top" data-toggle="tooltip" data-original-title="Stampa completa">'+
                    '<button class="btn btn-primary full-width complete-print-btn" type="button">'+
                    '<span class="glyphicon glyphicon-print" aria-hidden="true"></span>'+
                    '</button>'+
                    '</div>');


                    var toolboxContainer = $('.toolbox-seach-report').find('.back-white').children("div");
                    var smallToolboxContainer = $('.toolbox-seach-report-xs').find('.back-white').children("div");
                    toolboxContainer.append(btn);
                    smallToolboxContainer.append(btn.clone());
                    btn.tooltip();
                    toolboxContainer.append(btnStampaCompleta);
                    smallToolboxContainer.append(btnStampaCompleta.clone());
                    btnStampaCompleta.tooltip();

                    $('.request-info').click(function(){
                        //ottengo la lista delle righe selezionate
                        var selectedrows = grid.getSelectedRowsData();
                        var provinceSelected = $("select[name=provinceSelect]").val();

                        if (selectedrows.length == 0) {
                            $.notify.error("Selezionare almeno un elemento");
                            return false;
                        }

                        if (!provinceSelected) {
                            $.notify.error("Selezionare una provincia");
                            return false;
                        }

                        var container = $('<div class="request-info-territori-container"></div>');

                        var data = {};
                        var selectedrows = grid.getSelectedRowsData();
                        data.selectedLiberi = selectedrows;
                        data.province = provinceSelected;

                        var path = BASE + "liberi/richiediinfoterritori";
                        var formService = new fmodel.FormService();
                        formService.set("url", path);
                        formService.set("method", "POST");
                        formService.set("data", JSON.stringify(data));
                        formService.set("contentType", "application/json");

                        var form = new fviews.FormView(formService);
                        form.container = container;

                        form.on("render", function () {

                            // var winHeight = $(window).height();
                            //
                            // if (winHeight > 600)
                            //     winHeight = winHeight - 160;
                            // else
                            //     winHeight = '400';
                            // container.closest('.panel-body').css('height', winHeight + "px");
                            //
                            //
                            // //codice per rimuovoere il pulsante salva - annulla
                             container.find(".panel-footer, .panel-heading").hide();
                        });

                        form.show();

                        var dialog = container.modalDialog({
                            autoOpen: true,
                            title: "Richiesta info ai territori",
                            destroyOnClose: true,
                            height: 500,
                            width: 800,
                            buttons: {
                                Salva: {
                                    primary: true,
                                    command: function() {
                                        form.form.resetValidation();

                                        // Validazione e-mail
                                        var mails = $("input[name=destinatario]").val();
                                        var errors = self.validateMails(mails);

                                        if (errors.errors.length){
                                            form.form.handleValidationErrors(errors);
                                            return;
                                        }

                                        // Se la validazione è OK invio i dati per la mail al server
                                        var svc = new  fmodel.AjaxService();
                                        var data = self.normalizeSubmitResult(form.form);
                                        data.selectedRows = selectedrows;
                                        svc.set("url", BASE + "liberi/sendrichiediinfo");
                                        svc.set("contentType", "application/json");
                                        svc.set("data", JSON.stringify(data));
                                        svc.set("method", "POST");

                                        svc.on("load", function(response){
                                            $.loader.hide({parent:'body'});
                                            dialog.modalDialog("close");
                                            $.notify.success("La richiesta info è stata inoltrata con successo")
                                        });
                                        svc.on("error", function(error){
                                            $.loader.hide({parent:'body'});
                                            $.notify.error(error);
                                        });

                                        svc.load();
                                        $.loader.show({parent:'body'});
                                    }
                                }
                            }
                        });


                    });


                    $('.complete-print').click(function() {

                        //ottengo la lista delle righe selezionate
                        var selectedrows = grid.getSelectedRowsData();

                        if (selectedrows.length == 0) {
                            $.notify.error("Selezionare almeno un elemento");
                            return false;
                        }

                        $.loader.show({parent:'body'});

                        var svc = new  fmodel.AjaxService();

                        svc.set("url", BASE + "liberi/retrievefilestampa");
                        svc.set("contentType", "application/json");
                        svc.set("data", JSON.stringify(selectedrows));
                        svc.set("method", "POST");

                        svc.on("load", function(response){
                            $.loader.hide({parent:'body'});

                            // response è il path del file da scaricare
                            location.href = BASE + "liberi/print?path="+encodeURIComponent(response);
                            dialog.modalDialog("close");
                        });
                        svc.on("error", function(error){
                            $.loader.hide({parent:'body'});
                            $.notify.error(error);
                        });

                        svc.load();

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
        validateMails: function(mails) {
            var self = this;
            var result = {};
            result.errors = [];

            var regex_email = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

            // N.B.: potrebbero essere indicati una lista di e-mail separati da ';'
            $.each(mails.split(";"), function(i, o) {
                if (o != null && !regex_email.test(o))
                    result.errors.push(
                        {
                            property: "destinatario",
                            message: "E-mail non corretta"
                        }
                    );
            });

            return result;
        },
        initFilterOptions: function(response){
            var self = this;



            //recupero la barra dove cè il tasto esegui ricerca
            //in tale barra alla fine della ricerca devo inserire una select per la scelta dell'anno in
            //cui filtrare eventiali iscriziojni e una select per la provincia in cui è stata effettuata
            //una iscrizione


            //visualizzo i filtri sui liberi solo se ne ho trovato qualcuno

            if (response.length > 0){
                var province = [];
                var anni = [];

                //devo ricercare tutte le provicne presenti nelle iscrizioni e tutti gli anni di iscrizione
                $.each(response, function(index, elem){
                    var iscrizioni = elem.iscrizioni;
                    //ciclo su tutte le iscrizioni
                    $.each(iscrizioni, function(index1, elem1){
                        province.push(elem1.nomeProvincia);
                        anni.push(elem1.anno);
                    })
                });

                //per prima cosa eseguo una distinct su entrambi gli array per prendere le
                // province ed gli anni una sola volta
                var distinctProvince=province.filter(function(current,index,province){
                    return index==province.indexOf(current);
                });
                var distinctAnni=anni.filter(function(current,index,anni){
                    return index==anni.indexOf(current);
                });

                distinctAnni.sort(function(a,b) {
                    return a - b;
                });

                distinctProvince.sort();


                //adesso se uno dei due array è vuoto non mostro nulla perche non ci sono iscrizioni
                if (province.length > 0){

                    var bottombar = $('.bottom-form-bar');

                    var yearSelect =$(
                        '<select name="yearSelect"  style="margin-right: 5px">'+
                        '</select>');

                    yearSelect.append('<option selected="selected" value="">(Seleziona anno di iscrizione)</option>');

                    $.each(distinctAnni, function(index, elem){
                        yearSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });


                    var provinceSelect =$(
                        '<select name="provinceSelect"  style="margin-right: 5px">'+
                        '</select>');

                    provinceSelect.append('<option selected="selected" value="">(Seleziona provincia)</option>');

                    $.each(distinctProvince, function(index, elem){
                        provinceSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });


                    var infoSelect =$(
                        '<select name="infoSelect" style="margin-right: 5px">'+
                        '<option selected="selected" value="">Tutti</option>'+
                        '<option value="1">Senza richieste effettuate ad altri territori</option>'+
                        '<option value="2">Con richieste effettuate ad altri territori</option>'+
                        '</select>');


                    var topToolbar = $('<div class="pull-left top-toolbar"></div>');


                    topToolbar.append(yearSelect);
                    topToolbar.append(provinceSelect);
                    topToolbar.append(infoSelect);
                    bottombar.append(topToolbar);




                    //adesso posso attaccare gli handlers dei
                    yearSelect.change(function(){

                        var selected = this.value;
                        self.filterData(provinceSelect.val(),selected, infoSelect.val());

                    });
                    provinceSelect.change(function(){

                        var selected = this.value;
                        self.filterData(selected,yearSelect.val(), infoSelect.val());

                    });
                    infoSelect.change(function(){

                        var selected = this.value;
                        if (!yearSelect.val() && !provinceSelect.val() )
                            return;

                        //procedo a filtrare solo se cè un valore
                        self.filterData(provinceSelect.val(),yearSelect.val(), selected);

                    });


                }

            }

        },
        filterData : function(province, year, infoType){

            var self = this;

            if (!province && !year){
                self.initGrid(self.listOfLiberi);
                return;
            }

            var filterdList = [];
            $.each(self.listOfLiberi, function(index, elem){

                if (elem.numIscrizioni > 0){

                    $.each(elem.iscrizioni, function(index1, elem1){
                        //variabile che indica se il criterio per l'anno è soddisfatto
                        //se l'anno è nullo il criterio restituira sempre true delegando agli altri criteri
                        //la veirfica
                        //ecco perchè lo imposto a true
                       var foundAnno = true;
                       if (year){
                           if (elem1.anno != parseInt(year)){
                                foundAnno = false;
                           }
                       }

                        var foundProvince = true;
                        if (province){
                            if (elem1.nomeProvincia != province){
                                foundProvince = false;
                            }
                        }

                        if (foundAnno && foundProvince)
                        {
                            filterdList.push(elem);
                            return false;
                        }

                    });

                }


            });

            if (!infoType)
                self.initGrid(filterdList);
            else{
                //se info type è una dei due "con o senza info" invio tutti i record filtrati al server che provvedera
                //ad integrare tutte le informazioni delle iscrizioni con le relative informazioni circa le info
                //richieste ai territori

                //se arrivo qui vuol dire che devo richiedere le info ma devo accertarmi che ci sia un filtro sulle province
                if (!province){
                    alert("Selezionare un filtro sulla provincia");
                    self.initGrid(filterdList);
                    return;
                }
                if (filterdList.length == 0){
                    self.initGrid(filterdList);
                    return;
                }
                
                //vado sul server a chiedere informazioni
                var factory = new RepositoryServiceFactory();
                var svc = factory.requestInfoAiTettitori(filterdList, infoType, province);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});
                    self.initGrid(response);
                    
                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
                });

                svc.load();
                $.loader.show({parent:'body'});
                
                
                
            }

        },
        initGrid : function(responseData){



            var viewFirm = false;
            if ($('input[name="firm"]').val())
                viewFirm = true;

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"liberoData", visible : false, dataType:'date', visibleIndex: 5},
                    { dataField:"liberoProvincia",  visible : false, visibleIndex: 1},
                    { dataField:"liberoEnteBilaterale", visible : false, visibleIndex: 2},
                    { dataField:"liberoIscrittoA", visible : false , visibleIndex: 4},

                    { dataField:"lavoratoreNomeCompleto",  visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var completeName = options.data.lavoratoreNomeCompleto;
                            var fiscalCode = options.data.lavoratoreCodiceFiscale;
                            var province = options.data.liberoProvincia;
                            var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode + "&province=" + province);
                            $("<a />")
                                .text(completeName)
                                .attr("href", uri)
                                .attr("target", "_blank")

                                // .on('click', function(){
                                //
                                //     ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                //         fiscalCode:fiscalCode,
                                //         province: province
                                //     })
                                //
                                // })
                                .appendTo(container);
                        }
                        


                    },
                    { dataField:"lavoratoreCellulare", visible : false},
                    { dataField:"lavoratoreDelegheOwner", caption:"Possiede delega", visible : viewFirm,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var lavoratoreDelegheOwner = options.data.lavoratoreDelegheOwner;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (lavoratoreDelegheOwner){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">done</i>'));
                                span.appendTo(container);
                            }

                        }},

                    { dataField:"lavoratoreCodiceFiscale", visible : false},
                    { dataField:"lavoratoreDataNascita", dataType:'date', visible : false},
                    { dataField:"lavoratoreProvinciaResidenza", visible : false},
                    { dataField:"lavoratoreCittaResidenza", visible : false},
                    { dataField:"lavoratoreIndirizzo", visible : false},
                    { dataField:"lavoratoreCap", visible : false},
                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    {dataField:"numIscrizioni", visible: true,visibleIndex: 1, caption:"Iscritto storico",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var numIscrizioni = options.data.numIscrizioni;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (numIscrizioni){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">sentiment_satisfied</i>'));
                                span.appendTo(container);
                            }

                        }
                    },
                    { dataField:"aziendaRagioneSociale", visible : true, visibleIndex: 5,

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;
                            
                            if (!name)
                                return;

                            var uri = encodeURI(BASE + "#/summaryfirm/remoteIndex?description=" + name.replace("&", "*_").replace("'", "~_"));

                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                // .on('click', function(){
                                //     ui.Navigation.instance().navigate("summaryfirm", "remoteIndex", {
                                //         description:name
                                //     });
                                // })
                                .appendTo(container);
                        }
                    },
                    { dataField:"lavoratoreNazionalita", visible : false}


                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "lavoratoreNomeCompleto",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Non iscritti trovati: " + data.value;
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
                    fileName: "liberi",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportliberi"
                },
                paging:{
                    pageSize: 35
                },
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
                },
                rowAlternationEnabled: true,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"multiple",
                    showCheckBoxesMode: "always"
                },
                hoverStateEnabled: true,

                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;

                        container.addClass("internal-grid-container");
                        $("<div>").text("Iscrizioni lavoratore").appendTo(container);
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"nomeRegione",  visible : true, visibleIndex: 0},
                                    { dataField:"nomeProvincia",  visible : true, visibleIndex: 1},
                                    { dataField:"settore",  visible : true, visibleIndex: 2},
                                    { dataField:"ente",  visible : true, visibleIndex: 3},
                                    { dataField:"periodo",  visible : true, visibleIndex: 4},
                                    { dataField:"anno",  visible : true, visibleIndex: 5},
                                    { dataField:"azienda", visible : true, visibleIndex: 6},
                                    { dataField:"piva",  visible : true, visibleIndex: 7},
                                    { dataField:"livello",  visible : true, visibleIndex: 8},
                                    { dataField:"quota",  visible : true, visibleIndex: 9},
                                    { dataField:"contratto",  visible : true, visibleIndex: 10},
                                    {dataField: "numComunicazioni", visible: true, visibleIndex:11, caption: "Richieste ai territori",

                                        cellTemplate: function (container, options) {
                                            //container.addClass("img-container");
                                            var numComunicazioni = options.data.numComunicazioni;
                                            // <span class="color-black">
                                            //     <i class="material-icons">sentiment_satisfied</i>
                                            //     </span>
                                            if (numComunicazioni){
                                                var span =$("<span style='color:orange' />");
                                                span.append(numComunicazioni);
                                                span.appendTo(container);
                                            }

                                        }
                                    
                                    }


                                ],
                                dataSource: currentData.iscrizioni
                            }).appendTo(container);
                    }
                }

            }).dxDataGrid("instance");

            return grid;

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
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Report non iscritti"
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
                //             fs: this.fullScreenForm
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


    var ReportLiberiNewAppView = fviews.FormAppView.extend({
        ctor: function(formService) {
            ReportLiberiNewAppView.super.ctor.call(this, formService);
            this.geoUtils = new geoUtils.GeoUtils();

            var self = this;
            //questa sarà la lista dei risultati provenienti dal server
            self.listOfLiberi = null;

            self.formView.on("load", function(){
                self.createToolbar();
                self.createBreadcrumbs();

                // Setto la lunghezza delle colonne del form report
                $(".panel.col-div").css("height", "370px");


            });

            self.formView.on("submit", function(){


                //appena avvio la ricerca devo rimuovere le select per filtrare le iscrizioni
                $('.top-toolbar').remove();

                var data = self.normalizeSubmitResult(self.formView.form);

                var factory = new RepositoryServiceFactory();
                var svc = factory.searchLiberiNew(data);


                svc.on("load", function(response){

                    self.listOfLiberi = response;

                    //inizializzo gli eventuiuali filtri per le iscrizioni
                    self.initFilterOptions(response);

                    $.loader.hide({parent:'body'});

                    //inizializzo la griglia devexpress
                    var grid = self.initGrid(response);
                    //una volta ottenuti i risultati la griglia devexpress mostra una loader
                    //di attesa per la renderizzazione degli stessi! in quel momento rendo
                    //visibile l'intera area
                    //scrollando fino a rendere visibile la griglia
                    $('html, body').animate({scrollTop: $('#reportContainer').offset().top - 160}, 1400, "swing");

                    //configuro la navigabilità e la toolbar delle actions del report
                    var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "liberi", true);
                    reportResultsConfigurer.init();



                    //rimuovo il pulsante Stampa Completa se lo ho gia creato
                    $('.complete-print').remove();
                    //aggiungo un pulsante per la stampa completa
                    var btnStampaCompleta = $('<div class="col-md-12 col-xs-12 margin-bottom-10 p0 complete-print" title="" data-placement="top" data-toggle="tooltip" data-original-title="Stampa completa">'+
                        '<button class="btn btn-primary full-width complete-print-btn" type="button">'+
                        '<span class="glyphicon glyphicon-print" aria-hidden="true"></span>'+
                        '</button>'+
                        '</div>');


                    var toolboxContainer = $('.toolbox-seach-report').find('.back-white').children("div");
                    var smallToolboxContainer = $('.toolbox-seach-report-xs').find('.back-white').children("div");

                    toolboxContainer.append(btnStampaCompleta);
                    smallToolboxContainer.append(btnStampaCompleta.clone());
                    btnStampaCompleta.tooltip();




                    $('.complete-print').click(function() {

                        //ottengo la lista delle righe selezionate
                        var selectedrows = grid.getSelectedRowsData();

                        if (selectedrows.length == 0) {
                            $.notify.error("Selezionare almeno un elemento");
                            return false;
                        }

                        $.loader.show({parent:'body'});

                        var svc = new  fmodel.AjaxService();

                        svc.set("url", BASE + "liberi/retrievefilestampa");
                        svc.set("contentType", "application/json");
                        svc.set("data", JSON.stringify(selectedrows));
                        svc.set("method", "POST");

                        svc.on("load", function(response){
                            $.loader.hide({parent:'body'});

                            // response è il path del file da scaricare
                            location.href = BASE + "liberi/print?path="+encodeURIComponent(response);
                            dialog.modalDialog("close");
                        });
                        svc.on("error", function(error){
                            $.loader.hide({parent:'body'});
                            $.notify.error(error);
                        });

                        svc.load();

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

        initFilterOptions: function(response){
            var self = this;



            //recupero la barra dove cè il tasto esegui ricerca
            //in tale barra alla fine della ricerca devo inserire una select per la scelta dell'anno in
            //cui filtrare eventiali iscriziojni e una select per la provincia in cui è stata effettuata
            //una iscrizione


            //visualizzo i filtri sui liberi solo se ne ho trovato qualcuno

            if (response.length > 0){
                var province = [];
                var anni = [];

                //devo ricercare tutte le provicne presenti nelle iscrizioni e tutti gli anni di iscrizione
                $.each(response, function(index, elem){
                    var iscrizioni = elem.iscrizioni;
                    //ciclo su tutte le iscrizioni
                    $.each(iscrizioni, function(index1, elem1){
                        province.push(elem1.nomeProvincia);
                        anni.push(elem1.anno);
                    })
                });

                //per prima cosa eseguo una distinct su entrambi gli array per prendere le
                // province ed gli anni una sola volta
                var distinctProvince=province.filter(function(current,index,province){
                    return index==province.indexOf(current);
                });
                var distinctAnni=anni.filter(function(current,index,anni){
                    return index==anni.indexOf(current);
                });

                distinctAnni.sort(function(a,b) {
                    return a - b;
                });

                distinctProvince.sort();


                //adesso se uno dei due array è vuoto non mostro nulla perche non ci sono iscrizioni
                if (province.length > 0){

                    var bottombar = $('.bottom-form-bar');

                    var yearSelect =$(
                        '<select name="yearSelect"  style="margin-right: 5px">'+
                        '</select>');

                    yearSelect.append('<option selected="selected" value="">(Seleziona anno di iscrizione)</option>');

                    $.each(distinctAnni, function(index, elem){
                        yearSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });


                    var provinceSelect =$(
                        '<select name="provinceSelect"  style="margin-right: 5px">'+
                        '</select>');

                    provinceSelect.append('<option selected="selected" value="">(Seleziona provincia)</option>');

                    $.each(distinctProvince, function(index, elem){
                        provinceSelect.append('<option value="'+ elem + '">'+ elem + '</option>');
                    });


                    var infoSelect =$(
                        '<select name="infoSelect" style="margin-right: 5px">'+
                        '<option selected="selected" value="">Tutti</option>'+
                        '<option value="1">Senza richieste effettuate ad altri territori</option>'+
                        '<option value="2">Con richieste effettuate ad altri territori</option>'+
                        '</select>');


                    var topToolbar = $('<div class="pull-left top-toolbar"></div>');


                    topToolbar.append(yearSelect);
                    topToolbar.append(provinceSelect);
                    topToolbar.append(infoSelect);
                    bottombar.append(topToolbar);




                    //adesso posso attaccare gli handlers dei
                    yearSelect.change(function(){

                        var selected = this.value;
                        self.filterData(provinceSelect.val(),selected, infoSelect.val());

                    });
                    provinceSelect.change(function(){

                        var selected = this.value;
                        self.filterData(selected,yearSelect.val(), infoSelect.val());

                    });
                    infoSelect.change(function(){

                        var selected = this.value;
                        if (!yearSelect.val() && !provinceSelect.val() )
                            return;

                        //procedo a filtrare solo se cè un valore
                        self.filterData(provinceSelect.val(),yearSelect.val(), selected);

                    });


                }

            }

        },
        filterData : function(province, year, infoType){

            var self = this;

            if (!province && !year){
                self.initGrid(self.listOfLiberi);
                return;
            }

            var filterdList = [];
            $.each(self.listOfLiberi, function(index, elem){

                if (elem.numIscrizioni > 0){

                    $.each(elem.iscrizioni, function(index1, elem1){
                        //variabile che indica se il criterio per l'anno è soddisfatto
                        //se l'anno è nullo il criterio restituira sempre true delegando agli altri criteri
                        //la veirfica
                        //ecco perchè lo imposto a true
                        var foundAnno = true;
                        if (year){
                            if (elem1.anno != parseInt(year)){
                                foundAnno = false;
                            }
                        }

                        var foundProvince = true;
                        if (province){
                            if (elem1.nomeProvincia != province){
                                foundProvince = false;
                            }
                        }

                        if (foundAnno && foundProvince)
                        {
                            filterdList.push(elem);
                            return false;
                        }

                    });

                }


            });

            if (!infoType)
                self.initGrid(filterdList);
            else{
                //se info type è una dei due "con o senza info" invio tutti i record filtrati al server che provvedera
                //ad integrare tutte le informazioni delle iscrizioni con le relative informazioni circa le info
                //richieste ai territori

                //se arrivo qui vuol dire che devo richiedere le info ma devo accertarmi che ci sia un filtro sulle province
                if (!province){
                    alert("Selezionare un filtro sulla provincia");
                    self.initGrid(filterdList);
                    return;
                }
                if (filterdList.length == 0){
                    self.initGrid(filterdList);
                    return;
                }

                //vado sul server a chiedere informazioni
                var factory = new RepositoryServiceFactory();
                var svc = factory.requestInfoAiTettitori(filterdList, infoType, province);


                svc.on("load", function(response){

                    $.loader.hide({parent:'body'});
                    self.initGrid(response);

                });
                svc.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    alert("Errore: "  + error);
                });

                svc.load();
                $.loader.show({parent:'body'});



            }

        },
        initGrid : function(responseData){



            var viewFirm = false;
            if ($('input[name="firm"]').val())
                viewFirm = true;

            var grid = $('#reportContainer').dxDataGrid({
                dataSource:responseData,
                columns:[

                    { dataField:"liberoData", visible : false, dataType:'date', visibleIndex: 5},
                    { dataField:"liberoProvincia",  visible : false, visibleIndex: 1},
                    { dataField:"liberoEnteBilaterale", visible : false, visibleIndex: 2},
                    { dataField:"liberoIscrittoA", visible : false , visibleIndex: 4},

                    { dataField:"lavoratoreNomeCompleto",  visible : true, visibleIndex: 0,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var completeName = options.data.lavoratoreNomeCompleto;
                            var fiscalCode = options.data.lavoratoreCodiceFiscale;
                            var province = options.data.liberoProvincia;
                            var uri = encodeURI(BASE + "#/summaryworker/remoteIndex?fiscalCode=" + fiscalCode + "&province=" + province);
                            $("<a />")
                                .text(completeName)
                                .attr("href", uri)
                                .attr("target", "_blank")

                                // .on('click', function(){
                                //
                                //     ui.Navigation.instance().navigate("summaryworker", "remoteIndex", {
                                //         fiscalCode:fiscalCode,
                                //         province: province
                                //     })
                                //
                                // })
                                .appendTo(container);
                        }



                    },
                    { dataField:"lavoratoreCellulare", visible : false},
                    { dataField:"lavoratoreDelegheOwner", caption:"Possiede delega", visible : viewFirm,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var lavoratoreDelegheOwner = options.data.lavoratoreDelegheOwner;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (lavoratoreDelegheOwner){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">done</i>'));
                                span.appendTo(container);
                            }

                        }},

                    { dataField:"lavoratoreCodiceFiscale", visible : false},
                    { dataField:"lavoratoreDataNascita", dataType:'date', visible : false},
                    { dataField:"lavoratoreProvinciaResidenza", visible : false},
                    { dataField:"lavoratoreCittaResidenza", visible : false},
                    { dataField:"lavoratoreIndirizzo", visible : false},
                    { dataField:"lavoratoreCap", visible : false},
                    { dataField:"lavoratoreNome", visible : false},
                    { dataField:"lavoratoreCognome", visible : false},
                    { dataField:"lavoratoreSesso", visible : false},
                    {dataField:"numIscrizioni", visible: true,visibleIndex: 1, caption:"Iscritto storico",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var numIscrizioni = options.data.numIscrizioni;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (numIscrizioni){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">sentiment_satisfied</i>'));
                                span.appendTo(container);
                            }

                        }
                    },
                    {dataField:"numDeleghe", visible: true,visibleIndex: 1, caption:"Deleghe",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var numIscrizioni = options.data.numDeleghe;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (numIscrizioni){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">sentiment_satisfied</i>'));
                                span.appendTo(container);
                            }

                        }
                    },
                    {dataField:"numNonIscrizioni", visible: true,visibleIndex: 1, caption:"Iscrizioni ad altri",
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var numIscrizioni = options.data.numNonIscrizioni;
                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (numIscrizioni){
                                var span =$("<span style='color:green' />");
                                span.append($('<i class="material-icons" style=" text-align: center;display: block;">sentiment_satisfied</i>'));
                                span.appendTo(container);
                            }

                        }
                    },
                    { dataField:"aziendaRagioneSociale", visible : true, visibleIndex: 5,

                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var name = options.data.aziendaRagioneSociale;

                            if (!name)
                                return;

                            var uri = encodeURI(BASE + "#/summaryfirm/remoteIndex?description=" + name.replace("&", "*_").replace("'", "~_"));

                            $("<a />")
                                .text(name)
                                .attr("href", uri)
                                .attr("target", "_blank")
                                // .on('click', function(){
                                //     ui.Navigation.instance().navigate("summaryfirm", "remoteIndex", {
                                //         description:name
                                //     });
                                // })
                                .appendTo(container);
                        }
                    },
                    { dataField:"lavoratoreNazionalita", visible : false}


                ],
                // searchPanel: {
                //     visible: true
                //
                // },
                summary: {
                    totalItems: [{
                        column: "lavoratoreNomeCompleto",
                        summaryType: "count",
                        customizeText: function(data) {
                            return "Non iscritti trovati: " + data.value;
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
                    fileName: "liberi",
                    allowExportSelectedData: true
                },
                stateStoring: {
                    enabled: false,
                    type: "localStorage",
                    storageKey: "reportliberi"
                },
                paging:{
                    pageSize: 35
                },
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
                },
                rowAlternationEnabled: true,
                showBorders: true,
                allowColumnReordering:true,
                allowColumnResizing:true,
                columnAutoWidth: true,
                selection:{
                    mode:"multiple",
                    showCheckBoxesMode: "always"
                },
                hoverStateEnabled: true,

                masterDetail: {
                    enabled: true,
                    template: function(container, options) {
                        var currentData = options.data;

                        var fiscalCode = currentData.lavoratoreCodiceFiscale;

                        var containerString = '<div class="col-md-12">\n' +
                            '    <div class="tab-block mb25">\n' +
                            '        <ul class="nav nav-tabs tabs-border nav-justified">\n' +
                            '            <li class="active">\n' +
                            '                <a href="#tab15_1_' + fiscalCode + '" data-toggle="tab" aria-expanded="false">Iscrizioni db nazionale</a>\n' +
                            '            </li>\n' +
                            '            <li >\n' +
                            '                <a href="#tab15_2_' + fiscalCode + '" data-toggle="tab" aria-expanded="true"><i class="fa fa-pencil text-purple pr5"></i> Deleghe</a>\n' +
                            '            </li>\n' +
                            '            <li >\n' +
                            '                <a href="#tab15_3_' + fiscalCode + '" data-toggle="tab" aria-expanded="true"><i class="fa fa-pencil text-purple pr5"></i> Iscrizioni altro sindacato</a>\n' +
                            '            </li>\n' +
                            '        </ul>\n' +
                            '        <div class="tab-content">\n' +
                            '            <div id="tab15_1_' + fiscalCode + '" class="tab-pane active">\n' +
                            '                <div class="iscon"></div>\n' +
                            '            </div>\n' +
                            '            <div id="tab15_2_' + fiscalCode + '" class="tab-pane ">\n' +
                            '               <div class="delcon"></div>\n' +
                            '            </div>\n' +
                            '            <div id="tab15_3_' + fiscalCode + '" class="tab-pane">\n' +
                            '                 <div class="noniscon"></div>\n' +
                            '            </div>\n' +
                            '          \n' +
                            '        </div>\n' +
                            '    </div>\n' +
                            '</div>';

                        var mainContainer = $(containerString);

                        mainContainer.appendTo(container);

                        // container.addClass("internal-grid-container");
                        // $("<div>").text("Iscrizioni lavoratore")
                        //     .appendTo(container);
                        //
                        //
                        //
                        //
                        $("<div>")
                            .addClass("internal-grid")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"nomeRegione",  visible : true, visibleIndex: 0},
                                    { dataField:"nomeProvincia",  visible : true, visibleIndex: 1},
                                    { dataField:"settore",  visible : true, visibleIndex: 2},
                                    { dataField:"ente",  visible : true, visibleIndex: 3},
                                    { dataField:"periodo",  visible : true, visibleIndex: 4},
                                    { dataField:"anno",  visible : true, visibleIndex: 5},
                                    { dataField:"azienda", visible : true, visibleIndex: 6},
                                    { dataField:"piva",  visible : true, visibleIndex: 7},
                                    { dataField:"livello",  visible : true, visibleIndex: 8},
                                    { dataField:"quota",  visible : true, visibleIndex: 9},
                                    { dataField:"contratto",  visible : true, visibleIndex: 10},
                                    {dataField: "numComunicazioni", visible: true, visibleIndex:11, caption: "Richieste ai territori",

                                        cellTemplate: function (container, options) {
                                            //container.addClass("img-container");
                                            var numComunicazioni = options.data.numComunicazioni;
                                            // <span class="color-black">
                                            //     <i class="material-icons">sentiment_satisfied</i>
                                            //     </span>
                                            if (numComunicazioni){
                                                var span =$("<span style='color:orange' />");
                                                span.append(numComunicazioni);
                                                span.appendTo(container);
                                            }

                                        }

                                    }


                                ],
                                dataSource: currentData.iscrizioni
                            })
                            .appendTo(mainContainer.find('.iscon'));


                        $("<div>")
                            .addClass("internal-grid-deleghe")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"region", caption:"Regione", visible : true, visibleIndex: 0},
                                    { dataField:"province", caption:"Provincia", visible : true, visibleIndex: 0},
                                    { dataField:"documentDate", dataType:"date", caption: "Data", visible : true, visibleIndex: 1},
                                    { dataField:"state", caption: "Stato",  visible : true, visibleIndex: 3},
                                    { dataField:"sector", caption: "Settore", visible : true, visibleIndex: 4},
                                    { dataField:"ente", caption: "Ente",  visible : true, visibleIndex: 5},
                                    { dataField:"operator", caption: "Operatore",visible : true, visibleIndex: 6},
                                    { dataField:"acceptDate", caption: "Data accreditamento", dataType:"date",  visible : true, visibleIndex: 7},
                                    { dataField:"cancelDate", caption: "Data annullamento", dataType:"date", visible : true, visibleIndex: 8},
                                    { dataField:"revokeDate", caption: "Data revoca", dataType:"date",  visible : true, visibleIndex: 9},
                                   // { dataField:"notes",  visible : true, visibleIndex: 10},



                                ],
                                dataSource: currentData.delegheNazionali
                            })
                            .appendTo(mainContainer.find('.delcon'));


                        $("<div>")
                            .addClass("internal-grid-nonniscrizioni")
                            .dxDataGrid({
                                columnAutoWidth: true,
                                columns: [
                                    { dataField:"liberoAl", caption:"Data", visible : true, dataType:'date'},
                                    { dataField:"nomeProvinciaFeneal", caption:"Provincia",  visible : true},
                                    { dataField:"ente", caption:"Ente bilaterale", visible : true},
                                    { dataField:"iscrittoA", caption:"Altro sindacato", visible : true },
                                    { dataField:"currentAzienda", caption:"Azienda", visible : true, visibleIndex: 5}
                                ],
                                dataSource: currentData.nonIscrizioni
                            })
                            .appendTo(mainContainer.find('.noniscon'));


                    }
                }

            }).dxDataGrid("instance");

            return grid;

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
                    pageTitle: "Fenealweb"
                },
                {
                    icon: "glyphicon glyphicon-home",
                    href: BASE
                },
                {
                    label: "Report non iscritti"
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
                //             fs: this.fullScreenForm
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


    exports.ReportLiberiAppView = ReportLiberiAppView;
    exports.ReportLiberiNewAppView = ReportLiberiNewAppView;

    return exports;

});