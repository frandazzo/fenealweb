package applica.feneal.services.impl;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.UsersRepository;

import applica.feneal.domain.model.User;
import applica.feneal.services.SetupService;
import applica.feneal.services.impl.setup.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SetupServiceImpl implements SetupService {


    @Autowired
    private EntiSetup entiSetup;

    @Autowired
    private AdminSetup adminSetup;


    @Autowired
    private GeoSetup geoSetup;



    @Autowired
    private RolesSetup rolesSetup;



    @Autowired
    private UsersRepository userRep;

    @Autowired
    private CompanyRepository comRep;

    @Autowired
    private WidgetSetup wSetup;



    @Override
    public void setup() {



        //inizializzo in ordine e tenedo conto di eventuali dipendenze...
        geoSetup.setup();
        rolesSetup.setup();
        adminSetup.setup();
        entiSetup.setup();
        wSetup.setup();






    }



}
