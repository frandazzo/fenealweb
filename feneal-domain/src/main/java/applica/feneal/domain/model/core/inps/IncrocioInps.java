package applica.feneal.domain.model.core.inps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IncrocioInps {


    public IncrocioInps(RistornoInps ristorno ,List<InpsFile> files, List<QuotaInps> quote){
        this.ristorno = ristorno;
        this.quote = quote;
        this.files = files;
    }

    private RistornoInps ristorno;
    private List<InpsFile> files;
    private List<QuotaInps> quote;


    public List<QuotaInps> getQuotePagate(){
        return quote.stream()
                .filter(a -> a.getRistorno() != null &&
                        a.getRistorno().getLid() == ristorno.getLid()).collect(Collectors.toList());
    }


    public List<PagamentoReferenti> getListaReferenti(){
        List<PagamentoReferenti> result = new ArrayList<>();

        //dalle quote pagate recupero il totale degli importi ristornati
        //per referente
        //per farlo ho bisogno della lista dei referenti
        List<QuotaInps>  quote = getQuotePagate();
        List<String> s = quote.stream().map( a -> a.getReferente()).collect(Collectors.toList());
        Set<String> uniquereferenti = new HashSet<String>(s);







        for (String referente : uniquereferenti) {


            List<String> s1 = quote.stream()
                    .filter(a -> a.getReferente().equals(referente))
                    .map( a -> a.getLavoratore().getFiscalcode()).collect(Collectors.toList());
            Set<String> uniqueLavs = new HashSet<String>(s1);

            List<String> s11 = quote.stream()
                    .filter(a -> a.getReferente().equals(referente))
                    .filter(a->a.isLavoratoreEdile()).map( a -> a.getLavoratore().getFiscalcode()).collect(Collectors.toList());
            Set<String> uniqueLavsEdili = new HashSet<String>(s11);


            List<String> s12 = quote.stream()
                    .filter(a -> a.getReferente().equals(referente))
                    .filter(a->!a.isLavoratoreEdile()).map( a -> a.getLavoratore().getFiscalcode()).collect(Collectors.toList());
            Set<String> uniqueLavsNonEdili = new HashSet<String>(s12);





            PagamentoReferenti ref = new PagamentoReferenti();
            ref.setReferente(referente);
            ref.setNumeroLavoratori(uniqueLavs.size());
            ref.setNumeroLavoratoriEdili(uniqueLavsEdili.size());
            ref.setNumeroLavoratoriNonEdili(uniqueLavsNonEdili.size());




            ref.setTotale(round(quote.stream()
                    .filter(a -> a.getReferente().equals(referente))
                    .mapToDouble(a -> a.getImporto()).sum(), 2));
            ref.setTotaleRistornato(round(quote.stream()
                    .filter(a -> a.getReferente().equals(referente))
                    .mapToDouble(a -> a.getImportRistornato()).sum(), 2));



            double costoTessere = ristorno.getCostoTessera() * ref.getNumeroLavoratori();
            double costoTessereEdili = ristorno.getCostoTessera() * ref.getNumeroLavoratoriEdili();
            double costoTessereNonEdili = ristorno.getCostoTessera() * ref.getNumeroLavoratoriNonEdili();

            ref.setRistornoConCostoTessere(round(ref.getTotaleRistornato() - costoTessere, 2));
            ref.setRistornoConCostoTessereSoloEdili(round(ref.getTotaleRistornato() - costoTessereEdili, 2));
            ref.setRistornoConCostoTessereSoloNonEdili(round(ref.getTotaleRistornato() - costoTessereNonEdili, 2));


            result.add(ref);
        }
        return result;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    public RistornoInps getRistorno() {
        return ristorno;
    }

    public List<InpsFile> getFiles() {
        return files;
    }

    public List<QuotaInps> getQuote() {
        return quote;
    }


}
