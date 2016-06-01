package info.novatec.testit.livingdoc2.executionresult;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;


/**
 * @author Sebastian Letzel
 *
 * Basic functionalities for all {@link ExecutionResult}s
 */
public abstract class AbstractExecutionResult implements ExecutionResult {
    private final String callID;
    private String resultAsString;

    public AbstractExecutionResult(String callID) {
        this.callID = callID;
    }

    @Override
    public String getCallID() {
        return callID;
    }

    @Override
    public void setResultAsString(String resultAsString) {
        this.resultAsString = resultAsString;
    }

    @Override
    public String getStringResult() {
        return resultAsString;
    }
}
