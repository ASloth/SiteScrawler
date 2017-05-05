package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * Nutzer generated by hbm2java
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Nutzer extends Filtermanager implements java.io.Serializable
{

    private static final long       serialVersionUID    = 1L;
    private String                  vorname;
    private String                  nachname;
    private String                  email;
    private String                  passwort;
    private Set<Rolle>              rollen              = new HashSet<>(0);
    private Set<Filterprofilgruppe> filterprofilgruppen = new HashSet<>(0);
    private Set<Mitarbeiter>        mitarbeiter         = new HashSet<>(0);

    public Nutzer()
    {
    }

    public Nutzer(String email, String passwort)
    {
        this.email = email;
        this.passwort = passwort;
    }

    public Nutzer(String vorname, String nachname, String email, String passwort, Set<Rolle> rollen, Set<Filterprofilgruppe> filterprofilgruppes,
                  Set<Mitarbeiter> mitarbeiter)
    {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.rollen = rollen;
        this.filterprofilgruppen = filterprofilgruppes;
        this.mitarbeiter = mitarbeiter;
    }
    
    //Unmapped
    @Transient
    public String getGanzenNamen(){
    	return vorname + " " + nachname;
    }
    
    @Transient
    public boolean isFirmengruppeVonNutzer(Filterprofilgruppe filtergruppe){
    	return filterprofilgruppen.contains(filtergruppe);
    }
    
    public Firma getFirmaZuFirmengruppe(Filterprofilgruppe gruppe){
    	for(Firma f: getFirmen())
    	{
    		if(f.getFilterprofilgruppen().contains(gruppe))
    		{
    			return f;
    		}
    	}
    	return null;
    }
    
    //Mapped

    @Column(name = "vorname")
    public String getVorname()
    {
        return this.vorname;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    @Column(name = "nachname")
    public String getNachname()
    {
        return this.nachname;
    }

    public void setNachname(String nachname)
    {
        this.nachname = nachname;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Column(name = "passwort", nullable = false, length = 45)
    public String getPasswort()
    {
        return this.passwort;
    }

    public void setPasswort(String passwort)
    {
        this.passwort = passwort;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "nutzer_hat_rolle", joinColumns = { @JoinColumn(name = "Nutzer_Filtermanager_identifikation", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Rolle_rolle", nullable = false, updatable = false) })
    public Set<Rolle> getRollen()
    {
        return this.rollen;
    }

    public void setRollen(Set<Rolle> rollen)
    {
        this.rollen = rollen;
    }

    @Override
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "empfaenger", joinColumns = { @JoinColumn(name = "Nutzer_Filtermanager_identifikation", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Filtergruppe_FilterprofilgruppeId", nullable = false, updatable = false) })
    public Set<Filterprofilgruppe> getFilterprofilgruppen()
    {
        return this.filterprofilgruppen;
    }

    @Override
    public void setFilterprofilgruppen(Set<Filterprofilgruppe> filterprofilgruppen)
    {
        this.filterprofilgruppen = filterprofilgruppen;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nutzer")
    public Set<Mitarbeiter> getMitarbeiter()
    {
        return this.mitarbeiter;
    }

    public void setMitarbeiter(Set<Mitarbeiter> mitarbeiter)
    {
        this.mitarbeiter = mitarbeiter;
    }

    @Transient
    public List<Firma> getFirmen()
    {
        return this.mitarbeiter.stream().map(Mitarbeiter::getFirma).collect(Collectors.toList());
    }

}
