package applica.feneal.services.impl.excel;

import applica.framework.management.excel.ExcelInfo;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExcelValidator {
    private List<String> headerTemplates;
    private String error = "";

    public String getError() {
        return error;
    }

    public ExcelValidator(List<String> headerTemplates){

        this.headerTemplates = headerTemplates;
    }

    public boolean validate(ExcelInfo data){
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            error = String.format("Un file contiene errori: %s  <br>",  data.getError());
            return false;
        }



        List<String> headers = data.getHeaderFields();
        String name = FilenameUtils.getName(data.getSourceFile());
        String err = headersContainsTemplate(headers, headerTemplates);
        boolean equal = StringUtils.isEmpty(err);
        if (!equal){
            //recupero il nome del file

            error = String.format("Il file %s non contiene le intestazioni richieste<br>: Campi mancanti: %s", name, err);
            return false;
        }
        if (equal){
            //verifico che ci sia almeno una riga
            if (data.getOnlyValidRows().size() == 0){
                error = String.format("Il file %s non contiene informazioni<br>", name);
                return false;
            }

        }

        return true;
    }

    private String headersContainsTemplate(List<String> headers, List<String> template) {
        String errors = "";
        for (String s : template) {
            if (!headers.contains(s)){
                errors = s + ";" + errors;
            }
        }
        return errors;

    }


}
