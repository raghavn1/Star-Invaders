
import java.awt.Rectangle;

public class Ammunition {
    public double x, y;
   

     public Ammunition(double x, double y){
        this.x = x;
        this.y = y;
    }

     public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConstants.AMMO_SIZE, GameConstants.AMMO_SIZE);
    }



}
