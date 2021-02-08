package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.AbstractContestoAttribuzione;
import applica.feneal.domain.model.RSU.Election.ElezioneRSU;
import applica.feneal.domain.model.RSU.Election.EsitoAttribuzioneRSU;

public class CalcolatoreEsitoAttribuzione implements IEsitoAttribuzioneCalculatorStrategy{


    @Override
    public EsitoAttribuzioneRSU CalcolaEsitoAttribuzione(ElezioneRSU elezione) throws Exception {
        EsitoAttribuzioneRSU esitoAttribuzioneRsu = new EsitoAttribuzioneRSU();
        ContestoProporzionale contestoProporzionale = new ContestoProporzionale(elezione, elezione.CalcolaParametriAttribuzione());
        contestoProporzionale.CalcolaAttribuzioneSeggi();
        esitoAttribuzioneRsu.getContesti().add((AbstractContestoAttribuzione) contestoProporzionale);
        return esitoAttribuzioneRsu;
    }
}
