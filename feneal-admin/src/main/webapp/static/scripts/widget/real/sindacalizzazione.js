/**
 * Created by fgran on 17/05/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {

    var exports = {};

    var SindacalizzazioneWidget = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            SindacalizzazioneWidget.super.ctor.call(this, widgetName, widgetParams);
            this.chart = null;



            //imposto i valori di default se i paramteri sono nulli
            if (!this.provincia){
                var provinciaDefault = $(".sindacalizzazione-container").find('input[name="hidden-province"]').val();
                this.provincia = provinciaDefault;

            }
            if (!this.ente){
                var enteDefault = $(".sindacalizzazione-container").find('input[name="hidden-ente"]').val();
                this.ente = enteDefault;
            }


        },
        prepareWidget: function(){
            var self = this;

            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/sindacalizzazione/getData");

            service.on("load",function(resp){

                $(window).resize(function(){
                   // console.log($(self.chart.container).width());
                   //  var height = self.chart.height;
                   //  var width = $(self.chart.container).width();
                   //  var doAnimation = true;
                   //  self.chart.setSize(width, height, doAnimation);
                    //self.chart.redraw();
                });


                $(".tassoSindacalizzazione").html("Num. Lavoratori liberi: " + resp.liberi + "</br>Tasso sindacalizzazione " + resp.tassoSindacalizzazione + "%");

                var dataValues = [];

                var datas = [];



                var data = {};

                data.name = "Iscritti Feneal";
                data.data = resp.iscrittiFeneal;

                datas = [''+data.name+'',data.data];

                dataValues.push(datas);




                data = {};

                data.name = "Iscritti Filca";
                data.data = resp.iscrittiFilca;


                datas = [''+data.name+'',data.data];

                dataValues.push(datas);




                data = {};

                data.name = "Iscritti Fillea";
                data.data = resp.iscrittiFillea;

                datas = [''+data.name+'',data.data];

                dataValues.push(datas);




                // data = {};
                //
                // data.name = "Liberi";
                // data.data = resp.liberi;
                //
                // datas = [''+data.name+'',data.data];

                //dataValues.push(datas);


                // Define chart color patterns
                var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                    bgDanger, bgSuccess, bgSystem, bgDark
                ];

                var pie1 = $('#high-pie');

                if (pie1.length) {

                    // Pie Chart1
                    $('#high-pie').highcharts({
                        chart:{
                            type:'pie',
                            height: 300,
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                        },
                        credits: false,
                        title: {
                            text: 'Edilizia ' + self.provincia
                        },

                        subtitle: {
                            text: '('+ self.ente +')'
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
                            }
                        },
                        colors: highColors,
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

                    self.chart = $('#high-pie').highcharts()
                    $(pie1).children().addClass("full-width-important").css("height","283px !important;");
                }

                //$('#toggle_sidemenu_r').on("click",function(){
                //
                //    var width = $(pie1).parent().width();
                //
                //    pie1.setChartSize(width, 200, doAnimation = true);
                //
                //});

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
            var provinceSelect = $(".sindacalizzazione-container").find('div.param-fields-container').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (province)
                provinceSelect.val(province);
            else
                provinceSelect.val(provinceSelect.find('option').get(0).value);

            var enteSelect = $(".sindacalizzazione-container").find('div.param-fields-container').find('select[name="ente"]').clone();
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


    exports.SindacalizzazioneWidget = SindacalizzazioneWidget;

    return exports;


});