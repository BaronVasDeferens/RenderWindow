import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by skot on 3/27/17.
 */
public abstract class Sprite {

    BufferedImage image = null;
    int x= 0, y = 0;                                                // location (0,0) at upper left

    protected boolean isBackground = false;
    int layerOrder = 1;                                             // Higher renders later
    protected boolean disposeOnNextUpdate = false;                  // indicates whether an object should be disposed of
    static Random rando = new Random();

    public Sprite() {}

    public Sprite (String filename) {
        image = loadImage(filename);
    }

    public abstract void setX(int x);
    public abstract void setY(int y);

    public void setIsBackground(boolean isBackground) {
        this.isBackground = isBackground;
    }

    public boolean isBackground() { return isBackground; }

    abstract void updateAndDrawGraphics(Graphics g);

    public BufferedImage loadImage(String imageName) {

        BufferedImage loaded = null;
        try (InputStream fin = getClass().getResourceAsStream("images/" + imageName)) {
            loaded = ImageIO.read(fin);
            fin.close();
            System.out.println("loaded " + imageName + ": " + loaded.getWidth() + "x" + loaded.getHeight());
            return loaded;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }

}
