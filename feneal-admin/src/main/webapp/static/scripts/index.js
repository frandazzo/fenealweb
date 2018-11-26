/**
 * Created by fgran on 11/04/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};

   

    var Index = core.AObject.extend({

        ctor: function () {
            Index.super.ctor.call(this);

        },
        loadOptionsWebPart :function () {
            var url = $(this).attr("data-url");
            var BASEurl = BASE + url;
            var webpart = new webparts.WebPart();
            webpart.set("url", BASEurl);
            webpart.set("container", $(this));
            webpart.on("error", function (e) {
                alert(e);
            });
            webpart.load();
        },
        init: function(){
            var self = this;

            //nascondo il pulsante di opzioni utente
            //laterale per l'utente amministratore e amministratore della societa
            if (window.appcontext.roleid == "1" || window.appcontext.roleid == "2") {
                $('#skin-toolbox').remove();
                $('#toggle_sidemenu_r').remove();
                $('.notifcations-button').remove();
            }else{
                //Inizializzo le informazioni presenti nelle opzioni relative ai tre tab di
                //dashboard , worker, e azienda
                $(".dashboard-user-options").each(function(){
                    self.loadOptionsWebPart.call(this);
                });
                $(".worker-user-options").each(function(){
                    self.loadOptionsWebPart.call(this);
                });
                $(".firm-user-options").each(function(){
                    self.loadOptionsWebPart.call(this);
                });

            }

            //inizializzo il pulsante di ricerca globale per il lavoratore
            $('form.global-worker-search').submit(function(){

                var input = $(this).find('input').val();

                if (!input)
                    return false;

                ui.Navigation.instance().navigate("searchworkers", "index", {
                    workername:input.replace("\'", "\\'")
                });



                return false;
            });



            

            //$('#toggle_sidemenu_r').on("click",function(){
                //$(".highcharts-container").each(function(){
                //    $(this).css("width",$(this).parent().parent().width()+"px");
                //})
                //$(document).resize()
                //$(window).resize()
            //})

        }
    });

    exports.Index = Index;

    return exports;


});