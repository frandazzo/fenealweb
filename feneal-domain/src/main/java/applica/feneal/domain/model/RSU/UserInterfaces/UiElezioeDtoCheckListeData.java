package applica.feneal.domain.model.RSU.UserInterfaces;

import applica.feneal.domain.model.RSU.Dto.ElezioneDto;

public class UiElezioeDtoCheckListeData {
    private ElezioneDto dto;
    private Long firmRsu;
    private Long sedeRsu;
    private Long contrattoRsu;
    private int anno;

    public ElezioneDto getDto() {
        return dto;
    }

    public Long getContrattoRsu() {
        return contrattoRsu;
    }

    public void setContrattoRsu(Long contrattoRsu) {
        this.contrattoRsu = contrattoRsu;
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

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }
}
