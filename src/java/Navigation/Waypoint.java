/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigation;

import java.util.Date;
/*
 *
 * @author Tobias Haake (1000298)
 */
public class Waypoint {
    //Konstruktor: Reihenfolge der Parameter nach Navigation-Vorlage
    public Waypoint(int number, double latitude, double longitude, int bearing, double distance, Date eta) {
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
        this.distance = distance;
        this.eta = eta;
    }

    private int number;		//Wegpunkt Nummer
    private double latitude;	//geographischer Breitengrad
    private double longitude;	//geographischer Laengengrad
    private int bearing;	//Peilung zum naechsten Wegpunkt
    private double distance;	//Abstand zum naechsten Wegpunkt
    private Date eta;		//estimated time of arrival (voraussichtliche Ankunftszeit)

    public void setNumber(int number) { this.number = number; }
    public int getNumber() { return number; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLatitude() { return latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLongitude() { return longitude; }
    public void setBearing(int bearing) { this.bearing = bearing; }
    public int getBearing() { return bearing; }
    public void setDistance(double distance) { this.distance = distance; }
    //Umrechnung in Seemeilen mit Google: 1km = 0.539957sm
    //Wikipedia: Seemeile oder nautische Meile (M, Deutsch: sm, Englisch: NM)
    public String getDistance() { return new String(Math.round((distance/1000)*0.539957) + " NM"); }
    public void setEta(Date eta) { this.eta = eta; }
    //Nutzt Datentypumwandlung: "Date to String" aus Navigation
    public String getEta() { return new String(Navigation.dateToString(eta)); }
    public Date getEtaNavigation() { return this.eta; }
}