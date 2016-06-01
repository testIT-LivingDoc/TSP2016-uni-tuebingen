package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         This {@link info.novatec.testit.livingdoc2.api.action.Action} is used ignore a {@link
 *         info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult} check and only display the content from the
 *         result
 */
public class DisplayAction extends SimpleExpectationAction {
    public DisplayAction(String content, List<String> args) {
        super(content, args);
    }
}
