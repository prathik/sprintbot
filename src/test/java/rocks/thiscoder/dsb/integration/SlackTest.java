package rocks.thiscoder.dsb.integration;

import org.testng.annotations.Test;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.model.User;
import rocks.thiscoder.dsb.slack.Slack;

/**
 * @author prathik.raj
 */
@Test
public class SlackTest {
    @Test
    void createMessage() throws DSBException {
        Slack slack = Slack.getInstance();
        User u = new User("prathik.raj", "prathik.raj@inmobi.com");
        slack.sendMessageToUser(u, "hi");
    }

    @Test
    void captureMessagePostedByUser() throws DSBException, InterruptedException {
        Slack slack = Slack.getInstance();
        Thread.sleep(100000);

    }
}
