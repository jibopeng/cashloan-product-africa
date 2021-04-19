
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
 *         &lt;element name="DataTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsumerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="consumerMergeList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberEnquiryEngineID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enquiryID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "dataTicket",
    "consumerID",
    "consumerMergeList",
    "subscriberEnquiryEngineID",
    "enquiryID"
})
@XmlRootElement(name = "GetXSCoreConsumerFullCreditReport")
public class GetXSCoreConsumerFullCreditReport {

    @XmlElement(name = "DataTicket")
    protected String dataTicket;
    @XmlElement(name = "ConsumerID")
    protected int consumerID;
    protected String consumerMergeList;
    @XmlElement(name = "SubscriberEnquiryEngineID")
    protected String subscriberEnquiryEngineID;
    protected int enquiryID;

    /**
     * ��ȡdataTicket���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataTicket() {
        return dataTicket;
    }

    /**
     * ����dataTicket���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataTicket(String value) {
        this.dataTicket = value;
    }

    /**
     * ��ȡconsumerID���Ե�ֵ��
     * 
     */
    public int getConsumerID() {
        return consumerID;
    }

    /**
     * ����consumerID���Ե�ֵ��
     * 
     */
    public void setConsumerID(int value) {
        this.consumerID = value;
    }

    /**
     * ��ȡconsumerMergeList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumerMergeList() {
        return consumerMergeList;
    }

    /**
     * ����consumerMergeList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumerMergeList(String value) {
        this.consumerMergeList = value;
    }

    /**
     * ��ȡsubscriberEnquiryEngineID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriberEnquiryEngineID() {
        return subscriberEnquiryEngineID;
    }

    /**
     * ����subscriberEnquiryEngineID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriberEnquiryEngineID(String value) {
        this.subscriberEnquiryEngineID = value;
    }

    /**
     * ��ȡenquiryID���Ե�ֵ��
     * 
     */
    public int getEnquiryID() {
        return enquiryID;
    }

    /**
     * ����enquiryID���Ե�ֵ��
     * 
     */
    public void setEnquiryID(int value) {
        this.enquiryID = value;
    }

}
