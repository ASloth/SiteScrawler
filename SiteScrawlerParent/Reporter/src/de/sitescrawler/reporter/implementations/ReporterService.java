package de.sitescrawler.reporter.implementations;

import java.time.LocalDateTime; 
import java.util.List; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import de.sitescrawler.jpa.Filterprofilgruppe;
import de.sitescrawler.jpa.management.FiltergruppenZugriffsManager;
import de.sitescrawler.jpa.management.interfaces.IFiltergruppenZugriffsManager;
import de.sitescrawler.logger.LoggerSiteScrawler;
import de.sitescrawler.reporter.interfaces.IReporterService; 

public class ReporterService implements IReporterService {
	 
	@Inject
	IFiltergruppenZugriffsManager filtergruppenZugriff;
	ExecutorService      threadPool = Executors.newFixedThreadPool(5);
	
	public ReporterService(){
		if(filtergruppenZugriff == null)
			filtergruppenZugriff = new FiltergruppenZugriffsManager();
	}
	
	public void generiereReports(LocalDateTime zeitpunkt) { 
		LocalDateTime korrigierteAktuelleZeit = rundeZeitpunkt(zeitpunkt);
		
		List<Filterprofilgruppe> gruppen = getFiltergruppeMitEmpfangZuAktuellemZeitpunkt(korrigierteAktuelleZeit); 
		
		for(Filterprofilgruppe fg : gruppen){
			Runnable run = new ArchiveintragErstellen(fg, korrigierteAktuelleZeit);
            this.threadPool.submit(run);
		}
	}   
	
	private List<Filterprofilgruppe> getFiltergruppeMitEmpfangZuAktuellemZeitpunkt(LocalDateTime zeitpunkt){
		return filtergruppenZugriff.getFiltergruppeMitEmpfangZuZeitpunkt(zeitpunkt); 
	}
	
	/**
	 * Berechnet die aktuelle Zeit und rundet die Minuten auf die n�hste halbe Stunde/volle Stunde.
	 * @return
	 */
	private LocalDateTime rundeZeitpunkt(LocalDateTime aktuelleZeit){		
		//Runde Minute abh�ngig von der aktuellen Sekunde und setze Sekunde auf 0
		int sekunde = aktuelleZeit.getSecond();
		if(sekunde >= 30)
			aktuelleZeit = aktuelleZeit.withMinute(aktuelleZeit.getMinute()+1);
		
		aktuelleZeit = aktuelleZeit.withSecond(0);
		
		//Runde Minute auf die n�chste halbe Stunde/volle Stunde
		int minute = aktuelleZeit.getMinute();
		
		if(minute != 30 && minute != 30){
			
			//Berechne Abst�nde zu n�chsten vollen Stunden/halber Stunde
			int abstandZuLetzterStunde = minute;
			int abstandZuNaechsterStunde = 60 - minute;
			int abstandZuHalberStunde = minute < 30 ? 30 - minute : minute - 30;
			
			//Korrigiere Zeit zu n�chster Zeit
			if(abstandZuHalberStunde < abstandZuLetzterStunde && abstandZuHalberStunde < abstandZuNaechsterStunde){
				//N�hster Abstand zu halber Stunde
				aktuelleZeit = aktuelleZeit.withMinute(30);
			}else if(abstandZuLetzterStunde < abstandZuNaechsterStunde){
				//N�hster Abstand zum aktuellen Stunden Anfang
				aktuelleZeit = aktuelleZeit.withMinute(0);
			}
			else
			{
				//N�hster Abstand zu n�chster Stunde, z�hle auch Stunde hoch
				aktuelleZeit = aktuelleZeit.withMinute(0).withHour(aktuelleZeit.getHour()+1);
			}
		} 
		
		return aktuelleZeit; 
	}
}
