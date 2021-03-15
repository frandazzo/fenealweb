package applica.feneal.domain.model.RSU.UserInterfaces;

import applica.feneal.domain.model.RSU.UserInterfaces.UiEsitoVotazioneListe;

import java.util.List;

public class UiEsitoVotazione{
    private List<UiEsitoVotazioneListe> listeVotazione;
    private int anno;
    private Long sedeRsu;
    private Long firmRsu;
    private Long contrattoRsu;
    private int aventiDiritto;
    private int rsuEleggibili;
    private boolean addSchedeNulle;
    private int schedeBianche;
    private int schedeNulle;

    public Long getContrattoRsu() {
        return contrattoRsu;
    }

    public void setContrattoRsu(Long contrattoRsu) {
        this.contrattoRsu = contrattoRsu;
    }

    public List<UiEsitoVotazioneListe> getListeVotazione() {
        return listeVotazione;
    }

    public void setListeVotazione(List<UiEsitoVotazioneListe> listeVotazione) {
        this.listeVotazione = listeVotazione;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public Long getSedeRsu() {
        return sedeRsu;
    }

    public void setSedeRsu(Long sedeRsu) {
        this.sedeRsu = sedeRsu;
    }

    public Long getFirmRsu() {
        return firmRsu;
    }

    public void setFirmRsu(Long firmRsu) {
        this.firmRsu = firmRsu;
    }

    public int getAventiDiritto() {
        return aventiDiritto;
    }

    public void setAventiDiritto(int aventiDiritto) {
        this.aventiDiritto = aventiDiritto;
    }

    public int getRsuEleggibili() {
        return rsuEleggibili;
    }

    public void setRsuEleggibili(int rsuEleggibili) {
        this.rsuEleggibili = rsuEleggibili;
    }

    public boolean isAddSchedeNulle() {
        return addSchedeNulle;
    }

    public void setAddSchedeNulle(boolean addSchedeNulle) {
        this.addSchedeNulle = addSchedeNulle;
    }

    public int getSchedeBianche() {
        return schedeBianche;
    }

    public void setSchedeBianche(int schedeBianche) {
        this.schedeBianche = schedeBianche;
    }

    public int getSchedeNulle() {
        return schedeNulle;
    }

    public void setSchedeNulle(int schedeNulle) {
        this.schedeNulle = schedeNulle;
    }
}
