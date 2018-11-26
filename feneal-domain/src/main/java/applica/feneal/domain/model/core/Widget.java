package applica.feneal.domain.model.core;

import applica.framework.AEntity;

/**
 * Created by fgran on 05/04/2016.
 * Classe che definisce un widjet
 */
public class Widget extends AEntity {

    public static final String widget_locale = "locale";
    public static final String widget_regionale = "regionale";
    public static final String widget_nazionale = "nazionale";
    public static final String widget_all = "all";


    public static final String context_dashboard = "dashboard";
    public static final String context_lavoratore = "lavoratore";
    public static final String context_azienda = "azienda";



    private String description;
    //indica la posizione del widget in uno dei tre quadranti del layout
    private int defaultPosition;
    private String url;
    //indica la lista dei parametri di default per il widget
    private String defaultParams;
    //il tipo del widget di pende dal contesto in cui si trova
    //per definizione i widget nel contesto dashboard hanno una inizializzazione
    //che non contiene alcun paramtro. ATTENZIONE i dafultParams non sono i paramtri di inizializzazione
    //allo stesso modo i widget per il lavoratore hanno bisogno per essere inizializzati del'id del lavoratore
    private String type;

    //contesto in cui puo essere visualizzato un widget. Il contrìesto dipende dal ruolo dell'utente loggato
    private String context;

    //descrizione che andrà nella tooltip del widget dalla maschera delle opzioni
    private String longDescription;

    public int getDefaultPosition() {
        return defaultPosition;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setDefaultPosition(int defaultPosition) {
        this.defaultPosition = defaultPosition;
    }

    public String getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(String defaultParams) {
        this.defaultParams = defaultParams;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
