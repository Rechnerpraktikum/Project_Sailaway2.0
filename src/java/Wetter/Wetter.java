package Wetter;

public class Wetter {
    private int zufall;
    
    private VektorRechner Rechner = new VektorRechner();
    private Zyklon Zyklon1 = new Zyklon(48.1,15,110,5,850);
    private Zyklon Zyklon2 = new Zyklon(48.1,15,110,12,850);
    private Zyklon Zyklon3 = new Zyklon(48.1,15,100,8,850);
    private Zyklon Zyklon4 = new Zyklon(55,15,90,20,850);
    private Zyklon Zyklon5 = new Zyklon(55.6,14,120,10,850);
    
       
    public Wetter() {
        zufall = (int) ((Math.random()*3)+3);
        System.out.println("Es gibt: " + zufall + " Zyklone");
        
    }


    public double getLuftdruck(double n, double o, Zyklon thisZyklon, double zeit) {
        double nb = Math.abs((thisZyklon.getNoerdlicheBreite(zeit) - n) * 111.2);
        double oel = Math.abs((thisZyklon.getOestlicheLaenge(zeit) - o) * 96);
        double entfernung = Math.sqrt(Math.abs(Math.pow(nb, 2) + Math.pow(oel, 2))); 
        
        if (thisZyklon.getNoerdlicheBreite(zeit) - n == 0 ) {
            entfernung = oel;
            
        } else if ( thisZyklon.getOestlicheLaenge(zeit) - o == 0) {
            entfernung = nb;
        }
        
        double luftdruck = entfernung / 1.82 + thisZyklon.getTiefe(); 
        if ( luftdruck > 1013) {
            luftdruck = 1013;
        }
        
        return luftdruck;
    }
    
    public double getLuftdruck(double n, double o, Zyklon thisZyklon) {
        double nb = Math.abs((thisZyklon.getNoerdlicheBreite() - n) * 111.2);
        double oel = Math.abs((thisZyklon.getOestlicheLaenge() - o) * 96);
        double entfernung = Math.sqrt(Math.abs(Math.pow(nb, 2) + Math.pow(oel, 2))); 
        
        if (thisZyklon.getNoerdlicheBreite() - n == 0 ) {
            entfernung = oel;
            
        } else if ( thisZyklon.getOestlicheLaenge() - o == 0) {
            entfernung = nb;
        }
        
        double luftdruck = entfernung / 1.82 + thisZyklon.getTiefe(); 
        if ( luftdruck > 1013) {
            luftdruck = 1013;
        }
        
        return luftdruck;
    }

    public double getWindrichtung(double n , double o, Zyklon thisZyklon, double zeit) {
        double a = thisZyklon.getNoerdlicheBreite(zeit) - n;
        double b = Math.abs(thisZyklon.getOestlicheLaenge(zeit) - o); 
        double c = a/b;
            if ( a == 0 || b == 0) {
                c = 0;
            }
        double windrichtung = Math.toDegrees(Math.atan((c)));
        System.out.println("Windrichtung: "+windrichtung +" b: "+ thisZyklon.getNoerdlicheBreite(zeit));
      
        if ( n > thisZyklon.getNoerdlicheBreite(zeit) && o >= thisZyklon.getOestlicheLaenge(zeit) ) {
            windrichtung = windrichtung + 270;
        } else if ( n > thisZyklon.getNoerdlicheBreite(zeit) && o < thisZyklon.getOestlicheLaenge(zeit) ) {
            windrichtung = windrichtung + 180;
        } else if ( n < thisZyklon.getNoerdlicheBreite(zeit) && o <= thisZyklon.getOestlicheLaenge(zeit) ) {
            windrichtung = windrichtung + 90;
        }
        
        System.out.println("Windrichtung: "+windrichtung);
        return windrichtung;
    
    }
    
    
    
    public double getWindrichtung(double n , double o, Zyklon thisZyklon) {
        double a = thisZyklon.getNoerdlicheBreite() - n;
        double b = Math.abs(thisZyklon.getOestlicheLaenge() - o); 
        double c = a/b;
            if ( a == 0 || b == 0) {
                c = 0;
            }
        double windrichtung = Math.toDegrees(Math.atan((c)));
        System.out.println("Windrichtung: "+windrichtung +" b: "+ thisZyklon.getNoerdlicheBreite());
      
        if ( n > thisZyklon.getNoerdlicheBreite() && o >= thisZyklon.getOestlicheLaenge() ) {
            windrichtung = windrichtung + 270;
        } else if ( n > thisZyklon.getNoerdlicheBreite() && o < thisZyklon.getOestlicheLaenge() ) {
            windrichtung = windrichtung + 180;
        } else if ( n < thisZyklon.getNoerdlicheBreite() && o <= thisZyklon.getOestlicheLaenge() ) {
            windrichtung = windrichtung + 90;
        }
        
        System.out.println("Windrichtung: "+windrichtung);
        return windrichtung;
    }
    
