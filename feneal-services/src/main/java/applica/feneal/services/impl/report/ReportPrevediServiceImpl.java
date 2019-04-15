package applica.feneal.services.impl.report;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.dbnazionale.LavoratorePrevediRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.feneal.domain.model.dbnazionale.search.PrevediReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.ReportPrevediService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportPrevediServiceImpl implements ReportPrevediService {

    @Autowired
    private ProvinceRepository proRep;

    @Autowired
    private RegonsRepository regRep;

    @Autowired
    private LavoratorePrevediRepository preRep;

    @Override
    public List<Integer> getAnniPrevedi() {
        return Arrays.asList(2018);
    }

    @Override
    public List<LavoratorePrevedi> retrieveLavoratoriPrevedi(PrevediReportSearchParams params) {
        final Province p = proRep.get(Integer.parseInt(params.getProvince())).orElse(null);
        final Region  rr = regRep.get(p.getIdRegion()).get();

        int anno = GregorianCalendar.getInstance().get(Calendar.YEAR);
        if (!StringUtils.isEmpty(params.getAnno()))
        {
            try{
                anno = Integer.parseInt(params.getAnno());
            }catch (Exception e){

            }
        }


        final String queryLiberi = createQueryForIscrittiPrevedi(anno,p.getDescription(), rr.getDescription());
        final String queryIscrizioniDbNazionale = createQueryForIscrizioniDbNazionale(anno,p.getDescription(), rr.getDescription());






        final Box box = new Box();

        preRep.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = preRep.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();




                    List<Object[]> isscrizioni = createHibernateQueryForIscrizioniDbNazionale(s,queryIscrizioniDbNazionale).list();
                    List<Object[]> liberi = createHibernateQueryForIscrittiPrevedi(s,queryLiberi).list();

                    tx.commit();



                    //metto i dati di iscrizioni deleghe e iscrizioni altro indacato
                    // in una hashtable per codice fiscale
                    //in modo da poterle recuperare immediatamente

                    //materializoz le icrizoini
                    Hashtable<String, List<Iscrizione>> i = mateiralizeHashIscrizioni(isscrizioni);

                    List<LavoratorePrevedi> a = new ArrayList<>();
                    for (Object[] objects : liberi) {
                        LavoratorePrevedi ss = materializeIscrittiPrevedi(objects);
                        materialiazeObjectContents(i,  ss);
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

        return (List<LavoratorePrevedi>)box.getValue();

    }


    private void materialiazeObjectContents(Hashtable<String, List<Iscrizione>> i,  LavoratorePrevedi ss) {
        List<Iscrizione> sd = i.get(ss.getFiscalcode());


        if (sd == null)
            sd = new ArrayList<>();



        ss.setIscrizioni(sd);

    }
    private Hashtable<String, List<Iscrizione>> mateiralizeHashIscrizioni(List<Object[]> isscrizioni) throws Exception {
        int j = 0;
        try{

            Hashtable<String,List<Iscrizione>> i = new Hashtable<>();
            for (Object[] objects : isscrizioni) {
                j++;
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
        }catch(Exception e){
            throw new Exception("il num "+ j);
        }


    }


    private String createQueryForIscrittiPrevedi(int anno, String nomeProvincia, String nomeRegione){

//        select a.cassaEdile, a.cassaEdileRegione, a.inquadramento, a.tipoAdesione, a.anno,
//                a.name, a.surname, a.fiscalcode, a.birthDate, a.livingProvince, a.livingCity,
//                a.address, a.cap
//
//        from fenealweb_lavoratoriprevedi a where anno = 2018 and
//                (MATCH (a.cassaEdile)
//                        AGAINST ('milano lombardia' IN NATURAL LANGUAGE MODE)
//                        or a.livingProvince = "milano");




        String query =  String.format("select a.fiscalcode as CodiceFiscale, a.cassaEdile, a.cassaEdileRegione, a.inquadramento, a.tipoAdesione, a.anno,\n" +
                        "a.name, a.surname,  a.birthDate, a.livingProvince, " +
                        "a.livingCity,\n" +
                        "                a.address, a.cap\n" +
                        "\n" +
                        " from fenealweb_lavoratoriprevedi a where anno = %s and \n" +
                        "(MATCH (a.cassaEdile)\n" +
                        "    AGAINST ('%s %s' IN NATURAL LANGUAGE MODE)\n" +
                        " or a.livingProvince = \"%s\");",
                anno,
                nomeProvincia.replace("'","''"),
                nomeRegione.replace("'","''"),
                nomeProvincia.replace("'","''"));

        return query;

    }
    private SQLQuery createHibernateQueryForIscrittiPrevedi(Session session, String query){

        return session.createSQLQuery(query)
                .addScalar("CodiceFiscale")
                .addScalar("cassaEdile")
                .addScalar("cassaEdileRegione")
                .addScalar("inquadramento")
                .addScalar("tipoAdesione")
                .addScalar("anno")
                .addScalar("name")
                .addScalar("surname")
                .addScalar("birthDate")
                .addScalar("livingProvince")
                .addScalar("livingCity")
                .addScalar("address")
                .addScalar("cap");
    }
    private LavoratorePrevedi materializeIscrittiPrevedi(Object[] object){
        LavoratorePrevedi v = new LavoratorePrevedi();
        v.setFiscalcode((String)object[0]);
        v.setCassaEdile((String)object[1]);
        v.setCassaEdileRegione((String) object[2]);
        v.setInquadramento((String) object[3]);
        v.setTipoAdesione((String) object[4]);
        v.setAnno((Integer) object[5]);

        v.setName((String)object[6]);
        v.setSurname((String)object[7]);
        v.setBirthDate((Date)object[8]);
        v.setLivingProvince((String)object[9]);
        v.setLivingCity((String)object[10]);
        v.setAddress((String)object[11]);
        v.setCap((String)object[12]);

        return v;


    }


    private String createQueryForIscrizioniDbNazionale(int anno, String nomeProvincia, String nomeRegione){


//        select  p.fiscalcode as CodiceFiscale, i.Id_Lavoratore as idWorker, i.anno, i.NomeProvincia, i.NomeRegione, i.Settore, i.Ente, i.Azienda, i.Piva,
//                i.Livello, i.Quota, i.Periodo, i.Contratto
//        from fenealweb_lavoratoriprevedi p
//        inner join lavoratori l
//        on p.fiscalcode =  l.CodiceFiscale
//        left join iscrizioni i
//        on l.ID = i.Id_Lavoratore
//        where p.anno = 2018 and
//                (MATCH (p.cassaEdile)
//                        AGAINST ('roma lazio' IN NATURAL LANGUAGE MODE) or p.livingProvince = "Roma");



        String query =  String.format("select  p.fiscalcode as CodiceFiscale, i.Id_Lavoratore as idWorker, i.anno, i.NomeProvincia, i.NomeRegione, i.Settore, i.Ente, i.Azienda, i.Piva,\n" +
                        "               i.Livello, i.Quota, i.Periodo, i.Contratto \n" +
                        "               from fenealweb_lavoratoriprevedi p \n" +
                        "inner join lavoratori l \n" +
                        "on p.fiscalcode =  l.CodiceFiscale \n" +
                        "left join iscrizioni i\n" +
                        "on l.ID = i.Id_Lavoratore\n" +
                        "where p.anno = %s and \n" +
                        "(MATCH (p.cassaEdile)\n" +
                        "    AGAINST ('%s %s' IN NATURAL LANGUAGE MODE) or p.livingProvince = \"%s\");",
                anno,
                nomeProvincia.replace("'","''"),
                nomeRegione.replace("'","''"),
                nomeProvincia.replace("'","''"));

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



}
