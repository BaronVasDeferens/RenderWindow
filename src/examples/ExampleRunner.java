import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ExampleRunner implements KeyListener {

    Effector renderFrame;

    public static void main(String... args) {
        ExampleRunner examplifier = new ExampleRunner();
    }

    private ExampleRunner() {

        renderFrame = new Effector(500, 500);
        renderFrame.registerKeyListener(this);
        renderFrame.goFullscreen();
        renderFrame.start();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                renderFrame.quit();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class Effector extends RenderFrame {

        public Effector(int width, int height) {
            super(width, height);
            sprites.add(new SnowGlobe(1366, 768, 250, 25, 50));
        }
    }

}
