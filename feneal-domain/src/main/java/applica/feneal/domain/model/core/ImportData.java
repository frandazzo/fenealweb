package applica.feneal.domain.model.core;

/**
 * Created by fgran on 18/04/2017.
 */
public class ImportData {

    private String file1;
    private Integer updateResidenza;
    private Integer updateTelefoni;
    private Integer updateAzienda;
    private Integer createLista;
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public Integer isUpdateResidenza() {
        return updateResidenza;
    }

    public void setUpdateResidenza(Integer updateResidenza) {
        this.updateResidenza = updateResidenza;
    }

    public Integer isUpdateTelefoni() {
        return updateTelefoni;
    }

    public void setUpdateTelefoni(Integer updateTelefoni) {
        this.updateTelefoni = updateTelefoni;
    }

    public Integer isUpdateAzienda() {
        return updateAzienda;
    }

    public void setUpdateAzienda(Integer updateAzienda) {
        this.updateAzienda = updateAzienda;
    }

    public Integer isCreateLista() {
        return createLista;
    }

    public void setCreateLista(Integer createLista) {
        this.createLista = createLista;
    }
}
