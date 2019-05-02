package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.core.deleghe.DelegaDownloadRequestRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.DelegaDownloadRequest;
import applica.feneal.services.ComunicazioniService;
import applica.feneal.services.DelegheDownloadAutorizationService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import applica.framework.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DelegheDownloadAutorizationServiceImpl implements DelegheDownloadAutorizationService {

    @Autowired
    private DelegheRepository delRep;

    @Autowired
    private Security sec;

    @Autowired
    private DelegaDownloadRequestRepository delDownRep;

    @Autowired
    private ComunicazioniService tcFacade;

    @Override
    public void requireAuthorizzationToDownloadDelega(long delegaId) {
        Delega f = delRep.find(LoadRequest.build().disableOwnershipQuery().filter("id", delegaId)).findFirst().orElse(null);
        if (f == null)
            return;
        if (f.getCompanyId() == ((applica.feneal.domain.model.User) sec.getLoggedUser()).getCompany().getLid())
            return;

        DelegaDownloadRequest t = new DelegaDownloadRequest();
        t.setAuthorized(false);
        t.setApplicant(((applica.feneal.domain.model.User) sec.getLoggedUser()));
        t.setApplicantCompanyId(((applica.feneal.domain.model.User) sec.getLoggedUser()).getCompany().getLid());
        t.setDate(new Date());
        t.setRequestId(UUID.randomUUID().toString());


        delDownRep.save(t);


        sendComunication(t);

    }

    private void sendComunication(DelegaDownloadRequest t) {
        //a qyuesto punto posso inviare una mail o un sms con il link
        //per prima cosa verifico se ho gli sms abilitati
        if (tcFacade.existSmsCredentials()){



        }

        //invio comunique una mail
    }

    @Override
    public void resendRequest(long delegaId) {
        DelegaDownloadRequest d =  getDelegaDownloadRequest(delegaId);

        if (d != null){
            sendComunication(d);
        }

    }

    @Override
    public boolean isAuthorizedToDownloadDelega(long idDelega) {

        //se il company id delea dleega è lo stesso dell'utent eloggato l'autorizzaizone è implicita
        Delega f = delRep.find(LoadRequest.build().disableOwnershipQuery().filter("id", idDelega)).findFirst().orElse(null);
        if (f == null)
            return false;

        if (f.getCompanyId() == ((applica.feneal.domain.model.User) sec.getLoggedUser()).getCompany().getLid())
            return true;

        //ricerco la richiesta di download
        //per la company dell'utnete loggato per verificarne l'autorizzazione
        DelegaDownloadRequest d = getDelegaDownloadRequest(idDelega);

        if (d == null)
            return false;

        return d.isAuthorized();
    }

    private DelegaDownloadRequest getDelegaDownloadRequest(long idDelega) {
        User u = ((applica.feneal.domain.model.User) sec.getLoggedUser());
        LoadRequest req = LoadRequest.build().filter("delega", idDelega).filter("applicantCompanyId", ((applica.feneal.domain.model.User) u).getCompany().getLid());

        return delDownRep.find(req).findFirst().orElse(null);
    }
    private DelegaDownloadRequest getDelegaDownloadRequest(String requestId) {

        LoadRequest req = LoadRequest.build().filter("requestId", requestId);

        return delDownRep.find(req).findFirst().orElse(null);
    }


    @Override
    public boolean hasAuthorizationRequestSent(long delegaId) {
        return getDelegaDownloadRequest(delegaId) != null;
    }

    @Override
    public void authorizeDownloadDelega(long delegaId, String requestId) {
        //verifioc che la richiesta recuperata per l'utente sia la stessa recuperata per requestid
        DelegaDownloadRequest a1 = getDelegaDownloadRequest(delegaId);
        DelegaDownloadRequest a2 = getDelegaDownloadRequest(requestId);

        if (a1 != null && a2 != null){
            if (a1.getLid() == a2.getLid()){
                a1.setAuthorized(true);
                delDownRep.save(a1);
            }
        }


    }
}
