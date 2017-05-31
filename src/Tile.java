import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by skot on 3/30/17.
 */
public class Tile extends Sprite implements Draggable {

    Color backgroundColor;
    int size;

    public Tile (int x, int y, int size, Color backgroundColor) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.backgroundColor = backgroundColor;
    }

    public Tile() {

    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }


    public int applyDeltaX(int deltaX) {
        x += deltaX;
        return x;
    }


    public int applyDeltaY(int deltaY) {
        y += deltaY;
        return y;
    }

    @Override
    public void setIsBackground(boolean isBackground) {

    }

    @Override
    public Point getCenter() {
        return (new Point((x + size) / 2, (y + size) / 2));
    }




    public synchronized void updateAndDrawGraphics(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, size, size);
    }

    @Override
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
