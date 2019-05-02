package applica.feneal.data.hibernate.core.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.deleghe.DelegaDownloadRequestRepository;
import applica.feneal.domain.model.core.deleghe.DelegaDownloadRequest;
import applica.framework.LoadRequest;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class DelegaDownloadRequestHibernateRepository extends HibernateRepository<DelegaDownloadRequest> implements DelegaDownloadRequestRepository {




    @Override
    public void executeCommand(Command command) {
        command.execute();
    }




    @Override
    public Class<DelegaDownloadRequest> getEntityType() {
        return DelegaDownloadRequest.class;
    }



}

