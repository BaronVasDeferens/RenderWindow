import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class RenderFrame implements WindowListener {

    private Canvas canvas;
    private JFrame jFrame;
    private BufferStrategy buffer;
    private RenderThread renderer;

    protected int PANEL_WIDTH = 600;
    protected int PANEL_HEIGHT = 600;

    protected ArrayList<Sprite> sprites;

    private boolean isPaused = false;

    public RenderFrame(int width, int height) {

        super();

        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;

        jFrame = new JFrame();
        canvas = new Canvas();
        sprites = new ArrayList<>();

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

    public void setSleepDuration(int sleepDuration) {
        if (renderer != null) {
            renderer.setSleepDuration(sleepDuration);
        }
    }

    public void start() {

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
        renderer = new RenderThread(PANEL_WIDTH, PANEL_HEIGHT, sprites, canvas, buffer);
        renderer.setPriority(Thread.MAX_PRIORITY);

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

    public void quit() {
        renderer.quit();
        canvas = null;
        jFrame.dispose();
    }
}
