package rocks.thiscoder.dsb.unit;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.W8Svc;

/**
 * @author prathik.raj
 */
@Test
public class W8SvcTest {
    @Test
    void shouldWaitAtleastTwoSeconds() throws InterruptedException {
        W8Svc.setW8Svc(null);
        W8Svc w8Svc = W8Svc.getInstance();
        long start = DateTime.now().getMillis();
        w8Svc.sleep(2000);
        long end = DateTime.now().getMillis();
        Assert.assertTrue((end - start) >= 2000);
    }
}
