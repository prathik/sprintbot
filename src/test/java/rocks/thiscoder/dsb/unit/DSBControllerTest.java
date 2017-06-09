package rocks.thiscoder.dsb.unit;

import org.joda.time.DateTime;
import org.testng.annotations.Test;
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
    void buildDigestNow() throws DSBException {
        final UserDSB userDSB = mock(UserDSB.class);
        doNothing().when(userDSB).buildQuestions();
        doNothing().when(userDSB).askQuestions();
        doReturn("Success").when(userDSB).digest();
        List<UserDSB> userDSBS = new LinkedList<UserDSB>() {{
            add(userDSB);
        }};

        DSBController dsbController = new DSBController(userDSBS, DateTime.now().getHourOfDay());
        dsbController.run();
    }
}
