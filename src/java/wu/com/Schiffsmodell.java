/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wu.com;

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
 * @author h1252164
 */
@Entity
@Table(name = "SCHIFFSMODELL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schiffsmodell.findAll", query = "SELECT s FROM Schiffsmodell s"),
    @NamedQuery(name = "Schiffsmodell.findByModellid", query = "SELECT s FROM Schiffsmodell s WHERE s.modellid = :modellid"),
    @NamedQuery(name = "Schiffsmodell.findByModellname", query = "SELECT s FROM Schiffsmodell s WHERE s.modellname = :modellname")})
public class Schiffsmodell implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODELLID")
    private Integer modellid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MODELLNAME")
    private String modellname;
    @OneToMany(mappedBy = "modellid")
    private Collection<Schiff> schiffCollection;

    public Schiffsmodell() {
    }

    public Schiffsmodell(Integer modellid) {
        this.modellid = modellid;
    }

    public Schiffsmodell(Integer modellid, String modellname) {
        this.modellid = modellid;
        this.modellname = modellname;
    }

    public Integer getModellid() {
        return modellid;
    }

    public void setModellid(Integer modellid) {
        this.modellid = modellid;
    }

    public String getModellname() {
        return modellname;
    }

    public void setModellname(String modellname) {
        this.modellname = modellname;
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
        hash += (modellid != null ? modellid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schiffsmodell)) {
            return false;
        }
        Schiffsmodell other = (Schiffsmodell) object;
        if ((this.modellid == null && other.modellid != null) || (this.modellid != null && !this.modellid.equals(other.modellid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wu.com.Schiffsmodell[ modellid=" + modellid + " ]";
    }
    
}
