package rocks.thiscoder.dsb.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.model.User;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prathik.raj
 */
public class Slack {
    static Slack slack = null;

    final Map<String, SlackMessage> latestMessage = new HashMap<String, SlackMessage>();

    final SlackSession session;

    public SlackMessage getLatestMessageForUser(User user) {
        return latestMessage.get(user.getEmail());
    }

    private Slack() throws DSBException {
        Configurations configs = new Configurations();
        File propertiesFile = new File("config.properties");
        try {
            PropertiesConfiguration config = configs.properties(propertiesFile);
            session = SlackSessionFactory.createWebSocketSlackSession(config.getString("slackToken"));
            session.connect();
        } catch (ConfigurationException e) {
            throw new DSBException(e);
        } catch (IOException e) {
            throw new DSBException(e);
        }
        messagePostedListener();
    }

    public SlackSession getSession() {
        return session;
    }

    private void messagePostedListener() {
        SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener()
        {
            public void onEvent(SlackMessagePosted event, SlackSession session)
            {
                SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
                String messageContent = event.getMessageContent();
                SlackUser messageSender = event.getSender();
                latestMessage.put(event.getSender().getUserMail(),
                        new SlackMessage(event.getTimestamp(), messageContent));

            }
        };
        session.addMessagePostedListener(messagePostedListener);
    }


    public static Slack getInstance() throws DSBException {
        if(slack == null) {
            slack = new Slack();
        }
        return slack;
    }

    public void sendMessageToUser(User u, String message) {
        SlackUser user = session.findUserByEmail(u.getEmail());
        session.sendMessageToUser(user, message, null);
    }

}
