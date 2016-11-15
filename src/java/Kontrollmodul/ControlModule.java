/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontrollmodul;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Tianhe Liu
 */

@ManagedBean
public class ControlModule {
    
    private int rl;   
    private int bam;   
    private int sfr;   
    private int rpm;   
    private boolean eon;
 
    public int getRl() {
        return rl;
    }
 
    public void setRl(int rl) {
        this.rl = rl;
    }
 
    public int getBam() {
        return bam;
    }
 
    public void setBam(int bam) {
        this.bam = bam;
    }
 
    public int getSfr() {
        return sfr;
    }
 
    public void setSfr(int sfr) {
        this.sfr = sfr;
    }
 
    public int getRpm() {
        return rpm;
    }
 
    public void setRpm(int rpm) {
        this.rpm = rpm;
    }
 
    public boolean isEon() {
        return eon;
    }
 
    public void setEon(boolean eon) {
        this.eon = eon;
    }
    
    /*
     * Zwei Methoden müssen noch eingebaut werden für 
     * a) Anfrage auf aktuelle Schiffsdaten aus der Simulation bei eon == true
     * b) eine Lösung finden um rl, bam, sfr und rpm zu updaten
     */
}
