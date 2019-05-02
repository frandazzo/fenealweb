package applica.feneal.domain.data.core.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.deleghe.DelegaDownloadRequest;
import applica.framework.Repository;
import org.hibernate.Session;

public interface DelegaDownloadRequestRepository  extends Repository<DelegaDownloadRequest> {

    void executeCommand(Command command);

    Session getSession();



}