package applica.feneal.services.impl;

import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.data.geo.CountriesRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.core.lavoratori.FiscalData;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.services.GeoService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fgran on 09/04/2016.
 */
@Service
public class GeoServiceImpl implements GeoService {
@Autowired
    OptionsManager optMan;

   @Autowired
   private CountriesRepository couRep;

    @Autowired
    private RegonsRepository regRep;

    @Autowired
    private CitiesRepository citRep;

    @Autowired
    private ProvinceRepository proRep;

    @Override
    public Country getCountryByName(String countryName) {
        LoadRequest req = LoadRequest.build().filter("description",countryName, Filter.EQ);
        return couRep.find(req).findFirst().orElse(null);
    }

    @Override
    public Province getProvinceByName(String provinceName) {
        LoadRequest req = LoadRequest.build().filter("description",provinceName, Filter.EQ);
        return proRep.find(req).findFirst().orElse(null);
    }

    @Override
    public City getCityByName(String cityName) {
        LoadRequest req = LoadRequest.build().filter("description",cityName, Filter.EQ);
        return citRep.find(req).findFirst().orElse(null);
    }

    @Override
    public Region getREgionByName(String regionName) {
        LoadRequest req = LoadRequest.build().filter("description",regionName, Filter.EQ);
        return regRep.find(req).findFirst().orElse(null);
    }

    @Override
    public Country getCountryById(int countryId) {
        return couRep.get(countryId).orElse(null);
    }

    @Override
    public Province getProvinceById(int provinceId) {
        return proRep.get(provinceId).orElse(null);
    }

    @Override
    public City getCityById(int cityId) {
        return citRep.get(cityId).orElse(null);
    }

    @Override
    public Region getREgionById(int regionId) {
        return regRep.get(regionId).orElse(null);
    }

    @Override
    public FiscalData getFiscalData(String fiscalCode) throws RemoteException {
        FenealgestUtils svc = null;
        it.fenealgestweb.www.CalcolaDatiFiscaliResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,optMan.get("applica.fenealgestutils"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        CalcolaDatiFiscali f = new CalcolaDatiFiscali();
        f.setCodiceFiscale(fiscalCode);
        result = svc.calcolaDatiFiscali(f);

        it.fenealgestweb.www.FiscalData webResult = result.getCalcolaDatiFiscaliResult();

        FiscalData f1  = new FiscalData();

        City city = getCityByName(webResult.getComune());
        String cityId = city!=null?city.getId().toString():"";
        Province prov = null;
        if (city != null)
            prov = getProvinceById((city.getIdProvince()));
        String provinceId = prov!=null?prov.getId().toString():"";
        Country country =getCountryByName(webResult.getNazione());
        String countryId = country!=null?country.getId().toString():"";

        String sex = webResult.getSesso().equals("FEMMINA")?"F":"M";

        // mock
        f1.setComune(cityId);  // id comune Matera = 3972
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        f1.setDatanascita(form.format(webResult.getDataNascita().getTime()));
        f1.setProvincia(provinceId);  // id prov Matera = 77
        f1.setNazione(countryId);   // codice Italia = 100
        f1.setSex(sex);
        return f1;
    }

    @Override
    public String calculateFiscalCode(String name, String surname, Date birthDate, String sex, String birthPlace, String birthNation) throws RemoteException {
        FenealgestUtils svc = null;
        CalcolaCodiceFiscaleResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,optMan.get("applica.fenealgestutils"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        CalcolaCodiceFiscale params = new CalcolaCodiceFiscale();
        params.setCognome(surname);
        params.setNome(name);
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthDate);

        params.setDataNascita(cal);
        params.setNomeComuneNascita(birthPlace);

        params.setNomeNazione(birthNation);
        if (sex.equals("F"))
            sex = "FEMMINA";
        params.setSesso(sex);

        result = svc.calcolaCodiceFiscale(params);
        return result.getCalcolaCodiceFiscaleResult();
    }
}
