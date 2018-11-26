package applica.feneal.services.impl;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.inps.InpsFileRepository;
import applica.feneal.domain.data.core.inps.QuotaInpsRepository;
import applica.feneal.domain.data.core.inps.RistornoInpsRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.importData.CscRowValidator;
import applica.feneal.domain.model.core.importData.InpsFileRowValidator;
import applica.feneal.domain.model.core.inps.*;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.*;
import applica.feneal.services.impl.excel.ExcelValidator;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.fileserver.FileServer;
import applica.framework.management.csv.RowData;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.security.Security;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImpsPugliaServiceImpl implements InpsPugliaServices{

    @Autowired
    private Security sec;

//    @Autowired
//    private FileServer server;

    @Autowired
    private ExcelReaderDataService excelReader;

    @Autowired
    private ReportNonIscrittiService nonIscrittiService;

    @Autowired
    private RistornoInpsRepository ristRep;

    @Autowired
    private InpsFileRepository inpsFileRep;


    @Autowired
    private QuotaInpsRepository inpsQuotaRep;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private GeoService geo;

    @Autowired
    private DelegheService delServ;


    private Long getCountBySQL(String sql)
    {
        Long count = 0L;
        Session session = null;

        try {
            session = inpsQuotaRep.getSession();
            Query query = session.createSQLQuery(sql);

            final List<BigInteger> obj = query.list();
            for (BigInteger l : obj) {
                if (l != null) {
                    count = l.longValue();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }




    @Override
    public long incrociaQuoteInps(RistornoInps ristorno, List<InpsFile> files) throws Exception {

        List<QuotaInps> quote = quoteFromInpsfiles(files.stream().filter(a -> !a.isCsc()).collect(Collectors.toList()));
        List<CscData> datiCsc = cscFromCscFiles(files.stream().filter(a -> a.isCsc()).collect(Collectors.toList()));

        List<QuotaInps> incroci = incrociaQuote(ristorno, quote, datiCsc);


        final Box box = new Box();

        ristRep.executeCommand(new Command() {
            @Override
            public void execute()  {
                Session s = ristRep.getSession();
                Transaction tx = s.beginTransaction();
                try {

                    //logger.log(filename, "StartImport", "Avvio importazione dettagli quote: "  + iscrizioni.size());

                    s.saveOrUpdate(ristorno);

                    files.stream().forEach(a -> {
                        a.setRistornoInps(ristorno);
                        s.saveOrUpdate(a);
                    });

                    incroci.stream().forEach(a -> {
                          s.saveOrUpdate(a);
                    });


                    tx.commit();

                } catch (Exception ex) {
                    tx.rollback();
                    box.setValue(ex);
                } finally {
                    s.close();
                }

            }
        });


        if (box.getValue() != null)
            throw new Exception((Exception)box.getValue());


        return ristorno.getLid();
    }

    @Override
    public List<QuotaInps>  inserisciQuoteInps(String filepath) throws Exception {

        List<QuotaInps> inserted = new ArrayList<>();

        ExcelInfo i = getFileInfoForInps(filepath);

        Hashtable<String, QuotaInps> cache = new Hashtable<>();

        for (RowData rowData : i.getOnlyValidRows()) {

            Lavoratore l = constructLavoratore(rowData);
            if (l != null){
                constructQuotaIfnotExist(l, rowData, inserted, cache);
            }

        }

        return inserted;


    }


    private List<InpsFile> getFilesByRistorno(long ristonoId){
        LoadRequest req = LoadRequest.build().filter("ristornoInps", ristonoId);
        return inpsFileRep.find(req).getRows();
    }


    @Override
    public IncrocioInps getIncrociobyId(long id) throws Exception {

        List<InpsFile> files = getFilesByRistorno(id);
        RistornoInps ristorno = ristRep.get(id).orElse(null);

        List<QuotaInps> quote = quoteFromInpsfiles(files.stream().filter(a -> !a.isCsc()).collect(Collectors.toList()));
        return  new IncrocioInps(ristorno, files, quote);

    }

    @Override
    public List<RistornoInps> getRistorni() {
        return ristRep.find(LoadRequest.build()).getRows();
    }

    @Override
    public void deleteRistorno(long id) {
        final Box box = new Box();



        ristRep.executeCommand(new Command() {
            @Override
            public void execute()  {
                Session s = ristRep.getSession();
                Transaction tx = s.beginTransaction();
                try {

                    s.createSQLQuery(String.format(
                            "update fenealweb_quoteinps set lavoratoreEdile = b'0', importRistornato = 0, \n" +
                                    "referente = null, dataDomanda = null, ristornoId = null, cscFilename = null, cscFilepath = null where ristornoId = %d", id)).executeUpdate();

                    s.createSQLQuery(String.format("Delete from fenealweb_inpsfiles where ristornoInpsId = %d", id)).executeUpdate();

                    s.createSQLQuery(String.format("Delete from fenealweb_ristorni where id = %d", id)).executeUpdate();



                    tx.commit();

                } catch (Exception ex) {
                    tx.rollback();
                    box.setValue(ex);
                } finally {
                    s.close();
                }

            }
        });



    }

    @Override
    public List<QuotaInps> findQuote(Date lastDateStart, Date lastDateEnd) {

        LoadRequest req = LoadRequest.build();
        if (lastDateStart != null)
        {
            Filter f = new Filter();
            f.setProperty("dataValuta");
            f.setValue(lastDateStart);
            f.setType(Filter.GTE);
            req.getFilters().add(f);
        }

        if (lastDateEnd != null)
        {
            Filter f = new Filter();
            f.setProperty("dataValuta");
            f.setValue(lastDateEnd);
            f.setType(Filter.LTE);
            req.getFilters().add(f);
        }

        return inpsQuotaRep.find(req).getRows();




    }

    @Override
    public IncrocioInps getIncrociobyIdWithoutQuote(long id) {

        List<InpsFile> files = getFilesByRistorno(id);
        RistornoInps ristorno = ristRep.get(id).orElse(null);


        return  new IncrocioInps(ristorno, files, new ArrayList<>());

    }

    private void constructQuotaIfnotExist(Lavoratore l, RowData rowData, List<QuotaInps> inserted, Hashtable<String, QuotaInps> cache    ) throws ParseException {

        QuotaInps t = extractInpsDataExcelRowData(rowData, cache);

        if (t != null){
            inserted.add(t);
            return;
        }


        //creo la quota inps
        QuotaInps q = new QuotaInps();
        q.setLavoratore(l);
        q.setNumDomanda(rowData.getData().get("N. Domanda"));
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        Date s = ff.parse(rowData.getData().get("Data Valuta"));
        q.setDataValuta(s);
        q.setImporto(Double.parseDouble(rowData.getData().get("Importo Pagamento")));
        q.setPatronato(rowData.getData().get("Patronato"));


        inpsQuotaRep.save(q);
        inserted.add(q);

    }

    private List<QuotaInps> incrociaQuote(RistornoInps ristorno, List<QuotaInps> quote, List<CscData> datiCsc) {

        Hashtable<String, Boolean> cacheEdili = new Hashtable<>();


        List<QuotaInps> result = new ArrayList<>();
        //ciclo su tutte le quote per ricercare se tra i dati csc c'è qualcuno con cf
        //uguale e data domanda precedente alla data valuta
        for (QuotaInps quotaInps : quote) {

            //se ovviamente la quota inps è stata gia ristornata in precedenza non
            //ne tengo conto
            if (quotaInps.getRistorno() == null){
                String cf = quotaInps.getLavoratore().getFiscalcode();
                Date valuta = quotaInps.getDataValuta();
                CscData domanda = datiCsc.stream().filter(a -> {
                    return cf.equals(a.getCodiceFiscale()) && valuta.getTime() >= a.getData().getTime();
                }).findFirst().orElse(null);

                if (domanda != null){
                    //se ho trovato la domanda allora posso inserire nella quota tutte le informazioni
                    quotaInps.setDataDomanda(domanda.getData());
                    quotaInps.setReferente(domanda.getReferente());
                    quotaInps.setRistorno(ristorno);
                    quotaInps.setLavoratoreEdile(isLavoratoreEdile(quotaInps, cacheEdili));
                    quotaInps.calculateImportoRistornato(ristorno);
                    quotaInps.setCscFilename(domanda.getFileName());
                    quotaInps.setCscFilepath(domanda.getFilePath());
                    //aggiungo alla lista dei risultati
                    result.add(quotaInps);
                }
            }

        }
        return result;
    }

    private boolean isLavoratoreEdile(QuotaInps quota, Hashtable<String, Boolean> cacheEdili){

        if (cacheEdili.containsKey(quota.getLavoratore().getFiscalcode())){
            return cacheEdili.get(quota.getLavoratore().getFiscalcode());
        }else{
            Boolean c = calculateIfLavoratoreEdile(quota);
            cacheEdili.put(quota.getLavoratore().getFiscalcode(), c);
            return c;
        }
    }

    private Boolean calculateIfLavoratoreEdile(QuotaInps quota) {
        String queryOnDelegheEdili = String.format("SELECT count(d.id) " +
                        "FROM fenealweb_delega d " +
                        "inner join fenealweb_lavoratore l " +
                        "on l.id = d.workerId  where d.companyId = %s " +
                        "and l.fiscalcode = '%s' " +
                        "and  d.sectorId = 1",
                ((User) sec.getLoggedUser()).getCompany().getLid(),
                quota.getLavoratore().getFiscalcode());


        Long numDeleghe = getCountBySQL(queryOnDelegheEdili);

        boolean edile = numDeleghe > 0;//delServ.hasDelegaEdile(quota.getLavoratore().getLid());
        if (edile)
            return true;


        String queryDbNazionale = String.format("SELECT count(i.id) FROM feneal.iscrizioni i inner join lavoratori l on l.id = i.Id_Lavoratore where l.CodiceFiscale = '%s'", quota.getLavoratore().getLid());
        Long numIscrizioniDbnaz  = getCountBySQL(queryDbNazionale);

        boolean edileDbNaz = numIscrizioniDbnaz > 0;//delServ.hasDelegaEdile(quota.getLavoratore().getLid());
        if (edileDbNaz)
            return true;


        String queryOnLiberi = String.format("SELECT count(CodiceFiscale) " +
                "FROM %s where Id_ProvinciaFeneal = %s and CodiceFiscale = '%s'",
                "lavoratori_liberi", ((User) sec.getLoggedUser()).getDefaultProvince().getIid(),
                quota.getLavoratore().getFiscalcode());


        String queryOnLiberiCopy = String.format("SELECT count(CodiceFiscale) " +
                        "FROM %s where Id_ProvinciaFeneal = %s and CodiceFiscale = '%s'",
                "lavoratori_liberi_copy", ((User) sec.getLoggedUser()).getDefaultProvince().getIid(),
                quota.getLavoratore().getFiscalcode());

        Long numLibery = getCountBySQL(queryOnLiberi);

        boolean libero = numLibery > 0;//delServ.hasDelegaEdile(quota.getLavoratore().getLid());
        if (libero)
            return true;

        Long numLiberyCopy = getCountBySQL(queryOnLiberiCopy);
        return numLiberyCopy > 0;


    }

    private ExcelInfo getFileInfoForCsc(String path) throws Exception {
        return   excelReader.readExcelFile(path,0,
                0,0,new ExcelValidator(Arrays.asList("FISCALE",
                        "COGNOME_UTENTE",
                        "NOME_UTENTE",
                        "DATA DOMANDA NASPI",
                        "COGNOME_REFERENTE",
                        "NOME_REFERENTE")),new CscRowValidator());
    }


    private ExcelInfo getFileInfoForInps(String path) throws Exception {
        return  excelReader.readExcelFile(path,0,
                10,1,new ExcelValidator(Arrays.asList("Cognome/Nome",
                        "Codice Fiscale",
                        "Data di Nascita",
                        "Importo Pagamento",
                        "Data Valuta",
                        "N. Domanda",
                        "Comune Res.",
                        "Patronato")),new InpsFileRowValidator());
    }

    private List<CscData> cscFromCscFiles(List<InpsFile> files) throws Exception {

        List<CscData> result = new ArrayList<>();

        for (InpsFile file : files) {
            ExcelInfo i= getFileInfoForCsc(file.getFilepath());

            for (RowData rowData : i.getOnlyValidRows()) {
                result.add(extractCscDataExcelRowData(rowData, file));
            }
        }

        return  result;
    }

    private CscData extractCscDataExcelRowData(RowData data, InpsFile file){

        CscData d = new CscData();
        d.setCognome(data.getData().get("COGNOME_UTENTE"));
        d.setNome(data.getData().get("NOME_UTENTE"));
        d.setCodiceFiscale(data.getData().get("FISCALE"));
        String nomeRef = data.getData().get("NOME_REFERENTE");
        String cognomeRef = data.getData().get("COGNOME_REFERENTE");
        d.setReferente(cognomeRef + " " + nomeRef);


        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date s = ff.parse(data.getData().get("DATA DOMANDA NASPI"));
            d.setData(s);
        }catch (Exception ex){
            d.setData(new Date());
        }
        d.setFileName(file.getFilename());
        d.setFilePath(file.getFilepath());
        return d;
    }

    private List<QuotaInps> quoteFromInpsfiles(List<InpsFile> files) throws Exception {
        List<QuotaInps> result = new ArrayList<>();
        Hashtable<String, QuotaInps> cache = new Hashtable<>();

        for (InpsFile file : files) {
            ExcelInfo i= getFileInfoForInps(file.getFilepath());

            for (RowData rowData : i.getOnlyValidRows()) {
                result.add(extractInpsDataExcelRowData(rowData, cache));
            }
        }

        return  result;
    }

    private QuotaInps extractInpsDataExcelRowData(RowData data, Hashtable<String, QuotaInps> cache) throws ParseException {
        String numDomanda = data.getData().get("N. Domanda");
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        Date s = ff.parse(data.getData().get("Data Valuta"));


        //ricerco nella cache la quota riferita ad una deta e ad una certa doamnda
        String key = numDomanda + String.valueOf(s.getTime());
        if (cache.containsKey(key))
            return cache.get(key);

        QuotaInps q = findByNumeroDomandaCodDataValuta(numDomanda,s);
        if (q != null)
            cache.put(key,q);

        return q;
    }

    private QuotaInps findByNumeroDomandaCodDataValuta(String numeroDoamnda, Date valuta){

        LoadRequest req = LoadRequest.build().
                filter("numDomanda", numeroDoamnda)
                .filter("dataValuta", valuta);

        return inpsQuotaRep.find(req).findFirst().orElse(null);

    }


    private Lavoratore constructLavoratore(RowData rowData) throws Exception {
        Lavoratore l = lavSvc.findLavoratoreByFiscalCode(rowData.getData().get("Codice Fiscale").trim());
        if (l!= null)
            return l;

        String[] nomeCopgnome = rowData.getData().get("Cognome/Nome").split("/");
        l = new Lavoratore();
        l.setSurname(nomeCopgnome[0]);
        l.setName(nomeCopgnome[1]);
        l.setSex("M");
        l.setFiscalcode(rowData.getData().get("Codice Fiscale"));

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        try{
            l.setBirthDate(ff.parse(rowData.getData().get("Data di Nascita")));
        }catch (Exception e){
            GregorianCalendar c = new GregorianCalendar();
            c.set(Calendar.YEAR, 1900);
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND,0);
            l.setBirthDate(c.getTime());
        }



        if (rowData.getData().get("Comune Res.") != null){

            City cc1 = geo.getCityByName(rowData.getData().get("Comune Res."));
            if (cc1!= null){
                l.setLivingCity(cc1.getDescription());
                l.setLivingProvince(geo.getProvinceById(cc1.getIdProvince()).getDescription());
            }
        }

        lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
        return l;
    }
}
