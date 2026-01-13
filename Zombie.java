import java.awt.Rectangle;

public class Zombie {
    public double x, y;

    public Zombie(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void chase(Player p) {
        double angle = Math.atan2((p.y + GameConstants.PLAYER_SIZE / 2.0) - (y + GameConstants.ZOMBIE_SIZE / 2.0),
                                  (p.x + GameConstants.PLAYER_SIZE / 2.0) - (x + GameConstants.ZOMBIE_SIZE / 2.0));
        x += Math.cos(angle) * GameConstants.ZOMBIE_SPEED;
        y += Math.sin(angle) * GameConstants.ZOMBIE_SPEED;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConstants.ZOMBIE_SIZE, GameConstants.ZOMBIE_SIZE);
    }
}