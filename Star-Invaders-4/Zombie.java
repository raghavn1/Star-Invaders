//Class to create enemy zombies that follow the player
import java.awt.Rectangle;

public class Zombie extends GameObject {
    //Variables to store where the zombie is on the screen

    //Constructor to set the starting spot for the zombie
    public Zombie(double x, double y) {
        super.x = x;
        super.y = y;
    }

    //Method to make the zombie move toward the player (called multiple times per second)
    public void chase(Player p) {
        //Calculate the angle between the zombie and the player (Math.atan2 gives angle in radians given distances)
        double angle = Math.atan2((p.y + GameConstants.PLAYER_SIZE / 2.0) - (y + GameConstants.ZOMBIE_SIZE / 2.0), //Players Y distance from the zombie
                                  (p.x + GameConstants.PLAYER_SIZE / 2.0) - (x + GameConstants.ZOMBIE_SIZE / 2.0)); //Players X distance from the zombie
        
        //Move the zombie's X and Y position based on that angle and the zombie's speed, like the bullet move method
        x += Math.cos(angle) * GameConstants.ZOMBIE_SPEED;
        y += Math.sin(angle) * GameConstants.ZOMBIE_SPEED;
    }

    //Method to get the hitbox of the zombie for collision detection
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 80, 80);
    }
}