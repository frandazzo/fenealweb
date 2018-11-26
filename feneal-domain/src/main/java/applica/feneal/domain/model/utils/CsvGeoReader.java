package applica.feneal.domain.model.utils;

import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.framework.management.csv.CsvInfo;
import applica.framework.management.csv.CsvReader;
import applica.framework.management.csv.RowData;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvGeoReader {


    public static List<Country> readCountries()  {

        InputStream url =  CsvGeoReader.class.getResourceAsStream("/tbnazioni.csv");
        String tempFile = createTempFile(url);

        CsvReader reader = new CsvReader(tempFile,",","\"");

        CsvInfo result = reader.readFile();
        List<RowData> rawData = result.getImportedTableRows();

        return translateCountries(rawData);


    }

    private static String createTempFile(InputStream url) {
        try {

            File f = File.createTempFile("xxx", ".csv");
            FileOutputStream output = new FileOutputStream(f);
            IOUtils.copy(url, output);
            output.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
           e.printStackTrace();
            return "";
        }
    }

    private static List<Country> translateCountries(List<RowData> rawData) {
        List<Country> result = new ArrayList<Country>();

        for (RowData rowData : rawData) {

            Hashtable<String, String> h = rowData.getData();
            Country c = new Country();

            c.setId(Integer.parseInt(h.get("id")));
            c.setDescription(h.get("description"));
            c.setFiscalCode(h.get("fiscalCode"));
            c.setContinent(h.get("continent"));
            result.add(c);
        }

        return result;
    }

    public static List<City> readCities(){
        InputStream url =  CsvGeoReader.class.getResourceAsStream("/tbcomuni.csv");
        String tempFile = createTempFile(url);

        CsvReader reader = new CsvReader(tempFile,",","\"");

        CsvInfo result = reader.readFile();
        List<RowData> rawData = result.getImportedTableRows();

        return translateCities(rawData);
    }

    private static List<City> translateCities(List<RowData> rawData) {
        List<City> result = new ArrayList<City>();

        for (RowData rowData : rawData) {

            Hashtable<String, String> h = rowData.getData();
            City c = new City();

            c.setId(Integer.parseInt(h.get("id")));
            c.setDescription(h.get("description"));
            c.setCap(h.get("cap"));
            c.setFiscalcode(h.get("fiscalcode"));
            if (!h.get("idProvince").equals("NULL"))
                c.setIdProvince(Integer.parseInt(h.get("idProvince")));
            if (!h.get("idRegion").equals("NULL"))
                c.setIdRegion(Integer.parseInt(h.get("idRegion")));

            result.add(c);
        }

        return result;
    }


    public static List<Province> readProvinces(){
        InputStream url =  CsvGeoReader.class.getResourceAsStream("/tbprovince.csv");
        String tempFile = createTempFile(url);

        CsvReader reader = new CsvReader(tempFile,",","\"");

        CsvInfo result = reader.readFile();
        List<RowData> rawData = result.getImportedTableRows();

        return translateProvinces(rawData);

    }

    private static List<Province> translateProvinces(List<RowData> rawData) {
        List<Province> result = new ArrayList<Province>();

        for (RowData rowData : rawData) {

            Hashtable<String, String> h = rowData.getData();
            Province c = new Province();

            c.setId(Integer.parseInt(h.get("id")));
            c.setDescription(h.get("description"));
            c.setIdRegion(Integer.parseInt(h.get("idRegion")));

            result.add(c);
        }

        return result;
    }

    public static List<Region> readRegions(){
        InputStream url =  CsvGeoReader.class.getResourceAsStream("/tbregioni.csv");
        String tempFile = createTempFile(url);

        CsvReader reader = new CsvReader(tempFile,",","\"");

        CsvInfo result = reader.readFile();
        List<RowData> rawData = result.getImportedTableRows();

        return translateRegions(rawData);
    }

    private static List<Region> translateRegions(List<RowData> rawData) {
        List<Region> result = new ArrayList<Region>();

        for (RowData rowData : rawData) {

            Hashtable<String, String> h = rowData.getData();
            Region c = new Region();

            c.setId(Integer.parseInt(h.get("id")));
            c.setDescription(h.get("description"));


            result.add(c);
        }

        return result;
    }


}
