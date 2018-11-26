package excelReader;

import applica.framework.management.excel.ExcelInfo;
import applica.framework.management.excel.ExcelReader;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

/**
 * Created by fgran on 02/02/2017.
 */
//class RowValidator1 extends applica.framework.management.csv.RowValidator {
//
//    @Override
//    public void validateRow(Hashtable<String, String> row) {
//        if (row.get("COD. FISCALE") == null){
//            this.error = "Manca il campo cod. fiscale";
//            this.valid = false;
//        }else{
//            if (StringUtils.isEmpty(row.get("COD. FISCALE").toString().trim())){
//                this.error = "Manca il campo cod. fiscale";
//                this.valid = false;
//            }
//        }
//    }
//}
//
//
//public class ExcelTest extends TestCase {
//
//    String url;
//    protected void setUp() {
//        url =  "C:\\Users\\fgran\\Desktop\\aa.xlsx";
//    }
//
//
//    public void tearDown() throws Exception {
//
//    }
//
//
//    public void testReadExcelOld() throws Exception {
//
//
//        //qui testo il reader del csv
//
//
//        ExcelReader reader = new ExcelReader(url, 0, 1, new RowValidator1());
//
//        ExcelInfo info = reader.readFile();
////
////
//        Assert.assertEquals(info.getOnlyValidRows().size() , 232);
////        Assert.assertEquals(info.getFieldsNumber(), 6);
////        Assert.assertEquals(info.getRecordNumber(), 7);
////        Assert.assertEquals(info.getNonValidatedRowIndexes().size(),0);
////        Assert.assertEquals(info.getRowValidationErrors(), 0);
//
//    }
//
//
//}
//
//
