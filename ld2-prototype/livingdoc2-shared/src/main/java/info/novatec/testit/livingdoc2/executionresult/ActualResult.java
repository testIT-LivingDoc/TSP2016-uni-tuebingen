package info.novatec.testit.livingdoc2.executionresult;

/**
 * @author Sebastian Letzel
 */
public class ActualResult extends AbstractExecutionResult {

    private final Object result;

    public ActualResult(String callID, Object result) {
        super(callID);
        this.result = result;
    }

    @Override
    public Object getResult() {
        return result;
    }
}
