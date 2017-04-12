package de.sitescrawler.nutzerverwaltung.interfaces;

/**
 * Service, der den Nutzer und seine Daten der aktuellen Session zur�ckgibt
 *
 * @author Tobias Berner
 *
 */
public interface INutzerDatenService
{

    /**
     * Gibt aktuell angemeldeten Nutzer mit seinen Daten zur�ck.
     *
     * @return Nutzer mit seinen Daten
     */
    public Nutzer getNutzer();

    /**
     * Ermittelt der uid bei der Anmeldung die Nutzerdaten und gibt sie zur Verwaltung.
     * 
     * @param uid
     *            Benutzername
     */
    public void setNutzer(String uid);

}
