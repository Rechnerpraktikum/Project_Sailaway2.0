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
public class FuelStatus implements Serializable {
    
    private MeterGaugeChartModel fuelGaugeModel;
    
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
}
