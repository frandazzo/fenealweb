package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.AbstractContestoAttribuzione;
import applica.feneal.domain.model.RSU.Election.Attribuzione;

public class CalcolatoreAttribuzioneMinoriResti {

    private AbstractContestoAttribuzione _contesto;

    public CalcolatoreAttribuzioneMinoriResti(AbstractContestoAttribuzione contesto){
        this._contesto = contesto;
    }

    public void CalcolaAttribuzioneMinoriresti() throws Exception {
        String str = "";
        int rsuDaAttribuire = this._contesto.getRSUDaAttribuire();
        if (rsuDaAttribuire == 0)
        {
            this._contesto.setState(AbstractContestoAttribuzione.StatoContesto.Completo);
            this._contesto.setNote("Nessun RSU da attribuire al maggior resto");
            this._contesto.setRsuIndeterminate(0);
        }
        else
        {
            HelperAttribuzioneIndirettaContext indirettaContext = new HelperAttribuzioneIndirettaContext(this._contesto.getAttribuzioni());
            boolean flag = true;
            while (rsuDaAttribuire != 0)
            {
                double maxNumChiave = indirettaContext.getMaxNumChiave();
                if (maxNumChiave < 0d)
                {
                    if (this._contesto.getAttribuzioni().size() == 0)
                    {
                        str = "Nessuna lista presente poichè o non è firmataria di CCNL oppure ha ricevuto il seggio di solidarietà";
                        flag = false;
                        break;
                    }
                    str = String.format("Sono stati attribuiti RSU a tutte le liste ma rimangono ancora %s seggi da distribuire", rsuDaAttribuire);
                    flag = false;
                    break;
                }
                int occorrenza = indirettaContext.GetOccorrenza(maxNumChiave);
                if (occorrenza == 0)
                    throw new Exception("Errore imprevisto nell'algoritmo di calcolo attribuzione per maggior resto: Occorrenza nella hashtable delle occorrenze");
                if (occorrenza <= rsuDaAttribuire)
                {
                    for (HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem : indirettaContext.GetItems(maxNumChiave))
                    {
                        Attribuzione attribuzioneByLista = this._contesto.GetAttribuzioneByLista(attribuzioneIndirettaItem.getLista());
                        if (attribuzioneByLista == null)
                            throw new Exception("Errore imprevisto nell'algoritmo di calcolo attribuzione per maggior resto: Lista  non trovata nella collezione di liste");
                        attribuzioneByLista.setAttribuzioneMaggiorResto(1);
                        --rsuDaAttribuire;
                    }
                    indirettaContext.RemoveItems(maxNumChiave);
                }
                else
                {
                    flag = false;
                    str = String.format("Non è stato possibile assegnare gli RSU alle liste poichè ci sono liste con resti uguali e un numero di RSU non sufficiente da attribuire; Gli rSU ancora da attribuire sono %s;", String.valueOf(rsuDaAttribuire));
                    break;
                }
            }
            if (flag)
                this._contesto.setState(AbstractContestoAttribuzione.StatoContesto.Completo);
            else
                this._contesto.setState(AbstractContestoAttribuzione.StatoContesto.Indeterminato);
            this._contesto.setNote( str);
            this._contesto.setRsuIndeterminate(rsuDaAttribuire);
        }
    }
}
