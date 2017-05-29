import java.awt.*;

/**
 * Created by skot on 3/31/17.
 */
public class ScatterTile extends Tile {

    private int boogieFactorX = 0, boogieFactorY = 0;
    private int lowerBound, upperBound, leftBound, rightBound;

    public ScatterTile(int x, int y, int size, Color backgroundColor) {

        super(x,y,size,backgroundColor);
        resetBoogieFactors();
    }


    public void setBounds(int left, int right, int top, int bottom) {
        this.upperBound = top;
        this.lowerBound = bottom;
        this.leftBound = left;
        this.rightBound = right;
    }

    public boolean checkBounds() {
        return (x < leftBound) || (x > rightBound) || (y < upperBound) || (y > lowerBound);
    }

    @Override
    public boolean containsPoint(Point p) {
        return false;
    }

    @Override
    public synchronized void updateAndDrawGraphics(Graphics g) {

        x += boogieFactorX;
        y += boogieFactorY;

//        changeColor();

        if (checkBounds()) {
            resetBoogieFactors();
        }

        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public boolean containsPoint(Point p) {

        if ((p.getX() >= x) && (p.getX() <= x + size)){
            if ((p.getY() >= y) && (p.getY() <= y + size)) {
                return true;
            }
        }
        return false;
    }

    public void resetBoogieFactors() {

        boogieFactorX = rando.nextInt(8) - rando.nextInt(7);
        boogieFactorY = rando.nextInt(8) - rando.nextInt(7);

        if  (boogieFactorX == 0 && boogieFactorY == 0) {

            while (boogieFactorX == 0) {
                boogieFactorX = rando.nextInt(8) - rando.nextInt(7);
            }
            while (boogieFactorY == 0) {
                boogieFactorY = rando.nextInt(8) - rando.nextInt(7);
            }
        }
    }

    public void changeColor() {
//
//        int red = color.getRed();
//        int green = color.getGreen();
//        int blue = color.getBlue();
//
//        red = red - 1;
//        blue = blue - 2;
//
//        if (red <= 0) {
//            red = 0;
//            disposeOnNextUpdate = true;
//        }

        color = new Color(255, 0, 0);
    }
}
