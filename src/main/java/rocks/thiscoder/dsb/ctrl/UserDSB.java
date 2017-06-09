package rocks.thiscoder.dsb.ctrl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.joda.time.DateTime;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.actionhandler.AnswerActionHandler;
import rocks.thiscoder.dsb.actionhandler.CommonHandlersFactory;
import rocks.thiscoder.dsb.actionhandler.impl.ResolveOnYes;
import rocks.thiscoder.dsb.jira.Jira;
import rocks.thiscoder.dsb.model.User;
import rocks.thiscoder.dsb.slack.Slack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author prathik.raj
 */

@RequiredArgsConstructor
public class UserDSB {
    final User user;
    final Jira jira;
    final Slack slack;
    final List<AnswerActionHandler> answerActionHandlers;

    @Getter
    @Setter
    List<Question> questions;

    public void askQuestions() throws DSBException {
        for(Question question: getQuestions()) {
            question.ask();
            question.poll();
            question.persist();
        }
    }

    public void buildQuestions() throws DSBException {
        List<Question> questions = new LinkedList<Question>();
        try {
            for(Issue i: jira.getOpenIssuesForUser(user.getUsername())) {
                questions.add(new Question(i, user, DateTime.now(), answerActionHandlers,
                        slack));
            }
        } catch (JiraException e) {
            throw new DSBException(e);
        }
        setQuestions(questions);
    }

    public String digest() {
        StringBuilder sb = new StringBuilder();
        if(questions != null) {
            sb.append("Updates of " + user.getUsername());
            sb.append("\n\n");
            for(Question q: questions) {
                sb.append(q);
            }
        }
        return sb.toString();
    }

}
