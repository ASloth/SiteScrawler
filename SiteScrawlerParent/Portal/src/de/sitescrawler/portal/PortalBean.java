package de.sitescrawler.portal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.sitescrawler.jpa.Nutzer;
import de.sitescrawler.model.ProjectConfig;
import de.sitescrawler.nutzerverwaltung.interfaces.INutzerService;

@SessionScoped
@Named("portal")
public class PortalBean implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    private Nutzer nutzer = new Nutzer();

    @Inject
    private ProjectConfig     config;
    
    @Inject 
    private INutzerService nutzerService;

    public ProjectConfig getConfig()
    {
        return this.config;
    }
    
    /**
     * Berechnet die Stärke des aktuellen Passworts auf einer Skala von 0-10
     * @return
     */
    public int getPasswortStaerke(){
    	if(!passwortAngegeben()) return 0;
    	
    	double totaleStaerke = 0;
    	
    	String passwort = getNutzer().getPasswort();
    	
    	//Check auf Länge
    	if(passwort.length() > 6) totaleStaerke++;
    	
    	if(passwort.length() > 8) totaleStaerke++;
    	
    	//Check ob Kleinbuchstabe enthalten ist.
    	if(passwort.matches(".*[a-z]+.*")) totaleStaerke ++;
    	
    	//Check ob Großbuchstabe enthalten ist.
    	if(passwort.matches(".*[A-Z]+.*")) totaleStaerke ++;
    	
    	//Check ob Zahl enthalten ist..
    	if(passwort.matches(".*\\d+.*")) totaleStaerke ++;
    	
    	//Check ob Sonderzeichen enthalten ist.
    	if(passwort.matches(".*[@_$%&*#]+.*")) totaleStaerke ++; 
    	
    	return(int) ((totaleStaerke / 6) * 100);
    }
    
    public String getPasswortStaerkeAlsText(){ 
    	 int staerke = getPasswortStaerke();
    	 
    	 if(staerke < 20)
    		 return "schwach";
    	 
    	 if(staerke < 40)
    		 return "einfach";
    	 
    	 if(staerke < 60)
    		 return "mittel";
    	 
    	 if(staerke < 80)
    		 return "gut";
    	 
    	 return "stark";
    }
    
    public boolean passwortAngegeben(){
    	return getNutzer().getPasswort() != null && !getNutzer().getPasswort().isEmpty();
    }
    
    public void sendeRegistrierungAb(){
    	nutzerService.registrieren(nutzer);
    	//TODO Leite Nutzer weiter
    }

	public Nutzer getNutzer() {
		return nutzer;
	}

	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
}
