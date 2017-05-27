import java.awt.*;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;


public class DraggableBackground extends Tile implements GloballyScalable {

    // Renders sprites from low layer number(0) to high (9)
    ArrayList<PictureTile>[] spritesByLayer;

    PictureTile dragTarget = null;

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
     * @param layer
     */
    public void addSprite(PictureTile pTile, int layer) {
        spritesByLayer[layer].add(pTile);
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
        PictureTile assumedTarget = null;

        for (ArrayList<PictureTile> list : spritesByLayer) {

            for (PictureTile pTile : list) {

                if (pTile.isBackground())
                    assumedTarget = pTile;
                else if (pTile.containsPoint(p)) {
                    assumedTarget = pTile;
                }
            }
        }

        if (assumedTarget == null)
            return null;
        else
            dragTarget = assumedTarget;

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

            if (dragTarget.isBackground()) {

                int xDelta = priorX - e.getX();
                priorX = e.getX();
                dragTarget.applyX(-xDelta);

                int yDelta = priorY - e.getY();
                priorY = e.getY();
                dragTarget.applyY(-yDelta);

                // Update all other entities, skipping layer 0
                for (int i = 1; i < 10; i++) {
                    for (PictureTile pTile : spritesByLayer[i]) {
                        pTile.applyX(-xDelta);
                        pTile.applyY(-yDelta);
                    }
                }
            }

            else {
                int xDelta = priorX - e.getX();
                priorX = e.getX();
                dragTarget.applyX(-xDelta);

                int yDelta = priorY - e.getY();
                priorY = e.getY();
                dragTarget.applyY(-yDelta);
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

        for (ArrayList<PictureTile> list: spritesByLayer) {
            for (Sprite sprite: list) {
                if (sprite instanceof PictureTile) {
                    PictureTile picTile = (PictureTile) sprite;
                    picTile.updatePolygon();
                }
            }
        }
    }


    @Override
    public void scaleUp(MouseEvent e) {

        scaleFactor = scaleFactor.add(scaleStep).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(BigDecimal.ONE) == 1)
            scaleFactor = BigDecimal.ONE;

        priorX = e.getX();
        priorY = e.getY();

        for (ArrayList<PictureTile> list: spritesByLayer) {
            for (Sprite sprite: list) {
                if (sprite instanceof GloballyScalable) {
                    GloballyScalable globScale = (GloballyScalable) sprite;
                    globScale.scaleUp(e);
                }
            }
        }

    }

    @Override
    public void scaleDown(MouseEvent e) {

        scaleFactor = scaleFactor.subtract(scaleStep).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (scaleFactor.compareTo(new BigDecimal(0.1f)) <= 0)
            scaleFactor = new BigDecimal(0.1f);

        priorX = e.getX();
        priorY = e.getY();

        for (ArrayList<PictureTile> list: spritesByLayer) {
            for (Sprite sprite: list) {
                if (sprite instanceof GloballyScalable) {
                    GloballyScalable globScale = (GloballyScalable) sprite;
                    globScale.scaleDown(e);
                }
            }
        }
    }
}
