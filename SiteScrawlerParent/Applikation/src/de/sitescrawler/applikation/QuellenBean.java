package de.sitescrawler.applikation;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.sitescrawler.crawler.interfaces.ICrawlerLaufService;
import de.sitescrawler.jpa.Quelle;
import de.sitescrawler.jpa.management.interfaces.IQuellenManager;

/**
 * 
 * @author robin QuellenBean, alle Methoden zur Quellenverwaltung.
 */
@SessionScoped
@Named("quellen")
public class QuellenBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DataBean dataBean;

	@Inject
	private IQuellenManager quellenManager;

	@Inject
	private ICrawlerLaufService crawlerService;

	private Quelle gewaehlteQuelle;

	private Quelle neueQuelle;

	private Quelle gewaehlteQuelleKopie;

	@PostConstruct
	public void init() {
		setzeDefaultQuelle();
	}

	/**
	 * Setze die Default Quelle auf das erste Element
	 */
	private void setzeDefaultQuelle() {
		if (getQuellen() != null && !getQuellen().isEmpty())
			setGewaehlteQuelle(getQuellen().get(0));
	}

	/**
	 * Manuellen Crawlvorgang starten
	 */
	public void crawleManuell() {
		crawlerService.crawl();
	}

	/**
	 * Get von allen Quellen
	 * 
	 * @return Liste von Quellen
	 */
	public List<Quelle> getQuellen() {
		List<Quelle> quellen = quellenManager.getQuellen();
		return quellen;
	}

	/**
	 * Get auf die aktuell gew�hlte Quelle
	 * 
	 * @return
	 */
	public Quelle getGewaehlteQuelle() {
		return gewaehlteQuelle;
	}

	/**
	 * Setze die neue gew�hlte Quelle, erzeugt dabei eine Kopie
	 * 
	 * @param geweahlteQuelle
	 */
	public void setGewaehlteQuelle(Quelle geweahlteQuelle) {
		gewaehlteQuelleKopie = new Quelle(geweahlteQuelle.getName(), geweahlteQuelle.getBild(),
				geweahlteQuelle.getRsslink(), geweahlteQuelle.getTagOderId(), geweahlteQuelle.getFilterprofile());
		this.gewaehlteQuelle = geweahlteQuelle;
	}

	/**
	 * Erstelle neue Quelle
	 */
	public void starteQuellenErstellen() {
		neueQuelle = new Quelle();
	}

	/**
	 * Verwirft die �nderungen und setzt die Felder wieder auf die
	 * urspr�nglichen Werte.
	 */
	public void verwerfeAenderungen() {
		gewaehlteQuelle.setName(gewaehlteQuelleKopie.getName());
		gewaehlteQuelle.setRsslink(gewaehlteQuelleKopie.getRsslink());
		gewaehlteQuelle.setTagOderId(gewaehlteQuelleKopie.getTagOderId());
	}

	/**
	 * Speichert die �nderungen
	 */
	public void speichereAenderung() {
		quellenManager.modifiziereQuelle(gewaehlteQuelle);
		setGewaehlteQuelle(getGewaehlteQuelle());
	}

	/**
	 * �bernimmt die neue Quelle
	 */
	public void uebernehmeNeueQuelle() {
		quellenManager.erstelleQuelle(neueQuelle);
		neueQuelle = new Quelle();
		setzeDefaultQuelle();
	}

	/**
	 * L�scht die gew�hlte Quelle und setzt eine neue Default Quelle
	 */
	public void loescheQuelle() {
		quellenManager.loescheQuelle(gewaehlteQuelle);
		setGewaehlteQuelle(null);

		setzeDefaultQuelle();
	}

	/**
	 * Boolean, ob der L�schen Button angezeigt werden muss
	 * 
	 * @return
	 */
	public boolean zeigeLoeschButton() {
		return true;
	}

	/**
	 * Boolean, ob der Verwerfen Button angezeigt werden muss
	 * 
	 * @return
	 */
	public boolean zeigeVerwerfenButton() {
		return wurdeQuelleVeraendert();
	}

	/**
	 * Boolean, ob der neue Quelle verwerfen Button angezeigt werden muss.
	 * Liefert true wenn ein Feld bef�llt ist.
	 * 
	 * @return
	 */
	public boolean zeigeNeueQuelleVerwerfenButton() {
		if (neueQuelle == null)
			return false;

		if (neueQuelle.getName() != null && !neueQuelle.getName().isEmpty())
			return true;

		if (neueQuelle.getRsslink() != null && !neueQuelle.getRsslink().isEmpty())
			return true;

		return false;
	}

	/**
	 * Boolean, ob der Quelle erstellen Button angezeigt werden muss, liefert
	 * true wenn alle Felder bef�llt sind.
	 * 
	 * @return
	 */
	public boolean zeigeErstellenButton() {
		if (neueQuelle == null)
			return false;

		if (neueQuelle.getName() == null || neueQuelle.getName().isEmpty())
			return false;

		if (neueQuelle.getRsslink() == null || neueQuelle.getRsslink().isEmpty())
			return false;

		return true;
	}

	public boolean zeigeQuelleAn() {
		return getGewaehlteQuelle() != null;
	}

	/**
	 * Boolean, ob der Speichern button angezeit werden muss.
	 * 
	 * @return
	 */
	public boolean zeigeSpeichernButton() {
		return wurdeQuelleVeraendert();
	}

	/**
	 * Boolean, ob die Quelle ver�ndert wurde. �berpr�ft, ob Inhalte der
	 * gew�hlten Quelle und der Kopie gleich sind.
	 * 
	 * @return
	 */
	private boolean wurdeQuelleVeraendert() {

		if (gewaehlteQuelleKopie == null || gewaehlteQuelle == null)
			return false;

		if (!istStringGleich(gewaehlteQuelle.getName(), gewaehlteQuelleKopie.getName()))
			return true;
		if (!istStringGleich(gewaehlteQuelle.getRsslink(), gewaehlteQuelleKopie.getRsslink()))
			return true;
		if (!istStringGleich(gewaehlteQuelle.getTagOderId(), gewaehlteQuelleKopie.getTagOderId()))
			return true;

		return false;
	}

	/**
	 * �berpr�ft ob zwei Strings gleich sind.
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	private boolean istStringGleich(String string1, String string2) {
		if (string1 == null && string2 == null)
			return true;

		if (string1 == null && string2.isEmpty())
			return true;

		if (string1.isEmpty() && string2 == null)
			return true;

		if (string1 == null || string2 == null)
			return false;

		if (string1.equals(string2))
			return true;

		return false;
	}

	/**
	 * Get f�r die neue Quelle
	 * 
	 * @return
	 */
	public Quelle getNeueQuelle() {
		return neueQuelle;
	}

	/**
	 * Set f�r die neue Quelle
	 * 
	 * @param neueQuelle
	 */
	public void setNeueQuelle(Quelle neueQuelle) {
		this.neueQuelle = neueQuelle;
	}
}
