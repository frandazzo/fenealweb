package applica.feneal.services.impl.report;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.aziende.NonIscrittiAzienda;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerEnte;
import applica.feneal.domain.model.core.report.RiepilogoIscrittiPerSettore;
import applica.feneal.domain.model.core.report.RiepilogoIscrizione;
import applica.feneal.domain.model.core.report.Sindacalizzaizone;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.StatisticService;
import applica.feneal.services.utils.StatisticsUtils;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import it.fenealgestweb.www.FenealgestStatsStub;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 01/05/2016.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private ParitheticRepository par;

    @Autowired
    private Security sec;

    @Autowired
    private StatisticsUtils utils;

    @Autowired
    private OptionsManager opt;


    @Override
    public Sindacalizzaizone getSindacalizzazione(Long loggedUserId, String territorio, String ente) {

        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(territorio) || StringUtils.isEmpty(ente)){
            //per il terriotrio il paramtro di default è la prima provincia
            //mentre  per l'ente è la cassa edile
            territorio = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);
            ente = Paritethic.ente_cassaedile;
        }


        //modifica richiesta da maurizio d'aurelio
        //se ci sono le previzionali recuprare l'ultima previsionale inserita....
        //e gli iscritti prenderli da li
        int numUltimiIscrittiDbNazionale =  0;
        numUltimiIscrittiDbNazionale = utils.numIscrittiUltimaIQP(territorio, ente);
        if (numUltimiIscrittiDbNazionale == 0)
            numUltimiIscrittiDbNazionale = utils.numUltimiIscrittiDbNazionale(territorio, ente);


        int numNonIscrittiFilca =  utils.numNonIscritti(territorio, ente, "FILCA");
        int numNonIscrittiFILLEA =  utils.numNonIscritti(territorio, ente, "FILLEA");
        int numNonIscritti =  utils.numNonIscritti(territorio, ente, "");

        Sindacalizzaizone s = new Sindacalizzaizone();
        s.setIscrittiFeneal(numUltimiIscrittiDbNazionale);
        s.setIscrittiFilca(numNonIscrittiFilca);
        s.setIscrittiFillea(numNonIscrittiFILLEA);
        s.setLiberi(numNonIscritti);

        s.setTassoSindacalizzazione(utils.calculateTassoSindacalizzaizone(numUltimiIscrittiDbNazionale,numNonIscrittiFilca, numNonIscrittiFILLEA, numNonIscritti));


        s.setProbvincia(territorio);
        s.setEnte(ente);
        return s;
    }




    @Override
    public List<RiepilogoIscrizione> getAndamentoIscrittiPerTerritorioAccorpato(Long loggedUserId) {

        List<RiepilogoIscrizione> riepilogoIscriziones = new ArrayList<>();

        //recupero la lista degli anni in cui ci sono iscrizioni insieme con la lista dei terrirtori
        List<String> territori = getListaTerritori(((User) sec.getLoggedUser()).getLid());
        List<Integer> anni = utils.getListaAnniImportazioniPerTerritorio(territori);

        //ora posso ciclare su tutti gli anni e recuperare gli iscritti per ogni territorio
        for (Integer anno : anni) {
            for (String territorio : territori) {
                RiepilogoIscrizione r = new RiepilogoIscrizione();

                r.setAnno(anno);

                r.setNumIscritti(utils.getNumIscrittiTuttiISettoriPerAnnoETerritorio(anno, territorio));

                r.setTerritorio(territorio);

                riepilogoIscriziones.add(r);
            }
        }

        return riepilogoIscriziones;

    }

    @Override
    public List<RiepilogoIscrizione> getContatoreIscrittiPerTerritorioAccorpato(Long loggedUserId, Integer anno) {

        if (anno  == null)
            anno = Calendar.getInstance().get(Calendar.YEAR);

        List<RiepilogoIscrizione> riepilogoIscriziones = new ArrayList<>();

        //recupero la lista degli anni in cui ci sono iscrizioni insieme con la lista dei terrirtori
        List<String> territori = getListaTerritori(((User) sec.getLoggedUser()).getLid());

        //ora posso ciclare su tutti gli anni e recuperare gli iscritti per ogni territorio

            for (String territorio : territori) {
                RiepilogoIscrizione r = new RiepilogoIscrizione();

                r.setAnno(anno);

                r.setNumIscritti(utils.getNumIscrittiTuttiISettoriPerAnnoETerritorio(anno, territorio));

                r.setTerritorio(territorio);

                riepilogoIscriziones.add(r);
            }


        return riepilogoIscriziones;




    }

    @Override
    public List<RiepilogoIscrizione> getAndamentoIscrittiPerSettoreEdile(Long loggedUserId, String territorio) {

        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(territorio)){
            //per il terriotrio il paramtro di default è la prima provincia
            //mentre  per l'ente è la cassa edile
            territorio = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);

        }




        List<RiepilogoIscrizione> riepilogoIscriziones = new ArrayList<>();

        //recupero la lista degli anni in cui ci sono iscrizioni insieme con la lista dei terrirtori

        List<Integer> anni = utils.getListaAnniImportazioniPerTerritorio(territorio, "EDILE");
        List<String> enti = utils.getListaEntiForTerritorio(territorio);
        //ora posso ciclare su tutti gli anni e recuperare gli iscritti per ogni territorio
        for (Integer anno : anni) {
            for (String ente : enti) {
                RiepilogoIscrizione r = new RiepilogoIscrizione();

                r.setAnno(anno);
                r.setSettore("EDILE");
                r.setEnte(ente);
                r.setNumIscritti(utils.getNumIscrittiPerEntePerAnnoETerritorio(anno, territorio,ente));
                r.setTerritorio(territorio);

                riepilogoIscriziones.add(r);
            }
        }



        return riepilogoIscriziones;

    }

    @Override
    public List<RiepilogoIscrittiPerEnte> getContatoreIscrittiPerSettoreEdile(Long loggedUserId, Integer anno, String territorio) {

        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(territorio)){
            //per il terriotrio il paramtro di default è la prima provincia
            //mentre  per l'ente è la cassa edile
            territorio = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);
            anno = Calendar.getInstance().get(Calendar.YEAR);

        }




        List<RiepilogoIscrittiPerEnte> riepilogoIscriziones = new ArrayList<>();

        //recupero la lista degli anni in cui ci sono iscrizioni insieme con la lista dei terrirtori


        List<String> enti = utils.getListaEntiForTerritorio(territorio);
        //ora posso ciclare su tutti gli anni e recuperare gli iscritti per ogni territorio

            for (String ente : enti) {
                RiepilogoIscrittiPerEnte r = new RiepilogoIscrittiPerEnte();


                r.setEnte(ente);
                r.setNumIscritti(utils.getNumIscrittiPerEntePerAnnoETerritorio(anno, territorio,ente));


                riepilogoIscriziones.add(r);
            }




        return riepilogoIscriziones;

    }

    @Override
    public List<RiepilogoIscrizione> getAndamentoIscrittiPerProvincia(Long loggedUserId, String territorio) {

        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(territorio)){
            //per il terriotrio il paramtro di default è la prima provincia

            territorio = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);

        }



        List<RiepilogoIscrizione> riepilogoIscriziones = new ArrayList<>();


        List<Integer> anni = utils.getListaAnniImportazioniPerTerritorio(territorio, "");

        //per ogni anno recupero gli iscritti in gtutti i settori
        for (Integer anno : anni) {

            RiepilogoIscrizione r = new RiepilogoIscrizione();

            r.setAnno(anno);
            r.setSettore("EDILE");
            r.setNumIscritti(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio, "EDILE"));
            r.setTerritorio(territorio);

            riepilogoIscriziones.add(r);


            RiepilogoIscrizione r1 = new RiepilogoIscrizione();

            r1.setAnno(anno);
            r1.setSettore("IMPIANTI FISSI");
            r1.setNumIscritti(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio, "IMPIANTI FISSI"));
            r1.setTerritorio(territorio);

            riepilogoIscriziones.add(r1);

            RiepilogoIscrizione r11 = new RiepilogoIscrizione();

            r11.setAnno(anno);
            r11.setSettore("INPS");
            r11.setNumIscritti(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio, "INPS"));
            r11.setTerritorio(territorio);

            riepilogoIscriziones.add(r11);

        }


        return riepilogoIscriziones;

    }

    @Override
    public RiepilogoIscrittiPerSettore getContatoreIscritti(Long loggedUserId, Integer anno, String territorio) {


        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(territorio)){
            //per il terriotrio il paramtro di default è la prima provincia

            territorio = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);
            anno = Calendar.getInstance().get(Calendar.YEAR);
        }

        RiepilogoIscrittiPerSettore r = new RiepilogoIscrittiPerSettore();

        r.setIscrittiEdili(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio,"EDILE"));
        r.setIscrittiImpiantiFissi(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio,"IMPIANTI FISSI"));
        r.setIscrittiInps(utils.getNumIscrittiPerSettoriPerAnnoETerritorio(anno, territorio,"INPS"));

