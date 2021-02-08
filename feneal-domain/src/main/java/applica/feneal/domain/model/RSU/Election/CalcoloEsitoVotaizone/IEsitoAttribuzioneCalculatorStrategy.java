package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.ElezioneRSU;
import applica.feneal.domain.model.RSU.Election.EsitoAttribuzioneRSU;

public interface IEsitoAttribuzioneCalculatorStrategy {
    EsitoAttribuzioneRSU CalcolaEsitoAttribuzione(ElezioneRSU elezione) throws Exception;
}
