import java.awt.event.*;


public class GameEngine implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    RenderFrame renderFrame;

    private static int wheelClicks = 0;

    public static void main (String ... args) {
        GameEngine gameEngine = new GameEngine();
    }

    public GameEngine() {
        renderFrame = new RenderFrame(700, 700);

        // Register listeners
        renderFrame.registerKeyListener(this);
        renderFrame.registerMouseListener(this);
        renderFrame.registerMouseMotionListener(this);
        renderFrame.registerMouseWheelListener(this);

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
        //renderFrame.assessClick(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //renderFrame.releaseTarget(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        //renderFrame.moveTarget(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
//         wheelClicks++;
//
//        if (wheelClicks >= 1) {
//            renderFrame.zoomInOrOut(e);
//            wheelClicks = 0;
//        }
    }
}
