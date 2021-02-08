package applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone;

import applica.feneal.domain.model.RSU.Election.Attribuzione;
import applica.feneal.domain.model.RSU.Election.ListaElettorale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class HelperAttribuzioneIndirettaContext {

    private ArrayList<HelperAttribuzioneIndirettaItem> items;
    private Hashtable occorrenze;

    public HelperAttribuzioneIndirettaContext(List<Attribuzione> votazioni)
    {
        this.items = new ArrayList();
        for (Attribuzione attribuzione :votazioni)
        this.items.add(new HelperAttribuzioneIndirettaItem(attribuzione.getVotazione().getLista(), attribuzione.getQuorum().getResto()));
        Collections.sort(this.items);
        this.CalcolaOccorrenze();
    }

    private void CalcolaOccorrenze()
    {
        this.occorrenze = new Hashtable();
        for (HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem : this.items)
        {
            if (this.occorrenze.containsKey( attribuzioneIndirettaItem.getNumeroChiave()))
            {
                int num = (int) this.occorrenze.get (attribuzioneIndirettaItem.getNumeroChiave());
                this.occorrenze.replace(attribuzioneIndirettaItem.getNumeroChiave(), (num + 1));
            }
            else
                this.occorrenze.put( attribuzioneIndirettaItem.getNumeroChiave(),  1);
        }
    }

    public ArrayList getItems(){
        return this.items;
    }

    public Hashtable getOccorrenze(){return this.occorrenze;}

    public int GetOccorrenza(double numChiave)
    {
        double num = numChiave;
        return this.occorrenze.contains(num) ? (int)this.occorrenze.get(num) : 0;
    }

    public List<HelperAttribuzioneIndirettaItem> GetItems(
            double numChiave)
    {
        List<HelperAttribuzioneIndirettaItem> attribuzioneIndirettaItemList = new ArrayList<HelperAttribuzioneIndirettaItem>();
        for (HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem: this.items)
        {
            if (attribuzioneIndirettaItem.getNumeroChiave() == numChiave)
                attribuzioneIndirettaItemList.add(attribuzioneIndirettaItem);
        }
        return attribuzioneIndirettaItemList;
    }

    public void RemoveItems(double numChiave)
    {
        for (HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem : this.GetItems(numChiave))
        this.RemoveItem(attribuzioneIndirettaItem.getLista());
        this.CalcolaOccorrenze();
    }

    private void RemoveItem(ListaElettorale lista)
    {
        for (HelperAttribuzioneIndirettaItem attribuzioneIndirettaItem :this.items)
        {
            if (attribuzioneIndirettaItem.getLista() == lista)
            {
                this.items.remove( attribuzioneIndirettaItem);
                break;
            }
        }
    }

    public double getMaxNumChiave(){
        return this.items.size() > 0 ? ((HelperAttribuzioneIndirettaItem)this.items.get(0)).getNumeroChiave() : -1d;
    }







}
