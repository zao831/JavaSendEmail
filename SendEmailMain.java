import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple Java program to send a mail message using the smtp protocol with a
 * given text template There is no visual interface. Running on the command
 * line. For work it is necessary to connect JavaMail API 1.4.5. Available by
 * reference
 * http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.5-oth-JPR
 * or a newer version
 * 
 * @author Anton Baker
 * @date Friday, 2018-07-20
 */

public class SendEmailMain {

	public static void main(String[] args) throws IOException, AddressException, MessagingException {

		// Create an instance of a class LetterTemplate to access variables
		LetterTemplate letterTemplate = new LetterTemplate();

		// Create class variables
		String userFrom = "your email";
		String userPass = "your email password";
		String userTo = "e-mail address of the recipient";
		String host = "smtp server settings";
		int port = 0; // port number

		// Create a variable for storing the configuration and set the parameters
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", userFrom);
		properties.put("mail.smtp.password", userPass);
		properties.put("mail.smtp.ssl.trust", host);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.quitwait", "false");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// properties.put("mail.debug", "true");

		// Create a session variable for incoming mail servers
		Session session = Session.getDefaultInstance(properties);

		// Create email
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userFrom));

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(userTo));
		message.setSubject(letterTemplate.subject);
		message.setText(letterTemplate.text + "\n\n" + letterTemplate.fullName + "\n" + letterTemplate.telNumber);

		System.out.println("Started to send the email...");

		Transport transport = session.getTransport();
		transport.connect(host, userFrom, userPass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Email sent!");

	}
}