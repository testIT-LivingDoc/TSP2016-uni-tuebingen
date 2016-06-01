package info.novatec.testit.livingdoc2.executionresult;

/**
 * @author Sebastian Letzel
 *         <p/>
 *         Represents and error in the execution of the structure which will be printed in the table
 */
public class ExceptionResult extends AbstractExecutionResult {

    private final Throwable errorContent;

    public ExceptionResult(String callID, Exception e) {
        super(callID);
        errorContent = e;
    }

    public Object getResult() {
        return errorContent;
    }
}
