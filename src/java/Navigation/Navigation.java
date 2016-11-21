/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Navigation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.commons.lang3.time.DateUtils;
/*
 *
 * @author Tobias Haake (1000298)
 */
@ManagedBean(name = "Navigation")   //Marks a bean to be a managed bean with the name specified in name attribute. If the name attribute is not specified, then the managed bean name will default to class name portion of the fully qualified class name. 
@ApplicationScoped                  //Scope Annotation: Bean lives as long as the web application lives. It get created upon the first HTTP request involving this bean in the application (or when the web application starts up and the eager=true attribute is set in @ManagedBean) and get destroyed when the web application shuts down.
public class Navigation {
    
    private int velocity;               //Geschwindigkeit (Schnittstelle input (vom Schiff))
    private Date reached;               //Eta (Estimated time of arrival) vom letzten Wegpunkt (Schnittstelle output (reached - siehe Zeichnung))
    private Waypoint currentWaypoint;   //aktueller Wegpunkt
    private double[] currentLocation;   //aktuelle Position
    
    public void setVelocity() { this.velocity = 100; } //Zum setzen von einer Testgeschwindigkeit
    public double getVelocity() { return velocity; }
    public Waypoint getCurrentWaypoint() { return this.currentWaypoint; }
    public String getReached() { return dateToString(this.reached); }
    public double[] getCurrentLocation() { return currentLocation; }
    //setCurrentLocation() ohne Parameter erzeugt Testkoordinaten
    public double[] setCurrentLocation() { 
        double[] cache = new double[2];
        cache[0] = 56.7894321;
        cache[1] = -43.1256789;
        return cache;
    }
    //setCurrentLocation(LATITUDE,LONGITUDE)
    public double[] setCurrentLocation(double a, double b) {
        double[] cache = new double[2];
        cache[0] = a;
        cache[1] = b;
        return cache;
    }
    
