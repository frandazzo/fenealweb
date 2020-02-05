package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.feneal.domain.model.core.deleghe.bari.ImportRistorniDelegheBari;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.AEntity;

import java.util.Date;

public class Ristorno extends SecuredDomainEntity {
    private Date dataRistorno;
    private String anno;
    private String ente;
    private String attachment;
    private String attachmentName;
    private String quoteserialaized;

    public String getQuoteserialaized() {
        return quoteserialaized;
    }

    public void setQuoteserialaized(String quoteserialaized) {
        this.quoteserialaized = quoteserialaized;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Date getDataRistorno() {
        return dataRistorno;
    }

    public void setDataRistorno(Date dataRistorno) {
        this.dataRistorno = dataRistorno;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }


    public static Ristorno createFromImportazione(ImportRistorniDelegheBari importazione, long companyId, String quoteserialaized) {
        Ristorno a = new Ristorno();
        a.setCompanyId(companyId);
        a.setAnno(importazione.getCompetenceYear());
        a.setDataRistorno(new Date());
        a.setEnte(importazione.getParithetic());
        a.setAttachment(importazione.getFile1());
        a.setAttachmentName(importazione.getAttachmentName());
        a.setQuoteserialaized(quoteserialaized);

        return a;
    }

}
