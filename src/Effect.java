import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by skot on 4/5/17.
 */
public abstract class Effect extends Sprite {

    protected static Random rando;

    protected int width;
    protected int height;

    protected List<Sprite> sprites;
    protected List<Sprite> deadSprites ;

    Effect(int width, int height) {
        rando = new Random();

        this.width = width;
        this.height = height;
        sprites = new ArrayList<>();
        deadSprites = new ArrayList<>();
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public abstract void updateAndPaint(Graphics graphics);

}
