package applica.feneal.admin.viewmodel.reports;

import applica.feneal.domain.model.core.aziende.Azienda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 14/04/2016.
 */
public class UiDelega {


    private Date delegaDataDocumento;
    private Date delegaDataInoltro;
    private Date delegaDataAccettazione;
    private Date delegaDataAttivazione;
    private Date delegaDataAnnullamento;
    private Date delegaDataRevoca;
    //settore della delega se impianti fissi o edile

    private String delegaSettore;
    //ente bilaterale di riferimento (solo per delega edile)
    private String delegaEnteBilaterale;
    //stato della delega
    private String delegaStato;
    //collaboratore che ha fatto la delega
    private String delegaCollaboratore;
    private String contract;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    //territorio della dleega
    private String delegaProvincia;

    private String delegaCausaleSottoscrizione;
    private String delegaCausaleRevoca;
    private String delegaCausaleAnnullamento;
    private long delegaId;

    private String delegaNote;
    private String firstAziendaEdile;
    private Date validityDate;
    private String delegaScansione;


    public String getDelegaNote() {
        return delegaNote;
    }

    public void setDelegaNote(String delegaNote) {
        this.delegaNote = delegaNote;
    }

    //******************************************
    //Dati azienda//
    private String aziendaRagioneSociale;
    private String aziendaCitta;
    private String aziendaProvincia;
    private String aziendaCap;
    private String aziendaIndirizzo;
    private String aziendaNote;
    private long aziendaId;
    //******************************************




    public String getLavoratoreNomeCompleto() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s %s (%s)", lavoratoreCognome, lavoratoreNome, f.format(lavoratoreDataNascita) );
    }

    public void setLavoratoreNomeCompleto(String lavoratoreNomeCompleto) {
        this.lavoratoreNomeCompleto = lavoratoreNomeCompleto;
    }

    //*****************************************
    //dati lavoratore
    private String lavoratoreNomeCompleto;
    private String lavoratoreNome;
    private String lavoratoreCognome;
    private String lavoratoreSesso;
    private String lavoratoreCodiceFiscale;
    private Date lavoratoreDataNascita;
    private String lavoratoreNazionalita;
    private String lavoratoreProvinciaNascita;
    private String lavoratoreLuogoNascita;   // comune di nascita
    private String lavoratoreProvinciaResidenza;
    private String lavoratoreCittaResidenza;
    private String lavoratoreIndirizzo;
    private String lavoratoreCap;
    private String lavoratoreTelefono;
    private String lavoratoreCellulare;
    private String lavoratorMail;
    private String lavoratoreCodiceCassaEdile; // cassa edile
    private String lavoratoreCodiceEdilcassa; // ?????
    private String lavoratoreFondo;
    private String lavoratoreNote;
    private long lavoratoreId;



    //private List<UiDelegaTestDetail> details;

