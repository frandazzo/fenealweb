package applica.feneal.services.impl.deleghe.bariimport;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.RevocationReasonRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDelegheSummaryForBari;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 04/02/2017.
 */
@Component
public class DoppioneImporter implements IImportDelegaBari {

    @Autowired
    private DelegheService delSvc;

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRep;

    @Autowired
    private Security sec;

    @Autowired
    private GeoService geo;

    @Autowired
    private SectorRepository sectorRep;

    @Autowired
    private ParitheticRepository paritheticRepository;

    @Autowired
    private RevocationReasonRepository causaleRevocaRep;


    @Override
    public void importDelega(Lavoratore l, ImportDelegheSummaryForBari summary, File f) throws Exception {

        delSvc.writeToFile(f, "*******Entro in DoppioneImporter per il lavoratore " + l.getSurnamename() );


        //devo verificare se esiste una delega nella data di decorrenza della delega
        // indicata e se non esiste la creo e laccetto
        // se esiste ne creo una nuova in data protocollo ammesso che no9n esista gia
        // //e ovviamente deve essere revvocata con l'opportuna causale

        LoadRequest req = LoadRequest.build().filter("description", "ISCRIZIONE");
        CausaleIscrizioneDelega c = causaleIscrizioneDelegaRep.find(req).findFirst().orElse(null);


        LoadRequest req1 = LoadRequest.build().filter("description", summary.getDoubleDel());
        CausaleRevoca c1 = causaleRevocaRep.find(req1).findFirst().orElse(null);


        Date dataDecorrenza = createDate(summary);


        //recupero una tra le delghe che potrebbero essere efficaci
        //per eventualmente accettarle...

        Delega d = delSvc.getWorkerDelegheEdiliByDataAndEnte(l.getLid(),dataDecorrenza, Paritethic.ente_cassaedile).stream()
                .filter(a -> a.getState() == Delega.state_accepted ||
                             a.getState() == Delega.state_activated ||
                             a.getState() == Delega.state_subscribe||
                             a.getState() == Delega.state_sent)
                .findFirst().orElse(null);


        //passo all'accettazione
        SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy");
        if (d == null){
            //qui creo la delega ex novo
            delSvc.writeToFile(f, "*******Non ho trovato deleghe efficaci in data decorrenza ( " + ff.format(dataDecorrenza) + " ). procedo alla creazione della delega come delega accettata" );
            d = CreateDelega(l, c, summary, dataDecorrenza);

        }else{
            //se ho trovato la delega mi assicuro di accettarla
            delSvc.writeToFile(f, "*******Ho trovato una delega efficace in data decorrenza ( " + ff.format(dataDecorrenza) + " ). procedo alla possibile accettazione" );

        }
        delSvc.acceptDelega(dataDecorrenza, d);
        //********************************************
        //**********************************************
        //adesso sono sicuro che esiste una delega accettata

        //devo ora verificare che esistano una delega
        //in data protocollo da revocare tutte

        List<Delega> d1 = delSvc.getWorkerDelegheEdiliByDataAndEnte(l.getLid(),summary.getProtocolDate(), Paritethic.ente_cassaedile);

        if (d1.size() > 0){
            delSvc.writeToFile(f, "*******Trovate deleghe in data protocollo da revocare come doppioni ");

            //le revoco tutteù
            for (Delega delega : d1) {
                delSvc.revokeDelega(summary.getProtocolDate(),delega,c1);
            }

            return;
        }
        //a questo punto non esiste nessun doppione
        //lo creo e provvedo alla sua revoca
        delSvc.writeToFile(f, "*******Nessuna delega in data protocollo creata. Procedo alla creazione e revoca come doppione");
        Delega dd = CreateDelegaAsDoppione(l, c, summary);
        delSvc.revokeDelega(summary.getProtocolDate(), dd, c1);




    }

    private Date createDate(ImportDelegheSummaryForBari summary) {

        //la decorrenza è nel formato 2007/10 e sta per 01/10/2007

        //dove il mese è o 04 oppure 10  (aprile o ottobre

        String[] decorrenza = summary.getDecorrenza().split("/");
        String anno = decorrenza[0];
        String mese = decorrenza[1];


        int an = Integer.parseInt(anno);

        int me = mese.equals("04") ? 3 : 9;

        Calendar c = Calendar.getInstance();
        c.set(an, me, 1, 0, 0,0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }


    public Delega CreateDelega(Lavoratore l, CausaleIscrizioneDelega c,ImportDelegheSummaryForBari summary, Date dataDecorrenza) {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(dataDecorrenza);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");

        d.setImportGuid(s.format(new Date()));

        d.setWorker(l);
        d.setProvince(geo.getProvinceByName("BARI"));
        d.setSubscribeReason(c);

        LoadRequest req = LoadRequest.build().filter("type", Sector.sector_edile);
        d.setSector(sectorRep.find(req).findFirst().orElse(null));

        LoadRequest req1 = LoadRequest.build().filter("type", Paritethic.ente_cassaedile);
        d.setParitethic(paritheticRepository.find(req1).findFirst().orElse(null));


        return d;
    }

    public Delega CreateDelegaAsDoppione(Lavoratore l, CausaleIscrizioneDelega c,ImportDelegheSummaryForBari summary) {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(summary.getProtocolDate());

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");

        d.setImportGuid(s.format(new Date()));

        d.setWorker(l);
        d.setProvince(geo.getProvinceByName("BARI"));
        d.setSubscribeReason(c);

        LoadRequest req = LoadRequest.build().filter("type", Sector.sector_edile);
        d.setSector(sectorRep.find(req).findFirst().orElse(null));

        LoadRequest req1 = LoadRequest.build().filter("type", Paritethic.ente_cassaedile);
        d.setParitethic(paritheticRepository.find(req1).findFirst().orElse(null));


        return d;
    }
}

