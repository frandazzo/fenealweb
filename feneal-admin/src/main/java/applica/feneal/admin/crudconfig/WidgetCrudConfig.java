package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.VisibleFunctionSelectRenderer;
import applica.feneal.admin.fields.renderers.WidgetContextSelectRenderer;
import applica.feneal.admin.fields.renderers.WidgetPositionRenderer;
import applica.feneal.admin.fields.renderers.WidgetTypeSelectRenderer;
import applica.feneal.domain.data.core.VisibleFunctionRepository;
import applica.feneal.domain.data.core.WidgetRepository;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class WidgetCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Widget.class, "widget")
                .repository(WidgetRepository.class)
                .field("description", "Descrizione")
                .field("longDescription", "Testo Tooltip")
                .field("type", "Tipo", WidgetTypeSelectRenderer.class)
                .field("context", "Contesto", WidgetContextSelectRenderer.class)
                .field("defaultPosition", "Quadrante di default", WidgetPositionRenderer.class)
                .field("defaultParams", "Parametri")
                .field("url", "URL");



        GridConfigurator.configure(Widget.class, "widget")
                .repository(WidgetRepository.class)
                .column("description", "Descrizione", true)
                .column("url", "URL", false)
                .column("type", "Tipo", false)
                .column("context", "Contesto", false);
    }
}
