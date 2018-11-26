/**
 * FenealgestwebServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:01:29 GMT)
 */
package it.fenealgestweb.www;


/*
 *  FenealgestwebServices java interface
 */
public interface FenealgestwebServices {
    /**
     * Auto generated method signature
     *
     * @param importRendicontoNew21
     * @param credenziali22
     */
    public it.fenealgestweb.www.ImportRendicontoNewResponse importRendicontoNew(
        it.fenealgestweb.www.ImportRendicontoNew importRendicontoNew21,
        it.fenealgestweb.www.CredenzialiE credenziali22)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importRendicontoNew21
     * @param credenziali22
     */
    public void startimportRendicontoNew(
        it.fenealgestweb.www.ImportRendicontoNew importRendicontoNew21,
        it.fenealgestweb.www.CredenzialiE credenziali22,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param importData24
     */
    public it.fenealgestweb.www.ImportDataResponse importData(
        it.fenealgestweb.www.ImportData importData24)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importData24
     */
    public void startimportData(it.fenealgestweb.www.ImportData importData24,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param searchWorkersByAzienda26
     * @param credenziali27
     */
    public it.fenealgestweb.www.SearchWorkersByAziendaResponse searchWorkersByAzienda(
        it.fenealgestweb.www.SearchWorkersByAzienda searchWorkersByAzienda26,
        it.fenealgestweb.www.CredenzialiE credenziali27)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param searchWorkersByAzienda26
     * @param credenziali27
     */
    public void startsearchWorkersByAzienda(
        it.fenealgestweb.www.SearchWorkersByAzienda searchWorkersByAzienda26,
        it.fenealgestweb.www.CredenzialiE credenziali27,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getDocumentLen29
     * @param credenziali30
     */
    public it.fenealgestweb.www.GetDocumentLenResponse getDocumentLen(
        it.fenealgestweb.www.GetDocumentLen getDocumentLen29,
        it.fenealgestweb.www.CredenzialiE credenziali30)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getDocumentLen29
     * @param credenziali30
     */
    public void startgetDocumentLen(
        it.fenealgestweb.www.GetDocumentLen getDocumentLen29,
        it.fenealgestweb.www.CredenzialiE credenziali30,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param saveImportExportFile32
     * @param credenziali33
     */
    public it.fenealgestweb.www.SaveImportExportFileResponse saveImportExportFile(
        it.fenealgestweb.www.SaveImportExportFile saveImportExportFile32,
        it.fenealgestweb.www.CredenzialiE credenziali33)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param saveImportExportFile32
     * @param credenziali33
     */
    public void startsaveImportExportFile(
        it.fenealgestweb.www.SaveImportExportFile saveImportExportFile32,
        it.fenealgestweb.www.CredenzialiE credenziali33,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getDocumentLenFromServerSideAnalysis35
     * @param credenziali36
     */
    public it.fenealgestweb.www.GetDocumentLenFromServerSideAnalysisResponse getDocumentLenFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentLenFromServerSideAnalysis getDocumentLenFromServerSideAnalysis35,
        it.fenealgestweb.www.CredenzialiE credenziali36)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getDocumentLenFromServerSideAnalysis35
     * @param credenziali36
     */
    public void startgetDocumentLenFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentLenFromServerSideAnalysis getDocumentLenFromServerSideAnalysis35,
        it.fenealgestweb.www.CredenzialiE credenziali36,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param exportWorker38
     * @param credenziali39
     */
    public it.fenealgestweb.www.ExportWorkerResponse exportWorker(
        it.fenealgestweb.www.ExportWorker exportWorker38,
        it.fenealgestweb.www.CredenzialiE credenziali39)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param exportWorker38
     * @param credenziali39
     */
    public void startexportWorker(
        it.fenealgestweb.www.ExportWorker exportWorker38,
        it.fenealgestweb.www.CredenzialiE credenziali39,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getListOfFileToDownload41
     * @param credenziali42
     */
    public it.fenealgestweb.www.GetListOfFileToDownloadResponse getListOfFileToDownload(
        it.fenealgestweb.www.GetListOfFileToDownload getListOfFileToDownload41,
        it.fenealgestweb.www.CredenzialiE credenziali42)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getListOfFileToDownload41
     * @param credenziali42
     */
    public void startgetListOfFileToDownload(
        it.fenealgestweb.www.GetListOfFileToDownload getListOfFileToDownload41,
        it.fenealgestweb.www.CredenzialiE credenziali42,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param importRendicontoRlst44
     * @param credenziali45
     */
    public it.fenealgestweb.www.ImportRendicontoRlstResponse importRendicontoRlst(
        it.fenealgestweb.www.ImportRendicontoRlst importRendicontoRlst44,
        it.fenealgestweb.www.CredenzialiE credenziali45)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importRendicontoRlst44
     * @param credenziali45
     */
    public void startimportRendicontoRlst(
        it.fenealgestweb.www.ImportRendicontoRlst importRendicontoRlst44,
        it.fenealgestweb.www.CredenzialiE credenziali45,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param searchWorkers47
     * @param credenziali48
     */
    public it.fenealgestweb.www.SearchWorkersResponse searchWorkers(
        it.fenealgestweb.www.SearchWorkers searchWorkers47,
        it.fenealgestweb.www.CredenzialiE credenziali48)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param searchWorkers47
     * @param credenziali48
     */
    public void startsearchWorkers(
        it.fenealgestweb.www.SearchWorkers searchWorkers47,
        it.fenealgestweb.www.CredenzialiE credenziali48,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getListOfFileToDownloadFromServerSideAnalysis50
     * @param credenziali51
     */
    public it.fenealgestweb.www.GetListOfFileToDownloadFromServerSideAnalysisResponse getListOfFileToDownloadFromServerSideAnalysis(
        it.fenealgestweb.www.GetListOfFileToDownloadFromServerSideAnalysis getListOfFileToDownloadFromServerSideAnalysis50,
        it.fenealgestweb.www.CredenzialiE credenziali51)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getListOfFileToDownloadFromServerSideAnalysis50
     * @param credenziali51
     */
    public void startgetListOfFileToDownloadFromServerSideAnalysis(
        it.fenealgestweb.www.GetListOfFileToDownloadFromServerSideAnalysis getListOfFileToDownloadFromServerSideAnalysis50,
        it.fenealgestweb.www.CredenzialiE credenziali51,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param saveImportExportFileInpsForServerSideAnalysis53
     * @param credenziali54
     */
    public it.fenealgestweb.www.SaveImportExportFileInpsForServerSideAnalysisResponse saveImportExportFileInpsForServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileInpsForServerSideAnalysis saveImportExportFileInpsForServerSideAnalysis53,
        it.fenealgestweb.www.CredenzialiE credenziali54)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param saveImportExportFileInpsForServerSideAnalysis53
     * @param credenziali54
     */
    public void startsaveImportExportFileInpsForServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileInpsForServerSideAnalysis saveImportExportFileInpsForServerSideAnalysis53,
        it.fenealgestweb.www.CredenzialiE credenziali54,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param renameImportExportFileFomServerSideAnalysis56
     * @param credenziali57
     */
    public it.fenealgestweb.www.RenameImportExportFileFomServerSideAnalysisResponse renameImportExportFileFomServerSideAnalysis(
        it.fenealgestweb.www.RenameImportExportFileFomServerSideAnalysis renameImportExportFileFomServerSideAnalysis56,
        it.fenealgestweb.www.CredenzialiE credenziali57)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param renameImportExportFileFomServerSideAnalysis56
     * @param credenziali57
     */
    public void startrenameImportExportFileFomServerSideAnalysis(
        it.fenealgestweb.www.RenameImportExportFileFomServerSideAnalysis renameImportExportFileFomServerSideAnalysis56,
        it.fenealgestweb.www.CredenzialiE credenziali57,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param sendMailForLiberiSearch59
     * @param credenziali60
     */
    public it.fenealgestweb.www.SendMailForLiberiSearchResponse sendMailForLiberiSearch(
        it.fenealgestweb.www.SendMailForLiberiSearch sendMailForLiberiSearch59,
        it.fenealgestweb.www.CredenzialiE credenziali60)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param sendMailForLiberiSearch59
     * @param credenziali60
     */
    public void startsendMailForLiberiSearch(
        it.fenealgestweb.www.SendMailForLiberiSearch sendMailForLiberiSearch59,
        it.fenealgestweb.www.CredenzialiE credenziali60,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param saveImportExportFileFromServerSideAnalysis62
     * @param credenziali63
     */
    public it.fenealgestweb.www.SaveImportExportFileFromServerSideAnalysisResponse saveImportExportFileFromServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileFromServerSideAnalysis saveImportExportFileFromServerSideAnalysis62,
        it.fenealgestweb.www.CredenzialiE credenziali63)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param saveImportExportFileFromServerSideAnalysis62
     * @param credenziali63
     */
    public void startsaveImportExportFileFromServerSideAnalysis(
        it.fenealgestweb.www.SaveImportExportFileFromServerSideAnalysis saveImportExportFileFromServerSideAnalysis62,
        it.fenealgestweb.www.CredenzialiE credenziali63,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param importRendiconto65
     * @param credenziali66
     */
    public it.fenealgestweb.www.ImportRendicontoResponse importRendiconto(
        it.fenealgestweb.www.ImportRendiconto importRendiconto65,
        it.fenealgestweb.www.CredenzialiE credenziali66)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importRendiconto65
     * @param credenziali66
     */
    public void startimportRendiconto(
        it.fenealgestweb.www.ImportRendiconto importRendiconto65,
        it.fenealgestweb.www.CredenzialiE credenziali66,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param sendMailFromFenealgest68
     */
    public it.fenealgestweb.www.SendMailFromFenealgestResponse sendMailFromFenealgest(
        it.fenealgestweb.www.SendMailFromFenealgest sendMailFromFenealgest68)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param sendMailFromFenealgest68
     */
    public void startsendMailFromFenealgest(
        it.fenealgestweb.www.SendMailFromFenealgest sendMailFromFenealgest68,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param exportWorkers70
     * @param credenziali71
     */
    public it.fenealgestweb.www.ExportWorkersResponse exportWorkers(
        it.fenealgestweb.www.ExportWorkers exportWorkers70,
        it.fenealgestweb.www.CredenzialiE credenziali71)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param exportWorkers70
     * @param credenziali71
     */
    public void startexportWorkers(
        it.fenealgestweb.www.ExportWorkers exportWorkers70,
        it.fenealgestweb.www.CredenzialiE credenziali71,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param renameImportExportFile73
     * @param credenziali74
     */
    public it.fenealgestweb.www.RenameImportExportFileResponse renameImportExportFile(
        it.fenealgestweb.www.RenameImportExportFile renameImportExportFile73,
        it.fenealgestweb.www.CredenzialiE credenziali74)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param renameImportExportFile73
     * @param credenziali74
     */
    public void startrenameImportExportFile(
        it.fenealgestweb.www.RenameImportExportFile renameImportExportFile73,
        it.fenealgestweb.www.CredenzialiE credenziali74,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param traceUsage76
     */
    public it.fenealgestweb.www.TraceUsageResponse traceUsage(
        it.fenealgestweb.www.TraceUsage traceUsage76)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param traceUsage76
     */
    public void starttraceUsage(it.fenealgestweb.www.TraceUsage traceUsage76,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getDocument78
     * @param credenziali79
     */
    public it.fenealgestweb.www.GetDocumentResponse getDocument(
        it.fenealgestweb.www.GetDocument getDocument78,
        it.fenealgestweb.www.CredenzialiE credenziali79)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getDocument78
     * @param credenziali79
     */
    public void startgetDocument(
        it.fenealgestweb.www.GetDocument getDocument78,
        it.fenealgestweb.www.CredenzialiE credenziali79,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param getDocumentFromServerSideAnalysis81
     * @param credenziali82
     */
    public it.fenealgestweb.www.GetDocumentFromServerSideAnalysisResponse getDocumentFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentFromServerSideAnalysis getDocumentFromServerSideAnalysis81,
        it.fenealgestweb.www.CredenzialiE credenziali82)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param getDocumentFromServerSideAnalysis81
     * @param credenziali82
     */
    public void startgetDocumentFromServerSideAnalysis(
        it.fenealgestweb.www.GetDocumentFromServerSideAnalysis getDocumentFromServerSideAnalysis81,
        it.fenealgestweb.www.CredenzialiE credenziali82,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param importRendicontoRlstNew84
     * @param credenziali85
     */
    public it.fenealgestweb.www.ImportRendicontoRlstNewResponse importRendicontoRlstNew(
        it.fenealgestweb.www.ImportRendicontoRlstNew importRendicontoRlstNew84,
        it.fenealgestweb.www.CredenzialiE credenziali85)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importRendicontoRlstNew84
     * @param credenziali85
     */
    public void startimportRendicontoRlstNew(
        it.fenealgestweb.www.ImportRendicontoRlstNew importRendicontoRlstNew84,
        it.fenealgestweb.www.CredenzialiE credenziali85,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param saveImportExportFileInps87
     * @param credenziali88
     */
    public it.fenealgestweb.www.SaveImportExportFileInpsResponse saveImportExportFileInps(
        it.fenealgestweb.www.SaveImportExportFileInps saveImportExportFileInps87,
        it.fenealgestweb.www.CredenzialiE credenziali88)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param saveImportExportFileInps87
     * @param credenziali88
     */
    public void startsaveImportExportFileInps(
        it.fenealgestweb.www.SaveImportExportFileInps saveImportExportFileInps87,
        it.fenealgestweb.www.CredenzialiE credenziali88,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param userIsValid90
     * @param credenziali91
     */
    public it.fenealgestweb.www.UserIsValidResponse userIsValid(
        it.fenealgestweb.www.UserIsValid userIsValid90,
        it.fenealgestweb.www.CredenzialiE credenziali91)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param userIsValid90
     * @param credenziali91
     */
    public void startuserIsValid(
        it.fenealgestweb.www.UserIsValid userIsValid90,
        it.fenealgestweb.www.CredenzialiE credenziali91,
        final it.fenealgestweb.www.FenealgestwebServicesCallbackHandler callback)
        throws java.rmi.RemoteException;

    //
}
