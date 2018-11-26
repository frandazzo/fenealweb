package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.core.Company;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by angelo on 17/04/16.
 */
@Component
public class CompanySelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Company> companies = companyRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(companies, (c) -> c.getDescription(), (c) -> String.valueOf(c.getId()));
    }
}
