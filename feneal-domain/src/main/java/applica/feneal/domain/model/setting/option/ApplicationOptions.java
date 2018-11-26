package applica.feneal.domain.model.setting.option;

import applica.feneal.domain.model.utils.SecuredDomainEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class ApplicationOptions extends SecuredDomainEntity{

    public boolean isCreateDelegaAsAccettata() {
        return createDelegaAsAccettata;
    }

    public void setCreateDelegaAsAccettata(boolean createDelegaAsAccettata) {
        this.createDelegaAsAccettata = createDelegaAsAccettata;
    }

    private boolean createDelegaAsAccettata;



    private boolean updateFirmasDuringImport;
    private boolean updateWorkersDuringImport;
    private boolean creaDelegaIfNotExistDuringImport;
    //serve per attivare le deleghe in fase di importazione quote
    private boolean associaDelegaDuringImport;


    public boolean isUpdateFirmasDuringImport() {
        return updateFirmasDuringImport;
    }

    public void setUpdateFirmasDuringImport(boolean updateFirmasDuringImport) {
        this.updateFirmasDuringImport = updateFirmasDuringImport;
    }

    public boolean isUpdateWorkersDuringImport() {
        return updateWorkersDuringImport;
    }

    public void setUpdateWorkersDuringImport(boolean updateWorkersDuringImport) {
        this.updateWorkersDuringImport = updateWorkersDuringImport;
    }

    public boolean isCreaDelegaIfNotExistDuringImport() {
        return creaDelegaIfNotExistDuringImport;
    }

    public void setCreaDelegaIfNotExistDuringImport(boolean creaDelegaIfNotExistDuringImport) {
        this.creaDelegaIfNotExistDuringImport = creaDelegaIfNotExistDuringImport;
    }

    public boolean isAssociaDelegaDuringImport() {
        return associaDelegaDuringImport;
    }

    public void setAssociaDelegaDuringImport(boolean associaDelegaDuringImport) {
        this.associaDelegaDuringImport = associaDelegaDuringImport;
    }
}
