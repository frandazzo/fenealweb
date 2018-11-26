package applica.feneal.domain.data.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.framework.Repository;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface IscrizioniRepository extends Repository<Iscrizione> {

    List<Iscrizione> findIscrizioniByFiscalCode(String fiscalCode);

    List<Iscrizione> findIscrizioniByFiscalCodeWithQuoteDetailsMerge(String fiscalCode);

    List<Iscrizione> findIscrizioniByWorkerId(int workerId);

    void executeCommand(Command command);

    Session getSession();

}
