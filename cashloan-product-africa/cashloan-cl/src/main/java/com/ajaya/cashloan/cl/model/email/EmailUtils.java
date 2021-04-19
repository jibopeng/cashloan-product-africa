package com.ajaya.cashloan.cl.model.email;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

/**
 * 
 * <pre>
 * <h1>Debugging</h1>
 * The JavaMail API supports a debugging option that will can be very
 * useful if you run into problems. You can activate debugging on any of the
 * mail classes by calling setDebug(true). The debugging output will be written
 * to System.out.
 * 
 * Sometimes you want to experiment with various security setting or features of
 * commons-email. A good starting point is the test class EmailLiveTest and
 * EmailConfiguration which are used for testing commons-email with real SMTP
 * servers.
 * 
 * </pre>
 * 
 * @describe Apache Commons Email 使用示例
 * @author liwen
 * 
 */
public class EmailUtils {
    /**
     * <pre>
     * 
     * Our first example will create a basic email message to "John Doe" and
     * send it through your Google Mail (GMail) account.
     * 
     * The call to setHostName("mail.myserver.com") sets the address of the outgoing SMTP
     * server that will be used to send the message. If this is not set, the
     * system property "mail.host" will be used.
     * 
     * </pre>
     * 
     * @describe 发送内容为简单文本的邮件
     * @throws EmailException
     */
    public static void sendSimpleTextEmail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        // 用户名和密码为邮箱的账号和密码（不需要进行base64编码）
        email.setAuthenticator(new DefaultAuthenticator("username", "password"));
        email.setSSLOnConnect(true);
        email.setFrom("user@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("foo@bar.com");
        email.send();
    }

    /**
     * <pre>
     * 
     * To add attachments to an email, you will need to use the MultiPartEmail
     * class. This class works just like SimpleEmail except that it adds several
     * overloaded attach() methods to add attachments to the email. You can add
     * an unlimited number of attachments either inline or attached. The
     * attachments will be MIME encoded.
     * 
     * The simplest way to add the attachments is by using the EmailAttachment
     * class to reference your attachments.
     * 
     * In the following example, we will create an attachment for a picture. We
     * will then attach the picture to the email and send it.
     * 
     * </pre>
     * 
     * @describe 发送包含附件的邮件（附件为本地资源）
     * @throws EmailException
     */
    public static void sendEmailsWithAttachments() throws EmailException {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("mypictures/john.jpg");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Picture of John");
        attachment.setName("John");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("The picture");
        email.setMsg("Here is the picture you wanted");

        // add the attachment
        email.attach(attachment);

        // send the email
        email.send();
    }

    /**
     * <pre>
     * 
     * You can also use EmailAttachment to reference any valid URL for files
     * that you do not have locally. When the message is sent, the file will be
     * downloaded and attached to the message automatically.
     * 
     * The next example shows how we could have sent the apache logo to John
     * instead.
     * 
     * </pre>
     * 
     * @describe 发送包含附件的邮件（附件为在线资源）
     * @throws EmailException
     * @throws MalformedURLException
     */
    public static void sendEmailsWithOnlineAttachments() throws EmailException, MalformedURLException {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Apache logo");
        attachment.setName("Apache logo");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("The logo");
        email.setMsg("Here is Apache's logo");

        // add the attachment
        email.attach(attachment);

        // send the email
        email.send();
    }

    /**
     * <pre>
     * 
     * Sending HTML formatted email is accomplished by using the HtmlEmail
     * class. This class works exactly like the MultiPartEmail class with
     * additional methods to set the html content, alternative text content if
     * the recipient does not support HTML email, and add inline images.
     * 
     * In this example, we will send an email message with formatted HTML
     * content with an inline image.
     * 
     * First, notice that the call to embed() returns a String. This String is a
     * randomly generated identifier that must be used to reference the image in
     * the image tag.
     * 
     * Next, there was no call to setMsg() in this example. The method is still
     * available in HtmlEmail but it should not be used if you will be using
     * inline images. Instead, the setHtmlMsg() and setTextMsg() methods were
     * used.
     * 
     * <pre>
     * 
     * @describe 发送内容为HTML格式的邮件
     * @throws EmailException
     * @throws MalformedURLException
     */
    public static void sendHTMLFormattedEmail() throws EmailException, MalformedURLException {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("Test email with inline image");

        // embed the image and get the content id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");

        // set the html message
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }

    /**
     * <pre>
     * 
     * The previous example showed how to create a HTML email with embedded
     * images but you need to know all images upfront which is inconvenient when
     * using a HTML email template. The ImageHtmlEmail helps you solving this
     * problem by converting all external images to inline images.
     * 
     * First we create a HTML email template referencing some images. All
     * referenced images are automatically transformed to inline images by the
     * specified DataSourceResolver.
     * 
     * </pre>
     * 
     * @describe 发送内容为HTML格式的邮件（嵌入图片更方便）
     * @throws MalformedURLException
     * @throws EmailException
     */
    public static void sendHTMLFormattedEmailWithEmbeddedImages() throws MalformedURLException, EmailException {
        // load your HTML email template
        String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";

        // define you base URL to resolve relative resource locations
        URL url = new URL("http://www.apache.org");

        // create the email message
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setDataSourceResolver(new DataSourceUrlResolver(url));
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("Test email with inline image");

        // set the html message
        email.setHtmlMsg(htmlEmailTemplate);

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }

    public static void main(String[] args) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.exmail.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("liuxiaoliang@duandai.com", "Lxl552211"));
        email.setSSLOnConnect(true);
        email.setFrom("liuxiaoliang@duandai.com", "利文");
        email.setSubject("异常信息");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("1361149307@qq.com");
        email.send();
    }
    
    
    public void aaa(){
    	// Create the attachment
        try {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath("C:\\dayData.xls");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Picture of John");
			attachment.setName("陆雨.xls");
			// Create the email message
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("smtp.exmail.qq.com");
			email.setAuthentication("liuxiaoliang@duandai.com","Lxl552211");
			email.addTo("luyu@duandai.com", "John Doe");
			email.setFrom("liuxiaoliang@duandai.com", "Me");
			email.setSubject("The picture");
			email.setMsg("Here is the picture you wanted");
			// add the attachment
			email.attach(attachment);
			
			// send the email
			email.send();
		} catch (EmailException e) {

			e.printStackTrace();
		}
    }
    
    public static void sendEamilByFujian(String filePathName, String fileName, String toName){
    	// Create the attachment
        try {
        	System.out.println("向 " + toName + " 发送 " + filePathName);
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(filePathName);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("数据");
			attachment.setName(fileName);
			// Create the email message
			MultiPartEmail email = new MultiPartEmail();
			email.setSSLOnConnect(true);
			email.setSmtpPort(465);
			email.setHostName("smtp.exmail.qq.com");
			email.setAuthentication("luyu@duandai.com","Luyu211535!");//Luyu211535!
			email.addTo(toName, "John Doe");
			email.setFrom("luyu@duandai.com", "Me");
			email.setSubject("The file");
			email.setMsg("短贷王数据");
			// add the attachment
			email.attach(attachment);
			
			// send the email
			email.send();
		} catch (EmailException e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    }
    
    public void dddd(){
    	sendEamilByFujian("E://timingEmailOverdue.xls", "data.xls", "liuxiaoliang@duandai.com");
    }
}