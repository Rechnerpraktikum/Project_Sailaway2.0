
package RectangleCollision;

public class Rectangle {
    
    public class Coordinate {
        public int x;
        public int y;
        
        public Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    
    public Coordinate origin;
    public int height;
    public int width;
    
    public Rectangle(int x, int y, int h, int w) {
        origin = new Coordinate(x,y);
        height = h;
        width = w;
    }
    
}
    