    public double getWindstaerke(double n, double o, Zyklon thisZyklon, double zeit) {
        
        double windstaerke = 0.14 *(1013 - getLuftdruck(n, o, thisZyklon, zeit));
        return windstaerke;
    }
    
    
    
    public double getWindstaerke(double n, double o, Zyklon thisZyklon) {
        
        double windstaerke = 0.14 *(1013 - getLuftdruck(n, o, thisZyklon));
        System.out.println("Windstärke: "+windstaerke);        
                                                                                // Ausgabe in Km/h
        return windstaerke;
    }
    
    public void getWind(double n, double o) {
        Vektor Wind1 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon1),getWindstaerke(n,o,Zyklon1));
        Vektor Wind2 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon2),getWindstaerke(n,o,Zyklon2));
        Vektor Wind3 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon3),getWindstaerke(n,o,Zyklon3));
        Vektor Wind4 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon4),getWindstaerke(n,o,Zyklon4));
        Vektor Wind5 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon5),getWindstaerke(n,o,Zyklon5));
        
        Vektor WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3);
        
        switch (zufall) {
            case 3:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3);
                break;
            case 4:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3, Wind4);
                break;
            case 5:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3, Wind4, Wind5);
                break;
            default:
                break;
        }
        double richtung = Rechner.getVektorToRichtung(WindAusgabe);
        double laenge = Rechner.getVektorToLaenge(WindAusgabe);
        if ( WindAusgabe.getX() > 0) {
            if ( WindAusgabe.getY() > 0) {
                richtung = 90 - richtung;
            } else if ( WindAusgabe.getY() < 0) {
                richtung = richtung + 90;
            }
        }    
        if ( WindAusgabe.getX() <= o) {
            if ( WindAusgabe.getY() >= 0) {
                richtung = 270 + richtung;
            } else if ( WindAusgabe.getY() < 0) {
                richtung = 180 + richtung;
            }
        }
        if (WindAusgabe.getX() == 0 && WindAusgabe.getY() == 0) {
            richtung = 0;
        } else if (WindAusgabe.getX() == 0 && WindAusgabe.getY() > 0) { 
            richtung = 360;
        }    
        System.out.println("Richtung: " +richtung+ " Laenge: " +laenge);
        System.out.println(WindAusgabe.getX() +" " + WindAusgabe.getY() +" "+Rechner.getVektorToRichtung(WindAusgabe));
        
        
    }
    
    public void getWind(double n, double o, double zeit) {
        Vektor Wind1 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon1,zeit),getWindstaerke(n,o,Zyklon1,zeit));
        Vektor Wind2 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon2,zeit),getWindstaerke(n,o,Zyklon2,zeit));
        Vektor Wind3 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon3,zeit),getWindstaerke(n,o,Zyklon3,zeit));
        Vektor Wind4 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon4,zeit),getWindstaerke(n,o,Zyklon4,zeit));
        Vektor Wind5 = Rechner.getWinkelToVektor(getWindrichtung(n,o,Zyklon5,zeit),getWindstaerke(n,o,Zyklon5,zeit));
        
        Vektor WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3);
        
        switch (zufall) {
            case 3:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3);
                break;
            case 4:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3, Wind4);
                break;
            case 5:
                WindAusgabe = Rechner.vektorAddition(Wind1, Wind2, Wind3, Wind4, Wind5);
                break;
            default:
                break;
        }
        double richtung = Rechner.getVektorToRichtung(WindAusgabe);
        double laenge = Rechner.getVektorToLaenge(WindAusgabe);
        if ( WindAusgabe.getX() > 0) {
            if ( WindAusgabe.getY() >= 0) {
                richtung = 90 - richtung;
            } else if ( WindAusgabe.getY() < 0) {
                richtung = richtung + 90;
            }
        }    
        if ( WindAusgabe.getX() <= o) {
            if ( WindAusgabe.getY() >= 0) {
                richtung = 270 + richtung;
            } else if ( WindAusgabe.getY() < 0) {
                richtung = 180 + richtung;
            }
        }
        if (WindAusgabe.getX() == 0 && WindAusgabe.getY() == 0) {
            richtung = 0;
        } else if (WindAusgabe.getX() == 0 && WindAusgabe.getY() > 0) { 
            richtung = 360;
        }    
        System.out.println("Richtung: " +richtung+ " Laenge: " +laenge);
        
        
        
    }
    
}