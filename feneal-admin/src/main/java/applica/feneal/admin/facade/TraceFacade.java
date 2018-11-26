package applica.feneal.admin.facade;

import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.trace.UIUserActivityTrace;
import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.UserActivityTrace;
import applica.feneal.services.TraceService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by angelo on 22/11/2017.
 */
@Component
public class TraceFacade {

    @Autowired
    private TraceService traceServ;

    public void traceActivity(User user, String activity, String activityDetail, String activityParams) {

        new Thread(() -> {
            try {
                UserActivityTrace userActivityTrace = new UserActivityTrace();

                int year = FenealDateUtils.getCurrentYear();
                int month = FenealDateUtils.getCurrentMonth();
                String monthDescr = FenealDateUtils.getDescriptionByMonthCode(month);
                userActivityTrace.setOperationYear(year);
                userActivityTrace.setOperationMonth(monthDescr);
                userActivityTrace.setOperationDate(new Date());
                userActivityTrace.setActivity(activity);
                if (StringUtils.hasLength(activityDetail)){
                    byte ptext[] = activityDetail.getBytes();
                    String value = new String(ptext, "UTF-8");
                    userActivityTrace.setActivityDetail(value );
                }

                userActivityTrace.setUser(user);
                userActivityTrace.setActivityParams(activityParams);
                traceServ.traceActivity(userActivityTrace);

            } catch (Exception ex){
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }).start();

    }

    public List<UIUserActivityTrace> retrieveUserActivitiesTrace(Long userId) {

        List<UserActivityTrace> traces = traceServ.retrieveUserActivitiesTrace(userId);


        List<UIUserActivityTrace> result = new ArrayList<>();

        for (UserActivityTrace trace : traces) {
            UIUserActivityTrace r = new UIUserActivityTrace();
            r.setActivity(trace.getActivity());
            r.setId(trace.getId().toString());
            r.setOperationDate(trace.getOperationDate());
            r.setOperationMonth(trace.getOperationMonth());
            r.setOperationYear(trace.getOperationYear());
            r.setActivityParams(trace.getActivityParams());
            result.add(r);
        }

        return result;
    }

}
