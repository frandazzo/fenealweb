package applica.feneal.domain.model.RSU.Election;

public class Esiti {
    public Esiti()
    {
        this.esitoVotazione = new EsitoVotazione();
        this.attribuzioneRSU = new EsitoAttribuzioneRSU();
    }

    private EsitoVotazione esitoVotazione;

    private EsitoAttribuzioneRSU attribuzioneRSU;

    public EsitoAttribuzioneRSU getAttribuzioneRSU() {
        return attribuzioneRSU;
    }

    public void setAttribuzioneRSU(EsitoAttribuzioneRSU attribuzioneRSU) {
        this.attribuzioneRSU = attribuzioneRSU;
    }

    public EsitoVotazione getEsitoVotazione() {
        return esitoVotazione;
    }

    public void setEsitoVotazione(EsitoVotazione esitoVotazione) {
        this.esitoVotazione = esitoVotazione;
    }
}
