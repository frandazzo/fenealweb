/**
 * Created by david on 22/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui"], function(core, fmodel, fviews, ui) {


    var exports = {};


    //oggetto che inizializza la tootlbar delle operazioni che è possibile fare sulle griglie dei reports
    var ResultActionsToolbar = core.AObject.extend({
        ctor: function(toolbarElement, dxGrid, context, toTraceExport){
            ResultActionsToolbar.super.ctor.call(this);

            //elemento jquery che contiene tutti i pulsanti per operare sui dati e sulla griglia
            this.toolbarElement = toolbarElement;
            this.dxGrid = dxGrid;
            this.context = context;
            this.toTraceExport = toTraceExport;

            this.groupPanel = false;
            this.searchPanel = false;
            this.filterRow = false;
            this.headerFilter = false;

            this.showColumn = false;

        },
        init: function(){
            var self = this;


            $('.excel-export').off('click');
            $('.excel-export').on('click',function() {

                self.dxGrid.exportToExcel(false);

                /* Se indicato  provvedo a tracciare l'attività di export (indicazione dei C.F.) */
                if (self.toTraceExport) {
                    var rows = self.dxGrid.option('dataSource');

                    var fiscalCodeRows = [];
                    if (self.context == "liste lavoro") {
                        $.each(rows, function (i, o) {
                            fiscalCodeRows.push(o.fiscalcode);
                        });
                    } else {
                        $.each(rows, function (i, o) {
                            fiscalCodeRows.push(o.lavoratoreCodiceFiscale);
                        });
                    }

                    var svc = new fmodel.AjaxService();
                    svc.set("url", BASE + "trace/exportexcel");
                    svc.set("contentType", "application/json");
                    svc.set("data", JSON.stringify({fiscalCodeRows: fiscalCodeRows, context: self.context}));

                    svc.set("method", "POST");

                    svc.on("load", function (response) {
                        //
                    });
                    svc.on("error", function (error) {
                        $.notify.error(error);
                    });

                    svc.load();
                }
            });

            if (self.context == "documenti azienda"){
                $('.worker-list-export').hide();
            }else{
                $('.worker-list-export').off('click');
                $('.worker-list-export').on('click',function(){
                    //alert('ciao worker-list-export: ' + self.context);

                    var container = $('<div class="create-worker-list-cnt"><span class="text-bold">Descrizione</span><input type="text" class="field gui-input mt5" name="descrList"></div>');

                    var dialog = container.modalDialog({
                        autoOpen: true,
                        title: "Crea lista di lavoro",
                        destroyOnClose: true,
                        height: 120,
                        width: 300,
                        buttons:{
                            Crea: {
                                primary: true,
                                command: function() {
                                    var descrListaLavoro = container.find("input[name=descrList]").val();
                                    var selectedrows = self.dxGrid.getSelectedRowsData();

                                    var svc = new  fmodel.AjaxService();
                                    var data = {};
                                    data.selectedrows = selectedrows;
                                    svc.set("url", BASE + "listalavoro/"+self.context.replace(" ", "")+"/"+encodeURIComponent(descrListaLavoro));
                                    svc.set("contentType", "application/json");
                                    svc.set("data", JSON.stringify(selectedrows));
                                    svc.set("method", "POST");

                                    svc.on("load", function(response){
                                        $.loader.hide({parent:'body'});
                                        dialog.modalDialog("close");

                                        // response è l'id della lista lavoro da visualizzare
                                        ui.Navigation.instance().navigate("summarylistelavoro", "index", {
                                            id: response
                                        });

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
            }




            $('.group-report-data').off('click');
            $('.group-report-data').on('click',function(){

                if (self.groupPanel == true)
                    self.groupPanel = false;
                else
                    self.groupPanel = true;


                self.dxGrid.option('groupPanel.visible', self.groupPanel);
            });


            $('.search-report-data').off('click');
            $('.search-report-data').on('click',function(){
                if (self.searchPanel == true)
                    self.searchPanel = false;
                else
                    self.searchPanel = true;


                self.dxGrid.option('searchPanel.visible', self.searchPanel);
                if (self.searchPanel)
                    self.dxGrid.option('searchPanel.width', 300);
            });


            $('.search-report-columns').off('click');
            $('.search-report-columns').on('click',function(){
                if (self.filterRow == true)
                    self.filterRow = false;
                else
                    self.filterRow = true;


                self.dxGrid.option('filterRow.visible', self.filterRow);

            });


            $('.filter-report-data').off('click');
            $('.filter-report-data').on('click',function(){
                if (self.headerFilter == true)
                    self.headerFilter = false;
                else
                    self.headerFilter = true;


                self.dxGrid.option('headerFilter.visible', self.headerFilter);
            });


            $('.select-report-columns').off('click');
            $('.select-report-columns').on('click',function(){

                self.dxGrid.showColumnChooser();

            });
        }
    });


    //inizializza tutti i comportamenti per la renderizzazione e corretta navigazione dei risultati del
    //report
    var ReportScrollView = core.AObject.extend({
        ctor: function(dxGrid){
            ReportScrollView.super.ctor.call(this);

            this.dxGrid = dxGrid;

            //in particolare verranno gestiti due componeneti
            // -- il pulsante per scrollare in alto quando non è piu' visibile l'intestazione della griglia
            // -- la toolbox delle action che è possibile effettuare sui dati
            //il meccanismo di funzionamento della toolbar renderà la toolbox fixed allorquando scorro nelle righe della griglia


        },
        init: function(){


            $(window).resize(function(){

                if($(window).width()<=992){
                    $(".toolbox-seach-report").hide();
                    $(".toolbox-seach-report-xs").show();
                }
                else{
                    $(".toolbox-seach-report").show();
                    $(".toolbox-seach-report-xs").hide();
                }

            });


            var isSmXs = false;

            if($(window).width()<=992)
                isSmXs = true;

            //l'inizializzazione dipenda dal numeri di righe trovate
            //se non ce nè nessuna allora non inizializzo nulla..
            if (!this.dxGrid.option('dataSource').length)
                return;

            //inizializzo qui l'immagine del pulsantino che mi farà scrollare in alto quando sono troppo in fondo
            //alla griglia
            $(".buttonScrollTop").attr("src", STATIC_BASE + "images/buttonScrollTop.png");

            //visualizzo la toolbox e gestisco il suo posizionamento allo scrollare del report
            if(!isSmXs)
                $(".toolbox-seach-report").show();
            else
                $(".toolbox-seach-report-xs").show();

            if(!isSmXs){
                var scrollTop = $(".toolbox-seach-report").offset().top - 60;
                var width_fast_summary = $(".toolbox-seach-report").width();
            }


            $(window).scroll(function() {

                if($(window).scrollTop() >= scrollTop) {

                    if(!isSmXs){

                        $(".toolbox-seach-report").css('top', "65px");

                        if($("#sidebar_right").css("right")=="0px")
                            $(".toolbox-seach-report").css('right', $("#sidebar_right").width() + 'px');
                        else
                            $(".toolbox-seach-report").css('right', '0px');

                        $(".toolbox-seach-report").css('position', "fixed");
                        $(".toolbox-seach-report").width(width_fast_summary);

                    }

                }
                else if($(window).scrollTop() < scrollTop) {

                    if(!isSmXs){

                        $(".toolbox-seach-report").css('position', '');
                        $(".toolbox-seach-report").css('top', "");
                        $(".toolbox-seach-report").css('bottom', "");
                        $(".toolbox-seach-report").css('right', "");
                        $(".toolbox-seach-report").css('width', "");

                    }

                }

                //immagine che cliccandoci torna su la schermata
                var actualScroll = $(window).scrollTop();

                if(actualScroll>scrollTop){
                    $(".buttonScrollTop").show();
                }
                else{
                    $(".buttonScrollTop").hide();
                }

            });

            //quando clicco sul pulsantino che mi fa andare in alto....
            $(".buttonScrollTop").on("click",function(){

                $('html, body').animate({scrollTop: $("#reportContainer").offset().top - 80}, 1400, "swing");

            });


        }
    });

    //oggetto per la configurazione dell'interfaccia dei risultati del report
    var ReportUiConfigurer = core.AObject.extend({

        ctor: function (dxGrid, context) {
            ReportUiConfigurer.super.ctor.call(this);

            //contesto nel quale esegiuo il report
            //ad esempio : iscritti, libedri, comunicazioni ecc
            this.context = context;

            //questo è l'oggetto griglia su cui verranno eseguite le operazioni
            //di rendering e le eventuali actions
            this.dxGrid = dxGrid;
        },

        init: function () {
            var self = this;

            //inizializzo la scrollview
            var scrollView = new ReportScrollView(self.dxGrid);
            scrollView.init();

            //inizializzo container della toolbox
            var toolboxContainer = $('.toolbox-seach-report');
            var toolbar = new ResultActionsToolbar(toolboxContainer, self.dxGrid, self.context);
            toolbar.init();

        }

    });

    exports.ReportUiConfigurer = ReportUiConfigurer;

    return exports;


});