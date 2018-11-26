/**
 * Created by fgran on 02/01/2018.
 */
define(["framework/model",
], function(fmodel) {

    var exports = {};

    exports.getAnniIscrizioni = function() {

         var deferred = $.Deferred();
        // setTimeout(function () {
        //
        //     return deferred.resolve([2017, 2016, 2015])
        // }, 1000)
        //
        // return deferred.promise();


        var service = new  fmodel.AjaxService();

        service.set("url", BASE + "analisi/riepilogo/anni");
        service.set("method", "GET");

        service.on("load", function(response) {
            deferred.resolve(response);
        });
        service.on("error", function(error){
            deferred.reject(error);

        });

        service.load();

        return deferred.promise();


    };
    
    
    exports.getIscrittiPerAreaGeografica = function(anno, regione) {

        var deferred = $.Deferred();

        if (!anno){

            deferred.resolve({
                legenda: [],
                iscritti: []
            });

            return deferred.promise();
        }


        var service = new  fmodel.AjaxService();

        var url = BASE + "analisi/riepilogo/areageografica?year=" + anno;
        if (regione)
            url = url + "&region=" + encodeURIComponent(regione);
        else
            url = url + "&region=noregion";

        service.set("url", url);
        service.set("method", "GET");

        service.on("load", function(response) {
            deferred.resolve(response);
        });
        service.on("error", function(error){
            deferred.reject(error);

        });

        service.load();

        return deferred.promise();

        // setTimeout(function () {
        //
        //     if (!regione) {
        //         return deferred.resolve({
        //             legenda: [
        //                 {valMin: 0, valMax: 5000, label: "Da 0 a 5.000 Iscritti"},
        //                 {valMin: 5000, valMax: 10000, label: "Da 5.000 a 10000 Iscritti"},
        //                 {valMin: 10000, valMax: 25000, label: "Da 10000 a 25000 Iscritti"},
        //                 {valMin: 50000, valMax: 100000, label: "Da 50000 a 100000 Iscritti"},
        //                 {valMin: 100000, valMax: 150000, label: "Da 50000 a 150000 Iscritti"},
        //                 {valMin: 150000, valMax: 200000, label: "Da 150000 a 200000 Iscritti"},
        //                 {valMin: 200000, valMax: 250000, label: "Da 150000 a 250000 Iscritti"}
        //             ],
        //             iscritti: [
        //                 {
        //                     label: "Abruzzo",
        //                     total: 53686,
        //
        //                 },
        //                 {
        //                     label: "Molise",
        //                     total: 12832
        //                 },
        //                 {
        //                     label: "Campania",
        //                     total: 191583
        //                 },
        //                 {
        //                     label: "Puglia",
        //                     total: 205156
        //                 },
        //                 {
        //                     label: "Marche",
        //                     total: 31541
        //                 },
        //                 {
        //                     label: "Basilicata",
        //                     total: 2414
        //                 },
        //                 {
        //                     label: "Calabria",
        //                     total: 121090
        //                 },
        //                 {
        //                     label: "Sicilia",
        //                     total: 220489
        //                 },
        //                 {
        //                     label: "Sardegna",
        //                     total: 54487
        //                 },
        //                 {
        //                     label: "Lazio",
        //                     total: 184722
        //                 },
        //                 {
        //                     label: "Marche",
        //                     total: 44361
        //                 },
        //                 {
        //                     label: "Umbria",
        //                     total: 31609
        //                 },
        //                 {
        //                     label: "Toscana",
        //                     total: 73880
        //                 },
        //                 {
        //                     label: "Emilia Romagna",
        //                     total: 128590
        //                 },
        //                 {
        //                     label: "Friuli Venezia Giulia",
        //                     total: 32871
        //                 },
        //                 {
        //                     label: "Liguria",
        //                     total: 57376
        //                 },
        //                 {
        //                     label: "Lombardia",
        //                     total: 190324
        //                 },
        //                 {
        //                     label: "Piemonte",
        //                     total: 142415
        //                 },
        //                 {
        //                     label: "Trentino Alto Adige",
        //                     total: 25247
        //                 },
        //                 {
        //                     label: "Val D'Aosta",
        //                     total: 3323
        //                 },
        //                 {
        //                     label: "Veneto",
        //                     total: 11136
        //                 }
        //
        //             ]
        //         })
        //     } else {
        //         return deferred.resolve({
        //             legenda: null,
        //             iscritti: [
        //                 {
        //                     label: "Potenza",
        //                     total: 1200
        //                 },
        //                 {
        //                     label: "Matera",
        //                     total: 505
        //                 }
        //             ]
        //         })
        //     }
        // }, 1000)


        //return deferred.promise();
    }
    
    
    exports.getIscrittiPerCategoria = function(anno, regione){
        var deferred = $.Deferred();

        if (!anno){

            deferred.resolve({
                legenda: [],
                iscritti: []
            });

            return deferred.promise();
        }


        var service = new  fmodel.AjaxService();

        var url = BASE + "analisi/riepilogo/categoria?year=" + anno;
        if (regione)
            url = url + "&region=" + encodeURIComponent(regione);
        else
            url = url + "&region=noregion";

        service.set("url", url);
        service.set("method", "GET");

        service.on("load", function(response) {
            deferred.resolve(response);
        });
        service.on("error", function(error){
            deferred.reject(error);

        });

        service.load();

        return deferred.promise();
        // if (!anno) throw "Errore: manca l'anno in getIscrittiPerCategoria()"
        // var deferred = $.Deferred();
        // setTimeout(function () {
        //
        //
        //
        //     if (!regione) {
        //
        //
        //         return deferred.resolve({
        //             legenda: null,
        //             iscritti: [
        //                 {
        //                     label: "FENEAL",
        //                     total: 53686,
        //
        //                 },
        //                 {
        //                     label: "UIL",
        //                     total: 12832
        //                 },
        //                 {
        //                     label: "UIL-POSTE",
        //                     total: 191583
        //                 },
        //             ]
        //         })
        //     } else {
        //
        //
        //         return deferred.resolve({
        //             legenda: null,
        //             iscritti: [
        //                 {
        //                     label: "FENEAL",
        //                     total: 21411,
        //
        //                 },
        //                 {
        //                     label: "UIL",
        //                     total: 3211
        //                 },
        //                 {
        //                     label: "UIL-POSTE",
        //                     total: 54322
        //                 },
        //             ]
        //         })
        //     }
        // }, 1000)
        //
        //
        // return deferred.promise();
    }
    
    return exports;

});