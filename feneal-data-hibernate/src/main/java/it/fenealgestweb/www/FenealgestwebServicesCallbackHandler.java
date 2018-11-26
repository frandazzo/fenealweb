/**
 * FenealgestwebServicesCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:01:29 GMT)
 */
package it.fenealgestweb.www;


/**
 *  FenealgestwebServicesCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class FenealgestwebServicesCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public FenealgestwebServicesCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public FenealgestwebServicesCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for importRendicontoNew method
     * override this method for handling normal response from importRendicontoNew operation
     */
    public void receiveResultimportRendicontoNew(
        it.fenealgestweb.www.ImportRendicontoNewResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from importRendicontoNew operation
     */
    public void receiveErrorimportRendicontoNew(java.lang.Exception e) {
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

    /**
     * auto generated Axis2 call back method for searchWorkersByAzienda method
     * override this method for handling normal response from searchWorkersByAzienda operation
     */
    public void receiveResultsearchWorkersByAzienda(
        it.fenealgestweb.www.SearchWorkersByAziendaResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from searchWorkersByAzienda operation
     */
    public void receiveErrorsearchWorkersByAzienda(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDocumentLen method
     * override this method for handling normal response from getDocumentLen operation
     */
    public void receiveResultgetDocumentLen(
        it.fenealgestweb.www.GetDocumentLenResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDocumentLen operation
     */
    public void receiveErrorgetDocumentLen(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for saveImportExportFile method
     * override this method for handling normal response from saveImportExportFile operation
     */
    public void receiveResultsaveImportExportFile(
        it.fenealgestweb.www.SaveImportExportFileResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveImportExportFile operation
     */
    public void receiveErrorsaveImportExportFile(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDocumentLenFromServerSideAnalysis method
     * override this method for handling normal response from getDocumentLenFromServerSideAnalysis operation
     */
    public void receiveResultgetDocumentLenFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentLenFromServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDocumentLenFromServerSideAnalysis operation
     */
    public void receiveErrorgetDocumentLenFromServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for exportWorker method
     * override this method for handling normal response from exportWorker operation
     */
    public void receiveResultexportWorker(
        it.fenealgestweb.www.ExportWorkerResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from exportWorker operation
     */
    public void receiveErrorexportWorker(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getListOfFileToDownload method
     * override this method for handling normal response from getListOfFileToDownload operation
     */
    public void receiveResultgetListOfFileToDownload(
        it.fenealgestweb.www.GetListOfFileToDownloadResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getListOfFileToDownload operation
     */
    public void receiveErrorgetListOfFileToDownload(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for importRendicontoRlst method
     * override this method for handling normal response from importRendicontoRlst operation
     */
    public void receiveResultimportRendicontoRlst(
        it.fenealgestweb.www.ImportRendicontoRlstResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from importRendicontoRlst operation
     */
    public void receiveErrorimportRendicontoRlst(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for searchWorkers method
     * override this method for handling normal response from searchWorkers operation
     */
    public void receiveResultsearchWorkers(
        it.fenealgestweb.www.SearchWorkersResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from searchWorkers operation
     */
    public void receiveErrorsearchWorkers(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getListOfFileToDownloadFromServerSideAnalysis method
     * override this method for handling normal response from getListOfFileToDownloadFromServerSideAnalysis operation
     */
    public void receiveResultgetListOfFileToDownloadFromServerSideAnalysis(
        it.fenealgestweb.www.GetListOfFileToDownloadFromServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getListOfFileToDownloadFromServerSideAnalysis operation
     */
    public void receiveErrorgetListOfFileToDownloadFromServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for saveImportExportFileInpsForServerSideAnalysis method
     * override this method for handling normal response from saveImportExportFileInpsForServerSideAnalysis operation
     */
    public void receiveResultsaveImportExportFileInpsForServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileInpsForServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveImportExportFileInpsForServerSideAnalysis operation
     */
    public void receiveErrorsaveImportExportFileInpsForServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for renameImportExportFileFomServerSideAnalysis method
     * override this method for handling normal response from renameImportExportFileFomServerSideAnalysis operation
     */
    public void receiveResultrenameImportExportFileFomServerSideAnalysis(
        it.fenealgestweb.www.RenameImportExportFileFomServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from renameImportExportFileFomServerSideAnalysis operation
     */
    public void receiveErrorrenameImportExportFileFomServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendMailForLiberiSearch method
     * override this method for handling normal response from sendMailForLiberiSearch operation
     */
    public void receiveResultsendMailForLiberiSearch(
        it.fenealgestweb.www.SendMailForLiberiSearchResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sendMailForLiberiSearch operation
     */
    public void receiveErrorsendMailForLiberiSearch(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for saveImportExportFileFromServerSideAnalysis method
     * override this method for handling normal response from saveImportExportFileFromServerSideAnalysis operation
     */
    public void receiveResultsaveImportExportFileFromServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileFromServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveImportExportFileFromServerSideAnalysis operation
     */
    public void receiveErrorsaveImportExportFileFromServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for importRendiconto method
     * override this method for handling normal response from importRendiconto operation
     */
    public void receiveResultimportRendiconto(
        it.fenealgestweb.www.ImportRendicontoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from importRendiconto operation
     */
    public void receiveErrorimportRendiconto(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendMailFromFenealgest method
     * override this method for handling normal response from sendMailFromFenealgest operation
     */
    public void receiveResultsendMailFromFenealgest(
        it.fenealgestweb.www.SendMailFromFenealgestResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sendMailFromFenealgest operation
     */
    public void receiveErrorsendMailFromFenealgest(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for exportWorkers method
     * override this method for handling normal response from exportWorkers operation
     */
    public void receiveResultexportWorkers(
        it.fenealgestweb.www.ExportWorkersResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from exportWorkers operation
     */
    public void receiveErrorexportWorkers(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for renameImportExportFile method
     * override this method for handling normal response from renameImportExportFile operation
     */
    public void receiveResultrenameImportExportFile(
        it.fenealgestweb.www.RenameImportExportFileResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from renameImportExportFile operation
     */
    public void receiveErrorrenameImportExportFile(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for traceUsage method
     * override this method for handling normal response from traceUsage operation
     */
    public void receiveResulttraceUsage(
        it.fenealgestweb.www.TraceUsageResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from traceUsage operation
     */
    public void receiveErrortraceUsage(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDocument method
     * override this method for handling normal response from getDocument operation
     */
    public void receiveResultgetDocument(
        it.fenealgestweb.www.GetDocumentResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDocument operation
     */
    public void receiveErrorgetDocument(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDocumentFromServerSideAnalysis method
     * override this method for handling normal response from getDocumentFromServerSideAnalysis operation
     */
    public void receiveResultgetDocumentFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentFromServerSideAnalysisResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDocumentFromServerSideAnalysis operation
     */
    public void receiveErrorgetDocumentFromServerSideAnalysis(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for importRendicontoRlstNew method
     * override this method for handling normal response from importRendicontoRlstNew operation
     */
    public void receiveResultimportRendicontoRlstNew(
        it.fenealgestweb.www.ImportRendicontoRlstNewResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from importRendicontoRlstNew operation
     */
    public void receiveErrorimportRendicontoRlstNew(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for saveImportExportFileInps method
     * override this method for handling normal response from saveImportExportFileInps operation
     */
    public void receiveResultsaveImportExportFileInps(
        it.fenealgestweb.www.SaveImportExportFileInpsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveImportExportFileInps operation
     */
    public void receiveErrorsaveImportExportFileInps(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for userIsValid method
     * override this method for handling normal response from userIsValid operation
     */
    public void receiveResultuserIsValid(
        it.fenealgestweb.www.UserIsValidResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from userIsValid operation
     */
    public void receiveErroruserIsValid(java.lang.Exception e) {
    }
}
