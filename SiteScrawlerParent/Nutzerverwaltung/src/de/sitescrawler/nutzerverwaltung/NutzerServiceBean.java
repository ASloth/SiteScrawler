package de.sitescrawler.nutzerverwaltung;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import de.sitescrawler.jpa.Archiveintrag;
import de.sitescrawler.jpa.Artikel;
import de.sitescrawler.jpa.Filterprofilgruppe;
import de.sitescrawler.jpa.Nutzer;
import de.sitescrawler.jpa.Rolle;
import de.sitescrawler.nutzerverwaltung.interfaces.INutzerService;
import de.sitescrawler.solr.interfaces.ISolrService;

@ApplicationScoped
@Named
@Transactional
public class NutzerServiceBean implements INutzerService, Serializable
{
    private static final Logger LOGGER           = Logger.getLogger("de.sitescrawler.logger");
    private static final long   serialVersionUID = 1L;
    @PersistenceContext
    private EntityManager       entityManager;
    @Inject
    ISolrService                solrService;

    @Override
    @Transactional(value = TxType.REQUIRED)
    public void rolleAnlegen(Rolle rolle)
    {
        this.entityManager.merge(rolle);
        NutzerServiceBean.LOGGER.info(rolle + " als Rolle angelegt.");
    }

    @Override
    public Nutzer getNutzer(String email)
    {
        NutzerServiceBean.LOGGER.info("Suche Nutzer in der DB mit Email " + email);
        TypedQuery<Nutzer> query = this.entityManager.createQuery("SELECT n FROM Nutzer n WHERE n.email= :email", Nutzer.class);
        query.setParameter("email", email);
        EntityGraph<?> entityGraph = this.entityManager.getEntityGraph("Nutzer.*");
        query.setHint("javax.persistence.loadgraph", entityGraph);
        Nutzer nutzer = query.getSingleResult();
        this.completeNutzer(nutzer);
        return nutzer;
    }

    /**
     * L�dt alle Daten aus der DB um den nutzer zu vervollst�ndigen
     *
     * @param nutzer
     */
    private void completeNutzer(Nutzer nutzer)
    {
        for (Filterprofilgruppe filterprofilgruppe : nutzer.getFilterprofilgruppen())
        {
            for (Archiveintrag archiveintrag : filterprofilgruppe.getArchiveintraege())
            {
                for (Artikel artikel : archiveintrag.getArtikel())
                { 
            		this.solrService.komplettiereArtikel(artikel);
                }
            }
        }
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public void nutzerSpeichern(Nutzer nutzer)
    {
        NutzerServiceBean.LOGGER.info("Nutzer " + nutzer + " wird persistiert.");
        this.entityManager.merge(nutzer);
    }

    @Override
    public boolean isEmailVerfuegbar(String email)
    {
        TypedQuery<Nutzer> query = this.entityManager.createQuery("SELECT n FROM Nutzer n WHERE n.email = :email", Nutzer.class);
        query.setParameter("email", email);
        boolean result = query.getResultList().isEmpty();
        return result;
    }

    @Override
    public void registrieren(Nutzer nutzer)
    {
        // TODO Email Verifizierung
        this.nutzerSpeichern(nutzer);

    }

    @Override
    public void passwortZuruecksetzen(String email, String nutzername)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void neuesPasswortSetzen(String token, String neuesPasswort)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void nutzerLoeschen(Nutzer nutzer)
    {
        this.entityManager.remove(nutzer);
        NutzerServiceBean.LOGGER.info(nutzer + " wurde gel�scht.");
    }

}
