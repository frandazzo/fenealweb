package applica.feneal.domain.model.dbnazionale;

import applica.framework.AEntity;
import applica.framework.IEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 30/04/2016.
 */
public class UtenteDbNazionale extends IEntity {

    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String nomeCompleto;
    private Date dataNascita;
    private String sesso;// enum('FEMMINA','MASCHIO') DEFAULT 'MASCHIO',
    private Integer id_Nazione;
    private String nomeNazione;
    private Integer id_Provincia;
    private String nomeProvincia;
    private Integer id_Comune;
    private String nomeComune;
    private Integer id_Provincia_Residenza;
    private String nomeProvinciaResidenza;
    private Integer id_Comune_Residenza;
    private String nomeComuneResidenza;
    private String indirizzo;
    private String cap;
    private String telefono;
    private Date ultimaModifica;
    private String ultimaProvinciaAdAggiornare;

    //queste due proprietà sono necessarie solamente per la ricerca degli utenti nel database nazionale
    private transient int numIscrizioni;
    private transient List<Iscrizione> iscrizioni;

    public List<Iscrizione> getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(List<Iscrizione> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public int getNumIscrizioni() {
        return numIscrizioni;
    }

    public void setNumIscrizioni(int numIscrizioni) {
        this.numIscrizioni = numIscrizioni;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Integer getId_Nazione() {
        return id_Nazione;
    }

    public void setId_Nazione(Integer id_Nazione) {
        this.id_Nazione = id_Nazione;
    }

    public String getNomeNazione() {
        return nomeNazione;
    }

    public void setNomeNazione(String nomeNazione) {
        this.nomeNazione = nomeNazione;
    }

    public Integer getId_Provincia() {
        return id_Provincia;
    }

    public void setId_Provincia(Integer id_Provincia) {
        this.id_Provincia = id_Provincia;
    }

    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public Integer getId_Comune() {
        return id_Comune;
    }

    public void setId_Comune(Integer id_Comune) {
        this.id_Comune = id_Comune;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public Integer getId_Provincia_Residenza() {
        return id_Provincia_Residenza;
    }

    public void setId_Provincia_Residenza(Integer id_Provincia_Residenza) {
        this.id_Provincia_Residenza = id_Provincia_Residenza;
    }

    public String getNomeProvinciaResidenza() {
        return nomeProvinciaResidenza;
    }

    public void setNomeProvinciaResidenza(String nomeProvinciaResidenza) {
        this.nomeProvinciaResidenza = nomeProvinciaResidenza;
    }

    public Integer getId_Comune_Residenza() {
        return id_Comune_Residenza;
    }

    public void setId_Comune_Residenza(Integer id_Comune_Residenza) {
        this.id_Comune_Residenza = id_Comune_Residenza;
    }

    public String getNomeComuneResidenza() {
        return nomeComuneResidenza;
    }

    public void setNomeComuneResidenza(String nomeComuneResidenza) {
        this.nomeComuneResidenza = nomeComuneResidenza;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getUltimaModifica() {
        return ultimaModifica;
    }

    public void setUltimaModifica(Date ultimaModifica) {
        this.ultimaModifica = ultimaModifica;
    }

    public String getUltimaProvinciaAdAggiornare() {
        return ultimaProvinciaAdAggiornare;
    }

    public void setUltimaProvinciaAdAggiornare(String ultimaProvinciaAdAggiornare) {
        this.ultimaProvinciaAdAggiornare = ultimaProvinciaAdAggiornare;
    }
}
