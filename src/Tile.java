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


    void updateAndDrawGraphics(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, size, size);
    }


    private void setColor(Color color) {
        backgroundColor = color;
    }
}
