package rocks.thiscoder.dsb.unit;

import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.ctrl.DSBController;
import rocks.thiscoder.dsb.ctrl.UserDSB;

import java.util.LinkedList;
import java.util.List;

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


        DSBController dsbController = new DSBController(userDSBS);
        Assert.assertEquals(dsbController.run(), "Success");

        final UserDSB userDSB1 = mock(UserDSB.class);
        doNothing().when(userDSB1).buildQuestions();
        doNothing().when(userDSB1).askQuestions();
        doReturn("Success 1").when(userDSB1).digest();
        userDSBS.add(userDSB1);

        dsbController = new DSBController(userDSBS);
        Assert.assertEquals(dsbController.run(), "Success\nSuccess 1");
    }

}
