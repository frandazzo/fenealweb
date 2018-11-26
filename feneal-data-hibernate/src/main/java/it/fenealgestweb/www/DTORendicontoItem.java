/**
 * DTORendicontoItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.1  Built on : Feb 20, 2016 (10:02:19 GMT)
 */
package it.fenealgestweb.www;


/**
 *  DTORendicontoItem bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class DTORendicontoItem implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = DTORendicontoItem
       Namespace URI = www.fenealgestweb.it
       Namespace Prefix = ns1
     */

    /**
     * field for IdNodo
     * This was an Attribute!
     */
    protected java.lang.String localIdNodo;

    /**
     * field for IdNodoPadre
     * This was an Attribute!
     */
    protected java.lang.String localIdNodoPadre;

    /**
     * field for Livello
     * This was an Attribute!
     */
    protected int localLivello;

    /**
     * field for DescrizioneNodo
     * This was an Attribute!
     */
    protected java.lang.String localDescrizioneNodo;

    /**
     * field for ImportoNodoBilancio
     * This was an Attribute!
     */
    protected double localImportoNodoBilancio;

    /**
     * field for ImportoNodoPreventivo
     * This was an Attribute!
     */
    protected double localImportoNodoPreventivo;

    /**
     * field for IsLeaf
     * This was an Attribute!
     */
    protected boolean localIsLeaf;

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getIdNodo() {
        return localIdNodo;
    }

    /**
     * Auto generated setter method
     * @param param IdNodo
     */
    public void setIdNodo(java.lang.String param) {
        this.localIdNodo = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getIdNodoPadre() {
        return localIdNodoPadre;
    }

    /**
     * Auto generated setter method
     * @param param IdNodoPadre
     */
    public void setIdNodoPadre(java.lang.String param) {
        this.localIdNodoPadre = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getLivello() {
        return localLivello;
    }

    /**
     * Auto generated setter method
     * @param param Livello
     */
    public void setLivello(int param) {
        this.localLivello = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getDescrizioneNodo() {
        return localDescrizioneNodo;
    }

    /**
     * Auto generated setter method
     * @param param DescrizioneNodo
     */
    public void setDescrizioneNodo(java.lang.String param) {
        this.localDescrizioneNodo = param;
    }

    /**
     * Auto generated getter method
     * @return double
     */
    public double getImportoNodoBilancio() {
        return localImportoNodoBilancio;
    }

    /**
     * Auto generated setter method
     * @param param ImportoNodoBilancio
     */
    public void setImportoNodoBilancio(double param) {
        this.localImportoNodoBilancio = param;
    }

    /**
     * Auto generated getter method
     * @return double
     */
    public double getImportoNodoPreventivo() {
        return localImportoNodoPreventivo;
    }

    /**
     * Auto generated setter method
     * @param param ImportoNodoPreventivo
     */
    public void setImportoNodoPreventivo(double param) {
        this.localImportoNodoPreventivo = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIsLeaf() {
        return localIsLeaf;
    }

    /**
     * Auto generated setter method
     * @param param IsLeaf
     */
    public void setIsLeaf(boolean param) {
        this.localIsLeaf = param;
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
                    namespacePrefix + ":DTORendicontoItem", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "DTORendicontoItem", xmlWriter);
            }
        }

        if (localIdNodo != null) {
            writeAttribute("www.fenealgestweb.it", "IdNodo",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIdNodo), xmlWriter);
        }

        if (localIdNodoPadre != null) {
            writeAttribute("www.fenealgestweb.it", "IdNodoPadre",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIdNodoPadre), xmlWriter);
        }

        if (localLivello != java.lang.Integer.MIN_VALUE) {
            writeAttribute("www.fenealgestweb.it", "Livello",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLivello), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localLivello is null");
        }

        if (localDescrizioneNodo != null) {
            writeAttribute("www.fenealgestweb.it", "DescrizioneNodo",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDescrizioneNodo), xmlWriter);
        }

        if (!java.lang.Double.isNaN(localImportoNodoBilancio)) {
            writeAttribute("www.fenealgestweb.it", "ImportoNodoBilancio",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localImportoNodoBilancio), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localImportoNodoBilancio is null");
        }

        if (!java.lang.Double.isNaN(localImportoNodoPreventivo)) {
            writeAttribute("www.fenealgestweb.it", "ImportoNodoPreventivo",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localImportoNodoPreventivo), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localImportoNodoPreventivo is null");
        }

        if (true) {
            writeAttribute("www.fenealgestweb.it", "IsLeaf",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsLeaf), xmlWriter);
        }
        else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localIsLeaf is null");
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
        public static DTORendicontoItem parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            DTORendicontoItem object = new DTORendicontoItem();

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

                        if (!"DTORendicontoItem".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (DTORendicontoItem) it.fenealgestweb.www.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "IdNodo"
                java.lang.String tempAttribIdNodo = reader.getAttributeValue("www.fenealgestweb.it",
                        "IdNodo");

                if (tempAttribIdNodo != null) {
                    java.lang.String content = tempAttribIdNodo;

                    object.setIdNodo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribIdNodo));
                } else {
                }

                handledAttributes.add("IdNodo");

                // handle attribute "IdNodoPadre"
                java.lang.String tempAttribIdNodoPadre = reader.getAttributeValue("www.fenealgestweb.it",
                        "IdNodoPadre");

                if (tempAttribIdNodoPadre != null) {
                    java.lang.String content = tempAttribIdNodoPadre;

                    object.setIdNodoPadre(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribIdNodoPadre));
                } else {
                }

                handledAttributes.add("IdNodoPadre");

                // handle attribute "Livello"
                java.lang.String tempAttribLivello = reader.getAttributeValue("www.fenealgestweb.it",
                        "Livello");

                if (tempAttribLivello != null) {
                    java.lang.String content = tempAttribLivello;

                    object.setLivello(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            tempAttribLivello));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute Livello is missing");
                }

                handledAttributes.add("Livello");

                // handle attribute "DescrizioneNodo"
                java.lang.String tempAttribDescrizioneNodo = reader.getAttributeValue("www.fenealgestweb.it",
                        "DescrizioneNodo");

                if (tempAttribDescrizioneNodo != null) {
                    java.lang.String content = tempAttribDescrizioneNodo;

                    object.setDescrizioneNodo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribDescrizioneNodo));
                } else {
                }

                handledAttributes.add("DescrizioneNodo");

                // handle attribute "ImportoNodoBilancio"
                java.lang.String tempAttribImportoNodoBilancio = reader.getAttributeValue("www.fenealgestweb.it",
                        "ImportoNodoBilancio");

                if (tempAttribImportoNodoBilancio != null) {
                    java.lang.String content = tempAttribImportoNodoBilancio;

                    object.setImportoNodoBilancio(org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(
                            tempAttribImportoNodoBilancio));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ImportoNodoBilancio is missing");
                }

                handledAttributes.add("ImportoNodoBilancio");

                // handle attribute "ImportoNodoPreventivo"
                java.lang.String tempAttribImportoNodoPreventivo = reader.getAttributeValue("www.fenealgestweb.it",
                        "ImportoNodoPreventivo");

                if (tempAttribImportoNodoPreventivo != null) {
                    java.lang.String content = tempAttribImportoNodoPreventivo;

                    object.setImportoNodoPreventivo(org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(
                            tempAttribImportoNodoPreventivo));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute ImportoNodoPreventivo is missing");
                }

                handledAttributes.add("ImportoNodoPreventivo");

                // handle attribute "IsLeaf"
                java.lang.String tempAttribIsLeaf = reader.getAttributeValue("www.fenealgestweb.it",
                        "IsLeaf");

                if (tempAttribIsLeaf != null) {
                    java.lang.String content = tempAttribIsLeaf;

                    object.setIsLeaf(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsLeaf));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute IsLeaf is missing");
                }

                handledAttributes.add("IsLeaf");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
