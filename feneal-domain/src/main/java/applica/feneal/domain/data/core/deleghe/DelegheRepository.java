package applica.feneal.domain.data.core.deleghe;

import applica.feneal.domain.data.Command;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.framework.Repository;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by fgran on 05/04/2016.
 */
public interface DelegheRepository extends Repository<Delega> {

    void executeCommand(Command command);

    Session getSession();

    List<Delega> getDelegheByLavoratore(long lavoratoreId);



     boolean hasLavoratoreSomeDelegaForCompany(long companyId, String fiscalCode) ;

     boolean hasLavoratoreSomeDelegaForProvince(String provinceName, String fiscalCode) ;


}