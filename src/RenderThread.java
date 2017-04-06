import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skot on 10/15/16.
 */
public class RenderThread extends Thread {

    private int SLEEP_DURATION = 15;

    private boolean isPaused = false;

    final int width, height;

    Canvas canvas;
    List<Sprite> sprites;
    private boolean continueRendering = true;

    private BufferStrategy buffer;

    GraphicsEnvironment ge;
    GraphicsDevice gd;
    GraphicsConfiguration gc;


    public RenderThread(int width, int height, List<Sprite> sprites, Canvas canvas, BufferStrategy buffer) {

        this.width = width;
        this.height = height;

        this.sprites = sprites;
        this.canvas = canvas;
        this.buffer = buffer;

        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        gc = gd.getDefaultConfiguration();

        for (GraphicsConfiguration gcfg : gd.getConfigurations()) {
            if (gcfg.getBufferCapabilities().isPageFlipping() && gcfg.isTranslucencyCapable())
                gc = gcfg;
        }
    }

    public void run() {

        while (continueRendering) {

            while (isPaused) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Graphics g = buffer.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);

            List<Sprite> aliveSprites = new ArrayList<>();

            for (Sprite spr : sprites) {
                spr.updateAndDrawGraphics(g);
            }

            // Place any post-rendering stats here
//            g.setColor(Color.GREEN);
//            g.drawString("stats", 100, 100);

            if (!buffer.contentsLost()) {
                buffer.show();
                Toolkit.getDefaultToolkit().sync();
            }



            g.dispose();
            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void pauseRender() {
        isPaused = true;
    }

    public synchronized void resumeRender() {
        isPaused = false;
        notify();
    }

    public void quit() {
        continueRendering = false;
    }

}
