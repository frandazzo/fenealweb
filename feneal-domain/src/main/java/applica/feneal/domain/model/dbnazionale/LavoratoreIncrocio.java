package applica.feneal.domain.model.dbnazionale;

import java.util.Date;

public class LavoratoreIncrocio {
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
    private String Regioneterritorio;
    private String Settore;
    private String Ente;
    private String Azienda;
    private int Anno;
    private int Periodo;
    private Date DataEsportazione;


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

    public String getRegioneterritorio() {
        return Regioneterritorio;
    }

    public void setRegioneterritorio(String regioneterritorio) {
        Regioneterritorio = regioneterritorio;
    }

    public String getSettore() {
        return Settore;
    }

    public void setSettore(String settore) {
        Settore = settore;
    }

    public String getEnte() {
        return Ente;
    }

    public void setEnte(String ente) {
        Ente = ente;
    }

    public String getAzienda() {
        return Azienda;
    }

    public void setAzienda(String azienda) {
        Azienda = azienda;
    }

    public int getAnno() {
        return Anno;
    }

    public void setAnno(int anno) {
        Anno = anno;
    }

    public int getPeriodo() {
        return Periodo;
    }

    public Date getDataNascita() {
        return DataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        DataNascita = dataNascita;
    }

    public void setPeriodo(int periodo) {
        Periodo = periodo;
    }

    public Date getDataEsportazione() {
        return DataEsportazione;
    }

    public void setDataEsportazione(Date dataEsportazione) {
        DataEsportazione = dataEsportazione;
    }
}
