package applica.feneal.domain.model.RSU.UserInterfaces;

import applica.feneal.domain.model.RSU.Dto.ElezioneDto;

public class UiVerbalizzaVotazioneDto {
    private ElezioneDto dto;
    private Long firmRsu;
    private Long sedeRsu;
    private Long contrattoRsu;
    private int anno;
    private String nomeverbalizzazione;
    private String verbalizzazione;
    private String note;

    public ElezioneDto getDto() {
        return dto;
    }

    public void setDto(ElezioneDto dto) {
        this.dto = dto;
    }

    public Long getFirmRsu() {
        return firmRsu;
    }

    public void setFirmRsu(Long firmRsu) {
        this.firmRsu = firmRsu;
    }

    public Long getSedeRsu() {
        return sedeRsu;
    }

    public void setSedeRsu(Long sedeRsu) {
        this.sedeRsu = sedeRsu;
    }

    public Long getContrattoRsu() {
        return contrattoRsu;
    }

    public void setContrattoRsu(Long contrattoRsu) {
        this.contrattoRsu = contrattoRsu;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getNomeverbalizzazione() {
        return nomeverbalizzazione;
    }

    public void setNomeverbalizzazione(String nomeverbalizzazione) {
        this.nomeverbalizzazione = nomeverbalizzazione;
    }

    public String getVerbalizzazione() {
        return verbalizzazione;
    }

    public void setVerbalizzazione(String verbalizzazione) {
        this.verbalizzazione = verbalizzazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
