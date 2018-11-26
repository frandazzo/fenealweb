/**
 * Created by david on 19/05/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {


    var exports = {};


    var Contatore = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            Contatore.super.ctor.call(this, widgetName, widgetParams);

            //imposto i valori di default se i paramteri sono nulli
            if (!this.provincia){
                var provinciaDefault = $(".contatore-iscritti-container").find('input[name="hidden-province"]').val();
                this.provincia = provinciaDefault;

            }
            if (!this.anno){
                var annoDefault = $(".contatore-iscritti-container").find('input[name="hidden-year"]').val();
                this.anno = annoDefault;
            }




        },
        prepareWidget: function(){
            
            var self  =this;

            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/contatoreIscritti/getData");

            service.on("load",function(resp){


                var dataValues = [];
                var dataForTile = [];

                var datas = [];



                var data = {};

                data.name = "Iscritti Edili";
                data.data = resp.iscrittiEdili;

                datas = [''+data.name+'',data.data];

                dataValues.push(datas);
                dataForTile.push(data);




                data = {};

                data.name = "Iscritti Impianti fissi";
                data.data = resp.iscrittiImpiantiFissi;


                datas = [''+data.name+'',data.data];

                dataValues.push(datas);
                dataForTile.push(data);



                data = {};

                data.name = "Iscritti Inps";
                data.data = resp.iscrittiInps;

                datas = [''+data.name+'',data.data];

                dataValues.push(datas);
                dataForTile.push(data);


                // for(var i=0;i<dataForTile.length;i++){
                //
                //     var elemToAppend = $(".containerTileCI").find(".elemToAppend").clone().appendTo(".containerValuesTileCI");
                //     elemToAppend.find(".info").text(dataForTile[i].name + ' ' + dataForTile[i].data);
                //
                // }


                // Define chart color patterns
                var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                    bgDanger, bgSuccess, bgSystem, bgDark
                ];

                var pie1 = $('#high-pie-contatore-iscritti');

                if (pie1.length) {

                    // Pie Chart1
                    $('#high-pie-contatore-iscritti').highcharts({
                        chart:{
                            type:'pie',
                            height: 300,
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                        },
                        credits: false,
                        title: {
                            text: 'Iscritti ' +  self.provincia
                        },
                        subtitle: {
                            text: '(anno ' + self.anno + ')'
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                center: ['30%', '50%'],
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: true
                            },
                            series: {
                                dataLabels: {
                                    enabled: true,
                                    formatter: function() {
                                        return this.point.name.replace("Iscritti", "") + " " +  Math.round(this.percentage*100)/100 + ' %';
                                    },
                                    distance: -30,
                                    color:'black'
                                }

                            // series: {
                            //     dataLabels: {
                            //         enabled: true,
                            //         formatter: function() {
                            //             return Math.round(this.percentage*100)/100 + ' %';
                            //         },
                            //         distance: -30,
                            //         color:'black'
                            //     }
                            }
                        },

                        colors: highColors,
                        //legend: {
                        //    x: 30,
                        //    floating: true,
                        //    verticalAlign: "middle",
                        //    layout: "vertical",
                        //    align: 'right',
                        //    itemMarginTop: 20,
                        //    useHTML: true,
                        //    labelFormatter: function() {
                        //        return '<div class="panel-tile text-center elemToAppend col-md-12 col-xs-12">' +
                        //                    '<div class="panel-body bg-light">' +
                        //                        '<span class="mbn enteText">' + this.name + ' '  + this.y + '</span></div>' +
                        //                    '<div class="panel-footer bg-light dark br-t br-light p12">' +
                        //                        '<span class="fs11">Anno</span>' +
                        //                    '</div>' +
                        //                '</div>';
                        //    }
                        //},
                        legend: {
                            x: 20,
                            floating: true,
                            verticalAlign: "middle",
                            layout: "vertical",
                            align: 'right',
                            itemMarginTop: 10,
                            //useHTML: true,
                            labelFormatter: function() {
                                return '<div style="text-align: left; width:160px;">' + this.name + ' ' +  this.y + '</div>';
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: 'Percentuale',
                            data: dataValues
                        }]
                    });
                }

                $('#high-pie-contatore-iscritti').children().addClass("full-width-important");

            });
            service.on("error",function(e){
                alert(e);
            });
            service.load();

        },
        renderHtmlForm : function(editPanel, params){

            var province;
            var year;

            if (params.length == 0){
                province = "";
                year = new Date().getFullYear();
            }else if(params.length == 1){
                //è sicuramente la provincia  -- (attenzione sto facendo il caso che il widget richieda entrambi i parametri)
                province = params[0].value;
                year = new Date().getFullYear();
            }else{
                province = params[0].value;
                year = params[1].value;
            }



            var form = $("<form></form>");
            //se so che ci sarà il campo provincia recupero dal div.param-fields-container
            //un colne della select
            var provinceSelect = $(".contatore-iscritti-container").find('div.param-fields-container').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (province)
                provinceSelect.val(province);
            else
                provinceSelect.val(provinceSelect.find('option').get(0).value);

            //stessa cosa per l'anno
            var yearSelect = $(".contatore-iscritti-container").find('div.param-fields-container').find('select[name="year"]').clone();
            //recuperato il riferimento alla select ne imposto il valore
            yearSelect.val(year);
            //li aggiungo al form
            form.append(provinceSelect);
            form.append(yearSelect);
            //aggiungo il pulsante submit
            form.append("<input type='submit' value='Submit'>");

            //finalmente aggiungo all'edit-panel
            editPanel.append(form);

        }




    });


    exports.contatore = Contatore;

    return exports;


});