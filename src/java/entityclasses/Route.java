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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ROUTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
    @NamedQuery(name = "Route.findByRoutenid", query = "SELECT r FROM Route r WHERE r.routenid = :routenid"),
    @NamedQuery(name = "Route.findByRoutenname", query = "SELECT r FROM Route r WHERE r.routenname = :routenname")})
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROUTENID")
    private Integer routenid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ROUTENNAME")
    private String routenname;
    @JoinColumn(name = "RUFZEICHEN", referencedColumnName = "RUFZEICHEN")
    @ManyToOne
    private Schiff rufzeichen;
    @OneToMany(mappedBy = "routenid")
    private Collection<Wegpunkte> wegpunkteCollection;

    public Route() {
    }

    public Route(Integer routenid) {
        this.routenid = routenid;
    }

    public Route(Integer routenid, String routenname) {
        this.routenid = routenid;
        this.routenname = routenname;
    }

    public Integer getRoutenid() {
        return routenid;
    }

    public void setRoutenid(Integer routenid) {
        this.routenid = routenid;
    }

    public String getRoutenname() {
        return routenname;
    }

    public void setRoutenname(String routenname) {
        this.routenname = routenname;
    }

    public Schiff getRufzeichen() {
        return rufzeichen;
    }

    public void setRufzeichen(Schiff rufzeichen) {
        this.rufzeichen = rufzeichen;
    }

    @XmlTransient
    public Collection<Wegpunkte> getWegpunkteCollection() {
        return wegpunkteCollection;
    }

    public void setWegpunkteCollection(Collection<Wegpunkte> wegpunkteCollection) {
        this.wegpunkteCollection = wegpunkteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routenid != null ? routenid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.routenid == null && other.routenid != null) || (this.routenid != null && !this.routenid.equals(other.routenid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Route[ routenid=" + routenid + " ]";
    }
    
}