//    public List<UiDelegaTestDetail> getDetails() {
//        return details;
//    }
//
//    public void setDetails(List<UiDelegaTestDetail> details) {
//        this.details = details;
//    }


    public long getDelegaId() {
        return delegaId;
    }

    public void setDelegaId(long delegaId) {
        this.delegaId = delegaId;
    }

    public long getAziendaId() {
        return aziendaId;
    }

    public void setAziendaId(long aziendaId) {
        this.aziendaId = aziendaId;
    }

    public long getLavoratoreId() {
        return lavoratoreId;
    }

    public void setLavoratoreId(long lavoratoreId) {
        this.lavoratoreId = lavoratoreId;
    }

    public Date getDelegaDataDocumento() {
        return delegaDataDocumento;
    }

    public void setDelegaDataDocumento(Date delegaDataDocumento) {
        this.delegaDataDocumento = delegaDataDocumento;
    }

    public Date getDelegaDataInoltro() {
        return delegaDataInoltro;
    }

    public void setDelegaDataInoltro(Date delegaDataInoltro) {
        this.delegaDataInoltro = delegaDataInoltro;
    }

    public Date getDelegaDataAccettazione() {
        return delegaDataAccettazione;
    }

    public void setDelegaDataAccettazione(Date delegaDataAccettazione) {
        this.delegaDataAccettazione = delegaDataAccettazione;
    }

    public Date getDelegaDataAttivazione() {
        return delegaDataAttivazione;
    }

    public void setDelegaDataAttivazione(Date delegaDataAttivazione) {
        this.delegaDataAttivazione = delegaDataAttivazione;
    }

    public Date getDelegaDataAnnullamento() {
        return delegaDataAnnullamento;
    }

    public void setDelegaDataAnnullamento(Date delegaDataAnnullamento) {
        this.delegaDataAnnullamento = delegaDataAnnullamento;
    }

    public Date getDelegaDataRevoca() {
        return delegaDataRevoca;
    }

    public void setDelegaDataRevoca(Date delegaDataRevoca) {
        this.delegaDataRevoca = delegaDataRevoca;
    }

    public String getDelegaSettore() {
        return delegaSettore;
    }

    public void setDelegaSettore(String delegaSettore) {
        this.delegaSettore = delegaSettore;
    }

    public String getDelegaEnteBilaterale() {
        return delegaEnteBilaterale;
    }

    public void setDelegaEnteBilaterale(String delegaEnteBilaterale) {
        this.delegaEnteBilaterale = delegaEnteBilaterale;
    }

    public String getDelegaStato() {
        return delegaStato;
    }

    public void setDelegaStato(String delegaStato) {
        this.delegaStato = delegaStato;
    }

    public String getDelegaCollaboratore() {
        return delegaCollaboratore;
    }

    public void setDelegaCollaboratore(String delegaCollaboratore) {
        this.delegaCollaboratore = delegaCollaboratore;
    }

    public String getDelegaProvincia() {
        return delegaProvincia;
    }

    public void setDelegaProvincia(String delegaProvincia) {
        this.delegaProvincia = delegaProvincia;
    }

    public String getDelegaCausaleSottoscrizione() {
        return delegaCausaleSottoscrizione;
    }

    public void setDelegaCausaleSottoscrizione(String delegaCausaleSottoscrizione) {
        this.delegaCausaleSottoscrizione = delegaCausaleSottoscrizione;
    }

    public String getDelegaCausaleRevoca() {
        return delegaCausaleRevoca;
    }

    public void setDelegaCausaleRevoca(String delegaCausaleRevoca) {
        this.delegaCausaleRevoca = delegaCausaleRevoca;
    }

    public String getDelegaCausaleAnnullamento() {
        return delegaCausaleAnnullamento;
    }

    public void setDelegaCausaleAnnullamento(String delegaCausaleAnnullamento) {
        this.delegaCausaleAnnullamento = delegaCausaleAnnullamento;
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

    public String getLavoratoreNome() {
        return lavoratoreNome;
    }

    public void setLavoratoreNome(String lavoratoreNome) {
        this.lavoratoreNome = lavoratoreNome;
    }

    public String getLavoratoreCognome() {
        return lavoratoreCognome;
    }

    public void setLavoratoreCognome(String lavoratoreCognome) {
        this.lavoratoreCognome = lavoratoreCognome;
    }

    public String getLavoratoreSesso() {
        return lavoratoreSesso;
    }

    public void setLavoratoreSesso(String lavoratoreSesso) {
        this.lavoratoreSesso = lavoratoreSesso;
    }

    public String getLavoratoreCodiceFiscale() {
        return lavoratoreCodiceFiscale;
    }

    public void setLavoratoreCodiceFiscale(String lavoratoreCodiceFiscale) {
        this.lavoratoreCodiceFiscale = lavoratoreCodiceFiscale;
    }

    public Date getLavoratoreDataNascita() {
        return lavoratoreDataNascita;
    }

    public void setLavoratoreDataNascita(Date lavoratoreDataNascita) {
        this.lavoratoreDataNascita = lavoratoreDataNascita;
    }

    public String getLavoratoreNazionalita() {
        return lavoratoreNazionalita;
    }

    public void setLavoratoreNazionalita(String lavoratoreNazionalita) {
        this.lavoratoreNazionalita = lavoratoreNazionalita;
    }

    public String getLavoratoreProvinciaNascita() {
        return lavoratoreProvinciaNascita;
    }

    public void setLavoratoreProvinciaNascita(String lavoratoreProvinciaNascita) {
        this.lavoratoreProvinciaNascita = lavoratoreProvinciaNascita;
    }

    public String getLavoratoreLuogoNascita() {
        return lavoratoreLuogoNascita;
    }

    public void setLavoratoreLuogoNascita(String lavoratoreLuogoNascita) {
        this.lavoratoreLuogoNascita = lavoratoreLuogoNascita;
    }

    public String getLavoratoreProvinciaResidenza() {
        return lavoratoreProvinciaResidenza;
    }

    public void setLavoratoreProvinciaResidenza(String lavoratoreProvinciaResidenza) {
        this.lavoratoreProvinciaResidenza = lavoratoreProvinciaResidenza;
    }

    public String getLavoratoreCittaResidenza() {
        return lavoratoreCittaResidenza;
    }

    public void setLavoratoreCittaResidenza(String lavoratoreCittaResidenza) {
        this.lavoratoreCittaResidenza = lavoratoreCittaResidenza;
    }

    public String getLavoratoreIndirizzo() {
        return lavoratoreIndirizzo;
    }

    public void setLavoratoreIndirizzo(String lavoratoreIndirizzo) {
        this.lavoratoreIndirizzo = lavoratoreIndirizzo;
    }

    public String getLavoratoreCap() {
        return lavoratoreCap;
    }

    public void setLavoratoreCap(String lavoratoreCap) {
        this.lavoratoreCap = lavoratoreCap;
    }

    public String getLavoratoreTelefono() {
        return lavoratoreTelefono;
    }

    public void setLavoratoreTelefono(String lavoratoreTelefono) {
        this.lavoratoreTelefono = lavoratoreTelefono;
    }

    public String getLavoratoreCellulare() {
        return lavoratoreCellulare;
    }

    public void setLavoratoreCellulare(String lavoratoreCellulare) {
        this.lavoratoreCellulare = lavoratoreCellulare;
    }

    public String getLavoratorMail() {
        return lavoratorMail;
    }

    public void setLavoratorMail(String lavoratorMail) {
        this.lavoratorMail = lavoratorMail;
    }

    public String getLavoratoreCodiceCassaEdile() {
        return lavoratoreCodiceCassaEdile;
    }

    public void setLavoratoreCodiceCassaEdile(String lavoratoreCodiceCassaEdile) {
        this.lavoratoreCodiceCassaEdile = lavoratoreCodiceCassaEdile;
    }

    public String getLavoratoreCodiceEdilcassa() {
        return lavoratoreCodiceEdilcassa;
    }

    public void setLavoratoreCodiceEdilcassa(String lavoratoreCodiceEdilcassa) {
        this.lavoratoreCodiceEdilcassa = lavoratoreCodiceEdilcassa;
    }

    public String getLavoratoreFondo() {
        return lavoratoreFondo;
    }

    public void setLavoratoreFondo(String lavoratoreFondo) {
        this.lavoratoreFondo = lavoratoreFondo;
    }

    public String getLavoratoreNote() {
        return lavoratoreNote;
    }

    public void setLavoratoreNote(String lavoratoreNote) {
        this.lavoratoreNote = lavoratoreNote;
    }


    public void setFirstAziendaEdile(String firstAziendaEdile) {
        this.firstAziendaEdile = firstAziendaEdile;
    }

    public String getFirstAziendaEdile() {
        return firstAziendaEdile;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setDelegaScansione(String delegaScansione) {
        this.delegaScansione = delegaScansione;
    }

    public String getDelegaScansione() {
        return delegaScansione;
    }
}
