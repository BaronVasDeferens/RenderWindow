import java.awt.*;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;


public class DraggableBackground extends Tile implements GloballyScalable {

    ArrayList<Sprite>[] spritesByLayer;

    Draggable dragTarget = null;

    private int priorX = 0;                     // prior locations of mouse pointer (for scrolling)
    private int priorY = 0;

    BigDecimal scaleFactor;

    DraggableBackground(int width, int height) {
        super(width, height, 0, Color.WHITE);

        spritesByLayer = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            spritesByLayer[i] = new ArrayList<>();
        }

        scaleFactor = new BigDecimal(1.0f).setScale(2);

    }

    /**
     * Adds sprites to the correct layer.
     *
     * @param sprite
     * @param layer
     */
    public void addSprite(Sprite sprite, int layer) {
        spritesByLayer[layer].add(sprite);
    }


    /**
     * Darws sprites from low to high
     *
     * @param graphics
     */

    public void updateAndPaint(Graphics graphics) {

        Arrays.stream(spritesByLayer)
                .forEach((list) -> list.forEach(sprite -> sprite.updateAndDrawGraphics(graphics)));

    }

    @Override
    public void updateAndDrawGraphics(Graphics g) {
        updateAndPaint(g);
    }



    /**
     * Finds the Sprite with the highest "layer" attribute that contains the click
     * @param me
     * @return
     */
    public Clickable assessClick(MouseEvent me) {

        Point p = me.getPoint();
        Clickable assumedTarget = null;

        for (ArrayList<Sprite> list : spritesByLayer) {

            for (Sprite sprite : list) {

                if (sprite instanceof Clickable) {

                    Clickable clickedMe = (Clickable) sprite;

                    if (sprite.isBackground())
                        assumedTarget = clickedMe;
                    else if (clickedMe.containsPoint(p)) {
                        assumedTarget = clickedMe;
                    }
                }

            }
        }

        if (assumedTarget == null)
            return null;
        else
            dragTarget = (Draggable) assumedTarget;

        priorX = me.getX();
        priorY = me.getY();

        // debug: who got clicked
        System.out.println(dragTarget.toString());

        return dragTarget;
    }

    /**
     * Updates the positions of the various Sprites
     * @param e
     */
    public void moveTarget(MouseEvent e) {

        if (dragTarget != null) {

            int xDelta = priorX - e.getX();
            priorX = e.getX();
            dragTarget.applyDeltaX(-1 * xDelta);

            int yDelta = priorY - e.getY();
            priorY = e.getY();
            dragTarget.applyDeltaY(-1 * yDelta);

            // Drag & move background
            if (((Sprite) dragTarget).isBackground()) {

                // Update all other entities
                for (int i = 1; i < 10; i++) {
                    for (Sprite sprite : spritesByLayer[i]) {
                        // Center on mouse
                        sprite.x -= xDelta;
                        sprite.y -= yDelta;
                    }
                }
            }

        }

    }

    /**
     * Called when the user releases the mouse
     * @param e
     */
    public void releaseTarget(MouseEvent e) {

        priorX = e.getX();
        priorY = e.getY();
        dragTarget = null;

        for (ArrayList<Sprite> list: spritesByLayer) {
            for (Sprite sprite: list) {
                if (sprite instanceof PictureTile) {
                    PictureTile picTile = (PictureTile) sprite;
                    picTile.updatePolygon();
                }
            }
        }
    }


    @Override
    public void scaleUp() {

        scaleFactor = scaleFactor.add(new BigDecimal(.10f)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(BigDecimal.ONE) == 1)
            scaleFactor = BigDecimal.ONE;

        System.out.println(scaleFactor.floatValue());

    }

    @Override
    public void scaleDown() {

        scaleFactor = scaleFactor.subtract(new BigDecimal(.10f)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(new BigDecimal(0.1f)) <= 0)
            scaleFactor = new BigDecimal(0.1f);

        System.out.println(scaleFactor.floatValue());
    }
}
