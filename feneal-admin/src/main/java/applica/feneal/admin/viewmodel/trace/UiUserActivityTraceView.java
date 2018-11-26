package applica.feneal.admin.viewmodel.trace;

import applica.feneal.domain.model.core.UserActivityTrace;

import java.util.List;

public class UiUserActivityTraceView {

    private String content;
    private List<UIUserActivityTrace> traceActivities;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<UIUserActivityTrace> getTraceActivities() {
        return traceActivities;
    }

    public void setTraceActivities(List<UIUserActivityTrace> traceActivities) {
        this.traceActivities = traceActivities;
    }
}