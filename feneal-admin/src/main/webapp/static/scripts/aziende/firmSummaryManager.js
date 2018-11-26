/**
 * Created by fgran on 13/06/2016.
 */
define([
    "framework/core",
    "framework/model",
    "framework/views",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, views, widgets, plugins, webparts, ui) {


    var exports = {};


    var Tessera = core.AObject.extend({

        ctor: function () {
            Tessera.super.ctor.call(this);
        },

        init: function () {

            $(".tesseraButton").on("click",function(){

                $(".tessera-content-div").clone().modalDialog({
                    autoOpen: true,
                    title: "Info tessera",
                    destroyOnClose: true,
                    height: 300,
                    width: 400
                }).show();
            });

        }

    });


    var List = core.AObject.extend({

        ctor: function () {
            List.super.ctor.call(this);
        },

        init: function () {

            var webpart = new webparts.WebPart();
            webpart.set("url", BASE + "widget/dotList");
            webpart.set("container", $(".webpart-list-container"));
            webpart.on("load", function () {

            });
            webpart.on("error", function (e) {
                alert(e);
            });
            webpart.load();

        }

    });


    exports.tessera = Tessera;
    exports.list = List;

    return exports;


});