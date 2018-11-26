/**
 * Created by david on 26/05/2016.
 */

define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {

    var exports = {};

    var ContatoreIscrittiTerritorioAccorpato = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            ContatoreIscrittiTerritorioAccorpato.super.ctor.call(this, widgetName, widgetParams);

            if (!this.anno){
                var annoDefault = $('.contatore-iscritti-territorio-accorpato-container').find('input[name="hidden-year"]').val();
                this.anno = annoDefault;
            }
        },
        prepareWidget: function(){

            var self = this;
            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/contatoreIscrittiTerritorioAccorpato/getData");

            service.on("load",function(resp){

                var colorsCircles = ["warning","info","primary","alert"];

                var total = 0;

                for(var j=0;j<resp.length;j++){

                    total += resp[j].numIscritti;

                }

                for(var i=0;i<resp.length;i++){

                    var circle = $(".containerElement").find(".info-circle").clone().appendTo(".circleContainer");

                    circle.attr('id',i);
                    circle.attr('data-circle-color',colorsCircles[i]);
                    circle.attr('data-title',resp[i].territorio);

                    if(resp.length==1)
                        circle.removeClass("col-xs-6").addClass("col-xs-12");

                    var perc = (resp[i].numIscritti * 100) / total;

                    circle.attr('value',perc);


                    var tile = $(".containerElement").find(".tileToAppend").clone().appendTo(".tileContainer");

                    tile.find("h3").text("Iscritti " + resp[i].territorio + " : " + resp[i].numIscritti + " (" + perc.toFixed(1) + "%" + ")");
                    tile.find("h6").text("Anno " + self.anno);

                    if(resp.length==1)
                        tile.removeClass("col-xs-6").addClass("col-xs-12");

                }

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
                            width: 7,
                            colors: color,
                            text: function (value) {
                                var title = $(e).attr('data-title');
                               // console.log(e);
                               // console.log(title);
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
                    };
                    var lazyLayout = _.debounce(rescale, 300);
                    $(window).resize(lazyLayout);

                }

                $('.info-circle').children().addClass("full-width-important");

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

            var year;

            if (params.length == 0){

                year = new Date().getFullYear();

            }else{
                year = params[0].value;
            }



            var form = $("<form></form>");
            //se so che ci sarà il campo provincia recupero dal div.param-fields-container
            //un colne della select
            //var provinceSelect = $('div.param-fields-container').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            //if (province)
            //    provinceSelect.val(province);
            //else
            //    provinceSelect.val(provinceSelect.find('option').get(0).value);

            //stessa cosa per l'anno
            var yearSelect = $('.contatore-iscritti-territorio-accorpato-container').find('select[name="year"]').clone();
            //recuperato il riferimento alla select ne imposto il valore
            yearSelect.val(year);
            //li aggiungo al form
            //form.append(provinceSelect);
            form.append(yearSelect);
            //aggiungo il pulsante submit
            form.append("<input type='submit' value='Submit'>");

            //finalmente aggiungo all'edit-panel
            editPanel.append(form);

        }




    });


    exports.contatoreIscrittiTerritorioAccorpato = ContatoreIscrittiTerritorioAccorpato;

    return exports;


});