package applica.feneal.data.hibernate.dbnazionale;


import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.WorkerRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.UtenteDBNazioneRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.framework.LoadRequest;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
@Repository
public class IscrizioniHibernateRepository extends HibernateRepository<Iscrizione> implements IscrizioniRepository {

    @Autowired
    private UtenteDBNazioneRepository utRep;

    @Autowired
    private WorkerRepository wRep;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private RegonsRepository regRep;

    @Autowired
    private ProvinceRepository provRep;

    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("anno", true));
    }

    @Override
    public Class<Iscrizione> getEntityType() {
        return Iscrizione.class;
    }

    @Override
    public List<Iscrizione> findIscrizioniByFiscalCode(String fiscalCode) {
        UtenteDbNazionale n = utRep.findUtenteByFiscalCode(fiscalCode);

        if (n == null)
            return new ArrayList<Iscrizione>();


        LoadRequest r = LoadRequest.build().filter("id_Lavoratore", n.getIid());
        return this.find(r).getRows();


    }

    @Override
    public List<Iscrizione> findIscrizioniByFiscalCodeWithQuoteDetailsMerge(String fiscalCode) {
        //recupero l'id dell'utente
        List<Iscrizione> iscrizioni = new ArrayList<>();
        UtenteDbNazionale n = utRep.findUtenteByFiscalCode(fiscalCode);

        if (n != null){
            LoadRequest r = LoadRequest.build().filter("id_Lavoratore", n.getIid());
            iscrizioni = this.find(r).getRows();

        }




        //poichè sono state aggiunte le iscrizioni dirette che non mappano direttamente sul database nazionale
        //e poichè tale informazione è riportata sull'anagrafica del lavoratore sia nel riquadro
        //dell'iscrizione nell'anno in corso che nel grafico centrale di riepilogo e nella griglia riepilogativa
        //di tutte le iscrizioni del lavoratore, la visualizzazione delle iscrizioni
        //dovra contemplare anche le iscrizionilocali non recuperate dal db nazionale. Per fare cio dovro
        //eseguire una queri sul dettaglio quote associative per il codice fiscale indicato

        //una volta recuperate tali iscrizioni ne devo eseguire una elaborazione in modo da non duplicare
        //le quote per anno e settore: ci deve essere una sola quota pere anno e settoe proprio come nel db nazionale
        //inoltre tali quote devono essere mergiate con le iscrizioni con lo stesso criterio
        Lavoratore l = wRep.find(LoadRequest.build().filter("fiscalcode", fiscalCode)).findFirst().orElse(null);

        if (l == null)
            return iscrizioni;

        List<DettaglioQuotaAssociativa> dett = dettRep.find(LoadRequest.build().filter("idLavoratore", l.getLid())).getRows();
        if (dett.size() == 0)
            return iscrizioni;
        //posso adwesso iniziare l'elaborazione.....
        //riempio la lista delle iscrizioni con eventuali dati presi dal dettaglio quota
        fillIscrizioniWithMissingData(iscrizioni, dett, l);




        return iscrizioni;
    }

    private void fillIscrizioniWithMissingData(List<Iscrizione> iscrizioni, List<DettaglioQuotaAssociativa> dett, Lavoratore l) {

        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : dett) {
            //richiedo alla quota associativa di recuperare tutti gli anni non presenti tra le iscrizioni
            //ad esempio ho n iscrizioni per provincia e settore relativi al dettalgio quota
            //che coprono diversi anni
            //settore Edile; Provincia Napoli; anno 2016
            //settore Edile; Provincia Napoli; anno 2018
            //settore Edile; Provincia Napoli; anno 2019

            //e ho una quota associativo con dta inizio nell'anno 2017 e fine nel 2020
            //devo ottenere una lista di interi che contenga gli anni non coperti dalle iscriioni
            //contenuti nel dettaglio quota associativa
            Azienda az = azRep.get(dettaglioQuotaAssociativa.getIdAzienda()).orElse(null);
            Province p  = provRep.find(LoadRequest.build().filter("description", dettaglioQuotaAssociativa.getProvincia())).findFirst().orElse(null);
            Region reg = regRep.get(p.getIdRegion()).orElse(null);

            List<Integer> yearsNotConained = dettaglioQuotaAssociativa.findMissingYearsFromIscrizioni(iscrizioni);
            for (Integer integer : yearsNotConained) {
                iscrizioni.add(dettaglioQuotaAssociativa.toIscrizioneForYear(integer, l, az, reg.getDescription(), p.getIid()));
            }



        }

    }

    @Override
    public List<Iscrizione> findIscrizioniByWorkerId(int workerId) {

        LoadRequest r = LoadRequest.build().filter("id_Lavoratore", workerId);
        return this.find(r).getRows();
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }
}