//        r.setPercIscrittiEdili(20);
//        r.setPercIscrittiImpiantiFissi(30);
//        r.setPercIscrittiInps(50);

        return r;

    }

    @Override
    public List<String> getListaTerritori(Long loggedUserId) {
        User u = ((User) sec.getLoggedUser());

        return u.getCompany().getProvinces().stream().map(province -> province.getDescription()).collect(Collectors.toList());
    }

    @Override
    public List<String> getListaEnti() {
        List<Paritethic> p = par.find(null).getRows();

        return p.stream().map(paritethic -> paritethic.getType()).collect(Collectors.toList());
    }

    @Override
    public List<NonIscrittiAzienda> getClassificaNonIscritti( String province,  String ente) {
        //se uno dei due paramteri è nullo imposto i paramteri di default
        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(ente)){
            //per il terriotrio il paramtro di default è la prima provincia
            //mentre  per l'ente è la cassa edile
            province = getListaTerritori(((User) sec.getLoggedUser()).getLid()).get(0);
            ente = Paritethic.ente_cassaedile;
        }


        final String province1 = province;
        final String ente1 = ente;

        final Box box = new Box();

        par.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = par.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();

                    String query = createQuery(province1, ente1);
                    List<Object[]> objects = s.createSQLQuery(query)
                            //.addScalar("ID")
                            .addScalar("CurrentAzienda")
                            .addScalar("liberi")
                            .addScalar("filca")
                            .addScalar("fillea")

                            .list();

                    tx.commit();

                    List<NonIscrittiAzienda> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        NonIscrittiAzienda v = new NonIscrittiAzienda();

                        v.setAzienda((String)object[0]);
                        v.setLiberi(Integer.parseInt(object[1].toString()));
                        v.setFilca(Integer.parseInt(object[2].toString()));
                        v.setFillea(Integer.parseInt(object[3].toString()));

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

            private String createQuery(String province, String ente) {

                String query =  String.format("SELECT CurrentAzienda, " +
                        "        SUM(case IscrittoA when '' then 1 else 0 end) liberi, " +
                                "        SUM(case IscrittoA when 'filca' then 1 else 0 end) filca, " +
                                "        SUM(case IscrittoA when 'fillea' then 1 else 0 end) fillea " +
                                "FROM lavoratori_liberi where NomeProvinciaFeneal = '%s' and ente = '%s' " +
                                "GROUP BY CurrentAzienda  ", province
                        .replace("'","''"), ente);

                return query;

            }
        });



        List<NonIscrittiAzienda> libs = (List<NonIscrittiAzienda>)box.getValue();


        return libs;

    }

    @Override
    public List<String> statsGetDataExportIscritti(String provinceName) throws IOException {
        it.fenealgestweb.www.FenealgestStatsStub svc = null;
        try {
            String service = opt.get("applica.fenealgeststats");
            svc =  new FenealgestStatsStub(service);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        it.fenealgestweb.www.FenealgestStatsStub.FindDataExportsResponse result = null;
        it.fenealgestweb.www.FenealgestStatsStub.FindDataExports method = new it.fenealgestweb.www.FenealgestStatsStub.FindDataExports();
        method.setProvince(provinceName);
        result = svc.findDataExports(method);

        List<String> dataExportResultFilenames = new ArrayList<>();
        FenealgestStatsStub.DataExport[] dataExports = result.getFindDataExportsResult().getDataExport();
        for (FenealgestStatsStub.DataExport dataExport : dataExports) {
            dataExportResultFilenames.add(dataExport.getFileName());
        }

        return dataExportResultFilenames;
    }

    @Override
    public FenealgestStatsStub.DataExportResult statsGetStatisticsResult(String province, String filenames) throws IOException {
        it.fenealgestweb.www.FenealgestStatsStub svc = null;
        try {
            String service = opt.get("applica.fenealgeststats");
            svc =  new FenealgestStatsStub(service);
            //svc =  new FenealgestStatsStub("http://www.fenealgest.it/servizi/WebServices/FenealgestStats.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        it.fenealgestweb.www.FenealgestStatsStub.CalculateStatisticsResponse result = null;
        it.fenealgestweb.www.FenealgestStatsStub.CalculateStatistics method = new it.fenealgestweb.www.FenealgestStatsStub.CalculateStatistics();
        method.setProvince(province);
        method.setFilenames(filenames);
        result = svc.calculateStatistics(method);

        return result.getCalculateStatisticsResult();
    }

}
