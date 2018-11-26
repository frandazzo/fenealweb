package applica.feneal.admin.viewmodel.reports;

import java.util.Date;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiDocumentoAzienda {

    private Date docData;
    private String docTipoDocumento;
    private String docCollaboratore;
    private String docProvince;


    //*****************************************
    //Dati azienda
    private String aziendaRagioneSociale;
    private String aziendaCitta;
    private String aziendaProvincia;
    private String aziendaCap;
    private String aziendaIndirizzo;
    private String aziendaNote;
    private long aziendaId;


    public Date getDocData() {
        return docData;
    }

    public void setDocData(Date docData) {
        this.docData = docData;
    }

    public String getDocTipoDocumento() {
        return docTipoDocumento;
    }

    public void setDocTipoDocumento(String docTipoDocumento) {
        this.docTipoDocumento = docTipoDocumento;
    }

    public String getDocCollaboratore() {
        return docCollaboratore;
    }

    public void setDocCollaboratore(String docCollaboratore) {
        this.docCollaboratore = docCollaboratore;
    }

    public String getAziendaRagioneSociale() {
        return aziendaRagioneSociale;
    }

    public void setAziendaRagioneSociale(String aziendaRagioneSociale) {
        this.aziendaRagioneSociale = aziendaRagioneSociale;
    }

    public String getAziendaCitta() {
        return aziendaCitta;
    }

    public void setAziendaCitta(String aziendaCitta) {
        this.aziendaCitta = aziendaCitta;
    }

    public String getAziendaProvincia() {
        return aziendaProvincia;
    }

    public void setAziendaProvincia(String aziendaProvincia) {
        this.aziendaProvincia = aziendaProvincia;
    }

    public String getAziendaCap() {
        return aziendaCap;
    }

    public void setAziendaCap(String aziendaCap) {
        this.aziendaCap = aziendaCap;
    }

    public String getAziendaIndirizzo() {
        return aziendaIndirizzo;
    }

    public void setAziendaIndirizzo(String aziendaIndirizzo) {
        this.aziendaIndirizzo = aziendaIndirizzo;
    }

    public String getAziendaNote() {
        return aziendaNote;
    }

    public void setAziendaNote(String aziendaNote) {
        this.aziendaNote = aziendaNote;
    }

    public long getAziendaId() {
        return aziendaId;
    }

    public void setAziendaId(long aziendaId) {
        this.aziendaId = aziendaId;
    }

    public String getDocProvince() {
        return docProvince;
    }

    public void setDocProvince(String docProvince) {
        this.docProvince = docProvince;
    }
}
