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
    //gestione delle queri esclusive sui liberi
    //tale proprietà serve per fare in modo che il report
    //sui liberi possa esssere fatto dalle singole province in un territorio accorpato
    //SOLAMENTE per il proprio territorio per questioni di privacy
    //implementato per il veneto-pietro de angelis
    private Boolean enableExclusiveLiberiQuery;

    //campo che indica al fenealweb di comportarsi in maniera regionale
    //questo significa che il report non iscritti fornisce informazioni
    //inclusive di dati deleghe ed iscrizioni ad altro sindacto per tutta la regione
    //l'anagrafica è l timeline di condivisione dei dati regionali ecc...
    //implementatoper la lombardia Enrico Vizza
    private Boolean fenealwebRegionale;

    public Boolean getFenealwebRegionale() {
        return fenealwebRegionale;
    }

    public void setFenealwebRegionale(Boolean fenealwebRegionale) {
        this.fenealwebRegionale = fenealwebRegionale;
    }

    public Boolean getEnableExclusiveLiberiQuery() {
        return enableExclusiveLiberiQuery;
    }

    public void setEnableExclusiveLiberiQuery(Boolean enableExclusiveLiberiQuery) {
        this.enableExclusiveLiberiQuery = enableExclusiveLiberiQuery;
    }

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
