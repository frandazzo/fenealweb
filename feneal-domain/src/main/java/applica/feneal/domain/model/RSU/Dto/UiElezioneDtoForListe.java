package applica.feneal.domain.model.RSU.Dto;



public class UiElezioneDtoForListe {
    private ElezioneDto dto;
    private String nome;
    private Boolean firmataria;

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
