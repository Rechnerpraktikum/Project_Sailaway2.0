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
@Table(name = "SCHIFF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schiff.findAll", query = "SELECT s FROM Schiff s"),
    @NamedQuery(name = "Schiff.findByRufzeichen", query = "SELECT s FROM Schiff s WHERE s.rufzeichen = :rufzeichen"),
    @NamedQuery(name = "Schiff.findBySchiffname", query = "SELECT s FROM Schiff s WHERE s.schiffname = :schiffname"),
    @NamedQuery(name = "Schiff.findByLatitude", query = "SELECT s FROM Schiff s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Schiff.findByLongtitude", query = "SELECT s FROM Schiff s WHERE s.longtitude = :longtitude"),
    @NamedQuery(name = "Schiff.findByTankinhalt", query = "SELECT s FROM Schiff s WHERE s.tankinhalt = :tankinhalt"),
    @NamedQuery(name = "Schiff.findBySfm", query = "SELECT s FROM Schiff s WHERE s.sfm = :sfm"),
    @NamedQuery(name = "Schiff.findByM", query = "SELECT s FROM Schiff s WHERE s.m = :m"),
    @NamedQuery(name = "Schiff.findByLp", query = "SELECT s FROM Schiff s WHERE s.lp = :lp"),
    @NamedQuery(name = "Schiff.findByLwl", query = "SELECT s FROM Schiff s WHERE s.lwl = :lwl"),
    @NamedQuery(name = "Schiff.findByAmm", query = "SELECT s FROM Schiff s WHERE s.amm = :amm"),
    @NamedQuery(name = "Schiff.findByLbm", query = "SELECT s FROM Schiff s WHERE s.lbm = :lbm"),
    @NamedQuery(name = "Schiff.findByKsm", query = "SELECT s FROM Schiff s WHERE s.ksm = :ksm"),
    @NamedQuery(name = "Schiff.findByKsv", query = "SELECT s FROM Schiff s WHERE s.ksv = :ksv"),
    @NamedQuery(name = "Schiff.findByRpmm", query = "SELECT s FROM Schiff s WHERE s.rpmm = :rpmm"),
    @NamedQuery(name = "Schiff.findByDdp", query = "SELECT s FROM Schiff s WHERE s.ddp = :ddp"),
    @NamedQuery(name = "Schiff.findByDds", query = "SELECT s FROM Schiff s WHERE s.dds = :dds"),
    @NamedQuery(name = "Schiff.findByLm", query = "SELECT s FROM Schiff s WHERE s.lm = :lm")})
public class Schiff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "RUFZEICHEN")
    private String rufzeichen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SCHIFFNAME")
    private String schiffname;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "TANKINHALT")
    private int tankinhalt;
    @Column(name = "SFM")
    private Integer sfm;
    @Column(name = "M")
    private Integer m;
    @Column(name = "LP")
    private Integer lp;
    @Column(name = "LWL")
    private Integer lwl;
    @Column(name = "AMM")
    private Integer amm;
    @Column(name = "LBM")
    private Integer lbm;
    @Column(name = "KSM")
    private Integer ksm;
    @Column(name = "KSV")
    private Integer ksv;
    @Column(name = "RPMM")
    private Integer rpmm;
    @Column(name = "DDP")
    private Integer ddp;
    @Column(name = "DDS")
    private Integer dds;
    @Column(name = "LM")
    private Integer lm;
    @OneToMany(mappedBy = "rufzeichen")
    private Collection<Route> routeCollection;
    @OneToMany(mappedBy = "rufzeichen")
    private Collection<Crewmember> crewmemberCollection;
    @JoinColumn(name = "MODELLID", referencedColumnName = "MODELLID")
    @ManyToOne
    private Schiffsmodell modellid;
    @JoinColumn(name = "USRID", referencedColumnName = "USRID")
    @ManyToOne
    private Usr usrid;
    @OneToMany(mappedBy = "rufzeichen")
    private Collection<Logbucheintrag> logbucheintragCollection;

    public Schiff() {
    }

    public Schiff(String rufzeichen) {
        this.rufzeichen = rufzeichen;
    }

    public Schiff(String rufzeichen, String schiffname, String latitude, String longtitude, int tankinhalt) {
        this.rufzeichen = rufzeichen;
        this.schiffname = schiffname;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.tankinhalt = tankinhalt;
    }

    public String getRufzeichen() {
        return rufzeichen;
    }

    public void setRufzeichen(String rufzeichen) {
        this.rufzeichen = rufzeichen;
    }

    public String getSchiffname() {
        return schiffname;
    }

    public void setSchiffname(String schiffname) {
        this.schiffname = schiffname;
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

    public int getTankinhalt() {
        return tankinhalt;
    }

    public void setTankinhalt(int tankinhalt) {
        this.tankinhalt = tankinhalt;
    }

    public Integer getSfm() {
        return sfm;
    }

    public void setSfm(Integer sfm) {
        this.sfm = sfm;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public Integer getLwl() {
        return lwl;
    }

    public void setLwl(Integer lwl) {
        this.lwl = lwl;
    }

    public Integer getAmm() {
        return amm;
    }

    public void setAmm(Integer amm) {
        this.amm = amm;
    }

    public Integer getLbm() {
        return lbm;
    }

    public void setLbm(Integer lbm) {
        this.lbm = lbm;
    }

    public Integer getKsm() {
        return ksm;
    }

    public void setKsm(Integer ksm) {
        this.ksm = ksm;
    }

    public Integer getKsv() {
        return ksv;
    }

    public void setKsv(Integer ksv) {
        this.ksv = ksv;
    }

    public Integer getRpmm() {
        return rpmm;
    }

    public void setRpmm(Integer rpmm) {
        this.rpmm = rpmm;
    }

    public Integer getDdp() {
        return ddp;
    }

    public void setDdp(Integer ddp) {
        this.ddp = ddp;
    }

    public Integer getDds() {
        return dds;
    }

    public void setDds(Integer dds) {
        this.dds = dds;
    }

    public Integer getLm() {
        return lm;
    }

    public void setLm(Integer lm) {
        this.lm = lm;
    }

    @XmlTransient
    public Collection<Route> getRouteCollection() {
        return routeCollection;
    }

    public void setRouteCollection(Collection<Route> routeCollection) {
        this.routeCollection = routeCollection;
    }

    @XmlTransient
    public Collection<Crewmember> getCrewmemberCollection() {
        return crewmemberCollection;
    }

    public void setCrewmemberCollection(Collection<Crewmember> crewmemberCollection) {
        this.crewmemberCollection = crewmemberCollection;
    }

    public Schiffsmodell getModellid() {
        return modellid;
    }

    public void setModellid(Schiffsmodell modellid) {
        this.modellid = modellid;
    }

    public Usr getUsrid() {
        return usrid;
    }

    public void setUsrid(Usr usrid) {
        this.usrid = usrid;
    }

    @XmlTransient
    public Collection<Logbucheintrag> getLogbucheintragCollection() {
        return logbucheintragCollection;
    }

    public void setLogbucheintragCollection(Collection<Logbucheintrag> logbucheintragCollection) {
        this.logbucheintragCollection = logbucheintragCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rufzeichen != null ? rufzeichen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schiff)) {
            return false;
        }
        Schiff other = (Schiff) object;
        if ((this.rufzeichen == null && other.rufzeichen != null) || (this.rufzeichen != null && !this.rufzeichen.equals(other.rufzeichen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Schiff[ rufzeichen=" + rufzeichen + " ]";
    }
    
}
