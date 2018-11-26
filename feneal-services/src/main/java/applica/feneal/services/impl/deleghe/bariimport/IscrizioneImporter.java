package applica.feneal.services.impl.deleghe.bariimport;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDelegheSummaryForBari;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
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
public class IscrizioneImporter implements IImportDelegaBari {

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


    @Override
    public void importDelega(Lavoratore l, ImportDelegheSummaryForBari summary, File f) throws Exception {


        delSvc.writeToFile(f, "*******Entro in IscrizioneImporter per il lavoratore " + l.getSurnamename() );

        //devo verificare se esiste una delega nella data indicata e se non esiste la creo e l'accetto
        LoadRequest req = LoadRequest.build().filter("description", "ISCRIZIONE");
        CausaleIscrizioneDelega c = causaleIscrizioneDelegaRep.find(req).findFirst().orElse(null);

        List<Delega> d = delSvc.getWorkerDelegheEdiliByDataAndEnte(l.getLid(),summary.getProtocolDate(), Paritethic.ente_cassaedile);

        //se tra le deleghe c'è ne' una attiva o accettata esco
        //se ce nè una sottoscritta o inoltrata l'accetto

        //altrimenti ne creo una nuova e l'accetto
        Delega d1 = d.stream().filter(a -> a.getState() == Delega.state_activated || a.getState() == Delega.state_accepted).findFirst().orElse(null);

        if (d1 != null)
        {
            delSvc.writeToFile(f, "*******Trovata delega attiva o accettata " );

            //aggiorno eventualmente la causale Iscrizione
            //la causale iscrizione non può essere unlla se no non arrivavo qui!!!!!
            d1.setSubscribeReason(c);
            delSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(), d1);
            return;
        }

        Delega d2 = d.stream().filter(a -> a.getState() == Delega.state_sent || a.getState() == Delega.state_subscribe).findFirst().orElse(null);
        if (d2 != null)
        {
            delSvc.writeToFile(f, "*******Trovata delega inoltrata o sottoscritta. provvedo all'accettazione" );
            //aggiorno eventualmente la causale Iscrizione
            //la causale iscrizione non può essere unlla se no non arrivavo qui!!!!!
            d2.setSubscribeReason(c);
            delSvc.acceptDelega(d2.getDocumentDate(), d2);

            return;
        }
        delSvc.writeToFile(f, "*******Nessuna delega trovata. provvedo alla creazione" );
        //qui creo la delega ex novo
        Delega d3 = CreateDelega(l, c, summary);
        delSvc.acceptDelega(d3.getDocumentDate(), d3);


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
        if (!StringUtils.isEmpty(summary.getAzienda())){
            d.setNotes(String.format("Azienda: %s; Protocollo: %s",summary.getAzienda(), summary.getProtocol()));

        }else{
            d.setNotes(String.format("Protocollo: %s", summary.getProtocol()));

        }

        LoadRequest req = LoadRequest.build().filter("type", Sector.sector_edile);
        d.setSector(sectorRep.find(req).findFirst().orElse(null));

        LoadRequest req1 = LoadRequest.build().filter("type", Paritethic.ente_cassaedile);
        d.setParitethic(paritheticRepository.find(req1).findFirst().orElse(null));


        return d;
    }
}
