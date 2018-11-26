/**
 * Created by fgran on 17/06/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {

    var exports = {};

    var NonIscritti = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            NonIscritti.super.ctor.call(this, widgetName, widgetParams);
            this.chart = null;



            //imposto i valori di default se i paramteri sono nulli
            if (!this.provincia){
                var provinciaDefault = $(".noniscritti-container").find('input[name="hidden-province"]').val();
                this.provincia = provinciaDefault;

            }
            if (!this.ente){
                var enteDefault = $(".noniscritti-container").find('input[name="hidden-ente"]').val();
                this.ente = enteDefault;
            }


        },
        prepareWidget: function(){
            var self = this;

            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/noniscritticlassifica/getData");

            service.on("load",function(resp){

            $('#nonIscritti').dxDataGrid({
                    dataSource:resp,
                    columns:[

                        { dataField:"fillea", visible : true,  visibleIndex: 3},
                        { dataField:"liberi",  visible : true, visibleIndex: 1},
                        { dataField:"filca", visible : true, visibleIndex: 2},
                        { dataField:"azienda", visible : true, visibleIndex: 0,

                            cellTemplate: function (container, options) {
                                //container.addClass("img-container");
                                var name = options.data.azienda;
                                
                                //se il nome contiene & allora sotituiscoil caratter...
                               
                                

                                if (!name)
                                    return;

                                var uri = encodeURI(BASE + "#/summaryfirm/remoteIndex?description=" + name.replace("&", "*_").replace("'", "~_") );

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
                        


                    ],
                    // searchPanel: {
                    //     visible: true
                    //
                    // },
                    summary: {
                        totalItems: [{
                            column: "azienda",
                            summaryType: "count",
                            customizeText: function(data) {
                                return "Aziende trovate: " + data.value;
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
                        enabled: true,
                        fileName: "liberi",
                        allowExportSelectedData: true
                    },
                    paging:{
                        pageSize: 25
                    },
                    sorting:{
                        mode:"multiple"
                    },
                    rowAlternationEnabled: true,
                    showBorders: true,
                    allowColumnReordering:true,
                    allowColumnResizing:true,
                    columnAutoWidth: true,
                    hoverStateEnabled: true

                    

                }).dxDataGrid("instance");




            });
            service.on("error",function(e){
                $.notify.error(e);
            });
            service.load();

        },
        renderHtmlForm : function(editPanel, params){

            //questo codice commentato va decommentato quando un widget ha dei parametri
            //ovviamente va decommentato solo cio' che serve
            //qui  è implementato un widget che ha sia il parametro anno che il parmtro territorio
            //se non ci sono paramtri non c'è nulla (tutto commentato)


            //sfruttero il div.param-fields-container che valorizza entrambe le tendine
            //nella vista di ogni widget per clonare le select che mi servono e aggiungerle eventulemnte all'edit-panel


            //questa funzione astratta appende l'html del form nel pannello
            //a meno che non sia gia stato inserito nella vista....
            //e ne renderizza i paramtri

            //**************************************************
            //**************************************************
            //i paramtri hanno questo formato
            // var params = [
            //     {
            //         name: province,
            //         value:Matera
            //     },
            //     {
            //         name: year,
            //         value:2016
            //     }
            // ];

            //so che ci sarà un solo oggetto....


            //questo è il codice che puo servire.....


            //per prima cosa calcolo il valore dei parametri
            //faccio la suppoosizione che nei widget venga visualizzato sempre la select
            //delle province e poi quella dell'anno (ovviamente nel caso il widget le 
            //richiede entrambe)
            var province;
            var ente;

            if (params.length == 0){
                province = "";
                ente = "CASSA EDILE";
            }else if(params.length == 1){
                //è sicuramente la provincia  -- (attenzione sto facendo il caso che il widget richieda entrambi i parametri)
                province = params[0].value;
                ente = "CASSA EDILE";
            }else{
                province = params[0].value;
                ente = params[1].value;
            }



            var form = $("<form></form>");
            //se so che ci sarà il campo provincia recupero dal div.param-fields-container
            //un colne della select
            var provinceSelect = $(".noniscritti-container").find('div.param-fields-container').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (province)
                provinceSelect.val(province);
            else
                provinceSelect.val(provinceSelect.find('option').get(0).value);

            var enteSelect = $(".noniscritti-container").find('div.param-fields-container').find('select[name="ente"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (ente)
                enteSelect.val(ente);
            else
                enteSelect.val(enteSelect.find('option').get(0).value);
            //li aggiungo al form
            form.append(provinceSelect);
            form.append(enteSelect);
            //aggiungo il pulsante submit
            form.append("<input type='submit' value='Submit'>");

            //finalmente aggiungo all'edit-panel
            editPanel.append(form);

        }




    });


    exports.NonIscritti = NonIscritti;

    return exports;


});
