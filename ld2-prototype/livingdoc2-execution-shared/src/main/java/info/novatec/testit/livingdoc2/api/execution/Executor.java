package info.novatec.testit.livingdoc2.api.execution;

import java.util.List;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Interface for Executor implementations
 */
public interface Executor {
    List<ExecutionResult> executeActionList(List<Action> actions);

    ExecutionResult executeSingleAction(Action action);
}
