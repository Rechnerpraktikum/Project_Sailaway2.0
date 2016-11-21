
package Wetter;


class Zyklon {
   private double noerdlicheBreite;
   private double oestlicheLaenge;
   private double richtung;
   private double geschwindigkeit;
   private double tiefe; 
  
   
   public Zyklon() {
       noerdlicheBreite = ((Math.random()*30)+30);
       oestlicheLaenge = 0;
       richtung = ((Math.random()*30)+90);
       geschwindigkeit = ((Math.random()*15)+5)/1.85;
       tiefe = ((Math.random()*150)+850);
   }
   
   public double getNoerdlicheBreite(double zeit) {
       double c = richtung - 90;
              
       double nb =  noerdlicheBreite - ((geschwindigkeit * zeit)* Math.sin(Math.toRadians(c)))/111 ;
       
       
       return nb;    
   }
   public double getNoerdlicheBreite() {
       return noerdlicheBreite;
   }
   
   public double getOestlicheLaenge(double zeit) {
       double c = 90- (richtung - 90);
       
       double oel = ((geschwindigkeit * zeit) * Math.sin(Math.toRadians(c)))/111 + oestlicheLaenge;
      
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
