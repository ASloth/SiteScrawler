package de.sitescrawler.email.interfaces;

import java.util.List;
 
import de.sitescrawler.email.ServiceUnavailableException; 
import de.sitescrawler.jpa.Nutzer; 

/**
 * Verwaltet das Senden von Emails.
 * @author konrad mueller
 */
public interface IMailSenderService {

	/**
	 * Sendet eine Email an die angegebene Email Adresse.
	 * @param emailAdresse Adresse des Empfängers.
	 * @param subjekt Email Betreff.
	 * @param body Email Inhalt.
	 * @param htmlBody Soll der Email Inhalt als HTML gesendet werden.
	 * @param anhaenge Eine Liste von Anhängen.
	 * @throws ServiceUnavailableException 
	 */
	void sendeMail(String emailAdresse, String subjekt, String body, boolean htmlBody,  byte [] anhang) throws ServiceUnavailableException;
	
	/**
	 * Sendet eine Email an eine Liste von Empfängern.
	 * @param empfaenger E-Mail Adressen der Empfänger.
	 * @param subjekt Email Betreff.
	 * @param body Email Inhalt.
	 * @param htmlBody Soll der Email Inhalt als HTML gesendet werden.
	 * @param pdf Anzuhängendes PDF. 
	 * @throws ServiceUnavailableException 
	 */
	void sendeMail(List<String> empfaenger, String subjekt, String body, boolean htmlBody, byte [] anhang) throws ServiceUnavailableException;
} 
