package applica.feneal.services.impl.setup;

import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.data.geo.CountriesRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.geo.*;
import applica.feneal.domain.model.utils.CsvGeoReader;
import applica.feneal.domain.model.utils.LoggerClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GeoSetup implements AppSetup {

  //  private static final Logger logger = LogManager.getLogger();

    @Autowired
    private CountriesRepository counRep;


    @Autowired
    private ProvinceRepository proRep;


    @Autowired
    private CitiesRepository cityRep;

    @Autowired
    private RegonsRepository regRep;


    @Override
    public void setup() {

        try {
            LoggerClass.info("Start geo setup");

            if (!existCountries()){
                LoggerClass.info("Countries not existing -- Start creating --");
                createCountries();
                LoggerClass.info("Countries created");
            }
            if (!existRegions()){
                LoggerClass.info("Regions not existing -- Start creating --");
                createRegions();
                LoggerClass.info("Regions created");
            }

            if (!existProvinces()){
                LoggerClass.info("Provinces not existing -- Start creating --");
                createProvinces();
                LoggerClass.info("Provinces created");
            }
            if (!existCities()){
                LoggerClass.info("Cities not existing -- Start creating --");
                createCities();
                LoggerClass.info("Cities created");
            }

            LoggerClass.info("End geo setup");
        }catch(Exception ex){

            ex.printStackTrace();
            LoggerClass.error(ex.getMessage());

        }

    }


    private boolean existCountries() {


        Country c = counRep.get(100).orElse(null);
        if (c == null)
            return false;

        return true;
    }

    private boolean existRegions() {


        Region c = regRep.get(130).orElse(null);
        if (c == null)
            return false;

        return true;
    }

    private boolean existProvinces() {


        Province c = proRep.get(1).orElse(null);
        if (c == null)
            return false;

        return true;
    }


    private boolean existCities() {


        City c = cityRep.get(1).orElse(null);
        if (c == null)
            return false;

        return true;
    }

    private void createCities() {
        List<City> cities = CsvGeoReader.readCities();
        for (City city : cities) {
            cityRep.save(city);
        }
    }

    private void createProvinces() {
        List<Province> provinces = CsvGeoReader.readProvinces();
        for (Province province : provinces) {
            proRep.save(province);
        }
    }

    private void createRegions() {
        List<Region> regions = CsvGeoReader.readRegions();
        for (Region region : regions) {
            regRep.save(region);
        }
    }

    private void createCountries() {
        List<Country> countries = CsvGeoReader.readCountries();
        for (Country country : countries) {
            counRep.save(country);
        }
    }
}
