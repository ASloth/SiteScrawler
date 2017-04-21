package de.sitescrawler.solr;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import de.sitescrawler.model.Artikel;
import de.sitescrawler.model.Filteprofil;
import de.sitescrawler.model.VolltextArtikel;
import de.sitescrawler.solr.interfaces.ISolrService;

public class SolrServiceImpl implements ISolrService {

	private SolrClient solrClient;
	private static final String SolrUrl = "http://sitescrawler.de:8983/solr/gettingstarted";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");

	public SolrServiceImpl() {
		this.solrClient = new HttpSolrClient.Builder(SolrUrl).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sitescrawler.solr.ISolrService#addArtikel(de.sitescrawler.model.
	 * Artikel)
	 */
	@Override
	public void addArtikel(Artikel artikel) {
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("autor", artikel.getAutor());
		solrInputDocument.addField("titel", artikel.getTitel());
		solrInputDocument.addField("beschreibung", artikel.getBeschreibung());
		solrInputDocument.addField("link", artikel.getLink());
		solrInputDocument.addField("erstellungsdatum", formatter.format(artikel.getErstellungsdatum()));
		try {
			this.solrClient.add(solrInputDocument);
			this.solrClient.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sitescrawler.solr.ISolrService#sucheArtikel(de.sitescrawler.model.
	 * FilterProfil)
	 */
	@Override
	public List<Artikel> sucheArtikel(List<Filteprofil> filterprofile) {

		List<Artikel> artikel = new ArrayList<>();

		for (Filteprofil filterprofil : filterprofile) {
			artikel.addAll(sucheArtikel(filterprofil));
		}

		return artikel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sitescrawler.solr.ISolrService#sucheArtikel(de.sitescrawler.model.
	 * FilterProfil)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Artikel> sucheArtikel(Filteprofil filterprofil) {

		List<Artikel> artikel = new ArrayList<>();

		SolrQuery solrQuery = new SolrQuery();
		String query = filterprofil.getTags().stream().reduce((s1, s2) -> s1.concat(" " + s2)).get();
		solrQuery.setQuery(query);
		try {
			QueryResponse response = this.solrClient.query(solrQuery);
			SolrDocumentList results = response.getResults();
			for (SolrDocument solrDocument : results) {
				String date = ((ArrayList<String>) solrDocument.get("erstellungsdatum")).get(0);
				Date erstellungsdatum = formatter.parse(date);
				String autor = ((ArrayList<String>) solrDocument.get("autor")).get(0);
				String titel = ((ArrayList<String>) solrDocument.get("titel")).get(0);
				String beschreibung = ((ArrayList<String>) solrDocument.get("beschreibung")).get(0);
				String link = ((ArrayList<String>) solrDocument.get("link")).get(0);
				artikel.add(new Artikel(erstellungsdatum, autor, titel, beschreibung, link, null));
			}
		} catch (SolrServerException | IOException | ParseException e) {
			e.printStackTrace();
		}

		return artikel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sitescrawler.solr.ISolrService#clearSolr()
	 */
	@Override
	public void clearSolr() {
		try {
			this.solrClient.deleteByQuery("*:*");
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Artikel> getAlleArtikel()
    {
        List<Artikel> artikel = new ArrayList<>();
        SolrQuery solrQuery = new SolrQuery("*:*");
        try
        {
            QueryResponse response = this.solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            for (SolrDocument solrDocument : results)
            {
                String date = ((ArrayList<String>) solrDocument.get("erstellungsdatum")).get(0);
                Date erstellungsdatum = SolrServiceImpl.formatter.parse(date);
                String autor = ((ArrayList<String>) solrDocument.get("autor")).get(0);
                String titel = ((ArrayList<String>) solrDocument.get("titel")).get(0);
                String beschreibung = ((ArrayList<String>) solrDocument.get("beschreibung")).get(0);
                String link = ((ArrayList<String>) solrDocument.get("link")).get(0);
                VolltextArtikel volltextArtikel = null;
                artikel.add(new Artikel(erstellungsdatum, autor, titel, beschreibung, link, volltextArtikel));
            }
        }
        catch (SolrServerException | IOException | ParseException e)
        {
            e.printStackTrace();
        }
        return artikel;
    }
}
