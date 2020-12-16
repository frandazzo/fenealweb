package applica.feneal.services.impl.report;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocioNoniscritti;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioNonIscrittiSearchParams;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioSearchParams;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.GeoService;
import applica.feneal.services.ReportIncrocioService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportIncrocioServiceImpl implements ReportIncrocioService {
    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;

    @Autowired
    private GeoService geo;

    @Override
    public List<LavoratoreIncrocio> retrieveIncrocioProvinciaResidenza(ReportIncrocioSearchParams params) {
        final Box b = new Box();

        dettRep.executeCommand(new Command() {

            @Override
            public void execute() {

                String settore = "";
                if (!StringUtils.isEmpty(params.getSector())){
                    settore = "EDILE";
                }

                if(StringUtils.isEmpty(params.getData())){
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.MONTH, -2);
                    params.setData(c.getTime().toString());
                }


                String provincia = "";
                if (!StringUtils.isEmpty(params.getProvince())){
                    provincia = geo.getProvinceById(Integer.parseInt(params.getProvince())).getDescription();
                }


                Session s = dettRep.getSession();
                Transaction tx = s.beginTransaction();


                String whereclause = "";
                if (StringUtils.isEmpty(settore) && StringUtils.isEmpty(provincia)){
                    whereclause = "";
                }else if (!StringUtils.isEmpty(settore) && !StringUtils.isEmpty(provincia)){
                    whereclause = " i.Settore = '" + settore +"'  and l.NomeProvinciaResidenza = '"+ provincia + "' ";
                }else if (!StringUtils.isEmpty(settore) && StringUtils.isEmpty(provincia)){
                    whereclause = " i.Settore = '" + settore +"'" ;
                }else{
                    whereclause = " l.NomeProvinciaResidenza = "+ provincia + " ";
                }

                whereclause = whereclause + "and i.anno='" + params.getDatefromYearReport() + "' and DataEsportazione > '" + params.getData()+"'";

                if(params.getIncludeIscrittiProvincia().equals("0") && !StringUtils.isEmpty(provincia)){
                    whereclause = whereclause + "and i.NomeProvincia <> '"+ provincia+"'";
                }

                try {
                    //rimuovo tutti i dettagli per una determinata quota
                    String query = "select l.CodiceFiscale, l.Nome, l.Cognome,\n" +
                            "l.DataNascita, l.NomeComuneResidenza, l.NomeProvinciaResidenza, l.Indirizzo,\n" +
                            "l.Cap, l.Telefono, i.NomeProvincia as TerritorioFeneal, i.NomeRegione as Regioneterritorio,\n" +
                            "i.Settore, i.Ente, i.Azienda, i.Anno, i.Periodo , im.DataEsportazione\n" +
                            "from feneal.lavoratori l \n" +
                            "inner join feneal.iscrizioni i on l.id = i.id_lavoratore \n" +
                            "inner join feneal.importazioni im on im.ID = i.Id_Importazione\n" +
                            "where " + whereclause;


                    SQLQuery q = s.createSQLQuery(query);
                    q.setResultTransformer(Transformers.aliasToBean(LavoratoreIncrocio.class));


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

        return (List<LavoratoreIncrocio>)b.getValue();
    }

    @Override
    public List<LavoratoreIncrocioNoniscritti> retrieveIncrocioNoniscritti(ReportIncrocioNonIscrittiSearchParams params) {
        final Box b = new Box();

        dettRep.executeCommand(new Command() {

            @Override
            public void execute() {
                if(StringUtils.isEmpty(params.getData())){
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.MONTH, -2);
                    params.setData(c.getTime().toString());
                }


                String provincia = "";
                if (!StringUtils.isEmpty(params.getProvince())){
                    provincia = geo.getProvinceByName(params.getProvince()).getDescription();
                }


                Session s = dettRep.getSession();
                Transaction tx = s.beginTransaction();


                String whereclause = " l.NomeProvinciaResidenza = '"+ provincia + "' and NomeProvinciaFeneal <> '"+ provincia +"'  and liberoal > '"+ params.getData() +"'";


                try {
                    //rimuovo tutti i dettagli per una determinata quota
                    String query = "select l.CodiceFiscale, l.Nome, l.Cognome,\n" +
                            "l.DataNascita, l.NomeComuneResidenza, l.NomeProvinciaResidenza, \n" +
                            "l.Indirizzo,\n" +
                            "l.Cap, l.Telefono, i.NomeProvinciaFeneal as TerritorioFeneal, \n" +
                            "i.Ente, i.CurrentAzienda, i.IscrittoA , i.LiberoAl\n" +
                            "from feneal.lavoratori l inner join feneal.lavoratori_liberi i on l.CodiceFiscale = i.CodiceFiscale where" + whereclause;


                    SQLQuery q = s.createSQLQuery(query);
                    q.setResultTransformer(Transformers.aliasToBean(LavoratoreIncrocioNoniscritti.class));


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

        return (List<LavoratoreIncrocioNoniscritti>)b.getValue();
    }
}
