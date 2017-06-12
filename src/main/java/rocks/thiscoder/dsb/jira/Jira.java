package rocks.thiscoder.dsb.jira;

import net.rcarz.jiraclient.*;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.List;

/**
 * @author prathik.raj
 */
public class Jira {
    final BasicCredentials creds;
    final JiraClient jira;
    private static Jira jiraObj;

    public static Jira getInstance() {
        if(jiraObj == null) {
            jiraObj = new Jira();
        }
        return jiraObj;
    }

    private Jira() {

        JiraClient jira1;
        BasicCredentials creds1;
        Configurations configs = new Configurations();
        File propertiesFile = new File("config.properties");
        try {
            PropertiesConfiguration config = configs.properties(propertiesFile);
            creds1 = new BasicCredentials(config.getString("username"), config.getString("password"));
            jira1 = new JiraClient(config.getString("endpoint"), creds1);
        } catch (ConfigurationException e) {
            creds1 = null;
            jira1 = null;
            e.printStackTrace();
        }


        jira = jira1;
        creds = creds1;
    }

    public List<Issue> getOpenIssuesForUser(String userName) throws JiraException {
        String jql = String.format("assignee = %s AND status in (Open, \"In Progress\") AND Sprint in " +
                "openSprints() AND (\"Start Date\" <= now() OR \"Start Date\" = NULL) " +
                "ORDER BY createdDate DESC", userName);
        return jira.searchIssues(jql).issues;
    }

    public void resolveIssue(Issue i) throws JiraException {
        i.transition().field(Field.RESOLUTION, "Fixed").execute("Resolve Issue");
    }

    public void startProgress(Issue i) throws JiraException {
        i.transition().execute("Start Progress");
    }

    public JiraClient getJira() {
        return jira;
    }
}
