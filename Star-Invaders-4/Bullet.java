//Class to create bullet objects
import java.awt.Rectangle;

public class Bullet extends GameObject {
    //Current position and movement angle of the bullet
    public double angle;

    //Constructor to initialize bullet position and angle
    public Bullet(double x, double y, double angle) {
        super.x = x;
        super.y = y;
        this.angle = angle;
    }

    //Method looks where the bullet moves based on its angle, and multiplies by speed
    public void move() {
        x += Math.cos(angle) * GameConstants.BULLET_SPEED;
        y += Math.sin(angle) * GameConstants.BULLET_SPEED;
    }

    //Method to get the bounding rectangle of the bullet for collision detection
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConstants.BULLET_SIZE, GameConstants.BULLET_SIZE);
    }
}