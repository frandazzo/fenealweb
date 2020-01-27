package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.Semester;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DelegaBari extends SecuredDomainEntity {



    private Date protocolDate;
    private Date effectDate;
    private String signup;
    private String revocation;
    private String duplicate;
    private String anomaly;
    private String protocolNumber;
    //azienda del lavoratore
    private Azienda workerCompany;
    private Lavoratore worker;
    private String lastMovement;
    private Referenti managementContact;

    public Referenti getManagementContact() {
        return managementContact;
    }

    public void setManagementContact(Referenti managementContact) {
        this.managementContact = managementContact;
    }

    public boolean checkIfCanBePresentedUntilSemesterEnd(){

        //devo verficare se si tratta di delega bianca che
        //per essere recuperata deve essere presentataentro la fine del  semestre corrente
        //a patto che la data protocollo della revoca sia appartenenta al semestre corrente


        //devo pertanto calcolare l'intervallo date del semestre corrente
        //verifcare che la revoca sia stata fatta nel semestre corrente e se si allora ritornare true!!
        if (!calculateAction().equals(RevocaBianca))
            return false;

        Semester s = Semester.createSemester();
        if (s.contains(protocolDate))
            return true;

        return false;
    }




    public String toDate(Date d){
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        return g.format(d);
    }


    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public String getRevocation() {
        return revocation;
    }

    public void setRevocation(String revocation) {
        this.revocation = revocation;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    public String getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(String anomaly) {
        this.anomaly = anomaly;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public Azienda getWorkerCompany() {
        return workerCompany;
    }

    public void setWorkerCompany(Azienda workerCompany) {
        this.workerCompany = workerCompany;
    }

    public Lavoratore getWorker() {
        return worker;
    }

    public void setWorker(Lavoratore worker) {
        this.worker = worker;
    }




    public Date getProtocolDate() {
        return protocolDate;
    }

    public void setProtocolDate(Date protocolDate) {
        this.protocolDate = protocolDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public static final String  StatoIscrizione ="ISCRITTO" ;
    public static final String  StatoIscrizioneInEntrata ="ISCRITTO IN ENTRATA" ;
    public static final String  StatoNonIscrizione ="NON ISCRITTO" ;
    public static final String  StatoIscrizioneInUscita ="ISCRITTO IN USCITA" ;
    public static final String  StatoIscrizioneIndeterminato ="INDETERMINATO" ;



    public static final String  Iscrizione ="ISCRIZIONE" ;
    public static final String  IscrizioneInefficace ="ISCR.INEFF" ;
    public static final String  IscrizioneCongelata ="ISCR.CONG." ;
    public static final String  IscrizioneIRiconferma ="I RICONF." ;
    public static final String  RevocaDelega ="REVOCA/DEL" ;
    public static final String  RevocaBianca ="REVOCA/BIA" ;
    public static final String  RevocaInefficace ="REV.INEFF." ;
    public static final String  Doppione ="DOPPIONE" ;
    public static final String  Anomalia ="ANOMALIA" ;




    public String calculateState() {
        if (!StringUtils.isEmpty(anomaly))
            return "ANOMALIA";
        if (!StringUtils.isEmpty(duplicate)){
            if (new Date().getTime() > effectDate.getTime())
                return "ISCRITTO";
            else
                return "ISCRITTO IN ENTRATA";
        }
        if (!StringUtils.isEmpty(revocation)){

            //se non è una dlega revoca è una delega bianca
            if (revocation.equals("REVOCA/DEL") ){
                if (new Date().getTime() > effectDate.getTime())
                    return "NON ISCRITTO";
                else
                    return "ISCRITTO IN USCITA";
            }else if(revocation.equals("REVOCA/BIA")){
                if (new Date().getTime() > effectDate.getTime())
                    return "NON ISCRITTO";
                else
                    return "ISCRITTO IN USCITA";
            } else if (revocation.equals("REV.INEFF.")){
                return "ISCRITTO";
            }else{
                return "INDETERMINATO (REVOCA)";
            }


        }

        if (!StringUtils.isEmpty(signup)){

            //se non è una dlega revoca è una delega bianca
            if (signup.equals("ISCRIZIONE")) {
                if (new Date().getTime() > effectDate.getTime())
                    return "ISCRITTO";
                else
                    return "ISCRITTO IN ENTRATA";
            }else if (signup.equals("ISCR.INEFF")){
                    return "NON ISCRITTO";
            }else if (signup.equals("I RICONF.")){
                return "NON ISCRITTO";
            }else if (signup.equals("ISCR.CONG.")){
                return "NON ISCRITTO";
            }else{
                return "INDETERMINATO (ISRITTO)";
            }


        }


        return "INDETERMINATO";


    }

    public String calculateAction() {
        if (!StringUtils.isEmpty(anomaly))
            return anomaly;
        if (!StringUtils.isEmpty(duplicate)){
           return duplicate;
        }
        if (!StringUtils.isEmpty(revocation)){

            return revocation;


        }

        if (!StringUtils.isEmpty(signup)){

            return signup;

        }


        return "INDETERMINATO";

    }

    public int calculateContribute() {
        String action = calculateAction();
        if (action.equals("ISCRIZIONE"))
            return 1;

        if (action.equals("REVOCA/DEL") || action.equals("REVOCA/BIA"))
            return -1;

        return 0;
    }

    public void setLastMovement(String lastMovement) {
        this.lastMovement = lastMovement;
    }

    public String getLastMovement() {
        return lastMovement;
    }
}
