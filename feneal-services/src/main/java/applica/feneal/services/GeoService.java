package applica.feneal.services;

import applica.feneal.domain.model.core.lavoratori.FiscalData;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * Created by fgran on 09/04/2016.
 */
public interface GeoService {

    Country getCountryByName(String countryName);
    Province getProvinceByName(String provinceName);
    City getCityByName(String cityName);
    Region getREgionByName(String RegionName);


    Country getCountryById(int countryId);
    Province getProvinceById(int provinceId);
    City getCityById( int cityId);
    Region getREgionById(int RegionId);

    FiscalData getFiscalData(String fiscalCode) throws RemoteException;
    String calculateFiscalCode(String name, String surname, Date birthDate, String sex, String birthPlace, String birthNation) throws RemoteException;
}
