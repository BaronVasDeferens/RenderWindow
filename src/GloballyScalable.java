import java.math.BigDecimal;

/**
 * Created by skot on 5/21/17.
 */
public interface GloballyScalable {

    BigDecimal scaleStep = new BigDecimal(0.10f).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    void scaleUp();
    void scaleDown();
}
