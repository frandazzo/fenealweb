package applica.feneal.services;

public interface DelegheDownloadAutorizationService {

    void requireAuthorizzationToDownloadDelega(long delegaId) throws Exception;

    void resendRequest(long delegaId) throws Exception;

    boolean isAuthorizedToDownloadDelega(long idDelega);

    boolean hasAuthorizationRequestSent(long delegaId);

    void authorizeDownloadDelega(String requestId );
}
