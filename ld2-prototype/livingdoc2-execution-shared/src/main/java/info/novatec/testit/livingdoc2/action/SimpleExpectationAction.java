package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *
 * Action for simply calling a method and expecting a returntype
 */
public class SimpleExpectationAction extends AbstractExpectationAction {

    public SimpleExpectationAction(String content, List<String> args) {
        super("expectation", content, args);
    }
}
