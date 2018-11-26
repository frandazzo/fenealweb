/**
 * WorkerDTOE.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  WorkerDTOE bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class WorkerDTOE implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = WorkerDTO
       Namespace URI = http://www.fenealgestweb.it/
       Namespace Prefix = ns2
     */

    /**
     * field for Subscription
     */
    protected it.fenealgestweb.www.SubscriptionDTOE localSubscription;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSubscriptionTracker = false;

    /**
     * field for Subscriptions
     */
    protected it.fenealgestweb.www.ArrayOfSubscriptionDTOE localSubscriptions;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSubscriptionsTracker = false;

    /**
     * field for Documents
     */
    protected it.fenealgestweb.www.ArrayOfDocumentDTOE localDocuments;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDocumentsTracker = false;

    /**
     * field for RowNumber
     * This was an Attribute!
     */
    protected int localRowNumber;

    /**
     * field for IsValid
     * This was an Attribute!
     */
    protected boolean localIsValid;

    /**
     * field for ExistBirthPlace
     * This was an Attribute!
     */
    protected boolean localExistBirthPlace;

    /**
     * field for ExistLivingPlace
     * This was an Attribute!
     */
    protected boolean localExistLivingPlace;

    /**
     * field for Nationality
     * This was an Attribute!
     */
    protected java.lang.String localNationality;

    /**
     * field for LastModifer
     * This was an Attribute!
     */
    protected java.lang.String localLastModifer;

    /**
     * field for Errors
     * This was an Attribute!
     */
    protected java.lang.String localErrors;

    /**
     * field for Name
     * This was an Attribute!
     */
    protected java.lang.String localName;

    /**
     * field for Surname
     * This was an Attribute!
     */
    protected java.lang.String localSurname;

    /**
     * field for Fiscalcode
     * This was an Attribute!
     */
    protected java.lang.String localFiscalcode;

    /**
     * field for BirthDate
     * This was an Attribute!
     */
    protected java.util.Calendar localBirthDate;

    /**
     * field for LastUpdate
     * This was an Attribute!
     */
    protected java.util.Calendar localLastUpdate;

    /**
     * field for BirthPlace
     * This was an Attribute!
     */
    protected java.lang.String localBirthPlace;

    /**
     * field for CurrentAzienda
     * This was an Attribute!
     */
    protected java.lang.String localCurrentAzienda;

    /**
     * field for IscrittoA
     * This was an Attribute!
     */
    protected java.lang.String localIscrittoA;

    /**
     * field for LiberoAl
     * This was an Attribute!
     */
    protected java.util.Calendar localLiberoAl;

    /**
     * field for LivingPlace
     * This was an Attribute!
     */
    protected java.lang.String localLivingPlace;

    /**
     * field for Address
     * This was an Attribute!
     */
    protected java.lang.String localAddress;

    /**
     * field for Cap
     * This was an Attribute!
     */
    protected java.lang.String localCap;

    /**
     * field for Phone
     * This was an Attribute!
     */
    protected java.lang.String localPhone;

    public boolean isSubscriptionSpecified() {
        return localSubscriptionTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.SubscriptionDTOE
     */
    public it.fenealgestweb.www.SubscriptionDTOE getSubscription() {
        return localSubscription;
    }

    /**
     * Auto generated setter method
     * @param param Subscription
     */
    public void setSubscription(it.fenealgestweb.www.SubscriptionDTOE param) {
        localSubscriptionTracker = param != null;

        this.localSubscription = param;
    }

    public boolean isSubscriptionsSpecified() {
        return localSubscriptionsTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.ArrayOfSubscriptionDTOE
     */
    public it.fenealgestweb.www.ArrayOfSubscriptionDTOE getSubscriptions() {
        return localSubscriptions;
    }

    /**
     * Auto generated setter method
     * @param param Subscriptions
     */
    public void setSubscriptions(
        it.fenealgestweb.www.ArrayOfSubscriptionDTOE param) {
        localSubscriptionsTracker = param != null;

        this.localSubscriptions = param;
    }

    public boolean isDocumentsSpecified() {
        return localDocumentsTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.ArrayOfDocumentDTOE
     */
    public it.fenealgestweb.www.ArrayOfDocumentDTOE getDocuments() {
        return localDocuments;
    }

    /**
     * Auto generated setter method
     * @param param Documents
     */
    public void setDocuments(it.fenealgestweb.www.ArrayOfDocumentDTOE param) {
        localDocumentsTracker = param != null;

        this.localDocuments = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getRowNumber() {
        return localRowNumber;
    }

    /**
     * Auto generated setter method
     * @param param RowNumber
     */
    public void setRowNumber(int param) {
        this.localRowNumber = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIsValid() {
        return localIsValid;
    }

    /**
     * Auto generated setter method
     * @param param IsValid
     */
    public void setIsValid(boolean param) {
        this.localIsValid = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getExistBirthPlace() {
        return localExistBirthPlace;
    }

    /**
     * Auto generated setter method
     * @param param ExistBirthPlace
     */
    public void setExistBirthPlace(boolean param) {
        this.localExistBirthPlace = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getExistLivingPlace() {
        return localExistLivingPlace;
    }

    /**
     * Auto generated setter method
     * @param param ExistLivingPlace
     */
    public void setExistLivingPlace(boolean param) {
        this.localExistLivingPlace = param;
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
        this.localNationality = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getLastModifer() {
        return localLastModifer;
    }

    /**
     * Auto generated setter method
     * @param param LastModifer
     */
    public void setLastModifer(java.lang.String param) {
        this.localLastModifer = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getErrors() {
        return localErrors;
    }

    /**
     * Auto generated setter method
     * @param param Errors
     */
    public void setErrors(java.lang.String param) {
        this.localErrors = param;
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
        this.localName = param;
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
        this.localSurname = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFiscalcode() {
        return localFiscalcode;
    }

    /**
     * Auto generated setter method
     * @param param Fiscalcode
     */
    public void setFiscalcode(java.lang.String param) {
        this.localFiscalcode = param;
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
     * @return java.util.Calendar
     */
    public java.util.Calendar getLastUpdate() {
        return localLastUpdate;
    }

    /**
     * Auto generated setter method
     * @param param LastUpdate
     */
    public void setLastUpdate(java.util.Calendar param) {
        this.localLastUpdate = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getBirthPlace() {
        return localBirthPlace;
    }

    /**
     * Auto generated setter method
     * @param param BirthPlace
     */
    public void setBirthPlace(java.lang.String param) {
        this.localBirthPlace = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCurrentAzienda() {
        return localCurrentAzienda;
    }

    /**
     * Auto generated setter method
     * @param param CurrentAzienda
     */
    public void setCurrentAzienda(java.lang.String param) {
        this.localCurrentAzienda = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getIscrittoA() {
        return localIscrittoA;
    }

    /**
     * Auto generated setter method
     * @param param IscrittoA
     */
    public void setIscrittoA(java.lang.String param) {
        this.localIscrittoA = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getLiberoAl() {
        return localLiberoAl;
    }

    /**
     * Auto generated setter method
     * @param param LiberoAl
     */
    public void setLiberoAl(java.util.Calendar param) {
        this.localLiberoAl = param;
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
        this.localLivingPlace = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getAddress() {
        return localAddress;
    }

    /**
     * Auto generated setter method
     * @param param Address
     */
    public void setAddress(java.lang.String param) {
        this.localAddress = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCap() {
        return localCap;
    }

    /**
     * Auto generated setter method
     * @param param Cap
     */
    public void setCap(java.lang.String param) {
        this.localCap = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPhone() {
        return localPhone;
    }

    /**
     * Auto generated setter method
     * @param param Phone
     */
    public void setPhone(java.lang.String param) {
        this.localPhone = param;
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
                    namespacePrefix + ":WorkerDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "WorkerDTO", xmlWriter);
            }
        }

        if (localRowNumber != java.lang.Integer.MIN_VALUE) {
            writeAttribute("http://www.fenealgestweb.it/", "RowNumber",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localRowNumber), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localRowNumber is null");
        }

        if (true) {
            writeAttribute("http://www.fenealgestweb.it/", "IsValid",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsValid), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localIsValid is null");
        }

        if (true) {
            writeAttribute("http://www.fenealgestweb.it/", "ExistBirthPlace",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExistBirthPlace), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localExistBirthPlace is null");
        }

        if (true) {
            writeAttribute("http://www.fenealgestweb.it/", "ExistLivingPlace",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExistLivingPlace), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localExistLivingPlace is null");
        }

        if (localNationality != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Nationality",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNationality), xmlWriter);
        }

        if (localLastModifer != null) {
            writeAttribute("http://www.fenealgestweb.it/", "LastModifer",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLastModifer), xmlWriter);
        }

        if (localErrors != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Errors",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localErrors), xmlWriter);
        }

        if (localName != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Name",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localName), xmlWriter);
        }

        if (localSurname != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Surname",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSurname), xmlWriter);
        }

        if (localFiscalcode != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Fiscalcode",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localFiscalcode), xmlWriter);
        }

        if (localBirthDate != null) {
            writeAttribute("http://www.fenealgestweb.it/", "BirthDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localBirthDate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localBirthDate is null");
        }

        if (localLastUpdate != null) {
            writeAttribute("http://www.fenealgestweb.it/", "LastUpdate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLastUpdate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localLastUpdate is null");
        }

        if (localBirthPlace != null) {
            writeAttribute("http://www.fenealgestweb.it/", "BirthPlace",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localBirthPlace), xmlWriter);
        }

        if (localCurrentAzienda != null) {
            writeAttribute("http://www.fenealgestweb.it/", "CurrentAzienda",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCurrentAzienda), xmlWriter);
        }

        if (localIscrittoA != null) {
            writeAttribute("http://www.fenealgestweb.it/", "IscrittoA",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIscrittoA), xmlWriter);
        }

        if (localLiberoAl != null) {
            writeAttribute("http://www.fenealgestweb.it/", "LiberoAl",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLiberoAl), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localLiberoAl is null");
        }

        if (localLivingPlace != null) {
            writeAttribute("http://www.fenealgestweb.it/", "LivingPlace",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLivingPlace), xmlWriter);
        }

        if (localAddress != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Address",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localAddress), xmlWriter);
        }

        if (localCap != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Cap",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCap), xmlWriter);
        }

        if (localPhone != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Phone",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localPhone), xmlWriter);
        }

        if (localSubscriptionTracker) {
            if (localSubscription == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Subscription cannot be null!!");
            }

            localSubscription.serialize(new javax.xml.namespace.QName(
                    "http://www.fenealgestweb.it/", "Subscription"), xmlWriter);
        }

        if (localSubscriptionsTracker) {
            if (localSubscriptions == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Subscriptions cannot be null!!");
            }

            localSubscriptions.serialize(new javax.xml.namespace.QName(
                    "http://www.fenealgestweb.it/", "Subscriptions"), xmlWriter);
        }

        if (localDocumentsTracker) {
            if (localDocuments == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Documents cannot be null!!");
            }

            localDocuments.serialize(new javax.xml.namespace.QName(
                    "http://www.fenealgestweb.it/", "Documents"), xmlWriter);
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
        public static WorkerDTOE parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            WorkerDTOE object = new WorkerDTOE();

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

                        if (!"WorkerDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (WorkerDTOE) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "RowNumber"
                java.lang.String tempAttribRowNumber = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "RowNumber");

                if (tempAttribRowNumber != null) {
                    java.lang.String content = tempAttribRowNumber;

                    object.setRowNumber(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribRowNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute RowNumber is missing");
                }

                handledAttributes.add("RowNumber");

                // handle attribute "IsValid"
                java.lang.String tempAttribIsValid = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "IsValid");

                if (tempAttribIsValid != null) {
                    java.lang.String content = tempAttribIsValid;

                    object.setIsValid(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsValid));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute IsValid is missing");
                }

                handledAttributes.add("IsValid");

                // handle attribute "ExistBirthPlace"
                java.lang.String tempAttribExistBirthPlace = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "ExistBirthPlace");

                if (tempAttribExistBirthPlace != null) {
                    java.lang.String content = tempAttribExistBirthPlace;

                    object.setExistBirthPlace(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribExistBirthPlace));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ExistBirthPlace is missing");
                }

                handledAttributes.add("ExistBirthPlace");

                // handle attribute "ExistLivingPlace"
                java.lang.String tempAttribExistLivingPlace = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "ExistLivingPlace");

                if (tempAttribExistLivingPlace != null) {
                    java.lang.String content = tempAttribExistLivingPlace;

                    object.setExistLivingPlace(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribExistLivingPlace));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ExistLivingPlace is missing");
                }

                handledAttributes.add("ExistLivingPlace");

                // handle attribute "Nationality"
                java.lang.String tempAttribNationality = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Nationality");

                if (tempAttribNationality != null) {
                    java.lang.String content = tempAttribNationality;

                    object.setNationality(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNationality));
                } else {
                }

                handledAttributes.add("Nationality");

                // handle attribute "LastModifer"
                java.lang.String tempAttribLastModifer = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "LastModifer");

                if (tempAttribLastModifer != null) {
                    java.lang.String content = tempAttribLastModifer;

                    object.setLastModifer(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribLastModifer));
                } else {
                }

                handledAttributes.add("LastModifer");

                // handle attribute "Errors"
                java.lang.String tempAttribErrors = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Errors");

                if (tempAttribErrors != null) {
                    java.lang.String content = tempAttribErrors;

                    object.setErrors(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribErrors));
                } else {
                }

                handledAttributes.add("Errors");

                // handle attribute "Name"
                java.lang.String tempAttribName = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Name");

                if (tempAttribName != null) {
                    java.lang.String content = tempAttribName;

                    object.setName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribName));
                } else {
                }

                handledAttributes.add("Name");

                // handle attribute "Surname"
                java.lang.String tempAttribSurname = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Surname");

                if (tempAttribSurname != null) {
                    java.lang.String content = tempAttribSurname;

                    object.setSurname(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribSurname));
                } else {
                }

                handledAttributes.add("Surname");

                // handle attribute "Fiscalcode"
                java.lang.String tempAttribFiscalcode = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Fiscalcode");

                if (tempAttribFiscalcode != null) {
                    java.lang.String content = tempAttribFiscalcode;

                    object.setFiscalcode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribFiscalcode));
                } else {
                }

                handledAttributes.add("Fiscalcode");

                // handle attribute "BirthDate"
                java.lang.String tempAttribBirthDate = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "BirthDate");

                if (tempAttribBirthDate != null) {
                    java.lang.String content = tempAttribBirthDate;

                    object.setBirthDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribBirthDate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute BirthDate is missing");
                }

                handledAttributes.add("BirthDate");

                // handle attribute "LastUpdate"
                java.lang.String tempAttribLastUpdate = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "LastUpdate");

                if (tempAttribLastUpdate != null) {
                    java.lang.String content = tempAttribLastUpdate;

                    object.setLastUpdate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribLastUpdate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute LastUpdate is missing");
                }

                handledAttributes.add("LastUpdate");

                // handle attribute "BirthPlace"
                java.lang.String tempAttribBirthPlace = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "BirthPlace");

                if (tempAttribBirthPlace != null) {
                    java.lang.String content = tempAttribBirthPlace;

                    object.setBirthPlace(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribBirthPlace));
                } else {
                }

                handledAttributes.add("BirthPlace");

                // handle attribute "CurrentAzienda"
                java.lang.String tempAttribCurrentAzienda = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "CurrentAzienda");

                if (tempAttribCurrentAzienda != null) {
                    java.lang.String content = tempAttribCurrentAzienda;

                    object.setCurrentAzienda(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribCurrentAzienda));
                } else {
                }

                handledAttributes.add("CurrentAzienda");

                // handle attribute "IscrittoA"
                java.lang.String tempAttribIscrittoA = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "IscrittoA");

                if (tempAttribIscrittoA != null) {
                    java.lang.String content = tempAttribIscrittoA;

                    object.setIscrittoA(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribIscrittoA));
                } else {
                }

                handledAttributes.add("IscrittoA");

                // handle attribute "LiberoAl"
                java.lang.String tempAttribLiberoAl = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "LiberoAl");

                if (tempAttribLiberoAl != null) {
                    java.lang.String content = tempAttribLiberoAl;

                    object.setLiberoAl(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribLiberoAl));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute LiberoAl is missing");
                }

                handledAttributes.add("LiberoAl");

                // handle attribute "LivingPlace"
                java.lang.String tempAttribLivingPlace = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "LivingPlace");

                if (tempAttribLivingPlace != null) {
                    java.lang.String content = tempAttribLivingPlace;

                    object.setLivingPlace(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribLivingPlace));
                } else {
                }

                handledAttributes.add("LivingPlace");

                // handle attribute "Address"
                java.lang.String tempAttribAddress = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Address");

                if (tempAttribAddress != null) {
                    java.lang.String content = tempAttribAddress;

                    object.setAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribAddress));
                } else {
                }

                handledAttributes.add("Address");

                // handle attribute "Cap"
                java.lang.String tempAttribCap = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Cap");

                if (tempAttribCap != null) {
                    java.lang.String content = tempAttribCap;

                    object.setCap(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribCap));
                } else {
                }

                handledAttributes.add("Cap");

                // handle attribute "Phone"
                java.lang.String tempAttribPhone = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Phone");

                if (tempAttribPhone != null) {
                    java.lang.String content = tempAttribPhone;

                    object.setPhone(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribPhone));
                } else {
                }

                handledAttributes.add("Phone");

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Subscription").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Subscription").equals(
                            reader.getName())) {
                    object.setSubscription(it.fenealgestweb.www.SubscriptionDTOE.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Subscriptions").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Subscriptions").equals(
                            reader.getName())) {
                    object.setSubscriptions(it.fenealgestweb.www.ArrayOfSubscriptionDTOE.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "http://www.fenealgestweb.it/", "Documents").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "Documents").equals(
                            reader.getName())) {
                    object.setDocuments(it.fenealgestweb.www.ArrayOfDocumentDTOE.Factory.parse(
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
