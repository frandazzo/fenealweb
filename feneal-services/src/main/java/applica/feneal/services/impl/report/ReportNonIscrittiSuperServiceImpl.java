package applica.feneal.services.impl.report;

import applica.feneal.data.hibernate.geo.RegionsHibernateRepository;
import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleSecondaryRepository;
import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.data.geo.CountriesRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.dbnazionale.DelegaNazionale;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.ReportNonIscrittiSuper;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportNonIscrittiSuperServiceImpl implements ReportNonIscrittiSuper {



    @Autowired
    private ProvinceRepository proRep;

    @Autowired
    private RegonsRepository regRep;

    @Autowired
    private SectorRepository secRep;



    @Autowired
    private ParitheticRepository enteRep;


    @Autowired
    private Security sec;










    @Override
    public List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params, boolean isOldReportStyle) {

        final Province p = proRep.get(Integer.parseInt(params.getProvince())).orElse(null);

        final Paritethic t = enteRep.get(Long.parseLong(params.getParithetic())).orElse(null);


        final String queryLiberi = createQueryForLiberi(p.getDescription(), t.getType());
        final String queryIscrizioniDbNazionale = createQueryForIscrizioniDbNazionale(p.getDescription(), t.getType());

        final String queryDeleghe = createQueryForDeleghe(p.getDescription(), t.getType(), p.getIdRegion());
        final String queryNonIscrizioniAttuali = createQueryForNonIscrizioniAttuali(p.getDescription(), t.getType(), p.getIdRegion());
        final String queryNonIscrizioni = createQueryForNonIscrizioni(p.getDescription(), t.getType(), p.getIdRegion());

        final Box box = new Box();

        enteRep.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = enteRep.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();

                    List<Object[]> deleghe = new ArrayList<>();
                    List<Object[]> iscrizioniAltroSindacatoAttuali= new ArrayList<>();
                    List<Object[]> iscrizioniAltroSindacato= new ArrayList<>();

                    if (!isOldReportStyle){
                        deleghe = createHibernateQueryForDeleghe(s,queryDeleghe).list();
                        iscrizioniAltroSindacatoAttuali= createHibernateQueryForNonIscrizioniAttuali(s,queryNonIscrizioniAttuali).list();
                        iscrizioniAltroSindacato= createHibernateQueryForNonIscrizioni(s,queryNonIscrizioni).list();
                    }



                    List<Object[]> isscrizioni = createHibernateQueryForIscrizioniDbNazionale(s,queryIscrizioniDbNazionale).list();
                    List<Object[]> liberi = createHibernateQueryForLibery(s,queryLiberi).list();

                    tx.commit();



                    //metto i dati di iscrizioni deleghe e iscrizioni altro indacato
                    // in una hashtable per codice fiscale
                    //in modo da poterle recuperare immediatamente

                    //materializoz le icrizoini
                    Hashtable<String, List<Iscrizione>> i = mateiralizeHashIscrizioni(isscrizioni);
                    Hashtable<String, List<DelegaNazionale>> i1 = materializeHashDeleghe(deleghe);
                    Hashtable<String, List<LiberoDbNazionale>> i2 = materializeHashNonIscrizioni(iscrizioniAltroSindacatoAttuali, iscrizioniAltroSindacato);
                    List<LiberoDbNazionale> a = new ArrayList<>();
                    for (Object[] objects : liberi) {
                        LiberoDbNazionale ss = materializeLiberi(objects,t.getType(),p.getDescription());
                        materialiazeObjectContents(i, i1, i2, ss);
                        a.add(ss);
                    }





                    //associo adesso al record liberi le iscrizioni


                    box.setValue(a);


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

        return (List<LiberoDbNazionale>)box.getValue();



    }

    @Override
    public LiberoDbNazionale analyzeFiscaleCodeData(String fiscalCode) {

        User u = ((User) sec.getLoggedUser());
        int regionId = u.getCompany().getRegionId();

        final String queryIscrizioniDbNazionale = createQueryForIscrizioniDbNazionalePerCodiceFiscale(fiscalCode);

        final String queryDeleghe = createQueryForDeleghePerCodiceFiscale(regionId, fiscalCode);
        final String queryNonIscrizioniAttuali = createQueryForNonIscrizioniAttualiPerCodiceFiscale(regionId, fiscalCode);
        final String queryNonIscrizioni = createQueryForNonIscrizioniPerCodiceFiscale(regionId, fiscalCode);

        final Box box = new Box();

        enteRep.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = enteRep.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();

                    List<Object[]> deleghe = createHibernateQueryForDeleghe(s,queryDeleghe).list();
                    List<Object[]> iscrizioniAltroSindacatoAttuali= createHibernateQueryForNonIscrizioniAttuali(s,queryNonIscrizioniAttuali).list();
                    List<Object[]> iscrizioniAltroSindacato= createHibernateQueryForNonIscrizioni(s,queryNonIscrizioni).list();
                    List<Object[]> isscrizioni = createHibernateQueryForIscrizioniDbNazionale(s,queryIscrizioniDbNazionale).list();


                    tx.commit();



                    //metto i dati di iscrizioni deleghe e iscrizioni altro indacato
                    // in una hashtable per codice fiscale
                    //in modo da poterle recuperare immediatamente

                    //materializoz le icrizoini
                    Hashtable<String, List<Iscrizione>> i = mateiralizeHashIscrizioni(isscrizioni);
                    Hashtable<String, List<DelegaNazionale>> i1 = materializeHashDeleghe(deleghe);
                    Hashtable<String, List<LiberoDbNazionale>> i2 = materializeHashNonIscrizioni(iscrizioniAltroSindacatoAttuali, iscrizioniAltroSindacato);

                    LiberoDbNazionale ss = new LiberoDbNazionale();
                    materialiazeObjectContents(i, i1, i2, ss);


                    //associo adesso al record liberi le iscrizioni


                    box.setValue(ss);


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

        return (LiberoDbNazionale)box.getValue();


    }

    private void materialiazeObjectContents(Hashtable<String, List<Iscrizione>> i, Hashtable<String, List<DelegaNazionale>> i1, Hashtable<String, List<LiberoDbNazionale>> i2, LiberoDbNazionale ss) {
        List<Iscrizione> sd = i.get(ss.getCodiceFiscale());
        List<LiberoDbNazionale> sdc = i2.get(ss.getCodiceFiscale());
        List<DelegaNazionale> sddel = i1.get(ss.getCodiceFiscale());
        if (sd == null)
            sd = new ArrayList<>();
        if (sddel == null)
            sddel = new ArrayList<>();
        if (sdc == null)
            sdc = new ArrayList<>();
        ss.setIscrizioni(sd);
        ss.setDeleghe(sddel);
        ss.setIscrizioniAltroSindacato(sdc);
    }

    private Hashtable<String, List<Iscrizione>> mateiralizeHashIscrizioni(List<Object[]> isscrizioni) {
        Hashtable<String,List<Iscrizione>> i = new Hashtable<>();
        for (Object[] objects : isscrizioni) {
            Iscrizione r = materializeIscrizioniDbNazionle(objects);
            if (!i.containsKey(r.getCodiceFiscale())){
                List<Iscrizione> a1 = new ArrayList<>();
                a1.add(r);
                i.put(r.getCodiceFiscale(), a1);
            }

            else{
                List<Iscrizione> a1 = i.get(r.getCodiceFiscale());
                a1.add(r);
            }
        }
        return i;
    }
    private Hashtable<String, List<DelegaNazionale>> materializeHashDeleghe(List<Object[]> deleghe) {

        //recupero ttute le entittà necessarie alla eventuale materializzazione delle deleghe
        final List<Province> provinces = proRep.find(null).getRows();
        final List<Region> regions = regRep.find(null).getRows();
        final List<Paritethic> enti = enteRep.find(null).getRows();
        final List<Sector> settori = secRep.find(null).getRows();


        Hashtable<String,List<DelegaNazionale>> i = new Hashtable<>();
        for (Object[] objects : deleghe) {
            DelegaNazionale r = materializeDelegheDbNazionle(objects,settori,enti,provinces,regions);
            if (!i.containsKey(r.getCodiceFiscale())){
                List<DelegaNazionale> a1 = new ArrayList<>();
                a1.add(r);
                i.put(r.getCodiceFiscale(), a1);
            }

            else{
                List<DelegaNazionale> a1 = i.get(r.getCodiceFiscale());
                a1.add(r);
            }
        }
        return i;
    }
    private Hashtable<String, List<LiberoDbNazionale>> materializeHashNonIscrizioni(List<Object[]> attuali, List<Object[]> vecchie) {




        Hashtable<String,List<LiberoDbNazionale>> i = new Hashtable<>();
        for (Object[] objects : attuali) {
            LiberoDbNazionale r = materializeNonIscrizioniAttuali(objects);
            if (!i.containsKey(r.getCodiceFiscale())){
                List<LiberoDbNazionale> a1 = new ArrayList<>();
                a1.add(r);
                i.put(r.getCodiceFiscale(), a1);
            }

            else{
                List<LiberoDbNazionale> a1 = i.get(r.getCodiceFiscale());
                a1.add(r);
            }
        }


        for (Object[] objects : vecchie) {
            LiberoDbNazionale r = materializeNonIscrizioni(objects);
            if (!i.containsKey(r.getCodiceFiscale())){
                List<LiberoDbNazionale> a1 = new ArrayList<>();
                a1.add(r);
                i.put(r.getCodiceFiscale(), a1);
            }

            else{
                List<LiberoDbNazionale> a1 = i.get(r.getCodiceFiscale());
                a1.add(r);
            }
        }

        return i;
    }

    private String createQueryForNonIscrizioniPerCodiceFiscale(int idREgione, String codiceFiscale){


//        select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA
//        from lavoratori_liberi_copy
//        where NomeProvinciaFeneal in
//                (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = 150)
//        and iscrittoa <> "" and CodiceFiscale = "CPLMRZ71T23B872M"
        String query =  String.format("select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA " +
                        "from lavoratori_liberi " +
                        "where NomeProvinciaFeneal in" +
                        "  (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = %s)" +
                        "        and iscrittoa <> \"\" and  CodiceFiscale = \"%s\"",
                idREgione, codiceFiscale);

        return query;


    }
    private String createQueryForNonIscrizioni(String nomeProvincia, String nomeEnte, int idREgione){


//        select  t.CodiceFiscale, n.nomeprovinciafeneal, n.currentAzienda, n.liberoAl, n.ente, n.iscrittoa from lavoratori_liberi t
//        inner join
//        (select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA from lavoratori_liberi_copy where NomeProvinciaFeneal in
//                (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = 150 )
//        and iscrittoa <> "") n
//        on t.CodiceFiscale = n.codicefiscale

        String query =  String.format("select  " +
                        "t.CodiceFiscale, " +
                        "n.nomeprovinciafeneal, " +
                        "n.currentAzienda, " +
                        "n.liberoAl, " +
                        "n.ente, " +
                        "n.iscrittoa " +
                        "from lavoratori_liberi t\n" +
                        "        inner join\n" +
                        "        (select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA from lavoratori_liberi_copy where NomeProvinciaFeneal in\n" +
                        "                (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = %s )\n" +
                        "        and iscrittoa <> \"\") n\n" +
                        "        on t.CodiceFiscale = n.codicefiscale" +
                        " where t.NomeProvinciaFeneal = '%s' and t.ente = '%s' ",
                idREgione,  nomeProvincia.replace("'","''"), nomeEnte);

        return query;
    }
    private SQLQuery createHibernateQueryForNonIscrizioni(Session session, String query){
        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("nomeprovinciafeneal")
                .addScalar("currentAzienda")
                .addScalar("liberoAl")
                .addScalar("ente")
                .addScalar("iscrittoa")
                ;
    }
    private LiberoDbNazionale materializeNonIscrizioni(Object[] object){
        LiberoDbNazionale v = new LiberoDbNazionale();

        v.setCodiceFiscale((String)object[0]);
        v.setNomeProvinciaFeneal((String)object[1]);
        v.setCurrentAzienda((String)object[2]);
        v.setLiberoAl((Date)object[3]);
        v.setEnte((String)object[4]);
        v.setIscrittoA((String)object[5]);


        return v;
    }




    private String createQueryForNonIscrizioniAttualiPerCodiceFiscale(int idREgione, String codiceFiscale){


//        select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA
//        from lavoratori_liberi
//        where NomeProvinciaFeneal in
//                (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = 150)
//         and CodiceFiscale = "CPLMRZ71T23B872M"
        String query =  String.format("select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA " +
                        "from lavoratori_liberi " +
                        "where NomeProvinciaFeneal in" +
                        "  (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = %s)" +
                        "   and  CodiceFiscale = \"%s\"",
                idREgione, codiceFiscale);

        return query;


    }
    private String createQueryForNonIscrizioniAttuali(String nomeProvincia, String nomeEnte, int idREgione){


//        select  t.CodiceFiscale, n.nomeprovinciafeneal, n.currentAzienda, n.liberoAl, n.ente, n.iscrittoa from lavoratori_liberi t
//        inner join
//        (select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA from lavoratori_liberi where NomeProvinciaFeneal in
//                (select descrizione as provincia from tb_provincie where ID_TB_REGIONI = 150 and descrizione <> 'NAPOLI')
//        and iscrittoa <> "") n
//        on t.CodiceFiscale = n.codicefiscale
//        where t.NomeProvinciaFeneal = 'NAPOLI' and t.ente = 'CASSA EDILE';
        String query =  String.format("select  " +
                        "t.CodiceFiscale, " +
                        "n.nomeprovinciafeneal, " +
                        "n.currentAzienda, " +
                        "n.liberoAl, " +
                        "n.ente, " +
                        "n.iscrittoa " +
                        "from lavoratori_liberi t\n" +
                        "inner join \n" +
                        " (select CodiceFiscale, nomeprovinciafeneal, currentAzienda, liberoAl, ente, IscrittoA from lavoratori_liberi where NomeProvinciaFeneal in\n" +
                        "(select descrizione as provincia from tb_provincie where ID_TB_REGIONI = %s and descrizione <> '%s') \n" +
                        "and iscrittoa <> \"\") n\n" +
                        "on t.CodiceFiscale = n.codicefiscale " +
                " where t.NomeProvinciaFeneal = '%s' and t.ente = '%s' ",
                idREgione, nomeProvincia.replace("'","''"), nomeProvincia.replace("'","''"), nomeEnte);

        return query;


    }
    private SQLQuery createHibernateQueryForNonIscrizioniAttuali(Session session, String query){
        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("nomeprovinciafeneal")
                .addScalar("currentAzienda")
                .addScalar("liberoAl")
                .addScalar("ente")
                .addScalar("iscrittoa")
                ;
    }
    private LiberoDbNazionale materializeNonIscrizioniAttuali(Object[] object){
        LiberoDbNazionale v = new LiberoDbNazionale();

        v.setCodiceFiscale((String)object[0]);
        v.setNomeProvinciaFeneal((String)object[1]);
        v.setCurrentAzienda((String)object[2]);
        v.setLiberoAl((Date)object[3]);
        v.setEnte((String)object[4]);
        v.setIscrittoA((String)object[5]);


        return v;
    }



    private String createQueryForDeleghePerCodiceFiscale(int idRegione, String codiceFiscale){
//        select a.fiscalcode as CodiceFiscale, a.id as idWorker, d.state, d.documentDate, d.provinceId, d.sectorId,
//                d.paritethicId, c.description, d.acceptDate, d.cancelDate, d.revokeDate, r.ID_TB_REGIONI  as regione
//        from fenealweb_delega d
//        left join fenealweb_collaboratore c
//        on c.id = d.collaboratorId
//        inner join fenealweb_lavoratore a
//        on a.id = d.workerId
//        inner join tb_provincie r
//        on r.ID= d.provinceId where r.ID_TB_REGIONI = 150 and a.fiscalcode = "RCCCCT68A56L083I";

        String query = String.format("select a.fiscalcode as CodiceFiscale, a.id as idWorker, d.state, d.documentDate, d.provinceId, d.sectorId,\n" +
                "                d.paritethicId, c.description, d.acceptDate, d.cancelDate, d.revokeDate, r.ID_TB_REGIONI  as regione, d,notes \n" +
                "        from fenealweb_delega d\n" +
                "        left join fenealweb_collaboratore c\n" +
                "        on c.id = d.collaboratorId\n" +
                "        inner join fenealweb_lavoratore a\n" +
                "        on a.id = d.workerId\n" +
                "        inner join tb_provincie r\n" +
                "        on r.ID= d.provinceId where r.ID_TB_REGIONI = %s and a.fiscalcode = \"%s\"", idRegione, codiceFiscale);
        return query;
    }
    private String createQueryForDeleghe(String nomeProvincia, String nomeEnte, int idRegione){

//
//        select
//        t.CodiceFiscale,a.ID  as idWorker, a.companyId, d.state, d.documentDate, d.provinceId, d.sectorId,
//                d.paritethicId, c.description, d.acceptDate, d.cancelDate, d.revokeDate
//        from
//        lavoratori_liberi t
//        inner join
//        fenealweb_lavoratore a
//        on t.CodiceFiscale = a.fiscalcode
//        inner join fenealweb_delega d
//        on d.workerId = a.id
//        left join fenealweb_collaboratore c
//        on c.id = d.collaboratorId
//        where NomeProvinciaFeneal = 'NAPOLI' and t.ente = 'CASSA EDILE';

        String query =  String.format("select \n" +
                        "t.CodiceFiscale," +
                        "a.ID  as idWorker, " +
                        "a.companyId, " +
                        "d.state, " +
                        "d.documentDate, " +
                        "d.provinceId, " +
                        "d.sectorId, \n" +
                        "d.paritethicId, " +
                        "c.description, " +
                        "d.acceptDate, " +
                        "d.cancelDate, " +
                        "d.revokeDate, \n" +
                        "d.attachment, " +
                        "d.nomeattachment, \n" +
                        "d.notes\n" +
                        "from \n" +
                        "lavoratori_liberi t \n" +
                        "inner join \n" +
                        "fenealweb_lavoratore a \n" +
                        "on t.CodiceFiscale = a.fiscalcode\n" +
                        "inner join fenealweb_delega d \n" +
                        "on d.workerId = a.id\n" +
                        "left join fenealweb_collaboratore c\n" +
                        "on c.id = d.collaboratorId " +
                        "inner join tb_provincie p " +
                        "on p.ID = d.provinceId " +
                        " where NomeProvinciaFeneal = '%s' and ente = '%s' and  p.ID_TB_REGIONI = %s",
                nomeProvincia.replace("'","''"), nomeEnte, idRegione);

        return query;
    }
    private SQLQuery createHibernateQueryForDeleghe(Session session, String query){
        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("idWorker")
                .addScalar("companyId")
                .addScalar("state")
                .addScalar("documentDate")
                .addScalar("provinceId")
                .addScalar("sectorId")
                .addScalar("paritethicId")
                .addScalar("description")
                .addScalar("acceptDate")
                .addScalar("cancelDate")
                .addScalar("revokeDate")
                .addScalar("attachment")
                .addScalar("nomeattachment")
                .addScalar("notes");
    }
    private DelegaNazionale materializeDelegheDbNazionle(Object[] object,
                                                         List<Sector> sectors,
                                                         List<Paritethic> paritetichs,
                                                         List<Province> provinces,
                                                         List<Region> regions){
        DelegaNazionale v = new DelegaNazionale();
        v.setCodiceFiscale((String)object[0]);
        BigInteger dd = (BigInteger)object[1];

        v.setIdWorker(dd.longValue());
        BigInteger dd1 = (BigInteger)object[2];

        v.setCompanyId(dd1.longValue());

        Integer state = (Integer)object[3];
        if (state == Delega.state_subscribe || state == Delega.state_sent)
            v.setState("Sottoscritta");
        else if (state == Delega.state_accepted || state == Delega.state_activated)
            v.setState("Accreditata");
        else if (state == Delega.state_cancelled)
            v.setState("Annullata");
        else
            v.setState("Revocata");

        v.setDocumentDate((Date)object[4]);

        Province pp = provinces.stream()
                .filter(a -> a.getIid() == (Integer)object[5]).findFirst().get();
        v.setProvince(pp.getDescription());

        BigInteger dd2 = (BigInteger)object[6];

        v.setSector(sectors.stream()
                .filter(a -> a.getLid() == dd2.longValue()).findFirst().get().getType());
        BigInteger dd3 = (BigInteger)object[7];
        v.setEnte(paritetichs.stream()
                .filter(a -> a.getLid() == dd3.longValue()).findFirst().get().getType());
        v.setRegion(regions.stream().filter(a -> a.getIid() == pp.getIdRegion()).findFirst().get().getDescription());
        v.setOperator((String)object[8]);

        v.setAcceptDate((Date)object[9]);
        v.setCancelDate((Date)object[10]);
        v.setRevokeDate((Date)object[11]);

        v.setAttachment((String)object[12]);
        v.setNomeattachment((String)object[13]);
        v.setNotes((String)object[14]);
        return v;
    }


    private String createQueryForIscrizioniDbNazionalePerCodiceFiscale(String codiceFisclae){
//        select
//        a.CodiceFiscale,
//                a.ID as idWorker,
//        i.anno, i.NomeProvincia, i.NomeRegione, i.Settore, i.Ente, i.Azienda, i.Piva,
//                i.Livello, i.Quota, i.Periodo, i.Contratto
//        from
//        iscrizioni i
//        inner join
//        lavoratori a
//        on i.Id_Lavoratore = a.ID
//        where a.CodiceFiscale = "BAALA"

        String query =  String.format("        select\n" +
                "        a.CodiceFiscale,\n" +
                "                a.ID as idWorker,\n" +
                "        i.anno, i.NomeProvincia, i.NomeRegione, i.Settore, i.Ente, i.Azienda, i.Piva,\n" +
                "                i.Livello, i.Quota, i.Periodo, i.Contratto\n" +
                "        from\n" +
                " iscrizioni i\n" +
                "        inner join\n" +
                "        lavoratori a\n" +
                "        on i.Id_Lavoratore = a.ID\n" +
                "        where a.CodiceFiscale = \"%s\"", codiceFisclae);

        return query;

    }
    private String createQueryForIscrizioniDbNazionale(String nomeProvincia, String nomeEnte){



//        select
//        t.CodiceFiscale,
//                a.ID as idWorker,
//        i.anno, i.NomeProvincia, i.NomeRegione, i.Settore, i.Ente, i.Azienda, i.Piva,
//                i.Livello, i.Quota, i.Periodo, i.Contratto
//        from
//        lavoratori_liberi t
//        inner join
//        lavoratori a
//        on t.CodiceFiscale = a.CodiceFiscale
//        inner join
//        iscrizioni i
//        on a.ID = i.Id_Lavoratore
//        where t.NomeProvinciaFeneal = 'NAPOLI' and t.ente = 'CASSA EDILE';





        String query =  String.format("select" +
                        "        t.CodiceFiscale," +
                        "        a.ID as idWorker," +
                        "        i.anno, " +
                        "        i.NomeProvincia, " +
                        "        i.NomeRegione, " +
                        "        i.Settore, " +
                        "        i.Ente, " +
                        "        i.Azienda, " +
                        "        i.Piva," +
                        "        i.Livello, " +
                        "        i.Quota, " +
                        "        i.Periodo, " +
                        "        i.Contratto" +
                        "        from" +
                        "        lavoratori_liberi t" +
                        "        inner join" +
                        "        lavoratori a" +
                        "        on t.CodiceFiscale = a.CodiceFiscale" +
                        "        inner join" +
                        "        iscrizioni i" +
                        "        on a.ID = i.Id_Lavoratore " +
                        " where t.NomeProvinciaFeneal = '%s' and t.ente = '%s' ",
                nomeProvincia.replace("'","''"), nomeEnte);

        return query;

    }
    private SQLQuery createHibernateQueryForIscrizioniDbNazionale(Session session, String query){
        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("idWorker")
                .addScalar("anno")
                .addScalar("NomeProvincia")
                .addScalar("NomeRegione")
                .addScalar("Settore")
                .addScalar("Ente")
                .addScalar("Azienda")
                .addScalar("Piva")
                .addScalar("Livello")
                .addScalar("Quota")
                .addScalar("Periodo")
                .addScalar("Contratto");
    }
    private Iscrizione materializeIscrizioniDbNazionle(Object[] object){
        Iscrizione v = new Iscrizione();
        v.setCodiceFiscale((String)object[0]);
       v.setAnno((Integer)object[2]);
       v.setNomeProvincia((String) object[3]);
        v.setNomeRegione((String) object[4]);
        v.setSettore((String) object[5]);
        v.setEnte((String) object[6]);
        v.setAzienda((String) object[7]);
        v.setPiva((String) object[8]);
        v.setLivello((String) object[9]);
        v.setQuota((Double) object[10]);
        v.setPeriodo((Integer) object[11]);
        v.setContratto((String) object[12]);
        return v;
    }



    private String createQueryForLiberi(String nomeProvincia, String nomeEnte){



//        select
//        t.CodiceFiscale,t.CurrentAzienda,t.IscrittoA,t.LiberoAl,t.Nome,t.Cognome,t.DataNascita,
//                t.NomeNazione,t.NomeProvinciaResidenza,t.NomeComuneResidenza,t.Telefono,a.ID  as idWorker,
//        a.Telefono as telefonoAnagrafica, t.Indirizzo, t.Cap
//        from
//        lavoratori_liberi t
//        left join
//        lavoratori a
//        on t.CodiceFiscale = a.CodiceFiscale
//        where NomeProvinciaFeneal = 'NAPOLI' and t.ente = 'CASSA EDILE';





        String query =  String.format("select " +
                "t.CodiceFiscale," +
                "t.CurrentAzienda," +
                "t.IscrittoA," +
                "t.LiberoAl," +
                "t.Nome," +
                "t.Cognome," +
                "t.DataNascita," +
                "t.NomeNazione," +
                "t.NomeProvinciaResidenza," +
                "t.NomeComuneResidenza," +
                "t.Telefono," +
                "a.ID as idWorker , t.Indirizzo, t.Cap, a.Telefono as telefonodbnazionale from lavoratori_liberi t left join lavoratori a on t.CodiceFiscale = a.CodiceFiscale where NomeProvinciaFeneal = '%s' and ente = '%s' ",
                nomeProvincia.replace("'","''"), nomeEnte);

        return query;

    }
    private SQLQuery createHibernateQueryForLibery(Session session, String query){
        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("CurrentAzienda")
                .addScalar("IscrittoA")
                .addScalar("LiberoAl")
                .addScalar("Nome")
                .addScalar("Cognome")
                .addScalar("DataNascita")
                .addScalar("NomeNazione")
                .addScalar("NomeProvinciaResidenza")
                .addScalar("NomeComuneResidenza")
                .addScalar("Telefono")
                .addScalar("idWorker")
                .addScalar("Indirizzo")
                .addScalar("Cap")
                .addScalar("telefonodbnazionale");
    }
    private LiberoDbNazionale materializeLiberi(Object[] object, String ente, String provincia){
        LiberoDbNazionale v = new LiberoDbNazionale();

        v.setCodiceFiscale((String)object[0]);
        v.setCurrentAzienda((String)object[1]);
        v.setIscrittoA(String.valueOf(object[2]));
        v.setNomeProvinciaFeneal(provincia);
        v.setLiberoAl((Date)object[3]);
        v.setEnte(ente);
        v.setNome(String.valueOf(object[4]));
        v.setCognome(String.valueOf(object[5]));
        v.setNomeCompleto(v.getNome() + " " + v.getCognome());
        v.setDataNascita((Date)object[6]);
        v.setSesso("MASCHIO");
        v.setNomeNazione((String)object[7]);
        v.setNomeProvinciaResidenza((String)object[8]);
        v.setNomeComuneResidenza((String)object[9]);

        String telefono = (String)object[10];
        if (StringUtils.isEmpty(telefono) || telefono.trim().equals("-"))
            telefono = (String)object[14];

        v.setTelefono(telefono);
        v.setIdWorker((Integer)object[11]);
        v.setIndirizzo((String)object[12]);
        v.setCap((String)object[13]);

        return v;
    }


}
