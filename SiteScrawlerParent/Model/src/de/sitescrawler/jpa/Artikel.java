package de.sitescrawler.jpa;
// Generated 02.05.2017 16:40:27 by Hibernate Tools 5.2.0.CR1

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Artikelsolr generated by hbm2java
 */
@Entity
@XmlRootElement(name = "artikel")
public class Artikel implements java.io.Serializable
{
    private static final long  serialVersionUID = 1L;
    private Set<Archiveintrag> archiveintraege  = new HashSet<>(0);

    @Field
    private String             link;
    @Field
    private String             autor;
    @Field
    private String             titel;
    @Field
    private String             beschreibung;
    @Field
    private List<String>       absaetzeArtikel  = new ArrayList<>();
    @Field
    private int                retweetzahl;
    @Field
    private int                favoritenzahl;
    @Field
    private Date               erstellungsdatum;
    private Quelle             quelle;
    @Field
    private Integer            qid;

    public Artikel()
    {
    }

    public Artikel(Date erstellungsdatum, String autor, String titel, String beschreibung, String link, Quelle quelle)
    {
        this.erstellungsdatum = erstellungsdatum;
        this.autor = autor;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.link = link;
        this.quelle = quelle;
        this.qid = quelle.getQid();
    }

    public Artikel(Date erstellungsdatum, String autor, String titel, String beschreibung, String link, List<String> absaetzeArtikel, Quelle quelle)
    {
        this.erstellungsdatum = erstellungsdatum;
        this.autor = autor;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.link = link;
        this.absaetzeArtikel = absaetzeArtikel;
        this.quelle = quelle;
        this.qid = quelle.getQid();
    }

    public Artikel(Set<Archiveintrag> archiveintraege, Date erstellungsdatum, String autor, String titel, String beschreibung, String link,
                   List<String> absaetzeArtikel, Quelle quelle)
    {
        this.archiveintraege = archiveintraege;
        this.erstellungsdatum = erstellungsdatum;
        this.autor = autor;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.link = link;
        this.absaetzeArtikel = absaetzeArtikel;
        this.quelle = quelle;
        this.qid = quelle.getQid();
    }

    @Transient
    public boolean isTwitterQuelle()
    {
        if (this.qid == null)
        {
            return false;
        }
        return this.qid == 2;
    }

    @Transient
    public Integer getQid()
    {
        return this.qid;
    }

    public void setQid(Integer qid)
    {
        this.qid = qid;
    }

    @Transient
    public int getRetweetzahl()
    {
        return this.retweetzahl;
    }

    public void setRetweetzahl(int retweetzahl)
    {
        this.retweetzahl = retweetzahl;
    }

    @Transient
    public int getFavoritenzahl()
    {
        return this.favoritenzahl;
    }

    public void setFavoritenzahl(int favoritenzahl)
    {
        this.favoritenzahl = favoritenzahl;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "Archiveintrag_beinhaltet_Artikel", joinColumns = { @JoinColumn(name = "Artikel_link", nullable = false, updatable = false) },
               inverseJoinColumns = { @JoinColumn(name = "Archiveintrag_archiveintragid", nullable = false, updatable = false) })
    @XmlTransient
    public Set<Archiveintrag> getArchiveintraege()
    {
        return this.archiveintraege;
    }

    public void setArchiveintraege(Set<Archiveintrag> archiveintraege)
    {
        this.archiveintraege = archiveintraege;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "Quelle_qid", nullable = false)
    public Quelle getQuelle()
    {
        return this.quelle;
    }

    public void setQuelle(Quelle quelle)
    {
        this.quelle = quelle;
    }

    @XmlTransient
    @Transient
    public Date getErstellungsdatum()
    {
        return this.erstellungsdatum;
    }

    public void setErstellungsdatum(Date erstellungsdatum)
    {
        this.erstellungsdatum = erstellungsdatum;
    }

    @Transient
    @XmlElement(name = "autor")
    public String getAutor()
    {
        return this.autor;
    }

    public void setAutor(String autor)
    {
        this.autor = autor;
    }

    @XmlElement(name = "titel")
    @Transient
    public String getTitel()
    {
        return this.titel;
    }

    public void setTitel(String titel)
    {
        this.titel = titel;
    }

    @XmlElement(name = "beschreibung")
    @Transient
    public String getBeschreibung()
    {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.beschreibung = beschreibung;
    }

    @XmlElement(name = "link")
    @Id
    public String getLink()
    {
        return this.link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    @Transient
    public List<String> getAbsaetzeArtikel()
    {
        return this.absaetzeArtikel;
    }

    public void setAbsaetzeArtikel(List<String> absaetzeArtikel)
    {
        this.absaetzeArtikel = absaetzeArtikel;
    }

    // Wird für die XML bzw. PDF-Generierung benötigt
    @XmlElement(name = "datum")
    @Transient
    public String getDatumFormatiert()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd. MMMM yyyy", Locale.GERMAN);
        Date datumAlt = this.getErstellungsdatum();
        String datum = formatter.format(datumAlt);

        return datum;
    }

    @Override
    public String toString()
    {
        return super.toString() + " || " + this.titel + " || " + this.link;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        Artikel other = (Artikel) obj;
        if (this.link == null)
        {
            if (other.link != null)
            {
                return false;
            }
        }
        else
            if (!this.link.equals(other.link))
            {
                return false;
            }
        return true;
    }

}
