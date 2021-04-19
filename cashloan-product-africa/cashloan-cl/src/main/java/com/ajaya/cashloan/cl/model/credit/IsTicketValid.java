
package com.ajaya.cashloan.cl.model.credit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FirstCentralNigeriaWebServiceTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "firstCentralNigeriaWebServiceTicket"
})
@XmlRootElement(name = "IsTicketValid")
public class IsTicketValid {

    @XmlElement(name = "FirstCentralNigeriaWebServiceTicket")
    protected String firstCentralNigeriaWebServiceTicket;

    /**
     * ��ȡfirstCentralNigeriaWebServiceTicket���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstCentralNigeriaWebServiceTicket() {
        return firstCentralNigeriaWebServiceTicket;
    }

    /**
     * ����firstCentralNigeriaWebServiceTicket���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstCentralNigeriaWebServiceTicket(String value) {
        this.firstCentralNigeriaWebServiceTicket = value;
    }

}
