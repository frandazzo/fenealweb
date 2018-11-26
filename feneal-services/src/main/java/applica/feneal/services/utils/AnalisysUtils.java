package applica.feneal.services.utils;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.analisi.IscrittiDescriptor;
import applica.feneal.domain.model.core.analisi.IscrittiDescriptorItem;
import applica.feneal.domain.model.core.analisi.LegendaFactory;
import applica.feneal.domain.utils.Box;
import applica.framework.security.Security;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnalisysUtils {
    @Autowired
    private IscrizioniRepository iscRep;


    public List<Integer> getListaAnniIscrizioni(){

        final Box box = new Box();

        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = null;
//                    if (u.getCategory() != null){ //utnete categoria
//                        query= s.createSQLQuery(
//                                "select distinct anno from Iscrizioni where categoryId=" + u.getCategory().getLid() ).addScalar("anno");
//
//
//                    }else if (u.getRegion() != null){
//                        query= s.createSQLQuery(
//                                "select distinct anno from Iscrizioni where Id_Regione=" + u.getRegion().getIid() ).addScalar("anno");
//
//                    }
//                    else{
                        query= s.createSQLQuery(
                                "select distinct anno from Iscrizioni" ).addScalar("anno");
//                    }


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



    public IscrittiDescriptor getIscrittiPerSettore_UtenteNazionale(int anno, int regionId){

        final Box box = new Box();
        final IscrittiDescriptor result = new IscrittiDescriptor();
        result.setLegenda(LegendaFactory.constructLegenda().getItems());


        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = null;
                    if (regionId > 0){


                        String sql = "select i.Settore as label, count(i.CodiceFiscale) as total from \n" +
                                "\n" +
                                "(select l.codicefiscale as CodiceFiscale, l.nome as Nome, l.cognome as Cognome, l.nomenazione as Nazionalita,\n" +
                                " i.NomeProvincia as Territorio, i.settore as Settore from lavoratori l inner join\n" +
                                "iscrizioni i on l.id = i.id_lavoratore  where i.anno = "+ anno +" and i.Id_Regione = "+ regionId +" \n" +
                                "group by l.CodiceFiscale, i.nomeprovincia, i.Settore) i\n" +
                                "\n" +
                                "\n" +
                                "GROUP BY  Settore";


                        query= s.createSQLQuery(sql).addScalar("label").addScalar("total");

                    }
                    else{
                        String sql = "select i.Settore as label, count(i.CodiceFiscale) as total from \n" +
                                "\n" +
                                "(select l.codicefiscale as CodiceFiscale, l.nome as Nome, l.cognome as Cognome, l.nomenazione as Nazionalita,\n" +
                                " i.NomeProvincia as Territorio, i.settore as Settore from lavoratori l inner join\n" +
                                "iscrizioni i on l.id = i.id_lavoratore  where i.anno = "+ anno +"  \n" +
                                "group by l.CodiceFiscale, i.nomeprovincia, i.Settore) i\n" +
                                "\n" +
                                "\n" +
                                "GROUP BY  Settore";
                        query= s.createSQLQuery(sql).addScalar("label").addScalar("total");
                    }



                    List<Object[]> objects = query.list();

                    List<IscrittiDescriptorItem> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        IscrittiDescriptorItem v = new IscrittiDescriptorItem();

                        v.setLabel((String)object[0]);
                        v.setTotal(((BigInteger)object[1]).intValue());



                        a.add(v);
                    }
                    box.setValue(a);


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

        result.setIscritti((List<IscrittiDescriptorItem>)box.getValue());

        return result;

    }

    public IscrittiDescriptor getIscrittiPerAreaGeografica_UtenteNazionale(int anno, int regionId){

        final Box box = new Box();
        final IscrittiDescriptor result = new IscrittiDescriptor();
        result.setLegenda(LegendaFactory.constructLegenda().getItems());


        iscRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = iscRep.getSession();
                Transaction tx = null;
                try{

                    tx = s.beginTransaction();

                    SQLQuery query = null;
                    if (regionId > 0){

                        String sql = "select i.Territorio as label, count(i.CodiceFiscale) as total from \n" +
                                "\n" +
                                "(select l.codicefiscale as CodiceFiscale, \n" +
                                " i.NomeProvincia as Territorio, i.settore as Settore from lavoratori l inner join\n" +
                                "iscrizioni i on l.id = i.id_lavoratore  where i.anno = "+ anno +" and i.Id_Regione = "+ regionId +" \n" +
                                "group by l.CodiceFiscale, i.nomeprovincia, i.Settore) i\n" +
                                "\n" +
                                "\n" +
                                "GROUP BY  i.Territorio;";

                        query= s.createSQLQuery(sql).addScalar("label").addScalar("total");

                    }
                    else{

                        String sql = "select i.Regione as label, count(i.CodiceFiscale) as total from \n" +
                                "\n" +
                                "(select l.codicefiscale as CodiceFiscale, i.NomeRegione as Regione from lavoratori l inner join\n" +
                                "iscrizioni i on l.id = i.id_lavoratore  where i.anno = "+ anno +" \n" +
                                "group by l.CodiceFiscale, i.nomeprovincia, i.Settore) i\n" +
                                "\n" +
                                "\n" +
                                "GROUP BY  i.Regione";

                        query= s.createSQLQuery(sql).addScalar("label").addScalar("total");
                    }




                    List<Object[]> objects = query.list();

                    List<IscrittiDescriptorItem> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        IscrittiDescriptorItem v = new IscrittiDescriptorItem();

                        v.setLabel((String)object[0]);
                        v.setTotal(((BigInteger)object[1]).intValue());



                        a.add(v);
                    }
                    box.setValue(a);


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

        result.setIscritti((List<IscrittiDescriptorItem>)box.getValue());

        return result;

    }

//    public IscrittiDescriptor getIscrittiPerAreaGeografica_UtenteRegionale(int anno, int regionId){
//
//        final Box box = new Box();
//        final IscrittiDescriptor result = new IscrittiDescriptor();
//        result.setLegenda(LegendaFactory.constructLegenda().getItems());
//
//
//        iscRep.executeCommand(new Command() {
//            @Override
//            public void execute() {
//
//                Session s = iscRep.getSession();
//                Transaction tx = null;
//                try{
//
//                    tx = s.beginTransaction();
//
//                    SQLQuery query = null;
//                    query= s.createSQLQuery(
//                            "select nomeProvincia as label, count(Id_Lavoratore) as total from Iscrizioni where anno=" + anno +" and  Id_Regione=" + regionId +
//                                    " GROUP BY nomeProvincia").addScalar("label").addScalar("total");
//
//
//
//
//
//                    List<Object[]> objects = query.list();
//                    List<IscrittiDescriptorItem> a = new ArrayList<>();
//                    for (Object[] object : objects) {
//
//                        IscrittiDescriptorItem v = new IscrittiDescriptorItem();
//
//                        v.setLabel((String)object[0]);
//                        v.setTotal(((BigInteger)object[1]).intValue());
//
//
//
//                        a.add(v);
//                    }
//                    box.setValue(a);
//
//                    tx.commit();
//
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                    tx.rollback();
//
//                }
//                finally{
//                    s.close();
//
//                }
//
//
//
//            }
//        });
//
//        result.setIscritti((List<IscrittiDescriptorItem>)box.getValue());
//
//        return result;
//
//    }

//    public IscrittiDescriptor getIscrittiPerCategoria_UtenteRegionale(int anno, int regionId){
//
//        final Box box = new Box();
//        final IscrittiDescriptor result = new IscrittiDescriptor();
//        result.setLegenda(LegendaFactory.constructLegenda().getItems());
//
//
//        iscRep.executeCommand(new Command() {
//            @Override
//            public void execute() {
//
//                Session s = iscRep.getSession();
//                Transaction tx = null;
//                try{
//
//                    tx = s.beginTransaction();
//
//                    SQLQuery query = s.createSQLQuery(
//                            "select nomeCategoria as label, count(Id_Lavoratore) as total from Iscrizioni where anno=" + anno +" and  Id_Regione=" + regionId +
//                                    " GROUP BY nomeCategoria").addScalar("label").addScalar("total");
//
//
//                    List<Object[]> objects = query.list();
//                    List<IscrittiDescriptorItem> a = new ArrayList<>();
//                    for (Object[] object : objects) {
//
//                        IscrittiDescriptorItem v = new IscrittiDescriptorItem();
//
//                        v.setLabel((String)object[0]);
//                        v.setTotal(((BigInteger)object[1]).intValue());
//
//
//
//                        a.add(v);
//                    }
//                    box.setValue(a);
//
//                    tx.commit();
//
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                    tx.rollback();
//
//                }
//                finally{
//                    s.close();
//
//                }
//
//
//
//            }
//        });
//
//        result.setIscritti((List<IscrittiDescriptorItem>)box.getValue());
//
//        return result;
//
//    }

}
