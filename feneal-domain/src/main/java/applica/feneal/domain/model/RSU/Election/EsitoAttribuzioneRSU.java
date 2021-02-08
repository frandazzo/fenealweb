package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Election.Math.DivisioneDecimaleConQuozienteInteroEResto;

import java.util.ArrayList;
import java.util.List;

public class EsitoAttribuzioneRSU {

    private List<AbstractContestoAttribuzione> contesti = new ArrayList<AbstractContestoAttribuzione>();

    public List<AbstractContestoAttribuzione> getContesti(){
        return this.contesti;
    }

    public List<Attribuzione> getRiepilogoAttribuzioni(ElezioneRSU elezione)
    {
        List<Attribuzione> attribuzioneList = new ArrayList<Attribuzione>();
        for (Votazione votazione : elezione.getEsiti().getEsitoVotazione().getVotazioni())
        attribuzioneList.add(this.CreateAttribuzione(votazione));
        return attribuzioneList;
    }

    private Attribuzione CreateAttribuzione(Votazione item)
    {
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        for (AbstractContestoAttribuzione contestoAttribuzione : this.contesti)
        {
            Attribuzione attribuzioneByLista = contestoAttribuzione.GetAttribuzioneByLista(item.getLista());
            if (attribuzioneByLista != null)
            {
                num1 += attribuzioneByLista.getAttribuzioneDiretta();
                num2 += attribuzioneByLista.getAttribuzioneMaggiorResto();
                num3 += attribuzioneByLista.getAttribuzioneSolidarieta();
            }
        }
        Attribuzione att = new Attribuzione(item, (DivisioneDecimaleConQuozienteInteroEResto) null);

            att.setAttribuzioneSolidarieta(num3);
            att.setAttribuzioneMaggiorResto(num2);
            att.setAttribuzioneDiretta(num1);
        return att;
    }


}
