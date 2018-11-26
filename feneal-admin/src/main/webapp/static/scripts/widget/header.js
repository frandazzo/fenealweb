/**
 * Created by david on 07/04/2016.
 */


define([
    "framework/core",
    "framework/model",
    "framework/widgets",
    "framework/plugins",
    "framework/webparts",
    "framework/ui"], function (core, model, widgets, plugins, webparts, ui) {


    var exports = {};


    var Header = core.AObject.extend({

        ctor: function () {
            Header.super.ctor.call(this);

        },

        init: function () {

            $(".textIncrement").each(function(){

                $(this).prop('Counter',0).animate({
                    Counter: parseInt($(this).text())
                }, {
                    duration: 1200,
                    easing: 'swing',
                    step: function (now) {
                        $(this).text(Math.ceil(now).toLocaleString().replace(",00",""));
                    }
                });

            })

        }

    });

    exports.header = Header;

    return exports;


});