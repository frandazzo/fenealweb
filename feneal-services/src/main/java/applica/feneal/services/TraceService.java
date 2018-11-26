package applica.feneal.services;

import applica.feneal.domain.model.core.TraceLogin;
import applica.feneal.domain.model.core.UserActivityTrace;

import java.util.List;

/**
 * Created by angelo on 17/11/2017.
 */
public interface TraceService {

    List<TraceLogin> retrieveTraceLogins();

    List<UserActivityTrace> retrieveUserActivitiesTrace(final Long userId);

    void traceLogin(boolean isRemoteLogin, int year, String month, String company);

    void traceActivity(UserActivityTrace userActivityTrace);

    String getActivityDetail(Long id);
}
