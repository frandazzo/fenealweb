package applica.feneal.domain.model.core.deleghe.bari;

import java.util.*;
import java.util.stream.Collectors;

public class StatoDelegheFactory {


    public static List<StatoDelega> createDelegheStes(List<DelegaBari> deleghe){

        Hashtable<Long, StatoDelega> states = new Hashtable<>();

        for (DelegaBari delegaBari : deleghe) {
            if (states.containsKey(delegaBari.getWorker().getLid())){
                states.get(delegaBari.getWorker().getLid()).addDelega(delegaBari);
            }else{
                StatoDelega d = new StatoDelega();
                d.addDelega(delegaBari);
                states.put(delegaBari.getWorker().getLid(), d);
            }
        }

        return Collections.list(states.elements());

    }

    public static List<ProiezioneDelegheBari> createDelegheProjections(List<StatoDelega> prevDeleghe, List<DelegaBari> lastDeleghe, ProjectionDelegheFilter filter){

        List<StatoDelega> prevStates = filterData(prevDeleghe, filter);










        List<StatoDelega> lastStates = createDelegheStes(lastDeleghe);

        List<ProiezioneDelegheBari> result = new ArrayList<>();

        for (StatoDelega prevState : prevStates) {
            result.add(new ProiezioneDelegheBari(prevState,
                    lastStates.stream().filter(a -> a.getWorker().getLid() == prevState.getWorker().getLid())
                            .findFirst().orElse(null))
            );
        }

        return result;
    }

    private static List<StatoDelega> filterData(List<StatoDelega> prevDeleghe, ProjectionDelegheFilter filter) {
        if (!(filter.getIscrizioniCongelate() ||
                filter.getIscrizioniInefficaci() ||
                filter.getIscrizioniIRiconferma() ||
                filter.getRevocheBianche() ||
                filter.getRevocheDeleghe())){
            return prevDeleghe;
        }

        List<StatoDelega> iscrInefficaci = new ArrayList<>();
        List<StatoDelega> iscrCongelate = new ArrayList<>();
        List<StatoDelega> iscrIRiconfema = new ArrayList<>();
        List<StatoDelega> delBianche = new ArrayList<>();
        List<StatoDelega> delRevoche = new ArrayList<>();


        if (filter.getIscrizioniInefficaci())
            iscrInefficaci = prevDeleghe.stream().filter(a -> a.getLastAction()
                    .equals(DelegaBari.IscrizioneInefficace)).collect(Collectors.toList());
        if (filter.getIscrizioniCongelate())
            iscrCongelate = prevDeleghe.stream().filter(a -> a.getLastAction()
                    .equals(DelegaBari.IscrizioneCongelata)).collect(Collectors.toList());
        if (filter.getIscrizioniIRiconferma())
            iscrIRiconfema = prevDeleghe.stream().filter(a -> a.getLastAction()
                    .equals(DelegaBari.IscrizioneIRiconferma)).collect(Collectors.toList());
        if (filter.getRevocheDeleghe())
            delRevoche = prevDeleghe.stream().filter(a -> a.getLastAction()
                    .equals(DelegaBari.RevocaDelega)).collect(Collectors.toList());
        if (filter.getRevocheBianche())
            delBianche = prevDeleghe.stream().filter(a -> a.getLastAction()
                    .equals(DelegaBari.RevocaBianca)).collect(Collectors.toList());

        List<StatoDelega> listFinal = new ArrayList<StatoDelega>();
        listFinal.addAll(iscrInefficaci);
        listFinal.addAll(iscrCongelate);
        listFinal.addAll(iscrIRiconfema);
        listFinal.addAll(delBianche);
        listFinal.addAll(delRevoche);

        Collections.sort(listFinal, new Comparator<StatoDelega>() {
            @Override
            public int compare(StatoDelega o1, StatoDelega o2) {
                if (o1.getLastProtocolDate().getTime() > o2.getLastProtocolDate().getTime())
                    return 1;
                if (o1.getLastProtocolDate().getTime() < o2.getLastProtocolDate().getTime())
                    return -1;
                return 0;
            }
        });

        return listFinal;
    }


}
