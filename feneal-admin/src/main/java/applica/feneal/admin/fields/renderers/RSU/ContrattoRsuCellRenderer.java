package applica.feneal.admin.fields.renderers.RSU;

import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;

@Component
public class ContrattoRsuCellRenderer  extends BaseCellRenderer {

    @Autowired
    ContrattoRSURepository contrattoRSURepository;

    @Override
    public void render(Writer writer, GridColumn column, Object value) {

        ContrattoRSU c = contrattoRSURepository.get(value).get();

        String descriptionM = c.getDescription();

        super.render(writer, column, descriptionM);
    }
}
