/**
 * FenealgestUtils.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:01:29 GMT)
 */
package it.fenealgestweb.www;


/*
 *  FenealgestUtils java interface
 */
public interface FenealgestUtils {
    /**
     * Auto generated method signature
     *
     * @param calcolaCodiceFiscale0
     */
    public it.fenealgestweb.www.CalcolaCodiceFiscaleResponse calcolaCodiceFiscale(
        it.fenealgestweb.www.CalcolaCodiceFiscale calcolaCodiceFiscale0)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param calcolaCodiceFiscale0
     */
    public void startcalcolaCodiceFiscale(
        it.fenealgestweb.www.CalcolaCodiceFiscale calcolaCodiceFiscale0,
        final it.fenealgestweb.www.FenealgestUtilsCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param exportDocumentToExcel2
     */
    public it.fenealgestweb.www.ExportDocumentToExcelResponse exportDocumentToExcel(
        it.fenealgestweb.www.ExportDocumentToExcel exportDocumentToExcel2)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param exportDocumentToExcel2
     */
    public void startexportDocumentToExcel(
        it.fenealgestweb.www.ExportDocumentToExcel exportDocumentToExcel2,
        final it.fenealgestweb.www.FenealgestUtilsCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param calcolaDatiFiscali4
     */
    public it.fenealgestweb.www.CalcolaDatiFiscaliResponse calcolaDatiFiscali(
        it.fenealgestweb.www.CalcolaDatiFiscali calcolaDatiFiscali4)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param calcolaDatiFiscali4
     */
    public void startcalcolaDatiFiscali(
        it.fenealgestweb.www.CalcolaDatiFiscali calcolaDatiFiscali4,
        final it.fenealgestweb.www.FenealgestUtilsCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param exportTessere6
     */
    public it.fenealgestweb.www.ExportTessereResponse exportTessere(
        it.fenealgestweb.www.ExportTessere exportTessere6)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param exportTessere6
     */
    public void startexportTessere(
        it.fenealgestweb.www.ExportTessere exportTessere6,
        final it.fenealgestweb.www.FenealgestUtilsCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param importData8
     */
    public it.fenealgestweb.www.ImportDataResponse importData(
        it.fenealgestweb.www.ImportData importData8)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param importData8
     */
    public void startimportData(it.fenealgestweb.www.ImportData importData8,
        final it.fenealgestweb.www.FenealgestUtilsCallbackHandler callback)
        throws java.rmi.RemoteException;

    //
}
