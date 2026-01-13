import java.awt.Rectangle;

public class Bullet {
    public double x, y;
    public double angle;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void move() {
        x += Math.cos(angle) * GameConstants.BULLET_SPEED;
        y += Math.sin(angle) * GameConstants.BULLET_SPEED;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConstants.BULLET_SIZE, GameConstants.BULLET_SIZE);
    }
}