    //Methode benutzt java.text.DateFormat und java.text.SimpleDateFormat
    //Datentypumwandlung: "Date to String"
    //hh = hours, mm = minutes, ss = seconds, a = am/pm
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        return new String(dateFormat.format(date));
    }
    
    //Methode benutzt org.apache.commons.lang3.time.DateUtils
    //Methode berechnet Eta (Estimated time of arrival)
    public Date calculateEta(double distance, Date date) { return DateUtils.addMinutes(date, (int) Math.round(((distance/this.velocity)/60))); }

    //Methode, die die Distanz zwischen dem aktuellen und zu erreichenden Wegpunkt berechnet
    public double distance(double currentLatitude, double currentLongitude, double latitudeToReach, double longitudeToReach) {
        double differenceLatitudes = Math.toRadians(latitudeToReach-currentLatitude); //toRadians(double angdeg) Converts an angle measured in degrees to an approximately equivalent angle measured in radians
        double differenceLongitudes = Math.toRadians(longitudeToReach-currentLongitude);
        double cache = Math.sin(differenceLatitudes/2) * Math.sin(differenceLatitudes/2) + Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(latitudeToReach)) * Math.sin(differenceLongitudes/2) * Math.sin(differenceLongitudes/2);
        return (double) (6371001 * (2 * Math.atan2(Math.sqrt(cache), Math.sqrt(1-cache)))); //Wikipedia: Mittlerer Erdradius = 6371000,785 m; atan2(double y, double x) Returns the angle theta from the conversion of rectangular coordinates (x, y) to polar coordinates (r, theta)
    }    
    
    //Erzeuge eine Liste von allen eingegebenen Wegpunkten
    //Liste verwendet ausschliesslich Testdaten
    public List<Waypoint> createNavigation() {
        List<Waypoint> list = new ArrayList<Waypoint>();
        Date currentDate = new Date();
        currentLocation = setCurrentLocation();
        setVelocity();
        //Erzeuge einen Testeintrag (alle Koordinaten sind Testdaten)
        list.add(new Waypoint(0,12.3456789,-98.7654321,0,distance(currentLocation[0],currentLocation[1],12.3456789,-98.7654321),calculateEta(distance(currentLocation[0],currentLocation[1],12.3456789,-98.7654321),currentDate)));
        //Fuege eine Testliste hinzu (alle Koordinaten sind Testdaten)
        //Waypoint(number,latitude,longitude,bearing,distance,eta)
        for (int i = 1; i < 10; ++i) list.add(new Waypoint(i,(i+12.3456789),(i-12.3456789),(i+10),distance(list.get(i-1).getLatitude(),list.get(i-1).getLongitude(),i+10,i+20),calculateEta(distance(list.get(i-1).getLatitude(),list.get(i-1).getLongitude(),10,20),list.get(i-1).getEtaNavigation())));
        //Wegpunkte nach der Zeit, die benoetigt wird, um diese zu erreichen, sortieren
        Collections.sort(list, new Comparator<Waypoint>() {
        @Override //Einbindung von Metadaten in den Quelltext: Mit diesem Typ kann eine Methode gekennzeichnet werden, die die Methode ihrer Oberklasse überschreibt. Der Compiler stellt dann sicher, dass die Oberklasse diese Methode enthält und gibt einen Fehler aus, wenn dies nicht der Fall ist.
        public int compare(Waypoint firstWaypoint, Waypoint secondWaypoint) { if (firstWaypoint.getEtaNavigation().before(secondWaypoint.getEtaNavigation())) return -1; else return 1; } //return -1 if firstWaypoint should be before secondWaypoint else 1
        });
        for (int i = 1; i < list.size(); ++i) list.get(i).setNumber(i);     //Liste nummerieren
        this.reached = list.get(list.size()-1).getEtaNavigation();          //Setze reached gleich dem eta vom letzten Eintrag der Liste (Schnittstelle output)
        this.currentWaypoint = list.get(0);                                 //aktuellen (ersten) Wegpunkt setzen
        return list;
    }
    
    //Loeschen wenn currentLocation in der Naehe von einem Wegpunkt ist
    public List<Waypoint> compareWaypoints(List<Waypoint> list) {
        double differenceLatitudes = Math.abs(this.currentLocation[0]-list.get(0).getLatitude());                           //Math.abs() returns the absolute value of an int value.
        double differenceLongitudes = Math.abs(this.currentLocation[1]-list.get(0).getLongitude());
        double mark = 2; //Grenze fuer ein bisschen Spielraum, da sicher nicht die exakten Koordinaten getroffen werden
        if (Math.pow(differenceLatitudes, 2)+Math.pow(differenceLongitudes, 2) <= Math.pow(mark, 2)) { list.remove(0); }    //Math.pow() returns the value of the first argument raised to the power of the second argument.
        return list;
    }
    
    //Methode zum aktualisieren der Liste
    //Distanz und Eta werden neu berechnet
    public List<Waypoint> updateList(List<Waypoint> list) {
        list.get(0).setDistance(distance(currentLocation[0],currentLocation[1],list.get(0).getLatitude(),list.get(0).getLongitude()));
        Date currentDate = new Date();
        list.get(0).setEta(calculateEta(distance(currentLocation[0],currentLocation[1],list.get(0).getLatitude(),list.get(0).getLongitude()),currentDate));
        //Schleife berechnet fuer alle Listenelemente neue Eta Werte
        for (int i = 1; i < list.size(); ++i) list.get(i).setEta(calculateEta(distance(list.get(i-1).getLatitude(),list.get(i-1).getLongitude(),list.get(i).getLatitude(),list.get(i).getLongitude()),list.get(i-1).getEtaNavigation()));
        return list;
    }
    
    //Methode zum updaten der Liste, aktuelle Position, aktuelle Geschwindigkeit und zum entfernen von Wegpunkte, die erreicht wurden.
    //Methode verwendet Testdaten
    public List<Waypoint> updateNavigation(List<Waypoint> list) {
        currentLocation = setCurrentLocation(12.3456789,-98.7654321);   //Schnittstelle: input (Wo ist das Schiff gerade?)
        this.velocity = 200;                                            //Schnittstelle: input (Wie schnell ist das Schiff gerade?)
        list = compareWaypoints(list);                                  //Ist ein Wegpunkt in der Naehe wird dieser entfernt
        this.currentWaypoint = list.get(0);                             //currentWaypoint wird neu gesetzt
        list = updateList(list);                                        //Liste aktualisieren (Distanz + Eta)
        return list;
    }
}