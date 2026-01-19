//Class to create the player character and keep track of their position
import java.awt.Rectangle;

public class Player extends GameObject {
    //Variables to store the player's X and Y position
    public double angle;
    //Constructor to set the starting position of the player
    public Player(double x, double y) {
        super.x = x;
        super.y = y;
    }

    //Gets the "Hitbox" of the player for collision detection
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 20, 25);
    }
}