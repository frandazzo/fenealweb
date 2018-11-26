package applica.feneal.admin.viewmodel.app.dashboard.aziende;



import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.AppLavoratore;

import java.util.List;

/**
 * Created by fgran on 06/09/2016.
 */
public class AppAzienda {
    //queste sono le proprietà di default del tempalte devexpress
    //che verranno valorizzate di volta in volta in base alle necessità

    private String badge;
    private boolean disabled;
    private String html;
    private String key;
    private boolean showChevron;
    private String template;
    private String text;
    private boolean visible ;


    private String id;
    private String descrizione;
    private String provincia;
    private String comune;
    private String indirizzo;
    private String cap;

    private List<AppLavoratore> iscritti;
    private List<NonIscrittoAzienda> nonIscritti;


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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
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

    public List<AppLavoratore> getIscritti() {
        return iscritti;
    }

    public void setIscritti(List<AppLavoratore> iscritti) {
        this.iscritti = iscritti;
    }

    public List<NonIscrittoAzienda> getNonIscritti() {
        return nonIscritti;
    }

    public void setNonIscritti(List<NonIscrittoAzienda> nonIscritti) {
        this.nonIscritti = nonIscritti;
    }
}
