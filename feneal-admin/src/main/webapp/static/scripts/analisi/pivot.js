/**
 * Created by fgran on 27/12/2017.
 */
define([
    ], function() {

    var exports = {};

    
    var Pivot = function(urlDatasource){
        this.urlDataSource = urlDatasource;
    }
    
    Pivot.prototype.init = function(){
        
        var self = this;
        
        
        var pivotGridChart = $("#pivotgrid-chart").dxChart({
            commonSeriesSettings: {
                type: "bar"
            },
            tooltip: {
                enabled: true,
                customizeTooltip: function (args) {
                    var valueText =  args.originalValue;

                    return {
                        html: args.seriesName + "<div class='currency'>"
                        + valueText + "</div>"
                    };
                }
            },
            "export": {
                enabled: true
            },
            size: {
                height: 200
            },
            adaptiveLayout: {
                width: 450
            }
        }).dxChart("instance");

        var pivotGrid = $("#grid").dxPivotGrid({
            // height: 800,
            fieldChooser: {
                allowSearch: true
            },
            scrolling: { mode: "virtual" },
            fieldPanel: { visible: true },
            allowSorting: true,
            allowSortingBySummary: true,
            allowFiltering: true,
            showBorders: true,
            showColumnGrandTotals: false,
            showRowGrandTotals: true,
            showRowTotals: true,
            showColumnTotals: true,
            "export": {
                enabled: true,
                fileName: "ReportIscritti"
            },
            onCellClick: function(e) {

            },
            loadPanel: {
                position: {
                    of: $(window),
                    at: "center",
                    my: "center"
                }
            },
            dataSource: {
                fields: [
                    { dataField: "Anno", area: "column", sortByPath: [] },
                    { dataField: "Settore", area: "row",  sortOrder: "desc" },
                    { dataField: "Regione", area: "filter",  sortOrder: "desc" },
                    { dataField: "Provincia", area: "filter",  sortOrder: "desc" },
                    //{ dataField: "Territorio", area: "filter",  sortOrder: "desc" },
                    { dataField: "Nazionalita", area: "filter",  sortOrder: "desc" },


                    { dataField: "Id_Lavoratore", caption: "Num. Lavoratori", summaryType: "count", area: "data" }
                ],
                remoteOperations: true,
                store: DevExpress.data.AspNet.createStore({
                    key: "ID",
                    loadUrl: generateUrl()
                })
            }
        }).dxPivotGrid("instance");


        pivotGrid.bindChart(pivotGridChart, {
            dataFieldsDisplayMode: "splitPanes",
            alternateDataFields: false
        });

        function generateUrl(){

            var baseurl = self.urlDataSource + "?anno=2017";


            return baseurl;
        }
    }
    
    
    exports.Pivot = Pivot;
    
    
    return exports;
});