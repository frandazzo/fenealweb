package applica.feneal.domain.model.core.importData;

import java.util.Date;

/**
 * Created by fgran on 19/04/2017.
 */
public class ImportDelegaSummary {

    private String contratto;
    private String collaboratore;

    private Date data;
    private Date dataAccettazione;
    private Date dataAnnullamento;
    private Date dataRevoca;
    private String causaleIscrizione;
    private String causaleRevoca;
    private String note;

    private String settore;


    private String paritetic;
    private String azienda;
    private String aziendaNote;
    private String partita_iva;
    private String aziendaComune;
    private String aziendaIndirizzo;
    private String aziendaCap;
    private String aziendaTelefono;


    public String getAziendaNote() {
        return aziendaNote;
    }

    public void setAziendaNote(String aziendaNote) {
        this.aziendaNote = aziendaNote;
    }

    public String getPartita_iva() {
        return partita_iva;
    }

    public void setPartita_iva(String partita_iva) {
        this.partita_iva = partita_iva;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public String getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(String collaboratore) {
        this.collaboratore = collaboratore;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataAccettazione() {
        return dataAccettazione;
    }

    public void setDataAccettazione(Date dataAccettazione) {
        this.dataAccettazione = dataAccettazione;
    }

    public Date getDataAnnullamento() {
        return dataAnnullamento;
    }

    public void setDataAnnullamento(Date dataAnnullamento) {
        this.dataAnnullamento = dataAnnullamento;
    }

    public Date getDataRevoca() {
        return dataRevoca;
    }

    public void setDataRevoca(Date dataRevoca) {
        this.dataRevoca = dataRevoca;
    }


    public String getCausaleIscrizione() {
        return causaleIscrizione;
    }

    public void setCausaleIscrizione(String causaleIscrizione) {
        this.causaleIscrizione = causaleIscrizione;
    }

    public String getCausaleRevoca() {
        return causaleRevoca;
    }

    public void setCausaleRevoca(String causaleRevoca) {
        this.causaleRevoca = causaleRevoca;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getParitetic() {
        return paritetic;
    }

    public void setParitetic(String paritetic) {
        this.paritetic = paritetic;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }



    public String getAziendaComune() {
        return aziendaComune;
    }

    public void setAziendaComune(String aziendaComune) {
        this.aziendaComune = aziendaComune;
    }

    public String getAziendaIndirizzo() {
        return aziendaIndirizzo;
    }

    public void setAziendaIndirizzo(String aziendaIndirizzo) {
        this.aziendaIndirizzo = aziendaIndirizzo;
    }

    public String getAziendaCap() {
        return aziendaCap;
    }

    public void setAziendaCap(String aziendaCap) {
        this.aziendaCap = aziendaCap;
    }

    public String getAziendaTelefono() {
        return aziendaTelefono;
    }

    public void setAziendaTelefono(String aziendaTelefono) {
        this.aziendaTelefono = aziendaTelefono;
    }
}
