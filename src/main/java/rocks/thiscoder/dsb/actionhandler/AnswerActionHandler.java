package rocks.thiscoder.dsb.actionhandler;

import net.rcarz.jiraclient.Issue;
import rocks.thiscoder.dsb.DSBException;

/**
 * @author prathik.raj
 */
public interface AnswerActionHandler {
    boolean takeAction(String answer, Issue issue) throws DSBException;
}
