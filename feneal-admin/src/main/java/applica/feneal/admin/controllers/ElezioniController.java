package applica.feneal.admin.controllers;

import applica.feneal.domain.model.RSU.Election.*;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ElezioniController {


    @RequestMapping(value = "/elezione", method = RequestMethod.GET)
    public String tryElectionRsu(Model model) throws Exception {

        boolean nextPage = false;


        ElezioneRSU e = new ElezioneRSU();

//        if(e.CheckHeaderData()){
//            nextPage = true;
//        }else{
//            nextPage = false;
//        }

        if(e.CheckSolidarietaData()){
            nextPage = true;
        }else {
            nextPage = false;
        }


        if(e.CheckListeData()){
            nextPage = true;
        }else {
            nextPage = false;
        }

        if(e.CheckEsitoVotazioneData()){
            nextPage = true;
        }else {
            nextPage = false;
        }

        if(nextPage == true) {
            e.CalcolaEsitoAttribuzioneRSU();

            SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy");

            File f = new File("esito.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n                    <head>\r\n                    <title>\r\n                    Elezione RSU  </title>\r\n                    </head>\r\n                    <body>");
            bw.write(String.format("<h1>Elezione RSU (Eleborato dalla Feneal UIL: %s)</h1></br>", fr.format(new Date())));
            bw.write("<p>");
            bw.write(String.format("Azienda <strong>%s</strong>;", e.getAzienda()));
            if(!StringUtils.isEmpty(e.getDivisione())){
                bw.write(String.format("Unit&agrave produttiva  <strong>{0}</strong>;", e.getDivisione()));
            }
            bw.write("</p>");
            bw.write("<h2>Dati solidariet&agrave</h2>");
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

            Desktop.getDesktop().browse(f.toURI());



        }else{
            return "errore";
        }


        return "ok";
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
        String s1 = String.format("<p>Aventi diritto: <strong> %s </strong>; RSU da eleggere: <strong> %s </strong></p>", Integer.toString(aventiDiritto), Integer.toString(rsuElegibili));
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

}
