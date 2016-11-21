
package RectangleCollision;

public class CollisionDetector {
    
    public static boolean hasCollision(Rectangle A, Rectangle B){
        
        if( (A.origin.x + A.width >= B.origin.x ) &&
            (A.origin.x <= B.origin.x + B.width ) &&
            (A.origin.y + A.height >= B.origin.y ) &&
            (A.origin.y <= B.origin.y + B.height )
                ) {
            return true;
        }
        
        return false;
        
    }
    
}
