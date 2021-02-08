package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

public class CalcolatoreAttribuzioneFactory {
    public static IEsitoAttribuzioneCalculatorStrategy GetStrategy(
            boolean applicaSolidarieta)
    {
        return applicaSolidarieta ? (IEsitoAttribuzioneCalculatorStrategy) null : (IEsitoAttribuzioneCalculatorStrategy) new CalcolatoreEsitoAttribuzione();
    }
}
