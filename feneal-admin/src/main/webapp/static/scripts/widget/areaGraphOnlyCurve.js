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


    var AreaGraphOnlyCurve = core.AObject.extend({

        ctor: function () {
            AreaGraphOnlyCurve.super.ctor.call(this);

        },

        init: function (data) {

            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var area1 = $('#high-area');

            if (area1.length) {

                // Area 1
                $('#high-area').highcharts({
                    colors: highColors,
                    credits: false,
                    chart: {
                        type: 'areaspline',
                        spacing: 0,
                        margin: -5
                    },
                    title: {
                        text: null
                    },
                    legend: {
                        enabled: false
                    },
                    xAxis: {
                        allowDecimals: false,
                        tickColor: '#EEE',
                        labels: {
                            formatter: function () {
                                return this.value; // clean, unformatted number for year
                            }
                        }
                    },
                    yAxis: {
                        title: {
                            text: null
                        },
                        gridLineColor: 'transparent',
                        labels: {
                            enabled: false,
                        }
                    },
                    plotOptions: {
                        areaspline: {
                            fillOpacity: 0.25,
                            marker: {
                                enabled: true,
                                symbol: 'circle',
                                radius: 2,
                                states: {
                                    hover: {
                                        enabled: true
                                    }
                                }
                            }
                        }
                    },
                    series: data
                });
            }


        }

    });


    exports.areaGraphOnlyCurve = AreaGraphOnlyCurve;

    return exports;


});