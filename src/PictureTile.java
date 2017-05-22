import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;


public class PictureTile extends Sprite implements Draggable, GloballyScalable {

    public BufferedImage originalImage;
    private Polygon polygon;                        // describes the clickable region of the image

    BigDecimal scaleFactor;
    int xAtScaleOne, yAtScaleOne;

    public PictureTile(String fileName) {
        super(fileName);
        originalImage = image;

        polygon = new Polygon();
        updatePolygon();

        scaleFactor = new BigDecimal(1.0f).setScale(2);
    }

    public PictureTile(String fileName, int x, int y) {
        this(fileName);
        this.xAtScaleOne = x;
        this.yAtScaleOne = y;
        this.x = x;
        this.y = y;
        updatePolygon();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        xAtScaleOne += (int)((scaleFactor).floatValue() * xAtScaleOne);
        updatePolygon();
    }

    @Override
    public void setY(int y) {
        this.y = y;
        yAtScaleOne += (int)((scaleFactor).floatValue() * yAtScaleOne);
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

    @Override
    public void scaleUp() {


        scaleFactor = scaleFactor.add(new BigDecimal(.10f)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(BigDecimal.ONE) >= 0)
            scaleFactor = BigDecimal.ONE;

        System.out.println("scaleFactor = " + scaleFactor.floatValue());

        System.out.print("x = " + x + " -> ");

        this.setX(x + (int)(xAtScaleOne * scaleFactor.floatValue()));
        this.setY(y + (int)(yAtScaleOne * scaleFactor.floatValue()));

        System.out.println(x);

        BufferedImage scaledImage = new BufferedImage((int) (originalImage.getWidth() * scaleFactor.floatValue()),
                (int) (originalImage.getHeight() * scaleFactor.floatValue()),
                BufferedImage.TYPE_INT_ARGB
        );


        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        image = scaledImage;

    }

    @Override
    public void scaleDown() {

        scaleFactor = scaleFactor.subtract(scaleStep).abs();

        if (scaleFactor.compareTo(new BigDecimal(0.10f)) <= 0)
            scaleFactor = new BigDecimal(0.1f);

        System.out.println("scaleFactor = " + scaleFactor.floatValue());

        System.out.print("x = " + x + " -> ");

        this.setX(x - (int)(xAtScaleOne * scaleFactor.floatValue()));
        this.setY(y - (int)(yAtScaleOne * scaleFactor.floatValue()));

        System.out.println(x);

        BufferedImage scaledImage = new BufferedImage((int) (originalImage.getWidth() * scaleFactor.floatValue()),
                (int) (originalImage.getHeight() * scaleFactor.floatValue()),
                BufferedImage.TYPE_INT_ARGB
        );


        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        image = scaledImage;
    }
}
