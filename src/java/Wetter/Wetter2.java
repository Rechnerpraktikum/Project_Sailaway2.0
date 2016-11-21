
package Wetter;

public class Wetter2 {

    
    public static void main(String[] args) {
        
        Wetter Test = new Wetter();
        
        double[] t = Test.getWind(48,1,5);
        System.out.println(t[0] +" "+ t[1]);
    }
}
    
