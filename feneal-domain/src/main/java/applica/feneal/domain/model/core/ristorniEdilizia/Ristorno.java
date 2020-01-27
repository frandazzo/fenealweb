package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.framework.AEntity;

import java.util.Date;

public class Ristorno extends AEntity {
    private Date dataRistorno;
    private String anno;
    private String ente;
    private String attachment;
    private String nomeAttachement;


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

    public String getNomeAttachement() {
        return nomeAttachement;
    }

    public void setNomeAttachement(String nomeAttachement) {
        this.nomeAttachement = nomeAttachement;
    }
}
