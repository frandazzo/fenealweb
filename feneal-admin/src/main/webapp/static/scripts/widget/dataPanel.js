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


    var DataPanel = core.AObject.extend({

        ctor: function () {
            DataPanel.super.ctor.call(this);

        },

        init: function (data) {

            var column2 = $('#high-column2');

            if (column2.length) {

                // Column Chart 2
                $('#high-column2').highcharts({
                    credits: false,
                    colors: [bgPrimary, bgPrimary, bgWarning,
                        bgWarning, bgInfo, bgInfo
                    ],
                    chart: {
                        padding: 0,
                        marginTop: 25,
                        marginLeft: 15,
                        marginRight: 5,
                        marginBottom: 30,
                        type: 'column',
                    },
                    legend: {
                        enabled: false
                    },
                    title: {
                        text: null,
                    },
                    xAxis: {
                        lineWidth: 0,
                        tickLength: 6,
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: true
                        }
                    },
                    yAxis: {
                        max: 20,
                        lineWidth: 0,
                        gridLineWidth: 0,
                        lineColor: '#EEE',
                        gridLineColor: '#EEE',
                        title: {
                            text: null
                        },
                        labels: {
                            enabled: false,
                            style: {
                                fontWeight: '400'
                            }
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
                        }
                    },
                    series: [{
                        name: 'Testo di prova',
                        data: data
                    }]
                });

            }

        }

    });

    exports.dataPanel = DataPanel;

    return exports;


});