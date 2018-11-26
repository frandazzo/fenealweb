package applica.feneal.data.hibernate.core.lavoratori;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 06/04/2016.
 */
@Repository
public class LavoratoriHibernateRepository extends HibernateRepository<Lavoratore> implements LavoratoriRepository {

    @Autowired
    private CompanyRepository crep;

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Lavoratore searchLavoratoreForCompany(long companyId, String fiscalCode) {
        Session s = this.getSession();
        Transaction tx = s.beginTransaction();
        try {
            String sql = "SELECT * FROM fenealweb_lavoratore WHERE companyId = :company_id and fiscalcode = :fiscal";
            List<Lavoratore> cd = new ArrayList<Lavoratore>();
            SQLQuery query = s.createSQLQuery(sql);
            query.setParameter("company_id", companyId);
            query.setParameter("fiscal", fiscalCode);
            query.addEntity(Lavoratore.class);
            cd = (List<Lavoratore>) query.list();

            if (cd.size() >0)
                return cd.get(0);
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            //tx.rollback();
            throw ex;
        } finally {
            s.close();
        }
    }

    @Override
    public Lavoratore searchLavoratoreForProvince(String provinceName, String fiscalCode) {
        Company c = crep.findCompanyByProvinceName(provinceName);

        if (c == null)
            return null;


        return searchLavoratoreForCompany(c.getLid(), fiscalCode);
    }

    @Override
    public Class<Lavoratore> getEntityType() {
        return Lavoratore.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("surname", false));
    }


}