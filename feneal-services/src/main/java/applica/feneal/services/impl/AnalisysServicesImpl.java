package applica.feneal.services.impl;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.analisi.IscrittiDescriptor;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.services.AnalisysService;
import applica.feneal.services.GeoService;
import applica.feneal.services.utils.AnalisysUtils;
import applica.feneal.services.utils.StatisticsUtils;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalisysServicesImpl implements AnalisysService {
    @Autowired
    private Security sec;

    @Autowired
    private AnalisysUtils utils;

    @Autowired
    private GeoService geo;



    @Override
    public Integer[] getAnniIscrizioni() {

        List<Integer> a= utils.getListaAnniIscrizioni();

        Integer[] a1 = new Integer[a.size()];

        a1 = a.toArray(a1);


        return a1;
    }

    @Override
    public IscrittiDescriptor getIscrittiPerSettore(int anno, String region) {



            //utente nazionale
            Region regionData = geo.getREgionByName(region);
            if (regionData != null){
                return utils.getIscrittiPerSettore_UtenteNazionale(anno, regionData.getIid());
            }else{
                return utils.getIscrittiPerSettore_UtenteNazionale(anno, -1);
            }

    }

    @Override
    public IscrittiDescriptor getIscrittiPerAreaGeografica(int anno, String region) {




        Region regionData = geo.getREgionByName(region);


                 //utente nazionale

            if (regionData != null){
                return utils.getIscrittiPerAreaGeografica_UtenteNazionale(anno, regionData.getIid());
            }else{
                return utils.getIscrittiPerAreaGeografica_UtenteNazionale(anno, -1);
            }




    }
}
