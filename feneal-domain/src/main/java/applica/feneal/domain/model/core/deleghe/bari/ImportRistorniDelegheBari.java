package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;
import applica.feneal.domain.model.core.ristorniEdilizia.UiReferenti;

import java.util.List;

public class ImportRistorniDelegheBari {
    private String competenceYear;
    private String period;
    private String parithetic;
    private String file1;
    private String attachmentName;
    private List<UiReferenti> referentiList;
    private List<QuotaAssociativaBari> quoteAssocList;

    public List<QuotaAssociativaBari> getQuoteAssocList() {
        return quoteAssocList;
    }

    public void setQuoteAssocList(List<QuotaAssociativaBari> quoteAssocList) {
        this.quoteAssocList = quoteAssocList;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }


    public String getCompetenceYear() {
        return competenceYear;
    }

    public void setCompetenceYear(String competenceYear) {
        this.competenceYear = competenceYear;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getParithetic() {
        return parithetic;
    }

    public void setParithetic(String parithetic) {
        this.parithetic = parithetic;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public List<UiReferenti> getReferentiList() {
        return referentiList;
    }

    public void setReferentiList(List<UiReferenti> referentiList) {
        this.referentiList = referentiList;
    }
}
