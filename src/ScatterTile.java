import java.awt.*;

/**
 * Created by skot on 3/31/17.
 */
public class ScatterTile extends Tile {

    int boogieFactorX, boogieFactorY;

    public ScatterTile(int x, int y, int size, Color backgroundColor) {

        super(x,y,size,backgroundColor);
        boogieFactorX = rando.nextInt(15) - 3;
        boogieFactorY = rando.nextInt(15) - 3;
    }


    @Override
    void updateAndDrawGraphics(Graphics g) {
        x += boogieFactorX;
        y += boogieFactorY;

        changeColor();

        g.setColor(backgroundColor);
        g.fillRect(x, y, size, size);
    }


    private void changeColor() {

        int red = backgroundColor.getRed();
        int green = backgroundColor.getGreen();
        int blue = backgroundColor.getBlue();

        red = (red + 1) % 255;
        //blue = (blue + 1) % 255;

        backgroundColor = new Color(red, 0, 0);

        if (red == 0)
            disposeOnNextRerender = true;
    }
}
