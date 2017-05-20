import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by skot on 5/19/17.
 */
public class DraggableBackground extends Tile {

    ArrayList<Sprite>[] spritesByLayer;

    Sprite dragTarget = null;

    private int initialClickX, initialClickY;   // cordinates of the original click

    private int priorX = 0;         // prior locations of mouse pointer (for scrolling)
    private int priorY = 0;

    private int subPictureX = 0;                // tracks the upper left corner of the current "window" of
    private int subPictureY = 0;                // the background image

    DraggableBackground(int width, int height) {
        super(width, height, 0, Color.WHITE);

        spritesByLayer = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            spritesByLayer[i] = new ArrayList<>();
        }

    }

    /**
     * Adds sprites to the correct layer.
     *
     * @param sprite
     * @param layer
     */
    public void addSprite(Sprite sprite, int layer) {
        spritesByLayer[layer].add(sprite);
        System.out.println("[" + layer + "] : " + spritesByLayer[layer].size());
    }


    /**
     * Darws sprites from low to high
     *
     * @param graphics
     */

    public void updateAndPaint(Graphics graphics) {

        for (int i = 0; i < 10; i++) {
            for (Sprite sprite : spritesByLayer[i]) {
                sprite.updateAndDrawGraphics(graphics);
            }
        }
    }

    @Override
    public void updateAndDrawGraphics(Graphics g) {
        updateAndPaint(g);
    }

    @Override
    public void scale(float scaleValue) {

    }


    public Sprite assessClick(MouseEvent me) {

        Point p = me.getPoint();

        System.out.println(me.getX() + ":" + me.getY());

        Sprite assumedTarget = null;

        for (ArrayList<Sprite> list : spritesByLayer) {

            for (Sprite sprite : list) {

                if (sprite instanceof PictureTile) {
                    assumedTarget = sprite;
                }
                else if (sprite instanceof Tile) {

                    Tile tile = (Tile) sprite;

                    if (tile.containsPoint(p))
                        assumedTarget = tile;
                }
            }
        }

        if (assumedTarget == null)
            return null;
        else
            dragTarget = assumedTarget;

        initialClickX = me.getX();
        initialClickY = me.getY();
        priorX = me.getX();
        priorY = me.getY();

        System.out.println(dragTarget.toString());

        return dragTarget;
    }

    public void moveTarget(MouseEvent e) {

        if (dragTarget != null) {

            // Drag & move background
            if (dragTarget instanceof PictureTile) {
                PictureTile bg = (PictureTile) dragTarget;

                int xDelta = priorX - e.getX();
                priorX = e.getX();
                bg.x -= xDelta;

                int yDelta = priorY - e.getY();
                priorY = e.getY();
                bg.y -= yDelta;

                // Update all other entities
                for (int i = 1; i < 10; i++) {
                    for (Sprite sprite: spritesByLayer[i]) {
                        Tile tile = (Tile) sprite;
                        // Center on mouse
                        tile.x -= xDelta;
                        tile.y -= yDelta;
                    }
                }

            }
            // drag & move foreground objects
            else {

                Tile tile = (Tile) dragTarget;
                // Center on mouse
                tile.x = e.getX() - (tile.size / 2);
                tile.y = e.getY() - (tile.size / 2);
            }
        }

    }

    public void releaseTarget(MouseEvent e) {

        priorX = e.getX();
        priorY = e.getY();
        dragTarget = null;
    }


}
