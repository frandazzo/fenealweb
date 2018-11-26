/**
 * ExportTrace.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  ExportTrace bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ExportTrace implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = ExportTrace
       Namespace URI = www.fenealgestweb.it
       Namespace Prefix = ns1
     */

    /**
     * field for Workers
     */
    protected it.fenealgestweb.www.ArrayOfWorkerDTO localWorkers;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localWorkersTracker = false;

    /**
     * field for Errore
     */
    protected java.lang.String localErrore;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localErroreTracker = false;

    /**
     * field for FenealwebData
     */
    protected it.fenealgestweb.www.FenealwebData localFenealwebData;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFenealwebDataTracker = false;

    /**
     * field for ExportDate
     * This was an Attribute!
     */
    protected java.util.Calendar localExportDate;

    /**
     * field for Province
     * This was an Attribute!
     */
    protected java.lang.String localProvince;

    /**
     * field for ExporterMail
     * This was an Attribute!
     */
    protected java.lang.String localExporterMail;

    /**
     * field for ExportNumber
     * This was an Attribute!
     */
    protected int localExportNumber;

    /**
     * field for UserName
     * This was an Attribute!
     */
    protected java.lang.String localUserName;

    /**
     * field for Sector
     * This was an Attribute!
     */
    protected java.lang.String localSector;

    /**
     * field for ExporterName
     * This was an Attribute!
     */
    protected java.lang.String localExporterName;

    /**
     * field for Password
     * This was an Attribute!
     */
    protected java.lang.String localPassword;

    /**
     * field for ExportType
     * This was an Attribute!
     */
    protected it.fenealgestweb.www.ExprtType localExportType;

    /**
     * field for PeriodType
     * This was an Attribute!
     */
    protected it.fenealgestweb.www.PeriodType localPeriodType;

    /**
     * field for InitialDate
     * This was an Attribute!
     */
    protected java.util.Calendar localInitialDate;

    /**
     * field for EndDate
     * This was an Attribute!
     */
    protected java.util.Calendar localEndDate;

    /**
     * field for Year
     * This was an Attribute!
     */
    protected int localYear;

    /**
     * field for Period
     * This was an Attribute!
     */
    protected int localPeriod;

    /**
     * field for Transacted
     * This was an Attribute!
     */
    protected boolean localTransacted;

    /**
     * field for Struttura
     * This was an Attribute!
     */
    protected java.lang.String localStruttura;

    /**
     * field for Area
     * This was an Attribute!
     */
    protected java.lang.String localArea;

    public boolean isWorkersSpecified() {
        return localWorkersTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.ArrayOfWorkerDTO
     */
    public it.fenealgestweb.www.ArrayOfWorkerDTO getWorkers() {
        return localWorkers;
    }

    /**
     * Auto generated setter method
     * @param param Workers
     */
    public void setWorkers(it.fenealgestweb.www.ArrayOfWorkerDTO param) {
        localWorkersTracker = param != null;

        this.localWorkers = param;
    }

    public boolean isErroreSpecified() {
        return localErroreTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getErrore() {
        return localErrore;
    }

    /**
     * Auto generated setter method
     * @param param Errore
     */
    public void setErrore(java.lang.String param) {
        localErroreTracker = param != null;

        this.localErrore = param;
    }

    public boolean isFenealwebDataSpecified() {
        return localFenealwebDataTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.FenealwebData
     */
    public it.fenealgestweb.www.FenealwebData getFenealwebData() {
        return localFenealwebData;
    }

    /**
     * Auto generated setter method
     * @param param FenealwebData
     */
    public void setFenealwebData(it.fenealgestweb.www.FenealwebData param) {
        localFenealwebDataTracker = param != null;

        this.localFenealwebData = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getExportDate() {
        return localExportDate;
    }

    /**
     * Auto generated setter method
     * @param param ExportDate
     */
    public void setExportDate(java.util.Calendar param) {
        this.localExportDate = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getProvince() {
        return localProvince;
    }

    /**
     * Auto generated setter method
     * @param param Province
     */
    public void setProvince(java.lang.String param) {
        this.localProvince = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getExporterMail() {
        return localExporterMail;
    }

    /**
     * Auto generated setter method
     * @param param ExporterMail
     */
    public void setExporterMail(java.lang.String param) {
        this.localExporterMail = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getExportNumber() {
        return localExportNumber;
    }

    /**
     * Auto generated setter method
     * @param param ExportNumber
     */
    public void setExportNumber(int param) {
        this.localExportNumber = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserName() {
        return localUserName;
    }

    /**
     * Auto generated setter method
     * @param param UserName
     */
    public void setUserName(java.lang.String param) {
        this.localUserName = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSector() {
        return localSector;
    }

    /**
     * Auto generated setter method
     * @param param Sector
     */
    public void setSector(java.lang.String param) {
        this.localSector = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getExporterName() {
        return localExporterName;
    }

    /**
     * Auto generated setter method
     * @param param ExporterName
     */
    public void setExporterName(java.lang.String param) {
        this.localExporterName = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPassword() {
        return localPassword;
    }

    /**
     * Auto generated setter method
     * @param param Password
     */
    public void setPassword(java.lang.String param) {
        this.localPassword = param;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.ExprtType
     */
    public it.fenealgestweb.www.ExprtType getExportType() {
        return localExportType;
    }

    /**
     * Auto generated setter method
     * @param param ExportType
     */
    public void setExportType(it.fenealgestweb.www.ExprtType param) {
        this.localExportType = param;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.PeriodType
     */
    public it.fenealgestweb.www.PeriodType getPeriodType() {
        return localPeriodType;
    }

    /**
     * Auto generated setter method
     * @param param PeriodType
     */
    public void setPeriodType(it.fenealgestweb.www.PeriodType param) {
        this.localPeriodType = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getInitialDate() {
        return localInitialDate;
    }

    /**
     * Auto generated setter method
     * @param param InitialDate
     */
    public void setInitialDate(java.util.Calendar param) {
        this.localInitialDate = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getEndDate() {
        return localEndDate;
    }

    /**
     * Auto generated setter method
     * @param param EndDate
     */
    public void setEndDate(java.util.Calendar param) {
        this.localEndDate = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getYear() {
        return localYear;
    }

    /**
     * Auto generated setter method
     * @param param Year
     */
    public void setYear(int param) {
        this.localYear = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getPeriod() {
        return localPeriod;
    }

    /**
     * Auto generated setter method
     * @param param Period
     */
    public void setPeriod(int param) {
        this.localPeriod = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getTransacted() {
        return localTransacted;
    }

    /**
     * Auto generated setter method
     * @param param Transacted
     */
    public void setTransacted(boolean param) {
        this.localTransacted = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStruttura() {
        return localStruttura;
    }

    /**
     * Auto generated setter method
     * @param param Struttura
     */
    public void setStruttura(java.lang.String param) {
        this.localStruttura = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getArea() {
        return localArea;
    }

    /**
     * Auto generated setter method
     * @param param Area
     */
    public void setArea(java.lang.String param) {
        this.localArea = param;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                this, parentQName));
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        serialize(parentQName, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();
        writeStartElement(prefix, namespace, parentQName.getLocalPart(),
            xmlWriter);

        if (serializeType) {
            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "www.fenealgestweb.it");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":ExportTrace", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "ExportTrace", xmlWriter);
            }
        }

        if (localExportDate != null) {
            writeAttribute("www.fenealgestweb.it", "ExportDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExportDate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localExportDate is null");
        }

        if (localProvince != null) {
            writeAttribute("www.fenealgestweb.it", "Province",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localProvince), xmlWriter);
        }

        if (localExporterMail != null) {
            writeAttribute("www.fenealgestweb.it", "ExporterMail",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExporterMail), xmlWriter);
        }

        if (localExportNumber != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "ExportNumber",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExportNumber), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localExportNumber is null");
        }

        if (localUserName != null) {
            writeAttribute("www.fenealgestweb.it", "UserName",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localUserName), xmlWriter);
        }

        if (localSector != null) {
            writeAttribute("www.fenealgestweb.it", "Sector",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSector), xmlWriter);
        }

        if (localExporterName != null) {
            writeAttribute("www.fenealgestweb.it", "ExporterName",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExporterName), xmlWriter);
        }

        if (localPassword != null) {
            writeAttribute("www.fenealgestweb.it", "Password",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localPassword), xmlWriter);
        }

        if (localExportType != null) {
            writeAttribute("www.fenealgestweb.it", "ExportType",
                localExportType.toString(), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localExportType is null");
        }

        if (localPeriodType != null) {
            writeAttribute("www.fenealgestweb.it", "PeriodType",
                localPeriodType.toString(), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localPeriodType is null");
        }

        if (localInitialDate != null) {
            writeAttribute("www.fenealgestweb.it", "InitialDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localInitialDate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localInitialDate is null");
        }

        if (localEndDate != null) {
            writeAttribute("www.fenealgestweb.it", "EndDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localEndDate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localEndDate is null");
        }

        if (localYear != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Year",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localYear), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localYear is null");
        }

        if (localPeriod != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Period",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localPeriod), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localPeriod is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "Transacted",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localTransacted), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localTransacted is null");
        }

        if (localStruttura != null) {
            writeAttribute("www.fenealgestweb.it", "Struttura",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localStruttura), xmlWriter);
        }

        if (localArea != null) {
            writeAttribute("www.fenealgestweb.it", "Area",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localArea), xmlWriter);
        }

        if (localWorkersTracker) {
            if (localWorkers == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Workers cannot be null!!");
            }

            localWorkers.serialize(new javax.xml.namespace.QName(
                    "www.fenealgestweb.it", "Workers"), xmlWriter);
        }

        if (localErroreTracker) {
            namespace = "www.fenealgestweb.it";
            writeStartElement(null, namespace, "Errore", xmlWriter);

            if (localErrore == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Errore cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localErrore);
            }

            xmlWriter.writeEndElement();
        }

        if (localFenealwebDataTracker) {
            if (localFenealwebData == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "FenealwebData cannot be null!!");
            }

            localFenealwebData.serialize(new javax.xml.namespace.QName(
                    "www.fenealgestweb.it", "FenealwebData"), xmlWriter);
        }

        xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("www.fenealgestweb.it")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Utility method to write an element start tag.
     */
    private void writeStartElement(java.lang.String prefix,
        java.lang.String namespace, java.lang.String localPart,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
        } else {
            if (namespace.length() == 0) {
                prefix = "";
            } else if (prefix == null) {
                prefix = generatePrefix(namespace);
            }

            xmlWriter.writeStartElement(prefix, localPart, namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
        } else {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
            xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

            while (true) {
                java.lang.String uri = nsContext.getNamespaceURI(prefix);

                if ((uri == null) || (uri.length() == 0)) {
                    break;
                }

                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static ExportTrace parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            ExportTrace object = new ExportTrace();

            int event;
            javax.xml.namespace.QName currentQName = null;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                currentQName = reader.getName();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"ExportTrace".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ExportTrace) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "ExportDate"
                java.lang.String tempAttribExportDate = reader.getAttributeValue("www.fenealgestweb.it",
                        "ExportDate");

                if (tempAttribExportDate != null) {
                    java.lang.String content = tempAttribExportDate;

                    object.setExportDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribExportDate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ExportDate is missing");
                }

                handledAttributes.add("ExportDate");

                // handle attribute "Province"
                java.lang.String tempAttribProvince = reader.getAttributeValue("www.fenealgestweb.it",
                        "Province");

                if (tempAttribProvince != null) {
                    java.lang.String content = tempAttribProvince;

                    object.setProvince(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribProvince));
                } else {
                }

                handledAttributes.add("Province");

                // handle attribute "ExporterMail"
                java.lang.String tempAttribExporterMail = reader.getAttributeValue("www.fenealgestweb.it",
                        "ExporterMail");

                if (tempAttribExporterMail != null) {
                    java.lang.String content = tempAttribExporterMail;

                    object.setExporterMail(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribExporterMail));
                } else {
                }

                handledAttributes.add("ExporterMail");

                // handle attribute "ExportNumber"
                java.lang.String tempAttribExportNumber = reader.getAttributeValue("www.fenealgestweb.it",
                        "ExportNumber");

                if (tempAttribExportNumber != null) {
                    java.lang.String content = tempAttribExportNumber;

                    object.setExportNumber(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribExportNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ExportNumber is missing");
                }

                handledAttributes.add("ExportNumber");

                // handle attribute "UserName"
                java.lang.String tempAttribUserName = reader.getAttributeValue("www.fenealgestweb.it",
                        "UserName");

                if (tempAttribUserName != null) {
                    java.lang.String content = tempAttribUserName;

                    object.setUserName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribUserName));
                } else {
                }

                handledAttributes.add("UserName");

                // handle attribute "Sector"
                java.lang.String tempAttribSector = reader.getAttributeValue("www.fenealgestweb.it",
                        "Sector");

                if (tempAttribSector != null) {
                    java.lang.String content = tempAttribSector;

                    object.setSector(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribSector));
                } else {
                }

                handledAttributes.add("Sector");

                // handle attribute "ExporterName"
                java.lang.String tempAttribExporterName = reader.getAttributeValue("www.fenealgestweb.it",
                        "ExporterName");

                if (tempAttribExporterName != null) {
                    java.lang.String content = tempAttribExporterName;

                    object.setExporterName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribExporterName));
                } else {
                }

                handledAttributes.add("ExporterName");

                // handle attribute "Password"
                java.lang.String tempAttribPassword = reader.getAttributeValue("www.fenealgestweb.it",
                        "Password");

                if (tempAttribPassword != null) {
                    java.lang.String content = tempAttribPassword;

                    object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribPassword));
                } else {
                }

                handledAttributes.add("Password");

                // handle attribute "ExportType"
                java.lang.String tempAttribExportType = reader.getAttributeValue("www.fenealgestweb.it",
                        "ExportType");

                if (tempAttribExportType != null) {
                    java.lang.String content = tempAttribExportType;

                    object.setExportType(it.fenealgestweb.www.ExprtType.Factory.fromString(
                            reader, tempAttribExportType));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ExportType is missing");
                }

                handledAttributes.add("ExportType");

                // handle attribute "PeriodType"
                java.lang.String tempAttribPeriodType = reader.getAttributeValue("www.fenealgestweb.it",
                        "PeriodType");

                if (tempAttribPeriodType != null) {
                    java.lang.String content = tempAttribPeriodType;

                    object.setPeriodType(it.fenealgestweb.www.PeriodType.Factory.fromString(
                            reader, tempAttribPeriodType));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute PeriodType is missing");
                }

                handledAttributes.add("PeriodType");

                // handle attribute "InitialDate"
                java.lang.String tempAttribInitialDate = reader.getAttributeValue("www.fenealgestweb.it",
                        "InitialDate");

                if (tempAttribInitialDate != null) {
                    java.lang.String content = tempAttribInitialDate;

                    object.setInitialDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribInitialDate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute InitialDate is missing");
                }

                handledAttributes.add("InitialDate");

                // handle attribute "EndDate"
                java.lang.String tempAttribEndDate = reader.getAttributeValue("www.fenealgestweb.it",
                        "EndDate");

                if (tempAttribEndDate != null) {
                    java.lang.String content = tempAttribEndDate;

                    object.setEndDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribEndDate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute EndDate is missing");
                }

                handledAttributes.add("EndDate");

                // handle attribute "Year"
                java.lang.String tempAttribYear = reader.getAttributeValue("www.fenealgestweb.it",
                        "Year");

                if (tempAttribYear != null) {
                    java.lang.String content = tempAttribYear;

                    object.setYear(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribYear));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Year is missing");
                }

                handledAttributes.add("Year");

                // handle attribute "Period"
                java.lang.String tempAttribPeriod = reader.getAttributeValue("www.fenealgestweb.it",
                        "Period");

                if (tempAttribPeriod != null) {
                    java.lang.String content = tempAttribPeriod;

                    object.setPeriod(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribPeriod));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Period is missing");
                }

                handledAttributes.add("Period");

                // handle attribute "Transacted"
                java.lang.String tempAttribTransacted = reader.getAttributeValue("www.fenealgestweb.it",
                        "Transacted");

                if (tempAttribTransacted != null) {
                    java.lang.String content = tempAttribTransacted;

                    object.setTransacted(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribTransacted));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Transacted is missing");
                }

                handledAttributes.add("Transacted");

                // handle attribute "Struttura"
                java.lang.String tempAttribStruttura = reader.getAttributeValue("www.fenealgestweb.it",
                        "Struttura");

                if (tempAttribStruttura != null) {
                    java.lang.String content = tempAttribStruttura;

                    object.setStruttura(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribStruttura));
                } else {
                }

                handledAttributes.add("Struttura");

                // handle attribute "Area"
                java.lang.String tempAttribArea = reader.getAttributeValue("www.fenealgestweb.it",
                        "Area");

                if (tempAttribArea != null) {
                    java.lang.String content = tempAttribArea;

                    object.setArea(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribArea));
                } else {
                }

                handledAttributes.add("Area");

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("www.fenealgestweb.it",
                            "Workers").equals(reader.getName())) ||
                        new javax.xml.namespace.QName("", "Workers").equals(
                            reader.getName())) {
                    object.setWorkers(it.fenealgestweb.www.ArrayOfWorkerDTO.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("www.fenealgestweb.it",
                            "Errore").equals(reader.getName())) ||
                        new javax.xml.namespace.QName("", "Errore").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Errore" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setErrore(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("www.fenealgestweb.it",
                            "FenealwebData").equals(reader.getName())) ||
                        new javax.xml.namespace.QName("", "FenealwebData").equals(
                            reader.getName())) {
                    object.setFenealwebData(it.fenealgestweb.www.FenealwebData.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // 2 - A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
