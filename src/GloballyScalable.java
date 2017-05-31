import java.awt.event.MouseEvent;
import java.math.BigDecimal;

/**
 * Created by skot on 5/21/17.
 */
public interface GloballyScalable {

    BigDecimal scaleStep = new BigDecimal(0.10f).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    void scaleUp(MouseEvent e);
    void scaleDown(MouseEvent e);
}
