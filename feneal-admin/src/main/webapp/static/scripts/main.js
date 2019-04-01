window.SPA_CONTAINER = $("#spa-container"); //single page application container, where the ajax views are loaded for default

require([
    "framework/model",
    "framework/plugins",
    "framework/core",
    "framework/ui",
    "framework/views",
    "framework/controllers",
    "framework/helpers",
    "controllers",
    "users/userscontroller",
    "lavoratori/lavoratoriController",
    "listelavoro/listeLavoroController",
    "aziende/aziendeController",
    "documenti/aziendaDocumentiController",
    "reports/reportIscrittiController",
    "reports/reportLiberiController",
    "reports/reportIncassiQuoteController",
    "reports/reportDelegheController",
    "reports/reportDocumentiController",
    "reports/reportDocumentiAziendaController",
    "reports/reportComunicazioniController",
    "reports/reportRichiesteController",
    "deleghe/delegheController",
    "deleghe/accettDelegheController",
    "deleghe/inoltraDelegheController",
    "deleghe/magazzinoDelController",
    "quote/quoteImpiantiController",
    "documenti/documentiController",
    "magazzino/magazzinoDelegheController",
    "richieste/richiesteController",
    "comunicazioni/comunicazioniController",
    "versamenti/versamentiController",
    "importazioneDB/importazioneController",
    "quote/quoteAssocController",
    "framework/widgets",
    "importData/importDataController",
    "trace/traceController",
        "deleghe/delegheBariController",
    "deleghe/delegheLecceController",
    "inps/inpsLecceController",
    "reportnazionali/reportInviiDBNazionaleController",
    "reportnazionali/stampaIscrittiController","analisi/analisiController"],
    function(fmodel,_p, core, ui, fviews, fcontrollers, fhelpers,
                                                 controllers, usercontroller, 
                                                 lavController, listeLavoroController, azController, azDocController,
                                                 iscrittiReportController,
                                                 liberiReportController,
                                                 iqaReportController,
                                                 delController, docReportController, docAziendaReportController,
                                                 commReportController, richiesteReportController, delegheController, accettDelegheController,
                                                 inoltraDelegheController, magazzinoDelController, quoteImpiantiController,
                                                 docController, magazzController, ricController, comController,
                                                versController, importazController,
                                                quoteAssocController, fwidgets,
                                                importController,
             traceController, delegheBariController,
             delegheLecceController,
             inpsLecceController,
             reportInviiDBNazionaleController,
             stampaIscrittiController,
             analisiController) {

    $.datepicker.setDefaults( $.datepicker.regional[ "IT" ] );

    //register all js controllers here
    //specify singleton if controller must be a singleton
    (function registerRoutes() {
        ui.Navigation.instance().registerController("crud", function() { return new fcontrollers.CrudController(); }, "singleton");
        ui.Navigation.instance().registerController("librarydemo", function() { return new controllers.LibraryDemoController(); });
        ui.Navigation.instance().registerController("users", function() { return new usercontroller.UsersController(); }, "singleton");
        ui.Navigation.instance().registerController("dummyuser", function() { return new usercontroller.DummyUsersController(); }, "singleton");
        ui.Navigation.instance().registerController("tracelogins", function() { return new traceController.TraceLoginController(); }, "singleton");
        ui.Navigation.instance().registerController("traceactivities", function() { return new traceController.TraceActivityController(); }, "singleton");

        //percorsi per il lavoratore 
        ui.Navigation.instance().registerController("searchworkers", function() { return new lavController.LavoratoriSearchController(); }, "singleton");
        ui.Navigation.instance().registerController("summaryworker", function() { return new lavController.LavoratoreSummaryController(); }, "singleton");
        ui.Navigation.instance().registerController("editworker", function() { return new lavController.LavoratoreEditController(); }, "singleton");

        //percorsi per il crud dei documenti, delle richieste, delle comunicazioni e dello storico versamenti
        ui.Navigation.instance().registerController("documenticrud", function() { return new docController.DocumentiController(); }, "singleton");
        ui.Navigation.instance().registerController("magazzinocrud", function() { return new magazzController.MagazzinoDelegheController(); }, "singleton");
        ui.Navigation.instance().registerController("richiestecrud", function() { return new ricController.RichiesteController(); }, "singleton");
        ui.Navigation.instance().registerController("comunicazionicrud", function() { return new comController.ComunicazioniController(); }, "singleton");
        ui.Navigation.instance().registerController("versamenti", function() { return new versController.VersamentiController(); }, "singleton");
        ui.Navigation.instance().registerController("dettaglioversamenti", function() { return new versController.VersamentiDettaglioController(); }, "singleton");

        //percorsi per l'azienda
        ui.Navigation.instance().registerController("editfirm", function() { return new azController.AziendaEditController(); }, "singleton");
        ui.Navigation.instance().registerController("summaryfirm", function() { return new azController.AziendaSummaryController(); }, "singleton");
        ui.Navigation.instance().registerController("searchfirms", function() { return new azController.SearchFirmController(); }, "singleton");
        ui.Navigation.instance().registerController("firmdocscrud", function() { return new azDocController.AziendaDocumentiController(); }, "singleton");

        //percorso per liste di lavoro
        ui.Navigation.instance().registerController("searchlistelavoro", function() { return new listeLavoroController.ListeLavoroSearchController(); }, "singleton");
        ui.Navigation.instance().registerController("summarylistelavoro", function() { return new listeLavoroController.ListeLavoroSummaryController(); }, "singleton");
        ui.Navigation.instance().registerController("comparelistelavoro", function() { return new listeLavoroController.ListeLavoroComparisonController(); }, "singleton");

        //percorso per i reports
        ui.Navigation.instance().registerController("reportiscritti", function() { return new iscrittiReportController.IscrittiReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportliberi", function() { return new liberiReportController.LiberiReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportliberisuper", function() { return new liberiReportController.NewLiberiReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportiqa", function() { return new iqaReportController.IncassiQuoteReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportdeleghe", function() { return new delController.DelegheReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportdocumenti", function() { return new docReportController.DocumentiReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportdocumentiazienda", function() { return new docAziendaReportController.DocumentiAziendaReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportcomunicazioni", function() { return new commReportController.ComunicazioniReportController(); }, "singleton");
        ui.Navigation.instance().registerController("reportrichieste", function() { return new richiesteReportController.RichiesteReportController(); }, "singleton");


        // Percorsi report nazionali
        ui.Navigation.instance().registerController("inviidbnazionale", function() { return new reportInviiDBNazionaleController.ReportInviiDBNazionaleController(); }, "singleton");
        ui.Navigation.instance().registerController("stampaiscritti", function() { return new stampaIscrittiController.StampaIscrittiController(); }, "singleton");


        //Percorsi deleghe
        ui.Navigation.instance().registerController("deleghehome", function() { return new delegheController.DelegheHomeController(); }, "singleton");
        ui.Navigation.instance().registerController("editdelega", function() { return new delegheController.EditDelegaController(); }, "singleton");
        ui.Navigation.instance().registerController("deletedelega", function() { return new delegheController.DeleteDelegaController(); }, "singleton");
        ui.Navigation.instance().registerController("delegastatecontroller", function() { return new delegheController.DelegaStateController(); }, "singleton");
        ui.Navigation.instance().registerController("accettdeleghe", function() { return new accettDelegheController.AccettDelegheController(); }, "singleton");
        ui.Navigation.instance().registerController("inoltradeleghe", function() { return new inoltraDelegheController.InoltraDelegheController(); }, "singleton");
        ui.Navigation.instance().registerController("magazzinodeleghe", function() { return new magazzinoDelController.MagazzinoDelController(); }, "singleton");


        //deleghe lecce
        ui.Navigation.instance().registerController("generapredeleghe", function() { return new delegheLecceController.ReportDelegheController(); }, "singleton");
       // ui.Navigation.instance().registerController("delegheleccehome", function() { return new delegheLecceController.DelegheHomeController(); }, "singleton");
        ui.Navigation.instance().registerController("magazzinodeleghecrud", function() { return new delegheLecceController.MagazzinoDelegheController(); }, "singleton");

        //inpslecce
        ui.Navigation.instance().registerController("importainps", function() { return new inpsLecceController.IncrociaQuoteController(); }, "singleton");
        ui.Navigation.instance().registerController("ristorniinps", function() { return new inpsLecceController.RistorniQuoteController(); }, "singleton");
        ui.Navigation.instance().registerController("ristornoinps", function() { return new inpsLecceController.RistornoQuoteController(); }, "singleton");
        ui.Navigation.instance().registerController("reportquoteinps", function() { return new inpsLecceController.ReportQuoteInpsController(); }, "singleton");



       //deleghe bari
        ui.Navigation.instance().registerController("importadeleghe", function() { return new delegheController.ImportaDelegheController(); }, "singleton");

        ui.Navigation.instance().registerController("reportdeleghebari", function() { return new delegheBariController.ReportDelegheController(); }, "singleton");
        ui.Navigation.instance().registerController("proiettadeleghebari", function() { return new delegheBariController.ProiettaDelegheController(); }, "singleton");
        ui.Navigation.instance().registerController("deleghebarihome", function() { return new delegheBariController.DelegheHomeController(); }, "singleton");


        //Percorsi quote
        ui.Navigation.instance().registerController("quoteimpianti", function() { return new quoteImpiantiController.QuoteImpiantiController(); }, "singleton");
        ui.Navigation.instance().registerController("renewquoteimpianti", function() { return new quoteImpiantiController.RenewQuoteImpiantiController(); }, "singleton");
        ui.Navigation.instance().registerController("quoteassociative", function() { return new quoteAssocController.QuoteAssocController(); }, "singleton");
        ui.Navigation.instance().registerController("dettaglioquote", function() { return new quoteAssocController.QuotaDettaglioController; }, "singleton");
        ui.Navigation.instance().registerController("quotebrevimano", function() { return new quoteImpiantiController.QuoteBreviManoController(); }, "singleton");
        ui.Navigation.instance().registerController("quotemanuali", function() { return new quoteImpiantiController.QuoteManualiController(); }, "singleton");

        //Percorsi per importazione DB Nazionale
        ui.Navigation.instance().registerController("importazioneDB", function() { return new importazController.ImportazioneDBController(); }, "singleton");


        //importazioni massive
        ui.Navigation.instance().registerController("importanagrafichegenerali", function() { return new importController.ImportaAnagraficheController(); }, "singleton");
        ui.Navigation.instance().registerController("importdeleghegenerali", function() { return new importController.ImportaDelegheController(); }, "singleton");


        //analisi

        ui.Navigation.instance().registerController("riepilogo", function() { return new analisiController.RiepilogoController(); }, "singleton");
        ui.Navigation.instance().registerController("pivot", function() { return new analisiController.PivotController(); }, "singleton");



    })();


    fhelpers.AutoForm.instance().scan();

    //ajax navigation entry point
    ui.Navigation.instance().start();

    $("#sidebar_left").addClass("affix");

    fwidgets.Form.registerComponent("customFileUploader", function(element){


        var uploadButton = $(element).find("[data-sub-component=upload_button]");
        var deleteButton = $(element).find("[data-sub-component=delete_button]");
        var downloadButton = $(element).find("[data-sub-component=download_button]");
        var filenameInput = $(element).find("[data-sub-component=file_name_input]");
        var progress = $(element).find("[data-sub-component=progress]");
        var upload_value = $(element).find("[data-sub-component=upload_value]");
        var action = $(element).attr("data-action");
        var path = $(element).attr("data-path");

        var fileUploader = new fwidgets.FileUploader();

        fileUploader.set({
            url: BASE + action,
            button : uploadButton,
            progressBar: progress,
            data: { path: path },
            input: upload_value,
            filenameInput: filenameInput,
            downloadButton: downloadButton
        });

        //pulsante per rimuovere l'allegato
        deleteButton.click(function(){
            upload_value.val("");
            filenameInput.val("");
        });


        fileUploader.on("complete", function(data){

            //qui recupero il nome del file fisico e il nmome del file orginale
            var  originalFilename = filenameInput.val();
            var  physicalFilename = data.value;


            var incrocioInpsPanel = $('div.data-files-incrocio-inps');
            if (incrocioInpsPanel.length == 0)
                return;

            var dataPropertyName = $(element).closest('[data-container="customfileuploader"]').attr('data-property');
            //qui inserisco un div con i dati del nome del file, del suo percorso fisico
            //un flag per cancellarlo, uno per validarlo
            var div = $('<div class="file-incrocio data-file-container" data-filename="'+ originalFilename + '" data-physical-filename="' +  physicalFilename +'"></div>');
            var flags = $('<div class="flag-data data-icons-container">' +
                '<span class="glyphicon glyphicon-remove"></span>' +
                '<span class="glyphicon glyphicon-play-circle"></span>' +
                '</div>');

            var removeFlag= flags.find('.glyphicon-remove');
            removeFlag.click(function(){
                $(this).closest('.file-incrocio').remove();
            });

            var validateFlag= flags.find('.glyphicon-play-circle');
            validateFlag.click(function(){
                var self = $(this);
                var service = new  fmodel.AjaxService();
                service.set("contentType", "application/json");
                var data = {filename:originalFilename, filepath : physicalFilename};
                var stringified1 = JSON.stringify(data);
                service.set("data", stringified1);
                var csc = dataPropertyName == "file1"?"":"/csc"
                var route = BASE + "incrociaquoteinps/validate" + csc ;
                service.set("url", route);
                service.set("method", "POST");

                service.on('load', function(response){

                    self.removeClass('glyphicon-play-circle').addClass('glyphicon-ok');
                    validateFlag.off('click');

                    $.loader.hide({parent:'body'});
                });
                service.on("error", function(error){
                    $.loader.hide({parent:'body'});
                    self.removeClass('glyphicon-play-circle').addClass('glyphicon-ban-circle');
                    validateFlag.off('click');
                    alert("Errore: "  + error);
                });

                service.load();
                $.loader.show({parent:'body'});

            });



            div.append(originalFilename);
            div.append(flags);

            if (dataPropertyName == 'file1')
                incrocioInpsPanel.find('.inps').append(div);
            else
                incrocioInpsPanel.find('.csc').append(div);

        });


        this.callback("after-render", function() {
            fileUploader.init();
        });



    });

});