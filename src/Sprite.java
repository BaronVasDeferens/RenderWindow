import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by skot on 3/27/17.
 */
public abstract class Sprite {

    protected BufferedImage image;
    protected int x, y;
    protected boolean disposeOnNextUpdate = false;

    static Random rando = new Random();

    public Sprite() {}

    public Sprite (String filename) {
        image = loadImage(filename);
    }

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
