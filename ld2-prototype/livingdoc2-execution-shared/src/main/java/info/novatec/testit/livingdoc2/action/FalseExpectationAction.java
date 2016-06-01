package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Action for a methodcall which expects a false boolean as returntype
 */
public class FalseExpectationAction extends SimpleExpectationAction {
    public FalseExpectationAction(String content, List<String> args) {
        super(content, args);
    }
}
