package applica.feneal.admin.viewmodel.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiRichiesta {

    private Date richData;
    private String richRecipient;   // destinatario
    private String richProvince;


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


    public Date getRichData() {
        return richData;
    }

    public void setRichData(Date richData) {
        this.richData = richData;
    }

    public String getRichRecipient() {
        return richRecipient;
    }

    public void setRichRecipient(String richRecipient) {
        this.richRecipient = richRecipient;
    }

    public String getRichProvince() {
        return richProvince;
    }

    public void setRichProvince(String richProvince) {
        this.richProvince = richProvince;
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

    public long getLavoratoreId() {
        return lavoratoreId;
    }

    public void setLavoratoreId(long lavoratoreId) {
        this.lavoratoreId = lavoratoreId;
    }
}
