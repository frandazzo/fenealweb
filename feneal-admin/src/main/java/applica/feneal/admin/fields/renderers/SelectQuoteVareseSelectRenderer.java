package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class SelectQuoteVareseSelectRenderer extends SelectFieldRenderer {

    @Autowired
    QuoteAssocRepository quoteRepository;

    public List<SimpleItem> getItems() {
        List<RiepilogoQuoteAssociative> provinces =
                quoteRepository.find(LoadRequest.build()
                        .sort("dataRegistrazione", true))
                        .getRows();


        SimpleDateFormat gg = new SimpleDateFormat("dd/MM/yyyy");

        return SimpleItem.createList(provinces,
                (a)-> String.format("%s-%s-%s-%s-%s",
                        a.getProvincia(),
                        gg.format(a.getDataRegistrazione()),
                        a.getSettore(),
                        a.getEnte(),
                        a.getCompetenza()),
                     (a) -> a.getSid());
    }
}
