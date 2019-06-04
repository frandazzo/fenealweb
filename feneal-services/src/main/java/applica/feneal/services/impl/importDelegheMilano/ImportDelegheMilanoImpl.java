package applica.feneal.services.impl.importDelegheMilano;


import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.feneal.domain.model.core.deleghe.milano.DelegheMilanoObject;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.*;
import applica.feneal.services.impl.importData.ImportCausaliService;
import applica.framework.LoadRequest;
import applica.framework.fileserver.FileServer;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ImportCausaliService cauServ;

    @Autowired
    private Security sec;

    @Autowired
    private CollaboratorRepository collRep;

    @Autowired
    private DelegheService delServ;

    @Autowired
    private GeoService geo;


    @Autowired
    private LavoratoreService lavSvc;

    @Override
    public DelegheMilanoObject importDelegheMilano(String  importData) throws Exception{

        InputStream fileStream = fileServer.getFile(importData);

        //creo la cartella temporanea dove andare a salvare il file.zip
        File temp1 = File.createTempFile("import_deleghe_milano","");
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

    @Override
    public void executeImportDelegheMilano(List<DelegaMilano> deleghe) throws Exception {

        //inserisco tutti i lavoratori

        List<Lavoratore> lavs = new ArrayList<>();
        for (DelegaMilano delega : deleghe) {
            Lavoratore l = null;
                l = constructLavoratore(delega);
                lavs.add(l);
        }

        //inseriti tutti i lavoratori mi assicuro che ci sia almeno una causale dleega
        cauServ.createIfNotExistCausaleIscrizioneDelega("NUOVA ISCRIZIONE");
        //creo tutte le deleghe e le inserisco
        for (DelegaMilano delegaMilano : deleghe) {
            //se una delega nella stessa data non esiste allora la inserisco
            if (!existDelega(delegaMilano.getCodiceFiscale(), delegaMilano.getDataConferma())){
                Lavoratore lav = lavs.stream().filter(a -> a.getFiscalcode().equals(delegaMilano.getCodiceFiscale())).findFirst().get();
                Delega d = createDelega(lav, delegaMilano);
                delServ.saveOrUpdate(((User) sec.getLoggedUser()).getLid(), d);
                delServ.acceptDelega(d.getDocumentDate(), d);
            }
        }

    }

    private Delega createDelega(Lavoratore l, DelegaMilano summary) throws Exception {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(summary.getDataConferma());

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        d.setImportGuid(s.format(new Date()));
        d.setWorker(l);
        d.setProvince(geo.getProvinceByName("Milano"));
        d.setSubscribeReason(cauServ.getCausaleIscrizione("NUOVA ISCRIZIONE"));
        d.setSector(cauServ.getSettore("EDILE"));
        d.setParitethic(cauServ.getEnte("CASSA EDILE"));
        d.setCollaborator(retrieveCollaborator(summary.getCollaborator()));

        //
        d.setNomeattachment(summary.getFilename() + ".TIF");
        d.setAttachment(summary.getFilePath());
        d.setNotes(summary.getSummaryNotes());

        return d;
    }

    private Collaboratore retrieveCollaborator(String collaboratorId) {
        long i = 0;
        try{
            i = Long.parseLong(collaboratorId);
        }catch(Exception e){

        }
        if (i == 0)
            return null;


        return collRep.get(i).get();
    }


    private Lavoratore constructLavoratore(DelegaMilano delega) throws Exception {
        Lavoratore l = lavSvc.findLavoratoreByFiscalCode(delega.getCodiceFiscale());
        if (l == null){

            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            //creo il lavoratore
            l = new Lavoratore();
            l.setSex("M");
            l.setName(delega.getNome());
            l.setSurname(delega.getCognome());
            l.setBirthDate(delega.getDataNascita());
            l.setFiscalcode(delega.getCodiceFiscale());
            l.setCe(delega.getCodiceLavoratore());

            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);

        }
        else{
            l.setCe(delega.getCodiceLavoratore());

            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
        }
        return l;
    }







    @Override
    public boolean existDelega(String fiscalCode, Date date) {
        Lavoratore lav = lavSvc.findLavoratoreByFiscalCode(fiscalCode);
        if (lav == null)
            return false;
        return delServ.getWorkerDelegheEdiliByDataAndEnte(lav.getLid(),date, "CASSA EDILE" ).size() > 0;
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

            if (!StringUtils.isEmpty(del.getCodiceFiscale()))
                del.setImported(existDelega(del.getCodiceFiscale(), del.getDataConferma()));
            else
                del.setImported(false);

            if (!del.isImported())
            {
                String path = retrieveFilePath(del.getFilename(), dir);
                del.setFilePath(path);
            }
            if (!del.getCodiceFiscale().isEmpty())
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


