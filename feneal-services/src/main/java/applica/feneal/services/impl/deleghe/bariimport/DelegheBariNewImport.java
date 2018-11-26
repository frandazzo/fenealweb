package applica.feneal.services.impl.deleghe.bariimport;

import applica.feneal.domain.data.core.deleghe.DelegaBariRepository;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.validation.DelegaBariValidator;
import applica.feneal.services.DelegheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

@Component
public class DelegheBariNewImport {

    @Autowired
    private DelegaBariRepository delRep;

    @Autowired
    private DelegheService delSvc;

    @Autowired
    private DelegaBariValidator delVal;

    public void importDelega(DelegaBari summary, File f) throws Exception {

        delSvc.writeToFile(f, "*******Creo la delega per il lavoratore " + summary.getWorker().getSurnamename() );

        String error = delVal.validate(summary);

        if (!StringUtils.isEmpty(error)){
            //se ho trovato la delega mi assicuro di accettarla
            delSvc.writeToFile(f, "*******Errore nella creazione della delega: " +  error );
            return;
        }

        delRep.save(summary);
    }
}
