package info.novatec.testit.livingdoc2.perform;

import java.lang.reflect.Method;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Performs a method call on the getters of the fixture
 */
public class GetterInvocationPerformer extends SimpleExpectationPerformer {

    @Override
    protected Method getAppropriateMethod() throws NoSuchMethodException {
        return fixture.getGetter(action.getTarget());
    }
}
