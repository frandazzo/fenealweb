package applica.feneal.services.utils;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.QuoteAssociativeService;
import applica.framework.LoadRequest;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/06/2016.
 */
@Component
public class StatisticsUtils {
    @Autowired
    private ImportazioniRepository impRep;

    @Autowired
    private IscrizioniRepository iscRep;

    @Autowired
    private LiberoDbNazionaleRepository lib;


    @Autowired
    private QuoteAssocRepository quoteRep;


    @Autowired
    private QuoteAssociativeService quotServ;



    public int getNumIscrittiTuttiISettoriPerAnnoETerritorio(int anno, String territorio){
        //questo metodo è utile per il report sull'andamento degli iscritti nei territori accorpati
        //e deve essere ripeturo per ogni provincia per ogni anno

        //per prima cosa recuipero tutte le importazioni dell'anno
        LoadRequest req = LoadRequest.build().filter("nomeProvincia", territorio).filter("anno", anno);
        List<Importazione> importaioni = impRep.find(req).getRows();
        if (importaioni.size() == 0)
            return 0;


        List<Integer> ids = importaioni.stream().map(importazione -> importazione.getIid()).collect(Collectors.toList());
        String idsContainsString =  StringUtils.join(ids, ',');
        //eseguo la query

        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select count(distinct Id_Lavoratore) from Iscrizioni where Id_Importazione in ( " + idsContainsString + " )"  );


                    box.setValue(((BigInteger)query.uniqueResult()).intValue());

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });


        return (Integer)box.getValue();



    }


    public int getNumIscrittiPerSettoriPerAnnoETerritorio(int anno, String territorio, String settore){
        //questo metodo è utile per il report sull'andamento degli iscritti per provincia
        //e deve essere ripeturo per ogni anno e ognuno dei tre settori
        //per prima cosa recuipero tutte le importazioni dell'anno
        LoadRequest req = LoadRequest.build().filter("nomeProvincia", territorio).filter("anno", anno).filter("settore", settore);
        List<Importazione> importaioni = impRep.find(req).getRows();
        if (importaioni.size() == 0)
            return 0;


        List<Integer> ids = importaioni.stream().map(importazione -> importazione.getIid()).collect(Collectors.toList());
        String idsContainsString =  StringUtils.join(ids, ',');
        //eseguo la query

        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select count(distinct Id_Lavoratore) from Iscrizioni where Id_Importazione in ( " + idsContainsString + " )"  );


                    box.setValue(((BigInteger)query.uniqueResult()).intValue());

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });


        return (Integer)box.getValue();
    }

    public int getNumIscrittiPerEntePerAnnoETerritorio(int anno, String territorio, String ente){
        //questo metodo è utile per il report sull'andamento degli iscritti per il settore edile
        //e deve essere ripeturo per ogni anno e ognuno dei gìdegli enti enti presenti nelle iscrizioni inserite


        //questo metodo è utile per il report sull'andamento degli iscritti per provincia
        //e deve essere ripeturo per ogni anno e ognuno dei tre settori
        //per prima cosa recuipero tutte le importazioni dell'anno
        LoadRequest req = LoadRequest.build().filter("nomeProvincia", territorio).filter("anno", anno).filter("settore", "EDILE");
        List<Importazione> importaioni = impRep.find(req).getRows();
        if (importaioni.size() == 0)
            return 0;


        List<Integer> ids = importaioni.stream().map(importazione -> importazione.getIid()).collect(Collectors.toList());
        String idsContainsString =  StringUtils.join(ids, ',');
        //eseguo la query

        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select count(distinct Id_Lavoratore) from Iscrizioni where Id_Importazione in ( " + idsContainsString + " ) and ente=:ente"  );

                    query.setString("ente", ente);
                    box.setValue(((BigInteger)query.uniqueResult()).intValue());

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });


        return (Integer)box.getValue();


    }

    public List<String> getListaEntiForTerritorio(String territorio){
        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select distinct ente from Iscrizioni where NomeProvincia=:territorio and settore=:settore" ).addScalar("ente");
                    query.setString("territorio", territorio);
                    query.setString("settore", "EDILE");
                    List<Object[]> objects = query.list();
                    box.setValue(objects);

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });

        List<Object[]> result = (List<Object[]>)box.getValue();
        List<String> res = new ArrayList<>();
        for (Object s : result) {
            res.add(s.toString());
        }
        return res;

    }

    public  List<Integer> getListaAnniImportazioniPerTerritorio(String territorio, String settore){

        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = null;
                    if (StringUtils.isEmpty(settore)){
                        query= s.createSQLQuery(
                                "select distinct anno from Importazioni where NomeProvincia=:territorio" ).addScalar("anno");
                        query.setString("territorio", territorio);

                    }
                   else{
                        query= s.createSQLQuery(
                                "select distinct anno from Importazioni where NomeProvincia=:territorio and settore=:settore" ).addScalar("anno");
                        query.setString("territorio", territorio);
                        query.setString("settore", settore);
                    }


                    List<Object[]> objects = query.list();
                    box.setValue(objects);

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });

        List<Object[]> result = (List<Object[]>)box.getValue();
        List<Integer> res = new ArrayList<>();
        for (Object s : result) {
            res.add(Integer.parseInt(String.valueOf(s)));
        }
        return res;


    }

    public  List<Integer> getListaAnniImportazioniPerTerritorio(List<String> territorio){

        final Box box = new Box();

        String territori = "";
        for (String s : territorio) {
            if (StringUtils.isEmpty(territori)){
                territori = "'" + s + "'";
            }else{
                territori = territori+ ", "  + "'" + s + "'";
            }
        }

        final String t = territori;

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                                "select distinct anno from Importazioni where NomeProvincia in (" + t + ")" ).addScalar("anno");





                    List<Object[]> objects = query.list();
                    box.setValue(objects);

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });

        List<Object[]> result = (List<Object[]>)box.getValue();
        List<Integer> res = new ArrayList<>();
        for (Object s : result) {
            res.add(Integer.parseInt(String.valueOf(s)));
        }
        return res;


    }


    public  int numUltimiIscrittiDbNazionale(String territorio,String ente) {
        //recupero tutte le importaizone dal db nazionale
        LoadRequest req = LoadRequest.build().filter("nomeProvincia", territorio).filter("settore", "EDILE");
        List<Importazione> imps = impRep.find(req).getRows();

        if (imps.size() == 0)
            return  0;
        //prendo quella con l'anno maggiore e il periodo maggiore
        Importazione greater = retrieveGreaterImportaizone(imps, ente);

        if (greater == null)
            return 0;


        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select count(*) from Iscrizioni where Id_Importazione=:importazione and Ente=:ente" );
                    query.setInteger("importazione", greater.getIid());
                    query.setString("ente", ente);

                    box.setValue(((BigInteger)query.uniqueResult()).intValue());

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });


        return (Integer)box.getValue();

    }

    private  Importazione retrieveGreaterImportaizone(List<Importazione> imps, String ente) {

        int anno = 0;
        Importazione impSem1= null;
        Importazione impSem2 = null;


        for (Importazione imp : imps) {
            if (imp.getAnno() >= anno){
                if (imp.getPeriodo() == 1){

                    //prima di assegnare il corretto valore verifico che l'importazione sia riferita allo stesso
                    //ente
                    if (containsSubstriptionsForEnte(imp, ente)){
                        anno = imp.getAnno();
                        impSem1 = imp;
                        if (impSem2 != null)
                            if (impSem2.getAnno() != anno)
                                impSem2 = null;
                    }

                }
                else{
                    if (containsSubstriptionsForEnte(imp, ente)){
                        anno = imp.getAnno();
                        impSem2 = imp;
                        if (impSem1 != null)
                            if (impSem1.getAnno() != anno)
                                impSem1 = null;
                    }

                }

            }

        }

        //adesso nopn mi rimane che sceglierne uno dei due
        if (impSem2 != null)
            return impSem2;

        return impSem1;


    }

    private  boolean containsSubstriptionsForEnte(Importazione imp, String ente) {
        //devo recuperare una sola iscrizione per la determinata importazione

        LoadRequest req = LoadRequest.build().filter("id_Importazione",imp.getIid()).filter("ente", ente);
        req.setPage(1);
        req.setRowsPerPage(1);
        Iscrizione isc  = iscRep.find(req) .findFirst().orElse(null);

        if (isc == null)
            return false;

        if (ente.equals(isc.getEnte()))
            return true;

        return false;

    }

    public int numNonIscritti(String territorio, String ente, String iscrittoA) {


        final Box box = new Box();

        lib.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = s.createSQLQuery(
                            "select count(*) from Lavoratori_Liberi imp where imp.NomeProvinciaFeneal=:territorio and imp.Ente=:ente and imp.IscrittoA=:iscrittoA");
                    query.setString("territorio", territorio);
                    query.setString("ente", ente);
                    query.setString("iscrittoA", iscrittoA);

                    box.setValue(((BigInteger)query.uniqueResult()).intValue());

                    tx.commit();

                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();

                }
                finally{
                    s.close();

                }



            }
        });


        return (Integer)box.getValue();



    }

    public double calculateTassoSindacalizzaizone(int numUltimiIscrittiDbNazionale, int numNonIscrittiFilca, int numNonIscrittiFILLEA, int numNonIscritti) {
        int total = numNonIscritti + numNonIscrittiFilca + numNonIscrittiFILLEA + numUltimiIscrittiDbNazionale;
        if (total == 0)
            return 0;



        int sind = numNonIscrittiFilca + numNonIscrittiFILLEA + numUltimiIscrittiDbNazionale;

        double d = (double)sind * 100/total;
        return (double) Math.round(d * 100) / 100;
    }

    public int numIscrittiUltimaIQP(String territorio, String ente) {
        LoadRequest req = LoadRequest.build().filter("tipoDocumento", "IQP");
        List<RiepilogoQuoteAssociative> l = quoteRep.find(req).getRows();

        if (l.size() == 0)
            return 0;


        //recupero l'ultima IQP
        RiepilogoQuoteAssociative current = retrieveLast(l);

        List<DettaglioQuotaAssociativa> dett = quotServ.getDettagliQuota(current.getLid(), null);

        return dett.size();

    }

    private RiepilogoQuoteAssociative retrieveLast(List<RiepilogoQuoteAssociative> l) {
        if (l.size() == 1)
            return l.get(0);


        Collections.sort(l, new Comparator<RiepilogoQuoteAssociative>() {
            @Override
            public int compare(final RiepilogoQuoteAssociative object1, final RiepilogoQuoteAssociative object2) {
                return -1 * object1.getDataRegistrazione().compareTo(object2.getDataRegistrazione());
            }
        });


        return l.get(0);
    }
}
