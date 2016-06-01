package info.novatec.testit.livingdoc2.action;

import java.util.Collections;
import java.util.List;


/**
 * @author Sebastian Letzel
 *
 * Action which will call a setter method by name {@code setVariable} in the fixture und fills it with {@code setValue}
 */
public class SetVariableAction extends AbstractAction {

    private final String setVariable;
    private final String setValue;

    public SetVariableAction(String setVariable, String setValue) {
        super("setter");
        this.setVariable = setVariable;
        this.setValue = setValue;
    }

    @Override
    public String getTarget() {
        return setVariable;
    }

    /**
     * @return the single value as {@link java.util.Collections.SingletonList}
     */
    @Override
    public List<String> getArguments() {
        return Collections.singletonList(setValue);
    }
}
