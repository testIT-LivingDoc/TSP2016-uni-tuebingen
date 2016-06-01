package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Abstract implementation for expectation actions
 */
public abstract class AbstractExpectationAction extends AbstractAction {

    private final String methodName;
    private final List<String> methodArgs;

    public AbstractExpectationAction(String subtype, String content, List<String> args) {
        super(subtype);
        methodName = content;
        methodArgs = args;

    }

    @Override
    public String getTarget() {
        return methodName;
    }

    /**
     * @return returns an empty list to avoid {@link NullPointerException}
     */
    @Override
    public List<String> getArguments() {
        return methodArgs;
    }
}
