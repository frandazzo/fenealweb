define([
    "framework/core",
    "framework/model"
], function(core, fmodel) {

    var exports = {};

    var GeoUtils = core.AObject.extend({
        ctor: function() {
            GeoUtils.super.ctor.call(this);
        },
        calculateCap: function(cityId, textElement){
          
            var model = new fmodel.AjaxService();
            model.set("content-type", "application/json");
            model.set("url", BASE + "values/capforcity?keyword=" + cityId);


            model.on("load" , function(response){
               
                textElement.val(response);

            });

            model.on("error", function(error){
                alert(error);
            });



            model.load();
        },
        loadCities: function(provinceId, selectedValue, elem){
            var self = this;
            var model = new fmodel.AjaxService();
            model.set("content-type", "application/json");
            model.set("url", BASE + "values/cities?keyword=" + provinceId);


            model.on("load" , function(response){
                elem.empty().append("<option value=''>Select</option>");

                if (selectedValue) {
                    $.each(response, function(index, value){
                        var selected = "";
                        if (value.value == selectedValue)
                            selected = "selected='selected'";
                        else
                            selected = "";

                        elem.append($("<option " + selected + "></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                } else {
                    $.each(response, function(index, value){
                        elem.append($("<option></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                }

                self.invoke("citiesLoaded", provinceId, selectedValue);


            });

            model.on("error", function(error){
                alert(error);
            });



            model.load();
        },

        loadProvinces: function(nationalityId, selectedValue, elem){
            
            var self = this;

            var model = new fmodel.AjaxService();
            model.set("content-type", "application/json");
            model.set("url", BASE + "values/provinces?keyword=" + nationalityId);


            model.on("load" , function(response){

                elem.empty().append("<option value=''>Select</option>");

                if (selectedValue){
                    $.each(response, function(index, value){
                        var selected = "";
                        if (value.value == selectedValue)
                            selected = "selected='selected'";
                        else
                            selected = "";

                        elem.append($("<option " + selected + "></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                }else{
                    $.each(response, function(index, value){
                        elem.append($("<option></option>")
                            .attr("value",value.value)
                            .text(value.label));
                    });
                }

                self.invoke("provincesLoaded", selectedValue);


            });

            model.on("error", function(error){
                alert(error);
            });



            model.load();
        }

    });


    exports.GeoUtils = GeoUtils;

    return exports;

});