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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "KANTEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kanten.findAll", query = "SELECT k FROM Kanten k"),
    @NamedQuery(name = "Kanten.findByKantennummer", query = "SELECT k FROM Kanten k WHERE k.kantennummer = :kantennummer"),
    @NamedQuery(name = "Kanten.findByLongtitude", query = "SELECT k FROM Kanten k WHERE k.longtitude = :longtitude"),
    @NamedQuery(name = "Kanten.findByLatitude", query = "SELECT k FROM Kanten k WHERE k.latitude = :latitude")})
public class Kanten implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KANTENNUMMER")
    private Integer kantennummer;
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
    @JoinColumn(name = "UNLOCODE", referencedColumnName = "UNLOCODE")
    @ManyToOne
    private Hafen unlocode;

    public Kanten() {
    }

    public Kanten(Integer kantennummer) {
        this.kantennummer = kantennummer;
    }

    public Kanten(Integer kantennummer, String longtitude, String latitude) {
        this.kantennummer = kantennummer;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public Integer getKantennummer() {
        return kantennummer;
    }

    public void setKantennummer(Integer kantennummer) {
        this.kantennummer = kantennummer;
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

    public Hafen getUnlocode() {
        return unlocode;
    }

    public void setUnlocode(Hafen unlocode) {
        this.unlocode = unlocode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kantennummer != null ? kantennummer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kanten)) {
            return false;
        }
        Kanten other = (Kanten) object;
        if ((this.kantennummer == null && other.kantennummer != null) || (this.kantennummer != null && !this.kantennummer.equals(other.kantennummer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Kanten[ kantennummer=" + kantennummer + " ]";
    }
    
}
