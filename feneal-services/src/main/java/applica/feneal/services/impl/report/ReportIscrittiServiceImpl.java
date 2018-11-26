package applica.feneal.services.impl.report;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.feneal.domain.model.dbnazionale.UiIscrittiNazionaleExport;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import applica.feneal.services.ReportIscrittiService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by angelo on 29/05/2016.
 */
@Service
public class ReportIscrittiServiceImpl implements ReportIscrittiService {

    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private DelegheService delServ;


    @Autowired
    private SectorRepository secRep;


    @Autowired
    private GeoService geo;


    @Override
    public List<DettaglioQuotaAssociativa> retrieveQuoteIscritti(UiIscrittoReportSearchParams params) {

        Date dateFrom = createDate(1, params.getDatefromMonthReport(), params.getDatefromYearReport());
        Date dateTo = createDate(0, params.getDatetoMonthReport(), params.getDatetoYearReport());
        String province = params.getProvince();
        String typeQuoteCash = params.getTypeQuoteCash();
        String settore =  params.getSector();
        String ente = Sector.sector_edile.equals(settore) ? params.getParithetic() : null;
        String azienda = params.getFirm();

        //posso adesso fare la query

        LoadRequest req = LoadRequest.build();

        // filtro innanzitutto sulla data di competenza con i vari casi

//        Disjunction d = new Disjunction();
//        List<Filter> orFilters = new ArrayList<>();
//
//        Conjunction c1 = new Conjunction();
//        List<Filter> andFilters1 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataInizio", dateFrom, Filter.GTE);
//            andFilters1.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataFine", dateTo, Filter.LTE);
//            andFilters1.add(f2);
//        }
//        c1.setChildren(andFilters1);
//        orFilters.add(c1);
//
//        Conjunction c2 = new Conjunction();
//        List<Filter> andFilters2 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataInizio", dateFrom, Filter.LTE);
//            andFilters2.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataInizio", dateTo, Filter.GTE);
//            andFilters2.add(f2);
//        }
//        c2.setChildren(andFilters2);
//        orFilters.add(c2);
//
//
//        Conjunction c3 = new Conjunction();
//        List<Filter> andFilters3 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataFine", dateFrom, Filter.LTE);
//            andFilters3.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataFine", dateTo, Filter.GTE);
//            andFilters3.add(f2);
//        }
//        c3.setChildren(andFilters3);
//        orFilters.add(c3);
//
//
//        d.setChildren(orFilters);
//        req.getFilters().add(d);

        if (!dateFrom.before(dateTo)){
            Date dd = dateTo;
            dateTo = dateFrom;
            dateFrom = dd;
        }

        req.getFilters().add(new Filter("dataInizio", dateTo, Filter.LTE));
        req.getFilters().add(new Filter("dataFine", dateFrom, Filter.GTE));

        if (!StringUtils.isEmpty(province)){
            Filter f3 = new Filter("provincia", province, Filter.LIKE);
            req.getFilters().add(f3);
        }

        if (!StringUtils.isEmpty(typeQuoteCash)){
            Filter f6 = new Filter("tipoDocumento", typeQuoteCash, Filter.EQ);
            req.getFilters().add(f6);
        }

        // Solo se il tipo di quota non è quello INPS (IQI) tengo conto dell'eventuale campo di ricerca Settore, Ente, Azienda
        if (StringUtils.isEmpty(typeQuoteCash) || (!StringUtils.isEmpty(typeQuoteCash) && !RiepilogoQuoteAssociative.IQI.equals(typeQuoteCash))) {

            if (!StringUtils.isEmpty(settore)) {
                Filter f7 = new Filter("settore", settore, Filter.EQ);
                req.getFilters().add(f7);
            }

            if (ente != null) {
                if (!StringUtils.isEmpty(ente)) {
                    Filter f8 = new Filter("ente", ente, Filter.EQ);
                    req.getFilters().add(f8);
                }
            }

            if (!StringUtils.isEmpty(azienda)) {
                Filter f9 = new Filter("idAzienda", azienda, Filter.EQ);
                req.getFilters().add(f9);
            }
        }

        List<DettaglioQuotaAssociativa> quoteIscritti = dettRep.find(req).getRows();

        List<DettaglioQuotaAssociativa> quoteIscrittiSenzaDuplicati = new ArrayList<>();

        // Eventualmente faccio visualizzare una sola volta il record relativo ad un iscritto
        for (DettaglioQuotaAssociativa quota : quoteIscritti) {
            if (quoteIscrittiSenzaDuplicati.stream().filter(q -> q.getIdLavoratore() == quota.getIdLavoratore()).count() == 0)
                quoteIscrittiSenzaDuplicati.add(quota);
        }




        //a questo punto posso verificare eventuialmente l'esistenza della delega attiva o accettata
        if (!params.isDelegationActiveExist())
            return quoteIscrittiSenzaDuplicati;
        //se non sto cercando quote associative....
        if (!params.getTypeQuoteCash().equals("IQA"))
            return quoteIscrittiSenzaDuplicati;
        //se non sto cercando quote nel settore edile o impianti fissi non conto la verifica dell'esistenza della dleega
        if (!Sector.sector_edile.equals(settore) && !Sector.sector_IMPIANTIFISSI.equals(settore))
            return  quoteIscrittiSenzaDuplicati;

        //per ogni quota verifico l'esistenza di una delega attiva o accettata

        List<DettaglioQuotaAssociativa> ll = new ArrayList<>();
        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quoteIscrittiSenzaDuplicati) {


            String az = azienda;
            if (Sector.sector_IMPIANTIFISSI.equals(settore) && StringUtils.isEmpty(azienda)){
                //recupero l'azienda dall'identificativo della quota
                Azienda a = azRep.get(dettaglioQuotaAssociativa.getIdAzienda()).orElse(null);
                if (a != null)
                    az = a.getDescription();
            }


            if (!StringUtils.isEmpty(province)){
                if (delServ.hasWorkerDelegaAttivaOAccettata(dettaglioQuotaAssociativa.getIdLavoratore(), params.getSector(),params.getParithetic(), az, params.getProvince())){
                    ll.add(dettaglioQuotaAssociativa);
                }
            }else{
                //se non ho selezionato una provincia nella ricerca... la recupero dalla quota
                if (delServ.hasWorkerDelegaAttivaOAccettata(dettaglioQuotaAssociativa.getIdLavoratore(), params.getSector(),params.getParithetic(), az, dettaglioQuotaAssociativa.getProvincia())){
                    ll.add(dettaglioQuotaAssociativa);
                }

            }

        }

