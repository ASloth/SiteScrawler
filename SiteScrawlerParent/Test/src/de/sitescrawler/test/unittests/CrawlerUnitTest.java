package de.sitescrawler.test.unittests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.sitescrawler.jpa.Artikel;
import de.sitescrawler.jpa.Quelle;
import de.sitescrawler.services.artikelausschneiden.ArtikelZurechtschneiden;
import de.sitescrawler.services.artikelausschneiden.UnparsbarException;
import de.sitescrawler.services.crawler.Verarbeitung;
import de.sitescrawler.test.testdaten.CrawlerTestdaten; 

public class CrawlerUnitTest
{

    @Test
    public void f10_1ZuschneidenSollteKorrekteAbsaetzeLiefern()
    {
        List<String> spiegel_WieGelaehmt_Absaetze_erwartet = CrawlerTestdaten.spiegel_WieGelaehmt_Absaetze;
        String spiegel_WieGelaehmt_ArtikelUrl_WieGelaehmt_erwartet = CrawlerTestdaten.spiegel_WieGelaehmt_ArtikelUrl_WieGelaehmt;

        List<String> spiegel_WieGelaehmt_Absaetze_ausgeschnitten = new ArrayList<>();
        try
        {
        	Quelle spiegelQuelle = new Quelle();
        	spiegelQuelle.setTagOderId("spArticleContent");
        	
            // TODO String aus Datenbank lesen
            spiegel_WieGelaehmt_Absaetze_ausgeschnitten = new ArtikelZurechtschneiden().getAbsaetze(spiegel_WieGelaehmt_ArtikelUrl_WieGelaehmt_erwartet,
            		spiegelQuelle);
        }
        catch (UnparsbarException e)
        {
            Assert.fail();
        }

        Assert.assertTrue(spiegel_WieGelaehmt_Absaetze_ausgeschnitten.equals(spiegel_WieGelaehmt_Absaetze_erwartet));
    }
    
    @Test
    public void teste_twitter_hole_trends(){
    	Verarbeitung verarbeitung = new Verarbeitung();
    	List<Artikel> ergebnisse = verarbeitung.durchsucheTwitter(false);
    	Assert.assertNotNull(ergebnisse);
    }

}
