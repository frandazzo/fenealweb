package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.servizi.DocumentiAziendaRepository;
import applica.feneal.domain.model.core.servizi.DocumentoAzienda;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Writer;

/**
 * Applica
 * User: Angelo
 * Date: 28/04/16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AttachmentsFirmDocumentCellRenderer extends BaseCellRenderer {

    @Autowired
    private DocumentiAziendaRepository documentiAziendaRepository;

    @Override
    public void render(Writer writer, GridColumn column, Object value) {

        int numAttachments = 0;

        DocumentoAzienda document = documentiAziendaRepository.get(value).get();

        if (StringUtils.hasLength(document.getAllegato1()))
            numAttachments++;
        if (StringUtils.hasLength(document.getAllegato2()))
            numAttachments++;
        if (StringUtils.hasLength(document.getAllegato3()))
            numAttachments++;
        if (StringUtils.hasLength(document.getAllegato4()))
            numAttachments++;
        if (StringUtils.hasLength(document.getAllegato5()))
            numAttachments++;
        if (StringUtils.hasLength(document.getAllegato6()))
            numAttachments++;

        super.render(writer, column, numAttachments);
    }
}
