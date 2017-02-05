import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail extends Thread {
	public static boolean sendmail_vdo=false;
	public static boolean sendmail_notif=false;
	public void run(){
	while(true)
	{
		try {
			Thread.sleep(0, 10000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//System.out.println("@@@@@@@@@@@@@@@@@@....mail wala thread...@@@@@@@@@");
	if(sendmail_vdo && sendmail_notif )
	{
		  System.out.println("######################Sending start zala#################");
		  sendmail_vdo = false;
		  sendmail_notif = false;
		  // Recipient's email ID needs to be mentioned.
	      String to = "sibhalihastey@gmail.com";
	      // Sender's email ID needs to be mentioned
	      String from = "missblahboo@gmail.com";
	      
	      final String username = "missblahboo@gmail.com";//change accordingly
	      final String password = "blahblahbooboo";//change accordingly
	      String host = "74.125.206.108";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");	      
	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	            }
	         });

		try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject("Magic Eye Video");

	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("Hello!" + '\n' + "This is a video recorded by your Magic Eye System on " + Main.store_file_name + "." + '\n' + "Please take a look.");

	         // Create a multipart message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = Main.store_name;
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(Main.store_file_name +".mp4");
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);
	         System.out.println("reached jst b4 sending");

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("Sent message successfully....");
	         
	      } catch (MessagingException e) {
	    	 System.out.println("Sending failed!!!");
	         throw new RuntimeException(e);
	      }
	}
	}
	}

}
