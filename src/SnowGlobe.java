import java.awt.*;

/**
 * Created by skot on 4/5/17.
 */
public class SnowGlobe extends Effect {

    protected final int width;
    protected final int length;
    protected final int maxFlakes;
    protected final int minSize;
    protected final int maxSize;

    SnowGlobe(int width, int length, int maxFlakes, int minSize, int maxSize) {
        super(width, length);
        this.width = width;
        this.length = length;
        this.maxFlakes = maxFlakes;
        this.minSize = minSize;
        this.maxSize = maxSize;

        for (int i = 0; i < Math.sqrt(maxFlakes); i++) {
            for (int j = 0; j < Math.sqrt(maxFlakes); j++) {
                ScatterTile st = new ScatterTile(width/2,
                        height/2,
                        rando.nextInt(maxSize) + minSize,
                        new Color(0,255,0));
                st.setBounds(0, width, 0, height);
                sprites.add(st);
            }
        }
    }




    @Override
    public void updateAndPaint(Graphics graphics) {

        for (Sprite sprite : sprites) {
            sprite.updateAndDrawGraphics(graphics);

            if (sprite.disposeOnNextUpdate)
                deadSprites.add(sprite);
        }

        sprites.removeAll(deadSprites);
        deadSprites.clear();
    }

    @Override
    public void updateAndDrawGraphics(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        updateAndPaint(g);
    }
}
