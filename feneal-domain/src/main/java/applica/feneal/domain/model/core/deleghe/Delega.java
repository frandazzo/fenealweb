package applica.feneal.domain.model.core.deleghe;

import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.states.*;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fgran on 05/04/2016.
 */
public class Delega extends SecuredDomainEntity{


    public static final int state_subscribe = 1;
    public static final int state_sent = 2;
    public static final int state_accepted = 3;
    public static final int state_activated = 4;
    public static final int state_cancelled = 5;
    public static final int state_revoked = 6;


    //Utilizzato per identificare l'azione del "tornare indietro"
    public static final int ACTION_BACK = 0;


    private Date documentDate;
    private Date sendDate;
    private Date acceptDate;
    private Date activationDate;
    private Date cancelDate;
    private Date revokeDate;

    private String attachment;
    private String nomeattachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNomeattachment() {
        return nomeattachment;
    }

    public void setNomeattachment(String nomeattachment) {
        this.nomeattachment = nomeattachment;
    }

    private Date validityDate;

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    //azienda del lavoratore se edile
    //questo è un puro e semplice riferimento
    //per avere nell'anagrafica dell'azienda una lista di persone con la delega per l'azienda
    private Azienda firstAziendaEdile;

    public Azienda getFirstAziendaEdile() {
        return firstAziendaEdile;
    }

    public void setFirstAziendaEdile(Azienda firstAziendaEdile) {
        this.firstAziendaEdile = firstAziendaEdile;
    }

    //guid che identifica univocamente l'importazione che ha generato la delega
    private String importGuid;

    public String getImportGuid() {
        return importGuid;
    }

    public void setImportGuid(String importGuid) {
        this.importGuid = importGuid;
    }

    //settore della delega se impianti fissi o edile
    private Sector sector;
    //ente bilaterale di riferimento (solo per delega edile)
    private Paritethic paritethic;
    //azienda del lavoratore
    private Azienda workerCompany;

    //stato della delega
    private Integer state = state_subscribe;

    //collaboratore che ha fatto la delega
    private Collaboratore collaborator;

    //utente che hA sottoscritto la delega
    private Lavoratore worker;


    private Province province;

    private CausaleIscrizioneDelega subscribeReason;
    private CausaleRevoca revokeReason;
    private CausaleRevoca cancelReason;
    //flag per un eventuale annullamento da parte di un'altra delega
    //tale delega sara definitivamente annullata e non avrà alcuna possibilità di ritornare ad uno stato precendente
    //l'unica operazione possibile sarà la cancellazione
    private boolean tombstoneDelega;
    //stato precedente;
    private Integer preecedingState;

    //flag che indica se la delega è stata attivata senza la necessaria accettazione
    private Boolean activatedWithoutAcceptance;

    //ad indicale eventuali annotazioni legate ad una particolare annotazione nelle operazioni del sistema sulla delega
    private String notes;


    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public boolean checkIfActivateOrAccepted(){
        if (state != null && (state == (Delega.state_accepted)|| state == (Delega.state_activated)))
            return true;
        return false;
    }



    public CausaleRevoca getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(CausaleRevoca cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Date getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Paritethic getParitethic() {
        return paritethic;
    }

    public void setParitethic(Paritethic paritethic) {
        this.paritethic = paritethic;
    }

    public Azienda getWorkerCompany() {
        return workerCompany;
    }

    public void setWorkerCompany(Azienda workerCompany) {
        this.workerCompany = workerCompany;
    }

    public Collaboratore getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaboratore collaborator) {
        this.collaborator = collaborator;
    }

    public Lavoratore getWorker() {
        return worker;
    }

    public void setWorker(Lavoratore worker) {
        this.worker = worker;
    }

    public CausaleIscrizioneDelega getSubscribeReason() {
        return subscribeReason;
    }

    public void setSubscribeReason(CausaleIscrizioneDelega subscribeReason) {
        this.subscribeReason = subscribeReason;
    }

    public CausaleRevoca getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(CausaleRevoca revokeReason) {
        this.revokeReason = revokeReason;
    }


    public boolean isTombstoneDelega() {
        return tombstoneDelega;
    }

    public void setTombstoneDelega(boolean tombstoneDelega) {
        this.tombstoneDelega = tombstoneDelega;
    }

    public Boolean getActivatedWithoutAcceptance() {
        return activatedWithoutAcceptance;
    }

    public void setActivatedWithoutAcceptance(Boolean activatedWithoutAcceptance) {
        this.activatedWithoutAcceptance = activatedWithoutAcceptance;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPreecedingState() {
        return preecedingState;
    }

    public void setPreecedingState(Integer preecedingState) {
        this.preecedingState = preecedingState;
    }

    public String calculatePrecedingStateString(Integer preecedingState) {

        switch (preecedingState){
            case Delega.state_accepted:
                return String.format("Accettata il %s", new SimpleDateFormat("dd/MM/yyyy").format(this.getAcceptDate()));

            case Delega.state_sent:
                return String.format("Inoltrata il %s", new SimpleDateFormat("dd/MM/yyyy").format(this.getSendDate()));

            case Delega.state_subscribe:
                return String.format("Sotoscritta il %s", new SimpleDateFormat("dd/MM/yyyy").format(this.getDocumentDate()));

            case Delega.state_activated:
                return String.format("Attivata il %s", new SimpleDateFormat("dd/MM/yyyy").format(this.getActivationDate()));

            default:
               return "";
        }



    }
}
