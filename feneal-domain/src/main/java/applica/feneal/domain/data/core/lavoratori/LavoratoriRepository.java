package applica.feneal.domain.data.core.lavoratori;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 06/04/2016.
 */
public interface LavoratoriRepository extends Repository<Lavoratore> {

    void executeCommand(Command command);

    Session getSession();

    Lavoratore searchLavoratoreForCompany(long companyId, String fiscalCode);
    Lavoratore searchLavoratoreForProvince(String provinceName, String fiscalCode);


}