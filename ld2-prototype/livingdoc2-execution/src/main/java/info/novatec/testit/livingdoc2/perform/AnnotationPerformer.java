package info.novatec.testit.livingdoc2.perform;

import java.lang.reflect.Method;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.fixture.Fixture;
import info.novatec.testit.livingdoc2.perform.invocation.MethodInvoker;
import info.novatec.testit.livingdoc2.reflection.FixtureStore;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Performes the execution of annotations listet in {@link info.novatec.testit.livingdoc2.perform.annotations.AnnotationListing}
 *         Method
 */
public class AnnotationPerformer extends AbstractPerformer {
    @Override
    public ExecutionResult perform() {
        Fixture fixture = FixtureStore.getInstance().getCurrentFixture();

        Method annotated = fixture.getMethod(action.getType());
        return new MethodInvoker().execute(getCallID(), fixture, annotated);
    }
}
