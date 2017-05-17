import java.awt.*;

/**
 * Created by skot on 4/8/17.
 */
public class MouseRepellant extends SnowGlobe {

    MouseRepellant(int width, int height, int maxFlakes, int minSize, int maxSize) {
        super(width, height, maxFlakes, minSize, maxSize);
    }

    @Override
    public void updateAndDrawGraphics(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        updateAndPaint(g);
    }

    @Override
    public void updateAndPaint(Graphics graphics) {

        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();

        for (Sprite sprite : sprites) {
            ScatterTile scatterSprite  = (ScatterTile)sprite;
            if (scatterSprite.containsPoint(p)) {
                scatterSprite.changeColor();
                scatterSprite.resetBoogieFactors();
                Sound s = new Sound("txting_type_main.wav");
                s.start();
            }

            sprite.updateAndDrawGraphics(graphics);

            if (sprite.disposeOnNextUpdate)
                deadSprites.add(sprite);
        }

        sprites.removeAll(deadSprites);
        deadSprites.clear();
    }
}
