package applica.feneal.services.impl.excel;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.importData.ImportDelegheValidator;
import applica.feneal.services.ExcelReaderDataService;
import applica.framework.fileserver.FileServer;
import applica.framework.management.csv.RowValidator;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.management.excel.ExcelReader;
import applica.framework.security.Security;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;

@Service
public class ExcelReaderDataServiceImpl implements ExcelReaderDataService {

    @Autowired
    private Security sec;

    @Autowired
    private FileServer server;

    @Override
    public ExcelInfo readExcelFile(String fileNameOnFileServer, int sheetNumber, int rowNumber, int colNumber
            , ExcelValidator validator, RowValidator rowValidator) throws Exception {

        if (StringUtils.isEmpty(fileNameOnFileServer))
            throw new Exception("File missing");

        String user = ((User) sec.getLoggedUser()).getUsername();

        //creo una cartella temporanea dove inserirò il file excel preso dal fileserver
        File temp1 = File.createTempFile("readExcel_" + user,"");
        temp1.delete();
        temp1.mkdir();

        String file = getTempFile(fileNameOnFileServer, temp1);

        ExcelReader reader = new ExcelReader(file, sheetNumber,rowNumber , colNumber,rowValidator);

        ExcelInfo res = reader.readFile();

        boolean valid = true;
        if (validator != null){
            valid = validator.validate(res);
        }

        if (!valid)
            throw new Exception(validator.getError());



        return res;

    }


    private String getTempFile(String  filePath, File temp ) throws IOException {
        if (fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(filePath))
            return null;

        InputStream file =server.getFile(filePath);
        String mime = "." + FilenameUtils.getExtension(filePath);
        String  a=FilenameUtils.getName(filePath);


        return addToTempFolder(file, a, mime, temp);
    }

    private String addToTempFolder(InputStream inputStream, String name, String mime, File temp ) throws IOException {

        //aggiungo il file alla direcotry
        String filename = temp.getAbsolutePath() + "\\" + name;
        File nn = new File(filename);
        nn.createNewFile();

        //copio il file inviato nella cartella temporanea
        OutputStream outputStream = new FileOutputStream(nn);

        try{

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        return nn.getAbsolutePath();
    }


}
