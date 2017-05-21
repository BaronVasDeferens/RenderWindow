import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * Created by Skot on 5/16/2017.
 */
public class PictureTile extends Sprite implements Draggable {

    private BufferedImage originalImage;
    private Polygon polygon;                        // describes the clickable region of the image

    public PictureTile(String fileName) {
        super(fileName);
        originalImage = image;

        polygon = new Polygon();
        updatePolygon();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        updatePolygon();
    }

    @Override
    public void setY(int y) {
        this.y = y;
        updatePolygon();
    }

    public PictureTile(String fileName, int x, int y) {
        this(fileName);
        this.x = x;
        this.y = y;
        updatePolygon();
    }

    public void updatePolygon() {
        polygon = new Polygon();
        polygon.addPoint(x, y);
        polygon.addPoint(x + image.getWidth(), y);
        polygon.addPoint(x + image.getWidth(), y + image.getHeight());
        polygon.addPoint(x, y + image.getHeight());
    }

    @Override
    public boolean containsPoint(Point p) {
        return polygon.contains(p);
    }

    public Point getCenter() {
        return new Point((x + image.getWidth()) / 2, ((y + image.getHeight())) / 2);
    }



//    public void scale(BigDecimal scaleValue) {
//
//        if (!scalable)
//            return;
//
//        scale = scale.add(scaleValue);
//
//        if (scale.floatValue() <= 0)
//            scale = new BigDecimal(0.1f).setScale(2);
//
//        System.out.println("scale = " + scale.floatValue());
//
//        BufferedImage scaledImage = new BufferedImage(
//                (int) (image.getWidth() * scale.floatValue()),
//                (int) (image.getHeight() * scale.floatValue()),
//                BufferedImage.TYPE_INT_ARGB
//        );
//
//
//        Graphics2D g = scaledImage.createGraphics();
//        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
//
//        image = scaledImage;
//
//        // TODO: recalc polygon
//    }

    @Override
    public int applyDeltaX(int deltaX) {
        x += deltaX;
        updatePolygon();
        return x;
    }

    @Override
    public int applyDeltaY(int deltaY) {
        y += deltaY;
        updatePolygon();
        return y;
    }

    @Override
    public synchronized void updateAndDrawGraphics(Graphics g) {
        g.drawImage(image, x, y, null);
    }

}
