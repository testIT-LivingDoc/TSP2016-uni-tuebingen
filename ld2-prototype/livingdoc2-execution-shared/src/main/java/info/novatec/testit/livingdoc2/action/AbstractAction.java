package info.novatec.testit.livingdoc2.action;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.util.UIDGenerator;


/**
 * @author Sebastian Letzel
 *
 * Base implementation for {@link Action}s
 */
public abstract class AbstractAction implements Action {
    private final String callID;
    private final String type;

    public AbstractAction(String commandType) {
        this.callID = UIDGenerator.nextUID();
        type = commandType;
    }

    @Override
    public String getCallID() {
        return callID;
    }

    @Override
    public String getType() {
        return type;
    }
}
