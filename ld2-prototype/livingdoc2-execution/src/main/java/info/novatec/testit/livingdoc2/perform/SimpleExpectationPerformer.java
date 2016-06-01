package info.novatec.testit.livingdoc2.perform;

import java.lang.reflect.Method;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.fixture.Fixture;
import info.novatec.testit.livingdoc2.perform.invocation.MethodInvoker;
import info.novatec.testit.livingdoc2.reflection.FixtureStore;
import info.novatec.testit.livingdoc2.util.TypeConversion;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Simply calls a method and returns appropriate {@link ExecutionResult}
 */
public class SimpleExpectationPerformer extends AbstractPerformer {

    protected Fixture fixture;
    protected Method method;
    protected String[] args;

    @Override
    public ExecutionResult perform() {
        try {
            fixture = FixtureStore.getInstance().getCurrentFixture();
            method = getAppropriateMethod();
            args = getActionArguments().toArray(new String[getActionArguments().size()]);

            return invokeMethod();
        } catch (NoSuchMethodException e) {
            return new ExceptionResult(action.getCallID(), e);
        }
    }

    protected Method getAppropriateMethod() throws NoSuchMethodException {
        return fixture.getMethod(action.getTarget());
    }

    private ExecutionResult invokeMethod() {
        Class<?>[] types = method.getParameterTypes();
        Object[] parameters = null;
        if (args.length > 0) {
            parameters = TypeConversion.convert(args, types);
        }
        return new MethodInvoker().execute(getCallID(), fixture, method, parameters);
    }
}
