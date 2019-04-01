package applica.feneal.domain.model.dbnazionale;

import applica.framework.AEntity;
import applica.framework.IEntity;

import java.util.Date;

/**
 * Created by fgran on 30/04/2016.
 */
public class Iscrizione extends IEntity {


    private int id_Lavoratore;
    private int id_Importazione;
    private int id_Provincia;
    private String nomeProvincia;
    private int id_Regione;
    private String nomeRegione;
    private String settore;
    private String ente;
    private String piva;
    private String azienda;
    private String livello;
    private double quota ;
    private String tipoPeriodo; //'Semester''Interval''Month') DEFAULT 'Semester'
    private int periodo;
    private int  anno;
    private Date dataInizio;
    private Date dataFine;
    private String contratto;

    private String nomeCompleto;
    private String codiceFiscale;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public int getId_Lavoratore() {
        return id_Lavoratore;
    }

    public void setId_Lavoratore(int id_Lavoratore) {
        this.id_Lavoratore = id_Lavoratore;
    }

    public int getId_Importazione() {
        return id_Importazione;
    }

    public void setId_Importazione(int id_Importazione) {
        this.id_Importazione = id_Importazione;
    }

    public int getId_Provincia() {
        return id_Provincia;
    }

    public void setId_Provincia(int id_Provincia) {
        this.id_Provincia = id_Provincia;
    }

    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public int getId_Regione() {
        return id_Regione;
    }

    public void setId_Regione(int id_Regione) {
        this.id_Regione = id_Regione;
    }

    public String getNomeRegione() {
        return nomeRegione;
    }

    public void setNomeRegione(String nomeRegione) {
        this.nomeRegione = nomeRegione;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public double getQuota() {
        return quota;
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
}
