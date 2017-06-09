package rocks.thiscoder.dsb.ctrl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.joda.time.DateTime;
import rocks.thiscoder.dsb.DSBException;
import rocks.thiscoder.dsb.actionhandler.AnswerActionHandler;
import rocks.thiscoder.dsb.jira.Jira;
import rocks.thiscoder.dsb.model.User;
import rocks.thiscoder.dsb.slack.Slack;
import rocks.thiscoder.dsb.slack.SlackMessage;

import java.util.List;

/**
 * @author prathik.raj
 */
@RequiredArgsConstructor
public class Question {
    final String question;
    final User user;
    final DateTime time;
    final Issue issue;
    final List<AnswerActionHandler> answerActionHandlers;

    @Getter
    final Slack slack;
    DateTime askTime;

    String answer;

    public Question(Issue issue, User user, DateTime time, List<AnswerActionHandler> answerActionHandlers,
                    Slack slack) {
        this.issue = issue;
        this.question = issue.getSummary() + " done?";
        this.user = user;
        this.time = time;
        this.answerActionHandlers = answerActionHandlers;
        this.slack = slack;
    }

    public void ask() throws DSBException {
        askTime = DateTime.now();
        getSlack().sendMessageToUser(user, question);
    }

    public void poll() throws DSBException  {
        SlackMessage slackMessage = getSlack().getLatestMessageForUser(user);
        while(slackMessage == null || slackMessage.getTime().isBefore(askTime)) {
            System.out.println("Polling");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new DSBException(e);
            }
            slackMessage = getSlack().getLatestMessageForUser(user);
        }

        setAnswer(slackMessage.getMessage());

        for(AnswerActionHandler answerActionHandler: answerActionHandlers) {
            answerActionHandler.takeAction(this.getAnswer(), getIssue());
        }


    }

    public String getQuestion() {
        return question;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getQuestion() + "\n");
        sb.append(getAnswer() + "\n");
        sb.append("\n");
        return sb.toString();
    }

    public void persist() {

    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
