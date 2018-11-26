package applica.feneal.admin.viewmodel.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fgran on 15/04/2016.
 */
public class UiIscritto {

    private String iscrittoSettore;
    private String iscrittoEnteBilaterale;
    private double iscrittoQuota;
    private String iscrittoCompetenza;
    private String iscrittoLivello;
    private String iscrittoContratto;
    private Date iscrittoDataRegistrazione;
    private String iscrittoCollaboratore;
    private String iscrittoProvincia;
    private String tipo;


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

    //Dati azienda//
    private String aziendaRagioneSociale;
    private String aziendaCitta;
    private String aziendaProvincia;
    private String aziendaCap;
    private String aziendaIndirizzo;
    private String aziendaNote;
    private long aziendaId;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getLavoratoreId() {
        return lavoratoreId;
    }

    public void setLavoratoreId(long lavoratoreId) {
        this.lavoratoreId = lavoratoreId;
    }

    public long getAziendaId() {
        return aziendaId;
    }

    public void setAziendaId(long aziendaId) {
        this.aziendaId = aziendaId;
    }

    public String getIscrittoSettore() {
        return iscrittoSettore;
    }

    public void setIscrittoSettore(String iscrittoSettore) {
        this.iscrittoSettore = iscrittoSettore;
    }

    public String getIscrittoEnteBilaterale() {
        return iscrittoEnteBilaterale;
    }

    public void setIscrittoEnteBilaterale(String iscrittoEnteBilaterale) {
        this.iscrittoEnteBilaterale = iscrittoEnteBilaterale;
    }

    public double getIscrittoQuota() {
        return iscrittoQuota;
    }

    public void setIscrittoQuota(double iscrittoQuota) {
        this.iscrittoQuota = iscrittoQuota;
    }

    public String getIscrittoCompetenza() {
        return iscrittoCompetenza;
    }

    public void setIscrittoCompetenza(String iscrittoCompetenza) {
        this.iscrittoCompetenza = iscrittoCompetenza;
    }

    public String getIscrittoLivello() {
        return iscrittoLivello;
    }

    public void setIscrittoLivello(String iscrittoLivello) {
        this.iscrittoLivello = iscrittoLivello;
    }

    public String getIscrittoContratto() {
        return iscrittoContratto;
    }

    public void setIscrittoContratto(String iscrittoContratto) {
        this.iscrittoContratto = iscrittoContratto;
    }

    public Date getIscrittoDataRegistrazione() {
        return iscrittoDataRegistrazione;
    }

    public void setIscrittoDataRegistrazione(Date iscrittoDataRegistrazione) {
        this.iscrittoDataRegistrazione = iscrittoDataRegistrazione;
    }

    public String getIscrittoCollaboratore() {
        return iscrittoCollaboratore;
    }

    public void setIscrittoCollaboratore(String iscrittoCollaboratore) {
        this.iscrittoCollaboratore = iscrittoCollaboratore;
    }

    public String getIscrittoProvincia() {
        return iscrittoProvincia;
    }

    public void setIscrittoProvincia(String iscrittoProvincia) {
        this.iscrittoProvincia = iscrittoProvincia;
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
}
