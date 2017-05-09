package de.sitescrawler.email;

import java.util.ArrayList;
import java.util.List;

import de.sitescrawler.email.interfaces.IMailSenderService;

public class Main {

	public static void main(String[] args) {
		// Zu Testzwecken
		
		String emailAdresse = "sitescrawler@spoofmail.de";
		String subjekt = "Testtitel";
		String body = "testbody";
		boolean htmlBody = false;
		
		 
		byte[] data = new byte[1460];
		 
		
		IMailSenderService mail = new MailSenderService();
		try {
			mail.sendeMail(emailAdresse,subjekt,body,htmlBody,data);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}

}
