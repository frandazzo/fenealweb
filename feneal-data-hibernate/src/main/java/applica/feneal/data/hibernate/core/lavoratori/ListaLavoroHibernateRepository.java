package applica.feneal.data.hibernate.core.lavoratori;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.lavoratori.ListaLavoroRepository;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 04/06/2016.
 */
@Repository
public class ListaLavoroHibernateRepository extends HibernateRepository<ListaLavoro> implements ListaLavoroRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }


    @Override
    public Long getNumberOfListForWorker( long workerId) {
        Session s = this.getSession();
        Transaction tx = s.beginTransaction();
        try {
            String sql = "SELECT count(*) FROM fenealweb_listelavoro_lavoratori WHERE lavoratoreId = :lavoratoreId";

            SQLQuery query = s.createSQLQuery(sql);
            query.setParameter("lavoratoreId", workerId);

            BigInteger count = (BigInteger)query.uniqueResult();

            return count.longValue();

        } catch (Exception ex) {
            ex.printStackTrace();
            //tx.rollback();
            throw ex;
        } finally {
            s.close();
        }
    }



    @Override
    public Class<ListaLavoro> getEntityType() {
        return ListaLavoro.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("description", false));
    }

}


