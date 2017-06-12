package rocks.thiscoder.dsb.actionhandler.impl;

import lombok.RequiredArgsConstructor;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.actionhandler.AnswerActionHandler;
import rocks.thiscoder.dsb.jira.Jira;

/**
 * @author prathik.raj
 */
@RequiredArgsConstructor
public class InProgressHandler implements AnswerActionHandler {
    final Jira jira;

    public boolean takeAction(String answer, Issue issue) throws DSBException {
        if(answer.equals("In progress") || answer.equals("In Progress")) {
            try {
                jira.startProgress(issue);
            } catch (JiraException e) {
                throw new DSBException(e);
            }
            return true;
        }
        return false;
    }
}
