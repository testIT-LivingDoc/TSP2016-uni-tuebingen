package info.novatec.testit.livingdoc2.api.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Interface for action messages, which will be sent to the executor
 */
public interface Action {

    /**
     * @return the id of the {@code Call} which to which this action is connected
     */
    String getCallID();

    /**
     * @return the type of the command to find the appropriate executor
     */
    String getType();

    /**
     * @return content which is used for execution
     */
    String getTarget();

    /**
     * @return optional arguments used for execution
     */
    List<String> getArguments();
}
