package de.sitescrawler.applikation;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.sitescrawler.jpa.Archiveintrag;
import de.sitescrawler.jpa.Artikel;

@SessionScoped
@Named("archiv")
public class ArchivBean implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Inject
    private DataBean          dataBean;

    private Archiveintrag     geweahlterArchiveintrag;
    private Artikel			  geweahlterArtikel;

    private Date              abZeitpunkt      = new Date();
    private Date              bisZeitpunkt     = new Date();

    public ArchivBean()
    {

    }

    @PostConstruct
    void init()
    {
        this.setGeweahlterArchiveintrag((Archiveintrag) this.dataBean.getFiltergruppe().getArchiveintraege().toArray()[0]);
    }

    public Archiveintrag getGeweahlterArchiveintrag()
    {
        return this.geweahlterArchiveintrag;
    }

    public void setGeweahlterArchiveintrag(Archiveintrag geweahlterArchiveintrag)
    {
        this.geweahlterArchiveintrag = geweahlterArchiveintrag;
    }

    public void buttonAction(Archiveintrag eintrag)
    {
        this.setGeweahlterArchiveintrag(eintrag);
        this.addMessage("Archiveintrag vom " + eintrag.getErstellungsdatum() + " ausgew�hlt!");
    }

    private void addMessage(String summary)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Date getAbZeitpunkt()
    {
        return this.abZeitpunkt;
    }

    public void setAbZeitpunkt(Date abZeitpunkt)
    {
        this.abZeitpunkt = abZeitpunkt;
    }

    public Date getBisZeitpunkt()
    {
        return this.bisZeitpunkt;
    }

    public void setBisZeitpunkt(Date bisZeitpunkt)
    {
        this.bisZeitpunkt = bisZeitpunkt;
    }

	public Artikel getGeweahlterArtikel() {
		return geweahlterArtikel;
	}

	public void setGeweahlterArtikel(Artikel geweahlterArtikel) {
		this.geweahlterArtikel = geweahlterArtikel;
	}
}
