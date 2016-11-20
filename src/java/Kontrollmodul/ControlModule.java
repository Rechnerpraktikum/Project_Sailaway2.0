/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontrollmodul;

import entityclasses.Schiff;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Tianhe Liu
 */

@ManagedBean
@SessionScoped
public class ControlModule implements Serializable{
    
    private int rl;   
    private int bam;   
    private int sfr;   
    private int rpm;
    private int sprit;   
    private boolean eon;
    private MeterGaugeChartModel fuelGaugeModel;
    private Schiff schiff;
    
    /*
     * Constructor wird erstellt
     * Wenn die Simulation mit einem Request den Constructor aufruft, 
     * werden darin enthaltene Werte zurueckgeliefert.
     */ 
    public ControlModule() {
        FacesContext context = FacesContext.getCurrentInstance();
        schiff = (Schiff)context.getExternalContext().getSessionMap().get("schiff");
    }
    
    /*
     * Getter und Setter von privaten Variablen setzen,
     * damit diese oeffentlich zugaenglich sind.
     */
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
    
    public int getSprit() {
        return sprit;
    }
    
    public boolean isEon() {
        return eon;
    }
 
    public void setEon(boolean eon) {
        this.eon = eon;
    }
    
    /*
     * Tankanzeige wird ab hier aufgebaut
     */
    @PostConstruct
    /*
     * init() wird automatisch ausgefuehrt, wenn der Brower geoeffnet wird.
     * mit @PostConstruct wird sichergestellt, dass Meter Parameter zuerst geladen werden.
     */
    public void init() {
        createMeterGaugeModel();
    }
    
    public MeterGaugeChartModel getFuelGaugeModel() {
        return fuelGaugeModel;
    }
    
    private MeterGaugeChartModel initFuelGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(30);
            add(70);
            add(160);
            add(300);
        }};
        
        return new MeterGaugeChartModel (sprit, intervals); // Sprit --> Alternativloesungen
    }
    
    private void createMeterGaugeModel() {
        fuelGaugeModel = initFuelGaugeModel();
        fuelGaugeModel.setTitle("Treibstoff Status");
        fuelGaugeModel.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
        fuelGaugeModel.setGaugeLabel("Liter");
        fuelGaugeModel.setGaugeLabelPosition("bottom");
        fuelGaugeModel.setShowTickLabels(true);
        fuelGaugeModel.setLabelHeightAdjust(110);
        fuelGaugeModel.setIntervalOuterRadius(100);
    }
    
    
    /*
    Action Listener fuer Tankzeiger
    public void powerOn(ActionEvent actionEvent){
        this.eon = true;
        this.pointer = this.sprit;
    }
    
    public void powerOff(ActionEvent actionEvent){
        this.eon = false;
        this.pointer = 0;
    }

    Event Listener fuer Tankzeiger
    Event Listener (){
        if (eon == true){
            Contructor aufrufen fuer aktuellen Sprit Wert;
        }
        else{
            this.sprit == 0;
        }
    }
     */
}
