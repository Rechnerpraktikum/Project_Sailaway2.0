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
@Table(name = "WEGPUNKTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wegpunkte.findAll", query = "SELECT w FROM Wegpunkte w"),
    @NamedQuery(name = "Wegpunkte.findByWegpunktnummer", query = "SELECT w FROM Wegpunkte w WHERE w.wegpunktnummer = :wegpunktnummer"),
    @NamedQuery(name = "Wegpunkte.findByLatitude", query = "SELECT w FROM Wegpunkte w WHERE w.latitude = :latitude"),
    @NamedQuery(name = "Wegpunkte.findByLongtitude", query = "SELECT w FROM Wegpunkte w WHERE w.longtitude = :longtitude")})
public class Wegpunkte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "WEGPUNKTNUMMER")
    private Integer wegpunktnummer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LONGTITUDE")
    private String longtitude;
    @JoinColumn(name = "ROUTENID", referencedColumnName = "ROUTENID")
    @ManyToOne
    private Route routenid;

    public Wegpunkte() {
    }

    public Wegpunkte(Integer wegpunktnummer) {
        this.wegpunktnummer = wegpunktnummer;
    }

    public Wegpunkte(Integer wegpunktnummer, String latitude, String longtitude) {
        this.wegpunktnummer = wegpunktnummer;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Integer getWegpunktnummer() {
        return wegpunktnummer;
    }

    public void setWegpunktnummer(Integer wegpunktnummer) {
        this.wegpunktnummer = wegpunktnummer;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Route getRoutenid() {
        return routenid;
    }

    public void setRoutenid(Route routenid) {
        this.routenid = routenid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wegpunktnummer != null ? wegpunktnummer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wegpunkte)) {
            return false;
        }
        Wegpunkte other = (Wegpunkte) object;
        if ((this.wegpunktnummer == null && other.wegpunktnummer != null) || (this.wegpunktnummer != null && !this.wegpunktnummer.equals(other.wegpunktnummer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Wegpunkte[ wegpunktnummer=" + wegpunktnummer + " ]";
    }
    
}
