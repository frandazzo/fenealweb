/**
 * DocumentDTOE.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  DocumentDTOE bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class DocumentDTOE implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = DocumentDTO
       Namespace URI = http://www.fenealgestweb.it/
       Namespace Prefix = ns2
     */

    /**
     * field for DocumentDate
     * This was an Attribute!
     */
    protected java.util.Calendar localDocumentDate;

    /**
     * field for DocumentType
     * This was an Attribute!
     */
    protected java.lang.String localDocumentType;

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
     * field for Province
     * This was an Attribute!
     */
    protected java.lang.String localProvince;

    /**
     * field for City
     * This was an Attribute!
     */
    protected java.lang.String localCity;

    /**
     * field for Region
     * This was an Attribute!
     */
    protected java.lang.String localRegion;

    /**
     * field for State
     * This was an Attribute!
     */
    protected java.lang.String localState;

    /**
     * field for Notes
     * This was an Attribute!
     */
    protected java.lang.String localNotes;

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getDocumentDate() {
        return localDocumentDate;
    }

    /**
     * Auto generated setter method
     * @param param DocumentDate
     */
    public void setDocumentDate(java.util.Calendar param) {
        this.localDocumentDate = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getDocumentType() {
        return localDocumentType;
    }

    /**
     * Auto generated setter method
     * @param param DocumentType
     */
    public void setDocumentType(java.lang.String param) {
        this.localDocumentType = param;
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
    public java.lang.String getCity() {
        return localCity;
    }

    /**
     * Auto generated setter method
     * @param param City
     */
    public void setCity(java.lang.String param) {
        this.localCity = param;
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
    public java.lang.String getState() {
        return localState;
    }

    /**
     * Auto generated setter method
     * @param param State
     */
    public void setState(java.lang.String param) {
        this.localState = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNotes() {
        return localNotes;
    }

    /**
     * Auto generated setter method
     * @param param Notes
     */
    public void setNotes(java.lang.String param) {
        this.localNotes = param;
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
                    namespacePrefix + ":DocumentDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "DocumentDTO", xmlWriter);
            }
        }

        if (localDocumentDate != null) {
            writeAttribute("http://www.fenealgestweb.it/", "DocumentDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDocumentDate), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localDocumentDate is null");
        }

        if (localDocumentType != null) {
            writeAttribute("http://www.fenealgestweb.it/", "DocumentType",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDocumentType), xmlWriter);
        }

        if (localStruttura != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Struttura",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localStruttura), xmlWriter);
        }

        if (localArea != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Area",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localArea), xmlWriter);
        }

        if (localProvince != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Province",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localProvince), xmlWriter);
        }

        if (localCity != null) {
            writeAttribute("http://www.fenealgestweb.it/", "City",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCity), xmlWriter);
        }

        if (localRegion != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Region",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localRegion), xmlWriter);
        }

        if (localState != null) {
            writeAttribute("http://www.fenealgestweb.it/", "State",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localState), xmlWriter);
        }

        if (localNotes != null) {
            writeAttribute("http://www.fenealgestweb.it/", "Notes",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNotes), xmlWriter);
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
        public static DocumentDTOE parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            DocumentDTOE object = new DocumentDTOE();

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

                        if (!"DocumentDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (DocumentDTOE) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "DocumentDate"
                java.lang.String tempAttribDocumentDate = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "DocumentDate");

                if (tempAttribDocumentDate != null) {
                    java.lang.String content = tempAttribDocumentDate;

                    object.setDocumentDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribDocumentDate));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute DocumentDate is missing");
                }

                handledAttributes.add("DocumentDate");

                // handle attribute "DocumentType"
                java.lang.String tempAttribDocumentType = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "DocumentType");

                if (tempAttribDocumentType != null) {
                    java.lang.String content = tempAttribDocumentType;

                    object.setDocumentType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribDocumentType));
                } else {
                }

                handledAttributes.add("DocumentType");

                // handle attribute "Struttura"
                java.lang.String tempAttribStruttura = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Struttura");

                if (tempAttribStruttura != null) {
                    java.lang.String content = tempAttribStruttura;

                    object.setStruttura(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribStruttura));
                } else {
                }

                handledAttributes.add("Struttura");

                // handle attribute "Area"
                java.lang.String tempAttribArea = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Area");

                if (tempAttribArea != null) {
                    java.lang.String content = tempAttribArea;

                    object.setArea(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribArea));
                } else {
                }

                handledAttributes.add("Area");

                // handle attribute "Province"
                java.lang.String tempAttribProvince = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Province");

                if (tempAttribProvince != null) {
                    java.lang.String content = tempAttribProvince;

                    object.setProvince(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribProvince));
                } else {
                }

                handledAttributes.add("Province");

                // handle attribute "City"
                java.lang.String tempAttribCity = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "City");

                if (tempAttribCity != null) {
                    java.lang.String content = tempAttribCity;

                    object.setCity(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribCity));
                } else {
                }

                handledAttributes.add("City");

                // handle attribute "Region"
                java.lang.String tempAttribRegion = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Region");

                if (tempAttribRegion != null) {
                    java.lang.String content = tempAttribRegion;

                    object.setRegion(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribRegion));
                } else {
                }

                handledAttributes.add("Region");

                // handle attribute "State"
                java.lang.String tempAttribState = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "State");

                if (tempAttribState != null) {
                    java.lang.String content = tempAttribState;

                    object.setState(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribState));
                } else {
                }

                handledAttributes.add("State");

                // handle attribute "Notes"
                java.lang.String tempAttribNotes = reader.getAttributeValue("http://www.fenealgestweb.it/",
                        "Notes");

                if (tempAttribNotes != null) {
                    java.lang.String content = tempAttribNotes;

                    object.setNotes(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNotes));
                } else {
                }

                handledAttributes.add("Notes");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
