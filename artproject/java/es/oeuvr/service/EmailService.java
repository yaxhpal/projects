package es.oeuvr.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
	
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.debug", "false");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props, new MyAuthenticator());
		session.setDebug(true);
		Transport transport = session.getTransport();
		InternetAddress addressFrom = new InternetAddress("yaxhpal@gmail.com");
		MimeMessage message = new MimeMessage(session);
		message.setSender(addressFrom);

		for (int j = 0; j < 3; j++) {
			message.setSubject("Testing javamail plain" + Math.random());
			message.setContent("This is a test", "text/plain");
			String sendTo[] = { "yashpal@techletsolutions.com" };
			if (sendTo != null) {
				InternetAddress[] addressTo = new InternetAddress[sendTo.length];
				for (int i = 0; i < sendTo.length; i++) {
					addressTo[i] = new InternetAddress(sendTo[i]);
				}
				message.setRecipients(Message.RecipientType.TO, addressTo);

			}
			transport.connect();
			Transport.send(message);
			transport.close();
		}
	}
}

class MyAuthenticator extends Authenticator {
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("yaxhpal@gmail.com", "Palyash#21");
	}
}