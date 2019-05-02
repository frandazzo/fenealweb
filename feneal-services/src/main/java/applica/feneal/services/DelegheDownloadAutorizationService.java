package applica.feneal.services;

public interface DelegheDownloadAutorizationService {

    void requireAuthorizzationToDownloadDelega(long delegaId);

    boolean isAuthorizedToDownloadDelega(long idDelega);

    boolean hasAuthorizationRequestSent(long delegaId);

    void authorizeDownloadDelega(long delegaId, String requestId );
}
