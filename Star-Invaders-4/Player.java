//Class to create the player character and keep track of their position
import java.awt.Rectangle;

public class Player {
    //Variables to store the player's X and Y position
    public double x, y;
    public double angle; // the direction the player is looking (in radians)

    //Constructor to set the starting position of the player
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //Gets the "Hitbox" of the player for collision detection
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 20, 25);
    }
}