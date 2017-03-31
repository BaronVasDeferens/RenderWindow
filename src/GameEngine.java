import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameEngine implements KeyListener {

    RenderFrame renderFrame;

    public static void main (String ... args) {
        GameEngine gameEngine = new GameEngine();
    }

    public GameEngine() {
        renderFrame = new RenderFrame(this);
        renderFrame.goFullscreen();
        renderFrame.start();
    }


    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                renderFrame.quit();
                System.exit(0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_SPACE:
                renderFrame.pauseOrUnpause();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {  }

    public void keyTyped(KeyEvent e) {  }

}
