
package RectangleCollision;

public class CollisionDetectorTest {
    
    public void twoRectanglesCollide() {
        Rectangle A = new Rectangle(0,0,5,5);
        Rectangle B = new Rectangle(1,2,3,3);
        assert(CollisionDetector.hasCollision(A, B));
    }
    
    //zweiter Test
    public void twoRectanglesDoNotCollide() {
        Rectangle A = new Rectangle(0,0,6,7);
        Rectangle B = new Rectangle(0,8,4,5);
        assert(CollisionDetector.hasCollision(A, B));
    }
}
    