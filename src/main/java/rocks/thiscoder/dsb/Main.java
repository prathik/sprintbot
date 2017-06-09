package rocks.thiscoder.dsb;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import rocks.thiscoder.dsb.actionhandler.CommonHandlersFactory;
import rocks.thiscoder.dsb.ctrl.DSBController;
import rocks.thiscoder.dsb.ctrl.UserDSB;
import rocks.thiscoder.dsb.jira.Jira;
import rocks.thiscoder.dsb.model.User;
import rocks.thiscoder.dsb.slack.Slack;

import java.util.LinkedList;
import java.util.List;

/**
 * @author prathik.raj
 */
public class Main {
    public static void main(String[] args) {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml()
                                .setFileName("users.xml"));

        try {
            final Jira jira = Jira.getInstance();
            final Slack slack = Slack.getInstance();
            XMLConfiguration config = builder.getConfiguration();
            List<Object> usernames =  config.getList("user.name");
            List<Object> emails =  config.getList("user.email");
            List<UserDSB> userDSBList = new LinkedList<UserDSB>();

            for(int i = 0; i<usernames.size(); i++) {
                userDSBList.add(new UserDSB(new User(usernames.get(i).toString(),
                        emails.get(i).toString()),
                        jira,
                        slack,
                        CommonHandlersFactory.getCommonHandlers(jira)
                        ));
            }
            DSBController dsbController = new DSBController(userDSBList, 11);
            dsbController.run();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (DSBException e) {
            e.printStackTrace();
        }
    }
}
