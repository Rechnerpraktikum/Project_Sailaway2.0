/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author h1258009
 */
@Entity
@Table(name = "LOGBUCHEINTRAG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logbucheintrag.findAll", query = "SELECT l FROM Logbucheintrag l"),
    @NamedQuery(name = "Logbucheintrag.findByEintragnummer", query = "SELECT l FROM Logbucheintrag l WHERE l.eintragnummer = :eintragnummer"),
    @NamedQuery(name = "Logbucheintrag.findByZeit", query = "SELECT l FROM Logbucheintrag l WHERE l.zeit = :zeit"),
    @NamedQuery(name = "Logbucheintrag.findByLongtitude", query = "SELECT l FROM Logbucheintrag l WHERE l.longtitude = :longtitude"),
    @NamedQuery(name = "Logbucheintrag.findByLatitude", query = "SELECT l FROM Logbucheintrag l WHERE l.latitude = :latitude")})
public class Logbucheintrag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EINTRAGNUMMER")
    private Integer eintragnummer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZEIT")
    @Temporal(TemporalType.TIME)
    private Date zeit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LONGTITUDE")
    private String longtitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LATITUDE")
    private String latitude;
    @JoinColumn(name = "RUFZEICHEN", referencedColumnName = "RUFZEICHEN")
    @ManyToOne
    private Schiff rufzeichen;

    public Logbucheintrag() {
    }

    public Logbucheintrag(Integer eintragnummer) {
        this.eintragnummer = eintragnummer;
    }

    public Logbucheintrag(Integer eintragnummer, Date zeit, String longtitude, String latitude) {
        this.eintragnummer = eintragnummer;
        this.zeit = zeit;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public Integer getEintragnummer() {
        return eintragnummer;
    }

    public void setEintragnummer(Integer eintragnummer) {
        this.eintragnummer = eintragnummer;
    }

    public Date getZeit() {
        return zeit;
    }

    public void setZeit(Date zeit) {
        this.zeit = zeit;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Schiff getRufzeichen() {
        return rufzeichen;
    }

    public void setRufzeichen(Schiff rufzeichen) {
        this.rufzeichen = rufzeichen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eintragnummer != null ? eintragnummer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logbucheintrag)) {
            return false;
        }
        Logbucheintrag other = (Logbucheintrag) object;
        if ((this.eintragnummer == null && other.eintragnummer != null) || (this.eintragnummer != null && !this.eintragnummer.equals(other.eintragnummer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Logbucheintrag[ eintragnummer=" + eintragnummer + " ]";
    }
    
}
