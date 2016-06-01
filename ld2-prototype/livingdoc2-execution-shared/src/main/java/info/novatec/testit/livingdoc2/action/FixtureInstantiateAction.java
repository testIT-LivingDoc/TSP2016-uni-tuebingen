package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *
 * Holds the values necessary to invoke the fixture
 */
public class FixtureInstantiateAction extends AbstractAction {

    private final String fixtureName;
    private final List<String> fixtureArgs;

    public FixtureInstantiateAction(String fixtureName, List<String> fixtureArgs) {
        super("fixture");
        this.fixtureName = fixtureName;
        this.fixtureArgs = fixtureArgs;
    }

    public String getTarget() {
        return fixtureName;
    }

    public List<String> getArguments() {
        return fixtureArgs;
    }
}
