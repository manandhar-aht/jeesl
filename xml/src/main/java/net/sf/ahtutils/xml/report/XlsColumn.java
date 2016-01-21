
package net.sf.ahtutils.xml.report;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}langs"/&gt;
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}descriptions"/&gt;
 *         &lt;element ref="{http://ahtutils.aht-group.com/report}xlsTransformation"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="column" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="example" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="autoWidth" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "langs",
    "descriptions",
    "xlsTransformation"
})
@XmlRootElement(name = "xlsColumn")
public class XlsColumn
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Langs langs;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Descriptions descriptions;
    @XmlElement(required = true)
    protected XlsTransformation xlsTransformation;
    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "required")
    protected Boolean required;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "example")
    protected String example;
    @XmlAttribute(name = "autoWidth")
    protected Boolean autoWidth;
    @XmlAttribute(name = "width")
    protected Integer width;

    /**
     * Gets the value of the langs property.
     * 
     * @return
     *     possible object is
     *     {@link Langs }
     *     
     */
    public Langs getLangs() {
        return langs;
    }

    /**
     * Sets the value of the langs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Langs }
     *     
     */
    public void setLangs(Langs value) {
        this.langs = value;
    }

    public boolean isSetLangs() {
        return (this.langs!= null);
    }

    /**
     * Gets the value of the descriptions property.
     * 
     * @return
     *     possible object is
     *     {@link Descriptions }
     *     
     */
    public Descriptions getDescriptions() {
        return descriptions;
    }

    /**
     * Sets the value of the descriptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Descriptions }
     *     
     */
    public void setDescriptions(Descriptions value) {
        this.descriptions = value;
    }

    public boolean isSetDescriptions() {
        return (this.descriptions!= null);
    }

    /**
     * Gets the value of the xlsTransformation property.
     * 
     * @return
     *     possible object is
     *     {@link XlsTransformation }
     *     
     */
    public XlsTransformation getXlsTransformation() {
        return xlsTransformation;
    }

    /**
     * Sets the value of the xlsTransformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link XlsTransformation }
     *     
     */
    public void setXlsTransformation(XlsTransformation value) {
        this.xlsTransformation = value;
    }

    public boolean isSetXlsTransformation() {
        return (this.xlsTransformation!= null);
    }

    /**
     * Gets the value of the column property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the value of the column property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn(String value) {
        this.column = value;
    }

    public boolean isSetColumn() {
        return (this.column!= null);
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(boolean value) {
        this.required = value;
    }

    public boolean isSetRequired() {
        return (this.required!= null);
    }

    public void unsetRequired() {
        this.required = null;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    public boolean isSetLabel() {
        return (this.label!= null);
    }

    /**
     * Gets the value of the example property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExample() {
        return example;
    }

    /**
     * Sets the value of the example property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExample(String value) {
        this.example = value;
    }

    public boolean isSetExample() {
        return (this.example!= null);
    }

    /**
     * Gets the value of the autoWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAutoWidth() {
        return autoWidth;
    }

    /**
     * Sets the value of the autoWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoWidth(boolean value) {
        this.autoWidth = value;
    }

    public boolean isSetAutoWidth() {
        return (this.autoWidth!= null);
    }

    public void unsetAutoWidth() {
        this.autoWidth = null;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(int value) {
        this.width = value;
    }

    public boolean isSetWidth() {
        return (this.width!= null);
    }

    public void unsetWidth() {
        this.width = null;
    }

}
