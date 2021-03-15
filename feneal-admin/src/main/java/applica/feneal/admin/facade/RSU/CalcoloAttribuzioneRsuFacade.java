package applica.feneal.admin.facade.RSU;

import applica.feneal.domain.data.core.RSU.VerbalizzazioneVotazioneRepository;
import applica.feneal.domain.model.RSU.Dto.*;
import applica.feneal.domain.model.RSU.Election.*;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import applica.feneal.domain.model.RSU.UserInterfaces.*;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.domain.model.core.RSU.VerbalizzazioneVotazione;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.feneal.services.RSU.ContrattoRsuService;
import applica.feneal.services.RSU.SedeRsuService;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class CalcoloAttribuzioneRsuFacade {

    @Autowired
    private AziendaRsuService aziendaRsuService;

    @Autowired
    private SedeRsuService sedeRsuService;

    @Autowired
    private ContrattoRsuService contrattoRsuService;

    @Autowired
    private VerbalizzazioneVotazioneRepository verVotrep;

    @Autowired
    private Security sec;

    @Autowired
    private OptionsManager options;

    public ElezioneDto setDatiGeneraliElezioneRsu(Long firmRsu, Long sedeRsu, int anno) throws Exception {
        ElezioneDto dto = new ElezioneDto();
        ElezioneRSU e = new ElezioneRSU();

        //importo i dati nel dto
        AziendaRSU a = aziendaRsuService.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),firmRsu);
        SedeRSU s = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),sedeRsu);
        if(a != null)
            dto.setAzienda(a.getDescription());

        if(s != null) {
            dto.setDivisione(s.getDescription());
        }else{
            dto.setDivisione("");
        }

        dto.setAnno(anno);

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(dto);
        //controllo che i dati generali siano corretti
        e.CheckHeaderData();

        e.CheckSolidarietaData();

        dto = e.toDto(e);

        return dto;
    }

    public ElezioneDto createAndAddListToElezioneRsu(UiElezioneDtoForListe uiElezione) {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto newDto = new ElezioneDto();

        if(uiElezione.getDto() != null)
            e.Initialize(uiElezione.getDto());

        e.addLista(uiElezione.getNome(),uiElezione.getFirmataria());
        newDto = e.toDto(e);
        return newDto;
    }

    public ElezioneDto deleteFromListToElezioneRsu(UiElezioneDtoForListe uiElezione) {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto newDto = new ElezioneDto();

        if(uiElezione.getDto() != null)
            e.Initialize(uiElezione.getDto());

        e.deleteFromLista(uiElezione.getNome());
        newDto = e.toDto(e);
        return newDto;
    }

    public ElezioneDto editListToElezioneRsu(UiElezioneDtoForListe uiElezione) {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto newDto = new ElezioneDto();

        if(uiElezione.getDto() != null)
            e.Initialize(uiElezione.getDto());

        e.editFromList(uiElezione.getOldnome(),uiElezione.getOldfirmataria(),uiElezione.getNome(),uiElezione.getFirmataria());
        e.addLista(uiElezione.getNome(),uiElezione.getFirmataria());
        newDto = e.toDto(e);
        return newDto;
    }

    public ElezioneDto setListDataCalcoloAttribuzione(UiElezioeDtoCheckListeData uiElezione) throws Exception {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto dto = setDatiGeneraliElezioneRsu(uiElezione.getFirmRsu(),uiElezione.getSedeRsu(),uiElezione.getAnno());

        dto.setListe(uiElezione.getDto().getListe());

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(dto);
        //controllo che delle liste
        e.CheckListeData();

        dto = e.toDto(e);

        return dto;

    }

    public List<UiEsitoVotazioneListe> retrieveListVotazioniCalcoloAttribuzione(List<ListaElettoraleDto> list) {
        List<UiEsitoVotazioneListe> result = new ArrayList<>();
        for (ListaElettoraleDto l : list
             ) {
            UiEsitoVotazioneListe ui = new UiEsitoVotazioneListe();
            ui.setFirmataria(l.isFirmataria());
            ui.setName(l.getName());
            ui.setValidationError(l.getValidationError());
            ui.setVoti(0);
            result.add(ui);
        }

        return result;
    }

    public ElezioneDto createVotazioniAndSetEsitiVotazioni(UiEsitoVotazione uiEsito) throws Exception {

        //come primo passaggio mi devo ricostruire il dto con le ultime informazioni
        UiElezioeDtoCheckListeData uiElezione = createUiElezioneDto(uiEsito);

        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto dto = setListDataCalcoloAttribuzione(uiElezione);

        //adesso vado ad inserire i parametri per l'esito(numero pìvotanti,votazoni, ecc..)
        
        dto.setEsiti(createAndSetEditiDtoForCalcoloAttribuzioneRsu(uiEsito));

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(dto);

        //controllo  sui dati dell'esito votazione
        e.CheckEsitoVotazioneData();

        //controllo dei contratti
        ContrattoRSU c = contrattoRsuService.getContrattoRsuById(((User) sec.getLoggedUser()).getLid(),uiEsito.getContrattoRsu());
        if(e.getEsiti().getEsitoVotazione().getRSUElegibili() < c.getRsuMin() || e.getEsiti().getEsitoVotazione().getRSUElegibili() > c.getRsuMax()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(e.getValidationError()+"</br>");
            stringBuilder.append("Attenzione! Rispettare i parametri del contratto selezionato <br>Rsu Min: "+ c.getRsuMin() +"<br>Rsu Max: "+c.getRsuMax());
            e.setValidationError(stringBuilder.toString());
        }

        dto = e.toDto(e);

        return dto;
    }



    private File createDocumentForVerbalization(String pathFile,ElezioneRSU e){
        SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy");
        File f = new File(pathFile);
        try{
            if(f.getParentFile() != null)
                f.getParentFile().mkdirs();

            f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n                    <head>\r\n                    <title>\r\n                    Elezione RSU  </title>\r\n                    </head>\r\n                    <body>");
            bw.write(String.format("<h1>Elezione RSU (Eleborato dalla Feneal UIL: %s)</h1></br>", fr.format(new Date())));
            bw.write("<p>");
            bw.write(String.format("Azienda <strong>%s</strong>;", e.getAzienda()));
            if(!StringUtils.isEmpty(e.getDivisione())){
                bw.write(String.format("Unit&agrave produttiva  <strong>%s</strong>;", e.getDivisione()));
            }
            bw.write("</p>");
            bw.write("<h2>Dati solidariet&agrave;</h2>");
            bw.write(DocumentSolidarietaData(
                    e.getSolidarieta().isCriterioSolidarietaApplicabile(),
                    e.getSolidarieta().getAccordoSolidarieta().toString(),
                    e.getSolidarieta().getPercentualeSogliaInCasoListaDominante(),
                    e.getSolidarieta().getPercentualeSogliaCategoria(),
                    e.getSolidarieta().getPercentualeSogliaInterconfederale()));
            bw.write( "<h2>Liste presentate</h2>");
            bw.write(DocumentPresentedListTable(e.getListe()));
            bw.write( "<h2>Esito votazioni</h2>");
            bw.write( "<h3>Preliminari votazioni</h3>");
            bw.write(DocumentElectionPreliminaryData(
                    e.getEsiti().getEsitoVotazione().getAventiDiritto(),
                    e.getEsiti().getEsitoVotazione().getRSUElegibili(),
                    e.getEsiti().getEsitoVotazione().getCalcoloQuozienteElettoraleConSchedeNulle()));
            bw.write("<h3>Risultati votazione</h3>");
            bw.write(DocumentElectionDataTable(e.getEsiti().getEsitoVotazione()));
            bw.write("</br>");
            bw.write("</br>");
            for(AbstractContestoAttribuzione c : e.getEsiti().getAttribuzioneRSU().getContesti()){
                bw.write(DocumentContextDataTable(c,e));
                bw.write("</br>");
            }
            bw.write("</br>");
            bw.write("</br>");
            bw.write(DocumentSummaryTable(e.getEsiti().getAttribuzioneRSU().getRiepilogoAttribuzioni(e)));
            bw.write("</body>\r\n                    </html>");
            bw.close();

        }catch (Exception ex){
            StringBuilder s = new StringBuilder();
            s.append(e.getValidationError()+"</br>");
            s.append(ex.getMessage());
            e.setValidationError(s.toString());
        }

        return f;
    }

    private String DocumentSummaryTable(List<Attribuzione> riepilogoAttribuzioni) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</hr>");
        stringBuilder.append("</hr>");
        stringBuilder.append("<h2>RIEPILOGO</h2>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Lista</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>RSU</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        for(Attribuzione attribuzione : riepilogoAttribuzioni){
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getVotazione().getLista().getNome());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getTotaleSeggi());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private String DocumentContextDataTable(AbstractContestoAttribuzione c,ElezioneRSU e) {
        if(c.getTipo().equals(CalcolatoreParametriAttribuzione.TipoParziale.AttribuzioneSolidarieta))
            return DocumentSolidarietaContextDataTable(c,e);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</hr>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr >");
        stringBuilder.append("<td colspan=\"5\">");
        String str =
                c.getTipo() != CalcolatoreParametriAttribuzione.TipoParziale.AttribuzioneProporzionale ?
                        (c.getTipo() != CalcolatoreParametriAttribuzione.TipoParziale.AttribuzioneProporzionale2_3 ?
                                String.format("%s/%s", e.getEsiti().getEsitoVotazione().getSchedeValide(), Integer.toString(c.getParametri().getrSUElegibili1_3())) : String.format("{0}/{1}", String.format("%s/%s", Integer.toString(e.getEsiti().getEsitoVotazione().getSchedeValide()), Integer.toString(c.getParametri().getrSUElegibili2_3()))))
                        : String.format("%s/%s", Integer.toString(e.getEsiti().getEsitoVotazione().getSchedeValide()),Integer.toString( e.getEsiti().getEsitoVotazione().getRSUElegibili()));
        stringBuilder.append(String.format("ESITO CALCOLO: <strong>%s</strong>!  Contesto: <strong>%s</strong>; RSU da eleggere: <strong>%s</strong>; Quoziente elettorale: <strong>%s</strong> (%s)", c.getStato().toString().toUpperCase(), c.getTipo().toString(), Integer.toString(c.getRSUEleggibili()), Double.toString(c.getQuozienteElettorale()), str));
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Lista</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Voti</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Attribuzione diretta</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Attribuzione al maggior resto</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Totale</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        for(Attribuzione attribuzione : c.getAttribuzioni()){
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getVotazione().getLista().getNome());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getVotazione().getVoti());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getAttribuzioneDiretta());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(String.format("%s (Resto:%s)", Integer.toString(attribuzione.getAttribuzioneMaggiorResto()), Double.toString(attribuzione.getQuorum().getResto())));
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getTotaleSeggi());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("<tr >");
        stringBuilder.append("<td colspan=\"5\">");
        stringBuilder.append(String.format("Totale RSU attribuite : <strong>%s</strong>;  RSU indeterminate : <strong>%s</strong>;  Note: %s", Integer.toString(c.getRSUAttribuite()),Integer.toString(c.getRsuIndeterminate()), c.getNote()));
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private String DocumentSolidarietaContextDataTable(AbstractContestoAttribuzione c, ElezioneRSU e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</hr>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr >");
        stringBuilder.append("<td colspan=\"5\">");
        String str1 = !(e.getSolidarieta().getAccordoSolidarieta().equals("Categoria")) ?
                Integer.toString(e.getSolidarieta().getPercentualeSogliaInterconfederale()) :
                (!c.getHaListaDominante() ? Integer.toString(e.getSolidarieta().getPercentualeSogliaCategoria()) : Integer.toString(e.getSolidarieta().getPercentualeSogliaInCasoListaDominante()));
        String str2 = String.format("%s/%s * %s/100", Integer.toString(e.getEsiti().getEsitoVotazione().getSchedeValide()), Integer.toString(e.getEsiti().getEsitoVotazione().getRSUElegibili()),str1);

        stringBuilder.append(String.format("ESITO CALCOLO: <strong>%s</strong>!  Contesto: <strong>Attribuzione solidariet&agrave</strong>; RSU da eleggere: <strong>%s</strong>; Soglia minima attribuzione: <strong>%s</strong>(perc. sbarramento %s % (%s))", c.getStato().toString().toUpperCase(), Integer.toString(c.getRSUEleggibili()), ( c.getHaListaDominante() ? Double.toString(c.getParametri().getSogliaDiSbarramentoListaDominante()) : Double.toString(c.getParametri().getSogliaDiSbarramento())), str1,str2));
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Lista</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Voti</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Attribuzione diretta</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Attribuzione al maggior resto</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("<td>");
        stringBuilder.append("<strong>Totale</strong>");
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        for(Attribuzione attribuzione : c.getAttribuzioni()) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getVotazione().getLista().getNome());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getVotazione().getVoti());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getAttribuzioneDiretta());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            String str3 = attribuzione.getQuorum() != null ? Double.toString(attribuzione.getQuorum().getResto()) : "0";
            stringBuilder.append(String.format("%s (Resto:%s)", Integer.toString(attribuzione.getAttribuzioneMaggiorResto()), str3));
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(attribuzione.getTotaleSeggi());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("<tr>");
        stringBuilder.append("<td colspan=\"5\">");
        stringBuilder.append(String.format("Totale RSU attribuite : <strong>%s</strong>;  RSU indeterminate : <strong>%s</strong>;  Note: %s", Integer.toString(c.getRSUAttribuite()),Integer.toString(c.getRsuIndeterminate()), c.getNote()));
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private String DocumentElectionDataTable(EsitoVotazione esiti) {
        StringBuilder s = new StringBuilder();
        s.append("<table>");
        for(Votazione v : esiti.getVotazioni()){
            s.append("<tr>");
            s.append("<td>");
            s.append(v.getLista().getNome());
            s.append("</td>");
            s.append("<td>");
            s.append(String.format("<strong/> %s </strong>", Integer.toString(v.getVoti())));
            s.append("</td>");
            s.append("</tr>");
        }
        s.append("<tr>");
        s.append("<td>");
        s.append("<strong>Schede bianche</strong>");
        s.append("</td>");
        s.append("<td>");
        s.append(String.format("<strong/> %s </strong>", Integer.toString(esiti.getSchedeBianche())));
        s.append("</td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td>");
        s.append("<strong>Schede nulle</strong>");
        s.append("</td>");
        s.append("<td>");
        s.append(String.format("<strong/> %s </strong>", Integer.toString(esiti.getSchedeNulle())));
        s.append("</td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td>");
        s.append("<strong>Totale votanti</strong>");
        s.append("</td>");
        s.append("<td>");
        s.append(String.format("<strong/> %s </strong>", Integer.toString(esiti.getVotanti())));
        s.append("</td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td>");
        s.append("<strong>Totale schede liste</strong>");
        s.append("</td>");
        s.append("<td>");
        s.append(String.format("<strong/> %s </strong>", Integer.toString(esiti.getSchedeListe())));
        s.append("</td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td>");
        s.append("<strong>Totale schede valide</strong>");
        s.append("</td>");
        s.append("<td>");
        s.append(String.format("<strong/> %s </strong>", Integer.toString(esiti.getSchedeValide())));
        s.append("</td>");
        s.append("</tr>");
        s.append("</table>");
        return s.toString();
    }

    private String DocumentElectionPreliminaryData(int aventiDiritto, int rsuElegibili, boolean calcoloQuozienteElettoraleConSchedeNulle) {
        String s1 = String.format("<p>Aventi diritto: <strong>%s</strong>; RSU da eleggere: <strong>%s</strong>;</p>", Integer.toString(aventiDiritto), Integer.toString(rsuElegibili));
        String s2 = String.format("Ai fini del calcolo dei quoziozienti elettorali le schede nulle %s saranno tenute in conto", calcoloQuozienteElettoraleConSchedeNulle ? " " : "non");
        return  s1 + s2;
    }

    private String DocumentPresentedListTable(List<ListaElettorale> liste) {
        StringBuilder s = new StringBuilder();
        s.append("<table><tr><td><strong>Lista</strong></td><td><strong>Firmataria contratto</strong></td></tr>");
        for(ListaElettorale l : liste){
            s.append("<tr><td>");
            s.append(l.getNome());
            s.append("</td><td>");
            s.append(l.getFirmatariaCCNL() ? "S&igrave" : "No");
            s.append("</td></tr>");
        }
        s.append("</table>");
        return s.toString();
    }

    private String DocumentSolidarietaData(boolean applySolidarieta, String agreementType, int percentageDomailList, int percentageCategory, int percentageConfederal) {
        if (!applySolidarieta)
            return "<p>Nessun accordo di solidarietà applicato<p>";
        String str1 = String.format("Viene applicato il patto di solidariet&agrave con l'accordo %s. \r\n                                        Tale accordo prevede una soglia di sbarramento del %s %; ", agreementType == "Categoria" ?  "di Categoria" :  agreementType, (agreementType == "Categoria" ? Integer.toString(percentageCategory) : Integer.toString(percentageConfederal)));
        String str2 = "";
        if (agreementType == "Categoria")
            str2 = String.format("Nel caso di lista dominante (unica lista ad aver preso tutti  i 2/3 dei seggi disponibili) l'accordo prevede una soglia di sbarramento del %s %;", Integer.toString(percentageDomailList));
        return String.format("<p>%s%s</p>", str1, str2);
    }


    private EsitiDto createAndSetEditiDtoForCalcoloAttribuzioneRsu(UiEsitoVotazione uiEsito) {
        EsitiDto e = new EsitiDto();

        EsitoVotazioneDto esitoVotazioneDto = new EsitoVotazioneDto();

        esitoVotazioneDto.setAventiDiritto(uiEsito.getAventiDiritto());
        esitoVotazioneDto.setCalcoloQuozienteElettoraleConSchedeNulle(uiEsito.isAddSchedeNulle());
        esitoVotazioneDto.setrSUElegibili(uiEsito.getRsuEleggibili());
        esitoVotazioneDto.setSchedeBianche(uiEsito.getSchedeBianche());
        esitoVotazioneDto.setSchedeNulle(uiEsito.getSchedeNulle());
        esitoVotazioneDto.setValidationError("");

        List<VotazioneDto> voti = new ArrayList<>();
        for(UiEsitoVotazioneListe l : uiEsito.getListeVotazione()){
            VotazioneDto votazione = new VotazioneDto();

            ListaElettoraleDto dto = new ListaElettoraleDto();
            dto.setName(l.getName());
            dto.setFirmataria(l.isFirmataria());
            dto.setValidationError(l.getValidationError());

            if(dto != null){
                votazione.setLista(dto);
                votazione.setVoti(l.getVoti());
            }

            voti.add(votazione);
        }

        esitoVotazioneDto.setVotazioni(voti);
        e.setEsitoVotazione(esitoVotazioneDto);
        return e;
    }

    private UiElezioeDtoCheckListeData createUiElezioneDto(UiEsitoVotazione uiEsito) throws Exception {
        UiElezioeDtoCheckListeData uiElezione = new UiElezioeDtoCheckListeData();
        uiElezione.setAnno(uiEsito.getAnno());
        uiElezione.setFirmRsu(uiEsito.getFirmRsu());
        uiElezione.setSedeRsu(uiEsito.getSedeRsu());
        uiElezione.setDto(setElezioneDtoFromUiEsitoVotazioni(uiEsito));

        return uiElezione;
    }

    private ElezioneDto setElezioneDtoFromUiEsitoVotazioni(UiEsitoVotazione uiEsito) {
        ElezioneDto dto = new ElezioneDto();
        List<ListaElettoraleDto> list = new ArrayList<>();

        for (UiEsitoVotazioneListe l: uiEsito.getListeVotazione()) {
            ListaElettoraleDto ll = new ListaElettoraleDto();
            ll.setFirmataria(l.isFirmataria());
            ll.setName(l.getName());
            ll.setValidationError(l.getValidationError());

            list.add(ll);
        }


        dto.setListe(list);

        return dto;
    }

    public ElezioneDto stampaAttribuzioneRSU(UiVerbalizzaVotazioneDto uiEsito) throws Exception {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto dto = new ElezioneDto();

        SedeRSU s = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),uiEsito.getSedeRsu());

        if(s != null) {
            uiEsito.getDto().setDivisione(s.getDescription());
        }else{
            uiEsito.getDto().setDivisione("");
        }

        uiEsito.getDto().setAnno(uiEsito.getAnno());

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(uiEsito.getDto());
        if(e.CheckEsitoVotazioneData()){
            e.CalcolaEsitoAttribuzioneRSU();
            if(e.getEsiti().getAttribuzioneRSU() != null){
                stampaAttribuzioneRsu(e);
            }else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(e.getValidationError()+"</br>");
                stringBuilder.append("Errore: errore nel processo di stampa");
                e.setValidationError(stringBuilder.toString());
            }
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(e.getValidationError() + "</br>");
            stringBuilder.append("Errore nel calolare l'attribuzione RSU");
        }

        dto = e.toDto(e);


        return dto;
    }

    public ElezioneDto saveAttribuzioneRsu(UiVerbalizzaVotazioneDto uiVerb) throws Exception {
        //controllo che tutti i dati siano
        //corretti

        //procedura per il creare e calcolare l'esito
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto dto;

        uiVerb.getDto().setAnno(uiVerb.getAnno());
        SedeRSU s = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),uiVerb.getSedeRsu());
        uiVerb.getDto().setDivisione( s != null ? s.getDescription() : "");

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(uiVerb.getDto());
        if(e.CheckEsitoVotazioneData()){
            e.CalcolaEsitoAttribuzioneRSU();
            if(e.getEsiti().getAttribuzioneRSU() != null){
                //arrivato a questo punto mi creo tutto cio che mi servirebbe per salvare la varbalizzazione
                //quindi mi vado a creare il file dell'esito e lo salvo da qualche parte;


                File verbalizzazione = createAndSaveVerbDocumento(e);

                VerbalizzazioneVotazione verb = createAndSaveVerbVotazione(verbalizzazione,uiVerb);

                if(verb != null)
                    verVotrep.save(verb);
            }else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(e.getValidationError()+"</br>");
                stringBuilder.append("Errore: errore nel processo di stampa");
                e.setValidationError(s.toString());
            }
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(e.getValidationError() + "</br>");
            stringBuilder.append("Errore nel calolare l'attribuzione RSU");
        }

        dto = e.toDto(e);
        return dto;

    }

    private VerbalizzazioneVotazione createAndSaveVerbVotazione(File f, UiVerbalizzaVotazioneDto uiVerb) {
        VerbalizzazioneVotazione v = new VerbalizzazioneVotazione();


        AziendaRSU a = aziendaRsuService.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),uiVerb.getFirmRsu());
        SedeRSU s = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),uiVerb.getSedeRsu());
        ContrattoRSU c = contrattoRsuService.getContrattoRsuById(((User) sec.getLoggedUser()).getLid(),uiVerb.getContrattoRsu());

        v.setDataVotazione(new Date());
        v.setAziendaRsu(a);
        v.setSedeRsu(s);
        v.setContrattoRSU(c);
        v.setNote(uiVerb.getNote() != null? uiVerb.getNote() : "");

        if(f.exists()){
            v.setRisultatoVotazione("files/verbalizzazioniEsito/documenti/"+f.getName());
            v.setNomerisultatoVotazione(f.getName());
        }
        if(!StringUtils.isEmpty(uiVerb.getVerbalizzazione())){
            v.setNomeverbalizzazione(uiVerb.getNomeverbalizzazione());
            v.setVerbalizzazione(uiVerb.getVerbalizzazione());
        }
        return v;
    }

    private File createAndSaveVerbDocumento(ElezioneRSU e) {
        SimpleDateFormat fr = new SimpleDateFormat("dd_MM_yyyy");

        String sector = !StringUtils.isEmpty(e.getDivisione()) ? e.getDivisione()+"_" : "";

        String pathFile = options.get("applica.fenealquote.logfolder")+
                "/files/verbalizzazioniEsito/documenti/verb_"+
                e.getAzienda()+"_"+ sector +
                fr.format(new Date())+".html";

        File f = new File(pathFile);

        if(f.exists() && !f.isDirectory()){
            pathFile=options.get("applica.fenealquote.logfolder")+"/files/verbalizzazioniEsito/documenti/verb_"+e.getAzienda()+"_"+fr.format(new Date())+"_"+ UUID.randomUUID().toString()+".html";
        }

        f = createDocumentForVerbalization(pathFile,e);
        return f;
    }

    private void stampaAttribuzioneRsu(ElezioneRSU e) throws IOException {
        String pathFile = "esito.html";
        File f = createDocumentForVerbalization(pathFile,e);
        Desktop.getDesktop().browse(f.toURI());
    }
}
