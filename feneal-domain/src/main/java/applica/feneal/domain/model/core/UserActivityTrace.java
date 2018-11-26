package applica.feneal.domain.model.core;

import applica.feneal.domain.model.User;
import applica.framework.AEntity;

import java.util.Date;

/**
 * Created by angelo on 22/11/2017.
 */
public class UserActivityTrace extends AEntity {

    private User user;
    private Date operationDate;
    private String operationMonth;
    private int operationYear;
    private String activity;

    public String getActivityParams() {
        return activityParams;
    }

    public void setActivityParams(String activityParams) {
        this.activityParams = activityParams;
    }

    private String activityParams;
    private String activityDetail;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationMonth() {
        return operationMonth;
    }

    public void setOperationMonth(String operationMonth) {
        this.operationMonth = operationMonth;
    }

    public int getOperationYear() {
        return operationYear;
    }

    public void setOperationYear(int operationYear) {
        this.operationYear = operationYear;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }
}
