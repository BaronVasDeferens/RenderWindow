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

    public static int PANEL_WIDTH = 600;
    public static int PANEL_HEIGHT = 600;

    private ArrayList<Sprite> sprites;

    Tile dragTarget = null;

    private boolean isPaused = false;


    public RenderFrame() {

        super();

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

    public void assessClick(MouseEvent e) {

        Point p = e.getPoint();

        for (Sprite sprite: sprites) {
            Tile tile = (Tile)sprite;
            if (tile.containsPoint(p) && dragTarget == null)
                dragTarget = tile;
        }

    }

    public void moveTarget(MouseEvent e) {

        if (dragTarget != null) {
            dragTarget.x = e.getX();
            dragTarget.y = e.getY();
        }
    }


    public void releaseTarget() {
        dragTarget = null;
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
