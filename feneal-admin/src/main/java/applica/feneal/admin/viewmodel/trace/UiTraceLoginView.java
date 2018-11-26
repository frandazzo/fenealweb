package applica.feneal.admin.viewmodel.trace;

import applica.feneal.domain.model.core.TraceLogin;

import java.util.List;

public class UiTraceLoginView {

    private String content;
    private List<TraceLogin> traceLogins;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TraceLogin> getTraceLogins() {
        return traceLogins;
    }

    public void setTraceLogins(List<TraceLogin> traceLogins) {
        this.traceLogins = traceLogins;
    }
}
