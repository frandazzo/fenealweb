define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};

  


    var Dashboard = core.AObject.extend({

        ctor: function (context) {
            Dashboard.super.ctor.call(this);
            this.context = context;
        },
        init: function(){
            var self = this;

            $('.admin-panels-'+ self.context).adminpanel({
                grid: '.admin-grid' ,
                context: self.context,
                draggable: true,
                preserveGrid: true,
                mobile: false,
                onStart: function() {
                    // Do something before AdminPanels runs
                },
                onFinish: function() {
                    $('.admin-panels').addClass('animated fadeIn').removeClass('fade-onload');
                    //appena carico il plugin carico tutti i widgets
                    self.initWidgets();
                    
                },
                onSave: function() {
                    $(window).trigger('resize');
                }
            });
        },
        initWidgets:function(){
            $(".panel").each(function(){

                if($(this).hasClass("visible")){

                    var url = $(this).attr("data-webpart-url");

                    var webpart = new webparts.WebPart();
                    webpart.set("url", url);
                    webpart.set("container", $(this));
                    webpart.on("load", function () {

                    });
                    webpart.on("error", function (e) {
                        alert(e);
                    });
                    webpart.load();

                    $(this).show();

                }

            });
        }
    });

    exports.Dashboard = Dashboard;

    return exports;


});