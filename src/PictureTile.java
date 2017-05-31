import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;


public class PictureTile extends Sprite implements Draggable, GloballyScalable {

    public BufferedImage originalImage;
    private Polygon polygon;                        // describes the clickable region of the image

    BigDecimal scaleFactor = BigDecimal.ONE.setScale(2);
    int xAtScaleOne, yAtScaleOne;

    public PictureTile(String fileName) {
        super(fileName);
        originalImage = image;
        polygon = new Polygon();
        updatePolygon();
    }

    public PictureTile(String fileName, int x, int y) {
        this(fileName);
        this.xAtScaleOne = x;
        this.yAtScaleOne = y;
        this.x = x;
        this.y = y;
        updatePolygon();
    }

    public int getX() {
        return (int) (xAtScaleOne * scaleFactor.floatValue());
    }

    public int getY() {
        return (int) (yAtScaleOne * scaleFactor.floatValue());
    }

    @Override
    public void setX(int x) {
        xAtScaleOne += (int) ((BigDecimal.ONE.subtract(scaleFactor).floatValue() * xAtScaleOne));
        updatePolygon();
    }

    @Override
    public void setY(int y) {
        yAtScaleOne += (int) ((BigDecimal.ONE.subtract(scaleFactor).floatValue() * yAtScaleOne));
        updatePolygon();
    }

    public void applyX(int x) {
        xAtScaleOne += (int) (x / scaleFactor.floatValue());
        updatePolygon();
    }

    public void applyY(int y) {
        yAtScaleOne += (int) (y / scaleFactor.floatValue());
        updatePolygon();
    }

    public void updatePolygon() {
        polygon = new Polygon();
        polygon.addPoint(getX(), getY());
        polygon.addPoint(getX() + image.getWidth(), getY());
        polygon.addPoint(getX() + image.getWidth(), getY() + image.getHeight());
        polygon.addPoint(getX(), getY() + image.getHeight());
    }

    @Override
    public boolean containsPoint(Point p) {
        return polygon.contains(p);
    }

    public Point getCenter() {
        return new Point((getX() + image.getWidth()) / 2, ((getY() + image.getHeight())) / 2);
    }

    @Override
    public synchronized void updateAndDrawGraphics(Graphics g) {
        g.drawImage(image, getX(), getY(), null);
    }

    @Override
    public void scaleUp(MouseEvent e) {

        scaleFactor = scaleFactor.add(new BigDecimal(.10f)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(BigDecimal.ONE) >= 0)
            scaleFactor = BigDecimal.ONE;

        BufferedImage scaledImage = new BufferedImage((int) (originalImage.getWidth() * scaleFactor.floatValue()),
                (int) (originalImage.getHeight() * scaleFactor.floatValue()),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        image = scaledImage;
        updatePolygon();

    }

    @Override
    public void scaleDown(MouseEvent e) {

        scaleFactor = scaleFactor.subtract(scaleStep).abs();

        if (scaleFactor.compareTo(new BigDecimal(0.10f)) <= 0)
            scaleFactor = new BigDecimal(0.1f);

        BufferedImage scaledImage = new BufferedImage((int) (originalImage.getWidth() * scaleFactor.floatValue()),
                (int) (originalImage.getHeight() * scaleFactor.floatValue()),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        image = scaledImage;
        updatePolygon();
    }
}
