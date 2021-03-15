package applica.feneal.domain.model.core.RSU;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.Date;


public class VerbalizzazioneVotazione extends SecuredDomainEntity {
    private String note;
    private AziendaRSU aziendaRsu;
    private SedeRSU sedeRsu;
    private ContrattoRSU contrattoRSU;
    private String verbalizzazione;
    private String nomeverbalizzazione;
    private String risultatoVotazione;
    private String nomerisultatoVotazione;
    private Date dataVotazione;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AziendaRSU getAziendaRsu() {
        return aziendaRsu;
    }

    public void setAziendaRsu(AziendaRSU aziendaRsu) {
        this.aziendaRsu = aziendaRsu;
    }

    public SedeRSU getSedeRsu() {
        return sedeRsu;
    }

    public void setSedeRsu(SedeRSU sedeRsu) {
        this.sedeRsu = sedeRsu;
    }

    public ContrattoRSU getContrattoRSU() {
        return contrattoRSU;
    }

    public void setContrattoRSU(ContrattoRSU contrattoRSU) {
        this.contrattoRSU = contrattoRSU;
    }

    public String getVerbalizzazione() {
        return verbalizzazione;
    }

    public void setVerbalizzazione(String verbalizzazione) {
        this.verbalizzazione = verbalizzazione;
    }

    public String getNomeverbalizzazione() {
        return nomeverbalizzazione;
    }

    public void setNomeverbalizzazione(String nomeverbalizzazione) {
        this.nomeverbalizzazione = nomeverbalizzazione;
    }

    public String getRisultatoVotazione() {
        return risultatoVotazione;
    }

    public void setRisultatoVotazione(String risultatoVotazione) {
        this.risultatoVotazione = risultatoVotazione;
    }

    public String getNomerisultatoVotazione() {
        return nomerisultatoVotazione;
    }

    public void setNomerisultatoVotazione(String nomerisultatoVotazione) {
        this.nomerisultatoVotazione = nomerisultatoVotazione;
    }

    public Date getDataVotazione() {
        return dataVotazione;
    }

    public void setDataVotazione(Date dataVotazione) {
        this.dataVotazione = dataVotazione;
    }
}
