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


    var ResponseTime = core.AObject.extend({

        ctor: function () {
            ResponseTime.super.ctor.call(this);

        },

        init: function (data) {

            var highColors = [bgWarning, bgPrimary, bgInfo, bgAlert,
                bgDanger, bgSuccess, bgSystem, bgDark
            ];

            var column3 = $('#high-column3');

            if (column3.length) {

                // Column Chart3
                $('#high-column3').highcharts({
                    credits: false,
                    colors: highColors,
                    chart: {
                        type: 'column',
                        padding: 0,
                        spacingTop: 10,
                        marginTop: 100,
                        marginLeft: 30,
                        marginRight: 30
                    },
                    legend: {
                        enabled: false
                    },
                    title: {
                        text: data.averageValue,
                        style: {
                            fontSize: 20,
                            fontWeight: 600
                        }
                    },
                    subtitle: {
                        text: data.description,
                        style: {
                            color: '#AAA'
                        }
                    },
                    xAxis: {
                        lineWidth: 0,
                        tickLength: 0,
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: true,
                            formatter: function () {
                                return this.value + "-" + (
                                    this.value + 2) +
                                    "<br> lorem"; // clean, unformatted number for year
                            }
                        },
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
                            colorByPoint: true,
                            colors: [bgPrimary, bgPrimary,
                                bgInfo, bgInfo
                            ],
                            groupPadding: 0,
                            pointPadding: 0.24,
                            borderWidth: 0
                        }
                    },
                    series: data.rows,
                    dataLabels: {
                        enabled: true,
                        rotation: 0,
                        color: '#AAA',
                        align: 'center',
                        x: 0,
                        y: -8,
                    }
                });
            }

        }

    });

    exports.responseTime = ResponseTime;

    return exports;


});