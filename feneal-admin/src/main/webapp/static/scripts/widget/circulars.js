/**
 * Created by david on 06/04/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {


    var exports = {};


    
    
    var CircularsWidget = widgetManager.DashboardWidget.extend({
            ctor:function(widgetName, widgetParams){
                CircularsWidget.super.ctor.call(this, widgetName, widgetParams);
            },
            prepareWidget: function(){
                //metodo che definiisce il comportamento del widget

                var infoCircle = $('.info-circle');
                if (infoCircle.length) {
                    // Color Library we used to grab a random color
                    var colors = {
                        "primary": [bgPrimary, bgPrimaryLr,
                            bgPrimaryDr
                        ],
                        "info": [bgInfo, bgInfoLr, bgInfoDr],
                        "warning": [bgWarning, bgWarningLr,
                            bgWarningDr
                        ],
                        "success": [bgSuccess, bgSuccessLr,
                            bgSuccessDr
                        ],
                        "alert": [bgAlert, bgAlertLr, bgAlertDr]
                    };
                    // Store all circles
                    var circles = [];
                    infoCircle.each(function (i, e) {
                        // Define default color
                        var color = ['#DDD', bgPrimary];
                        // Modify color if user has defined one
                        var targetColor = $(e).data(
                            'circle-color');
                        if (targetColor) {
                            var color = ['#DDD', colors[
                                targetColor][0]]
                        }
                        // Create all circles
                        var circle = Circles.create({
                            id: $(e).attr('id'),
                            value: $(e).attr('value'),
                            radius: $(e).width() / 2,
                            width: 14,
                            colors: color,
                            text: function (value) {
                                var title = $(e).attr('title');
                                if (title) {
                                    return '<h2 class="circle-text-value">' + value + '</h2><p>' + title + '</p>'
                                }
                                else {
                                    return '<h2 class="circle-text-value mb5">' + value + '</h2>'
                                }
                            }
                        });
                        circles.push(circle);
                    });

                    // Add debounced responsive functionality
                    var rescale = function () {
                        infoCircle.each(function (i, e) {
                            var getWidth = $(e).width() / 2;
                            circles[i].updateRadius(
                                getWidth);
                        });
                        setTimeout(function () {
                            // Add responsive font sizing functionality
                            $('.info-circle').find('.circle-text-value').fitText(0.4);
                        }, 50);
                    }
                    var lazyLayout = _.debounce(rescale, 300);
                    $(window).resize(lazyLayout);

                }



            },
            renderHtmlForm : function(editPanel, params){
                //questa funzione astratta appende l'html del form nel pannello
                //a meno che non sia gia stato inserito nella vista....
                //e ne renderizza i paramtri


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
                var val = "";
                if (params.length == 1)
                    val = params[0].value;

                var form = "<form> <input name='prova' type='text' value='" +  val + "' /> <input type='submit' value='Submit'></form>"
                editPanel.append(form);
            }




    });


    exports.circulars = CircularsWidget;

    return exports;


});