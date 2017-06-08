package rocks.thiscoder.dsb;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

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

    public void run() throws DSBException {
        while (true) {
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
                Thread.sleep(waitTime(DateTime.now(), startHour));
            } catch (InterruptedException e) {
                throw new DSBException(e);
            }
        }
    }
}
