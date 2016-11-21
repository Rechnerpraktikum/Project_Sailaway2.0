
package RectangleCollision;

import java.util.Random;

public class RectangleGenerator {
    
    public static Rectangle generateRectangle() {
        Random rng = new Random();
        
        return new Rectangle(
                    rng.nextInt(10),
                    rng.nextInt(10),
                    rng.nextInt(9) + 1,
                    rng.nextInt(9) + 1
                );
        
    }
    
}