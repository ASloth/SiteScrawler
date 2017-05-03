package de.sitescrawler.email.interfaces;

import java.util.List;

import de.sitescrawler.email.ServiceUnavailableException;

/**
 * Verwaltet das Senden von Emails.
 * @author konrad mueller
 */
public interface IMailSenderService {

	/**
	 * Sendet eine Email an die angegebene Email Adresse.
	 * @param emailAdresse Adresse des Empf�ngers.
	 * @param subjekt Email Betreff.
	 * @param body Email Inhalt.
	 * @param htmlBody Soll der Email Inhalt als HTML gesendet werden.
	 * @param anhaenge Eine Liste von Anh�ngen.
	 * @throws ServiceUnavailableException 
	 */
	void sendeMail(String emailAdresse, String subjekt, String body, boolean htmlBody,  List<byte []> anhaenge) throws ServiceUnavailableException;
	
	/**
	 * Sendet eine Email an eine Liste von Empf�ngern.
	 * @param emailAdressen E-Mail Adressen der Empf�nger.
	 * @param subjekt Email Betreff.
	 * @param body Email Inhalt.
	 * @param htmlBody Soll der Email Inhalt als HTML gesendet werden.
	 * @param anhaenge Eine Liste von Anh�ngen.
	 * @throws ServiceUnavailableException 
	 */
	void sendeMassenMail(List<String> emailAdressen, String subjekt, String body, boolean htmlBody, List<byte []> anhaenge) throws ServiceUnavailableException;
}
