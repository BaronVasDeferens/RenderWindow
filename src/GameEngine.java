import java.awt.event.*;


public class GameEngine implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    RenderFrame renderFrame;

    public static void main (String ... args) {
        GameEngine gameEngine = new GameEngine();
    }

    public GameEngine() {
        renderFrame = new RenderFrame(800, 800);

        // Register listeners
        renderFrame.registerKeyListener(this);
        renderFrame.registerMouseListener(this);
        renderFrame.registerMouseMotionListener(this);
        renderFrame.registerMouseWheelListener(this);

        //renderFrame.goFullscreen();
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        renderFrame.assessClick(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        renderFrame.releaseTarget();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        renderFrame.moveTarget(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = e.getWheelRotation();
        renderFrame.zoomInOrOut(direction);
    }
}
