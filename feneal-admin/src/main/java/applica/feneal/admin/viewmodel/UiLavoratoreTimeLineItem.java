package applica.feneal.admin.viewmodel;

import java.util.Date;

public class UiLavoratoreTimeLineItem {

    private Date data;
    private String dataStringa;
    private String periodo;
    private int anno;
    private String regione;
    private String provincia;
    private String ente;
    private String azienda;
    private String iscrittoA;


    private String livello;
    private String contratto;
    private String piva;

    private String stato;
    private int intStato;
    private String dataAccreditamento;
    private String dataCessazione;
    private String annotazioni;
    private long delegaId;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataStringa() {
        return dataStringa;
    }

    public void setDataStringa(String dataStringa) {
        this.dataStringa = dataStringa;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getIscrittoA() {
        return iscrittoA;
    }

    public void setIscrittoA(String iscrittoA) {
        this.iscrittoA = iscrittoA;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getIntStato() {
        return intStato;
    }

    public void setIntStato(int intStato) {
        this.intStato = intStato;
    }

    public String getDataAccreditamento() {
        return dataAccreditamento;
    }

    public void setDataAccreditamento(String dataAccreditamento) {
        this.dataAccreditamento = dataAccreditamento;
    }

    public String getDataCessazione() {
        return dataCessazione;
    }

    public void setDataCessazione(String dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    public String getAnnotazioni() {
        return annotazioni;
    }

    public void setAnnotazioni(String annotazioni) {
        this.annotazioni = annotazioni;
    }

    public long getDelegaId() {
        return delegaId;
    }

    public void setDelegaId(long delegaId) {
        this.delegaId = delegaId;
    }
}
