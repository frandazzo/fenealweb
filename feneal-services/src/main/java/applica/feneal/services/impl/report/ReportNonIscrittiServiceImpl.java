package applica.feneal.services.impl.report;

import applica.feneal.domain.data.Command;
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
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionaleSecondary;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.ReportNonIscrittiService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
@Service
public class ReportNonIscrittiServiceImpl implements ReportNonIscrittiService {

    @Autowired
    private LiberoDbNazionaleRepository libRep;

    @Autowired
    private LiberoDbNazionaleSecondaryRepository libSecRep;

    @Autowired
    private ProvinceRepository proRep;

    @Autowired
    private CountriesRepository couRep;

    @Autowired
    private CitiesRepository citRep;

    @Autowired
    private IscrizioniRepository isRep;


    @Autowired
    private ParitheticRepository enteRep;

    @Autowired
    private DelegheRepository delRep;

    @Autowired
    private Security sec;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private LavoratoriRepository lavRep;



    @Override
    public List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params) {

        String province = params.getProvince();
        if (StringUtils.isEmpty(province))
            throw new RuntimeException("Provincia nulla per report non iscritti");





        //posso adesso fare la querty
        LoadRequest req = LoadRequest.build();
        Integer prId = Integer.parseInt(province);
        final Province p = proRep.get(prId).orElse(null);

        Paritethic t = enteRep.get(Long.parseLong(params.getParithetic())).orElse(null);


        final Box box = new Box();

        isRep.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = isRep.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();

                    String query = createQuery(params);
                    List<Object[]> objects = s.createSQLQuery(query)
                            //.addScalar("ID")
                            .addScalar("CodiceFiscale")
                            .addScalar("CurrentAzienda")
                            .addScalar("IscrittoA")
                            //.addScalar("NomeProvinciaFeneal")

                            //.addScalar("Id_ProvinciaFeneal")
                            .addScalar("LiberoAl")
                            //.addScalar("Ente")
                            .addScalar("Nome")
                            .addScalar("Cognome")

                            //.addScalar("NomeCompleto")
                            .addScalar("DataNascita")
                            //.addScalar("Sesso")
                            //.addScalar("Id_Nazione")
                            .addScalar("NomeNazione")

                            //.addScalar("Id_Provincia")
                            //.addScalar("NomeProvincia")
                            //.addScalar("Id_Comune")
                            //.addScalar("NomeComune")
                            //.addScalar("Id_Provincia_Residenza")



                            .addScalar("NomeProvinciaResidenza")
                            //.addScalar("Id_Comune_Residenza")
                            .addScalar("NomeComuneResidenza")
                            //.addScalar("Indirizzo")
                            //.addScalar("Cap")

                            .addScalar("Telefono")
                            //.addScalar("UltimaModifica")
                            //.addScalar("UltimaProvinciaAdAggiornare")
                            .addScalar("idWorker")
                            .list();

                    tx.commit();

                    List<LiberoDbNazionale> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        LiberoDbNazionale v = new LiberoDbNazionale();

                        v.setCodiceFiscale((String)object[0]);
                        v.setCurrentAzienda((String)object[1]);
                        v.setIscrittoA(String.valueOf(object[2]));
                        v.setNomeProvinciaFeneal(p.getDescription());
                        v.setLiberoAl((Date)object[3]);
                        v.setEnte(t.getType());
                        v.setNome(String.valueOf(object[4]));
                        v.setCognome(String.valueOf(object[5]));
                        v.setNomeCompleto(v.getNome() + " " + v.getCognome());
                        v.setDataNascita((Date)object[6]);
                        v.setSesso("MASCHIO");
                        v.setNomeNazione((String)object[7]);
                        v.setNomeProvinciaResidenza((String)object[8]);
                        v.setNomeComuneResidenza((String)object[9]);
                        v.setTelefono((String)object[10]);
                        v.setIdWorker((Integer)object[11]);
                        a.add(v);
                    }


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

            private String createQuery(LiberoReportSearchParams params) {

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
                        "a.ID as idWorker from lavoratori_liberi t left join lavoratori a on t.CodiceFiscale = a.CodiceFiscale where NomeProvinciaFeneal = '%s' and ente = '%s' ", p.getDescription()
                        .replace("'","''"), t.getType());

                String iscrittoA = params.getSignedTo();
                String nationality = params.getNationality();
                String livingProvince = params.getLivingProvince();
                String livingCity = params.getLivingCity();
                String firm = params.getFirm();

                if (!StringUtils.isEmpty(firm)){
                    Long firmId = Long.parseLong(firm);

                    Azienda p1 = azRep.get(firmId).orElse(null);
                    query =  query + " and t.CurrentAzienda = '" + p1.getDescription().replace("'","''") + "' ";

                }

                if (!StringUtils.isEmpty(iscrittoA)){
                    if (iscrittoA.toLowerCase().contains("codici")) {
                        query = query + " and t.iscrittoA = '' ";

                    }else{
                        query =  query + " and t.iscrittoA = '" + iscrittoA.toUpperCase() + "' ";
                    }
                }

                if (!StringUtils.isEmpty(nationality)){
                    Integer natId = Integer.parseInt(nationality);

                    Country p1 = couRep.get(natId).orElse(null);
                    query =  query + " and t.nomeNazione = '" + p1.getDescription().replace("'","''") + "' ";

                }

                if (!StringUtils.isEmpty(livingProvince)){
                    Integer proId = Integer.parseInt(livingProvince);
                    Province p2 = proRep.get(proId).orElse(null);
                    query =  query + " and t.nomeProvinciaResidenza = '" + p2.getDescription().replace("'","''") + "' ";

                }

                if (!StringUtils.isEmpty(livingCity)){
                    Integer comId = Integer.parseInt(livingCity);
                    City p3 = citRep.get(comId).orElse(null);
                    query =  query + " and t.nomeComuneResidenza = '" + p3.getDescription().replace("'","''") + "' ";


                }

                return query;

            }
        });



        List<LiberoDbNazionale> libs = (List<LiberoDbNazionale>)box.getValue();

        //una volta ottenuti tutti gli utenti posso per ognuno calcolare le iscirzioni
        for (LiberoDbNazionale lib : libs) {
            if (lib.getIdWorker() != null){
                lib.setIscrizioni(isRep.findIscrizioniByWorkerId(lib.getIdWorker()));
            }

        }
