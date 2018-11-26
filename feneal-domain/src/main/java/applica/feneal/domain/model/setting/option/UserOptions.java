package applica.feneal.domain.model.setting.option;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/04/2016.
 */
public class UserOptions extends SecuredDomainEntity{

    //tipi di layout per contenere i widgets
    public static final String layout_horizzontal = "h";
    public static final String layout_vertical = "v";
    public static final String layout_horizzontaUp = "hu";
    public static final String layout_horizzontaDown = "hd";

    private User user;



    private List<UserWidgetOption> widgets = new ArrayList<>();

    private String dashboardLayout = layout_vertical;
    private String workerLayout = layout_horizzontal;
    private String firmLayout = layout_horizzontal;



    public List<UserWidgetOption> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<UserWidgetOption> widgets) {
        this.widgets = widgets;
    }

    public void removeWiget(Widget widget, String context){
        List<UserWidgetOption> opt = null;

        if (context.equals(Widget.context_dashboard)){
            opt = getDashboardWidgets();
        }else if (context.equals(Widget.context_lavoratore)){
            opt = getWorkerdWidget();
        }else if (context.equals(Widget.context_azienda)){
            opt = getFirmWidgets();
        }

        if (opt == null)
            return;

        UserWidgetOption a = null;
        for (UserWidgetOption option : opt) {
            if (widget.getLid() == option.getWidget().getLid()){
                a = option;
                break;
            }
        }
        if (a != null)
            widgets.remove(a);
    }

    public void addOrUpdateWidgetOption(String context, Widget widget, String params){

        List<UserWidgetOption> opt = null;

        if (context.equals(Widget.context_dashboard)){
            opt = getDashboardWidgets();
        }else if (context.equals(Widget.context_lavoratore)){
            opt = getWorkerdWidget();
        }else if (context.equals(Widget.context_azienda)){
            opt = getFirmWidgets();
        }

        if (opt == null)
            return;


        for (UserWidgetOption option : opt) {
            if (widget.getLid() == option.getWidget().getLid()){
                option.setWidgetParams(params);
                return;
            }
        }

        UserWidgetOption o = new UserWidgetOption();
        o.setWidget(widget);
        o.setWidgetParams(widget.getDefaultParams());
        o.setWidgetPosition(widget.getDefaultPosition());
        o.setContext(context);
        widgets.add(o);


    }


    public String getDashboardLayout() {
        return dashboardLayout;
    }

    public void setDashboardLayout(String dashboardLayout) {
        this.dashboardLayout = dashboardLayout;
    }

    public String getWorkerLayout() {
        return workerLayout;
    }

    public void setWorkerLayout(String workerLayout) {
        this.workerLayout = workerLayout;
    }

    public String getFirmLayout() {
        return firmLayout;
    }

    public void setFirmLayout(String firmLayout) {
        this.firmLayout = firmLayout;
    }

    public List<UserWidgetOption> getDashboardWidgets() {
        return widgets.stream().filter(p -> p.getContext().equals(Widget.context_dashboard)).collect(Collectors.toList());
    }



    public List<UserWidgetOption> getWorkerdWidget() {
        return widgets.stream().filter(p -> p.getContext().equals(Widget.context_lavoratore)).collect(Collectors.toList());
    }


    public List<UserWidgetOption> getFirmWidgets() {
        return widgets.stream().filter(p -> p.getContext().equals(Widget.context_azienda)).collect(Collectors.toList());
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean contains(Widget widget, String context) {
        List<UserWidgetOption> data = getDashboardWidgets();
        if (context.equals(Widget.context_azienda))
            data = getFirmWidgets();
        else if (context.equals(Widget.context_lavoratore))
            data = getWorkerdWidget();

        for (UserWidgetOption wWidgetOpt : data) {
            if (wWidgetOpt.getWidget().getLid() == widget.getLid())
                return true;
        }

        return false;
    }

    public String getLayout(String context) {
        if (context.equals(Widget.context_azienda))
            return getFirmLayout();
        else if (context.equals(Widget.context_lavoratore))
            return getWorkerLayout();

        return getDashboardLayout();
    }

    public Widget findWidget(String description, String context) {
        List<UserWidgetOption> data = getDashboardWidgets();
        if (context.equals(Widget.context_azienda))
            data = getFirmWidgets();
        else if (context.equals(Widget.context_lavoratore))
            data = getWorkerdWidget();

        for (UserWidgetOption wWidgetOpt : data) {
            if (wWidgetOpt.getWidget().getDescription().equals(description))
                return wWidgetOpt.getWidget();
        }

        return null;
    }

    public UserWidgetOption findWidgetOptions(String description, String context) {
        List<UserWidgetOption> data = getDashboardWidgets();
        if (context.equals(Widget.context_azienda))
            data = getFirmWidgets();
        else if (context.equals(Widget.context_lavoratore))
            data = getWorkerdWidget();

        for (UserWidgetOption wWidgetOpt : data) {
            if (wWidgetOpt.getWidget().getDescription().equals(description))
                return wWidgetOpt;
        }

        return null;
    }
}
