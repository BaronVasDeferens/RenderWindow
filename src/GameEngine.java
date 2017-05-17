import java.awt.*;
import java.awt.event.*;


public class GameEngine implements KeyListener, MouseListener, MouseMotionListener {

    RenderFrame renderFrame;

    public static void main (String ... args) {
        GameEngine gameEngine = new GameEngine();
    }

    public GameEngine() {
        renderFrame = new RenderFrame();
        renderFrame.registerKeyListener(this);
        renderFrame.registerMouseListener(this);
        //renderFrame.registerMouseMotionListener(this);
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
                renderFrame.pauseOrContinue();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {  }

    public void keyTyped(KeyEvent e) {  }

    @Override
    public void mouseClicked(MouseEvent e) {
        renderFrame.pauseOrContinue();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        System.out.println(p.getX() + "," + p.getY());
    }
}
