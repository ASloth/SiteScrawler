package de.sitescrawler.email;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.*;

import de.sitescrawler.email.interfaces.IMailSenderService;
import de.sitescrawler.jpa.Nutzer;
import de.sitescrawler.model.ProjectConfig;

@ApplicationScoped
public class MailSenderService implements IMailSenderService {

	private Properties props = System.getProperties();
	private String username;
	private String password;
	private String host;

	@Inject
	private ProjectConfig projectConfig;

	@PostConstruct
	void init() {
		username = projectConfig.getUsername();
		password = projectConfig.getPassword();
		host = "smtp.outlook.com";

		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
	}

	private final static Logger LOGGER = Logger.getLogger("de.sitescrawler.logger");

	public void sendeMail(String emailAdresse, String subjekt, String body, boolean htmlBody, byte[] anhaenge)
			throws ServiceUnavailableException { 
		manuellerInject();
		List<String> empfaenger = new ArrayList<>();
		empfaenger.add(emailAdresse);
		
		erstelleUndVerschickeNachricht(empfaenger, subjekt, body, htmlBody, anhaenge);
	}

	public void sendeMail(List<String> empfaenger, String subjekt, String body, boolean htmlBody, byte[] anhaenge)
			throws ServiceUnavailableException {
		manuellerInject();
		erstelleUndVerschickeNachricht(empfaenger, subjekt, body, htmlBody, anhaenge);
	} 
	
	private void manuellerInject(){
		if(projectConfig == null)
		{
			projectConfig = new ProjectConfig();
			username = projectConfig.getUsername();
			password = projectConfig.getPassword();
			host = "smtp.outlook.com";

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", username);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
		} 
	}
	
	private void erstelleUndVerschickeNachricht(List<String> emailAdresse, String subjekt, String body, boolean htmlBody, byte[] anhaenge){
		Session session = Session.getInstance(props);
		List<MimeMessage> nachrichten = new ArrayList<>();
		for(String empfaenger: emailAdresse)
		{
			try {
				nachrichten.add(erstelleNachricht(session, empfaenger, subjekt, body, htmlBody, anhaenge));
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sendeNachricht(nachrichten, session);
	}
	
	private MimeMessage erstelleNachricht(Session session, String emailAdresse, String subjekt, String body, boolean htmlBody, byte[] anhaenge) throws AddressException, MessagingException{
	
		MimeMessage nachricht = new MimeMessage(session); 
		nachricht.setFrom(new InternetAddress(username));
		nachricht.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAdresse));
		nachricht.setSubject(subjekt);

		// Teil eins ist die Nachricht
		MimeBodyPart nachrichtTeil = new MimeBodyPart();
		if (htmlBody)
			nachrichtTeil.setContent(body, "text/html");
		else
			nachrichtTeil.setText(body);

		// Erstelle eine multipar nachricht
		Multipart multipart = new MimeMultipart();
		// Setze text Nachricht Teil
		multipart.addBodyPart(nachrichtTeil);

		// Teil zwei ist Anhang 
		MimeBodyPart anhang = new MimeBodyPart();
		DataSource bds = new ByteArrayDataSource(anhaenge, "application/pdf");
		anhang.setDataHandler(new DataHandler(bds));
		anhang.setFileName("Pressespiegel Nr."); 
		multipart.addBodyPart(anhang);
		 

		// Zusammenführen der Teile
		nachricht.setContent(multipart);  
		
		return nachricht;
	}

	private void sendeNachricht(List<MimeMessage> nachrichten, Session session) {
		
		try {
			
			Transport transport = session.getTransport("smtp");

			transport.connect(host, username, password);

			MailSenderService.LOGGER.log(Level.INFO, "Verbindung zu Mailserver geöffnet.");

			for (MimeMessage nachricht : nachrichten) {
				Address[] adressen = nachricht.getAllRecipients();
				transport.sendMessage(nachricht, adressen);

				String alleAdressen = "";
				for (Address s : adressen) {
					alleAdressen += s + "; ";
				}
				MailSenderService.LOGGER.log(Level.INFO, "Email versendet an: " + alleAdressen);
			}

			transport.close();
			MailSenderService.LOGGER.log(Level.INFO, "Verbindung zu Mailserver geschlossen.");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}