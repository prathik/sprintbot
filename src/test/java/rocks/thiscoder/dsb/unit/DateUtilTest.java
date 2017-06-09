package rocks.thiscoder.dsb.unit;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.ctrl.DSBController;

/**
 * @author prathik.raj
 */
@Test
public class DateUtilTest {
    @Test
    void deltaForZerothHour() {
        DateTime time = new DateTime(2017, 06, 8, 17, 30);
        Assert.assertEquals(DSBController.waitTime(time, 0), 23400000);
        Assert.assertEquals(DSBController.waitTime(time, 11), 63000000);
    }
}
