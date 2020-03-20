package applica.feneal.admin.facade;

import applica.feneal.domain.model.core.deleghe.bari.StampaRistorniBariParams;
import applica.feneal.services.impl.deleghe.DelegheBariRistorniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import applica.feneal.admin.utils.RistroniBariExcelExporter;

import java.io.IOException;

@Component
public class DelegheBariFacade {

    @Autowired
    private RistroniBariExcelExporter exporter;

    @Autowired
    private DelegheBariRistorniService delBariRisServ;


    public String printRistorno (StampaRistorniBariParams params) throws IOException {

        return exporter.createExcelFile(params);
    }

    public void deleteRistorno(long id) {
        delBariRisServ.deleteRistorno(id);
    }


}
