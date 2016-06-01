package info.novatec.testit.livingdoc2.perform;

import java.lang.reflect.Method;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Calls setter with given value
 */
public class SetterInvocationPerformer extends SimpleExpectationPerformer {

    @Override
    protected Method getAppropriateMethod() throws NoSuchMethodException {
        return fixture.getSetter(action.getTarget());
    }
}
