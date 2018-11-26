/**
 * Created by david on 19/05/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui", "widgetManager"], function (core, model, widgets, plugins, webparts, ui, widgetManager) {


    var exports = {};




    var Contatore = widgetManager.DashboardWidget.extend({
        ctor:function(widgetName, widgetParams){
            Contatore.super.ctor.call(this, widgetName, widgetParams);

            //imposto i valori di default se i paramteri sono nulli
            if (!this.provincia){
                var provinciaDefault = $('div.param-fields-container-cise').find('input[name="hidden-province"]').val();
                this.provincia = provinciaDefault;

            }
            if (!this.anno){
                var annoDefault = $('div.param-fields-container-cise').find('input[name="hidden-year"]').val();
                this.anno = annoDefault;
            }

        },
        prepareWidget: function(){
            var self = this;

            var service = new model.AjaxService();

            service.set("url",BASE + "widget/real/contatoreIscrittiEdile/getData");

            service.on("load",function(resp){

                var total = 0;

                for(var j=0;j<resp.length;j++){
                    total += resp[j].numIscritti;
                }

                for(var i=0;i<resp.length;i++){

                    var elemToAppend = $(".containerTile").find(".elemToAppend").clone().appendTo(".containerValuesTile");
                    elemToAppend.find(".enteText").text(resp[i].ente);
                    elemToAppend.find(".numIscrittiText").text(resp[i].numIscritti);

                    var percentage = (100*resp[i].numIscritti)/total;

                    elemToAppend.find(".percentageText").text(percentage + "%");

                    if(resp.length==1)
                        elemToAppend.removeClass("col-md-6").addClass("col-md-12");

                    var provincia = self.provincia;
                    var anno = self.anno;

                    $('.param-info').removeClass('fs11').css("font-size", "18px").html(provincia + ' <b>' + anno + ' </b>')

                }

            });
            service.on("error",function(e){
                alert(e);
            });
            service.load();

        },
        renderHtmlForm : function(editPanel, params){

            var province;
            var year;

            if (params.length == 0){
                province = "";
                year = new Date().getFullYear();
            }else if(params.length == 1){
                //è sicuramente la provincia  -- (attenzione sto facendo il caso che il widget richieda entrambi i parametri)
                province = params[0].value;
                year = new Date().getFullYear();
            }else{
                province = params[0].value;
                year = params[1].value;
            }



            var form = $("<form></form>");
            //se so che ci sarà il campo provincia recupero dal div.param-fields-container
            //un colne della select
            var provinceSelect = $('div.param-fields-container-cise').find('select[name="province"]').clone();
            //recuperato il riferimento alla select ne imposto il valore

            //se non ho una provincia selezionata
            //prendo la prima disponibile
            if (province)
                provinceSelect.val(province);
            else
                provinceSelect.val(provinceSelect.find('option').get(0).value);

            //stessa cosa per l'anno
            var yearSelect = $('div.param-fields-container-cise').find('select[name="year"]').clone();
            //recuperato il riferimento alla select ne imposto il valore
            yearSelect.val(year);
            //li aggiungo al form
            form.append(provinceSelect);
            form.append(yearSelect);
            //aggiungo il pulsante submit
            form.append("<input type='submit' value='Submit'>");

            //finalmente aggiungo all'edit-panel
            editPanel.append(form);

        }




    });


    exports.contatore = Contatore;

    return exports;


});