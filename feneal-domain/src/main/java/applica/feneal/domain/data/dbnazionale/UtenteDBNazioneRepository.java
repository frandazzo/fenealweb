package applica.feneal.domain.data.dbnazionale;

import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.framework.Repository;

/**
 * Created by fgran on 11/05/2016.
 */
public interface UtenteDBNazioneRepository extends Repository<UtenteDbNazionale> {

    UtenteDbNazionale findUtenteByFiscalCode(String fiscalCode);
}
