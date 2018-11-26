/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  ExtensionMapper class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ExtensionMapper {
    public static java.lang.Object getTypeObject(
        java.lang.String namespaceURI, java.lang.String typeName,
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "DTORendiconto".equals(typeName)) {
            return it.fenealgestweb.www.DTORendiconto.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "DocumentDTO".equals(typeName)) {
            return it.fenealgestweb.www.DocumentDTOE.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ArrayOfWorkerDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfWorkerDTO.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "FenealwebData".equals(typeName)) {
            return it.fenealgestweb.www.FenealwebData.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "ArrayOfString".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfString.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "PeriodType".equals(typeName)) {
            return it.fenealgestweb.www.PeriodType.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "SubscriptionDTO".equals(typeName)) {
            return it.fenealgestweb.www.SubscriptionDTO.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ArrayOfSubscriptionDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfSubscriptionDTO.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "Credenziali".equals(typeName)) {
            return it.fenealgestweb.www.Credenziali.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "DTORendicontoItem".equals(typeName)) {
            return it.fenealgestweb.www.DTORendicontoItem.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ArrayOfDTORendicontoItem".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfDTORendicontoItem.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "FenealwebSubscriptionDTOData".equals(typeName)) {
            return it.fenealgestweb.www.FenealwebSubscriptionDTOData.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "QueryParameters".equals(typeName)) {
            return it.fenealgestweb.www.QueryParameters.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ArrayOfDocumentDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfDocumentDTO.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ExprtType".equals(typeName)) {
            return it.fenealgestweb.www.ExprtType.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "WorkerDTO".equals(typeName)) {
            return it.fenealgestweb.www.WorkerDTOE.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "SubscriptionDTO".equals(typeName)) {
            return it.fenealgestweb.www.SubscriptionDTOE.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "ArrayOfSubscriptionDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfSubscriptionDTOE.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "FenealwebSubscriptionDTOData".equals(typeName)) {
            return it.fenealgestweb.www.FenealwebSubscriptionDTODataE.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "ArrayOfDocumentDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfDocumentDTOE.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "WorkerDTO".equals(typeName)) {
            return it.fenealgestweb.www.WorkerDTO.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "MailData".equals(typeName)) {
            return it.fenealgestweb.www.MailData.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "ExportTrace".equals(typeName)) {
            return it.fenealgestweb.www.ExportTrace.Factory.parse(reader);
        }

        if ("www.fenealgestweb.it".equals(namespaceURI) &&
                "DocumentDTO".equals(typeName)) {
            return it.fenealgestweb.www.DocumentDTO.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "ArrayOfWorkerDTO".equals(typeName)) {
            return it.fenealgestweb.www.ArrayOfWorkerDTOE.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "QueryResultDTO".equals(typeName)) {
            return it.fenealgestweb.www.QueryResultDTO.Factory.parse(reader);
        }

        if ("http://www.fenealgestweb.it/".equals(namespaceURI) &&
                "PeriodType".equals(typeName)) {
            return it.fenealgestweb.www.PeriodTypeE.Factory.parse(reader);
        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " +
            namespaceURI + " " + typeName);
    }
}
