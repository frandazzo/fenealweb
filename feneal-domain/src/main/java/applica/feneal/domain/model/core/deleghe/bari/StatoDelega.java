package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.*;

public class StatoDelega {




    public String getLavoratoreCodiceFiscale(){
        if (added)
            recalculateLastState();
        return worker.getFiscalcode();
    }

    public long getLavoratoreId(){
        if (added)
            recalculateLastState();
        return worker.getLid();
    }

    public int getContributeToBalace() {
        if (added)
            recalculateLastState();
        return contributeToBalace;
    }

    public int getNumDeleghe(){
        return deleghe.size();
    }

    public Lavoratore getWorker() {
        if (added)
            recalculateLastState();
        return worker;
    }

    public String getLastMovement() {
        if (added)
            recalculateLastState();
        return lastMovement;
    }

    public Azienda getLastAzienda() {
        if (added)
            recalculateLastState();
        return lastAzienda;
    }

    public Date getLastProtocolDate() {
        if (added)
            recalculateLastState();
        return lastProtocolDate;
    }

    public String getLastAction() {
        if (added)
            recalculateLastState();
        return lastAction;
    }

    public Date getLastEffectDate() {
        if (added)
            recalculateLastState();
        return lastEffectDate;
    }


    public boolean isRetrieveDelegaBianca() {
        if (added)
            recalculateLastState();
        return retrieveDelegaBianca;
    }

    public String getReferente(){
        if (added)
            recalculateLastState();
        return referente;
    }


    public String getState() {
        if (added)
            recalculateLastState();
        return state;
    }

    private void recalculateLastState() {

        //ordino la lista per eventi in senso decrescente per data protocollo
        Collections.sort(deleghe, new Comparator<DelegaBari>() {
            @Override
            public int compare(DelegaBari o1, DelegaBari o2) {

                if (o1.getProtocolDate().getTime() > o2.getProtocolDate().getTime())
                    return -1;
                if (o1.getProtocolDate().getTime() < o2.getProtocolDate().getTime())
                    return 1;
                return 0;
            }
        });

        //a queto punto la prima delega è l'ultima in termini temporali
        DelegaBari d = deleghe.get(0);
        if (d != null) {
            worker = d.getWorker();
            lastAzienda = d.getWorkerCompany();
            lastProtocolDate = d.getProtocolDate();
            lastAction = d.calculateAction();
            lastEffectDate = d.getEffectDate();
            state = d.calculateState();
            lastMovement = d.getLastMovement();
            retrieveDelegaBianca = d.checkIfCanBePresentedUntilSemesterEnd();
            contributeToBalace = d.calculateContribute();
            referente = d.getManagementContact() != null ? d.getManagementContact().getCompleteName(): "";
        }
        added = false;
    }



    public List<DelegaBari> getDeleghe() {
        return deleghe;
    }

    private Lavoratore worker;
    private Azienda lastAzienda;
    private Date lastProtocolDate;
    private String lastAction;
    private Date lastEffectDate;
    private int contributeToBalace;
    private String state;
    private String lastMovement;
    private boolean added = false;
    private String referente;


    private boolean retrieveDelegaBianca;

    private List<DelegaBari> deleghe = new ArrayList<>();



    public void addDelega(DelegaBari delegaBari) {
        if (delegaBari == null)
            return;
        deleghe.add(delegaBari);
        added = true;
    }
}
