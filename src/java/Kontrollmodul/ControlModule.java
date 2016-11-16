/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontrollmodul;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Tianhe Liu
 */

@ManagedBean
public class ControlModule implements Serializable{
    
    private int rl;   
    private int bam;   
    private int sfr;   
    private int rpm;   
    private boolean eon;
    private MeterGaugeChartModel fuelGaugeModel;
 
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
    
    @PostConstruct
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
        
        return new MeterGaugeChartModel (0, intervals); // 0 muss ersetzt werden durch Wert aus der Simulation
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
     * Methode fuer if eon == true, Zeiger des Tankmeters updaten, entnommen aus Schiff Simulation
     * Methode fuer if eon == ture, Veraenderungen der Steuerungselementen an Schiff Simulation weitergeben
     * evtl. EventListener? Komplex
     */
}
