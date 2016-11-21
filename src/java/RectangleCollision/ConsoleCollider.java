
package RectangleCollision;

public class ConsoleCollider {
    
    public static void main(String[] args) {
        Rectangle A = RectangleGenerator.generateRectangle();
        Rectangle B = RectangleGenerator.generateRectangle();
        //git update
        
        System.out.println("Schiff");
        printOutSchiffHafenInformation(A);
        System.out.println("Hafen");
        printOutSchiffHafenInformation(B);
        if (CollisionDetector.hasCollision(A, B)){
            System.out.println("Es gab eine Kollision!");
        } else {
            System.out.println("Keine Kollision erkannt");
        }
        
    }
    
    private static void printOutSchiffHafenInformation(Rectangle R){
        System.out.println("Origin at: " + R.origin.x + "," + R.origin.y);
        System.out.println("Height: " + R.height + ", Width: " + R.width);
    }
}
