package info.novatec.testit.livingdoc2.perform.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executionresult.ActualResult;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;
import info.novatec.testit.livingdoc2.fixture.Fixture;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Class for handling the method calls on the fixture and returning the appropriate execution result
 */
public class MethodInvoker {

    /**
     * Executes the methodcall and returns appropriate {@link ExecutionResult}.
     *
     * @param callID callid of the {@link info.novatec.testit.livingdoc2.api.action.Action} to map it to the result
     * @param fixture {@link Fixture} with the actual fixture class to invoke the method on it
     * @param method method which is to be executed on the fixture
     * @param args arguments for the method
     * @return the {@link ExecutionResult} of this execution
     */
    public ExecutionResult execute(String callID, Fixture fixture, Method method, Object... args) {
        try {
            Object returnValue = invokeMethod(fixture, method, args);
            if (returnValue != null) {
                return new ActualResult(callID, returnValue);
            } else {
                return new VoidResult(callID);
            }
        } catch (Exception e) {
            return new ExceptionResult(callID, e);
        }
    }

    private Object invokeMethod(Fixture fixture, Method method, Object[] args)
        throws IllegalAccessException, InvocationTargetException {
        Object instance = fixture.getInvokedFixture();
        return method.invoke(instance, args);
    }
}
