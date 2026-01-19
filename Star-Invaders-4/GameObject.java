import java.awt.Rectangle;

public abstract class GameObject {
    public double x, y;
    public double angle;
    public abstract Rectangle getBounds();
}