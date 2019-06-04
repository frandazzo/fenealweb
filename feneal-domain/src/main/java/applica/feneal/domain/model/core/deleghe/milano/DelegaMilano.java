package applica.feneal.domain.model.core.deleghe.milano;

import applica.framework.IEntity;

import java.util.Date;

public class DelegaMilano{

    private String numProgressivo;
    private String numProtocollo;
    private Date dataArchiviazione;
    private Date dataArrivo;
    private String cognome;
    private String nome;


    private String lavoratoreNomeCompleto;
    private String filename;
    private String filePath;

    public String getLavoratoreNomeCompleto() {
        return lavoratoreNomeCompleto;
    }

    public void setLavoratoreNomeCompleto(String lavoratoreNomeCompleto) {
        this.lavoratoreNomeCompleto = lavoratoreNomeCompleto;
    }

    private Date dataNascita;
    private String codiceLavoratore;
    private long barCode;
    private String codiceFiscale;
    private long codiceSind;
    private long codiceCompr;
    private Date dataConferma;
    private Date dataAdesione;
    private String numDelega;
    private String note;
    private String collaborator;

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public String getNumProgressivo() {
        return numProgressivo;
    }

    public void setNumProgressivo(String numProgressivo) {
        this.numProgressivo = numProgressivo;
    }

    public String getNumProtocollo() {
        return numProtocollo;
    }

    public void setNumProtocollo(String numProtocollo) {
        this.numProtocollo = numProtocollo;
    }

    public Date getDataArchiviazione() {
        return dataArchiviazione;
    }

    public void setDataArchiviazione(Date dataArchiviazione) {
        this.dataArchiviazione = dataArchiviazione;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public void setDataArrivo(Date dataArrivo) {
        this.dataArrivo = dataArrivo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCodiceLavoratore() {
        return codiceLavoratore;
    }

    public void setCodiceLavoratore(String codiceLavoratore) {
        this.codiceLavoratore = codiceLavoratore;
    }

    public long getBarCode() {
        return barCode;
    }

    public void setBarCode(long barCode) {
        this.barCode = barCode;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public long getCodiceSind() {
        return codiceSind;
    }

    public void setCodiceSind(long codiceSind) {
        this.codiceSind = codiceSind;
    }

    public long getCodiceCompr() {
        return codiceCompr;
    }

    public void setCodiceCompr(long codiceCompr) {
        this.codiceCompr = codiceCompr;
    }

    public Date getDataConferma() {
        return dataConferma;
    }

    public void setDataConferma(Date dataConferma) {
        this.dataConferma = dataConferma;
    }

    public Date getDataAdesione() {
        return dataAdesione;
    }

    public void setDataAdesione(Date dataAdesione) {
        this.dataAdesione = dataAdesione;
    }

    public String getNumDelega() {
        return numDelega;
    }

    public void setNumDelega(String numDelega) {
        this.numDelega = numDelega;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
