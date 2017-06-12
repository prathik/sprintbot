package rocks.thiscoder.dsb.actionhandler;

import rocks.thiscoder.dsb.actionhandler.impl.InProgressHandler;
import rocks.thiscoder.dsb.actionhandler.impl.ResolveOnYes;
import rocks.thiscoder.dsb.jira.Jira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author prathik.raj
 */
public class CommonHandlersFactory {
    private final static HashMap<Jira, List<AnswerActionHandler>> handlersMap = new HashMap<Jira,
            List<AnswerActionHandler>>();

    public static List<AnswerActionHandler> getCommonHandlers(Jira jira) {
        if(handlersMap.get(jira) == null) {
            final AnswerActionHandler answerActionHandler = new ResolveOnYes(jira);
            final AnswerActionHandler inProgress = new InProgressHandler(jira);
            handlersMap.put(jira, new ArrayList<AnswerActionHandler>() {{
                add(answerActionHandler);
                add(inProgress);
            }});
        }
        return handlersMap.get(jira);
    }
}
