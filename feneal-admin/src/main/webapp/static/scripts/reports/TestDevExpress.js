
    jQuery(document).ready(function() {

    var grid = null;

    $("#countselectedrows").click(function () {

    var data = grid.getSelectedRowsData();

        var filterExpression =  grid.getCombinedFilter(true);
        if(data.length) {
            var dataSource = new DevExpress.data.DataSource({
                filter:filterExpression,
                paginate: false,
                store: new DevExpress.data.ArrayStore({
                    data: data,
                    key: "lavoratoreNomeCompleto"
                })
            })
            dataSource.load().done(function(r){
                data = r;

            })
        }

    alert(data.length);


});
    "use strict";

    // Init Theme Core
    $("#eseguiricerca").click(function () {
    console.log("ciao");
    var jsonData  = JSON.stringify(
{
    request:
{province: "Grosseto",
    datefromMonthReport: "1",
    datefromYearReport: "2018",
    datetoMonthReport: "1",
    datetoYearReport: "2020",
    typeQuoteCash: "IQA",
    sector: "EDILE",
    parithetic: "CASSA EDILE",
    firm: ""}});

    $.ajax({
    url: "/ciccio/ciccio",
    method : "POST",
    data: jsonData,
    contentType: "application/json; charset=utf-8"
}).done(function(data, textStatus, jqXHR ) {

    grid = $('#reportContainer').dxDataGrid({
    dataSource:data.value,
    columns:[

{ dataField:"aziendaRagioneSociale", visible : true,
},

{ dataField:"lavoratoreNomeCompleto", fixed :true, visible : true, visibleIndex: 0,
},
    ],
    // searchPanel: {
    //     visible: true
    //
    // },
    summary: {
    totalItems: [{
    column: "lavoratoreNomeCompleto",
    summaryType: "count",
    customizeText: function(data) {
    return "Iscritti trovati: " + data.value;
}
}]
},
    // columnChooser: {
    //     enabled: true
    // },
    // onCellClick: function (clickedCell) {
    //     alert(clickedCell.column.dataField);
    // },
    "export": {
    enabled: false,
    fileName: "iscritti",
    allowExportSelectedData: true
},
    stateStoring: {
    enabled: false,
    type: "localStorage",
    storageKey: "reportiscritti"
},
    paging:{
    pageSize:5
},
    headerFilter: {
    allowSearch:false,
    height:325,
    searchTimeout:500,
    texts: {},
    visible:true,
    width:252
},
    sorting:{
    mode:"multiple"
},
    onContentReady: function (e) {
    var columnChooserView = e.component.getView("columnChooserView");
    if (!columnChooserView._popupContainer) {
    columnChooserView._initializePopupContainer();
    columnChooserView.render();
    columnChooserView._popupContainer.option("dragEnabled", false);
}
},
    rowAlternationEnabled: true,
    showBorders: true,
    allowColumnReordering:true,
    allowColumnResizing:true,
    columnAutoWidth: true,
    selection:{
    allowSelectAll:true,
    deferred:false,
    mode:"multiple",
    selectAllMode:"allPages",
    showCheckBoxesMode:"always"
},
    hoverStateEnabled: true

    // masterDetail: {
    //     enabled: true,
    //     template: function(container, options) {
    //         var currentData = options.data;
    //         container.addClass("internal-grid-container");
    //         $("<div>").text(currentData.delegaSettore  + " Dettagli:").appendTo(container);
    //         $("<div>")
    //             .addClass("internal-grid")
    //             .dxDataGrid({
    //                 columnAutoWidth: true,
    //                 columns: [{
    //                     dataField: "id"
    //                 }, {
    //                     dataField: "description",
    //                     caption: "Description",
    //                     calculateCellValue: function(rowData) {
    //                         return rowData.description + "ciao ciao";
    //                     }
    //                 }],
    //                 dataSource: currentData.details
    //             }).appendTo(container);
    //     }
    // }

}).dxDataGrid("instance");



})
    .fail(function(jqXHR, textStatus, errorThrown) {
    alert( "error" );
});
});

});

