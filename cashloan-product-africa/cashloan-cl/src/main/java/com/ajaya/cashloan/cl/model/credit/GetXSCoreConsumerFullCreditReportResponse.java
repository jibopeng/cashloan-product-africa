
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
 *         &lt;element name="GetXSCoreConsumerFullCreditReportResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getXSCoreConsumerFullCreditReportResult"
})
@XmlRootElement(name = "GetXSCoreConsumerFullCreditReportResponse")
public class GetXSCoreConsumerFullCreditReportResponse {

    @XmlElement(name = "GetXSCoreConsumerFullCreditReportResult")
    protected String getXSCoreConsumerFullCreditReportResult;

    /**
     * ��ȡgetXSCoreConsumerFullCreditReportResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetXSCoreConsumerFullCreditReportResult() {
        return getXSCoreConsumerFullCreditReportResult;
    }

    /**
     * ����getXSCoreConsumerFullCreditReportResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetXSCoreConsumerFullCreditReportResult(String value) {
        this.getXSCoreConsumerFullCreditReportResult = value;
    }

}
