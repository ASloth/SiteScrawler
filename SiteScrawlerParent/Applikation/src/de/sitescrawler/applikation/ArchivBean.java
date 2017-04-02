package de.sitescrawler.applikation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.sitescrawler.model.Archiveintrag;
import de.sitescrawler.model.Artikel;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

@SessionScoped
@Named("archiv")
public class ArchivBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DataBean dataBean;
	
	private Archiveintrag geweahlterArchiveintrag;
	
	private Date abZeitpunkt = new Date();
	private Date bisZeitpunkt= new Date();
		
	public ArchivBean(){
		
	}
	
	@PostConstruct
	void init() {
		setGeweahlterArchiveintrag(getArchiveintraege().get(0));
	}
	
	public Archiveintrag getGeweahlterArchiveintrag() {
		return geweahlterArchiveintrag;
	}

	public void setGeweahlterArchiveintrag(Archiveintrag geweahlterArchiveintrag) {
		this.geweahlterArchiveintrag = geweahlterArchiveintrag; 
	}

	public void buttonAction(Archiveintrag eintrag) {
		 setGeweahlterArchiveintrag(eintrag);
         addMessage("Archiveintrag vom " + eintrag.getErstellungsDatum() + " ausgew�hlt!");
    }   
	
	private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public List<Archiveintrag> getArchiveintraege(){
		 List<Archiveintrag> result = dataBean.getFiltergruppe().getArchiveintraege();
		return result;
	}

	public Date getAbZeitpunkt() {
		return abZeitpunkt;
	}

	public void setAbZeitpunkt(Date abZeitpunkt) {
		this.abZeitpunkt = abZeitpunkt;
	}

	public Date getBisZeitpunkt() {
		return bisZeitpunkt;
	}

	public void setBisZeitpunkt(Date bisZeitpunkt) {
		this.bisZeitpunkt = bisZeitpunkt;
	}
}

