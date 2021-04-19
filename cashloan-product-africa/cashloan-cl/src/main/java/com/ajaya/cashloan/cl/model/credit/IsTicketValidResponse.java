
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
 *         &lt;element name="IsTicketValidResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isTicketValidResult"
})
@XmlRootElement(name = "IsTicketValidResponse")
public class IsTicketValidResponse {

    @XmlElement(name = "IsTicketValidResult")
    protected boolean isTicketValidResult;

    /**
     * ��ȡisTicketValidResult���Ե�ֵ��
     * 
     */
    public boolean isIsTicketValidResult() {
        return isTicketValidResult;
    }

    /**
     * ����isTicketValidResult���Ե�ֵ��
     * 
     */
    public void setIsTicketValidResult(boolean value) {
        this.isTicketValidResult = value;
    }

}
