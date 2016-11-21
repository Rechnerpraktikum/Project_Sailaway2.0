package Wetter;


class VektorRechner {
    private double winkelAlpha;
    private double winkelBeta;
    private double x;
    private double y;
    private Vektor AusgabeVektor;

    public VektorRechner (){};
    
    public Vektor getWinkelToVektor(double richtung, double laenge) {
        if ( richtung <= 180 ) {
            winkelAlpha = Math.abs(richtung - 90);
        } else {
            winkelAlpha = Math.abs(richtung - 270);
        }
        winkelBeta = 90 - winkelAlpha;
        
        x = Math.sin(Math.toRadians(winkelBeta))*laenge;
        y = Math.sin(Math.toRadians(winkelAlpha))*laenge;
    
        if ( richtung >= 90 && richtung < 180) {
            y = y * (-1);
        } else if ( richtung >= 180 && richtung < 270) {
            y = y *(-1);
            x = x *(-1);
        } else if ( richtung >= 270 && richtung < 360) {
            x= x* (-1);
        }
        
        AusgabeVektor = new Vektor(x,y);
        return AusgabeVektor;
    }
    
    public Vektor vektorAddition (Vektor Vektor1, Vektor Vektor2) {
        Vektor VektorNeu = new Vektor (Vektor1.getX() + Vektor2.getX(), Vektor1.getY() + Vektor2.getY());   
        return VektorNeu;
    }
    public Vektor vektorAddition (Vektor Vektor1, Vektor Vektor2, Vektor Vektor3) {
        Vektor VektorNeu = new Vektor (Vektor1.getX() + Vektor2.getX() + Vektor3.getX(), 
                                       Vektor1.getY() + Vektor2.getY()+ Vektor3.getY());   
        return VektorNeu;
    }
    public Vektor vektorAddition (Vektor Vektor1, Vektor Vektor2, Vektor Vektor3, Vektor Vektor4) {
        Vektor VektorNeu = new Vektor (Vektor1.getX() + Vektor2.getX() + Vektor3.getX() + Vektor4.getX(), 
                                       Vektor1.getY() + Vektor2.getY()+ Vektor3.getY() + Vektor4.getY());   
        return VektorNeu;
    }
    public Vektor vektorAddition (Vektor Vektor1, Vektor Vektor2, Vektor Vektor3, Vektor Vektor4, Vektor Vektor5) {
        Vektor VektorNeu = new Vektor (Vektor1.getX() + Vektor2.getX() + Vektor3.getX() + Vektor4.getX() + Vektor5.getX(), 
                                       Vektor1.getY() + Vektor2.getY()+ Vektor3.getY() + Vektor4.getY() + Vektor5.getY());   
        return VektorNeu;
    }
    

    public double getVektorToRichtung(Vektor VektorEingabe) {
        double laenge = Math.sqrt(Math.pow(Math.abs(VektorEingabe.getX()),2)+Math.pow(Math.abs(VektorEingabe.getY()),2));
        double richtung = Math.toDegrees(Math.asin((Math.abs(VektorEingabe.getY())/laenge)));
        richtung = 90 - richtung;
        return richtung;
    }
    public double getVektorToLaenge(Vektor VektorEingabe) {
        double laenge = Math.sqrt(Math.pow(VektorEingabe.getX(),2)+Math.pow(VektorEingabe.getY(),2));               
        return laenge;
    }
}
