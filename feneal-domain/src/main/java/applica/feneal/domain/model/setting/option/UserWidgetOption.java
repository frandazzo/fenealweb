package applica.feneal.domain.model.setting.option;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.AEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class UserWidgetOption extends AEntity {


    private  Widget widget;

    private String widgetParams;
    private int widgetPosition;



    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String getWidgetParams() {
        return widgetParams;
    }

    public void setWidgetParams(String widgetParams) {
        this.widgetParams = widgetParams;
    }

    public int getWidgetPosition() {
        return widgetPosition;
    }

    public void setWidgetPosition(int widgetPosition) {
        this.widgetPosition = widgetPosition;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

}
