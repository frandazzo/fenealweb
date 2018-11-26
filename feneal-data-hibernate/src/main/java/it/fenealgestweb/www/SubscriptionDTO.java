/**
 * SubscriptionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  SubscriptionDTO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class SubscriptionDTO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = SubscriptionDTO
       Namespace URI = www.fenealgestweb.it
       Namespace Prefix = ns1
     */

    /**
     * field for FenealwebSubscriptionDTOData
     */
    protected it.fenealgestweb.www.FenealwebSubscriptionDTOData localFenealwebSubscriptionDTOData;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFenealwebSubscriptionDTODataTracker = false;

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

    /**
     * field for Sector
     * This was an Attribute!
     */
    protected java.lang.String localSector;

    /**
     * field for Contract
     * This was an Attribute!
     */
    protected java.lang.String localContract;

    /**
     * field for Region
     * This was an Attribute!
     */
    protected java.lang.String localRegion;

    /**
     * field for Entity
     * This was an Attribute!
     */
    protected java.lang.String localEntity;

    /**
     * field for EmployCompany
     * This was an Attribute!
     */
    protected java.lang.String localEmployCompany;

    /**
     * field for FiscalCode
     * This was an Attribute!
     */
    protected java.lang.String localFiscalCode;

    /**
     * field for Quota
     * This was an Attribute!
     */
    protected double localQuota;

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
     * field for Semester
     * This was an Attribute!
     */
    protected int localSemester;

    /**
     * field for PeriodType
     * This was an Attribute!
     */
    protected it.fenealgestweb.www.PeriodType localPeriodType;

    /**
     * field for Level
     * This was an Attribute!
     */
    protected java.lang.String localLevel;

    /**
     * field for Province
     * This was an Attribute!
     */
    protected java.lang.String localProvince;

    public boolean isFenealwebSubscriptionDTODataSpecified() {
        return localFenealwebSubscriptionDTODataTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.FenealwebSubscriptionDTOData
     */
    public it.fenealgestweb.www.FenealwebSubscriptionDTOData getFenealwebSubscriptionDTOData() {
        return localFenealwebSubscriptionDTOData;
    }

    /**
     * Auto generated setter method
     * @param param FenealwebSubscriptionDTOData
     */
    public void setFenealwebSubscriptionDTOData(
        it.fenealgestweb.www.FenealwebSubscriptionDTOData param) {
        localFenealwebSubscriptionDTODataTracker = param != null;

        this.localFenealwebSubscriptionDTOData = param;
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
    public java.lang.String getContract() {
        return localContract;
    }

    /**
     * Auto generated setter method
     * @param param Contract
     */
    public void setContract(java.lang.String param) {
        this.localContract = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getRegion() {
        return localRegion;
    }

    /**
     * Auto generated setter method
     * @param param Region
     */
    public void setRegion(java.lang.String param) {
        this.localRegion = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getEntity() {
        return localEntity;
    }

    /**
     * Auto generated setter method
     * @param param Entity
     */
    public void setEntity(java.lang.String param) {
        this.localEntity = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getEmployCompany() {
        return localEmployCompany;
    }

    /**
     * Auto generated setter method
     * @param param EmployCompany
     */
    public void setEmployCompany(java.lang.String param) {
        this.localEmployCompany = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFiscalCode() {
        return localFiscalCode;
    }

    /**
     * Auto generated setter method
     * @param param FiscalCode
     */
    public void setFiscalCode(java.lang.String param) {
        this.localFiscalCode = param;
    }

    /**
     * Auto generated getter method
     * @return double
     */
    public double getQuota() {
        return localQuota;
    }

    /**
     * Auto generated setter method
     * @param param Quota
     */
    public void setQuota(double param) {
        this.localQuota = param;
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
    public int getSemester() {
        return localSemester;
    }

    /**
     * Auto generated setter method
     * @param param Semester
     */
    public void setSemester(int param) {
        this.localSemester = param;
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
     * @return java.lang.String
     */
    public java.lang.String getLevel() {
        return localLevel;
    }

    /**
     * Auto generated setter method
     * @param param Level
     */
    public void setLevel(java.lang.String param) {
        this.localLevel = param;
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
                    namespacePrefix + ":SubscriptionDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "SubscriptionDTO", xmlWriter);
            }
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

        if (localSector != null) {
            writeAttribute("www.fenealgestweb.it", "Sector",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSector), xmlWriter);
        }

        if (localContract != null) {
            writeAttribute("www.fenealgestweb.it", "Contract",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localContract), xmlWriter);
        }

        if (localRegion != null) {
            writeAttribute("www.fenealgestweb.it", "Region",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localRegion), xmlWriter);
        }

        if (localEntity != null) {
            writeAttribute("www.fenealgestweb.it", "Entity",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localEntity), xmlWriter);
        }

        if (localEmployCompany != null) {
            writeAttribute("www.fenealgestweb.it", "EmployCompany",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localEmployCompany), xmlWriter);
        }

        if (localFiscalCode != null) {
            writeAttribute("www.fenealgestweb.it", "FiscalCode",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localFiscalCode), xmlWriter);
        }

        if (!java.lang.Double.isNaN(localQuota)) {
            writeAttribute("www.fenealgestweb.it", "Quota",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localQuota), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localQuota is null");
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

        if (localSemester != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Semester",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSemester), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localSemester is null");
        }

        if (localPeriodType != null) {
            writeAttribute("www.fenealgestweb.it", "PeriodType",
                localPeriodType.toString(), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localPeriodType is null");
        }

        if (localLevel != null) {
            writeAttribute("www.fenealgestweb.it", "Level",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLevel), xmlWriter);
        }

        if (localProvince != null) {
            writeAttribute("www.fenealgestweb.it", "Province",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localProvince), xmlWriter);
        }

        if (localFenealwebSubscriptionDTODataTracker) {
            if (localFenealwebSubscriptionDTOData == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "FenealwebSubscriptionDTOData cannot be null!!");
            }

            localFenealwebSubscriptionDTOData.serialize(new javax.xml.namespace.QName(
                    "www.fenealgestweb.it", "FenealwebSubscriptionDTOData"),
                xmlWriter);
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
        public static SubscriptionDTO parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            SubscriptionDTO object = new SubscriptionDTO();

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

                        if (!"SubscriptionDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (SubscriptionDTO) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

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

                // handle attribute "Contract"
                java.lang.String tempAttribContract = reader.getAttributeValue("www.fenealgestweb.it",
                        "Contract");

                if (tempAttribContract != null) {
                    java.lang.String content = tempAttribContract;

                    object.setContract(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribContract));
                } else {
                }

                handledAttributes.add("Contract");

                // handle attribute "Region"
                java.lang.String tempAttribRegion = reader.getAttributeValue("www.fenealgestweb.it",
                        "Region");

                if (tempAttribRegion != null) {
                    java.lang.String content = tempAttribRegion;

                    object.setRegion(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribRegion));
                } else {
                }

                handledAttributes.add("Region");

                // handle attribute "Entity"
                java.lang.String tempAttribEntity = reader.getAttributeValue("www.fenealgestweb.it",
                        "Entity");

                if (tempAttribEntity != null) {
                    java.lang.String content = tempAttribEntity;

                    object.setEntity(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribEntity));
                } else {
                }

                handledAttributes.add("Entity");

                // handle attribute "EmployCompany"
                java.lang.String tempAttribEmployCompany = reader.getAttributeValue("www.fenealgestweb.it",
                        "EmployCompany");

                if (tempAttribEmployCompany != null) {
                    java.lang.String content = tempAttribEmployCompany;

                    object.setEmployCompany(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribEmployCompany));
                } else {
                }

                handledAttributes.add("EmployCompany");

                // handle attribute "FiscalCode"
                java.lang.String tempAttribFiscalCode = reader.getAttributeValue("www.fenealgestweb.it",
                        "FiscalCode");

                if (tempAttribFiscalCode != null) {
                    java.lang.String content = tempAttribFiscalCode;

                    object.setFiscalCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribFiscalCode));
                } else {
                }

                handledAttributes.add("FiscalCode");

                // handle attribute "Quota"
                java.lang.String tempAttribQuota = reader.getAttributeValue("www.fenealgestweb.it",
                        "Quota");

                if (tempAttribQuota != null) {
                    java.lang.String content = tempAttribQuota;

                    object.setQuota(org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(
                            tempAttribQuota));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Quota is missing");
                }

                handledAttributes.add("Quota");

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

                // handle attribute "Semester"
                java.lang.String tempAttribSemester = reader.getAttributeValue("www.fenealgestweb.it",
                        "Semester");

                if (tempAttribSemester != null) {
                    java.lang.String content = tempAttribSemester;

                    object.setSemester(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribSemester));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Semester is missing");
                }

                handledAttributes.add("Semester");

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

                // handle attribute "Level"
                java.lang.String tempAttribLevel = reader.getAttributeValue("www.fenealgestweb.it",
                        "Level");

                if (tempAttribLevel != null) {
                    java.lang.String content = tempAttribLevel;

                    object.setLevel(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribLevel));
                } else {
                }

                handledAttributes.add("Level");

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

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("www.fenealgestweb.it",
                            "FenealwebSubscriptionDTOData").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("",
                            "FenealwebSubscriptionDTOData").equals(
                            reader.getName())) {
                    object.setFenealwebSubscriptionDTOData(it.fenealgestweb.www.FenealwebSubscriptionDTOData.Factory.parse(
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
