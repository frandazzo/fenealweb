package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleSecondaryRepository;
import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.data.geo.CountriesRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.ReportNonIscrittiSuper;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportNonIscrittiSuperServiceImpl implements ReportNonIscrittiSuper {



    @Autowired
    private ProvinceRepository proRep;



    @Autowired
    private ParitheticRepository enteRep;







    @Override
    public List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params) {
        return null;
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
                        " where NomeProvinciaFeneal = '%s' and ente = '%s' ",
                idREgione,  nomeProvincia.replace("'","''"), nomeEnte);

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
                " where NomeProvinciaFeneal = '%s' and ente = '%s' ",
                idREgione, nomeProvincia.replace("'","''"), nomeProvincia.replace("'","''"), nomeEnte);

        return query;


    }
    private String createQueryForDeleghe(String nomeProvincia, String nomeEnte){

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
                        "d.nomeattachment\n" +
                        "from \n" +
                        "lavoratori_liberi t \n" +
                        "inner join \n" +
                        "fenealweb_lavoratore a \n" +
                        "on t.CodiceFiscale = a.fiscalcode\n" +
                        "inner join fenealweb_delega d \n" +
                        "on d.workerId = a.id\n" +
                        "left join fenealweb_collaboratore c\n" +
                        "on c.id = d.collaboratorId " +
                        " where NomeProvinciaFeneal = '%s' and ente = '%s' ",
                nomeProvincia.replace("'","''"), nomeEnte);

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
                .addScalar("nomeattachment");
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
                        " where NomeProvinciaFeneal = '%s' and ente = '%s' ",
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
        if (StringUtils.isEmpty(telefono))
            telefono = (String)object[14];

        v.setTelefono(telefono);
        v.setIdWorker((Integer)object[11]);
        v.setIndirizzo((String)object[12]);
        v.setCap((String)object[13]);

        return v;
    }


}
