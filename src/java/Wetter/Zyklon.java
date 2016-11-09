
package Wetter;


class Zyklon {
   private double noerdlicheBreite;
   private double oestlicheLaenge;
   private double richtung;
   private double geschwindigkeit;
   private double tiefe; 
  
   
   public Zyklon(double noerdlicheBreite, double oestlicheLaenge, double richtung, double geschwindigkeit, double tiefe) {
       this.noerdlicheBreite = noerdlicheBreite;
       this.oestlicheLaenge = oestlicheLaenge;
       this.richtung = richtung; // 90-120° 
       this.geschwindigkeit = geschwindigkeit; // in Km/H
       this.tiefe = tiefe; // 850 - 1013
   }
   
   public double getNoerdlicheBreite(double zeit) {
       double c = richtung - 90;
              
       double nb =  noerdlicheBreite - ((geschwindigkeit * zeit)* Math.sin(Math.toRadians(c)))/111.2 ;
       return nb;    
   }
   public double getNoerdlicheBreite() {
       return noerdlicheBreite;
   }
   
   public double getOestlicheLaenge(double zeit) {
       double c = 90- (richtung - 90);
       
       double oel = ((geschwindigkeit * zeit) * Math.sin(Math.toRadians(c)))/96 + oestlicheLaenge;
       
       return oel;  
   }
   public double getOestlicheLaenge() {
       return oestlicheLaenge;
   }
   
   public double getTiefe() {
       return tiefe;
   }
   public double getGeschwindigkeit() {
        return geschwindigkeit;
   }
   
   public double getRichtung() {
   return richtung;
   }
}
