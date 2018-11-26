package applica.feneal.admin.viewmodel.trace;

import java.util.Date;

/**
 * Created by fgran on 02/01/2018.
 */
public class UIUserActivityTrace {

    private String id;

    private Date operationDate;
    private String operationMonth;
    private int operationYear;
    private String activity;
    private String activityParams;
    public String getActivityParams() {
        return activityParams;
    }

    public void setActivityParams(String activityParams) {
        this.activityParams = activityParams;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
