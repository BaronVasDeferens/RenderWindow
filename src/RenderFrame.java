import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class RenderFrame implements WindowListener {

    Canvas canvas;
    JFrame jFrame;
    BufferStrategy buffer;
    RenderThread renderer;

    private int PANEL_WIDTH = 600;
    private int PANEL_HEIGHT = 600;

    private ArrayList<Sprite> sprites;

    final float SCALE_FACTOR = 0.10f;

    Sprite dragTarget = null;

    private int initialClickX, initialClickY;   // cordinates of the original click

    private int priorX = PANEL_WIDTH/2;         // prior locations of mouse pointer (for scrolling)
    private int priorY = PANEL_HEIGHT/2;

    private int subPictureX = 0;                // tracks the upper left corner of the current "window" of
    private int subPictureY = 0;                // the background image


    private boolean isPaused = false;


    public RenderFrame(int width, int height) {

        super();

        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;

        jFrame = new JFrame();
        canvas = new Canvas();
        sprites = new ArrayList<>();

//        if (System.getProperty("os.name").contains("Windows")) {
//            jFrame.setUndecorated(true);
//        }
    }

    public void registerKeyListener(KeyListener keyListener) {
        jFrame.addKeyListener(keyListener);
        canvas.addKeyListener(keyListener);
    }

    public void registerMouseListener(MouseListener mouseListener) {
        jFrame.addMouseListener(mouseListener);
        canvas.addMouseListener(mouseListener);
    }

    public void registerMouseMotionListener(MouseMotionListener mouseMotion) {
        jFrame.addMouseMotionListener(mouseMotion);
        canvas.addMouseMotionListener(mouseMotion);
    }

    public void registerMouseWheelListener(MouseWheelListener wheelListener) {
        jFrame.addMouseWheelListener(wheelListener);
        canvas.addMouseWheelListener(wheelListener);
    }

    private void init() {

        jFrame.requestFocus();
        jFrame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        jFrame.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        canvas.setBackground(Color.BLACK);
        canvas.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        canvas.setIgnoreRepaint(true);

        jFrame.add(canvas);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addWindowListener(this);
        jFrame.pack();
        jFrame.setVisible(true);

        // sprites.add(new SnowGlobe(PANEL_WIDTH, PANEL_HEIGHT, 900, 2, 40));



        PictureTile background = new PictureTile("../images/suspended_map.png");
        background.setBackground(true);
        sprites.add(background);

        sprites.add(new Tile(100,100,50, Color.RED));
        sprites.add(new Tile(200,200,50, Color.GREEN));

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
        renderer = new RenderThread(PANEL_WIDTH, PANEL_HEIGHT, sprites, canvas, buffer);
        renderer.setPriority(Thread.MAX_PRIORITY);
    }

    public void start() {
        init();
        renderer.start();
    }

    public void goFullscreen() {

        for (GraphicsDevice graphicsDev : java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {

            System.out.println(graphicsDev.toString());
            System.out.println(graphicsDev.getDisplayMode().getWidth() + "x" + graphicsDev.getDisplayMode().getHeight());

            if (graphicsDev.isFullScreenSupported()) {
                graphicsDev.setFullScreenWindow(jFrame);
                PANEL_WIDTH = graphicsDev.getDisplayMode().getWidth();
                PANEL_HEIGHT = graphicsDev.getDisplayMode().getHeight();
                init();
                return;
            }
        }
    }


    public void pauseOrContinue() {

        if (isPaused) {
            isPaused = false;
            renderer.resumeRender();
        }
        else {
            isPaused = true;
            renderer.pauseRender();
        }
    }


    /**
     * Determine whether the user has clicked on a Sprite;
     * If yes, then set that Sprite as the "dragTarget"
     * @param e
     */
    public void assessClick(MouseEvent e) {

        Point p = e.getPoint();

        for (Sprite sprite: sprites) {

            if (sprite instanceof Tile) {
                Tile tile = (Tile)sprite;

                if (tile.containsPoint(p) && dragTarget == null) {
                    initialClickX = e.getX();
                    initialClickY = e.getY();
                    priorX = e.getX();


                    dragTarget = tile;
                }
            }

            else if (sprite instanceof PictureTile) {
                priorX = e.getX();
                priorY = e.getY();

                PictureTile bg = (PictureTile) sprite;
                dragTarget = bg;
            }

        }

    }

    /**
     * Moves the "dragTarget" around with the mouse. If the target of the click is a Tile, move the tile;
     * If the target is a background PictureTile, scroll around the image
     * @param e
     */
    public void moveTarget(MouseEvent e) {

        if (dragTarget != null) {

            if (dragTarget instanceof Tile) {
                Tile tile = (Tile) dragTarget;
                // Center on mouse
                tile.x = e.getX() - (tile.size/2);
                tile.y = e.getY() - (tile.size/2);
            }
            // drag background
            else if (dragTarget instanceof PictureTile) {

                PictureTile bg = (PictureTile) dragTarget;
                if (bg.isBackground()) {

                    int xDelta = priorX - e.getX();
                    priorX = e.getX();
                    bg.x -= xDelta;

                    int yDelta = priorY - e.getY();
                    priorY = e.getY();
                    bg.y -= yDelta;

                }
            }

        }
    }

    /**
     * When the user releases the button, release the "dragTarget"
     */
    public void releaseTarget(MouseEvent e) {
        if (dragTarget instanceof PictureTile) {
            PictureTile bg = (PictureTile) dragTarget;
            priorX = e.getX();
            priorY = e.getY();
        }

        dragTarget = null;
    }

    /**
     * Zooms the entities on screen in or out, depending on whether zoomFactor is positive (in) or negative (out)
     * @param zoomFactor
     */
    public void zoomInOrOut(int zoomFactor) {

        for (Sprite sprite : sprites) {
            sprite.scale(zoomFactor * SCALE_FACTOR);
        }

    }


    public void quit() {
        renderer.quit();
        canvas = null;
        jFrame.dispose();
    }


    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        renderer.quit();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        renderer.quit();
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }



}
