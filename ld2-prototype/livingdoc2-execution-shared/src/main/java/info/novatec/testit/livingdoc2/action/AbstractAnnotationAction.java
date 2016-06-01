package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Abstract class for all actions concerning annotations
 */
public abstract class AbstractAnnotationAction extends AbstractAction {

    public AbstractAnnotationAction(String commandType) {
        super(commandType);
    }

    @Override
    public String getTarget() {
        return null;
    }

    @Override
    public List<String> getArguments() {
        return null;
    }
}
