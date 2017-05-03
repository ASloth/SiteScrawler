package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.sitescrawler.model.ZeitIntervall;

/**
 * Intervall generated by hbm2java
 */
@Entity
public class Intervall implements java.io.Serializable
{

    private static final long       serialVersionUID    = 1L;
    private ZeitIntervall           zeitintervall;
    private Set<Filterprofilgruppe> filterprofilgruppen = new HashSet<>(0);

    public Intervall()
    {
    }

    public Intervall(ZeitIntervall intervall)
    {
        this.zeitintervall = intervall;
    }

    public Intervall(ZeitIntervall intervall, Set<Filterprofilgruppe> filterprofilgruppen)
    {
        this.zeitintervall = intervall;
        this.filterprofilgruppen = filterprofilgruppen;
    }

    @Id

    @Column(name = "intervall", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    public ZeitIntervall getZeitIntervall()
    {
        return this.zeitintervall;
    }

    public void setZeitIntervall(ZeitIntervall intervall)
    {
        this.zeitintervall = intervall;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "intervall")
    public Set<Filterprofilgruppe> getFilterprofilgruppen()
    {
        return this.filterprofilgruppen;
    }

    public void setFilterprofilgruppen(Set<Filterprofilgruppe> filterprofilgruppen)
    {
        this.filterprofilgruppen = filterprofilgruppen;
    }

}
