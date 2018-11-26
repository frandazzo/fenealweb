package applica.feneal.services.impl.quote;

import applica.feneal.domain.model.utils.LocalStorage;
import applica.framework.library.options.OptionsManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fgran on 21/05/2016.
 */
@Component
public class ImportQuoteLogger {
    @Autowired
    private OptionsManager optManager;

    @Autowired
    private LocalStorage storage;

    //questa funzione logga su un file di testo e su un local storage (in questo caso è la session)
    //tutti i conenuti raggruppati per titolo
    public void log(String fileName, String title, String content) throws Exception {

        if (StringUtils.isEmpty(fileName))
            throw new Exception("Nome file non esistente");

        //recupero il nome della cartella di log
        String logFolder = optManager.get("applica.fenealquote.logfolder");
        String filePath = logFolder + fileName;
        //creo se non esiste il file...
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            // il file esiste e posso loggare
        }else{
            //altrimenti lo creo
            f.createNewFile();
        }

        writeToFile(f, content);

        if (!StringUtils.isEmpty(title))
            storage.put(title, content);


    }


    public void log(String fileName, String title, String content, boolean includeLocalStorage) throws Exception {

        if (StringUtils.isEmpty(fileName))
            throw new Exception("Nome file non esistente");

        //recupero il nome della cartella di log
        String logFolder = optManager.get("applica.fenealquote.logfolder");
        String filePath = logFolder + fileName;
        //creo se non esiste il file...
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            // il file esiste e posso loggare
        }else{
            //altrimenti lo creo
            f.createNewFile();
        }

        SimpleDateFormat f1 = new SimpleDateFormat("[dd/MM/yyyy hh:mm:ss]: ");
        String data = f1.format(new Date()) + content;
        writeToFile(f, data);

        if (includeLocalStorage)
            if (!StringUtils.isEmpty(title))
                storage.put(title, content);


    }

    private void writeToFile(File myFile, String content) {
        BufferedWriter bufferedWriter = null;
        try{
                Writer writer = new FileWriter(myFile,true);
                bufferedWriter = new BufferedWriter(writer);
                PrintWriter out = new PrintWriter(bufferedWriter);
                out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
            } catch(Exception ex){

            }
        }
        //Files.write(myFile.getAbsolutePath(), content.getBytes(), StandardOpenOption.APPEND);
    }


}