        return ll;
    }

    @Override
    public List<UiIscrittiNazionaleExport> getIscrittiNazionale(int anno, String settoreDescr, String provinciaId, String raggruppa) {


        final Box b = new Box();
        //procedo alla cancellazione
        dettRep.executeCommand(new Command() {

            @Override
            public void execute() {

                String settore = "";
                if (!StringUtils.isEmpty(settoreDescr)){
                    settore = secRep.get(settoreDescr).orElse(null).getDescription();
                }


                String provincia = "";
                if (!StringUtils.isEmpty(provinciaId)){
                    provincia = geo.getProvinceByName(provinciaId).getDescription();
                }


                Session s = dettRep.getSession();
                Transaction tx = s.beginTransaction();

                String settoreGroup = ", i.Settore \n";
                if (!raggruppa.equals("true")){
                    settoreGroup = " \n";
                }

                String whereclause = "";
                if (StringUtils.isEmpty(settore) && StringUtils.isEmpty(provincia)){
                    whereclause = "";
                }else if (!StringUtils.isEmpty(settore) && !StringUtils.isEmpty(provincia)){
                    whereclause = " and i.Settore = '" + settore +"'  and i.Id_Provincia = "+ provincia + " ";
                }else if (!StringUtils.isEmpty(settore) && StringUtils.isEmpty(provincia)){
                    whereclause = " and i.Settore = '" + settore +"'" ;
                }else{
                    whereclause = " and i.Id_Provincia = "+ provincia + " ";
                }

                try {
                    //rimuovo tutti i dettagli per una determinata quota
                    String query = "select l.codicefiscale as CodiceFiscale, l.nome as Nome, l.cognome as Cognome, l.nomenazione as Nazionalita,\n" +
                            " i.NomeProvincia as Territorio, i.settore as Settore from lavoratori l inner join\n" +
                            "iscrizioni i on l.id = i.id_lavoratore  where i.anno = " +  String.valueOf(anno) + whereclause + "\n" +
                            "group by l.CodiceFiscale, i.nomeprovincia " + settoreGroup;


                    SQLQuery q = s.createSQLQuery(query);
                    q.setResultTransformer(Transformers.aliasToBean(UiIscrittiNazionaleExport.class));


                    b.setValue(q.list());


                    tx.commit();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    tx.rollback();
                } finally {
                    s.close();
                }
            }
        });

        return (List<UiIscrittiNazionaleExport>)b.getValue();


    }


    /* Crea oggetto java Date dato il mese e l'anno */
    private Date createDate(int day, String month, String year) {

        if (month == null || year == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (day == 0)
            calendar.set(Calendar.MONTH, Integer.valueOf(month));
        else
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);

        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        Date date = calendar.getTime();

        return date;
    }
}
