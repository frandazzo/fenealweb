/**
 * Created by david on 02/06/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {


    var exports = {};


    var Andamento = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            Andamento.super.ctor.call(this, widgetName, widgetParams);

            if (!this.provincia){
                var provinciaDefault = $(".andamento-iscritti-settore-edile-container").find('input[name="hidden-province"]').val();
                this.provincia = provinciaDefault;

            }
        },
        prepareWidget: function(){
            var self = this;

            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/andamentoIscrittiPerSettoreEdile/getData");

            service.on("load",function(resp){

                $('.andamento-iscritti-settore-edile-container').find("#container-highcharts").highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: 'Iscritti ' + self.provincia
                    },
                    subtitle: {
                        text: '(per ente)'
                    },
                    xAxis: {
                        categories: resp.anni,
                        crosshair: true,
                        title: {
                            text: 'Anni'
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: 'Numero iscritti'
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y} iscritti</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series: resp.values
                });

                $('.andamento-iscritti-settore-edile-container').find("#container-highcharts").children().addClass("full-width-important");

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
            }else if(params.length == 1){
                //è sicuramente la provincia  -- (attenzione sto facendo il caso che il widget richieda entrambi i parametri)
                province = params[0].value;
            }else{
                province = params[0].value;
            }



            var form = $("<form></form>");
            //se so che ci sarà il campo provincia recupero dal div.param-fields-container
            //un colne della select
            var provinceSelect = $(".andamento-iscritti-settore-edile-container").find('div.param-fields-container').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (province)
                provinceSelect.val(province);
            else
                provinceSelect.val(provinceSelect.find('option').get(0).value);


            //aggiungo al form
            form.append(provinceSelect);
            //form.append(yearSelect);
            //aggiungo il pulsante submit
            form.append("<input type='submit' value='Submit'>");

            //finalmente aggiungo all'edit-panel
            editPanel.append(form);

        }




    });


    exports.andamento = Andamento;

    return exports;


});