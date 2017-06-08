package rocks.thiscoder.dsb.slack;

import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author prathik.raj
 */
@ToString
public class SlackMessage {
    @Getter
    final DateTime time;
    @Getter
    final String message;

    SlackMessage(String timestamp, String message) {
        Double posixTime = Double.valueOf(timestamp);
        BigDecimal bd = new BigDecimal(posixTime);
        bd = bd.multiply(new BigDecimal(1000)).setScale(0, RoundingMode.HALF_UP);
        time = new DateTime(bd.longValue());
        this.message = message;
    }

    public SlackMessage(DateTime timestamp, String message) {
        this.time = timestamp;
        this.message = message;
    }
}
