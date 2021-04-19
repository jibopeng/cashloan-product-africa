
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
 *         &lt;element name="ConnectConsumerMatchResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "connectConsumerMatchResult"
})
@XmlRootElement(name = "ConnectConsumerMatchResponse")
public class ConnectConsumerMatchResponse {

    @XmlElement(name = "ConnectConsumerMatchResult")
    protected String connectConsumerMatchResult;

    /**
     * ��ȡconnectConsumerMatchResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectConsumerMatchResult() {
        return connectConsumerMatchResult;
    }

    /**
     * ����connectConsumerMatchResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectConsumerMatchResult(String value) {
        this.connectConsumerMatchResult = value;
    }

}
