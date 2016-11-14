/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wu.com;

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
 * @author h1252164
 */
@Entity
@Table(name = "CREWMEMBER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crewmember.findAll", query = "SELECT c FROM Crewmember c"),
    @NamedQuery(name = "Crewmember.findByCrewmemberid", query = "SELECT c FROM Crewmember c WHERE c.crewmemberid = :crewmemberid"),
    @NamedQuery(name = "Crewmember.findByVorname", query = "SELECT c FROM Crewmember c WHERE c.vorname = :vorname"),
    @NamedQuery(name = "Crewmember.findByNachname", query = "SELECT c FROM Crewmember c WHERE c.nachname = :nachname")})
public class Crewmember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREWMEMBERID")
    private Integer crewmemberid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "VORNAME")
    private String vorname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NACHNAME")
    private String nachname;
    @JoinColumn(name = "RUFZEICHEN", referencedColumnName = "RUFZEICHEN")
    @ManyToOne
    private Schiff rufzeichen;

    public Crewmember() {
    }

    public Crewmember(Integer crewmemberid) {
        this.crewmemberid = crewmemberid;
    }

    public Crewmember(Integer crewmemberid, String vorname, String nachname) {
        this.crewmemberid = crewmemberid;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Integer getCrewmemberid() {
        return crewmemberid;
    }

    public void setCrewmemberid(Integer crewmemberid) {
        this.crewmemberid = crewmemberid;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
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
        hash += (crewmemberid != null ? crewmemberid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crewmember)) {
            return false;
        }
        Crewmember other = (Crewmember) object;
        if ((this.crewmemberid == null && other.crewmemberid != null) || (this.crewmemberid != null && !this.crewmemberid.equals(other.crewmemberid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wu.com.Crewmember[ crewmemberid=" + crewmemberid + " ]";
    }
    
}
