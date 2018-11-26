package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CompanyRepository;

import applica.feneal.domain.model.core.Company;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyHibernateRepository extends HibernateRepository<Company> implements CompanyRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Company findCompanyByProvinceName(String provinceName) {

        if (StringUtils.isEmpty(provinceName))
            return null;

        //recupero tutti i territori anagrafati
        List<Company> c = this.find(null).getRows();

        for (Company company : c) {
            if (company.containProvince(provinceName))
                return company;
        }

        return null;
    }

    @Override
    public Class<Company> getEntityType() {
        return Company.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("description", false));
    }


}
