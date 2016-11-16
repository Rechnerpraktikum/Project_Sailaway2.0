/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author h1258009
 */
@Entity
@Table(name = "HAFEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hafen.findAll", query = "SELECT h FROM Hafen h"),
    @NamedQuery(name = "Hafen.findByUnlocode", query = "SELECT h FROM Hafen h WHERE h.unlocode = :unlocode"),
    @NamedQuery(name = "Hafen.findByStadt", query = "SELECT h FROM Hafen h WHERE h.stadt = :stadt"),
    @NamedQuery(name = "Hafen.findByLand", query = "SELECT h FROM Hafen h WHERE h.land = :land")})
public class Hafen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "UNLOCODE")
    private String unlocode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "STADT")
    private String stadt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LAND")
    private String land;
    @OneToMany(mappedBy = "unlocode")
    private Collection<Kanten> kantenCollection;

    public Hafen() {
    }

    public Hafen(String unlocode) {
        this.unlocode = unlocode;
    }

    public Hafen(String unlocode, String stadt, String land) {
        this.unlocode = unlocode;
        this.stadt = stadt;
        this.land = land;
    }

    public String getUnlocode() {
        return unlocode;
    }

    public void setUnlocode(String unlocode) {
        this.unlocode = unlocode;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    @XmlTransient
    public Collection<Kanten> getKantenCollection() {
        return kantenCollection;
    }

    public void setKantenCollection(Collection<Kanten> kantenCollection) {
        this.kantenCollection = kantenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unlocode != null ? unlocode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hafen)) {
            return false;
        }
        Hafen other = (Hafen) object;
        if ((this.unlocode == null && other.unlocode != null) || (this.unlocode != null && !this.unlocode.equals(other.unlocode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Hafen[ unlocode=" + unlocode + " ]";
    }
    
}
