package rocks.thiscoder.dsb.integration;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.testng.Assert;
import org.testng.annotations.Test;
import rocks.thiscoder.dsb.jira.Jira;

import java.util.List;

/**
 * @author prathik.raj
 */
@Test
public class JiraTest {
    @Test
    void getJiraIssues() throws JiraException {
        Jira jira = Jira.getInstance();
        List<Issue> issues = jira.getOpenIssuesForUser("prathik.raj");
        System.out.println("issues = " + issues);
        Assert.assertTrue(issues.size() > 0);
        for(int i = 0; i < issues.size(); i++) {
            Assert.assertTrue(issues.get(i).getStatus().toString().equals("Open")
                    || issues.get(i).getStatus().toString().equals("In Progress"));
        }

    }
}
