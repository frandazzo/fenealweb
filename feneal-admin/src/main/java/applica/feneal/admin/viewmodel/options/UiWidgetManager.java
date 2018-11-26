package applica.feneal.admin.viewmodel.options;

import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.setting.option.UserOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/04/2016.
 */
public class UiWidgetManager {

    private List<UiEnableWidget> widgets;
    private String layout = UserOptions.layout_horizzontaUp;

    public List<UiEnableWidget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<UiEnableWidget> widgets) {
        this.widgets = widgets;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }



    public List<UiEnableWidget> calculateWidgetsListForQuadrante(int position){

        List<UiEnableWidget> list = new ArrayList<>();
        if (widgets != null) {
            list = widgets.stream().filter(f -> f.getPosition() == position).collect(Collectors.toList());
        }
        return list;

    }


    public String calculateBootstrapClassForFirstQuadrante(){

        if (layout.equals(UserOptions.layout_horizzontal))
            return "col-md-12";
        if (layout.equals(UserOptions.layout_horizzontaUp))
            return "col-md-12";
        if (layout.equals(UserOptions.layout_horizzontaDown))
            return "col-md-6";
        // if (layout.equals(UserOptions.layout_vertical))
        return "col-md-4";

    }

    public String calculateBootstrapClassForSecondQuadrante(){

        if (layout.equals(UserOptions.layout_horizzontal))
            return "col-md-12";
        if (layout.equals(UserOptions.layout_horizzontaUp))
            return "col-md-6";
        if (layout.equals(UserOptions.layout_horizzontaDown))
            return "col-md-6";
        // if (layout.equals(UserOptions.layout_vertical))
        return "col-md-4";

    }

    public String calculateBootstrapClassForThirdQuadrante(){

        if (layout.equals(UserOptions.layout_horizzontal))
            return "col-md-12";
        if (layout.equals(UserOptions.layout_horizzontaUp))
            return "col-md-6";
        if (layout.equals(UserOptions.layout_horizzontaDown))
            return "col-md-12";
        // if (layout.equals(UserOptions.layout_vertical))
        return "col-md-4";

    }

    public UiEnableWidget getWidgetByUrl(String url) {
        for (UiEnableWidget widget : widgets) {
            if (widget.getUrl().equals(url))
                return widget;
        }

        return null;
    }
}
