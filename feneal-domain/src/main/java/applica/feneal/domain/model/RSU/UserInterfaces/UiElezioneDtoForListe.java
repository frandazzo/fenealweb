package applica.feneal.domain.model.RSU.UserInterfaces;


import applica.feneal.domain.model.RSU.Dto.ElezioneDto;

public class UiElezioneDtoForListe {
    private ElezioneDto dto;
    private String nome;
    private Boolean firmataria;
    private String oldnome;
    private Boolean oldfirmataria;

    public String getOldnome() {
        return oldnome;
    }

    public void setOldnome(String oldnome) {
        this.oldnome = oldnome;
    }

    public Boolean getOldfirmataria() {
        return oldfirmataria;
    }

    public void setOldfirmataria(Boolean oldfirmataria) {
        this.oldfirmataria = oldfirmataria;
    }

    public ElezioneDto getDto() {
        return dto;
    }

    public void setDto(ElezioneDto dto) {
        this.dto = dto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getFirmataria() {
        return firmataria;
    }

    public void setFirmataria(Boolean firmataria) {
        this.firmataria = firmataria;
    }
}
