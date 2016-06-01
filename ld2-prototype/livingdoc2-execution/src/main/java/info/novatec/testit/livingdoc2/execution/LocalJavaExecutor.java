package info.novatec.testit.livingdoc2.execution;

import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.api.perform.ActionPerformer;
import info.novatec.testit.livingdoc2.config.ExecutionConfig;


/**
 * @author Sebastian Letzel
 *
 * Controles the flow of the execution
 */
public class LocalJavaExecutor extends AbstractJavaExecutor {

    private final ExecutionConfig executionConfig;

    public LocalJavaExecutor(ExecutionConfig executionConfig) {
        super();
        this.executionConfig = executionConfig;
    }

    @Override
    public List<ExecutionResult> executeActionList(List<Action> actions) {
        List<ExecutionResult> results = new ArrayList<>();

        for (Action action : actions) {
            ExecutionResult result = executeSingleAction(action);
            results.add(result);
        }

        return results;
    }

    @Override
    public ExecutionResult executeSingleAction(Action action) {
        ActionPerformer performer = getActionPerformer(action);
        performer.setAction(action);
        return performer.perform();
    }

}
