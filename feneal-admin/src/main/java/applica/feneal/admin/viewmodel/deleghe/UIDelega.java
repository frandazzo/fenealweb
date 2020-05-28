package applica.feneal.admin.viewmodel.deleghe;

import applica.feneal.domain.model.core.deleghe.Delega;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoniolovicario on 17/04/16.
 */
public class UIDelega {
    /*
    Entità per gestire le comunicazione client- server
     */

    private String id;
    private String documentDate;
    private String sendDate;
    private String acceptDate;
    private String activationDate;
    private String cancelDate;
    private String revokeDate;
    private List<Integer> supportedNextStates = new ArrayList<>();
    private String validityDate;
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

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    //settore della delega se impianti fissi o edile
    private String sector;
    //ente bilaterale di riferimento (solo per delega edile)
    private String paritethic;
    //azienda del lavoratore
    private String workerCompany;
    private String workerCompanyId;

    public String getWorkerCompanyId() {
        return workerCompanyId;
    }

    public void setWorkerCompanyId(String workerCompanyId) {
        this.workerCompanyId = workerCompanyId;
    }

    //stato della delega
    private int state;

    //collaboratore che ha fatto la delega
    private String collaborator;

    //utente che hA sottoscritto la delega
    private String worker;
    private String workerId;


    private String province;

    private String subscribeReason;
    private String revokeReason;
    private String cancelReason;
    private String notes;
    private String firstAziendaEdile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(String revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getParitethic() {
        return paritethic;
    }

    public void setParitethic(String paritethic) {
        this.paritethic = paritethic;
    }

    public String getWorkerCompany() {
        return workerCompany;
    }

    public void setWorkerCompany(String workerCompany) {
        this.workerCompany = workerCompany;
    }

    public String getStateDescription() {
        switch (state) {
            case Delega.state_accepted:
                return "Accettata";
            case Delega.state_subscribe:
                return "Sottoscritta";
            case Delega.state_sent:
                return "Inoltrata";
            case Delega.state_activated:
                return "Attivata";
            case Delega.state_cancelled:
                return "Annullata";
            case Delega.state_revoked:
                return "Revocata";
            default:
                return "";
        }

    }

    //Ottiene il nome dell'azione corrispondente al passaggio verso lo stato passato come parametro
    public String getNewStateAction(int newState) {
        switch (newState) {
            case Delega.state_accepted:
                return "Accetta delega";
            case Delega.state_subscribe:
                return "Sottoscrivi delega";
            case Delega.state_sent:
                return "Inoltra delega";
            case Delega.state_activated:
                return "Attiva delega";
            case Delega.state_cancelled:
                return "Annulla delega";
            case Delega.state_revoked:
                return "Revoca delega";
            case Delega.ACTION_BACK:
                return "Ripristina stato precedente";
            default:
                return "";
        }
    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSubscribeReason() {
        return subscribeReason;
    }

    public void setSubscribeReason(String subscribeReason) {
        this.subscribeReason = subscribeReason;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public List<Integer> getSupportedNextStates() {
        return supportedNextStates;
    }

    public void setSupportedNextStates(List<Integer> supportedNextStates) {
        this.supportedNextStates = supportedNextStates;
    }

    public String getFirstAziendaEdile() {
        return firstAziendaEdile;
    }

    public void setFirstAziendaEdile(String firstAziendaEdile) {
        this.firstAziendaEdile = firstAziendaEdile;
    }
}
