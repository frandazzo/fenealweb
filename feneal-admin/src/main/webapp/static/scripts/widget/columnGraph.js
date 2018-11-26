/**
 * Created by david on 04/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var ColumnGraph = core.AObject.extend({

        ctor: function () {
            ColumnGraph.super.ctor.call(this);

        },

        init: function (data) {

            var dataValues = [];

            for(var i=0;i<data.length;i++){

                var obj = {};
                obj.name = data[i].name;
                obj.data = [data[i].data];

                dataValues.push(obj);

            }


            // Define chart color patterns
            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var column1 = $('#high-column');

            if (column1.length) {

                // Column Chart 1
                $('#high-column').highcharts({
                    credits: false,
                    colors: highColors,
                    chart: {
                        backgroundColor: 'transparent',
                        type: 'column',
                        padding: 0,
                        margin: 0,
                        marginTop: 10
                    },
                    legend: {
                        enabled: false
                    },
                    title: {
                        text: null
                    },
                    xAxis: {
                        lineWidth: 0,
                        tickLength: 0,
                        minorTickLength: 0,
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: false
                        }
                    },
                    yAxis: {
                        gridLineWidth: 0,
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: false
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            groupPadding: 0.05,
                            pointPadding: 0.25,
                            borderWidth: 0
                        }
                    },
                    series: dataValues
                });
            }

        }

    });


    exports.columnGraph = ColumnGraph;

    return exports;


});