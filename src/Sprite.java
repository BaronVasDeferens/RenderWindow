import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;


public abstract class Sprite {

    BufferedImage image = null;
    int x= 0, y = 0;                                                // location (0,0) at upper left

    protected boolean isBackground = false;
    int layerOrder = 1;                                             // Higher renders later
    protected boolean disposeOnNextUpdate = false;                  // indicates whether an object should be disposed of
    static Random rando = new Random();

    public Sprite() {}

    public Sprite(final BufferedImage image) {
        this.image = image;
    }

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
        try (InputStream fin = getClass().getResourceAsStream(imageName)) {
            loaded = ImageIO.read(fin);
            fin.close();
            return loaded;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }

    public void setImage(final BufferedImage image) {

    }

}
