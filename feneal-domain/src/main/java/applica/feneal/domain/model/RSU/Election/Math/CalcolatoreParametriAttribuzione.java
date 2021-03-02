package applica.feneal.domain.model.RSU.Election.Math;

import applica.feneal.domain.model.RSU.Election.EsitoVotazione;
import applica.feneal.domain.model.RSU.Election.ParametriAttribuzioneRSU;
import applica.feneal.domain.model.RSU.Election.PattoSolidarieta;

public class CalcolatoreParametriAttribuzione {

    public enum TipoParziale
    {
        AttribuzioneProporzionale,
        AttribuzioneProporzionale1_3,
        AttribuzioneSolidarieta,
        AttribuzioneProporzionale2_3,
    }

    public static ParametriAttribuzioneRSU CalcolaParametriAttribuzione(
            EsitoVotazione votazione,
            PattoSolidarieta solidarieta) throws Exception {
        if (!votazione.checkValidation())
            throw new Exception(votazione.getValidationError());
        ParametriAttribuzioneRSU p = new ParametriAttribuzioneRSU();
        p.setQuozienteElettorale(CalcolatoreParametriAttribuzione.CalcolaQuoziente(TipoParziale.AttribuzioneProporzionale, votazione));
        //p.QuozienteElettorale2_3 = CalcolatoreParametriAttribuzione.CalcolaQuoziente(TipoParziale.AttribuzioneProporzionale2_3, votazione);
        //p.QuozienteElettorale1_3 = CalcolatoreParametriAttribuzione.CalcolaQuoziente(TipoParziale.AttribuzioneProporzionale1_3, votazione);
        CalcolatoreParametriAttribuzione.CalcolaElegibilitàParziale(votazione, p);
        CalcolatoreParametriAttribuzione.CalcolaSoglieDiSbarramento(solidarieta, votazione, p);
        return p;
    }
    private static void CalcolaSoglieDiSbarramento(
            PattoSolidarieta solidarieta,
            EsitoVotazione votazione,
            ParametriAttribuzioneRSU p)
    {
        int num1 = solidarieta.getAccordoSolidarieta() == PattoSolidarieta.TipoAccordoSolidarieta.Categoria ? solidarieta.getPercentualeSogliaCategoria() : solidarieta.getPercentualeSogliaInterconfederale();
        int num2 = solidarieta.getAccordoSolidarieta() == PattoSolidarieta.TipoAccordoSolidarieta.Categoria ? solidarieta.getPercentualeSogliaInCasoListaDominante() : 0;
        p.setSogliaDiSbarramento(Math.round(p.getQuozienteElettorale() / 100d * (double) num1)*100.0/100.0);
        p.setSogliaDiSbarramentoListaDominante(Math.round(p.getQuozienteElettorale() / 100d * (double) num2)*100.0/100.0);
    }

    private static void CalcolaElegibilitàParziale(
            EsitoVotazione votazione,
            ParametriAttribuzioneRSU p)
    {

        RsuEleggibili dd = CalcolatoreParametriAttribuzione.CalcolaRsuEleggibili(votazione);
        p.setrSUElegibili1_3((int)(dd.rsueleggibili1_3));
        p.setrSUElegibili2_3((int)(dd.rsueleggibili2_3));
    }

    private static double CalcolaQuoziente(
            TipoParziale tipoParziale,
            EsitoVotazione votazione) throws Exception {
        double schedeValide = (double) votazione.getSchedeValide();
        double rsuElegibili = (double) votazione.getRSUElegibili();

        RsuEleggibili dd = CalcolatoreParametriAttribuzione.CalcolaRsuEleggibili(votazione);

            if (tipoParziale== TipoParziale.AttribuzioneProporzionale)
                return Math.round((schedeValide / rsuElegibili)*100.0)/100.0;
        if (tipoParziale==  TipoParziale.AttribuzioneProporzionale1_3)
                return Math.round((schedeValide / dd.rsueleggibili1_3) * 100.0)/100.0;
        if (tipoParziale==  TipoParziale.AttribuzioneSolidarieta)
                throw new Exception("Operazione non consentita");
        if (tipoParziale==  TipoParziale.AttribuzioneProporzionale2_3)
                return Math.round((schedeValide / dd.rsueleggibili2_3) * 100.0)/100.0;
//            default:
                throw new Exception("Tipo parziale sconosciuto");

    }

    private static RsuEleggibili CalcolaRsuEleggibili(
            EsitoVotazione votazione)
    {
        double schedeValide = (double) votazione.getSchedeValide();
        double rsuElegibili = (double) votazione.getRSUElegibili();
        RsuEleggibili dd = new RsuEleggibili();
        dd.rsueleggibili2_3 = Math.floor(Math.round((rsuElegibili * 2d / 3d) * 100.0)/100.0);
        dd.rsueleggibili1_3 = rsuElegibili - dd.rsueleggibili2_3;
        return dd;
    }

}

class RsuEleggibili{
    public  double rsueleggibili2_3;
    public double rsueleggibili1_3;
}
