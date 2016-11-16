/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author h1258009
 */
@Entity
@Table(name = "ARTIKEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a"),
    @NamedQuery(name = "Artikel.findByArtikelnummer", query = "SELECT a FROM Artikel a WHERE a.artikelnummer = :artikelnummer"),
    @NamedQuery(name = "Artikel.findByArtikelname", query = "SELECT a FROM Artikel a WHERE a.artikelname = :artikelname")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARTIKELNUMMER")
    private Integer artikelnummer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ARTIKELNAME")
    private String artikelname;

    public Artikel() {
    }

    public Artikel(Integer artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public Artikel(Integer artikelnummer, String artikelname) {
        this.artikelnummer = artikelnummer;
        this.artikelname = artikelname;
    }

    public Integer getArtikelnummer() {
        return artikelnummer;
    }

    public void setArtikelnummer(Integer artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public String getArtikelname() {
        return artikelname;
    }

    public void setArtikelname(String artikelname) {
        this.artikelname = artikelname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artikelnummer != null ? artikelnummer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.artikelnummer == null && other.artikelnummer != null) || (this.artikelnummer != null && !this.artikelnummer.equals(other.artikelnummer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Artikel[ artikelnummer=" + artikelnummer + " ]";
    }
    
}
