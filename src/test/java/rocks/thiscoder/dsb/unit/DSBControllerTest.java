package rocks.thiscoder.dsb.unit;

import org.joda.time.DateTime;
import org.mockito.Matchers;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.W8Svc;
import rocks.thiscoder.dsb.ctrl.DSBController;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.ctrl.UserDSB;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author prathik.raj
 */
@Test
public class DSBControllerTest {
    @Test
    void buildDigestNow() throws DSBException, InterruptedException {
        final UserDSB userDSB = mock(UserDSB.class);
        doNothing().when(userDSB).buildQuestions();
        doNothing().when(userDSB).askQuestions();
        doReturn("Success").when(userDSB).digest();
        List<UserDSB> userDSBS = new LinkedList<UserDSB>() {{
            add(userDSB);
        }};

        W8Svc w8Svc = mock(W8Svc.class);
        doNothing().when(w8Svc).sleep(Matchers.anyLong());
        DSBController dsbController = new DSBController(userDSBS, DateTime.now().getHourOfDay(), w8Svc);
        dsbController.runOnce();
    }
}
