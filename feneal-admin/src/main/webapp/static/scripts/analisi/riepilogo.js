/**
 * Created by fgran on 02/01/2018.
 */
define(["analisi/riepilogoData"
], function(dataService) {

    var exports = {};

    var riepilogo = function(){
        
       
        this.currentYear = null;
        this.anni = [];
        //campo per indicare visione globale o lacale
        this.viewType = "global";
       
    };
    
    riepilogo.prototype.init = function(){
        var self = this;

        $('#indietro').click(function(){
            self.viewType = "global";
            //self.loadPageForYear(self.currentYear);
            self.showCharts(self.currentYear, null);
            //self.disposeTabs(self.anni);
            $('.mapContainer').show();
            $(".pure-button").hide();

        });


      //  $.loader.show({parent:'body'});

        dataService.getAnniIscrizioni().done(function (arrayAnni) {

            if (arrayAnni.length == 0){
                alert("Nessun anno trovato per le iscrizioni!!!");
                return;
            }

            self.currentYear = arrayAnni[0];

            // Call horizontalNav on the navigations wrapping element
            self.disposeTabs(arrayAnni);
            self.loadPageForYear(arrayAnni[0]);
            self.anni = arrayAnni;
        });
    };

    riepilogo.prototype.disposeTabs = function (years) {
        var self = this;
        var tabs = [];
        $.each(years, function (index, year) {
            tabs.push({
                text: year
            });
        });

        $("#longtabs > .tabs-container").dxTabs({
            dataSource: tabs,
            selectedIndex: 0,
            onItemClick: function (e) {
                var index = e.itemIndex;
                self.currentYear = self.anni[index];
                self.loadPageForYear(self.anni[index]);
                $('.mapContainer').show();
            }
        });

    };

    riepilogo.prototype.loadPageForYear = function(year) {
        this.viewType = "global";
        this.showVectorMap(year);
        this.showCharts(year, null);
        $(".pure-button").hide();
    };

    /** VECTOR MAP **/
    riepilogo.prototype.showVectorMap = function(year) {
        
        var self = this;
        
       //$('.mapContainer').hide();
        $.loader.show({parent:'.mapContainer'});
        DevExpress.viz.vectormaputils.parse(BASE +'static/mapdata/ITA_adm1', {precision: 2}, function (data) {
            dataService.getIscrittiPerAreaGeografica(self.currentYear, null).done(function (iscritti) {

                var groups = [];
                $.each(iscritti.legenda, function (index, v) {
                    groups.push(v.valMin);
                })
                groups.push(iscritti.legenda[iscritti.legenda.length - 1].valMax);

                var subscrivers = iscritti.iscritti;

                var map = $('.map').dxVectorMap({
                    wheelEnabled: false,
                    panningEnabled: false,
                    zoomFactor: 25,
                    maxZoomFactor: 50,
                    center: [15, 42],
                    title: {
                        text: "<b>Iscritti Feneal Italia</b>",
                        subtitle: "anno " + year
                    },
                    controlBar: {
                        enabled: false
                    },
                    "export": {
                        enabled: true
                    },
                    layers: [{
                        name: "areas",
                        type: 'area',
                        dataSource: data,
                        palette: "Red",
                        colorGroups: groups,
                        colorGroupingField: "iscritti",
                        customize: function (elements) {

                            $.each(elements, function (index, element) {
                                var currentDataObject = self.getObjectWithMapId(subscrivers, element.attribute("NAME_1"))
                                element.attribute("iscritti", currentDataObject.total)
                                element.attribute("label", currentDataObject.label)
                            })

                        }
                    }],
                    legends: [{
                        source: {layer: "areas", grouping: "color"},
                        customizeText: function (arg) {
                            //return legendLabels[arg.index]

                            return iscritti.legenda[arg.index].label;
                        }
                    }],
                    tooltip: {
                        enabled: true,
                        customizeTooltip: function (arg) {
                            return {
                                text: arg.attribute("label") + ": " + arg.attribute("iscritti") + " nel " + self.currentYear
                            }
                        }
                    },

                    onClick: function (e) {
                        var element = e.target;
                        if (element !== null && element !== undefined && element.layer.type === 'area') {
                            //navigate to detail
                            //$.loader.show({parent:'body'});

                            self.viewType = "local";
                            var selected = self.getObjectWithMapId(subscrivers, element.attribute("NAME_1"))
                            self.showCharts(self.currentYear, selected.label)
                            $(".pure-button").show();
                            //
                            //
                             $('.mapContainer').hide();
                        }
                    }

                });
                
                //$('.map').show();
                $.loader.hide({parent:'.mapContainer'});

            });

        });

    };

    riepilogo.prototype.getObjectWithMapId = function(objects, mapId) {
        var transcodedMapId = mapId.toUpperCase();
        
        if (transcodedMapId == "PROV. AUTONOMA BOLZANO")
            transcodedMapId = "PROV. AUT. BOLZANO";

        if (transcodedMapId == "PROV. AUTONOMA TRENTO")
            transcodedMapId = "PROV. AUT. TRENTO";
        
        
        var indexes = $.map(objects, function(obj, index) {
            if(obj.label === transcodedMapId) {
                return index;
            }
        });
        if(indexes.length > 0){
            return objects[indexes[0]]
        }
        return {};
    };


    riepilogo.prototype.showCharts = function(year, region){
        var self = this;
        $.loader.show({parent:'.charts'});
        // $(".donught1").hide();
        // $(".lines1").hide();
        // $(".donught2").hide();
        // $(".lines2").hide();
        
        dataService.getIscrittiPerAreaGeografica(year, region).done(function (iscritti) {
            var results = iscritti.iscritti;
            $(".donught1").dxPieChart({
                type: "doughnut",
                dataSource: results,
                palette: "Red",
                title: {
                    text: self.viewType == "global" ? "<b>Percentuale per regione</b> - " + self.currentYear: "<b>Percentuale per provincia</b> - "+ self.currentYear,
                    subtitle: region ? region : ""
                },
                legend: {
                    horizontalAlignment: "left",
                    verticalAlignment: "center",
                    margin: 5,
                    customizeText: function (arg) {

                        return arg.pointName
                    }
                },
                onPointClick: function(arg) {
                    arg.target.select();
                },
                "export": {
                    enabled: true
                },
                tooltip: {
                    enabled: true,
                    format: function(v){
                        return v
                    },
                    customizeTooltip: function() {
                        return { text: this.argumentText + "<br>"  + this.percentText};
                    }
                },
                series: [{
                    argumentField: "label",
                    valueField: "total",
                    hoverStyle: {
                        color: "#ffd700"
                    }

                }]
            });
            $(".lines1").dxChart({
                dataSource: results,
                rotated: true,
                palette: "Red",
                series: {

                },
                tooltip: {
                    enabled: true,
                    format: function(v){
                        return v
                    },
                    customizeTooltip: function() {
                        return { text: this.argumentText + "<br>"  + this.valueText};
                    }
                },
                title: {
                    text: self.viewType == "global" ? "<b>Totale per regione</b> - " + self.currentYear : "<b>Totale per provincia</b> - "+ self.currentYear,

                    subtitle: region ? region : ""
                },
                "export": {
                    enabled: true
                },
                legend: {
                    visible: false
                },
                commonSeriesSettings: {
                    type: "bar",
                    valueField: "total",
                    argumentField: "label",

                },
                equalBarWidth: false,
                seriesTemplate: {
                    nameField: "label"
                },

            });
            
            // $(".donught1").show();
            // $(".lines1").show();
            
            dataService.getIscrittiPerCategoria(year, region).done(function(iscritti){

                var results = iscritti.iscritti;
                $(".donught2").dxPieChart({
                    type: "doughnut",
                    dataSource: results,
                    palette: "Red",
                    title: {
                        text: "<b>Perc. per settore - </b>" + self.currentYear,
                        subtitle: region ? region : ""
                    },
                    legend: {
                        horizontalAlignment: "left",
                        verticalAlignment: "center",
                        margin: 5,
                        customizeText: function (arg) {

                            return arg.pointName
                        }
                    },
                    onPointClick: function(arg) {
                        arg.target.select();
                    },
                    "export": {
                        enabled: true
                    },
                    tooltip: {
                        enabled: true,
                        format: function(v){
                            return v
                        },
                        customizeTooltip: function() {
                            return { text: this.argumentText + "<br>"  + this.percentText};
                        }
                    },
                    series: [{
                        argumentField: "label",
                        valueField: "total",
                        hoverStyle: {
                            color: "#ffd700"
                        },

                    }]
                });
                $(".lines2").dxChart({
                    dataSource: results,
                    rotated: true,
                    palette: "Red",
                    series: {

                    },
                    title: {
                        text: "<b>Totale per settore - </b>" + self.currentYear,
                        subtitle: region ? region : ""
                    },
                    tooltip: {
                        enabled: true,
                        format: function(v){
                            return v
                        },
                        customizeTooltip: function() {
                            return { text: this.argumentText + "<br>"  + this.valueText};
                        }
                    },
                    "export": {
                        enabled: true
                    },
                    legend: {
                        visible: false
                    },
                    commonSeriesSettings: {
                        type: "bar",
                        valueField: "total",
                        argumentField: "label",

                    },
                    equalBarWidth: false,
                    seriesTemplate: {
                        nameField: "label"
                    },

                });
                // $(".donught2").show();
                // $(".lines2").show();
                
                $.loader.hide({parent:'.charts'});
            })

        })
    };

    exports.Riepilogo = riepilogo;

    return exports;
});