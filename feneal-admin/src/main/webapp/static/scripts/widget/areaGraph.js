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


    var AreaGraph = core.AObject.extend({

        ctor: function () {
            AreaGraph.super.ctor.call(this);

        },

        init: function (data) {

            var dataValues = [];

            for(var i=0;i<data.length;i++){
                dataValues.push(data[i].row);
            }

            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var line3 = $('#high-line3');

            if (line3.length) {

                // High Line 3
                $('#high-line3').highcharts({
                    credits: false,
                    colors: highColors,
                    chart: {
                        backgroundColor: '#f9f9f9',
                        className: 'br-r',
                        type: 'line',
                        zoomType: 'x',
                        panning: true,
                        panKey: 'shift',
                        marginTop: 25,
                        marginRight: 1,
                    },
                    title: {
                        text: null
                    },
                    xAxis: {
                        gridLineColor: '#EEE',
                        lineColor: '#EEE',
                        tickColor: '#EEE',
                        categories: ['Jan', 'Feb', 'Mar', 'Apr',
                            'May', 'Jun', 'Jul', 'Aug',
                            'Sep', 'Oct', 'Nov', 'Dec'
                        ]
                    },
                    yAxis: {
                        min: 0,
                        tickInterval: 5,
                        gridLineColor: '#EEE',
                        title: {
                            text: null,
                        }
                    },
                    plotOptions: {
                        spline: {
                            lineWidth: 3,
                        },
                        area: {
                            fillOpacity: 0.2
                        }
                    },
                    legend: {
                        enabled: false,
                    },
                    series: dataValues
                });

            }

        }

    });


    exports.areaGraph = AreaGraph;

    return exports;


});