import java.awt.*;

/**
 * Created by skot on 3/30/17.
 */
public class Tile extends Sprite {

    Color backgroundColor;
    int size;

    public Tile (int x, int y, int size, Color backgroundColor) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.backgroundColor = backgroundColor;
    }


    public synchronized void updateAndDrawGraphics(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, size, size);
    }

    public boolean containsPoint(Point p) {
        if (p.getX() >= x && p.getX() <= (x + size)) {
            if (p.getY() >= y && p.getY() <= (y + size))
                return true;
        }

        return false;
    }

    private void setColor(Color color) {
        backgroundColor = color;
    }
}
