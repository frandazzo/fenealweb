package applica.feneal.domain.model.RSU.Dto;

public class EsitiDto {
    private EsitoVotazioneDto esitoVotazione;

    private EsitoAttribuzioneRsuDto attribuzioneRSU;

    public EsitoVotazioneDto getEsitoVotazione() {
        return esitoVotazione;
    }

    public void setEsitoVotazione(EsitoVotazioneDto esitoVotazione) {
        this.esitoVotazione = esitoVotazione;
    }

    public EsitoAttribuzioneRsuDto getAttribuzioneRSU() {
        return attribuzioneRSU;
    }

    public void setAttribuzioneRSU(EsitoAttribuzioneRsuDto attribuzioneRSU) {
        this.attribuzioneRSU = attribuzioneRSU;
    }
}