//

        if (!StringUtils.isEmpty(params.getFirm())){
            for (LiberoDbNazionale lib : libs) {
                //cerco il numeor di deleghe per ogni libero
                lib.setDelegheOwner(delRep.hasLavoratoreSomeDelegaForCompany(((User) sec.getLoggedUser()).getCompany().getLid(), lib.getCodiceFiscale()));
            }
        }


        return libs;


    }

    @Override
    public List<LiberoDbNazionale> retrieveLiberiForWorker(long id) {
        Lavoratore l = lavRep.get(id).orElse(null);
        if (l == null)
            return new ArrayList<>();

        LoadRequest req = LoadRequest.build().filter("codiceFiscale", l.getFiscalcode());
        List<LiberoDbNazionale> res = libRep.find(req).getRows();

        List<LiberoDbNazionale> result = new ArrayList<>();
        for (LiberoDbNazionale re : res) {
            if (((User) sec.getLoggedUser()).getCompany().containProvince(re.getNomeProvinciaFeneal()))
                result.add(re);
        }



        LoadRequest req1 = LoadRequest.build().filter("codiceFiscale", l.getFiscalcode());
        List<LiberoDbNazionaleSecondary> res1 = libSecRep.find(req1).getRows();


        for (LiberoDbNazionaleSecondary re : res1) {
            if (((User) sec.getLoggedUser()).getCompany().containProvince(re.getNomeProvinciaFeneal()))
                result.add(re.creteLiberoDBNazionale());
        }


        return result;
    }
}
