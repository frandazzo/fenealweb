package applica.feneal.admin.viewmodel.options;

import applica.feneal.domain.model.core.Widget;

import java.util.List;

/**
 * Created by fgran on 11/04/2016.
 */
public class UiEnableWidget {

    private long id;
    private String widgetName;
    private boolean present;
    private String longDescription;
    private String params;
    private int position;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }


    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getParams() {
        if (params == null)
            return "";
        return params;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
