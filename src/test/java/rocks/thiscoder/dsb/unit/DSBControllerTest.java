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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

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

        W8Svc w8Svc = W8Svc.getInstance();
        W8Svc spy = spy(w8Svc);
        doNothing().when(spy).sleep(anyLong());

        W8Svc.setW8Svc(spy);

        DSBController dsbController = new DSBController(userDSBS, DateTime.now().getHourOfDay());
        dsbController.runOnce();
        W8Svc.setW8Svc(null);
    }

    @Test(expectedExceptions = DSBException.class)
    void waitException() throws DSBException, InterruptedException {
        final UserDSB userDSB = mock(UserDSB.class);
        doNothing().when(userDSB).buildQuestions();
        doNothing().when(userDSB).askQuestions();
        doReturn("Success").when(userDSB).digest();
        List<UserDSB> userDSBS = new LinkedList<UserDSB>() {{
            add(userDSB);
        }};

        W8Svc w8Svc = mock(W8Svc.class);
        doThrow(new InterruptedException("Testing")).when(w8Svc).sleep(anyLong());

        W8Svc.setW8Svc(w8Svc);

        DSBController dsbController = new DSBController(userDSBS, DateTime.now().getHourOfDay());
        dsbController.runOnce();
        W8Svc.setW8Svc(null);
    }
}
