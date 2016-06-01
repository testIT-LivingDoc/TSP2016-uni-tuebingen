package info.novatec.testit.livingdoc2.perform;

import java.util.Collections;
import java.util.List;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.perform.ActionPerformer;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Implements basic functionalities for performer classes
 */
abstract class AbstractPerformer implements ActionPerformer {

    protected Action action;

    public void setAction(Action action) {
        this.action = action;
    }

    protected List<String> getActionArguments() {
        return action.getArguments() != null ? action.getArguments() : Collections.emptyList();
    }

    protected String getCallID() {
        return action.getCallID();
    }
}
