/**
 * DTORendiconto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  DTORendiconto bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class DTORendiconto implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = DTORendiconto
       Namespace URI = www.fenealgestweb.it
       Namespace Prefix = ns1
     */

    /**
     * field for Items
     */
    protected it.fenealgestweb.www.ArrayOfDTORendicontoItem localItems;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localItemsTracker = false;

    /**
     * field for Regione
     * This was an Attribute!
     */
    protected java.lang.String localRegione;

    /**
     * field for Proprietario
     * This was an Attribute!
     */
    protected java.lang.String localProprietario;

    /**
     * field for Provincia
     * This was an Attribute!
     */
    protected java.lang.String localProvincia;

    /**
     * field for Anno
     * This was an Attribute!
     */
    protected int localAnno;

    /**
     * field for IsRegionale
     * This was an Attribute!
     */
    protected boolean localIsRegionale;

    /**
     * field for IsBilancioQuadrato
     * This was an Attribute!
     */
    protected boolean localIsBilancioQuadrato;

    /**
     * field for IsPreventivoQuadrato
     * This was an Attribute!
     */
    protected boolean localIsPreventivoQuadrato;

    /**
     * field for StatoPatrimoniale
     * This was an Attribute!
     */
    protected java.lang.String localStatoPatrimoniale;

    /**
     * field for ContoRLST
     * This was an Attribute!
     */
    protected double localContoRLST;

    /**
     * field for Versione
     * This was an Attribute!
     */
    protected int localVersione;

    public boolean isItemsSpecified() {
        return localItemsTracker;
    }

    /**
     * Auto generated getter method
     * @return it.fenealgestweb.www.ArrayOfDTORendicontoItem
     */
    public it.fenealgestweb.www.ArrayOfDTORendicontoItem getItems() {
        return localItems;
    }

    /**
     * Auto generated setter method
     * @param param Items
     */
    public void setItems(it.fenealgestweb.www.ArrayOfDTORendicontoItem param) {
        localItemsTracker = param != null;

        this.localItems = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getRegione() {
        return localRegione;
    }

    /**
     * Auto generated setter method
     * @param param Regione
     */
    public void setRegione(java.lang.String param) {
        this.localRegione = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getProprietario() {
        return localProprietario;
    }

    /**
     * Auto generated setter method
     * @param param Proprietario
     */
    public void setProprietario(java.lang.String param) {
        this.localProprietario = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getProvincia() {
        return localProvincia;
    }

    /**
     * Auto generated setter method
     * @param param Provincia
     */
    public void setProvincia(java.lang.String param) {
        this.localProvincia = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getAnno() {
        return localAnno;
    }

    /**
     * Auto generated setter method
     * @param param Anno
     */
    public void setAnno(int param) {
        this.localAnno = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIsRegionale() {
        return localIsRegionale;
    }

    /**
     * Auto generated setter method
     * @param param IsRegionale
     */
    public void setIsRegionale(boolean param) {
        this.localIsRegionale = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIsBilancioQuadrato() {
        return localIsBilancioQuadrato;
    }

    /**
     * Auto generated setter method
     * @param param IsBilancioQuadrato
     */
    public void setIsBilancioQuadrato(boolean param) {
        this.localIsBilancioQuadrato = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIsPreventivoQuadrato() {
        return localIsPreventivoQuadrato;
    }

    /**
     * Auto generated setter method
     * @param param IsPreventivoQuadrato
     */
    public void setIsPreventivoQuadrato(boolean param) {
        this.localIsPreventivoQuadrato = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStatoPatrimoniale() {
        return localStatoPatrimoniale;
    }

    /**
     * Auto generated setter method
     * @param param StatoPatrimoniale
     */
    public void setStatoPatrimoniale(java.lang.String param) {
        this.localStatoPatrimoniale = param;
    }

    /**
     * Auto generated getter method
     * @return double
     */
    public double getContoRLST() {
        return localContoRLST;
    }

    /**
     * Auto generated setter method
     * @param param ContoRLST
     */
    public void setContoRLST(double param) {
        this.localContoRLST = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getVersione() {
        return localVersione;
    }

    /**
     * Auto generated setter method
     * @param param Versione
     */
    public void setVersione(int param) {
        this.localVersione = param;
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
                    namespacePrefix + ":DTORendiconto", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "DTORendiconto", xmlWriter);
            }
        }

        if (localRegione != null) {
            writeAttribute("www.fenealgestweb.it", "Regione",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localRegione), xmlWriter);
        }

        if (localProprietario != null) {
            writeAttribute("www.fenealgestweb.it", "Proprietario",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localProprietario), xmlWriter);
        }

        if (localProvincia != null) {
            writeAttribute("www.fenealgestweb.it", "Provincia",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localProvincia), xmlWriter);
        }

        if (localAnno != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Anno",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localAnno), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localAnno is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "IsRegionale",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsRegionale), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localIsRegionale is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "IsBilancioQuadrato",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsBilancioQuadrato), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localIsBilancioQuadrato is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "IsPreventivoQuadrato",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsPreventivoQuadrato), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localIsPreventivoQuadrato is null");
        }

        if (localStatoPatrimoniale != null) {
            writeAttribute("www.fenealgestweb.it", "StatoPatrimoniale",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localStatoPatrimoniale), xmlWriter);
        }

        if (!java.lang.Double.isNaN(localContoRLST)) {
            writeAttribute("www.fenealgestweb.it", "ContoRLST",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localContoRLST), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localContoRLST is null");
        }

        if (localVersione != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Versione",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localVersione), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localVersione is null");
        }

        if (localItemsTracker) {
            if (localItems == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Items cannot be null!!");
            }

            localItems.serialize(new javax.xml.namespace.QName(
                    "www.fenealgestweb.it", "Items"), xmlWriter);
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
        public static DTORendiconto parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            DTORendiconto object = new DTORendiconto();

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

                        if (!"DTORendiconto".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (DTORendiconto) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "Regione"
                java.lang.String tempAttribRegione = reader.getAttributeValue("www.fenealgestweb.it",
                        "Regione");

                if (tempAttribRegione != null) {
                    java.lang.String content = tempAttribRegione;

                    object.setRegione(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribRegione));
                } else {
                }

                handledAttributes.add("Regione");

                // handle attribute "Proprietario"
                java.lang.String tempAttribProprietario = reader.getAttributeValue("www.fenealgestweb.it",
                        "Proprietario");

                if (tempAttribProprietario != null) {
                    java.lang.String content = tempAttribProprietario;

                    object.setProprietario(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribProprietario));
                } else {
                }

                handledAttributes.add("Proprietario");

                // handle attribute "Provincia"
                java.lang.String tempAttribProvincia = reader.getAttributeValue("www.fenealgestweb.it",
                        "Provincia");

                if (tempAttribProvincia != null) {
                    java.lang.String content = tempAttribProvincia;

                    object.setProvincia(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribProvincia));
                } else {
                }

                handledAttributes.add("Provincia");

                // handle attribute "Anno"
                java.lang.String tempAttribAnno = reader.getAttributeValue("www.fenealgestweb.it",
                        "Anno");

                if (tempAttribAnno != null) {
                    java.lang.String content = tempAttribAnno;

                    object.setAnno(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribAnno));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Anno is missing");
                }

                handledAttributes.add("Anno");

                // handle attribute "IsRegionale"
                java.lang.String tempAttribIsRegionale = reader.getAttributeValue("www.fenealgestweb.it",
                        "IsRegionale");

                if (tempAttribIsRegionale != null) {
                    java.lang.String content = tempAttribIsRegionale;

                    object.setIsRegionale(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsRegionale));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute IsRegionale is missing");
                }

                handledAttributes.add("IsRegionale");

                // handle attribute "IsBilancioQuadrato"
                java.lang.String tempAttribIsBilancioQuadrato = reader.getAttributeValue("www.fenealgestweb.it",
                        "IsBilancioQuadrato");

                if (tempAttribIsBilancioQuadrato != null) {
                    java.lang.String content = tempAttribIsBilancioQuadrato;

                    object.setIsBilancioQuadrato(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsBilancioQuadrato));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute IsBilancioQuadrato is missing");
                }

                handledAttributes.add("IsBilancioQuadrato");

                // handle attribute "IsPreventivoQuadrato"
                java.lang.String tempAttribIsPreventivoQuadrato = reader.getAttributeValue("www.fenealgestweb.it",
                        "IsPreventivoQuadrato");

                if (tempAttribIsPreventivoQuadrato != null) {
                    java.lang.String content = tempAttribIsPreventivoQuadrato;

                    object.setIsPreventivoQuadrato(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsPreventivoQuadrato));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute IsPreventivoQuadrato is missing");
                }

                handledAttributes.add("IsPreventivoQuadrato");

                // handle attribute "StatoPatrimoniale"
                java.lang.String tempAttribStatoPatrimoniale = reader.getAttributeValue("www.fenealgestweb.it",
                        "StatoPatrimoniale");

                if (tempAttribStatoPatrimoniale != null) {
                    java.lang.String content = tempAttribStatoPatrimoniale;

                    object.setStatoPatrimoniale(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribStatoPatrimoniale));
                } else {
                }

                handledAttributes.add("StatoPatrimoniale");

                // handle attribute "ContoRLST"
                java.lang.String tempAttribContoRLST = reader.getAttributeValue("www.fenealgestweb.it",
                        "ContoRLST");

                if (tempAttribContoRLST != null) {
                    java.lang.String content = tempAttribContoRLST;

                    object.setContoRLST(org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(
                            tempAttribContoRLST));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ContoRLST is missing");
                }

                handledAttributes.add("ContoRLST");

                // handle attribute "Versione"
                java.lang.String tempAttribVersione = reader.getAttributeValue("www.fenealgestweb.it",
                        "Versione");

                if (tempAttribVersione != null) {
                    java.lang.String content = tempAttribVersione;

                    object.setVersione(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribVersione));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Versione is missing");
                }

                handledAttributes.add("Versione");

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("www.fenealgestweb.it",
                            "Items").equals(reader.getName())) ||
                        new javax.xml.namespace.QName("", "Items").equals(
                            reader.getName())) {
                    object.setItems(it.fenealgestweb.www.ArrayOfDTORendicontoItem.Factory.parse(
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
