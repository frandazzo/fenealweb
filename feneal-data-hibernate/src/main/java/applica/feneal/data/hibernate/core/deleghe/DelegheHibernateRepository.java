package applica.feneal.data.hibernate.core.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.LoadRequest;
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
 * Created by fgran on 05/04/2016.
 */
@Repository
public class DelegheHibernateRepository extends HibernateRepository<Delega> implements DelegheRepository {

    @Autowired
    private CompanyRepository crep;

    @Autowired
    private LavoratoriRepository lavRep;

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public List<Delega> getDelegheByLavoratore(long lavoratoreId) {
        LoadRequest req = LoadRequest.build().filter("worker", lavoratoreId);
        return this.find(req).getRows();
    }

    @Override
    public boolean hasLavoratoreSomeDelegaForCompany(long companyId, String fiscalCode) {

        Company c = crep.get(companyId).orElse(null);

        if (c == null)
            return false;

        Lavoratore l = lavRep.searchLavoratoreForCompany(c.getLid(), fiscalCode);

        if (l == null)
            return false;





        Session s = this.getSession();
        Transaction tx = s.beginTransaction();
        try {
            String sql = "SELECT * FROM fenealweb_delega WHERE companyId = :company_id and workerId = :id";
            List<Delega> cd = new ArrayList<Delega>();
            SQLQuery query = s.createSQLQuery(sql);
            query.setParameter("company_id", companyId);
            query.setParameter("id", l.getLid());
            query.addEntity(Delega.class);
            cd = (List<Delega>) query.list();

            if (cd.size() >0)
                return true;
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            //tx.rollback();
            throw ex;
        } finally {
            s.close();
        }
    }

    @Override
    public boolean hasLavoratoreSomeDelegaForProvince(String provinceName, String fiscalCode) {
        Company c = crep.findCompanyByProvinceName(provinceName);

        if (c == null)
            return false;


        return hasLavoratoreSomeDelegaForCompany(c.getLid(), fiscalCode);
    }




    @Override
    public Class<Delega> getEntityType() {
        return Delega.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("documentDate", false));
    }


}

