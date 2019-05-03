package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.core.deleghe.DelegaDownloadRequestRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.DelegaDownloadRequest;
import applica.feneal.services.ComunicazioniService;
import applica.feneal.services.DelegheDownloadAutorizationService;
import applica.feneal.services.MessageService;
import applica.feneal.services.messages.MessageInput;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import applica.framework.security.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Autowired
    private MessageService msgServ;

    @Autowired
    private OptionsManager optMan;

    @Override
    public void requireAuthorizzationToDownloadDelega(long delegaId) throws Exception {
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
        t.setDelega(f);

        delDownRep.save(t);


        trySendComunication(t);

    }

    private void trySendComunication(DelegaDownloadRequest t) throws Exception {
        //verifico la presenza  di una mail e del cell del segretario
        Company c = ((applica.feneal.domain.model.User) sec.getLoggedUser()).getCompany();

        String mail = c.getSegretarioMail();
        String cell = c.getSegretarioCell();


        if (StringUtils.isEmpty(mail) && StringUtils.isEmpty(cell))
            return;

        //ors  che posso inviare un messaggio creo il testo della mail...
        String url = optMan.get("feneal.downloadauthorizationpath") + t.getRequestId();
        String currentUser = ((applica.feneal.domain.model.User) sec.getLoggedUser()).getCompleteName();
        String currentCompany = c.getDescription();

        String content = String.format("La %s (%s) vorrebbe scaricare la delega di %s. Autorizza qui: %s",
                 currentCompany, currentUser, t.getDelega().getWorker().getSurnamename(), url);


        sendComunication(t,mail,cell, content);


    }

    private void sendComunication(DelegaDownloadRequest t, String mail, String cell, String content) throws Exception {
        //a qyuesto punto posso inviare una mail o un sms con il link
       //invio prima la mail e poi verifico se inviare anche il messaggio con cellulare
        if (!StringUtils.isEmpty(mail)){
            MessageInput mailInput = new MessageInput();
            mailInput.setContent(content);
            mailInput.setMailFrom("fenealgest@gmail.com");
            mailInput.setRecipients(Arrays.asList(mail));
            mailInput.setSubject("Richiesta autorizzazione per scaricare mail");
            msgServ.sendSimpleMail(mailInput);

        }


        if (!StringUtils.isEmpty(cell)){
            if (tcFacade.existSmsCredentials()){

                msgServ.sendSms(cell, content);

            }
        }



    }

    @Override
    public void resendRequest(long delegaId) throws Exception {
        DelegaDownloadRequest d =  getDelegaDownloadRequest(delegaId);

        if (d != null){
            trySendComunication(d);
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
    public void authorizeDownloadDelega( String requestId) {

        DelegaDownloadRequest a2 = getDelegaDownloadRequest(requestId);

        if (a2 != null){

                a2.setAuthorized(true);
                delDownRep.save(a2);

        }


    }
}
