package applica.feneal.services;

import applica.feneal.services.impl.excel.ExcelValidator;
import applica.framework.management.csv.RowValidator;
import applica.framework.management.excel.ExcelInfo;
import org.springframework.stereotype.Service;


public interface ExcelReaderDataService {

    ExcelInfo readExcelFile(String fileNameOnFileServer, int sheetNumber, int rowNumber, int colNumber, ExcelValidator validator, RowValidator rowValidator) throws Exception;
}
