/**
 * FenealgestUtilsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:01:29 GMT)
 */
package it.fenealgestweb.www;


/**
 *  FenealgestUtilsCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class FenealgestUtilsCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public FenealgestUtilsCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public FenealgestUtilsCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for calcolaCodiceFiscale method
     * override this method for handling normal response from calcolaCodiceFiscale operation
     */
    public void receiveResultcalcolaCodiceFiscale(
        it.fenealgestweb.www.CalcolaCodiceFiscaleResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from calcolaCodiceFiscale operation
     */
    public void receiveErrorcalcolaCodiceFiscale(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for exportDocumentToExcel method
     * override this method for handling normal response from exportDocumentToExcel operation
     */
    public void receiveResultexportDocumentToExcel(
        it.fenealgestweb.www.ExportDocumentToExcelResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from exportDocumentToExcel operation
     */
    public void receiveErrorexportDocumentToExcel(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for calcolaDatiFiscali method
     * override this method for handling normal response from calcolaDatiFiscali operation
     */
    public void receiveResultcalcolaDatiFiscali(
        it.fenealgestweb.www.CalcolaDatiFiscaliResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from calcolaDatiFiscali operation
     */
    public void receiveErrorcalcolaDatiFiscali(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for exportTessere method
     * override this method for handling normal response from exportTessere operation
     */
    public void receiveResultexportTessere(
        it.fenealgestweb.www.ExportTessereResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from exportTessere operation
     */
    public void receiveErrorexportTessere(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for importData method
     * override this method for handling normal response from importData operation
     */
    public void receiveResultimportData(
        it.fenealgestweb.www.ImportDataResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from importData operation
     */
    public void receiveErrorimportData(java.lang.Exception e) {
    }
}
