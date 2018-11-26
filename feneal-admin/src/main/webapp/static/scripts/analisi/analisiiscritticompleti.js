/**
 * Created by fgran on 27/12/2017.
 */
define(["analisi/riepilogoData"
], function(dataService) {

    var exports = {};


    var AnalisiIscrittiCompleti = function(urlDatasource){
        this.urlDataSource = urlDatasource;

        this.currentYear = null;
        this.anni = [];
        //campo per indicare visione globale o lacale
        this.viewType = "global";

        this.moreTime = false;
    }

    AnalisiIscrittiCompleti.prototype.init = function(){
        var self = this;

        var iscrittiCheckBox = $("#iscrittiCheckBox").dxCheckBox({
            text: "Iscritti presi una sola volta.",
            value: false,
            onValueChanged: function (isChecked) {
                if (isChecked) {
                    self.moreTime = true;
                }
                else {
                    self.moreTime = false;
                }
                self.loadData(self.currentYear, self.moreTime);
            }
        });

        dataService.getAnniIscrizioni().done(function (arrayAnni) {

            if (arrayAnni.length == 0){
                alert("Nessun anno trovato per le iscrizioni!!!");
                return;
            }

            self.currentYear = arrayAnni[0];

            // Call horizontalNav on the navigations wrapping element
            self.disposeTabs(arrayAnni);
            self.loadData(arrayAnni[0], self.moreTime);
            self.anni = arrayAnni;
        });

    };

    AnalisiIscrittiCompleti.prototype.loadData = function(anno, moreTime){

        var self = this;

        var fields = [
            {dataField: "Anno", area: "column", sortByPath: []},
            {dataField: "Settore", area: "row", sortOrder: "desc"},
            {dataField: "Regione", area: "filter", sortOrder: "desc"},
            {dataField: "Provincia", area: "filter", sortOrder: "desc"},

            {dataField: "Nazionalita", area: "filter", sortOrder: "desc"},


            {dataField: "Id_Lavoratore", caption: "Num. Lavoratori", summaryType: "count", area: "data"}
        ];
        if (moreTime)
            fields = [
                {dataField: "Anno", area: "column", sortByPath: []},
                // { dataField: "Settore", area: "row",  sortOrder: "desc" },
                {dataField: "Regione", area: "row", sortOrder: "desc"},
                {dataField: "Provincia", area: "filter", sortOrder: "desc"},

                {dataField: "Nazionalita", area: "filter", sortOrder: "desc"},


                {dataField: "Id_Lavoratore", caption: "Num. Lavoratori", summaryType: "count", area: "data"}
            ];


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
                fields: fields,
                remoteOperations: true,
                store: DevExpress.data.AspNet.createStore({
                    key: "ID",
                    loadUrl: self.generateUrl(anno, moreTime)
                })
            }
        }).dxPivotGrid("instance");


        pivotGrid.bindChart(pivotGridChart, {
            dataFieldsDisplayMode: "splitPanes",
            alternateDataFields: false
        });


    };
    AnalisiIscrittiCompleti.prototype.generateUrl = function(anno, moreTime){
        var self = this;

        if (!anno)
            anno = 2017;

        var baseurl = self.urlDataSource + "?anno=" + anno;

        if (moreTime) {
            baseurl += "&moreTime=true";
        }


        return baseurl;
    };
    AnalisiIscrittiCompleti.prototype.disposeTabs = function (years) {
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
                self.loadData(self.anni[index]);
                $('.mapContainer').show();
            }
        });

    };


    exports.AnalisiIscrittiCompleti = AnalisiIscrittiCompleti;


    return exports;
});