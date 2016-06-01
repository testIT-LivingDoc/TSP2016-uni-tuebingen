package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Action for a methodcall which expects a true boolean as returntype
 */
public class TrueExpectationAction extends SimpleExpectationAction {
    public TrueExpectationAction(String content, List<String> args) {
        super(content, args);
    }
}
