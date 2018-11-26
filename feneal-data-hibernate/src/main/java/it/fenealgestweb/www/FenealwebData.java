/**
 * FenealwebData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  FenealwebData bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class FenealwebData implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = FenealwebData
       Namespace URI = www.fenealgestweb.it
       Namespace Prefix = ns1
     */

    /**
     * field for AuthomaticIntegrationFilename
     * This was an Attribute!
     */
    protected java.lang.String localAuthomaticIntegrationFilename;

    /**
     * field for NormalizedXmlFile
     * This was an Attribute!
     */
    protected java.lang.String localNormalizedXmlFile;

    /**
     * field for DocumentType
     * This was an Attribute!
     */
    protected java.lang.String localDocumentType;

    /**
     * field for Guid
     * This was an Attribute!
     */
    protected java.lang.String localGuid;

    /**
     * field for Entity
     * This was an Attribute!
     */
    protected java.lang.String localEntity;

    /**
     * field for UpdateFirmas
     * This was an Attribute!
     */
    protected boolean localUpdateFirmas;

    /**
     * field for UpdateWorkers
     * This was an Attribute!
     */
    protected boolean localUpdateWorkers;

    /**
     * field for CreateDelegaIfNotExist
     * This was an Attribute!
     */
    protected boolean localCreateDelegaIfNotExist;

    /**
     * field for CreateListaLavoro
     * This was an Attribute!
     */
    protected boolean localCreateListaLavoro;

    /**
     * field for AssociateDelega
     * This was an Attribute!
     */
    protected boolean localAssociateDelega;

    /**
     * field for Notes1
     * This was an Attribute!
     */
    protected java.lang.String localNotes1;

    /**
     * field for Notes2
     * This was an Attribute!
     */
    protected java.lang.String localNotes2;

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getAuthomaticIntegrationFilename() {
        return localAuthomaticIntegrationFilename;
    }

    /**
     * Auto generated setter method
     * @param param AuthomaticIntegrationFilename
     */
    public void setAuthomaticIntegrationFilename(java.lang.String param) {
        this.localAuthomaticIntegrationFilename = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNormalizedXmlFile() {
        return localNormalizedXmlFile;
    }

    /**
     * Auto generated setter method
     * @param param NormalizedXmlFile
     */
    public void setNormalizedXmlFile(java.lang.String param) {
        this.localNormalizedXmlFile = param;
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
    public java.lang.String getGuid() {
        return localGuid;
    }

    /**
     * Auto generated setter method
     * @param param Guid
     */
    public void setGuid(java.lang.String param) {
        this.localGuid = param;
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
     * @return boolean
     */
    public boolean getUpdateFirmas() {
        return localUpdateFirmas;
    }

    /**
     * Auto generated setter method
     * @param param UpdateFirmas
     */
    public void setUpdateFirmas(boolean param) {
        this.localUpdateFirmas = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getUpdateWorkers() {
        return localUpdateWorkers;
    }

    /**
     * Auto generated setter method
     * @param param UpdateWorkers
     */
    public void setUpdateWorkers(boolean param) {
        this.localUpdateWorkers = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getCreateDelegaIfNotExist() {
        return localCreateDelegaIfNotExist;
    }

    /**
     * Auto generated setter method
     * @param param CreateDelegaIfNotExist
     */
    public void setCreateDelegaIfNotExist(boolean param) {
        this.localCreateDelegaIfNotExist = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getCreateListaLavoro() {
        return localCreateListaLavoro;
    }

    /**
     * Auto generated setter method
     * @param param CreateListaLavoro
     */
    public void setCreateListaLavoro(boolean param) {
        this.localCreateListaLavoro = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getAssociateDelega() {
        return localAssociateDelega;
    }

    /**
     * Auto generated setter method
     * @param param AssociateDelega
     */
    public void setAssociateDelega(boolean param) {
        this.localAssociateDelega = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNotes1() {
        return localNotes1;
    }

    /**
     * Auto generated setter method
     * @param param Notes1
     */
    public void setNotes1(java.lang.String param) {
        this.localNotes1 = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNotes2() {
        return localNotes2;
    }

    /**
     * Auto generated setter method
     * @param param Notes2
     */
    public void setNotes2(java.lang.String param) {
        this.localNotes2 = param;
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
                    namespacePrefix + ":FenealwebData", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "FenealwebData", xmlWriter);
            }
        }

        if (localAuthomaticIntegrationFilename != null) {
            writeAttribute("www.fenealgestweb.it",
                "AuthomaticIntegrationFilename",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localAuthomaticIntegrationFilename), xmlWriter);
        }

        if (localNormalizedXmlFile != null) {
            writeAttribute("www.fenealgestweb.it", "NormalizedXmlFile",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNormalizedXmlFile), xmlWriter);
        }

        if (localDocumentType != null) {
            writeAttribute("www.fenealgestweb.it", "DocumentType",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDocumentType), xmlWriter);
        }

        if (localGuid != null) {
            writeAttribute("www.fenealgestweb.it", "Guid",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localGuid), xmlWriter);
        }

        if (localEntity != null) {
            writeAttribute("www.fenealgestweb.it", "Entity",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localEntity), xmlWriter);
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "UpdateFirmas",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localUpdateFirmas), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localUpdateFirmas is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "UpdateWorkers",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localUpdateWorkers), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localUpdateWorkers is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "CreateDelegaIfNotExist",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCreateDelegaIfNotExist), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localCreateDelegaIfNotExist is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "CreateListaLavoro",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCreateListaLavoro), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localCreateListaLavoro is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "AssociateDelega",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localAssociateDelega), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localAssociateDelega is null");
        }

        if (localNotes1 != null) {
            writeAttribute("www.fenealgestweb.it", "Notes1",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNotes1), xmlWriter);
        }

        if (localNotes2 != null) {
            writeAttribute("www.fenealgestweb.it", "Notes2",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNotes2), xmlWriter);
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
        public static FenealwebData parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            FenealwebData object = new FenealwebData();

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

                        if (!"FenealwebData".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (FenealwebData) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "AuthomaticIntegrationFilename"
                java.lang.String tempAttribAuthomaticIntegrationFilename = reader.getAttributeValue("www.fenealgestweb.it",
                        "AuthomaticIntegrationFilename");

                if (tempAttribAuthomaticIntegrationFilename != null) {
                    java.lang.String content = tempAttribAuthomaticIntegrationFilename;

                    object.setAuthomaticIntegrationFilename(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribAuthomaticIntegrationFilename));
                } else {
                }

                handledAttributes.add("AuthomaticIntegrationFilename");

                // handle attribute "NormalizedXmlFile"
                java.lang.String tempAttribNormalizedXmlFile = reader.getAttributeValue("www.fenealgestweb.it",
                        "NormalizedXmlFile");

                if (tempAttribNormalizedXmlFile != null) {
                    java.lang.String content = tempAttribNormalizedXmlFile;

                    object.setNormalizedXmlFile(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNormalizedXmlFile));
                } else {
                }

                handledAttributes.add("NormalizedXmlFile");

                // handle attribute "DocumentType"
                java.lang.String tempAttribDocumentType = reader.getAttributeValue("www.fenealgestweb.it",
                        "DocumentType");

                if (tempAttribDocumentType != null) {
                    java.lang.String content = tempAttribDocumentType;

                    object.setDocumentType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribDocumentType));
                } else {
                }

                handledAttributes.add("DocumentType");

                // handle attribute "Guid"
                java.lang.String tempAttribGuid = reader.getAttributeValue("www.fenealgestweb.it",
                        "Guid");

                if (tempAttribGuid != null) {
                    java.lang.String content = tempAttribGuid;

                    object.setGuid(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribGuid));
                } else {
                }

                handledAttributes.add("Guid");

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

                // handle attribute "UpdateFirmas"
                java.lang.String tempAttribUpdateFirmas = reader.getAttributeValue("www.fenealgestweb.it",
                        "UpdateFirmas");

                if (tempAttribUpdateFirmas != null) {
                    java.lang.String content = tempAttribUpdateFirmas;

                    object.setUpdateFirmas(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribUpdateFirmas));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute UpdateFirmas is missing");
                }

                handledAttributes.add("UpdateFirmas");

                // handle attribute "UpdateWorkers"
                java.lang.String tempAttribUpdateWorkers = reader.getAttributeValue("www.fenealgestweb.it",
                        "UpdateWorkers");

                if (tempAttribUpdateWorkers != null) {
                    java.lang.String content = tempAttribUpdateWorkers;

                    object.setUpdateWorkers(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribUpdateWorkers));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute UpdateWorkers is missing");
                }

                handledAttributes.add("UpdateWorkers");

                // handle attribute "CreateDelegaIfNotExist"
                java.lang.String tempAttribCreateDelegaIfNotExist = reader.getAttributeValue("www.fenealgestweb.it",
                        "CreateDelegaIfNotExist");

                if (tempAttribCreateDelegaIfNotExist != null) {
                    java.lang.String content = tempAttribCreateDelegaIfNotExist;

                    object.setCreateDelegaIfNotExist(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribCreateDelegaIfNotExist));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute CreateDelegaIfNotExist is missing");
                }

                handledAttributes.add("CreateDelegaIfNotExist");

                // handle attribute "CreateListaLavoro"
                java.lang.String tempAttribCreateListaLavoro = reader.getAttributeValue("www.fenealgestweb.it",
                        "CreateListaLavoro");

                if (tempAttribCreateListaLavoro != null) {
                    java.lang.String content = tempAttribCreateListaLavoro;

                    object.setCreateListaLavoro(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribCreateListaLavoro));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute CreateListaLavoro is missing");
                }

                handledAttributes.add("CreateListaLavoro");

                // handle attribute "AssociateDelega"
                java.lang.String tempAttribAssociateDelega = reader.getAttributeValue("www.fenealgestweb.it",
                        "AssociateDelega");

                if (tempAttribAssociateDelega != null) {
                    java.lang.String content = tempAttribAssociateDelega;

                    object.setAssociateDelega(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribAssociateDelega));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute AssociateDelega is missing");
                }

                handledAttributes.add("AssociateDelega");

                // handle attribute "Notes1"
                java.lang.String tempAttribNotes1 = reader.getAttributeValue("www.fenealgestweb.it",
                        "Notes1");

                if (tempAttribNotes1 != null) {
                    java.lang.String content = tempAttribNotes1;

                    object.setNotes1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNotes1));
                } else {
                }

                handledAttributes.add("Notes1");

                // handle attribute "Notes2"
                java.lang.String tempAttribNotes2 = reader.getAttributeValue("www.fenealgestweb.it",
                        "Notes2");

                if (tempAttribNotes2 != null) {
                    java.lang.String content = tempAttribNotes2;

                    object.setNotes2(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNotes2));
                } else {
                }

                handledAttributes.add("Notes2");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
