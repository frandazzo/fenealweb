package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.*;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import applica.feneal.domain.model.RSU.Election.Math.DivisioneDecimaleConQuozienteInteroEResto;

public class ContestoProporzionale extends AbstractContestoAttribuzione {
    protected ContestoProporzionale(ElezioneRSU elezione, ParametriAttribuzioneRSU parametriAttribuzione) throws Exception {
        super(elezione, parametriAttribuzione);
        for (Votazione votazione : elezione.getEsiti().getEsitoVotazione().getVotazioni())
        this._attribuzioni.add(new Attribuzione(votazione, new DivisioneDecimaleConQuozienteInteroEResto((double) votazione.getVoti(), this._parametri.getQuozienteElettorale())));
    }

    @Override
    public void CalcolaAttribuzioneSeggi() throws Exception {
        this.CalcolaAttribuzioneDiretta();
        new CalcolatoreAttribuzioneMinoriResti((AbstractContestoAttribuzione) this).CalcolaAttribuzioneMinoriresti();

    }

    @Override
    public double getQuozienteElettorale() {
        return this._parametri.getQuozienteElettorale();
    }

    @Override
    public int getRSUEleggibili() {
        return this._parametri.getrSUElegibili1_3() + this._parametri.getrSUElegibili2_3();
    }

    @Override
    public CalcolatoreParametriAttribuzione.TipoParziale getTipo() {
        return CalcolatoreParametriAttribuzione.TipoParziale.AttribuzioneProporzionale;
    }
}
