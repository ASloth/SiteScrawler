package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Filterprofilgruppe generated by hbm2java
 */
@Entity
@NamedQuery(name = "Fitlerprofilgruppen.findAll", query = "SELECT f FROM Filterprofilgruppe f")
public class Filterprofilgruppe implements java.io.Serializable
{

    private static final long  serialVersionUID = 1L;
    private int                filterprofilgruppeId;
    private Filtermanager      filtermanager;
    private Intervall          intervall;
    private String             titel;
    private Date               letzteerstellung;
    private Boolean            verschickeemail;
    // 1 - 31
    private Integer            monatlicherTermin;
    private DayOfWeek          wochentag;
    private Set<Filterprofil>  filterprofile    = new HashSet<>(0);
    private Set<Nutzer>        empfaenger       = new HashSet<>(0);
    private Set<Uhrzeit>       uhrzeiten        = new HashSet<>(0);
    private Set<Archiveintrag> archiveintraege  = new HashSet<>(0);

    // Unmapped
    private Firma              firma;
    private Nutzer             nutzer;

    public Filterprofilgruppe()
    {
    }

    public Filterprofilgruppe(Filtermanager filtermanager, Intervall intervall, String titel)
    {
        this(filtermanager, intervall, titel, null, false, 1, DayOfWeek.SUNDAY, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), null, null);
    }

    public Filterprofilgruppe(Filtermanager filtermanager, Intervall intervall, String titel, Date letzteerstellung, Boolean verschickeemail,
                              Integer monatlicherTermin, DayOfWeek wochentag, Set<Filterprofil> filterprofile, Set<Nutzer> empfaenger, Set<Uhrzeit> uhrzeiten,
                              Set<Archiveintrag> archiveintraege, Firma firma, Nutzer nutzer)
    {
        this.filtermanager = filtermanager;
        this.intervall = intervall;
        this.titel = titel;
        this.letzteerstellung = letzteerstellung;
        this.verschickeemail = verschickeemail;
        this.monatlicherTermin = monatlicherTermin;
        this.wochentag = wochentag;
        this.filterprofile = filterprofile;
        this.empfaenger = empfaenger;
        this.uhrzeiten = uhrzeiten;
        this.archiveintraege = archiveintraege;
        this.firma = firma;
        this.nutzer = nutzer;
    }
    // Unmapped

    @Transient
    public boolean isFirmenGruppe()
    {
        return this.getFirma() != null;
    }

    @Transient
    public boolean isNutzerGruppe()
    {
        return this.getNutzer() != null;
    }

    @Transient
    public Firma getFirma()
    {
        return this.firma;
    }

    public void setFirma(Firma firma)
    {
        this.firma = firma;
    }

    @Transient
    public Nutzer getNutzer()
    {
        return this.nutzer;
    }

    public void setNutzer(Nutzer nutzer)
    {
        this.nutzer = nutzer;
    }

    // Mapped

    // NOT MAPPED

    // MAPPED

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "filterprofilgruppeId", unique = true, nullable = false)
    public int getFilterprofilgruppeId()
    {
        return this.filterprofilgruppeId;
    }

    public void setFilterprofilgruppeId(int filterprofilgruppeId)
    {
        this.filterprofilgruppeId = filterprofilgruppeId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "Filtermanager_identifikation", nullable = false)
    public Filtermanager getFiltermanager()
    {
        return this.filtermanager;
    }

    public void setFiltermanager(Filtermanager filtermanager)
    {
        this.filtermanager = filtermanager;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "Intervall_intervall", nullable = false)
    public Intervall getIntervall()
    {
        return this.intervall;
    }

    public void setIntervall(Intervall intervall)
    {
        this.intervall = intervall;
    }

    @Column(name = "titel", nullable = false)
    public String getTitel()
    {
        return this.titel;
    }

    public void setTitel(String titel)
    {
        this.titel = titel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "letzteerstellung", length = 19)
    public Date getLetzteerstellung()
    {
        return this.letzteerstellung;
    }

    public void setLetzteerstellung(Date letzteerstellung)
    {
        this.letzteerstellung = letzteerstellung;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "filterprofilgruppe_beinhaltet_filterprofil",
               joinColumns = { @JoinColumn(name = "Filterprofilgruppe_FilterprofilgruppeId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Filterprofil_FilterprofilId", nullable = false, updatable = false) })
    public Set<Filterprofil> getFilterprofile()
    {
        return this.filterprofile;
    }

    public void setFilterprofile(Set<Filterprofil> filterprofile)
    {
        this.filterprofile = filterprofile;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    @JoinTable(name = "empfaenger", joinColumns = { @JoinColumn(name = "Filtergruppe_FilterprofilgruppeId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Nutzer_Filtermanager_identifikation", nullable = false, updatable = false) })
    public Set<Nutzer> getEmpfaenger()
    {
        return this.empfaenger;
    }

    public void setEmpfaenger(Set<Nutzer> empfaenger)
    {
        this.empfaenger = empfaenger;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    @JoinTable(name = "Filtergruppe_erstellt_zu_Uhrzeit",
               joinColumns = { @JoinColumn(name = "Filtergruppe_FilterprofilgruppeId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Uhrzeit_uhrzeit", nullable = false, updatable = false) })
    public Set<Uhrzeit> getUhrzeiten()
    {
        return this.uhrzeiten;
    }

    public void setUhrzeiten(Set<Uhrzeit> uhrzeiten)
    {
        this.uhrzeiten = uhrzeiten;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filterprofilgruppe", cascade = { CascadeType.ALL })
    public Set<Archiveintrag> getArchiveintraege()
    {
        return this.archiveintraege;
    }

    public void setArchiveintraege(Set<Archiveintrag> archiveintraege)
    {
        this.archiveintraege = archiveintraege;
    }

    public Boolean getVerschickeemail()
    {
        return this.verschickeemail;
    }

    public void setVerschickeemail(Boolean verschickeemail)
    {
        this.verschickeemail = verschickeemail;
    }

    public Integer getMonatlicherTermin()
    {
        return this.monatlicherTermin;
    }

    public void setMonatlicherTermin(Integer monatlicherTermin)
    {
        this.monatlicherTermin = monatlicherTermin;
    }

    @Enumerated(EnumType.STRING)
    public DayOfWeek getWochentag()
    {
        return this.wochentag;
    }

    public void setWochentag(DayOfWeek wochentag)
    {
        this.wochentag = wochentag;
    }

}
