package applica.feneal.admin.viewmodel.app.dashboard.lavoratori;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 06/09/2016.
 */
public class AppLavoratore {

    //queste sono le proprietà di default del tempalte devexpress
    //che verranno valorizzate di volta in volta in base alle necessità

    private String badge;
    private boolean disabled;
    private String html;
    private String key;
    private boolean showChevron;
    private String template;
    private String text;
    private boolean visible;

    //proprietà lavoratore
    private String id;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String fiscale;
    private String sesso;
    private String provinciaResidenza;
    private String comuneResidenza;
    private String nazione;
    private String provinciaNascita;
    private String comuneNascita;
    private String indirizzo;
    private String cap;
    private String cellulare;
    private String mail;
    private String telefono;


    private String[] stampeTessera = new String[]{};
    private List<QuotaLavoratore> quote = new ArrayList<>();
    private List<DelegaLavoratore> deleghe = new ArrayList<>();
    private List<MagazzinoLavoratore> magazzino = new ArrayList<>();
    private List<NonIscrittoLavoratore> nonIscrizioni = new ArrayList<>();
    private List<IscrittoLavoratore> iscrizioni = new ArrayList<>();

    //dato sull'iscrizione corrente
    private IscrittoLavoratore iscrizioneCorrente;
    //flag che indica se è stata stampata la tessera nall'anno corrente
    private boolean tesseraStampata;
    private long dataNascitaTime;

    public IscrittoLavoratore getIscrizioneCorrente() {
        return iscrizioneCorrente;
    }

    public void setIscrizioneCorrente(IscrittoLavoratore iscrizioneCorrente) {
        this.iscrizioneCorrente = iscrizioneCorrente;
    }

    public boolean isTesseraStampata() {
        return tesseraStampata;
    }

    public void setTesseraStampata(boolean tesseraStampata) {
        this.tesseraStampata = tesseraStampata;
    }

    public String[] getStampeTessera() {
        return stampeTessera;
    }

    public void setStampeTessera(String[] stampeTessera) {
        this.stampeTessera = stampeTessera;
    }

    public List<QuotaLavoratore> getQuote() {
        return quote;
    }

    public void setQuote(List<QuotaLavoratore> quote) {
        this.quote = quote;
    }

    public List<DelegaLavoratore> getDeleghe() {
        return deleghe;
    }

    public void setDeleghe(List<DelegaLavoratore> deleghe) {
        this.deleghe = deleghe;
    }

    public List<MagazzinoLavoratore> getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(List<MagazzinoLavoratore> magazzino) {
        this.magazzino = magazzino;
    }

    public List<NonIscrittoLavoratore> getNonIscrizioni() {
        return nonIscrizioni;
    }

    public void setNonIscrizioni(List<NonIscrittoLavoratore> nonIscrizioni) {
        this.nonIscrizioni = nonIscrizioni;
    }

    public List<IscrittoLavoratore> getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(List<IscrittoLavoratore> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isShowChevron() {
        return showChevron;
    }

    public void setShowChevron(boolean showChevron) {
        this.showChevron = showChevron;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getFiscale() {
        return fiscale;
    }

    public void setFiscale(String fiscale) {
        this.fiscale = fiscale;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getProvinciaNascita() {
        return provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita) {
        this.provinciaNascita = provinciaNascita;
    }

    public String getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
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

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDataNascitaTime(long dataNascitaTime) {
        this.dataNascitaTime = dataNascitaTime;
    }

    public long getDataNascitaTime() {
        return dataNascitaTime;
    }
}
