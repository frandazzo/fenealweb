package applica.feneal.admin.fields.renderers;

import applica.feneal.data.hibernate.core.servizi.DocumentoHibernateRepository;
import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Writer;
import java.util.List;

/**
 * Applica
 * User: Angelo
 * Date: 28/04/16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AttachmentsDocumentCellRenderer extends BaseCellRenderer {

    @Autowired
    private DocumentiRepository documentiRepository;

    @Override
    public void render(Writer writer, GridColumn column, Object value) {

        int numAttachments = 0;

        Documento document = documentiRepository.get(value).get();

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
