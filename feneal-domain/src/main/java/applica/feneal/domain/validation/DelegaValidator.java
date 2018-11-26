package applica.feneal.domain.validation;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.framework.AEntity;
import applica.framework.data.security.SecureCrudStrategy;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by antoniolovicario on 16/04/16.
 */
@Component
public class DelegaValidator  implements DomainClassValidator  {
    @Autowired
    private Security sec;


    @Autowired
    private CompanyRepository comRep;


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
        Delega d = (Delega)elem;

        if (d.getLid() > 0)
            return "";

        //devo verificare che ci sia un settore e che sia impianti fissi  o edile
        //che ci sia un ente e che se si tratta di impianti fissi ci sia lazienda

        if (d.getSector() == null)
            b.append("Settore mancante");

        if (d.getProvince() == null)
            b.append("Provincia mancante");


        //devo assicurarmi che la provincia faccia parte dell'utnete logato
        //User u = ((User) sec.getLoggedUser());
        long companyId = ((long)
                secStrategy.getOwnerProvider().provide());
        Company cc = comRep.get(companyId).orElse(null);
        if (cc == null){
            return "Company nulla";
        }

        if (d.getProvince() != null)
            if (!cc.containProvince(d.getProvince().getDescription()))
                b.append("Provincia non ammessa");

        if (d.getSector() != null)
            if (d.getSector().getDescription().equals(Sector.sector_inps))
                b.append("Settore non ammesso");


        if (d.getSector() != null)
            if (d.getSector().getType().equals(Sector.sector_edile)){

                if (d.getParitethic() == null)
                    b.append("Ente bilaterale nullo");

                //metto comunque a nullo un eventuale valore per l'azienda
                d.setWorkerCompany(null);
            }else{
                if (d.getWorkerCompany() == null)
                    b.append("Azienda nulla");

                //metto l'ente a null
                d.setParitethic(null);
            }


        return b.toString();


    }
}
