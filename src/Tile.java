import java.awt.*;

/**
 * A 2D solid colored tile
 */
public class Tile extends Sprite {

    Color color;
    int size;

    public Tile (int x, int y, int size, Color color) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }


    void updateAndDrawGraphics(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }


    private void setColor(Color color) {
        this.color = color;
    }
}
