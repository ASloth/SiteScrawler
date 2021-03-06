package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Filterprofil generated by hbm2java
 */
@Entity
public class Filterprofil implements java.io.Serializable
{

    private static final long       serialVersionUID    = 1L;
    private Integer                 filterprofilId;
    private Filtermanager           filtermanager;
    private String                  filterprofilname;
    private String                  tagstring;
    private byte[]                  solrquerylogik;
    private Set<Kategorie>          kategorien          = new HashSet<>(0);
    private Set<Filterprofilgruppe> filterprofilgruppen = new HashSet<>(0);
    private Set<Quelle>             quellen             = new HashSet<>(0);

    public Filterprofil()
    {
    }

    public Filterprofil(Filtermanager filtermanager, String filterprofilname)
    {
        this(filtermanager, filterprofilname, "", new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Filterprofil(Filtermanager filtermanager, String filterprofilname, String tagstring, Set<Kategorie> kategorien,
                        Set<Filterprofilgruppe> filterprofilgruppen, Set<Quelle> quellen)
    {
        this.filtermanager = filtermanager;
        this.filterprofilname = filterprofilname;
        this.tagstring = tagstring;
        this.kategorien = kategorien;
        this.filterprofilgruppen = filterprofilgruppen;
        this.quellen = quellen;
    }

    // NOT MAPPED

    // MAPPED

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "filterprofilId", unique = true, nullable = false)
    public Integer getFilterprofilId()
    {
        return this.filterprofilId;
    }

    public void setFilterprofilId(Integer filterprofilId)
    {
        this.filterprofilId = filterprofilId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "Filtermanager_identifikation")
    public Filtermanager getFiltermanager()
    {
        return this.filtermanager;
    }

    public void setFiltermanager(Filtermanager filtermanager)
    {
        this.filtermanager = filtermanager;
    }

    @Column(name = "filterprofilname")
    public String getFilterprofilname()
    {
        return this.filterprofilname;
    }

    public void setFilterprofilname(String filterprofilname)
    {
        this.filterprofilname = filterprofilname;
    }

    @Column(name = "tagstring", length = 65535)
    public String getTagstring()
    {
        return this.tagstring;
    }

    public void setTagstring(String tagstring)
    {
        this.tagstring = tagstring;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "Filterprofil_beinhaltet_Kategorie",
               joinColumns = { @JoinColumn(name = "Filterprofil_FilterprofilId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Kategorie_kategorienname", nullable = false, updatable = false) })
    public Set<Kategorie> getKategorien()
    {
        return this.kategorien;
    }

    public void setKategorien(Set<Kategorie> kategorien)
    {
        this.kategorien = kategorien;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "filterprofilgruppe_beinhaltet_filterprofil",
               joinColumns = { @JoinColumn(name = "Filterprofil_FilterprofilId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Filterprofilgruppe_FilterprofilgruppeId", nullable = false, updatable = false) })
    public Set<Filterprofilgruppe> getFilterprofilgruppen()
    {
        return this.filterprofilgruppen;
    }

    public void setFilterprofilgruppen(Set<Filterprofilgruppe> filterprofilgruppen)
    {
        this.filterprofilgruppen = filterprofilgruppen;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "Filterprofil_beinhaltet_Quelle",
               joinColumns = { @JoinColumn(name = "Filterprofil_FilterprofilId", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Quelle_qid", nullable = false, updatable = false) })
    public Set<Quelle> getQuellen()
    {
        return this.quellen;
    }

    public void setQuellen(Set<Quelle> quellen)
    {
        this.quellen = quellen;
    }

    public byte[] getSolrquerylogik()
    {
        return this.solrquerylogik;
    }

    public void setSolrquerylogik(byte[] solrquerylogik)
    {
        this.solrquerylogik = solrquerylogik;
    }

}
