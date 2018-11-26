package applica.feneal.domain.validation;

import applica.feneal.domain.data.core.deleghe.DelegaBariRepository;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.framework.AEntity;
import applica.framework.data.security.SecureCrudStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelegaBariValidator implements DomainClassValidator  {


    @Autowired
    private DelegaBariRepository delRep;



    //ottengo un riferimento a questo perchè ho bisogno della company corrente durante la validazione
    //ma potrei non essere loggato
    //durante l'import dei dati da fenealgest...
    @Autowired
    private SecureCrudStrategy secStrategy;

    @Override
    public String validate(AEntity elem) throws Exception {

        //provvedo solo alla validazione delle nuove deleghe.
        //quelle aggiornabili, infatti possono aggiornare solo dati ininfluenti e non obbligatori
        StringBuilder b = new StringBuilder();
        DelegaBari d = (DelegaBari)elem;

        if (d.getWorker()!= null){
            if (delRep.getDelegaByLavoratoreAndData(d.getWorker().getLid(), d.getProtocolDate()) != null){
                b.append("delega esistente");

            }
        }




        if (d.getWorker()== null){
            b.append("Lavoratore nullo");
        }

        return b.toString();


    }
}
