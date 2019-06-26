/**
 * Created by angelo on 16/11/2017.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/ui",
    "framework/widgets", "framework/plugins", "reports/reportResultsConfigurer"],
    function(core, fmodel, fviews, ui, widgets, plugins, resultsConfigurer) {

    var exports = {};

    var ReportRisorseRemoteView = fviews.RemoteContentView.extend({
        ctor: function(service){
            ReportRisorseRemoteView.super.ctor.call(this, service);

            var self = this;


            this.on("load", function(){

                function retrieveContactTokens(){
                    var deferred = $.Deferred();

                    var jsonData  = JSON.stringify( {request:{Key: "", Token: "23508d4b-a72e-4b20-be6c-90e1de7f6d71", Output: "json", Requests:[ {module: "surveysnapshot",method: "get", id:"166"} ]}});
                    $.ajax({
                        url: "https://www.fenealweb.it/sharetop/Services/Application/TurnigestService.asmx/MainService",
                        method : "POST",
                        data: jsonData,
                        contentType: "application/json; charset=utf-8"
                    })
                        .done(function(data, textStatus, jqXHR ) {

                            var contacts = [];
                            data.d.Responses[0].Data[0].Contacts.forEach(function(element){
                                contacts.push({
                                    externaleToken: element.ExternalToken,
                                    contactName: element.ContactName,
                                    compiled : element.Compiled
                                });
                            });
                            deferred.resolve(contacts)
                        });
                    return deferred.promise();
                }

                function retrieveCompiledRisorseUmaneSurveyIdForContact(token){

                    var deferred = $.Deferred();

                    var jsonDataQuestion = '{Key: "", Output: "json",Requests: [{module: "compiledsurvey", method: "GET", token: "'+token+'"}]}';
                    $.ajax({
                        url: "https://www.fenealweb.it/sharetop/Services/Application/GuestHandler.ashx",
                        method : "POST",
                        data: jsonDataQuestion,
                        contentType: "application/json; charset=utf-8"
                    })
                        .done(function(data, textStatus, jqXHR ) {
                            var compiledSurveyes=JSON.parse(data);
                            var l = compiledSurveyes.d.Responses[0].Data;

                            var result = null;

                            l.forEach(function(element){
                                if(element.Description === "RISORSE UMANE 2019"){
                                    result= {
                                        surveyId: element.Id,
                                        token: token
                                    };
                                    return false;
                                }
                            });

                            deferred.resolve(result);

                        });

                    return deferred.promise();

                }

                function retrieveCompiledRisorseUmaneSurveyForContact(surveyInfo){

                    var deferred = $.Deferred();


                    var jsonDataRep= '{"Key": "", "Token": "", "Output": "json", "Requests":[ {"module": "survey","method": "GET", "id":"'+surveyInfo.surveyId+'", "token":"'+surveyInfo.token+'"}]}';
                    $.ajax({
                        url: "https://www.fenealweb.it/sharetop/Services/Application/GuestHandler.ashx",
                        method : "POST",
                        data: jsonDataRep,
                        contentType: "application/json; charset=utf-8"
                    })
                        .done(function(data, textStatus, jqXHR ) {
                            var c = JSON.parse(data);
                            var l = c.d.Responses[0].Data[0].QuestionAnswers[1].Rows;

                            var nominativo = c.d.Responses[0].Data[0].QuestionAnswers[0].StringAnswer
                            var result = [];

                            l.forEach(function (element) {
                                if (element.QuestionAnswers[0].StringAnswer) {

                                    result.push({
                                        nominativoReg: nominativo,
                                        nominativoRis: element.QuestionAnswers[0].StringAnswer,
                                        codFiscale: element.QuestionAnswers[1].StringAnswer,
                                        tipRapp: element.QuestionAnswers[2].StringAnswer,
                                        livInq: element.QuestionAnswers[3].StringAnswer,
                                        inden: element.QuestionAnswers[4].StringAnswer,
                                        ruoloOrg: element.QuestionAnswers[5].StringAnswer,
                                        prev: element.QuestionAnswers[6].StringAnswer,
                                        tfrAcconto: element.QuestionAnswers[7].StringAnswer
                                    });
                                }
                            });
                            deferred.resolve(result);

                        });



                    return deferred.promise();
                }

                    $("#buttonSearch").click(function() {
                        $.loader.show({parent:'body'});
                        var idT = $("#idRisorse").find("option:selected").attr("value");

                        if(idT === "default"){

                            var contactTokenPromise = retrieveContactTokens();

                            contactTokenPromise.done(function(contactTokens){

                                console.log(contactTokens);

                                var promises = [];
                                contactTokens.forEach(function(element){
                                    promises.push(retrieveCompiledRisorseUmaneSurveyIdForContact(element.externaleToken));
                                });
                                $.when.apply($,promises).done(function(){
                                    var arg = Array.prototype.slice.call(arguments);


                                    var promisesComplete = [];
                                    arg.forEach(function(element) {
                                        if(element)
                                            promisesComplete.push(retrieveCompiledRisorseUmaneSurveyForContact(element));
                                    });
                                    $.when.apply($, promisesComplete).done(function () {
                                        console.log(arguments);
                                        var argResult = Array.prototype.slice.call(arguments);

                                        var dataSource = [];
                                        argResult.forEach(function (element) {
                                            element.forEach(function (elem) {
                                                dataSource.push(elem);
                                            })
                                        })
                                        $.loader.hide({parent:'body'});

                                        var grid = $("#reportContainerRisorse").dxDataGrid({
                                            dataSource:dataSource,
                                            columns:[
                                                { dataField:"nominativoReg", visible : true, caption:"Regione"},
                                                { dataField:"nominativoRis", visible : true, caption:"Nominativo"},
                                                { dataField:"codFiscale", visible : true, caption:"Codice fiscale"},
                                                { dataField:"tipRapp", visible : true, caption:"Tipo rapporto"},
                                                { dataField:"livInq", visible : true, caption:"Livello inquadramento"},
                                                { dataField:"inden", visible : true, caption:"Indennita"},
                                                { dataField:"ruoloOrg", visible : true, caption:"Ruolo organizzativo"},
                                                { dataField:"prev", visible : true, caption:"previdenza integrativa"},
                                                { dataField:"tfrAcconto", visible : true, caption:"Tfr accantonato"}
                                            ],
                                            "export": {
                                                enabled: false,
                                                fileName: "risorse_umane2019",
                                                allowExportSelectedData: true
                                            },
                                            summary: {
                                                totalItems: [{
                                                    column: "nominativoReg",
                                                    summaryType: "count",
                                                    customizeText: function(data) {
                                                        return "Risorse umane presenti: " + data.value;
                                                    }
                                                }]
                                            },
                                            paging:{
                                                pageSize: 30
                                            },
                                            showBorders: true,
                                            rowAlternationEnabled: true,
                                            allowColumnReordering:true,
                                            allowColumnResizing:true,
                                            columnAutoWidth: true,
                                        }).dxDataGrid("instance");
                                        var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "risorse", true);
                                        reportResultsConfigurer.init();
                                    });

                                });
                            });
                        }
                        else{

                            var idT = $("#idRisorse").find("option:selected").attr("value");

                            var promises=[]
                            promises.push(retrieveCompiledRisorseUmaneSurveyIdForContact(idT));
                            $.when.apply($,promises).done(function(){
                                    var arg = Array.prototype.slice.call(arguments);

                                if (arg[0] === null) {
                                    $.loader.hide({parent:'body'});
                                    $.notify.error("Non ci sono risultati utili");
                                    return false;
                                }
                                else{
                                    var promisesComplete = [];
                                    arg.forEach(function(element) {
                                        if(element)
                                            promisesComplete.push(retrieveCompiledRisorseUmaneSurveyForContact(element));
                                    });
                                    $.when.apply($, promisesComplete).done(function () {
                                        console.log(arguments);
                                        var argResult = Array.prototype.slice.call(arguments);

                                        var dataSource = [];
                                        argResult.forEach(function (element) {
                                            element.forEach(function (elem) {
                                                dataSource.push(elem);
                                            })
                                        })

                                        $.loader.hide({parent:'body'});

                                        var grid = $("#reportContainerRisorse").dxDataGrid({
                                            dataSource:dataSource,
                                            columns:[
                                                { dataField:"nominativoReg", visible : true, caption:"Regione"},
                                                { dataField:"nominativoRis", visible : true, caption:"Nominativo"},
                                                { dataField:"codFiscale", visible : true, caption:"Codice fiscale"},
                                                { dataField:"tipRapp", visible : true, caption:"Tipo rapporto"},
                                                { dataField:"livInq", visible : true, caption:"Livello inquadramento"},
                                                { dataField:"inden", visible : true, caption:"Indennita"},
                                                { dataField:"ruoloOrg", visible : true, caption:"Ruolo organizzativo"},
                                                { dataField:"prev", visible : true, caption:"previdenza integrativa"},
                                                { dataField:"tfrAcconto", visible : true, caption:"Tfr accantonato"}
                                            ],
                                            "export": {
                                                enabled: false,
                                                fileName: "risorse_umane2019",
                                                allowExportSelectedData: true
                                            },
                                            summary: {
                                                totalItems: [{
                                                    column: "nominativoReg",
                                                    summaryType: "count",
                                                    customizeText: function(data) {
                                                        return "Risorse umane presenti: " + data.value;
                                                    }
                                                }]
                                            },
                                            paging:{
                                                pageSize: 35
                                            },
                                            selection: {
                                                mode: "multiple"
                                            },
                                            showBorders: true,
                                            rowAlternationEnabled: true,
                                            allowColumnReordering:true,
                                            allowColumnResizing:true,
                                            columnAutoWidth: true,
                                        }).dxDataGrid("instance");

                                        var reportResultsConfigurer = new resultsConfigurer.ReportUiConfigurer(grid, "risorse", true);
                                        reportResultsConfigurer.init();

                                    });
                                }
                            });
                        }
                    });

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
        createBreadcrumbs: function() {
            var items = this.getBreadcrumbItems();

            var $b = $("#breadcrumbs");
            if(!$b.breadcrumbs("isBreadcrumbs")) {
                $b.breadcrumbs();
            }

            $b.breadcrumbs('clear');
            $b.breadcrumbs('addAll', items);
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
                    label: "Sharetop Risorse umane 2019"
                }
            ];
        }

    });

    exports.ReportRisorseRemoteView = ReportRisorseRemoteView;

    return exports;

});



// $("#buttonSearch").click(function(){
//
//     //chiamata per prendere l'id delle risorse
//
//     //estraggo l'externalToken
//     var idT = $("#idRisorse").find("option:selected").attr("value");
//
//     var jsonDataQuestion = '{Key: "", Output: "json",Requests: [{module: "compiledsurvey", method: "GET", token: "'+idT+'"},{module: "uncompiledsurveysnapshot", method: "GET", token: "'+idT+'"}], Token: "" }';
//
//     $.ajax({
//         url: "https://www.fenealweb.it/sharetop/Services/Application/GuestHandler.ashx",
//         method : "POST",
//         data: jsonDataQuestion,
//         contentType: "application/json; charset=utf-8"
//     })
//
//         .done(function(data,textStatus,jqXHR){
//
//             var a=JSON.parse(data);
//             var l = a.d.Responses[0].Data.length;
//
//             for(var i=0; i < l;i++){
//                 if(a.d.Responses[0].Data[i].Description === "RISORSE UMANE 2019")
//                     var idSession = a.d.Responses[0].Data[i].Id;
//             }
//
//             //estraggo l'externalToken
//             var idT = $("#idRisorse").find("option:selected").attr("value");
//
//             //chiamata per la lista di operatori
//             var jsonDataRep= '{"Key": "", "Token": "", "Output": "json", "Requests":[ {"module": "survey","method": "GET", "id":"'+idSession+'", "token":"'+idT+'"}]}';//JSON.stringify({Key: "", Output: "json", Requests: [{module: "survey", method: "GET", id: "1653", token:idT}], Token: ""})
//
//             $.ajax({
//                 url: "https://www.fenealweb.it/sharetop/Services/Application/GuestHandler.ashx",
//                 method : "POST",
//                 data: jsonDataRep,
//                 contentType: "application/json; charset=utf-8"
//             })
//                 .done(function(data, textStatus, jqXHR ){
//                     var c = JSON.parse(data);
//                     var l = c.d.Responses[0].Data[0].QuestionAnswers[1].Rows.length;
//
//                     var array = [];
//
//                     for(var i=0;i < l; i++){
//                         if(c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[0].StringAnswer){
//
//                             var risorseUmaneObj = {
//                                 nominativoReg:c.d.Responses[0].Data[0].QuestionAnswers[0].StringAnswer,
//                                 nominativoRis:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[0].StringAnswer,
//                                 codFiscale:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[1].StringAnswer,
//                                 tipRapp:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[2].StringAnswer,
//                                 livInq:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[3].StringAnswer,
//                                 inden:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[4].StringAnswer,
//                                 ruoloOrg:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[5].StringAnswer,
//                                 prev:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[6].StringAnswer,
//                                 tfrAcconto:c.d.Responses[0].Data[0].QuestionAnswers[1].Rows[i].QuestionAnswers[7].StringAnswer
//                             };
//
//                             array.push(risorseUmaneObj);
//                         }
//                     }
//
//
//                             $("#reportContainerRisorse").dxDataGrid({
//                                 dataSource:array,
//                                 columns:[
//                                     { dataField:"nominativoReg", visible : true, caption:"Regione"},
//                                     { dataField:"nominativoRis", visible : true, caption:"Nominativo"},
//                                     { dataField:"codFiscale", visible : true, caption:"Codice fiscale"},
//                                     { dataField:"tipRapp", visible : true, caption:"Tipo rapporto"},
//                                     { dataField:"livInq", visible : true, caption:"Livello inquadramento"},
//                                     { dataField:"inden", visible : true, caption:"Indennità"},
//                                     { dataField:"ruoloOrg", visible : true, caption:"Ruolo organizzativo"},
//                                     { dataField:"prev", visible : true, caption:"previdenza integrativa"},
//                                     { dataField:"tfrAcconto", visible : true, caption:"Tfr accantonato"}
//                                 ],
//                                 "export": {
//                                     enabled: true,
//                                     fileName: "risorse_umane2019",
//                                     allowExportSelectedData: true
//                                 },
//                                 selection:{
//                                     mode:"multiple",
//                                     showCheckBoxesMode: "always"
//                                 },
//                                 summary: {
//                                     totalItems: [{
//                                         column: "nominativoReg",
//                                         summaryType: "count",
//                                         customizeText: function(data) {
//                                             return "Risorse umane presenti: " + data.value;
//                                         }
//                                     }]
//                                 },
//                                 paging:{
//                                     pageSize: 30
//                                 },
//                                 showBorders: true,
//                                 rowAlternationEnabled: true,
//                                 allowColumnReordering:true,
//                                 allowColumnResizing:true,
//                                 columnAutoWidth: true,
//                             }).dxDataGrid("instance");
//
//                 })
//
//                 .fail(function(jqXHR, textStatus, errorThrown) {
//                     alert( "error" );
//                 });
//
//         })
//
//
//         .fail(function(jqXHR, textStatus, errorThrown) {
//             alert( "error" );
//         });
//
// });
