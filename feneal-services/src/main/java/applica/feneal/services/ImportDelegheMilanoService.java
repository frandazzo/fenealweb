package applica.feneal.services;


import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.feneal.domain.model.core.deleghe.milano.DelegheMilanoObject;

import java.util.Date;
import java.util.List;

public interface ImportDelegheMilanoService {

    DelegheMilanoObject importDelegheMilano(String importData) throws Exception;


    void executeImportDelegheMilano(List<DelegaMilano> deleghe) throws Exception;
    boolean existDelega(String fiscalCode, Date date);


}
