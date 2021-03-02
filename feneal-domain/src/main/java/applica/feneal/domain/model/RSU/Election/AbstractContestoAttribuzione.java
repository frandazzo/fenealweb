package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContestoAttribuzione {

    protected ElezioneRSU _elezione;
    protected ParametriAttribuzioneRSU _parametri;
    protected List<Attribuzione> _attribuzioni =  new ArrayList<Attribuzione>();
    protected AbstractContestoAttribuzione.StatoContesto _stato = AbstractContestoAttribuzione.StatoContesto.NonCalcolato;
    protected boolean _criterioListaDominante;

    public  void setState(AbstractContestoAttribuzione.StatoContesto stato) {
        this._stato = stato;
    }

    public abstract void CalcolaAttribuzioneSeggi() throws Exception;

    public List<Attribuzione> getAttribuzioni()
    {

            List<Attribuzione> attribuzioneList = new ArrayList<Attribuzione>();
            for (Attribuzione attribuzione: this._attribuzioni)
                attribuzioneList.add(attribuzione);
            return attribuzioneList;

    }

    public boolean getCriterioListaDominante() {
        return this._criterioListaDominante;
    }

    public boolean getHaListaDominante()
    {

            int count = this._attribuzioni.size();
            int num = 0;
            for (Attribuzione attribuzione : this._attribuzioni)
            {
                if (attribuzione.getAttribuzioneDiretta() + attribuzione.getAttribuzioneMaggiorResto() == 0)
                    ++num;
            }
            return num == count - 1;

    }

    public ParametriAttribuzioneRSU getParametri() {
        return this._parametri;
    }

    public AbstractContestoAttribuzione.StatoContesto getStato() {
        return this._stato;
    }

    public void AddAttribuzione(Attribuzione attribuzione) {
        this._attribuzioni.add(attribuzione);
    }

    private String note;

    public abstract double getQuozienteElettorale();

    public abstract int getRSUEleggibili();

    private int rsuIndeterminate;

    public abstract CalcolatoreParametriAttribuzione.TipoParziale getTipo();

    public int getRSUAttribuite()
    {

            int num = 0;
            for (Attribuzione attribuzione : this._attribuzioni)
            num += attribuzione.getTotaleSeggi();
            return num;

    }

    public int getRSUDaAttribuire() {
        return this.getRSUEleggibili() - this.getRSUAttribuite();
    }

    protected AbstractContestoAttribuzione(
            ElezioneRSU elezione,
            ParametriAttribuzioneRSU parametriAttribuzione)
    {
        this._parametri = parametriAttribuzione;
        this._elezione = elezione;
    }



    protected void CalcolaAttribuzioneDiretta() throws Exception {
        for (Attribuzione attribuzione : this._attribuzioni)
            attribuzione.CalcolaAttribuzioneDiretta();
    }

    public Attribuzione GetAttribuzioneByLista(ListaElettorale lista)
    {
        for (Attribuzione attribuzione : this._attribuzioni)
        {
            if (lista == attribuzione.getVotazione().getLista())
                return attribuzione;
        }
        return (Attribuzione) null;
    }

    public int getRsuIndeterminate() {
        return rsuIndeterminate;
    }

    public void setRsuIndeterminate(int rsuIndeterminate) {
        this.rsuIndeterminate = rsuIndeterminate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public enum StatoContesto
    {
        Completo,
        Indeterminato,
        NonCalcolato,
    }
}





