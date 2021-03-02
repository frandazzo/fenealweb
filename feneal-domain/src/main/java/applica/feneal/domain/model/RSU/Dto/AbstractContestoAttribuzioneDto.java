package applica.feneal.domain.model.RSU.Dto;

import applica.feneal.domain.model.RSU.Election.AbstractContestoAttribuzione;
import applica.feneal.domain.model.RSU.Election.ParametriAttribuzioneRSU;

import java.util.List;

public class AbstractContestoAttribuzioneDto {
    private ElezioneDto _elezione;
    private List<AttribuzioneDto> _attribuzioni;
    private boolean _criterioListaDominante;
    private ParametriAttribuzioneRSU _parametri;
    private String note;
    private int rsuIndeterminate;
    private AbstractContestoAttribuzioneDto.StatoContesto _stato = AbstractContestoAttribuzioneDto.StatoContesto.NonCalcolato;

    public ElezioneDto get_elezione() {
        return _elezione;
    }

    public void set_elezione(ElezioneDto _elezione) {
        this._elezione = _elezione;
    }

    public List<AttribuzioneDto> get_attribuzioni() {
        return _attribuzioni;
    }

    public void set_attribuzioni(List<AttribuzioneDto> _attribuzioni) {
        this._attribuzioni = _attribuzioni;
    }

    public boolean is_criterioListaDominante() {
        return _criterioListaDominante;
    }

    public void set_criterioListaDominante(boolean _criterioListaDominante) {
        this._criterioListaDominante = _criterioListaDominante;
    }

    public ParametriAttribuzioneRSU get_parametri() {
        return _parametri;
    }

    public void set_parametri(ParametriAttribuzioneRSU _parametri) {
        this._parametri = _parametri;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRsuIndeterminate() {
        return rsuIndeterminate;
    }

    public void setRsuIndeterminate(int rsuIndeterminate) {
        this.rsuIndeterminate = rsuIndeterminate;
    }

    public StatoContesto get_stato() {
        return _stato;
    }

    public void set_stato(StatoContesto _stato) {
        this._stato = _stato;
    }

    public enum StatoContesto
    {
        Completo,
        Indeterminato,
        NonCalcolato,
    }
}
