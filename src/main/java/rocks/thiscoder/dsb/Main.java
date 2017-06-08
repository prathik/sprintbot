package rocks.thiscoder.dsb;

import rocks.thiscoder.dsb.slack.Slack;

import java.util.LinkedList;
import java.util.List;

/**
 * @author prathik.raj
 */
public class Main {
    public static void main(String[] args) throws DSBException {
        final Jira jira = new Jira();
        final Slack slack = Slack.getInstance();
        List<UserDSB> userDSBList = new LinkedList<UserDSB>() {{
            add(new UserDSB(new User("prathik.raj", "prathik.raj@inmobi.com"), jira, slack));
        }};

        DSBController dsbController = new DSBController(userDSBList, 11);
        dsbController.run();
    }
}
