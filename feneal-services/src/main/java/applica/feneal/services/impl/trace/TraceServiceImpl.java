package applica.feneal.services.impl.trace;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.TraceLoginRepository;
import applica.feneal.domain.data.core.UserActivityTraceRepository;
import applica.feneal.domain.model.core.TraceLogin;
import applica.feneal.domain.model.core.UserActivityTrace;
import applica.feneal.domain.utils.Box;
import applica.feneal.services.TraceService;
import applica.framework.LoadRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by angelo on 17/11/2017.
 */
@Service
public class TraceServiceImpl implements TraceService {

    @Autowired
    private TraceLoginRepository traceLoginRepository;

    @Autowired
    private UserActivityTraceRepository userActivityTraceRepository;

    @Override
    public List<TraceLogin> retrieveTraceLogins() {
        return traceLoginRepository.find(LoadRequest.build()).getRows();
    }

    @Override
    public List<UserActivityTrace> retrieveUserActivitiesTrace(final Long userId) {

        final Box box = new Box();

        traceLoginRepository.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = traceLoginRepository.getSession();
                Transaction tx = null;

                try{
                    tx = s.beginTransaction();

                    String query = createQuery(userId);
                    List<Object[]> objects = s.createSQLQuery(query)


                            .addScalar("id")
                            .addScalar("operationDate")
                            .addScalar("operationMonth")
                            .addScalar("operationYear")
                            .addScalar("activity")
                            .addScalar("activityParams")


                            .list();

                    tx.commit();

                    List<UserActivityTrace> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        UserActivityTrace v = new UserActivityTrace();

                        v.setId(object[0]);
                        //v.setUser((String)object[1]);
                        v.setOperationDate((Date) object[1]);
                        v.setOperationMonth((String)object[2]);
                        v.setOperationYear((int)object[3]);
                        v.setActivity((String)object[4]);
                        v.setActivityParams((String)object[5]);

                        a.add(v);
                    }
                    box.setValue(a);
                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();
                }
                finally{
                    s.close();

                }
            }

            private String createQuery(Long userId ) {

                String query =  String.format("select \n" +
                        "i.id, " +
                        "i.operationDate, " +
                        "i.operationMonth, " +
                        "i.operationYear,\n" +
                        "i.activity, " +
                        "i.activityParams " +
                        "from fenealweb_useractivitytraces i \n" +
                        "where \n" +
                        "i.userId = %s " +
                        "order by i.operationDate desc", userId);
                return query;

            }
        });

        return (List<UserActivityTrace>)box.getValue();
    }

    @Override
    public void traceLogin(boolean isRemoteLogin, int year, String month, String company) {

        TraceLogin tracelogin = traceLoginRepository.find(LoadRequest.build()
                .filter("year", year)
                .filter("month", month)
                .filter("company", company)).findFirst().orElse(null);

        if (tracelogin == null) {
            tracelogin = new TraceLogin();
            tracelogin.setYear(year);
            tracelogin.setMonth(month);
            tracelogin.setCompany(company);
        }

        if (isRemoteLogin)
            tracelogin.setCounterApp(tracelogin.getCounterApp() + 1);
        else
            tracelogin.setCounterWebsite(tracelogin.getCounterWebsite() + 1);

        traceLoginRepository.save(tracelogin);
    }

    @Override
    public void traceActivity(UserActivityTrace userTraceActivity) {

        if (userTraceActivity != null)
            userActivityTraceRepository.save(userTraceActivity);
    }

    @Override
    public String getActivityDetail(Long id) {
        UserActivityTrace userActivityTrace = userActivityTraceRepository.get(id).orElse(null);

        if (userActivityTrace != null)
            return userActivityTrace.getActivityDetail();

        return "";
    }
}
