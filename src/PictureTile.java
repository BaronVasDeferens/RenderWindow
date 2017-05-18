import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * Created by Skot on 5/16/2017.
 */
public class PictureTile extends Sprite {

    private BufferedImage originalImage;

    private boolean isBackground = false;

    public PictureTile(String fileName) {
        super(fileName);
        originalImage = image;
    }

    @Override
    public void scale(float scaleValue) {

        if (!scalable)
            return;

        scale = scale.add(new BigDecimal(scaleValue));

        if (scale.floatValue() <= 0 )
            scale = new BigDecimal(0.1f).setScale(2);

        System.out.println("scale = " + scale.floatValue());

        BufferedImage scaledImage = new BufferedImage(
                (int) (image.getWidth() * scale.floatValue()),
                (int) (image.getHeight() * scale.floatValue()),
                BufferedImage.TYPE_INT_ARGB
        );


        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);

        image = scaledImage;

    }

    @Override
    public synchronized void updateAndDrawGraphics(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean background) {
        isBackground = background;
    }
}
