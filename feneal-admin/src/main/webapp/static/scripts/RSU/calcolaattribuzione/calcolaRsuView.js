define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/plugins",
    "framework/webparts"], function(core, fmodel, fviews, ui, plugins, webparts) {
    var exports = {};
    var ReportRsuHomeRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service, sedeId,firmId){
            ReportRsuHomeRemoteView.super.ctor.call(this, service);

            var self = this;
            this.saveResponse;
            this.on("load", function(){



                var container = document.getElementById("datigenerali");
                var formService = new fmodel.FormService();
                formService.set("method", "GET");
                formService.set("data", {});
                formService.set("url", BASE + "reportrsu/datigenerali/"+sedeId);


                var formView = new fviews.FormView(formService);
                formView.container = container;

                formView.on("render", function() {
                    $(".dati-generali-elezione").find(".panel-footer, .panel-heading").hide();
                    $(".content-header pb20").hide();
                    $(".panel-body").css("overflow", "hidden");
                });

                formView.show();

                formView.on("load", function () {

                    // //qui attacco levento on change della select delle aziende per la visualizzazione delle sedi
                    // $('input[name="firmrsu"]').change(function(){
                    //     var selectedVal = $(this).val();
                    //     self.loadSediRsu(selectedVal, "", $('select[name="sedersu"]'));
                    // });
                });


                $("#avanti-datigenerali").click(function () {

                        var data = {
                            dto: this.saveResponse,
                            firmRsu: firmId,
                            sedeRsu: sedeId,
                            anno: $('select[name="annofromYearReport"]').val()
                        };

                        var svc = new fmodel.AjaxService();
                        svc.set("method", "POST");
                        svc.set("data", JSON.stringify(data));
                        svc.set("contentType", "application/json");
                        svc.set("url", BASE + "reportrsu/datigenerali");



                        svc.on("load", function(response){
                            this.saveResponse = response;
                            $.loader.hide({parent:'body'});

                            var result = self.validateDatiGenerali(response);

                            if(result.errors.length == 0){

                                $.notify.success("Operazione completata");

                                var x = document.getElementById("listepresentate");
                                if (x.style.display === "none") {
                                    x.style.display = "block";
                                    $("#avanti-datigenerali").attr("disabled", "disabled");


                                    var grid = self.initGridListe(response.liste);

                                    $("#add_list").click(function () {
                                        var formService = new fmodel.FormService();
                                        formService.set("method", "GET");
                                        formService.set("data", {});
                                        formService.set("url", BASE + "reportrsu/createlista");

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


                                                        var svc = new  fmodel.AjaxService();

                                                        var data = {
                                                            dto: response,
                                                            nome: nome,
                                                            firmataria:firmataria
                                                        };

                                                        svc.set("url", BASE + "reportrsu/createlista");
                                                        svc.set("data", JSON.stringify(data));
                                                        svc.set("contentType", "application/json");
                                                        svc.set("method", "POST");

                                                        svc.on("load", function(responseFromList){
                                                            $.loader.hide({parent:'body'});

                                                            this.saveResponse = responseFromList;

                                                            response.liste = responseFromList.liste;
                                                            response.validationError = responseFromList.validationError;
                                                            console.log(response);
                                                            var result = self.validateDatiGenerali(response);

                                                            if(result.errors.length == 0){

                                                                $("#gridListe")
                                                                    .dxDataGrid("instance")
                                                                    .option("dataSource", responseFromList.liste);
                                                            }

                                                            dialog.modalDialog("close");
                                                            $.loader.hide({parent:'body'});
                                                        });
                                                        svc.on("error", function(error){
                                                            $.loader.hide({parent:'body'});
                                                            $.notify.error(error);
                                                        });

                                                        svc.load();

                                                        $(dialog).modalDialog("close");
                                                    }
                                                }
                                            }
                                        });
                                    });
                                } else {
                                    x.style.display = "none";
                                }
                            }else{
                                return;
                            }

                        });


                        svc.on("error", function(error){
                            $.loader.hide({parent:'body'});
                            alert("Errore: "  + error);
                        });

                        svc.load();
                        $.loader.show({parent:'body'});



                });


                $("#avanti2").click(function () {
                    var x = document.getElementsByClassName("div2");
                    if (x.style.display === "none") {
                        x.style.display = "block";
                        $("#avanti2").attr("disabled", "disabled");
                    } else {
                        x.style.display = "none";
                    }
                });


                $("#avanti3").click(function () {
                    alert("report inviato");
                });

                $("#indietro3").click(function () {
                    var x = document.getElementById("div3");
                    if (x.style.display === "block") {
                        x.style.display = "none";
                        $("#avanti2").prop('disabled', false);
                    } else {
                        x.style.display = "block";
                    }
                });

                $("#indietro-liste").click(function () {
                    var x = document.getElementById("listepresentate");
                    if (x.style.display === "block") {
                        x.style.display = "none";
                        $("#avanti-datigenerali").prop('disabled', false);
                    } else {
                        x.style.display = "block";
                    }
                });


                self.createToolbar();
                self.createBreadcrumbs();



            });

        },
        validateDatiGenerali: function(response){
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
        initGridListe: function(responseData){
            var grid = $('#gridListe').dxDataGrid({
                dataSource:responseData,
                columns:[
                    { dataField:"name",caption:"Nome lista", visible : true},
                    { dataField:"firmataria", caption:"Firmataria CCNL",visible : true,
                        cellTemplate: function (container, options) {
                            //container.addClass("img-container");
                            var isActive = options.data.firmataria;

                            // <span class="color-black">
                            //     <i class="material-icons">sentiment_satisfied</i>
                            //     </span>
                            if (isActive){
                                var span =$("<span style='font-size: 20px;'></span>");
                                span.append($('<i class="fa fa-check" style=" color:green;padding: 10px;"></i>'));
                                span.appendTo(container);
                            }else{
                                var span =$("<span style='font-size: 20px;'></span>");
                                span.append($('<i class="fa fa-times" style="color:red; padding: 10px;"></i>'));
                                span.appendTo(container);
                            }

                        }}
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
                    mode:"single"
                },
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
