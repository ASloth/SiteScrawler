package de.sitescrawler.firmenverwaltung.interfaces;

import de.sitescrawler.jpa.Nutzer;

/**
 * Service f�r das erstellen von Firmen.
 * @author konrad mueller
 */
public interface IFirmenErstellungsService {
	
	/**
	 * Ermittelt ob ein Firmenname noch verf�gbar ist.
	 * @param name Der zu pr�fende Firmenname.
	 * @return Ob der name noch verf�gbar ist.
	 */
	boolean IsFirmennameVerfuegbar(String name);
	
	/**
	 * Beantragt eine neue Firmen Entit�t. Der Antrag wird anschlie�end gepr�ft und manuell freigeschaltet.
	 * @param nutzer Der Nutzer, welcher die Firma beantragt.
	 * @param firmenName Name der beantragten Firma.
	 * @param comment Kommentar zu Firmenbeantragung.
	 * @return Ob der Antrag erfolgreich erstellt wurde.
	 */
	boolean FirmaBeantragen(Nutzer nutzer, 
			String firmenName, 
			String comment);
}
