import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;


public class RenderFrame implements WindowListener {

    Canvas canvas;
    JFrame jFrame;
    BufferStrategy buffer;
    RenderThread renderer;

    public static int PANEL_WIDTH = 600;
    public static int PANEL_HEIGHT = 600;

    private ArrayList<Sprite> sprites;

    private boolean isPaused = false;


    public RenderFrame(KeyListener listener) {

        super();

        jFrame = new JFrame();
        jFrame.addKeyListener(listener);

        canvas = new Canvas();
        sprites = new ArrayList<>();

        init();
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

        Random rando = new Random();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (j % 2 == 0)
                    sprites.add(new Tile(i * 5, j * 5, 10, new Color(rando.nextInt(240), 144, 66)));
                else
                    sprites.add(new ScatterTile(i * 5, j * 5, 10, new Color(rando.nextInt(240), 144, 66)));
            }
//            sprites.add(new Tumbler("asteroid.png", PANEL_WIDTH, PANEL_HEIGHT));
        }

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
        renderer = new RenderThread(PANEL_WIDTH, PANEL_HEIGHT, sprites, canvas, buffer);
        renderer.setPriority(Thread.MAX_PRIORITY);
    }

    public void start() {
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


    public void pauseOrUnpause() {

        if (isPaused) {

            synchronized(renderer) {
                renderer.notify();
                isPaused = false;
            }
        }
        else {

            try {
                synchronized (renderer) {
                    renderer.wait();
                    isPaused = true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void quit() {
        renderer.quit();
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
