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
@Table(name = "USR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usr.findAll", query = "SELECT u FROM Usr u"),
    @NamedQuery(name = "Usr.findByUsrid", query = "SELECT u FROM Usr u WHERE u.usrid = :usrid"),
    @NamedQuery(name = "Usr.findByPasswort", query = "SELECT u FROM Usr u WHERE u.passwort = :passwort")})
public class Usr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USRID")
    private Integer usrid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PASSWORT")
    private String passwort;
    @OneToMany(mappedBy = "usrid")
    private Collection<Schiff> schiffCollection;

    public Usr() {
    }

    public Usr(Integer usrid) {
        this.usrid = usrid;
    }

    public Usr(Integer usrid, String passwort) {
        this.usrid = usrid;
        this.passwort = passwort;
    }

    public Integer getUsrid() {
        return usrid;
    }

    public void setUsrid(Integer usrid) {
        this.usrid = usrid;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @XmlTransient
    public Collection<Schiff> getSchiffCollection() {
        return schiffCollection;
    }

    public void setSchiffCollection(Collection<Schiff> schiffCollection) {
        this.schiffCollection = schiffCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrid != null ? usrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usr)) {
            return false;
        }
        Usr other = (Usr) object;
        if ((this.usrid == null && other.usrid != null) || (this.usrid != null && !this.usrid.equals(other.usrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Usr[ usrid=" + usrid + " ]";
    }
    
}
