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
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 04/02/2017.
 */
@Component
public class AnomaliaImporter implements  IImportDelegaBari {

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


        delSvc.writeToFile(f, "*******Entro in AnomaliaImporter per il lavoratore " + l.getSurnamename() );

        //devo verificare se esiste una delega nella data
        // indicata e se esiste revoco con l'opportuna causale
        //altrimenti creo e revoco
        LoadRequest req = LoadRequest.build().filter("description", "ISCRIZIONE");
        CausaleIscrizioneDelega c = causaleIscrizioneDelegaRep.find(req).findFirst().orElse(null);


        LoadRequest req1 = LoadRequest.build().filter("description", "ANOMALIA");
        CausaleRevoca c1 = causaleRevocaRep.find(req1).findFirst().orElse(null);





        List<Delega> d = delSvc.getWorkerDelegheEdiliByDataAndEnte(l.getLid(),summary.getProtocolDate(), Paritethic.ente_cassaedile);

        if (d.size()  == 0){
            delSvc.writeToFile(f, "*******Non ho trovato deleghe in data protocollo. procedo alla creazione e revoca come anomalia" );

            //qui creo la delega ex novo
            Delega d3 = CreateDelega(l, c, summary);
            delSvc.revokeDelega(d3.getDocumentDate(), d3,c1);

            return;
        }

        //le revoco tutte
        delSvc.writeToFile(f, "*******Trovate " + d.size()   + " deleghe . Provvedo alla loro revoca (se revocabili) con causale inefficae");delSvc.writeToFile(f, "*******Non ho trovato deleghe in data protocollo. procedo alla creazione e revoca inefficace" );

        for (Delega delega : d) {
            delega.setSubscribeReason(c);
            delega.setNotes(summary.getAnomalia());
            delSvc.revokeDelega(delega.getDocumentDate(), delega,c1);

        }



    }

    public Delega CreateDelega(Lavoratore l, CausaleIscrizioneDelega c,ImportDelegheSummaryForBari summary) {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(summary.getProtocolDate());

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");

        d.setImportGuid(s.format(new Date()));

        d.setWorker(l);
        d.setProvince(geo.getProvinceByName("BARI"));
        d.setSubscribeReason(c);
        d.setNotes(summary.getAnomalia());

        LoadRequest req = LoadRequest.build().filter("type", Sector.sector_edile);
        d.setSector(sectorRep.find(req).findFirst().orElse(null));

        LoadRequest req1 = LoadRequest.build().filter("type", Paritethic.ente_cassaedile);
        d.setParitethic(paritheticRepository.find(req1).findFirst().orElse(null));


        return d;
    }
}
