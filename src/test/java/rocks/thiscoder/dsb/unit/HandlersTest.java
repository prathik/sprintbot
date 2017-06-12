package rocks.thiscoder.dsb.unit;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.actionhandler.AnswerActionHandler;
import rocks.thiscoder.dsb.actionhandler.CommonHandlersFactory;
import rocks.thiscoder.dsb.actionhandler.impl.InProgressHandler;
import rocks.thiscoder.dsb.actionhandler.impl.ResolveOnYes;
import rocks.thiscoder.dsb.jira.Jira;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author prathik.raj
 */

@Test
public class HandlersTest {
    @Test
    void resolveOnYesTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doNothing().when(jira).resolveIssue(issue);
        ResolveOnYes resolveOnYes = new ResolveOnYes(jira);
        Assert.assertTrue(resolveOnYes.takeAction("Yes", issue));
    }

    @Test
    void dontResolveOnYesTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doNothing().when(jira).resolveIssue(issue);
        ResolveOnYes resolveOnYes = new ResolveOnYes(jira);
        Assert.assertFalse(resolveOnYes.takeAction("No", issue));
    }

    @Test(expectedExceptions = DSBException.class)
    void resolveOnYesExceptionTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doThrow(new JiraException("Invalid Jira")).when(jira).resolveIssue(issue);
        ResolveOnYes resolveOnYes = new ResolveOnYes(jira);
        Assert.assertTrue(resolveOnYes.takeAction("Yes", issue));
    }

    @Test
    void iPhPostitiveTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doNothing().when(jira).startProgress(issue);
        InProgressHandler inProgressHandler = new InProgressHandler(jira);
        Assert.assertTrue(inProgressHandler.takeAction("In Progress", issue));
        Assert.assertTrue(inProgressHandler.takeAction("In progress", issue));
    }

    @Test
    void iPHNegativeTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doNothing().when(jira).startProgress(issue);
        InProgressHandler inProgressHandler = new InProgressHandler(jira);
        Assert.assertFalse(inProgressHandler.takeAction("No", issue));
    }

    @Test(expectedExceptions = DSBException.class)
    void iPHOnYesExceptionTest() throws JiraException, DSBException {
        Issue issue = mock(Issue.class);
        Jira jira = mock(Jira.class);
        doThrow(new JiraException("Invalid Jira")).when(jira).startProgress(issue);
        InProgressHandler inProgressHandler = new InProgressHandler(jira);
        Assert.assertTrue(inProgressHandler.takeAction("In Progress", issue));
    }


    @Test
    void factoryTest() {
        Jira jira = mock(Jira.class);
        Jira jira2 = mock(Jira.class);
        List<AnswerActionHandler> answerActionHandlers = CommonHandlersFactory.getCommonHandlers(jira);
        Assert.assertEquals(answerActionHandlers, CommonHandlersFactory.getCommonHandlers(jira));
        Assert.assertNotEquals(answerActionHandlers, CommonHandlersFactory.getCommonHandlers(jira2));
    }

}
