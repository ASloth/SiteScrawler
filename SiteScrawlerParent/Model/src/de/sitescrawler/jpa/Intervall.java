package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Intervall generated by hbm2java
 */
@Entity
public class Intervall implements java.io.Serializable
{

    private static final long       serialVersionUID    = 1L;
    private String                  intervall;
    private Set<Filterprofilgruppe> filterprofilgruppen = new HashSet<>(0);

    public Intervall()
    {
    }

    public Intervall(String intervall)
    {
        this.intervall = intervall;
    }

    public Intervall(String intervall, Set<Filterprofilgruppe> filterprofilgruppen)
    {
        this.intervall = intervall;
        this.filterprofilgruppen = filterprofilgruppen;
    }

    @Id

    @Column(name = "intervall", unique = true, nullable = false)
    public String getIntervall()
    {
        return this.intervall;
    }

    public void setIntervall(String intervall)
    {
        this.intervall = intervall;
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
