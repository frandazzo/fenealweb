package applica.feneal.admin.viewmodel.app.dashboard.lavoratori;

import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
public class UiLavoratoriForExport {

    private List<String> fiscalCodeRows;
    private String context;

    public List<String> getFiscalCodeRows() {
        return fiscalCodeRows;
    }

    public void setFiscalCodeRows(List<String> fiscalCodeRows) {
        this.fiscalCodeRows = fiscalCodeRows;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
