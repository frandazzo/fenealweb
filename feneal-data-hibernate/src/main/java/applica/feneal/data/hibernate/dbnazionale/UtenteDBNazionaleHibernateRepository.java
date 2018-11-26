package applica.feneal.data.hibernate.dbnazionale;

import applica.feneal.domain.data.dbnazionale.UtenteDBNazioneRepository;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.framework.LoadRequest;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
@Repository
public class UtenteDBNazionaleHibernateRepository extends HibernateRepository<UtenteDbNazionale> implements UtenteDBNazioneRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("cognome", false));
    }

    @Override
    public Class<UtenteDbNazionale> getEntityType() {
        return UtenteDbNazionale.class;
    }


    @Override
    public UtenteDbNazionale findUtenteByFiscalCode(String fiscalCode) {

        if (StringUtils.isEmpty(fiscalCode))
            return null;

        LoadRequest req = LoadRequest.build().filter("codiceFiscale", fiscalCode);
        List<UtenteDbNazionale> c = this.find(req).getRows();

        //ce ne puo essere solo uno...........
        if (c.size() >= 1)
            return c.get(0);

        return null;
    }

}