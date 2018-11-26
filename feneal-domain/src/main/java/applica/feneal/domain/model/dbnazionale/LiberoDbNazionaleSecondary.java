package applica.feneal.domain.model.dbnazionale;

import applica.framework.IEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 18/07/2017.
 */
public class LiberoDbNazionaleSecondary extends IEntity {
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


    public boolean isDelegheOwner() {
        return delegheOwner;
    }

    public void setDelegheOwner(boolean delegheOwner) {
        this.delegheOwner = delegheOwner;
    }

    private transient boolean delegheOwner;



    private String currentAzienda;
    private String iscrittoA;
    private String nomeProvinciaFeneal;
    private Integer  id_ProvinciaFeneal;
    private Date liberoAl;
    private String ente;
    //campo utilizzato nel report liberi per tradurre il campo codice fiscale
    //nel campo idLavoratore nella tabella dei lavoratori
    //al fine di recuperare la lista delle iscrizioni riducendo il num di queries
    private transient Integer idWorker;

    public Integer getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Integer idWorker) {
        this.idWorker = idWorker;
    }

    private transient List<Iscrizione> iscrizioni = new ArrayList<>();

    public List<Iscrizione> getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(List<Iscrizione> iscrizioni) {
        this.iscrizioni = iscrizioni;
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

    public String getCurrentAzienda() {
        return currentAzienda;
    }

    public void setCurrentAzienda(String currentAzienda) {
        this.currentAzienda = currentAzienda;
    }

    public String getIscrittoA() {
        return iscrittoA;
    }

    public void setIscrittoA(String iscrittoA) {
        this.iscrittoA = iscrittoA;
    }

    public String getNomeProvinciaFeneal() {
        return nomeProvinciaFeneal;
    }

    public void setNomeProvinciaFeneal(String nomeProvinciaFeneal) {
        this.nomeProvinciaFeneal = nomeProvinciaFeneal;
    }

    public Integer getId_ProvinciaFeneal() {
        return id_ProvinciaFeneal;
    }

    public void setId_ProvinciaFeneal(Integer id_ProvinciaFeneal) {
        this.id_ProvinciaFeneal = id_ProvinciaFeneal;
    }

    public Date getLiberoAl() {
        return liberoAl;
    }

    public void setLiberoAl(Date liberoAl) {
        this.liberoAl = liberoAl;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public LiberoDbNazionale creteLiberoDBNazionale() {
        LiberoDbNazionale d = new LiberoDbNazionale();


        d.setCodiceFiscale(codiceFiscale);
        d.setNome(nome);
        d.setCognome(cognome);
        d.setNomeCompleto(nomeCompleto);
        d.setDataNascita(dataNascita);
        d.setSesso(sesso);// enum('FEMMINA','MASCHIO') DEFAULT 'MASCHIO',
        d.setId_Nazione(id_Nazione);
        d.setNomeNazione(nomeNazione);
        d.setId_Provincia(id_Provincia);
        d.setNomeProvincia(nomeProvincia);
        d.setId_Comune(id_Comune);
        d.setNomeComune(nomeComune);
        d.setId_Provincia_Residenza(id_Provincia_Residenza);
        d.setNomeProvinciaResidenza(nomeProvinciaResidenza);
        d.setId_Comune_Residenza(id_Comune_Residenza);
        d.setNomeComuneResidenza(nomeComuneResidenza);
        d.setIndirizzo(indirizzo);
        d.setCap(cap);
        d.setTelefono(telefono);
        d.setUltimaModifica(ultimaModifica);
        d.setUltimaProvinciaAdAggiornare(ultimaProvinciaAdAggiornare);



        d.setCurrentAzienda(currentAzienda);
        d.setIscrittoA(iscrittoA);
        d.setNomeProvinciaFeneal(nomeProvinciaFeneal);
        d.setId_ProvinciaFeneal(id_ProvinciaFeneal);
        d.setLiberoAl(liberoAl);
        d.setEnte(ente);




        return d;
    }
}

