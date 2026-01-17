import java.awt.Rectangle;

public class Player {
    public double x, y;
    public double angle; // in radians

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE);
    }


}