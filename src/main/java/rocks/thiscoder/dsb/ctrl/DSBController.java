package rocks.thiscoder.dsb.ctrl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rocks.thiscoder.dsb.DSBException;

import java.util.List;

/**
 * @author prathik.raj
 */
@RequiredArgsConstructor
@Slf4j
public class DSBController {
    final List<UserDSB> userDSBs;

    public String run() throws DSBException {
        StringBuilder report = new StringBuilder();
        log.debug("Running");
        boolean first = true;
        for (UserDSB userDSB : userDSBs) {
            userDSB.buildQuestions();
            userDSB.askQuestions();
            if(first) {
                first = false;
            } else {
                report.append("\n");
            }
            report.append(userDSB.digest());

        }

        log.debug(report.toString());
        return report.toString();
    }

}
