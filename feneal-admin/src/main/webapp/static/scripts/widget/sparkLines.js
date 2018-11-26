/**
 * Created by david on 06/04/2016.
 */

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


    var SparkLines = core.AObject.extend({

        ctor: function () {
            SparkLines.super.ctor.call(this);

        },

        init: function (data) {

            $(".inlinesparkline").each(function(){

                var values = $(this).attr("values");
                values = values.replace("[","").replace("]","").replace(" ","");
                $(this).attr("values",values);



                var sparkColors = {
                    "primary": [bgPrimary, bgPrimaryLr, bgPrimaryDr],
                    "info": [bgInfo, bgInfoLr, bgInfoDr],
                    "warning": [bgWarning, bgWarningLr, bgWarningDr],
                    "success": [bgSuccess, bgSuccessLr, bgSuccessDr],
                    "alert": [bgAlert, bgAlertLr, bgAlertDr]
                };

                var sparkLine = $('.inlinesparkline');
                // Init Sparklines
                if (sparkLine.length) {

                    var sparklineInit = function() {
                        $('.inlinesparkline').each(function(i, e) {
                            var This = $(this);
                            var Color = sparkColors["primary"];
                            var Height = '35';
                            var Width = '70%';
                            This.children().remove();
                            // default color is "primary"
                            // Color[0] = default shade
                            // Color[1] = light shade
                            // Color[2] = dark shade
                            //alert('hi')
                            // User assigned color and height, else default
                            var userColor = This.data('spark-color');
                            var userHeight = This.data('spark-height');
                            if (userColor) {
                                Color = sparkColors[userColor];
                            }
                            if (userHeight) {
                                Height = userHeight;
                            }
                            $(e).sparkline('html', {
                                type: 'line',
                                width: Width,
                                height: Height,
                                enableTagOptions: true,
                                lineColor: Color[2], // Also tooltip icon color
                                fillColor: Color[1],
                                spotColor: Color[0],
                                minSpotColor: Color[0],
                                maxSpotColor: Color[0],
                                highlightSpotColor: bgWarningDr,
                                highlightLineColor: bgWarningLr
                            });
                        });
                    }

                    // Refresh Sparklines on Resize
                    var refreshSparklines;

                    $(window).resize(function(e) {
                        clearTimeout(refreshSparklines);
                        refreshSparklines = setTimeout(sparklineInit, 500);
                    });

                    sparklineInit();
                }

            })

        }

    });


    exports.sparkLines = SparkLines;

    return exports;


});