/**
 * Created by david on 05/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var PieCharts = core.AObject.extend({

        ctor: function () {
            PieCharts.super.ctor.call(this);

        },

        init: function (data) {


            var dataValues = [];

            for(var i=0;i<data.rows.length;i++){
                var datas = [''+data.rows[i].name+'',data.rows[i].data];
                dataValues.push(datas);
            }

            // Define chart color patterns
            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var pie1 = $('#high-pie');

            if (pie1.length) {

                // Pie Chart1
                $('#high-pie').highcharts({
                    credits: false,
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: null
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
                        }
                    },
                    colors: highColors,
                    legend: {
                        x: 90,
                        floating: true,
                        verticalAlign: "middle",
                        layout: "vertical",
                        itemMarginTop: 10
                    },
                    series: [{
                        type: 'pie',
                        name: data.name,
                        data: dataValues
                    }]
                });
            }

        }

    });

    exports.pieCharts = PieCharts;

    return exports;


});