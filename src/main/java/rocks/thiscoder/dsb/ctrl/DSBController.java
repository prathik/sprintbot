package rocks.thiscoder.dsb.ctrl;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.W8Svc;
import rocks.thiscoder.dsb.ctrl.UserDSB;

import java.util.List;

/**
 * @author prathik.raj
 */
@RequiredArgsConstructor
public class DSBController {
    final List<UserDSB> userDSBs;
    final Integer startHour;

    public static long waitTime(DateTime current, int startHour) {
        DateTime nextDay = current.plusDays(1);
        nextDay = nextDay.withTime(startHour,0,0,0);
        return (nextDay.getMillis() - current.getMillis());
    }

    public void runOnce() throws DSBException {
        if (DateTime.now().getHourOfDay() == startHour) {
            StringBuilder report = new StringBuilder();
            System.out.println("Running");
            for (UserDSB userDSB : userDSBs) {
                userDSB.buildQuestions();
                userDSB.askQuestions();
                report.append(userDSB.digest());
                report.append("\n");
            }
            System.out.println("report.toString() = " + report.toString());
        }

        try {
            System.out.println("Sleeping");
            W8Svc.getInstance().sleep(waitTime(DateTime.now(), startHour));
        } catch (InterruptedException e) {
            throw new DSBException(e);
        }
    }

    public void run() throws DSBException {
        while (true) {
            runOnce();
        }
    }
}
