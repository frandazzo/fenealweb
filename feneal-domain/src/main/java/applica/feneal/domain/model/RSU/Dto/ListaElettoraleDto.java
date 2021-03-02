package applica.feneal.domain.model.RSU.Dto;

public class ListaElettoraleDto {

    private String name;
    private boolean firmataria;
    private String validationError;

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

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
}
