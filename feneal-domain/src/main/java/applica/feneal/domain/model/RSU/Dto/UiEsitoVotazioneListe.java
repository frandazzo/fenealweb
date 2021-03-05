package applica.feneal.domain.model.RSU.Dto;

public class UiEsitoVotazioneListe {
    private String name;
    private boolean firmataria;
    private String validationError;
    private Integer voti;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFirmataria() {
        return firmataria;
    }

    public void setFirmataria(boolean firmataria) {
        this.firmataria = firmataria;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public Integer getVoti() {
        return voti;
    }

    public void setVoti(Integer voti) {
        this.voti = voti;
    }
}
