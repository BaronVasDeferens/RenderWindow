import java.awt.*;

/**
 * Created by skot on 5/21/17.
 */
public interface Draggable extends Clickable {

    Point getCenter();

    int applyDeltaY(int deltaY);
    int applyDeltaX(int deltaX);

}
