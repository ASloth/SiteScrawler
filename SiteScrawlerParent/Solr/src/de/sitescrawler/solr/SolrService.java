package de.sitescrawler.solr;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.mysql.jdbc.Util;

import de.sitescrawler.jpa.Artikel;
import de.sitescrawler.jpa.Filterprofil;
import de.sitescrawler.solr.interfaces.ISolrService;
import de.sitescrawler.utility.DateUtils;

/**
 * Diese Klasse stellt Methoden zum Indizieren aller gefundenen Artikel und Bereitstellen von gesuchten Artikeln. 
 * Dies ermöglicht es, die gespeicherten Artikel schnell zu durchsuchen.
 * @author Marcel, William
 *
 */
@ApplicationScoped
@Named
public class SolrService implements ISolrService, Serializable
{
    private static final long             serialVersionUID = 1L;
    private static final Logger           LOGGER           = Logger.getLogger("de.sitescrawler.logger");

    private SolrClient                    solrClient;

    // TODO: in config-Datei auslagern
    // private static final String SolrUrl = "http://sitescrawler.de:8983/solr/testdaten";
    //private static final String SolrUrl = "http://sitescrawler.de:8983/solr/spielwiesewilliam";
    private static final String           SolrUrl          = "http://sitescrawler.de:8983/solr/sitescrawler_dev_solr";
    private static final SimpleDateFormat formatter        = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public SolrService()
    {
        SolrService.LOGGER.log(Level.INFO, "Nutze Solr Instanz: " + SolrService.SolrUrl);
        this.solrClient = new HttpSolrClient.Builder(SolrService.SolrUrl).build();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sitescrawler.solr.ISolrService#addArtikel(de.sitescrawler.model. Artikel)
     */
    @Override
    public void addArtikel(List<Artikel> artikel)
    {
        artikel.forEach(a -> {
                a.setErstellungsdatum(a.getErstellungsdatum());
        });
        try
        {
            SolrService.LOGGER.info("Schreibe in Solrinstanz " + SolrService.SolrUrl + " folgende Artikel: " + artikel);
            this.solrClient.addBeans(artikel);
            this.solrClient.commit();
            SolrService.LOGGER.info("Schreiben in Solr erfolgreich");
        }
        catch (SolrServerException | IOException e)
        {
            SolrService.LOGGER.log(Level.SEVERE, "Fehler beim Schreiben in Solrinstanz " + SolrService.SolrUrl, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sitescrawler.solr.ISolrService#sucheArtikel(de.sitescrawler.model. FilterProfil)
     */
    @Override
    public List<Artikel> sucheArtikel(List<Filterprofil> filterprofile, Date letzteSuche)
    {
    	SolrQuery solrQuery = new SolrQuery();
    	
    	for(Filterprofil fp : filterprofile)
    	{
    		addSuchstring(solrQuery, fp.getTagstring());
    	}
    	if(letzteSuche!=null){
    	    optionSucheArtikelinZeitraum(solrQuery, letzteSuche, null);    	    
    	}
    	LOGGER.info("Durchsuche Solr nach. " + solrQuery);
    	
    	List<Artikel> solrErgebnis = getArtikel(solrQuery);
    	
    	LOGGER.info(solrErgebnis.size() + " Artikel gefunden.");
    	
        return solrErgebnis;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sitescrawler.solr.ISolrService#sucheArtikel(de.sitescrawler.model. FilterProfil)
     */

    @Override
    public List<Artikel> sucheArtikel(Filterprofil filterprofil)
    {
        SolrQuery solrQuery = new SolrQuery();
        String query = filterprofil.getTagstring();
        solrQuery.setQuery(query);
        return this.getArtikel(solrQuery);
    }

    private List<Artikel> getArtikel(SolrQuery solrQuery)
    {
        List<Artikel> artikel = new ArrayList<>();
        try
        {
            QueryResponse response = this.solrClient.query(solrQuery);
            artikel = new ArrayList<>(new HashSet<>(response.getBeans(Artikel.class)));
        }
        catch (SolrServerException | IOException e)
        {
            SolrService.LOGGER.log(Level.SEVERE, "Fehler beim Suchen von Artikeln.", e);
        } 
        return artikel;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sitescrawler.solr.ISolrService#clearSolr()
     */
    @Override
    public void clearSolr()
    {
        try
        {
            this.solrClient.deleteByQuery("*:*");
        }
        catch (SolrServerException | IOException e)
        {
            SolrService.LOGGER.log(Level.SEVERE, "Fehler bei clearSolr.", e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Artikel> getAlleArtikel()
    {
        return this.getArtikel(new SolrQuery("*:*"));
    }

    @Override
    public Artikel sucheArtikelMitLink(String link)
    {
        String linkMitEscaping = "";

        for (char ch : link.toCharArray())
        {
            if ((ch + "").matches("[:/\\?]"))
            {
                linkMitEscaping += "\\" + ch;
            }
            else
            {
                linkMitEscaping += ch;
            }
        }
        SolrQuery solrQuery = new SolrQuery("link:" + linkMitEscaping);
        List<Artikel> artikel = this.getArtikel(solrQuery);
        return artikel.isEmpty() ? null : artikel.get(0);
    }

    @Override
    public void komplettiereArtikel(Artikel artikel)
    {
        Artikel solrArtikel = this.sucheArtikelMitLink(artikel.getLink());
        if (solrArtikel == null)
        {
            return;
        }

        // Fülle bestehenden Artikel mit Solr Informationen auf.
        artikel.setAbsaetzeArtikel(solrArtikel.getAbsaetzeArtikel());
        artikel.setAutor(solrArtikel.getAutor());
        artikel.setBeschreibung(solrArtikel.getBeschreibung());
        Date korrigiertesDate = de.sitescrawler.utility.DateUtils.asDate(DateUtils.asLocalDateTime(solrArtikel.getErstellungsdatum()).plusHours(2));
        
        artikel.setErstellungsdatum(korrigiertesDate);
        artikel.setFavoritenzahl(solrArtikel.getFavoritenzahl());
        artikel.setRetweetzahl(solrArtikel.getRetweetzahl());
        artikel.setTitel(solrArtikel.getTitel());
        artikel.setQid(solrArtikel.getQid());
    }
        
    /**
     * Fuegt der SolrQuery die Bedingung hinzu, in einem definierten Zeitintervall nach Artikeln zu suchen.
     * 
     * @param solrQuery Die aktuelle SolrQuery.
     * @param datumVon Datum, ab dem nach Artikeln gesucht werden soll.
     * @param datumBis Datum, bis wann nach Artikeln gesucht werden soll.
     */
    private void optionSucheArtikelinZeitraum(SolrQuery solrQuery, Date datumVon, Date datumBis){
        
        if(datumBis==null){
            System.out.println(SolrService.formatter.format(datumVon));
            solrQuery.set("fq","erstellungsdatum: ["+
                       SolrService.formatter.format(datumVon) + " TO NOW]");
//           SolrService.LOGGER.log(Level.INFO, "Ergebnissuche wurde auf das zeitliche Intervall [" + datumVon +  " bis " + new Date() + "] eingeschraenkt.");
        }
        else{
            solrQuery.set("fq","erstellungsdatum: ["+
                            SolrService.formatter.format(datumVon) + " TO " + SolrService.formatter.format(datumBis) + "]");
//            SolrService.LOGGER.log(Level.INFO, "Ergebnissuche wurde auf das zeitliche Intervall [" + datumVon +  " bis " + datumBis + "] eingeschraenkt.");
        }

    }
    
    /**
     * Fuegt der SolrQuery die Bedingung hinzu, nur eine bestimmte Anzahl an Ergebnissen zu erhalten.
     * 
     * @param solrQuery Die aktuelle SolrQuery.
     * @param maxArtikel Die maximale Anzahl an Artikeln, die ausgegeben werden soll.
     */
    private void optionSetzeMaximaleAnzahl(SolrQuery solrQuery, Integer maxArtikel){
        solrQuery.set("rows", maxArtikel);
//        SolrService.LOGGER.log(Level.INFO, "Ergebnisanzahl wurde auf " + maxArtikel + " reduziert.");
    }
    
    /**
     * Fuegt einen Suchstring der SolrQuery hinzu.
     * @param solrQuery Die aktuelle SolrQuery.
     * @param suchstring Der Suchstring, der der Suche hinzugefügt werden soll.
     */
    private void addSuchstring(SolrQuery solrQuery, String suchstring){
        if(solrQuery.get("q")==null){
            solrQuery.set("q", suchstring);
        }
        else{
            solrQuery.set("q", solrQuery.get("q") + " " + suchstring);
        }
        SolrService.LOGGER.log(Level.INFO, "Suchstring " + suchstring + " hinzugefuegt.");
    }
    
    /**
     * Fuegt der SolrQuery die Bedingung hinzu, dass ein bestimmter Prozentanteil von Suchbegriffen in den Artikeln vorliegen muss.
     * 
     * @param solrQuery Die aktuelle SolrQuery.
     * @param prozentQuote Anteil der gewuenschten Suchbegriffe in Prozent
     */
    private void optionSetzeMinimaleTrefferquote(SolrQuery solrQuery, Integer prozentQuote){
        if(Math.abs(prozentQuote)<=100){
            solrQuery.set("defType", "edismax");
            solrQuery.set("mm", prozentQuote.toString()+"%");
//            SolrService.LOGGER.log(Level.INFO, "Prozentanteil wurde auf " + prozentQuote + "% gesetzt.");
        }
        else{
            SolrService.LOGGER.log(Level.SEVERE, "Ungueltige Prozentzahlangabe bei der minimalen Trefferquote.");
        }
    }
    

}
