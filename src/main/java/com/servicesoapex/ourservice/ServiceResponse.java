//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.05 at 02:43:37 PM CET 
//


package com.servicesoapex.ourservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Myresult" type="{http://www.servicesoapex.com/ourservice}Myresult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "myresult"
})
@XmlRootElement(name = "ServiceResponse")
public class ServiceResponse {

    @XmlElement(name = "Myresult", required = true)
    protected Myresult myresult;

    /**
     * Gets the value of the myresult property.
     * 
     * @return
     *     possible object is
     *     {@link Myresult }
     *     
     */
    public Myresult getMyresult() {
        return myresult;
    }

    /**
     * Sets the value of the myresult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Myresult }
     *     
     */
    public void setMyresult(Myresult value) {
        this.myresult = value;
    }

}