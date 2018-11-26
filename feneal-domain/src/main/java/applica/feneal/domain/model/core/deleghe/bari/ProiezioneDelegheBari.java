package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.bari.StatoDelega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.Date;

public class ProiezioneDelegheBari {

    public String getLavoratoreCodiceFiscale(){

        return worker.getFiscalcode();
    }

    public long getLavoratoreId(){

        return worker.getLid();
    }

    public ProiezioneDelegheBari(StatoDelega prevDelega, StatoDelega lastDelega)  {
        if (prevDelega== null)
            throw new RuntimeException("Delega precedente nulla");

        worker = prevDelega.getWorker();
        prevAction = prevDelega.getLastAction();
        prevState = prevDelega.getState();
        prevCompany = prevDelega.getLastAzienda();
        prevDate = prevDelega.getLastProtocolDate();
        prevMovement = prevDelega.getLastMovement();

        lastMovement = lastDelega != null ? lastDelega.getLastMovement():"";
        lastAction = lastDelega != null ? lastDelega.getLastAction():"";
        lastState = lastDelega != null ? lastDelega.getState():"";
        lastDate = lastDelega != null ? lastDelega.getLastProtocolDate():null;
        lastCompany = lastDelega != null ? lastDelega.getLastAzienda():null;
        effectDate = lastDelega != null ? lastDelega.getLastEffectDate():null;
    }

    private Lavoratore worker;

    private String lastState;
    private String prevState;

    private String lastAction;
    private String prevAction;


    private String lastMovement;
    private String prevMovement;


    private Date lastDate;
    private Date prevDate;


    private Azienda lastCompany;
    private Azienda prevCompany;


    private Date effectDate;



    public Lavoratore getWorker() {
        return worker;
    }

    public String getLastState() {
        return lastState;
    }

    public String getPrevState() {
        return prevState;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getPrevAction() {
        return prevAction;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public Date getPrevDate() {
        return prevDate;
    }

    public Azienda getLastCompany() {
        return lastCompany;
    }

    public Azienda getPrevCompany() {
        return prevCompany;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public String getLastMovement() {
        return lastMovement;
    }

    public String getPrevMovement() {
        return prevMovement;
    }
}
