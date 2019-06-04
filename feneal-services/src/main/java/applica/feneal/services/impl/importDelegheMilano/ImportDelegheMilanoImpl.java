package applica.feneal.services.impl.importDelegheMilano;


import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.feneal.domain.model.core.deleghe.milano.DelegheMilanoObject;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.FileSearchPath;
import applica.feneal.services.ImportDelegheMilanoService;
import applica.framework.LoadRequest;
import applica.framework.fileserver.FileServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ImportDelegheMilanoImpl implements ImportDelegheMilanoService {
    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private FileServer server;

    @Autowired
    private FileSearchPath searchPath;

    @Autowired
    private FileServer fileServer;

    @Autowired
    private CollaboratorRepository collRep;

    @Override
    public DelegheMilanoObject importDelegheMilano(String  importData) throws Exception{

        InputStream fileStream = fileServer.getFile(importData);

        //creo la cartella temporanea dove andare a salvare il file.zip
        File temp1 = File.createTempFile("import_deleghe_milano","",new File("C:/Users/felic/Desktop/"));
        temp1.delete();
        temp1.mkdir();

        //effettuo l'unzip del file e lo salvo nella cartella temporanea
        //creta in precedenza
        unzip(fileStream , temp1);


        File selec = new File(temp1.getCanonicalPath()+"/INDEX/Deleghe.bak");


        //una volta selezionato il file che mi interessa
        //lo vado a trasformare in una lista di String
        List<String> fileSelected = transformFile(selec);


        //creo le due liste per il lavoratori con e senza CF
        DelegheMilanoObject obj = new DelegheMilanoObject();

        obj.setCollaboratori(collRep.find(LoadRequest.build()).getRows());
        //inserisco il lavoratori pervisualizzarli nel report
        convertToDelega(fileSelected, temp1, obj);



        return obj;
    }


    public void unzip(InputStream zipFile,File outputFolder) throws IOException {

        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(zipFile);

        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = outputFolder.getCanonicalPath() + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                new File(filePath).getParentFile().mkdirs();
                extractFile(zipIn, filePath);
                System.out.println(filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                System.out.println(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public List<String> transformFile(File myfile) throws Exception{
        BufferedReader abc = new BufferedReader(new FileReader(myfile));
        List<String> data = new ArrayList<String>();
        String s;
        while((s=abc.readLine())!=null) {
            data.add(s);
            System.out.println(s);
        }
        abc.close();

        return data;
    }

    public void convertToDelega(List<String> selectedRow, File dir, DelegheMilanoObject obj) throws Exception{


        List<DelegaMilano> conCodici = obj.getConCodici();
        List<DelegaMilano> senzaCiodici = obj.getSenzaCodici();

        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        for (String r : selectedRow) {
            String delimiter = "\\|";
            String[] tempArray = r.split(delimiter);
            DelegaMilano del = new DelegaMilano();


            del.setCollaborator(getFirtsCollaboratorIdIfExist(obj.getCollaboratori()));
            del.setNumProgressivo(tempArray[2]);
            del.setNumProtocollo(tempArray[3]);
            del.setDataArchiviazione(d.parse(tempArray[4]));
            del.setCognome(tempArray[7]);
            del.setNome(tempArray[8]);
            del.setLavoratoreNomeCompleto(tempArray[7]+" "+tempArray[8]);
            del.setDataNascita(d.parse(tempArray[9]));
            del.setCodiceLavoratore(tempArray[12]);
            del.setBarCode(Long.parseLong(tempArray[13]));
            del.setCodiceFiscale(tempArray[14]);
            del.setCodiceSind(Long.parseLong(tempArray[17]));
            del.setCodiceCompr(Long.parseLong(tempArray[18]));
            del.setDataConferma(d.parse(tempArray[19]));
            del.setDataAdesione(d.parse(tempArray[22]));
            del.setNumDelega(tempArray[23]);
            del.setNote(tempArray[28]);
            del.setFilename(tempArray[29]);
            String path = retrieveFilePath(del.getFilename(), dir);
            del.setFilePath(path);
            if (!tempArray[14].isEmpty())
                conCodici.add(del);
            else
                senzaCiodici.add(del);
        }
    }

    private String getFirtsCollaboratorIdIfExist(List<Collaboratore> collaboratori) {

        if (collaboratori.size() == 0)
            return "";

        return collaboratori.get(0).getSid();

    }

    private String retrieveFilePath(String filename, File targetStream) throws Exception{
        String pathFound = searchPath.searchFilePath(targetStream, filename);
        InputStream path = new FileInputStream(pathFound);
        return server.saveFile("files/importMilanoDeleghe/deleghe/", "TIF", path);
    }

}


