package info.novatec.testit.livingdoc2.perform;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;


/**
 * @author Sebastian Letzel
 */
public class AfterSpecPerformer extends AbstractPerformer {
    @Override
    public ExecutionResult perform() {
        return new VoidResult(getCallID());
    }
}
