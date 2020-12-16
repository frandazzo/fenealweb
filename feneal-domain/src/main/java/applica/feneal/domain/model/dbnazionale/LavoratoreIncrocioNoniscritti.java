package applica.feneal.domain.model.dbnazionale;

import java.util.Date;

public class LavoratoreIncrocioNoniscritti {
    private String CodiceFiscale;
    private String Nome;
    private String Cognome;
    private Date DataNascita;
    private String NomeComuneResidenza;
    private String NomeProvinciaResidenza;
    private String Indirizzo;
    private String Cap;
    private String Telefono;
    private String TerritorioFeneal;
    private String Ente;
    private String CurrentAzienda;
    private String IscrittoA;
    private Date LiberoAl;

    public String getCodiceFiscale() {
        return CodiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        CodiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public Date getDataNascita() {
        return DataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        DataNascita = dataNascita;
    }

    public String getNomeComuneResidenza() {
        return NomeComuneResidenza;
    }

    public void setNomeComuneResidenza(String nomeComuneResidenza) {
        NomeComuneResidenza = nomeComuneResidenza;
    }

    public String getNomeProvinciaResidenza() {
        return NomeProvinciaResidenza;
    }

    public void setNomeProvinciaResidenza(String nomeProvinciaResidenza) {
        NomeProvinciaResidenza = nomeProvinciaResidenza;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

    public String getCap() {
        return Cap;
    }

    public void setCap(String cap) {
        Cap = cap;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getTerritorioFeneal() {
        return TerritorioFeneal;
    }

    public void setTerritorioFeneal(String territorioFeneal) {
        TerritorioFeneal = territorioFeneal;
    }

    public String getEnte() {
        return Ente;
    }

    public void setEnte(String ente) {
        Ente = ente;
    }

    public String getCurrentAzienda() {
        return CurrentAzienda;
    }

    public void setCurrentAzienda(String currentAzienda) {
        CurrentAzienda = currentAzienda;
    }

    public String getIscrittoA() {
        return IscrittoA;
    }

    public void setIscrittoA(String iscrittoA) {
        IscrittoA = iscrittoA;
    }

    public Date getLiberoAl() {
        return LiberoAl;
    }

    public void setLiberoAl(Date liberoAl) {
        LiberoAl = liberoAl;
    }
}
