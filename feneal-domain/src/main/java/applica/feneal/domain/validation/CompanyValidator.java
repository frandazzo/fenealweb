package applica.feneal.domain.validation;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.geo.Province;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by fgran on 18/05/2016.
 */
@Component
public class CompanyValidator implements Validator {

    @Autowired
    private Security secure;


    @Autowired
    private CompanyRepository userRep;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Company.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Company c = ((Company) o);

        //valido la descrizione
        if (org.apache.commons.lang.StringUtils.isEmpty(c.getDescription()))
            errors.rejectValue("description", null, "Descrizione obbligatoria");

       //devo verificare che non ci sia un'altra company con lo stesso nome
        //per lo username devo verificare che non esistano altri utenti con lo stesso nome
        if (!org.apache.commons.lang.StringUtils.isEmpty(c.getDescription()))
            if (checkDescriptionIsInUse(c)){
                errors.rejectValue("description", null, "Nome territorio già in uso");
                return;
            }

        //devo adesso validare le provincie di riferimento
        if (c.getProvinces() == null){
            errors.rejectValue("provinces", null, "Nessuna provincia selezionata");
            return;
        }
        if (c.getProvinces().size()== 0){
            errors.rejectValue("provinces", null, "Nessuna provincia selezionata");
            return;
        }

        //devo verificare che tutte le province selezionate appartengano ad una sola regione
        if (!povincesBelongToSameRegion(c)){
            errors.rejectValue("provinces", null, "Province non della stessa regione");
            return;
        }


        //devo verificare che non ci siano altri territori che fanno riferimento ad una delle province selezionate
        for (Province province : c.getProvinces()) {
            Company temp = userRep.findCompanyByProvinceName(province.getDescription());
            if (temp != null){
                //vuol dire che una company gia possiede un riferimento alla provincia selezionata
                //se sto creando una nuova company allora è un errore altrimenti
                //devo verificare che non si tratti della stessa..
                if (c.getLid() == 0){
                    errors.rejectValue("provinces", null, "Esite già un territorio afferente alla stessa provincia");
                    return;
                }else{
                    if (c.getLid() != temp.getLid()){
                        errors.rejectValue("provinces", null, "Esite già un territorio afferente alla stessa provincia");
                        return;
                    }
                }

            }
        }

        //devo inoltre verificare che l'utente che sta creando è un amministratore
        if (((User) secure.getLoggedUser()).retrieveUserRole().getLid() != 1)
            errors.rejectValue("description", null, "Solo un amministratore può creare un territorio");



    }

    private boolean povincesBelongToSameRegion(Company c) {
        //prendo la prima provincia e verifico che tutte abbiano la stessa regione
        Province p = c.getProvinces().get(0);
        int regionId = p.getIdRegion();

        for (Province province : c.getProvinces()) {
            if (province.getIdRegion() != regionId)
                return false;
        }

        return true;
    }

    private boolean checkDescriptionIsInUse(Company o) {
        Company temp = userRep.find(LoadRequest.build().filter("description", o.getDescription())).findFirst().orElse(null);
        if (temp == null)
            return false;
        if (o.getLid()>0){ //sto aggiornamndo....
            //se sto aggiornando l'unico user con lo stesso username deve avere lo stesso id
            //se cosi non è lancio una validation exception..
            if (o.getLid() == temp.getLid())
                return false;

            return true;

        }

        //in questo caso sto inserendo un nuovo utente e poichè esiste gia un utilizzo dello username...
        return true;


    }

}
