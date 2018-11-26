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


    var BarGraph = core.AObject.extend({

        ctor: function () {
            BarGraph.super.ctor.call(this);

        },

        init: function (data) {

            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var bars1 = $('#high-bars');

            if (bars1.length) {

                // Bar Chart 1
                $('#high-bars').highcharts({
                    colors: highColors,
                    credits: false,
                    legend: {
                        enabled: false,
                        y: -5,
                        verticalAlign: 'top',
                        useHTML: true
                    },
                    chart: {
                        spacingLeft: 30,
                        type: 'bar',
                        marginBottom: 0,
                        marginTop: 0
                    },
                    title: {
                        text: null
                    },
                    xAxis: {
                        showEmpty: false,
                        tickLength: 80,
                        lineColor: '#EEE',
                        tickColor: '#EEE',
                        offset: 1,
                        categories: data.categories,
                        title: {
                            text: null
                        },
                        labels: {
                            align: 'right',
                        }
                    },
                    yAxis: {
                        min: 0,
                        gridLineWidth: 0,
                        showEmpty: false,
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: false,
                        }
                    },
                    tooltip: {
                        valueSuffix: data.suffix
                    },
                    plotOptions: {
                        bar: {}
                    },
                    series: data.rows
                });
            }

        }

    });

    exports.barGraph = BarGraph;

    return exports;


});