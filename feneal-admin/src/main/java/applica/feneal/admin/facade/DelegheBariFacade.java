package applica.feneal.admin.facade;

import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import applica.feneal.admin.utils.RistroniBariExcelExporter;

import java.io.IOException;
import java.util.List;

@Component
public class DelegheBariFacade {

    @Autowired
    private RistroniBariExcelExporter exporter;


    public String printRistorno (List<QuotaAssociativaBari> listQuote) throws IOException {

        return exporter.createExcelFile(listQuote);
    }


}
