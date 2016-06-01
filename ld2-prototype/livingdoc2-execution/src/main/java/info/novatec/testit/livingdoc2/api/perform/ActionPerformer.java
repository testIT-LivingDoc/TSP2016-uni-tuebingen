package info.novatec.testit.livingdoc2.api.perform;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Interface for action performing Classes
 */
public interface ActionPerformer {

    /**
     * Starts the execution of the {@link Action} and returns the {@link ExecutionResult}.
     *
     * @return {@link ExecutionResult} of execution
     */
    ExecutionResult perform();

    void setAction(Action action);
}
