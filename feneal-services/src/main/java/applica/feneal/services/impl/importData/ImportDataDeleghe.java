package applica.feneal.services.impl.importData;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by fgran on 09/05/2017.
 */
@Component
public class ImportDataDeleghe {

    @Autowired
    private DelegheService delSvc;

    @Autowired
    private ImportCausaliService importCausaliService;

    @Autowired
    private GeoService geo;


    public void activateDelega(RiepilogoQuoteAssociative riepilogo, DettaglioQuotaAssociativa dettaglioQuotaAssociativa, boolean createDelegaIfNotExist, boolean associateDelega) throws Exception {

        //variabile per verificare se la delega è stata inserita precedentemente...
        //in modo da evitare un aggiornamento della quota associativa

        boolean delegaAlreadyExist = false;

        if (createDelegaIfNotExist || associateDelega){

            //recupero la delega
            Delega d =delSvc.retrieveActivableWorkerDelega(dettaglioQuotaAssociativa.getIdLavoratore(),geo.getProvinceByName( dettaglioQuotaAssociativa.getProvincia()).getIid(),
                    dettaglioQuotaAssociativa.getSettore().toUpperCase(), dettaglioQuotaAssociativa.getEnte(), dettaglioQuotaAssociativa.getIdAzienda() );


            if (dettaglioQuotaAssociativa.getIdLavoratore() > 0){
                if (d == null){

                    if (createDelegaIfNotExist){

                        //creo la delega
                        d = createDelegaFromDettaglioQuota(dettaglioQuotaAssociativa, riepilogo);
                        delSvc.saveOrUpdate(0, d);
                    }
                }else{
                    //la delega già esisteva e quindi posso aggiornare la quota
                    //se la devo associare alla dleega
                    delegaAlreadyExist = true;
                }



                if (associateDelega){
                    if (d != null){
                        //la attivo

                        //ho adesso una potenziale delega da attivare
                        //se è attivata non faccio nulla
                        //se è accettata attivo
                        //se è inoltrata o sottoscritta prima accetto e poi attivo
                        if (d.getState() == Delega.state_subscribe || d.getState() == Delega.state_sent){

                            delSvc.acceptDelega(new Date(), d);
                            delSvc.activateDelega(new Date(), d);
                        }else if (d.getState() == Delega.state_accepted ){

                            delSvc.activateDelega(new Date(), d);
                            /*if (delegaAlreadyExist){
                                //solo se la delega gia essiteva posso inserire i dati di associazione
                                //dleega quota...
                                dettaglioQuotaAssociativa.setDelegaMansione(d.getMansione());
                                dettaglioQuotaAssociativa.setDelegaLuogoLavoro(d.getLuogoLavoro());
                                dettaglioQuotaAssociativa.setDelegaNote(d.getNotes());
                                if (d.getCollaborator() != null)
                                    dettaglioQuotaAssociativa.setDelegaCollaboratore(d.getCollaborator().getDescription());
                                dettRep.save(dettaglioQuotaAssociativa);
                            }*/
                        }
                    }
                }
            }
        }
    }

    private Delega createDelegaFromDettaglioQuota(DettaglioQuotaAssociativa dettaglioQuotaAssociativa, RiepilogoQuoteAssociative riepilogo) {

        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(new Date());
        d.setImportGuid(riepilogo.getGuid());

        Lavoratore l = new Lavoratore();
        l.setId(dettaglioQuotaAssociativa.getIdLavoratore());
        d.setWorker(l);


        d.setProvince(geo.getProvinceByName(riepilogo.getProvincia()));
        importCausaliService.createIfNotExistCausaleIscrizioneDelega("NUOVA ISCRIZIONE");
        d.setSubscribeReason( importCausaliService.getCausaleIscrizione("NUOVA ISCRIZIONE"));

        d.setSector(importCausaliService.getSettore(riepilogo.getSettore()));

        if (d.getSector() != null && d.getSector().getType().equals(Sector.sector_edile)){
            //carico l'ente
            d.setParitethic(importCausaliService.getEnte(riepilogo.getEnte()));
        }else{
            //carico l'azienda trattandosi di impianti fissi
            Azienda a = null;

            if (dettaglioQuotaAssociativa.getIdAzienda() > 0){
                a = new Azienda();
                a.setId(dettaglioQuotaAssociativa.getIdAzienda());
            }

            d.setWorkerCompany(a);
        }

        return d;

    }




}
