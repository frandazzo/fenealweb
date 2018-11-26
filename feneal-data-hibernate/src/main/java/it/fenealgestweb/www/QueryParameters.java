/**
 * QueryParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  QueryParameters bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class QueryParameters implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = QueryParameters
       Namespace URI = http://www.fenealgestweb.it/
       Namespace Prefix = ns2
     */

    /**
     * field for Name
     */
    protected java.lang.String localName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNameTracker = false;

    /**
     * field for Surname
     */
    protected java.lang.String localSurname;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSurnameTracker = false;

    /**
     * field for LivingPlace
     */
    protected java.lang.String localLivingPlace;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localLivingPlaceTracker = false;

    /**
     * field for Nationality
     */
    protected java.lang.String localNationality;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNationalityTracker = false;

    /**
     * field for BirthDate
     */
    protected java.util.Calendar localBirthDate;

    /**
     * field for CheckDate
     */
    protected boolean localCheckDate;

    /**
     * field for Region
     */
    protected java.lang.String localRegion;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localRegionTracker = false;

    /**
     * field for MaxResult
     */
    protected int localMaxResult;

    /**
     * field for CompanyFiscalCode
     */
    protected java.lang.String localCompanyFiscalCode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCompanyFiscalCodeTracker = false;

    /**
     * field for CompanyDescription
     */
    protected java.lang.String localCompanyDescription;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCompanyDescriptionTracker = false;

    public boolean isNameSpecified() {
        return localNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getName() {
        return localName;
    }

    /**
     * Auto generated setter method
     * @param param Name
     */
    public void setName(java.lang.String param) {
        localNameTracker = param != null;

        this.localName = param;
    }

    public boolean isSurnameSpecified() {
        return localSurnameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSurname() {
        return localSurname;
    }

    /**
     * Auto generated setter method
     * @param param Surname
     */
    public void setSurname(java.lang.String param) {
        localSurnameTracker = param != null;

        this.localSurname = param;
    }

    public boolean isLivingPlaceSpecified() {
        return localLivingPlaceTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getLivingPlace() {
        return localLivingPlace;
    }

    /**
     * Auto generated setter method
     * @param param LivingPlace
     */
    public void setLivingPlace(java.lang.String param) {
        localLivingPlaceTracker = param != null;

        this.localLivingPlace = param;
    }

    public boolean isNationalitySpecified() {
        return localNationalityTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNationality() {
        return localNationality;
    }

    /**
     * Auto generated setter method
     * @param param Nationality
     */
    public void setNationality(java.lang.String param) {
        localNationalityTracker = param != null;

        this.localNationality = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getBirthDate() {
        return localBirthDate;
    }

    /**
     * Auto generated setter method
     * @param param BirthDate
     */
    public void setBirthDate(java.util.Calendar param) {
        this.localBirthDate = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getCheckDate() {
        return localCheckDate;
    }

    /**
     * Auto generated setter method
     * @param param CheckDate
     */
    public void setCheckDate(boolean param) {
        this.localCheckDate = param;
    }

    public boolean isRegionSpecified() {
        return localRegionTracker;
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
        localRegionTracker = param != null;

        this.localRegion = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getMaxResult() {
        return localMaxResult;
    }

    /**
     * Auto generated setter method
     * @param param MaxResult
     */
    public void setMaxResult(int param) {
        this.localMaxResult = param;
    }

    public boolean isCompanyFiscalCodeSpecified() {
        return localCompanyFiscalCodeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCompanyFiscalCode() {
        return localCompanyFiscalCode;
    }

    /**
     * Auto generated setter method
     * @param param CompanyFiscalCode
     */
    public void setCompanyFiscalCode(java.lang.String param) {
        localCompanyFiscalCodeTracker = param != null;

        this.localCompanyFiscalCode = param;
    }

    public boolean isCompanyDescriptionSpecified() {
        return localCompanyDescriptionTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCompanyDescription() {
        return localCompanyDescription;
    }

    /**
     * Auto generated setter method
     * @param param CompanyDescription
     */
    public void setCompanyDescription(java.lang.String param) {
        localCompanyDescriptionTracker = param != null;

        this.localCompanyDescription = param;
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
                    "http://www.fenealgestweb.it/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":QueryParameters", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "QueryParameters", xmlWriter);
            }
        }

        if (localNameTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "Name", xmlWriter);

            if (localName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Name cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localName);
            }

            xmlWriter.writeEndElement();
        }

        if (localSurnameTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "Surname", xmlWriter);

            if (localSurname == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Surname cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSurname);
            }

            xmlWriter.writeEndElement();
        }

        if (localLivingPlaceTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "LivingPlace", xmlWriter);

            if (localLivingPlace == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "LivingPlace cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localLivingPlace);
            }

            xmlWriter.writeEndElement();
        }

        if (localNationalityTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "Nationality", xmlWriter);

            if (localNationality == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Nationality cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNationality);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://www.fenealgestweb.it/";
        writeStartElement(null, namespace, "BirthDate", xmlWriter);

        if (localBirthDate == null) {
            // write the nil attribute
            throw new org.apache.axis2.databinding.ADBException(
                "BirthDate cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localBirthDate));
        }

        xmlWriter.writeEndElement();

        namespace = "http://www.fenealgestweb.it/";
        writeStartElement(null, namespace, "CheckDate", xmlWriter);

        if (false) {
            throw new org.apache.axis2.databinding.ADBException(
                "CheckDate cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCheckDate));
        }

        xmlWriter.writeEndElement();

        if (localRegionTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "Region", xmlWriter);

            if (localRegion == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Region cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localRegion);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://www.fenealgestweb.it/";
        writeStartElement(null, namespace, "MaxResult", xmlWriter);

        if (localMaxResult == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "MaxResult cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localMaxResult));
        }

        xmlWriter.writeEndElement();

        if (localCompanyFiscalCodeTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "CompanyFiscalCode", xmlWriter);

            if (localCompanyFiscalCode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CompanyFiscalCode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCompanyFiscalCode);
            }

            xmlWriter.writeEndElement();
        }

        if (localCompanyDescriptionTracker) {
            namespace = "http://www.fenealgestweb.it/";
            writeStartElement(null, namespace, "CompanyDescription", xmlWriter);

            if (localCompanyDescription == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CompanyDescription cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCompanyDescription);
            }

            xmlWriter.writeEndElement();
        }

        xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("http://www.fenealgestweb.it/")) {
            return "ns2";
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
        public static QueryParameters parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            QueryParameters object = new QueryParameters();

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

                        if (!"QueryParameters".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (QueryParameters) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Name").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Name").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Name" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Surname").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Surname").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Surname" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSurname(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "LivingPlace").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "LivingPlace").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "LivingPlace" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setLivingPlace(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Nationality").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Nationality").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Nationality" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNationality(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "BirthDate").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "BirthDate").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "BirthDate" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setBirthDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // 1 - A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "CheckDate").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "CheckDate").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CheckDate" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCheckDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // 1 - A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Region").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Region").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Region" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setRegion(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "MaxResult").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "MaxResult").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "MaxResult" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setMaxResult(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // 1 - A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "CompanyFiscalCode").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "CompanyFiscalCode").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CompanyFiscalCode" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCompanyFiscalCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "CompanyDescription").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "CompanyDescription").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CompanyDescription" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCompanyDescription(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

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
