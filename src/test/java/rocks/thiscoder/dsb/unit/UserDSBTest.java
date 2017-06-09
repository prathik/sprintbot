package rocks.thiscoder.dsb.unit;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.*;
import rocks.thiscoder.dsb.actionhandler.AnswerActionHandler;
import rocks.thiscoder.dsb.actionhandler.CommonHandlersFactory;
import rocks.thiscoder.dsb.ctrl.Question;
import rocks.thiscoder.dsb.ctrl.UserDSB;
import rocks.thiscoder.dsb.jira.Jira;
import rocks.thiscoder.dsb.model.User;
import rocks.thiscoder.dsb.slack.Slack;
import rocks.thiscoder.dsb.slack.SlackMessage;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author prathik.raj
 */
@Test
public class UserDSBTest {
    @Test
    void noQuestions() throws DSBException, JiraException {
        Jira jira = mock(Jira.class);
        when(jira.getOpenIssuesForUser("prathik.raj")).thenReturn(new LinkedList<Issue>() {{
        }});
        User u = new User("prathik.raj", "prathik.raj@inmobi.com");
        Slack slack = mock(Slack.class);
        UserDSB userDSB = new UserDSB(u, jira, slack, CommonHandlersFactory.getCommonHandlers(jira));
        userDSB.buildQuestions();
        List<Question> questions = userDSB.getQuestions();
        Assert.assertTrue(questions.size() == 0);
    }

    @Test
    void askAllQuestions() throws DSBException, JiraException {
        final Issue issue = mock(Issue.class);
        when(issue.getSummary()).thenReturn("DSB Bot");

        Jira jira = mock(Jira.class);
        when(jira.getOpenIssuesForUser("prathik.raj")).thenReturn(new LinkedList<Issue>() {{
            add(issue);
        }});
        doNothing().when(jira).resolveIssue(issue);

        User u = new User("prathik.raj", "prathik.raj@inmobi.com");

        Slack slack = mock(Slack.class);
        doReturn(new SlackMessage(DateTime.now().plusDays(1), "Yup"))
                .when(slack).getLatestMessageForUser(u);

        List<AnswerActionHandler> answerActionHandlers = CommonHandlersFactory.getCommonHandlers(jira);

        final Question question = new Question(issue, u, DateTime.now().minusDays(1),
                answerActionHandlers, slack);
        final Question spyQuestion = spy(question);
        doNothing().when(spyQuestion).ask();
        doNothing().when(spyQuestion).persist();

        UserDSB userDSB = new UserDSB(u, jira, slack, answerActionHandlers);
        UserDSB spy = spy(userDSB);
        doNothing().when(spy).buildQuestions();
        spy.setQuestions(new LinkedList<Question>() {{
            add(spyQuestion);
        }});

        spy.askQuestions();

        Assert.assertEquals(spy.digest(), "Updates of prathik.raj\n" +
                "\n" +
                "DSB Bot done?\n" +
                "Yup\n\n");
    }
}
